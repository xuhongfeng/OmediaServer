package org.tsinghua.omedia.utils;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

/**
 * 
 * @author xuhongfeng
 *
 */
public class StringUtils {
    private static final Logger logger = Logger.getLogger(StringUtils.class);
    
    public static String utf8toiso88591(String s) {
        try {
            return new String(s.getBytes("utf8"), "ISO8859_1");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            return null;
        }
    }
    
    public static String iso88591toutf8(String s) {
        try {
            return new String(s.getBytes("ISO8859_1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            return null;
        }
    }
}
