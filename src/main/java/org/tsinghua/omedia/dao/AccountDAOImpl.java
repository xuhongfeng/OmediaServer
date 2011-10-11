package org.tsinghua.omedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.Account;

@Component("accountDao")
public class AccountDAOImpl extends BaseDao implements AccountDAO {
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(AccountDAOImpl.class);

    public Account getAccount(String username) throws DbException {
        String sql = "select accountId,password,email,realName,address,phone," +
                "version,token,friendsVersion,friendRequestVersion" +
                " from account where username=?";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            Account account = null;
            if(rs.next()) {
                account = new Account();
                long id = rs.getLong(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String realName = rs.getString(4);
                String address = rs.getString(5);
                String phone = rs.getString(6);
                long version = rs.getLong(7);
                long token = rs.getLong(8);
                long friendsVersion = rs.getLong(9);
                long friendRequestVersion = rs.getLong(10);
                account.setEmail(email);
                account.setAccountId(id);
                account.setPassword(password);
                account.setUsername(username);
                account.setRealName(realName);
                account.setAddress(address);
                account.setPhone(phone);
                account.setVersion(version);
                account.setToken(token);
                account.setFriendsVersion(friendsVersion);
                account.setFriendRequestVersion(friendRequestVersion);
            }
            return account;
        } catch (Exception e) {
            throw new DbException("getAccountByUsername failed,username="
                    + username, e);
        } finally {
            closeConnection(conn);
        }
    }
    
    public Account getAccount(long accountId) throws DbException {
        String sql = "select username,password,email,realName,address,phone," +
                "version,token,friendsVersion,friendRequestVersion" +
                " from account where accountId=?";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountId);
            ResultSet rs = stmt.executeQuery();
            Account account = null;
            if(rs.next()) {
                account = new Account();
                String username = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String realName = rs.getString(4);
                String address = rs.getString(5);
                String phone = rs.getString(6);
                long version = rs.getLong(7);
                long token = rs.getLong(8);
                long friendsVersion = rs.getLong(9);
                long friendRequestVersion = rs.getLong(10);
                account.setEmail(email);
                account.setAccountId(accountId);
                account.setPassword(password);
                account.setUsername(username);
                account.setRealName(realName);
                account.setAddress(address);
                account.setPhone(phone);
                account.setVersion(version);
                account.setToken(token);
                account.setFriendsVersion(friendsVersion);
                account.setFriendRequestVersion(friendRequestVersion);
            }
            return account;
        } catch (Exception e) {
            throw new DbException("getAccountByUsername failed,accountId="
                    + accountId, e);
        } finally {
            closeConnection(conn);
        }
    }

    public Account getAccount(String username, String password)
            throws DbException {
        String sql = "select accountId,email,realName,address,phone," +
                "version,token,friendsVersion,friendRequestVersion" +
                " from account where username=? and password=?";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            Account account = null;
            if(rs.next()) {
                account = new Account();
                long id = rs.getLong(1);
                String email = rs.getString(2);
                String realName = rs.getString(3);
                String address = rs.getString(4);
                String phone = rs.getString(5);
                long version = rs.getLong(6);
                long token = rs.getLong(7);
                long friendsVersion = rs.getLong(8);
                long friendRequestVersion = rs.getLong(9);
                account.setEmail(email);
                account.setAccountId(id);
                account.setPassword(password);
                account.setUsername(username);
                account.setRealName(realName);
                account.setAddress(address);
                account.setPhone(phone);
                account.setVersion(version);
                account.setToken(token);
                account.setFriendsVersion(friendsVersion);
                account.setFriendRequestVersion(friendRequestVersion);
            }
            return account;
        } catch (Exception e) {
            throw new DbException("getAccountByNameAndPassword failed,username="
                    + username + ",password=" + password, e);
        } finally {
            closeConnection(conn);
        }
    }


    public void saveAccount(Account account) throws DbException {
        String sql = "replace into account(accountId,username,password,email," +
                "realName,address,phone,version,token,friendsVersion,friendRequestVersion)" +
                " values(?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, account.getAccountId());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getPassword());
            stmt.setString(4, account.getEmail());
            stmt.setString(5, account.getRealName());
            stmt.setString(6, account.getAddress());
            stmt.setString(7, account.getPhone());
            stmt.setLong(8, account.getVersion());
            stmt.setLong(9, account.getToken());
            stmt.setLong(10, account.getFriendsVersion());
            stmt.setLong(11, account.getFriendRequestVersion());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("add account failed! account="
                    + account, e);
        } finally {
            closeConnection(conn);
        }
    }

    public void updateToken(long accountId, long token) throws DbException {
        String sql = "update account set token=? where accountId=?";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, token);
            stmt.setLong(2, accountId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("update account token failed! accountId="
                    + accountId, e);
        } finally {
            closeConnection(conn);
        }
    }
    
    public void updateFriendsVersion(long accountId, long friendsVersion)
            throws DbException {
        String sql = "update account set friendsVersion=? where accountId=?";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, friendsVersion);
            stmt.setLong(2, accountId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("update account friendsVersion failed! accountId="
                    + accountId, e);
        } finally {
            closeConnection(conn);
        }
    }

    public void updateFriendRequestVersion(long accountId,
            long friendRequestVersion) throws DbException {
        String sql = "update account set friendRequestVersion=? where accountId=?";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, friendRequestVersion);
            stmt.setLong(2, accountId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("update account friendRequestVersion failed! accountId="
                    + accountId, e);
        } finally {
            closeConnection(conn);
        }
    }

    public List<Account> searchAccounts(String keyword) throws DbException {
        List<Account> ret = new ArrayList<Account>();
        //以whitespace split String, 结果可能含whitespace
        String[] temp  =keyword.split("\\s+");
        List<String> words = new ArrayList<String>();
        //取出whitespace
        for(String e:temp) {
            if(!e.trim().isEmpty()) {
                words.add(e);
            }
        }
        if(words.size() == 0) {
            return ret;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select accountId,username,password,email,realName,address,phone," +
                "version,token,friendsVersion,friendRequestVersion" +
                " from account where username like ? or realName like ? or address like ?");
        for(int i=1; i<words.size(); i++) {
            sql.append(" or username like ? or realName like ? or address like ?");
        };
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            for(int i=0; i<words.size(); i++) {
                stmt.setString(3*i+1, "%"+words.get(i)+"%");
                stmt.setString(3*i+2, "%"+words.get(i)+"%");
                stmt.setString(3*i+3, "%"+words.get(i)+"%");
            }
            ResultSet rs = stmt.executeQuery();
            Account account = null;
            while(rs.next()) {
                account = new Account();
                long accountId = rs.getLong(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                String email = rs.getString(4);
                String realName = rs.getString(5);
                String address = rs.getString(6);
                String phone = rs.getString(7);
                long version = rs.getLong(8);
                long token = rs.getLong(9);
                long friendsVersion = rs.getLong(10);
                long friendRequestVersion = rs.getLong(11);
                account.setEmail(email);
                account.setAccountId(accountId);
                account.setPassword(password);
                account.setUsername(username);
                account.setRealName(realName);
                account.setAddress(address);
                account.setPhone(phone);
                account.setVersion(version);
                account.setToken(token);
                account.setFriendsVersion(friendsVersion);
                account.setFriendRequestVersion(friendRequestVersion);
                ret.add(account);
            }
            return ret;
        } catch (Exception e) {
            throw new DbException("search accounts failed,keyword="
                    + keyword, e);
        } finally {
            closeConnection(conn);
        }
    }
    
}
