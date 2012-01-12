package org.tsinghua.omedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.CcnFile;

/**
 * 
 * @author xuhongfeng
 *
 */
@Component("ccnDao")
public class CcnDaoImpl extends BaseDao implements CcnDao {

    @Override
    public List<CcnFile> listAllCcnFiles() throws DbException {
        String sql = "select accountId, ccnName, time, filePath, type from ccnFile "
                + " where order by time desc";
        List<CcnFile> ret = new ArrayList<CcnFile>();
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long accountId = rs.getLong(1);
                String ccnName = rs.getString(2);
                java.util.Date time = new java.util.Date(rs.getTimestamp(3)
                        .getTime());
                String filePath = rs.getString(4);
                int type = rs.getInt(5);
                CcnFile ccnFile = new CcnFile();
                ccnFile.setAccountId(accountId);
                ccnFile.setCcnname(ccnName);
                ccnFile.setFilePath(filePath);
                ccnFile.setTime(time);
                ccnFile.setType(type);
                ret.add(ccnFile);
            }
            return ret;
        } catch (Exception e) {
            throw new DbException("listAllCcnFiles failed");
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }
    }

    @Override
    public List<CcnFile> listCcnFiles(int type) throws DbException {
        String sql = "select accountId, ccnName, time, filePath from ccnFile "
                + " where type =? order by time desc";
        List<CcnFile> ret = new ArrayList<CcnFile>();
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long accountId = rs.getLong(1);
                String ccnName = rs.getString(2);
                java.util.Date time = new java.util.Date(rs.getTimestamp(3)
                        .getTime());
                String filePath = rs.getString(4);
                CcnFile ccnFile = new CcnFile();
                ccnFile.setAccountId(accountId);
                ccnFile.setCcnname(ccnName);
                ccnFile.setFilePath(filePath);
                ccnFile.setTime(time);
                ccnFile.setType(type);
                ret.add(ccnFile);
            }
            return ret;
        } catch (Exception e) {
            throw new DbException("listCcnFiles failed,type=" + type,
                    e);
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }
    }

    @Override
    public List<CcnFile> listPrivateCcnFiles(long accountId) throws DbException {
        String sql = "select ccnName, time, filePath, type from ccnFile "
                + " where accountId =? order by time desc";
        List<CcnFile> ret = new ArrayList<CcnFile>();
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String ccnName = rs.getString(1);
                java.util.Date time = new java.util.Date(rs.getTimestamp(2).getTime());
                String filePath = rs.getString(3);
                int type = rs.getInt(4);
                CcnFile ccnFile = new CcnFile();
                ccnFile.setAccountId(accountId);
                ccnFile.setCcnname(ccnName);
                ccnFile.setFilePath(filePath);
                ccnFile.setTime(time);
                ccnFile.setType(type);
                ret.add(ccnFile);
            }
            return ret;
        } catch (Exception e) {
            throw new DbException("listPrivateCcnFiles failed,accountId="
                    + accountId, e);
        } finally {
            if(conn != null) {
                closeConnection(conn);
            }
        }
    }

    @Override
    public List<CcnFile> listAllCcnFiles(long accountId) throws DbException {
        String sql = "select accountId, ccnName, time, filePath, type from ccnFile "
                + " where accountId =? or type=? order by time desc";
        List<CcnFile> ret = new ArrayList<CcnFile>();
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountId);
            stmt.setInt(2, CcnFile.TYPE_PUBLIC);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                long id = rs.getLong(1);
                String ccnName = rs.getString(2);
                java.util.Date time = new java.util.Date(rs.getTimestamp(3).getTime());
                String filePath = rs.getString(4);
                int type = rs.getInt(5);
                CcnFile ccnFile = new CcnFile();
                ccnFile.setAccountId(id);
                ccnFile.setCcnname(ccnName);
                ccnFile.setFilePath(filePath);
                ccnFile.setTime(time);
                ccnFile.setType(type);
                ret.add(ccnFile);
            }
            return ret;
        } catch (Exception e) {
            throw new DbException("listAllCcnFiles failed,accountId="
                    + accountId, e);
        } finally {
            if(conn != null) {
                closeConnection(conn);
            }
        }
    }


    @Override
    public void saveCcnFile(CcnFile ccnFile) throws DbException {
        String sql = "replace ccnFile(accountId,ccnName,time,filePath,type) "
                + "values(?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, ccnFile.getAccountId());
            stmt.setString(2, ccnFile.getCcnname());
            stmt.setTimestamp(3, new Timestamp(ccnFile.getTime().getTime()));
            stmt.setString(4, ccnFile.getFilePath());
            stmt.setInt(5, ccnFile.getType());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("saveCcnFile failed!,ccnFile="
                    + ccnFile, e);
        } finally {
            closeConnection(conn);
        }
    }

}
