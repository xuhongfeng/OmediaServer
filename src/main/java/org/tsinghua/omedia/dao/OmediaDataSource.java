package org.tsinghua.omedia.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.tsinghua.omedia.utils.LogWriter;

@Component("dataSource")
public class OmediaDataSource implements DataSource {
    private static final Logger logger = Logger.getLogger(OmediaDataSource.class);
    
    private PrintWriter logWritter = new PrintWriter(new LogWriter(logger), true);
    private int loginTimeout = 60;
    
    @Value("${mysql.url}")
    private String url;
    @Value("${mysql.user}")
    private String user;
    @Value("${mysql.password}")
    private String password;
    
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWritter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        logWritter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        loginTimeout = seconds;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return loginTimeout;
    }

    @SuppressWarnings("unchecked")
    public <T> T  unwrap(Class<T> iface) throws SQLException {
        Assert.notNull(iface, "Interface argument must not be null");
        if (!DataSource.class.equals(iface)) {
            throw new SQLException("DataSource of type [" + getClass().getName() +
                    "] can only be unwrapped as [javax.sql.DataSource], not as [" + iface.getName());
        }
        return (T) this;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return DataSource.class.equals(iface);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
