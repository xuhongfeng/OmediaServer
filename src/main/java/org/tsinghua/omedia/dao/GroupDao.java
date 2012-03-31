package org.tsinghua.omedia.dao;

import java.util.List;

import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.Group;
import org.tsinghua.omedia.model.GroupUser;

/**
 * 
 * @author xuhongfeng
 *
 */
public interface GroupDao {
    public void saveGroup(Group group) throws DbException;
    public Group getGroup(long groupId) throws DbException;
    public List<GroupUser> listGroupUser(long accountId) throws DbException;
    public void saveGroupUser(GroupUser groupUser) throws DbException;
}
