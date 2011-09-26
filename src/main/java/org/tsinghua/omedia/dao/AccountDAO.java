package org.tsinghua.omedia.dao;

import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.Account;

public interface AccountDAO extends DAO {
    public Account getAccount(String username) throws DbException;
    public Account getAccount(String username, String password) throws DbException;

    public void addAccount(Account account) throws DbException;
}
