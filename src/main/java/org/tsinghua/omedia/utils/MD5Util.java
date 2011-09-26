package org.tsinghua.omedia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

/**
 *  
 * @author xuhongfeng
 *
 */
@Component("md5Util")
public class MD5Util {
    
    /**
     *  è®¡ç?ä¸?¸ª??»¶??D5?¼ï? è¿??ç»??ä»?6è¿??å­??ä¸²è¡¨ç¤ºï?32ä¸??ç¬??å¤§å?ï¼?     * @param file
     * @return
     * @throws IOException
     */
    public String md5(File file) throws IOException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("can not found algorighm MD5", e);
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IOException("file for md5 not found!", e);
        }
        byte[] buf = new byte[1024];
        long readLen = 0;
        long totalLen = file.length();
        int tmpLen;
        while(readLen < totalLen) {
            tmpLen = fis.read(buf, 0, 1024);
            md.update(buf, 0, tmpLen);
            readLen += tmpLen;
        }
        byte[] digest = md.digest();
        return getHashtext(digest);
    }
    
    /**
     * è®¡ç?ä¸?¸ªå­??ä¸²ç?MD5?¼ï? è¿??ç»??ä»?6è¿??å­??ä¸²è¡¨ç¤ºï?32ä¸??ç¬??å¤§å?ï¼?     * @param s
     * @return
     * @throws IOException
     */
    public String md5(String s) throws IOException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("can not found algorighm MD5", e);
        }
        byte[] digest = md.digest(s.getBytes());
        return getHashtext(digest);
    }

    private String getHashtext(byte[] digest) {
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext.toUpperCase();
    }
}
