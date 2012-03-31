package org.tsinghua.omedia.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.model.CcnFile;

@Controller
public class CcnController extends BaseController {
    private Logger logger = Logger.getLogger(CcnController.class);
    
    public static volatile long ccnFileVersion = 0L;
    
    @Value("${ccn.storage.path}")
    private String ccnStoragePath;
    
    private static File tmpDir = new File(System.getProperty("java.io.tmpdir"));

    @RequestMapping(value="/showFriendCcnFiles.do", method=RequestMethod.GET)
    @ResponseBody
    public String showFriendCcnFile(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token
            ,@RequestParam("friendId") long friendId) {
        logger.info("showFriendCcnFiles.do called, accountId="+accountId +",token=" + token
                +",friendId="+friendId);
        long startTime = System.currentTimeMillis();
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            boolean isFriend = friendService.isFriend(accountId, friendId);
            if(!isFriend) {
                return "{\"result\":4}";
            }
            List<CcnFile> ccnFiles = ccnService.listCcnFiles(friendId);
            JsonCcnFileArray json = new JsonCcnFileArray(ccnFileVersion, ccnFiles.toArray(new CcnFile[0]));
            logger.info("showFriendCcnFiles.do, time cost="+(System.currentTimeMillis()-startTime));
            return objectMapper.writeValueAsString(json);
        } catch (IOException e) {
            logger.error("showFriendCcnFiles failed", e);
            return "{\"result\":-1}";
        }
    }
    
    @RequestMapping(value="/showCcnFiles.do", method=RequestMethod.GET)
    @ResponseBody
    public String showCcnFiles(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token) {
        logger.info("showPublicCcnFiles called, accountId="+accountId +",token=" + token);
        long startTime = System.currentTimeMillis();
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            List<CcnFile> ccnFiles = ccnService.listCcnFiles(accountId);
            JsonCcnFileArray json = new JsonCcnFileArray(ccnFileVersion, ccnFiles.toArray(new CcnFile[0]));
            logger.info("showCcnFiles.do, time cost="+(System.currentTimeMillis()-startTime));
            return objectMapper.writeValueAsString(json);
        } catch (IOException e) {
            logger.error("showPublicCcnFiles failed", e);
            return "{\"result\":-1}";
        }
    }
    
    @RequestMapping(value="/showCcnFiles-vm.do", method=RequestMethod.GET)
    public ModelAndView showCcnFiles_vm(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token
            ,ModelAndView mav, HttpServletResponse response) {
        logger.info("showPublicCcnFilesVm called, accountId="+accountId +",token=" + token);
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
            	return errorMav("cookie out of date");
            }
            List<CcnFile> ccnFiles = ccnService.listCcnFiles(accountId);
            mav.setViewName("ccn_file");
            mav.addObject("files", ccnFiles);
            mav.addObject("tool", velocityTool);
            response.setContentType("text/html;charset=UTF-8");
            return mav;
        } catch (IOException e) {
            logger.error("showPublicCcnFiles failed", e);
            return errorMav("server error");
        }
    }
    
    @RequestMapping(value="/ccnPutFile.do", method=RequestMethod.POST)
    @ResponseBody
    public String ccnPutFile(HttpServletRequest request) {
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
                    model.put("size", item.getSize());
                    try {
                        input = item.getInputStream();
                    } catch (IOException e) {
                        logger.error("", e);
                        return "{\"result\":-1}";
                    }
                }
            }
            long accountId = Long.parseLong(model.get("accountId").toString());
            long token = Long.parseLong(model.get("token").toString());
            Long size = (Long) model.get("size");
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
            ccnFile.setType(CcnFile.TYPE_PUBLIC);
            ccnFile.setTime(new Date());
            ccnFile.setFilePath(filePath);
            ccnFile.setSize(size);
            ccnService.saveCcnFile(ccnFile);
            logger.info("save ccnFile:" + ccnFile);
            ccnUtils.ccnPutFile(ccnFile);
            return "{\"result\":1}";
        } catch (FileUploadException e) {
            logger.error("", e);
            return "{\"result\":-1}";
        } catch (IOException e) {
            logger.error("", e);
            return "{\"result\":-1}";
        }
    }

    @RequestMapping(value="/deleteCcnFile.do", method=RequestMethod.GET)
    @ResponseBody
    public String deleteCcnFile(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token,
            @RequestParam("ccnName") String ccnName) {
        logger.info("deleteCcnFile called, accountId="+accountId +",token=" + token
                +", ccnName="+ccnName);
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            ccnService.deleteCcnFile(accountId, ccnName);
            return "{\"result\":1}";
        } catch (IOException e) {
            logger.error("deleteCcnFile failed", e);
            return "{\"result\":-1}";
        }
    }

    @SuppressWarnings("unused")
    private static class JsonCcnFileArray {
        private int result = 1;
        private long version;
        private JsonCcnFile[] ccnFiles = new JsonCcnFile[0];
        
        public JsonCcnFileArray(long version, CcnFile[] files) {
            this.version = version;
            this.ccnFiles = new JsonCcnFile[files.length];
            for(int i=0; i<ccnFiles.length; i++) {
                ccnFiles[i] = new JsonCcnFile(files[i]);
            }
        }
        
        public int getResult() {
            return result;
        }
        public void setResult(int result) {
            this.result = result;
        }
        public long getVersion() {
            return version;
        }
        public void setVersion(long version) {
            this.version = version;
        }
        public JsonCcnFile[] getCcnFiles() {
            return ccnFiles;
        }
        public void setCcnFiles(JsonCcnFile[] ccnFiles) {
            this.ccnFiles = ccnFiles;
        }
        
    }
    
    @SuppressWarnings("unused")
    private static class JsonCcnFile {
        private long accountId;
        private long time;
        private String ccnName;
        private String filePath;
        private int type;
        private long size;
        
        public JsonCcnFile(CcnFile ccnFile) {
            accountId =ccnFile.getAccountId();
            time = ccnFile.getTime().getTime();
            ccnName = ccnFile.getCcnname();
            filePath = ccnFile.getFilePath();
            type = ccnFile.getType();
            size = ccnFile.getSize();
        }
        
        public long getAccountId() {
            return accountId;
        }
        public void setAccountId(long accountId) {
            this.accountId = accountId;
        }
        public long getTime() {
            return time;
        }
        public void setTime(long time) {
            this.time = time;
        }
        public String getCcnName() {
            return ccnName;
        }
        public void setCcnName(String ccnName) {
            this.ccnName = ccnName;
        }
        public String getFilePath() {
            return filePath;
        }
        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }
        public long getSize() {
            return size;
        }
        public void setSize(long size) {
            this.size = size;
        }
    }
}
