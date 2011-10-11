package org.tsinghua.omedia.model;


public class Friends {
    private long accountId;
    private long friendId;
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    public long getFriendId() {
        return friendId;
    }
    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (accountId ^ (accountId >>> 32));
        result = prime * result + (int) (friendId ^ (friendId >>> 32));
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Friends other = (Friends) obj;
        if (accountId != other.accountId)
            return false;
        if (friendId != other.friendId)
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Friends [accountId=" + accountId + ", friendId=" + friendId
                + "]";
    }
    
}
