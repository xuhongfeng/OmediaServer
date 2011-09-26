package org.tsinghua.omedia.dao;

import java.io.IOException;
import java.sql.Connection;

public interface DAO {
    Connection openConnection() throws IOException ;
    void closeConnection(Connection conn);
}
