package org.tsinghua.omedia.dao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.FriendRequest;
import org.tsinghua.omedia.model.Friends;

@ContextConfiguration(locations = { "classpath:web-context.xml" })
public class TestFriendDao extends AbstractTestNGSpringContextTests {
    private Logger logger = Logger.getLogger(TestFriendDao.class);
    
    @Autowired
    private FriendDAO friendDao;

    private Friends expectedFriends;
    private FriendRequest expectedFriendRequest;

    @BeforeMethod
    public void beforeMethod() {
        expectedFriends = new Friends();
        expectedFriends.setAccountId(1L);
        expectedFriends.setFriendId(2L);
        
        expectedFriendRequest = new FriendRequest();
        expectedFriendRequest.setAccountId(1L);
        expectedFriendRequest.setMsg("¹þ¹þ");
        expectedFriendRequest.setRequesterId(2l);
        expectedFriendRequest.setStatus(1);
        expectedFriendRequest.setTime(new Date());
    }
    
    @Test
    public void testFriends() {
        try {
            friendDao.saveFriends(expectedFriends.getAccountId(),
                    expectedFriends.getFriendId());
            Friends actualFriends = friendDao.getFriends(expectedFriends.getAccountId(),
                    expectedFriends.getFriendId());
            Assert.assertEquals(actualFriends, expectedFriends);
            friendDao.deleteFriends(expectedFriends.getAccountId(), expectedFriends.getFriendId());
            actualFriends = friendDao.getFriends(expectedFriends.getAccountId(),
                    expectedFriends.getFriendId());
            Assert.assertNull(actualFriends);
        } catch (DbException e) {
            logger.error("testFriends failed!", e);
            Assert.assertTrue(false);
        }
    }
    
    @Test
    public void testSaveFriendRequest() {
//        try {
//            friendDao.saveFriendRequest(expectedFriendRequest);
//        } catch (DbException e) {
//            logger.error("testSaveFriendRequest failed!", e);
//            Assert.assertTrue(false);
//        }
    }
}
