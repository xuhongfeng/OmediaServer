package org.tsinghua.omedia.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.model.Account;

@Component("accountUtil")
public class AccountUtil {
    @Autowired
    private MD5Util md5Util;
    
    public void generatedPassword(Account account) throws IOException {
        account.setPassword(md5Util.md5(account.getPassword()));
    }
    
    public void generateId(Account account) {
        account.setId(genId());
    }
    
    private synchronized long genId () {
        long id = System.currentTimeMillis();
        try {
            Thread.sleep(5L);
        } catch (InterruptedException e) {
            //ignore it
        }
        return id;
    }
}
