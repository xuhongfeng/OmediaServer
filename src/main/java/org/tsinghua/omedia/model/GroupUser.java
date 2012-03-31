package org.tsinghua.omedia.model;

/**
 * 
 * @author xuhongfeng
 *
 */
public class GroupUser {
    private long groupId;
    private long accountId;
    public long getGroupId() {
        return groupId;
    }
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (accountId ^ (accountId >>> 32));
        result = prime * result + (int) (groupId ^ (groupId >>> 32));
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
        GroupUser other = (GroupUser) obj;
        if (accountId != other.accountId)
            return false;
        if (groupId != other.groupId)
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "GroupUser [groupId=" + groupId + ", accountId=" + accountId
                + "]";
    }
    
    
}
