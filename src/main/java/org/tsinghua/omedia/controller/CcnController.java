package org.tsinghua.omedia.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tsinghua.omedia.model.CcnFile;
import org.tsinghua.omedia.service.CcnService;

@Controller
public class CcnController {
    private Logger logger = Logger.getLogger(CcnController.class);
    
    private volatile long ccnFileVersion = 0L;
    
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CcnService ccnService;

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
