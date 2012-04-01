package org.tsinghua.omedia.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.dao.AccountDao;
import org.tsinghua.omedia.dao.GroupDao;
import org.tsinghua.omedia.model.Group;
import org.tsinghua.omedia.model.GroupUser;
import org.tsinghua.omedia.utils.IDUtils;

/**
 * 
 * @author xuhongfeng
 *
 */
@Component("groupService")
public class GroupServiceImpl extends BaseService implements GroupService {
    @Autowired
    private IDUtils idUtils;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private AccountDao accountDao;
    
    @Override
    public void createGroup(long accountId, String name) throws IOException {
        Group group = new Group();
        group.setCount(1);
        group.setCreateTime(new Date());
        group.setCreatorId(accountId);
        group.setGroupId(idUtils.genId());
        group.setName(name);
        groupDao.saveGroup(group);
        
        GroupUser gu = new GroupUser();
        gu.setAccountId(accountId);
        gu.setGroupId(group.getGroupId());
        groupDao.saveGroupUser(gu);
        
        accountDao.updateGroupVersion(accountId, System.currentTimeMillis());
    }

    @Override
    public List<Group> getGroup(long accountId) throws IOException {
        List<GroupUser> guList = groupDao.listGroupUser(accountId);
        List<Group> groupList = new ArrayList<Group>(guList.size());
        for(GroupUser gu:guList) {
            Group group = groupDao.getGroup(gu.getGroupId());
            groupList.add(group);
        }
        return groupList;
    }

}
