package org.tsinghua.omedia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  
 * @author xuhongfeng
 *
 */
public class MD5Utils {
    
    public static String md5(File file) throws IOException {
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
    
    public static String md5(String s) throws IOException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("can not found algorighm MD5", e);
        }
        byte[] digest = md.digest(s.getBytes());
        return getHashtext(digest);
    }

    private static String getHashtext(byte[] digest) {
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext.toUpperCase();
    }
}
