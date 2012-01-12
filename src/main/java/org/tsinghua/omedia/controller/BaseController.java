package org.tsinghua.omedia.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.tsinghua.omedia.service.AccountService;
import org.tsinghua.omedia.service.CcnService;
import org.tsinghua.omedia.service.FriendService;
import org.tsinghua.omedia.utils.AccountUtils;
import org.tsinghua.omedia.utils.CcnUtils;
import org.tsinghua.omedia.utils.MD5Utils;

public class BaseController {
    
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected AccountService accountService;
    @Autowired
    protected FriendService friendService;
    @Autowired
    protected CcnService ccnService;
    @Autowired
    protected CcnUtils ccnUtils;
    @Autowired
    protected MD5Utils md5Utils;
    @Autowired
    protected AccountUtils accountUtils;
}
