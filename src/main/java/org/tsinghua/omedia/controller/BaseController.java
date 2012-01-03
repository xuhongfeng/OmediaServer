package org.tsinghua.omedia.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.tsinghua.omedia.service.AccountService;
import org.tsinghua.omedia.service.CcnService;
import org.tsinghua.omedia.service.FriendService;

public class BaseController {
    
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected AccountService accountService;
    @Autowired
    protected FriendService friendService;
    @Autowired
    protected CcnService ccnService;
}
