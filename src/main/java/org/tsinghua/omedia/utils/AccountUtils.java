package org.tsinghua.omedia.utils;

import java.io.IOException;
import java.util.Random;

import org.tsinghua.omedia.model.Account;

public class AccountUtils {
    
    private static Random random = new Random();
    
    public static String encryptPassword(String password) throws IOException {
        return MD5Utils.md5(password);
    }
    
    public static void generateId(Account account) {
        account.setAccountId(genId());
    }
    
    public static long generateToken() {
        return random.nextInt();
    }
    
    private static synchronized long genId () {
        long id = System.currentTimeMillis();
        try {
            Thread.sleep(5L);
        } catch (InterruptedException e) {
            //ignore it
        }
        return id;
    }
}
