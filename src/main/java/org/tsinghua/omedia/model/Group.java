package org.tsinghua.omedia.model;

import java.util.Date;

/**
 * 
 * @author xuhongfeng
 *
 */
public class Group {
    private long groupId;
    private long creatorId;
    private String name;
    private Date createTime;
    private int count;
    public long getGroupId() {
        return groupId;
    }
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
    public long getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + count;
        result = prime * result
                + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + (int) (creatorId ^ (creatorId >>> 32));
        result = prime * result + (int) (groupId ^ (groupId >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Group other = (Group) obj;
        if (count != other.count)
            return false;
        if (createTime == null) {
            if (other.createTime != null)
                return false;
        } else if (!createTime.equals(other.createTime))
            return false;
        if (creatorId != other.creatorId)
            return false;
        if (groupId != other.groupId)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Group [groupId=" + groupId + ", creatorId=" + creatorId
                + ", name=" + name + ", createTime=" + createTime + ", count="
                + count + "]";
    }

}
