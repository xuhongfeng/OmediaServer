package org.tsinghua.omedia.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.Group;
import org.tsinghua.omedia.model.GroupUser;

/**
 * 
 * @author xuhongfeng
 *
 */

@ContextConfiguration(locations = { "classpath:web-context.xml" })
@SuppressWarnings("unused")
public class TestGroupDao extends AbstractTestNGSpringContextTests {
    private Logger logger = Logger.getLogger(TestGroupDao.class);
    
    @Autowired
    private GroupDao groupDao;
    
    private Group expectedGroup;
    private GroupUser expectedGroupUser;
    
    @BeforeMethod
    public void beforeMethod() {
        expectedGroup = new Group();
        expectedGroup.setCount(0);
        expectedGroup.setCreateTime(new Date(1000L));
        expectedGroup.setCreatorId(1L);
        expectedGroup.setGroupId(2L);
        expectedGroup.setName("name");
        
        expectedGroupUser = new GroupUser();
        expectedGroupUser.setAccountId(1L);
        expectedGroupUser.setGroupId(2L);
    }
    
    @Test
    public void testSaveGroup() {
        try {
            groupDao.saveGroup(expectedGroup);
        } catch (DbException e) {
            logger.error("testSaveGroup failed", e);
            Assert.fail();
        }
    }
    
    @Test
    public void testSaveGroupUser() {
        try {
            groupDao.saveGroupUser(expectedGroupUser);
        } catch (DbException e) {
            logger.error("testSaveGroupUser failed", e);
            Assert.fail();
        }
    }
    
    @Test
    public void testListGroupUser() {
        try {
            List<GroupUser> list = groupDao.listGroupUser(expectedGroupUser.getAccountId());
            Assert.assertEquals(list.size(), 1);
            Assert.assertEquals(list.get(0), expectedGroupUser);
        } catch (DbException e) {
            logger.error("testListGroupUser failed", e);
            Assert.fail();
        }
    }
    
    @Test
    public void testGetGroup() {
        try {
            Group group = groupDao.getGroup(expectedGroup.getGroupId());
            Assert.assertEquals(group, expectedGroup);
        } catch (DbException e) {
            logger.error("testGetGroup failed", e);
            Assert.fail();
        }
    }
}
