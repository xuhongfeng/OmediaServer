package org.tsinghua.omedia.service;

import java.io.IOException;

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

    public boolean isAccountExist(String username) throws IOException {
        Account account = accountDao.getAccount(username);
        return account != null;
    }

    public Account addAccount(String username, String password, String email) throws IOException {
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setUsername(username);
        accountUtil.generateId(account);
        accountUtil.generatedPassword(account);
        accountDao.addAccount(account);
        return account;
    }

    public Account login(String username, String password) throws IOException {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        accountUtil.generatedPassword(account);
        account = accountDao.getAccount(account.getUsername(), account.getPassword());
        return account;
    }
}
