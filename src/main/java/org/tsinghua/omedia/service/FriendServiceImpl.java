package org.tsinghua.omedia.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.dao.AccountDAO;
import org.tsinghua.omedia.dao.FriendDAO;
import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.model.FriendRequest;
import org.tsinghua.omedia.model.Friends;

@Component("friendService")
public class FriendServiceImpl extends BaseService implements FriendService {
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountDAO accountDao;
    @Autowired
    private FriendDAO friendDao;

    public List<Account> searchFriends(String keyword) throws IOException {
        return accountDao.searchAccounts(keyword);
    }
    
    public List<Account> getFriends(long accountId) throws IOException {
        List<Friends> list = friendDao.getFriends(accountId);
        List<Account> friends = new ArrayList<Account>();
        for(Friends e:list) {
            Account account = accountDao.getAccount(e.getFriendId());
            if(account != null) {
                friends.add(account);
            } else {
                friendDao.deleteFriends(e.getAccountId(), e.getFriendId());
            }
        }
        return friends;
    }



    @Override
    public boolean isFriend(long id1, long id2)  throws IOException {
        return innerIsFriend(id1, id2);
    }
    
    private boolean innerIsFriend(long id1, long id2)  throws IOException {
        Friends f1 = friendDao.getFriends(id1, id2);
        Friends f2 = friendDao.getFriends(id2, id1);
        if(f1==null && f2==null) {
            return false;
        }
        if(f1==null && f2!=null) {
            friendDao.deleteFriends(id2, id1);
            accountService.updateFriendsVersion(id2);
            return false;
        }
        if(f1!=null && f2==null) {
            friendDao.deleteFriends(id1, id2);
            accountService.updateFriendsVersion(id1);
            return false;
        }
        return true;
    }

    public List<FriendRequest> getFriendRequest(long accountId)
            throws IOException {
        return friendDao.getFriendRequest(accountId);
    }
    

    public void deleteFriends(long accountId, long friendId) throws IOException {
        friendDao.deleteFriends(accountId, friendId);
        friendDao.deleteFriends(friendId, accountId);
        accountDao.updateFriendsVersion(accountId, friendId);
        accountDao.updateFriendsVersion(friendId, accountId);
    }

    public void addFriendRequest(long requesterId, long friendId, String msg)  throws IOException {
        if(innerIsFriend(requesterId, friendId)) {
            return;
        }
        List<FriendRequest> friendRequests = friendDao.getFriendRequest(requesterId, friendId);
        if(friendRequests.size() != 0) {
            //�Է��Ѿ����ҷ��͹��������
            for(FriendRequest e: friendRequests) {
                e.setStatus(FriendRequest.STATUS_ACCEPT);
                friendDao.saveFriendRequest(e);
            }
            List<FriendRequest> myRequests = friendDao.getFriendRequest(friendId, requesterId);
            for(FriendRequest e: myRequests) {
                e.setStatus(FriendRequest.STATUS_ACCEPT);
                friendDao.saveFriendRequest(e);
            }
            friendDao.saveFriends(requesterId, friendId);
            friendDao.saveFriends(friendId, requesterId);
            accountService.updateFriendRequestVersion(friendId);
            accountService.updateFriendRequestVersion(requesterId);
            accountDao.updateFriendsVersion(requesterId, System.currentTimeMillis());
            accountDao.updateFriendsVersion(friendId, System.currentTimeMillis());
            return;
        }
        FriendRequest request = new FriendRequest();
        request.setAccountId(friendId);
        request.setMsg(msg);
        request.setRequesterId(requesterId);
        request.setStatus(FriendRequest.STATUS_INIT);
        request.setTime(new Date(System.currentTimeMillis()));
        friendDao.saveFriendRequest(request);
        accountService.updateFriendRequestVersion(friendId);
    }

    @Override
    public boolean friendRequestReply(long accountId, long friendId,
            int reply) throws IOException {
        List<FriendRequest> requests = friendDao.getFriendRequest(accountId, friendId);
        if(requests.size() == 0) {
            return false;
        }
        if(reply == 1) {
            for(FriendRequest e:requests) {
                e.setStatus(FriendRequest.STATUS_ACCEPT);
                friendDao.saveFriendRequest(e);
            }
            friendDao.saveFriends(accountId, friendId);
            friendDao.saveFriends(friendId, accountId);
            accountDao.updateFriendsVersion(accountId, System.currentTimeMillis());
            accountDao.updateFriendsVersion(friendId, System.currentTimeMillis());
        } else {
            for(FriendRequest e:requests) {
                e.setStatus(FriendRequest.STATUS_REJECT);
                friendDao.saveFriendRequest(e);
            }
        }
        accountDao.updateFriendRequestVersion(accountId, System.currentTimeMillis());
        return true;
    }
}
