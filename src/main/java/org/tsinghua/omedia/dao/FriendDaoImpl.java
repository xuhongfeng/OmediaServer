package org.tsinghua.omedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.FriendRequest;
import org.tsinghua.omedia.model.Friends;

/**
 * 
 * @author xuhongfeng
 *
 */
@Component("friendDao")
public class FriendDaoImpl extends BaseDao implements FriendDao {
    
    private Logger logger = Logger.getLogger(FriendDaoImpl.class);

    public List<Friends> getFriends(long accountId) throws DbException {
        String sql = "select friendId from friends"
                + " where accountId=?";
        Connection conn = null;
        List<Friends> ret = new ArrayList<Friends>();
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Friends friends = new Friends();
                friends.setAccountId(accountId);
                friends.setFriendId(rs.getLong(1));
                ret.add(friends);
            }
            return ret;
        } catch (Exception e) {
            throw new DbException("getFriends failed,accountId="
                    + accountId, e);
        } finally {
            closeConnection(conn);
        }
    }

    public Friends getFriends(long accountId, long friendId) throws DbException {
        String sql = "select accountId from friends"
                + " where accountId=? and friendId=?";
//        logger.info("friendDao.getFriends(),accountId="+accountId
//                +",friendId="+friendId);
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountId);
            stmt.setLong(2, friendId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Friends friends = new Friends();
                friends.setAccountId(accountId);
                friends.setFriendId(friendId);
                return friends;
            }
            return null;
        } catch (Exception e) {
            throw new DbException("getFriends failed,accountId="
                    + accountId + " friendId=" + friendId, e);
        } finally {
            closeConnection(conn);
        }
    }
    
    public void saveFriends(long accountId, long friendId) throws DbException {
        String sql = "replace into friends(accountId,friendId)" +
                " values(?,?)";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountId);
            stmt.setLong(2, friendId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("save friends failed!,accountId="
                    + accountId + " friendId=" + friendId, e);
        } finally {
            closeConnection(conn);
        }
    }



    public void deleteFriends(long accountId, long friendId) throws DbException {
        String sql = "delete from friends where accountId=? and friendId=?";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountId);
            stmt.setLong(2, friendId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("delete friends failed!,accountId="
                    + accountId + " friendId=" + friendId, e);
        } finally {
            closeConnection(conn);
        }
    }

    public void saveFriendRequest(FriendRequest request) throws DbException {
        String sql = "replace into friendRequest(accountId,time,requesterId,status,msg)" +
                " values(?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, request.getAccountId());
            stmt.setTimestamp(2, new Timestamp(request.getTime().getTime()));
            stmt.setLong(3, request.getRequesterId());
            stmt.setInt(4, request.getStatus());
            stmt.setString(5, request.getMsg());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("save friendRequest failed!,request="+request, e);
        } finally {
            closeConnection(conn);
        }
    }
    

    @Override
    public List<FriendRequest> getFriendRequest(long accountId)
            throws DbException {
        String sql = "select time,requesterId,msg" +
            " from friendRequest where accountId=? and status=?" +
            " order by time desc";
        List<FriendRequest> ret = new ArrayList<FriendRequest>();
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountId);
            stmt.setInt(2, FriendRequest.STATUS_INIT);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                java.util.Date time = new java.util.Date(rs.getTimestamp(1).getTime());
                long requesterId = rs.getLong(2);
                String msg = rs.getString(3);
                FriendRequest fr = new FriendRequest();
                fr.setAccountId(accountId);
                fr.setMsg(msg);
                fr.setRequesterId(requesterId);
                fr.setStatus(FriendRequest.STATUS_INIT);
                fr.setTime(time);
                ret.add(fr);
            }
            return ret;
        } catch (Exception e) {
            throw new DbException("getFriendRequest failed,accountId="
                    + accountId, e);
        } finally {
            closeConnection(conn);
        }
    
    }

    @Override
    public List<FriendRequest> getFriendRequest(long accountId, long friendId)
            throws DbException {
        String sql = "select time,msg" +
                " from friendRequest where accountId=? and requesterId=? and status=?" +
                " order by time desc";
            List<FriendRequest> ret = new ArrayList<FriendRequest>();
            Connection conn = null;
            try {
                conn = openConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setLong(1, accountId);
                stmt.setLong(2, friendId);
                stmt.setInt(3, FriendRequest.STATUS_INIT);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    java.util.Date time = new java.util.Date(rs.getTimestamp(1).getTime());
                    String msg = rs.getString(2);
                    FriendRequest fr = new FriendRequest();
                    fr.setAccountId(accountId);
                    fr.setMsg(msg);
                    fr.setRequesterId(friendId);
                    fr.setStatus(FriendRequest.STATUS_INIT);
                    fr.setTime(time);
                    ret.add(fr);
                }
                return ret;
            } catch (Exception e) {
                throw new DbException("getFriendRequest failed,accountId="
                        + accountId, e);
            } finally {
                closeConnection(conn);
            }
        
    }

    @Override
    public FriendRequest getFriendRequest(long accountId, long friendId,
            long timeMill) throws DbException {
        String sql = "select msg" +
                " from friendRequest where accountId=? and requesterId=? and time=? and status=?" +
                " order by time desc";
            Connection conn = null;
            try {
                conn = openConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setLong(1, accountId);
                stmt.setLong(2, friendId);
                stmt.setTimestamp(3, new Timestamp(timeMill));
                stmt.setInt(4, FriendRequest.STATUS_INIT);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    String msg = rs.getString(1);
                    FriendRequest fr = new FriendRequest();
                    fr.setAccountId(accountId);
                    fr.setMsg(msg);
                    fr.setRequesterId(friendId);
                    fr.setStatus(FriendRequest.STATUS_INIT);
                    fr.setTime(new java.util.Date(timeMill));
                    return fr;
                }
                return null;
            } catch (Exception e) {
                throw new DbException("getFriendRequest failed,accountId="
                        + accountId, e);
            } finally {
                closeConnection(conn);
            }
        
    }
    
    
}
