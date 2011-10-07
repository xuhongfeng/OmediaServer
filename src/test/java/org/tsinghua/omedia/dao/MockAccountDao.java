package org.tsinghua.omedia.dao;

import java.util.List;

import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.Account;

public class MockAccountDao implements AccountDAO {
    private List<Account> datas;
    
    public MockAccountDao(List<Account> datas) {
        this.datas = datas;
    }

    @Override
    public Account getAccount(String username) throws DbException {
        for(Account e:datas) {
            if(e.getUsername().equals(username)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Account getAccount(long accountId) throws DbException {
        for(Account e:datas) {
            if(e.getAccountId() == accountId) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Account getAccount(String username, String password)
            throws DbException {
        for (Account e : datas) {
            if (e.getUsername().equals(username)
                    && e.getPassword().equals(password)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void saveAccount(Account account) throws DbException {
        for(int i=0; i<datas.size(); i++) {
            if(datas.get(i).getAccountId() == account.getAccountId()) {
                datas.set(i, account);
                return;
            }
        }
        datas.add(account);
    }

    @Override
    public void updateToken(long accountId, long token) throws DbException {
        for(Account e:datas) {
            if(e.getAccountId() == accountId) {
                e.setToken(token);
                return;
            }
        }
    }

}
