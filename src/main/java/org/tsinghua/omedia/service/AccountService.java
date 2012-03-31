package org.tsinghua.omedia.service;

import java.io.IOException;

import org.tsinghua.omedia.dao.AccountDao;
import org.tsinghua.omedia.model.Account;

/**
 * 
 * @author xuhongfeng
 *
 */
public interface AccountService {
    public Account getAccount(long accountId) throws IOException;
    public boolean isUsernameExist(String username) throws IOException;
    public Account createAccount(String username, String password, String email) throws IOException;
    public void updateAccount(Account account) throws IOException;
    public Account login(String username,String password) throws IOException;
    public long updateToken(long accountId) throws IOException;
    public boolean checkToken(long accountId, long token) throws IOException;
    public void updateFriendsVersion(long accountId) throws IOException;
    public void updateFriendRequestVersion(long accountId) throws IOException;
    public String getName(long accountId) throws IOException;
    
    public void setAccountDao(AccountDao accountDao);
}
