package org.tsinghua.omedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.Account;

@Component("accountDAO")
public class AccountDAOImpl extends AbstractDAO implements AccountDAO {
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(AccountDAOImpl.class);

    public Account getAccount(String username) throws DbException {
        String sql = "select id,password,email from account where username=?";
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
                account.setEmail(email);
                account.setId(id);
                account.setPassword(password);
                account.setUsername(username);
            }
            return account;
        } catch (Exception e) {
            throw new DbException("getAccountByUsername failed,username="
                    + username, e);
        } finally {
            closeConnection(conn);
        }
    }
    
    public Account getAccount(String username, String password)
            throws DbException {
        String sql = "select id,email from account where username=? and password=?";
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
                account.setEmail(email);
                account.setId(id);
                account.setPassword(password);
                account.setUsername(username);
            }
            return account;
        } catch (Exception e) {
            throw new DbException("getAccountByNameAndPassword failed,username="
                    + username + ",password=" + password, e);
        } finally {
            closeConnection(conn);
        }
    }


    public void addAccount(Account account) throws DbException {
        String sql = "replace into account(id,username,password,email) values(?,?,?,?)";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, account.getId());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getPassword());
            stmt.setString(4, account.getEmail());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("add account failed! account="
                    + account, e);
        } finally {
            closeConnection(conn);
        }
    }
}
