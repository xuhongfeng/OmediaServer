package org.tsinghua.omedia.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class BaseDao {
    private static final Logger logger = Logger.getLogger(BaseDao.class);
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
    public Connection openConnection() throws IOException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new IOException("open mysql connection failed!\nurl="+url
                    +",username="+user+",password="+password, e);
        }
    }
    
    public void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.warn("close mysql connection failed!", e);
            }
        }
    }
}
