package org.tsinghua.omedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.Group;
import org.tsinghua.omedia.model.GroupUser;

/**
 * 
 * @author xuhongfeng
 *
 */
@Component("groupDao")
public class GroupDaoImpl extends BaseDao implements GroupDao {

    @Override
    public void saveGroup(Group group) throws DbException {
        String sql = "replace into omediaGroup(groupId,creatorId,name,createTime,count)" +
                " values(?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, group.getGroupId());
            stmt.setLong(2, group.getCreatorId());
            stmt.setString(3, group.getName());
            stmt.setTimestamp(4, new Timestamp(group.getCreateTime().getTime()));
            stmt.setInt(5, group.getCount());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("save group failed! group="
                    + group, e);
        } finally {
            closeConnection(conn);
        }
    }

    public List<GroupUser> listGroupUser(long accountId) throws DbException {
        String sql = "select groupId from groupUser where accountId=?";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountId);
            ResultSet rs = stmt.executeQuery();
            List<GroupUser> list = new ArrayList<GroupUser>();
            while (rs.next()) {
                long groupId = rs.getLong(1);
                GroupUser gu = new GroupUser();
                gu.setAccountId(accountId);
                gu.setGroupId(groupId);
                list.add(gu);
            }
            return list;
        } catch (Exception e) {
            throw new DbException("listGroupUser failed! accountId="
                    + accountId, e);
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public void saveGroupUser(GroupUser groupUser) throws DbException {
        String sql = "replace into groupUser(accountId,groupId)"
                + " values(?,?)";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, groupUser.getAccountId());
            stmt.setLong(2, groupUser.getGroupId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DbException("saveGroupUser failed! groupUser=" + groupUser, e);
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public Group getGroup(long groupId) throws DbException {
        String sql = "select creatorId,createTime,name,count from omediaGroup where groupId=?";
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, groupId);
            ResultSet rs = stmt.executeQuery();
            Group group = null;
            if (rs.next()) {
                group = new Group();
                long creatorId = rs.getLong(1);
                Timestamp createTime = rs.getTimestamp(2);
                String name = rs.getString(3);
                int count = rs.getInt(4);
                group = new Group();
                group.setCount(count);
                group.setCreateTime(new Date(createTime.getTime()));
                group.setCreatorId(creatorId);
                group.setGroupId(groupId);
                group.setName(name);
            }
            return group;
        } catch (Exception e) {
            throw new DbException("getGroup failed! groupId="
                    + groupId, e);
        } finally {
            closeConnection(conn);
        }
    }

    
}
