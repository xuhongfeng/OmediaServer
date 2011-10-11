package org.tsinghua.omedia.dao;

import java.util.List;

import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.Account;

public interface AccountDAO {
    public Account getAccount(String username) throws DbException;
    public Account getAccount(long accountId) throws DbException;
    public Account getAccount(String username, String password) throws DbException;

    public void saveAccount(Account account) throws DbException;
    public void updateToken(long accountId, long token) throws DbException;
    public void updateFriendsVersion(long accountId, long friendsVersion) throws DbException;
    public void updateFriendRequestVersion(long accountId, long friendRequestVersion) throws DbException;
    public List<Account> searchAccounts(String keyword) throws DbException;
}
