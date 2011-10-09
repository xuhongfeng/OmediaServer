package org.tsinghua.omedia.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.dao.AccountDAO;
import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.utils.AccountUtil;

@Component("accountService")
public class AccountServiceImple implements AccountService {
    @Autowired
    private AccountDAO accountDao;
    @Autowired
    private AccountUtil accountUtil;

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
        account.setPassword(accountUtil.encryptPassword(password));
        account.setUsername(username);
        accountUtil.generateId(account);
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
                accountUtil.encryptPassword(password));
        return account;
    }

    public long updateToken(long accountId) throws IOException {
        long token = accountUtil.generateToken();
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

    public void setAccountDao(AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> searchAccounts(String keyword) throws IOException {
        return accountDao.searchAccounts(keyword);
    }
}
