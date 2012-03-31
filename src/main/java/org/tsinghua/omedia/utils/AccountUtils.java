package org.tsinghua.omedia.utils;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.model.Account;

@Component("accountUtils")
public class AccountUtils {
    @Autowired
    private MD5Utils md5Utils;
    
    @Autowired
    private IDUtils idUtils;
    
    private Random random = new Random();
    
    public String encryptPassword(String password) throws IOException {
        return md5Utils.md5(password);
    }
    
    public void generateId(Account account) {
        account.setAccountId(idUtils.genId());
    }
    
    public long generateToken() {
        return random.nextInt();
    }
    
}
