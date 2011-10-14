package org.tsinghua.omedia.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.model.FriendRequest;
import org.tsinghua.omedia.service.AccountService;
import org.tsinghua.omedia.service.FriendService;

@Controller
public class FriendController {
    private static final Logger logger = Logger.getLogger(FriendController.class);
    
    @Autowired
    private AccountService accountService;
    @Autowired
    private FriendService friendService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value="/searchFriends.do", method=RequestMethod.GET)
    @ResponseBody
    public String searchFriends(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token
            ,@RequestParam("keyword") String keyword) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            List<Account> accounts = friendService.searchFriends(new String(
                    keyword.getBytes("ISO8859_1"), "utf8"));
            JsonSearchFriends json = new JsonSearchFriends(accounts);
            return objectMapper.writeValueAsString(json);
        } catch (Exception e) {
            logger.error("searchFriends failed", e);
            return "{\"result\":-1}";
        }
    }

    @RequestMapping(value="/getFriendRequest.do", method=RequestMethod.GET)
    @ResponseBody
    public String getFriendRequest(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            List<FriendRequest> requests = friendService.getFriendRequest(accountId);
            List<Account> accountList = new ArrayList<Account>();
            for(FriendRequest e:requests) {
                Account friend = accountService.getAccount(e.getRequesterId());
                if(friend == null) {
                    requests.remove(e);
                } else {
                    accountList.add(friend);
                }
            }
            return objectMapper.writeValueAsString(new JsonFriendRequestArray(
                    accountList, requests, account.getFriendRequestVersion()));
        } catch (Exception e) {
            logger.error("getFriendRequest failed", e);
            return "{\"result\":-1}";
        }
    }
    

    @RequestMapping(value="/getFriends.do", method=RequestMethod.GET)
    @ResponseBody
    public String getFriends(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            List<Account> friends = friendService.getFriends(accountId);
            
            return objectMapper.writeValueAsString(new JsonFriendArray(friends, account.getFriendsVersion()));
        } catch (Exception e) {
            logger.error("getFriends failed", e);
            return "{\"result\":-1}";
        }
    }

    @RequestMapping(value="/addFriend.do", method=RequestMethod.GET)
    @ResponseBody
    public String addFriend(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token
            ,@RequestParam("friendId") long friendId
            ,@RequestParam("msg") String msg) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            if(accountId == friendId) {
                return "{\"result\":4}";
            }
            if(friendService.isFriend(accountId, friendId)) {
                return "{\"result\":5}";
            }
            friendService.addFriendRequest(accountId, friendId, new String(
                    msg.getBytes("ISO8859_1"), "utf8"));
            return "{\"result\":1}";
        } catch (Exception e) {
            logger.error("addFriend failed", e);
            return "{\"result\":-1}";
        }
    }
    

    @RequestMapping(value="/friendRequestReply.do", method=RequestMethod.GET)
    @ResponseBody
    public String friendRequestReply(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token
            ,@RequestParam("friendId") long friendId
            ,@RequestParam("reply") int reply) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            boolean r = friendService.friendRequestReply(accountId, friendId, reply);
            if(r) {
                return "{\"result\":"+reply+"}";
            } else {
                return "{\"result\":4}";
            }
        } catch (Exception e) {
            logger.error("get friendRequestReply failed", e);
            return "{\"result\":-1}";
        }
    }

    @RequestMapping(value="/deleteFriends.do", method=RequestMethod.GET)
    @ResponseBody
    public String deleteFriends(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token
            ,@RequestParam("friendId") long friendId) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            friendService.deleteFriends(accountId, friendId);
            return "{\"result\":1}";
        } catch (Exception e) {
            logger.error("deleteFriends", e);
            return "{\"result\":-1}";
        }
    }
    

    @RequestMapping(value="/socialGraph.do", method=RequestMethod.GET)
    public String socialGraph(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token, Model model) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "tokenError.html";
            }
            model.addAttribute("accountId", accountId);
            model.addAttribute("token", token);
            return "jsp/socialGraph.jsp";
        } catch (Exception e) {
            logger.error("get contacts failed", e);
            return "serverError.html";
        }
    }
    
    @RequestMapping(value="/expandSocialGraph.do", method=RequestMethod.GET)
    @ResponseBody
    public String expandSocialGraph(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token
            ,@RequestParam("friendId") long friendId) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            if(friendId != accountId) {
                if(! friendService.isFriend(accountId, friendId)) {
                    return "{\"result\":4}";
                }
            }
            List<Account> friends = friendService.getFriends(friendId);
            if(accountId == friendId) {
                friends.add(account);
            }
            //version not used
            return objectMapper.writeValueAsString(new JsonFriendArray(friends,0));
        } catch (Exception e) {
            logger.error("deleteFriends", e);
            return "{\"result\":-1}";
        }
    }
    
    @SuppressWarnings("unused")
    private static class JsonSearchFriends {
        private int result;
        private JsonFriend[] friends;
        
        public JsonSearchFriends(List<Account> accounts) throws UnsupportedEncodingException {
            result = 1;
            friends = new JsonFriend[accounts.size()];
            for(int i=0; i<friends.length; i++) {
                friends[i] = new JsonFriend(accounts.get(i));
            }
        }
        
        public int getResult() {
            return result;
        }
        public void setResult(int result) {
            this.result = result;
        }
        public JsonFriend[] getFriends() {
            return friends;
        }
        public void setFriends(JsonFriend[] friends) {
            this.friends = friends;
        }
        
    }

    @SuppressWarnings("unused")
    private static class JsonFriend {
        private long accountId;
        private String username;
        private String email;
        private String realName;
        private String address;
        private String phone;
        
        public JsonFriend(Account account) throws UnsupportedEncodingException {
            this.accountId = account.getAccountId();
            this.username = account.getUsername();
            this.email = account.getEmail();
            this.realName = new String(account.getRealName().getBytes("utf8"),"ISO8859_1");
            this.address = new String(account.getAddress().getBytes("utf8"),"ISO8859_1");
            this.phone = new String(account.getPhone().getBytes("utf8"),"ISO8859_1");
        }
        
        
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public long getAccountId() {
            return accountId;
        }
        public void setAccountId(long accountId) {
            this.accountId = accountId;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getRealName() {
            return realName;
        }
        public void setRealName(String realName) {
            this.realName = realName;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getPhone() {
            return phone;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        
    }

    @SuppressWarnings("unused")
    private static class JsonFriendRequestArray {
        private int result;
        private long version;
        private JsonFriendRequest[] requests;

        public JsonFriendRequestArray(List<Account> accountList,
                List<FriendRequest> requestList, long version) throws UnsupportedEncodingException {
            result = 1;
            this.version = version;
            requests = new JsonFriendRequest[accountList.size()];
            for (int i = 0; i < requests.length; i++) {
                requests[i] = new JsonFriendRequest(accountList.get(i),
                        requestList.get(i));
            }
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public JsonFriendRequest[] getRequests() {
            return requests;
        }

        public void setRequests(JsonFriendRequest[] requests) {
            this.requests = requests;
        }

        public long getVersion() {
            return version;
        }

        public void setVersion(long version) {
            this.version = version;
        }
    }
    
    @SuppressWarnings("unused")
    private static class JsonFriendRequest {
        private String msg;
        private Date time;
        private JsonFriend friend;
        
        public JsonFriendRequest(Account friend, FriendRequest request) throws UnsupportedEncodingException {
            msg = new String(request.getMsg().getBytes("utf8"),"ISO8859_1");
            this.friend = new JsonFriend(friend);
            time = request.getTime();
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public JsonFriend getFriend() {
            return friend;
        }

        public void setFriend(JsonFriend friend) {
            this.friend = friend;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }
        
    }
    
    @SuppressWarnings("unused")
    private static class JsonFriendArray {
        private int result;
        private long version;
        private JsonFriend[] friends;
        
        public JsonFriendArray(List<Account> accounts, long version) throws UnsupportedEncodingException {
            result = 1;
            friends = new JsonFriend[accounts.size()];
            for(int i=0; i<friends.length; i++) {
                friends[i] = new JsonFriend(accounts.get(i));
            }
            this.version = version;
        }
        
        public long getVersion() {
            return version;
        }

        public void setVersion(long version) {
            this.version = version;
        }

        public int getResult() {
            return result;
        }
        public void setResult(int result) {
            this.result = result;
        }
        public JsonFriend[] getFriends() {
            return friends;
        }
        public void setFriends(JsonFriend[] friends) {
            this.friends = friends;
        }
        
    }
}
