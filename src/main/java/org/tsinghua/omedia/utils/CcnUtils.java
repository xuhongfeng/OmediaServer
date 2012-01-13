package org.tsinghua.omedia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.io.CCNOutputStream;
import org.ccnx.ccn.io.RepositoryOutputStream;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.ccnx.ccn.utils.CommonParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.dao.CcnDao;
import org.tsinghua.omedia.model.CcnFile;

/**
 * 
 * @author xuhongfeng
 *
 */
@Component("ccnUtils")
public class CcnUtils {
    private static final Logger logger = Logger.getLogger(CcnUtils.class);
    
    @Autowired
    private CcnDao ccnDao;
    
    public void ccnPutFile(CcnFile ccnFile) throws IOException {
        logger.info("CcnUtils.ccnPutFile="+ccnFile);
        String ccnUrl = ConfigManager.getConfig().getCcnUrl()
                + File.separator + ccnFile.getCcnname();
        logger.info("ccnUrl="+ccnUrl);
        ccnrm(ccnUrl);
        String filePath = ccnFile.getFilePath();
        try {
            InputStream is = new FileInputStream(filePath);
            ContentName contentName = ContentName.fromURI(ccnUrl);
            logger.info("contentName="+contentName);
            CCNHandle handle = CCNHandle.open();
            CCNOutputStream os = 
                    new RepositoryOutputStream(contentName, handle, false);
            int size = CommonParameters.BLOCK_SIZE;
            int readLen = 0;
            byte [] buffer = new byte[CommonParameters.BLOCK_SIZE];
            while ((readLen = is.read(buffer, 0, size)) != -1){ 
                logger.info("readLen="+readLen);
                os.write(buffer, 0, readLen);
            }
            logger.info("readLen="+readLen);
            os.close();
            is.close();
        } catch (MalformedContentNameStringException e) {
            throw new IOException(e);
        } catch (ConfigurationException e) {
            throw new IOException(e);
        }
    }
    
    public void ccnrm(String ccnUrl) throws IOException {
        String cmd = "ccnrm " + ccnUrl;
        Runtime run = Runtime.getRuntime();
        Process pr;
        try {
            pr = run.exec(cmd);
            pr.waitFor();
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
    
    public void dumpFromDb() {
        try {
            List<CcnFile> files = ccnDao.listAllCcnFiles();
            for(CcnFile e:files) {
                ccnPutFile(e);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
