package org.tsinghua.omedia.utils;

import org.springframework.stereotype.Component;

/**
 * 
 * @author xuhongfeng
 *
 */
@Component("idUtils")
public class IDUtils {

    public synchronized long genId () {
        long id = System.currentTimeMillis();
        try {
            Thread.sleep(5L);
        } catch (InterruptedException e) {
            //ignore it
        }
        return id;
    }
}
