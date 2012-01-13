package org.tsinghua.omedia.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.dao.AccountDao;
import org.tsinghua.omedia.model.Account;

@Component("accountService")
public class AccountServiceImpl extends BaseService implements AccountService {
    @Autowired
    private AccountDao accountDao;

    public Account getAccount(long accountId) throws IOException {
        return accountDao.getAccount(accountId);
    }

    public boolean isUsernameExist(String username) throws IOException {
        Account account = accountDao.getAccount(username);
        return account != null;
    }

    public Account createAccount(String username, String password, String email) throws IOException {
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(accountUtils.encryptPassword(password));
        account.setUsername(username);
        accountUtils.generateId(account);
        account.setVersion(System.currentTimeMillis());
        accountDao.saveAccount(account);
        return account;
    }

    public void updateAccount(Account account) throws IOException {
        account.setVersion(System.currentTimeMillis());
        accountDao.saveAccount(account);
    }

    public Account login(String username, String password) throws IOException {
        Account account = accountDao.getAccount(username,
                accountUtils.encryptPassword(password));
        return account;
    }

    public long updateToken(long accountId) throws IOException {
        long token = accountUtils.generateToken();
        accountDao.updateToken(accountId, token);
        return token;
    }

    public boolean checkToken(long accountId, long token) throws IOException {
        Account account = accountDao.getAccount(accountId);
        if(account==null || account.getToken()!=token) {
            return false;
        }
        return true;
    }
    
    public void updateFriendsVersion(long accountId) throws IOException {
        long friendsVersion = System.currentTimeMillis();
        accountDao.updateFriendsVersion(accountId, friendsVersion);
    }

    public void updateFriendRequestVersion(long accountId) throws IOException {
        long friendRequestVersion = System.currentTimeMillis();
        accountDao.updateFriendRequestVersion(accountId, friendRequestVersion);
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
