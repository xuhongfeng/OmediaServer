package org.tsinghua.omedia.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.model.Config;
import org.tsinghua.omedia.utils.ConfigManager;

@Controller
public class CommonsController extends BaseController {
    private static final Logger logger = Logger.getLogger(CommonsController.class);

    @RequestMapping(value="/checkDataVersion.do", method=RequestMethod.GET)
    @ResponseBody
    public String checkDataVersion(@RequestParam("accountId") long accountId,
            @RequestParam("token") long token,
            @RequestParam("accountVersion") long accountVersion,
            @RequestParam("friendRequestVersion") long friendRequestVersion,
            @RequestParam("friendsVersion") long friendsVersion,
            @RequestParam("ccnFileVersion") long ccnFileVersion,
            @RequestParam("configVersion") long configVersion) {
        Account account;
        try {
            account = accountService.getAccount(accountId);
            if(account==null || account.getToken()!=token) {
                return "{\"result\":3}";
            }
            JsonCheckDataVersion json = new JsonCheckDataVersion();
            json.setResult(1);
            if(account.getVersion() == accountVersion) {
                json.setAccount(0);
            } else {
                json.setAccount(1);
            }
            if(account.getFriendRequestVersion() == friendRequestVersion) {
                json.setFriendRequest(0);
            } else {
                json.setFriendRequest(1);
            }
            if(account.getFriendsVersion() == friendsVersion) {
                json.setFriends(0);
            } else {
                json.setFriends(1);
            }
            if(account.getCcnFileVersion() == ccnFileVersion) {
                json.setCcnFile(0);
            } else {
                json.setCcnFile(1);
            }
            if(ConfigManager.getConfigVersion() == configVersion) {
                json.setConfig(0);
            } else {
                json.setConfig(1);
            }
            return objectMapper.writeValueAsString(json);
        } catch (Exception e) {
            logger.error("check data version failed", e);
            return "{\"result\":-1}";
        }
    }

    @RequestMapping(value="/getConfig.do", method=RequestMethod.GET)
    @ResponseBody
    public String getConfig() {
        long version = ConfigManager.getConfigVersion();
        Config config = ConfigManager.getConfig();
        JsonConfig json = new JsonConfig();
        json.setConfig(config);
        json.setVersion(version);
        try {
            return objectMapper.writeValueAsString(json);
        } catch (Exception e) {
            logger.error("", e);
            return "{\"result\":-1}";
        }
    }

    @SuppressWarnings("unused")
    private static class JsonCheckDataVersion {
        private int account;//==1 means accountData need to update
        private int friendRequest;
        private int friends;
        private int config;
        private int ccnFile;
        private int result;

        public int getFriends() {
            return friends;
        }


        public void setFriends(int friends) {
            this.friends = friends;
        }

        public int getCcnFile() {
            return ccnFile;
        }


        public void setCcnFile(int ccnFile) {
            this.ccnFile = ccnFile;
        }


        public int getResult() {
            return result;
        }
        

        public int getFriendRequest() {
            return friendRequest;
        }


        public void setFriendRequest(int friendRequest) {
            this.friendRequest = friendRequest;
        }

        public int getConfig() {
            return config;
        }


        public void setConfig(int config) {
            this.config = config;
        }


        public void setResult(int result) {
            this.result = result;
        }

        public int getAccount() {
            return account;
        }

        public void setAccount(int account) {
            this.account = account;
        }
    }

    @SuppressWarnings("unused")
    private static class JsonConfig {
        private Config config;
        private long version;
        private int result = 1;
        
        public Config getConfig() {
            return config;
        }
        public void setConfig(Config config) {
            this.config = config;
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
        
    }
}
