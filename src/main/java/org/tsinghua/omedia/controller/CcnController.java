package org.tsinghua.omedia.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.model.CcnFile;
import org.tsinghua.omedia.service.AccountService;
import org.tsinghua.omedia.service.CcnService;

@Controller
public class CcnController {
    private Logger logger = Logger.getLogger(CcnController.class);
    
    private volatile long ccnFileVersion = 0L;
    
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CcnService ccnService;
    @Autowired
    private AccountService accountService;
    
    @Value("${ccn.storage.path}")
    private String ccnStoragePath;
    
    private static File tmpDir = new File(System.getProperty("java.io.tmpdir"));

    @RequestMapping(value="/showCcnFiles.do", method=RequestMethod.GET)
    @ResponseBody
    public String showCcnFiles(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token) {
        logger.info("showPublicCcnFiles called, accountId="+accountId +",token=" + token);
        try {
            List<CcnFile> ccnFiles = ccnService.listCcnFiles(accountId);
            JsonCcnFileArray json = new JsonCcnFileArray(ccnFileVersion, ccnFiles.toArray(new CcnFile[0]));
            return objectMapper.writeValueAsString(json);
        } catch (IOException e) {
            logger.error("showPublicCcnFiles failed", e);
            return "{\"result\":-1}";
        }
    }

    @RequestMapping(value="/ccnPutFile.do/{accountId}/{token}", method=RequestMethod.POST)
    @ResponseBody
    public String ccnPutFile(HttpServletRequest request
            ,@PathVariable long accountId, @PathVariable long token) {
        logger.info("ccnPutFile.do called!accountId="+accountId+",token="+token);
        Account account;
        try {
            account = accountService.getAccount(accountId);
        } catch (IOException e1) {
            logger.error("", e1);
            return "{\"result\":-1}";
        }
        if(account==null || account.getToken()!=token) {
            logger.info("auth failed!");
            return "{\"result\":3}";
        }
        if(!ServletFileUpload.isMultipartContent(request)) {
            logger.error("Not a multipart request");
            return "{\"result\":-1}";
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(tmpDir);
        factory.setSizeThreshold(10*1024*1024);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            @SuppressWarnings("unchecked")
            List<FileItem> items = upload.parseRequest(request);
            ModelMap model = new ModelMap();
            InputStream input = null;
            for(FileItem item:items) {
                if(item.isFormField()) {
                    model.put(item.getFieldName(), item.getString());
                } else {
                    String fileName = item.getFieldName();
                    model.put("fileName", fileName);
                    logger.info("file size = " + item.getSize());
                    try {
                        input = item.getInputStream();
                    } catch (IOException e) {
                        logger.error("", e);
                        return "{\"result\":-1}";
                    }
                }
            }
            if(input == null) {
                logger.error("inputstream = null");
                return "{\"result\":-1}";
            }
            String fileName = (String) model.get("fileName");
            if(fileName == null) {
                logger.error("fileName = null");
                return "{\"result\":-1}";
            }
            String ccnName = account.getUsername() + "-" + fileName;
            String filePath = ccnStoragePath+File.separator+ccnName;
            File file = new File(filePath);
            FileUtils.deleteQuietly(file);
            FileChannel out = FileUtils.openOutputStream(file).getChannel();
            ByteBuffer buf = ByteBuffer.allocate(4096);
            buf.clear();
            int readedLen = 0;
            while((readedLen=input.read(buf.array(), buf.position(),
                    buf.remaining()))!= -1) {
                buf.position(buf.position()+readedLen);
                logger.info("readed len = " + buf.position());
                buf.flip();
                out.write(buf);
                buf.clear();
            }
            out.close();
            
            CcnFile ccnFile = new CcnFile();
            ccnFile.setAccountId(account.getAccountId());
            ccnFile.setCcnname(ccnName);
            ccnFile.setType(CcnFile.TYPE_PRIVATE);
            ccnFile.setTime(new Date());
            ccnFile.setFilePath(filePath);
            ccnService.saveCcnFile(ccnFile);
            logger.info("save ccnFile:" + ccnFile);
            return "{\"result\":1}";
        } catch (FileUploadException e) {
            logger.error("", e);
            return "{\"result\":-1}";
        } catch (IOException e) {
            logger.error("", e);
            return "{\"result\":-1}";
        }
    }

    @SuppressWarnings("unused")
    private static class JsonCcnFileArray {
        private int result = 1;
        private long version;
        private CcnFile[] ccnFiles = new CcnFile[0];
        public JsonCcnFileArray(long version, CcnFile[] ccnFiles) {
            this.version = version;
            this.ccnFiles = ccnFiles;
        }
    }
}
