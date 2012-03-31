package org.tsinghua.omedia.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.model.Group;
import org.tsinghua.omedia.service.AccountService;
import org.tsinghua.omedia.service.GroupService;

/**
 * 
 * @author xuhongfeng
 *
 */
@Controller
public class GroupController extends BaseController {
    private static final Logger logger = Logger.getLogger(GroupController.class);
    
    @Autowired
    private AccountService accountService;
    @Autowired
    private GroupService groupService;

    @RequestMapping(value="/createGroup.do", method=RequestMethod.GET)
    @ResponseBody
    public String createGroup(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token,
            @RequestParam("name") String name) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            groupService.createGroup(accountId, name);
            return "{\"result\":1}";
        } catch (Exception e) {
            logger.error("createGroup failed", e);
            return "{\"result\":-1}";
        }
    }

    @RequestMapping(value="/getGroup.do", method=RequestMethod.GET)
    @ResponseBody
    public String getGroup(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            List<Group> groupList = groupService.getGroup(accountId);
            List<JsonGroup> jsonList = new ArrayList<JsonGroup>(groupList.size());
            for(Group group:groupList) {
                String creatorName = accountService.getName(group.getCreatorId());
                JsonGroup json = new JsonGroup(group, creatorName);
                jsonList.add(json);
            }
            JsonGroupArray groupArray = new JsonGroupArray(jsonList);
            return objectMapper.writeValueAsString(groupArray);
        } catch (Exception e) {
            logger.error("getGroup.do failed", e);
            return "{\"result\":-1}";
        }
    }

    @SuppressWarnings("unused")
    private static class JsonGroup {
        private long groupId;
        private long creatorId;
        private String name;
        private long createTime;
        private int count;
        private String creatorName;
        
        public JsonGroup(Group group, String creatorName) {
            groupId = group.getGroupId();
            creatorId = group.getCreatorId();
            name = group.getName();
            createTime = group.getCreateTime().getTime();
            count = group.getCount();
            this.creatorName = creatorName;
        }
    }

    @SuppressWarnings("unused")
    private static class JsonGroupArray {
        private int result = 1;
        private JsonGroup[] groups;
        
        public JsonGroupArray(List<JsonGroup> groups) {
            this.groups = groups.toArray(new JsonGroup[0]);
        }
    }
}
