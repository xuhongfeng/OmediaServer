package org.tsinghua.omedia.utils;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.model.Account;

@Component("accountUtil")
public class AccountUtil {
    @Autowired
    private MD5Util md5Util;
    
    private Random random = new Random();
    
    public void generatedPassword(Account account) throws IOException {
        account.setPassword(md5Util.md5(account.getPassword()));
    }
    
    public void generateId(Account account) {
        account.setAccountId(genId());
    }
    
    public void generateToken(Account account) {
        account.setToken(account.getToken() + random.nextLong());
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
