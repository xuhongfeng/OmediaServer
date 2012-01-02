package org.tsinghua.omedia.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class BaseDao {
    @Autowired
    protected DataSource dataSource;
    
    protected Connection openConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    
    protected void closeConnection(Connection conn) {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
