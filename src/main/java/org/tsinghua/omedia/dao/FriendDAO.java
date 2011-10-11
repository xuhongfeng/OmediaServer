package org.tsinghua.omedia.dao;

import java.util.List;

import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.FriendRequest;
import org.tsinghua.omedia.model.Friends;

public interface FriendDAO {
    public List<Friends> getFriends(long accountId) throws DbException;
    public Friends getFriends(long accountId, long friendId) throws DbException ;
    public void saveFriends(long accountId, long friendId) throws DbException ;
    public void deleteFriends(long accountId, long friendId) throws DbException ;
    
    public void saveFriendRequest(FriendRequest request) throws DbException;
    public List<FriendRequest> getFriendRequest(long accountId) throws DbException;
    public List<FriendRequest> getFriendRequest(long accountId,
            long friendId) throws DbException;
    public FriendRequest getFriendRequest(long accountId,
            long friendId, long timeMill) throws DbException;
}
