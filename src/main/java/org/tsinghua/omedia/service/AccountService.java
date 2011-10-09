package org.tsinghua.omedia.service;

import java.io.IOException;
import java.util.List;

import org.tsinghua.omedia.dao.AccountDAO;
import org.tsinghua.omedia.model.Account;

public interface AccountService extends Service {
    public Account getAccount(long accountId) throws IOException;
    public boolean isUsernameExist(String username) throws IOException;
    public Account createAccount(String username, String password, String email) throws IOException;
    public void updateAccount(Account account) throws IOException;
    public Account login(String username,String password) throws IOException;
    public long updateToken(long accountId) throws IOException;
    public boolean checkToken(long accountId, long token) throws IOException;
    public List<Account> searchAccounts(String keyword) throws IOException;
    
    public void setAccountDao(AccountDAO accountDao);
}
