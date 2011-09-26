package org.tsinghua.omedia.service;

import java.io.IOException;

import org.tsinghua.omedia.model.Account;

public interface AccountService extends Service {
    public boolean isAccountExist(String username) throws IOException;
    public Account addAccount(String username, String password, String email) throws IOException;
    public Account login(String username,String password) throws IOException;
}
