package org.tsinghua.omedia.service;

import java.io.IOException;
import java.util.List;

import org.tsinghua.omedia.model.Account;
import org.tsinghua.omedia.model.FriendRequest;

public interface FriendService {
    public List<Account> searchFriends(String keyword) throws IOException;
    public boolean isFriend(long id1, long id2) throws IOException;
    public List<Account> getFriends(long accountId) throws IOException;
    public void deleteFriends(long accountId, long friendId) throws IOException;

    public void addFriendRequest(long accountId, long friendId, String msg) throws IOException;
    public List<FriendRequest> getFriendRequest(long accountId) throws IOException;
    public boolean friendRequestReply(long accountId, long friendId, int reply) throws IOException;
}
