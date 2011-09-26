package org.tsinghua.omedia.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.service.AccountService;

@Controller
public class OmediaClientController{
    private static final Logger logger = Logger.getLogger(OmediaClientController.class);

    @Autowired
    private AccountService accountService;
    
    @RequestMapping(value="/register.do", method=RequestMethod.GET)
    @ResponseBody
    public String register(@RequestParam("username") String username
            ,@RequestParam("password") String password
            ,@RequestParam("email") String email) {
        logger.debug("register username="+username + ",password="+password + ",email=" + email);
        try {
            if(accountService.isAccountExist(username)) {
                return "{result:2}";
            }
            accountService.addAccount(username, password, email);
            return "{result:1}";
        } catch (IOException e) {
            logger.error("register failed! username="+username+",email="+email+",password="+password, e);
            return "{result:-1}";
        }
    }

    @RequestMapping(value="/login.do", method=RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam("username") String username
            ,@RequestParam("password") String password) {
        logger.debug("login username="+username + ",password="+password );
        try {
            Account account = accountService.login(username, password);
            if(account == null) {
                return "{result:2}";
            }
            return "{result:1}";
        } catch (IOException e) {
            logger.error("login failed! username="+username+",password="+password, e);
            return "{result:-1}";
        }
    }
    
}
