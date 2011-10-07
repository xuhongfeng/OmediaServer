package org.tsinghua.omedia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.service.AccountService;
import org.tsinghua.omedia.utils.AccountUtil;

@ContextConfiguration(locations = { "classpath:web-context.xml" })
public class TestAccountService extends AbstractTestNGSpringContextTests {
    private final static Logger logger = Logger
            .getLogger(TestAccountService.class);

    private AccountDAO accountDao;
    private Account account1;
    private List<Account> datas;
    
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountUtil accountUtil;
    
    @BeforeMethod
    public void beforeClass() throws IOException {
        datas = new ArrayList<Account>();
        account1 = new Account();
        account1.setAccountId(1L);
        account1.setAddress("1.address");
        account1.setEmail("1.email");
        account1.setPassword(accountUtil.encryptPassword("1.password"));
        account1.setPhone("1.phone");
        account1.setRealName("1.realName");
        account1.setToken(1L);
        account1.setUsername("1.username");
        account1.setVersion(1L);
        datas.add(account1);
        accountDao = new MockAccountDao(datas);
        accountService.setAccountDao(accountDao);
    }
    
    @Test
    public void getAccountTest() {
        try {
            Account account = accountService.getAccount(account1.getAccountId());
            Assert.assertEquals(account1, account);
        } catch (IOException e) {
            logger.error("test get account failed!", e);
            Assert.assertTrue(false);
        }
    }
    
    @Test
    public void isUserNameExistsTest() {
        try {
            Assert.assertTrue(accountService.isUsernameExist(account1.getUsername()));
        } catch (IOException e) {
            logger.error("test isUserNameExists failed!", e);
            Assert.assertTrue(false);
        }
    }
    
    @Test
    public void createAccountTest() {
        String username = "username";
        String password = "password";
        String email = "email";
        Account actualAccount;
        try {
            actualAccount = accountService.createAccount(username, password, email);
            Account expectedAccount = new Account();
            expectedAccount.setAccountId(actualAccount.getAccountId());
            expectedAccount.setVersion(actualAccount.getVersion());
            expectedAccount.setUsername(username);
            expectedAccount.setPassword(accountUtil.encryptPassword(password));
            expectedAccount.setEmail(email);
            Assert.assertEquals(expectedAccount, actualAccount);
        } catch (IOException e) {
            logger.error("test createAccount failed!", e);
            Assert.assertTrue(false);
        }
    }
    
    @Test
    public void updateAccount() {
        try {
            account1.setAddress("newAddress");
            accountService.updateAccount(account1);
            Account actualAccount = accountService.getAccount(account1.getAccountId());
            Assert.assertEquals(account1, actualAccount);
        } catch (IOException e) {
            logger.error("test updateAccount failed!", e);
            Assert.assertTrue(false);
        }
    }
    
    @Test
    public void loginTest() {
        try {
            Account account = accountService.login(account1.getUsername(),
                    account1.getAccountId()+".password");
            Assert.assertEquals(account1, account);
        } catch (IOException e) {
            logger.error("test login failed!", e);
            Assert.assertTrue(false);
        }
        
    }
    @Test
    public void updateTokenTest() {
        try {
            long token = account1.getToken();
            accountService.updateToken(account1.getAccountId());
            Assert.assertNotSame(account1.getToken(), token);
        } catch (IOException e) {
            logger.error("test update token failed!", e);
            Assert.assertTrue(false);
        }
    }
    
    @Test
    public void checkAccountTokenTest() {
        try {
            Assert.assertTrue(accountService.checkToken(
                    account1.getAccountId(), account1.getToken()));
        } catch (IOException e) {
            logger.error("test check token failed!", e);
            Assert.assertTrue(false);
        }
    }
}
