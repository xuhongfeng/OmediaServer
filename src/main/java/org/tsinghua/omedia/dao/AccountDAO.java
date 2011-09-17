package org.tsinghua.omedia.dao;

import org.tsinghua.omedia.model.Account;

public interface AccountDAO extends DAO {
    public static final String TABLE_NAME = "account";
    public static final String COL_ID = "id";
    public static final String COL_USER_NAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_EMAIL = "email";
    
    public Account getAccountByUsername(String username);
}
