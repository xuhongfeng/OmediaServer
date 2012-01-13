package org.tsinghua.omedia.model;

import java.util.Date;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CcnFile {
    public static final int TYPE_PRIVATE = 0;
    public static final int TYPE_PUBLIC = TYPE_PRIVATE+1;
    
    private long accountId;
    private Date time;
    private String ccnName;
    private String filePath;
    private int type;
    private long size;
    
    public CcnFile() {
        
    }
    
    public CcnFile(long accountId, Date time, String ccnName, String filePath,
            int type) {
        this.accountId = accountId;
        this.time = time;
        this.ccnName = ccnName;
        this.filePath = filePath;
        this.type = type;
    }
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getCcnname() {
        return ccnName;
    }
    public void setCcnname(String ccnName) {
        this.ccnName = ccnName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getCcnName() {
        return ccnName;
    }

    public void setCcnName(String ccnName) {
        this.ccnName = ccnName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (accountId ^ (accountId >>> 32));
        result = prime * result + ((ccnName == null) ? 0 : ccnName.hashCode());
        result = prime * result
                + ((filePath == null) ? 0 : filePath.hashCode());
        result = prime * result + (int) (size ^ (size >>> 32));
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + type;
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
        CcnFile other = (CcnFile) obj;
        if (accountId != other.accountId)
            return false;
        if (ccnName == null) {
            if (other.ccnName != null)
                return false;
        } else if (!ccnName.equals(other.ccnName))
            return false;
        if (filePath == null) {
            if (other.filePath != null)
                return false;
        } else if (!filePath.equals(other.filePath))
            return false;
        if (size != other.size)
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CcnFile [accountId=" + accountId + ", time=" + time
                + ", ccnName=" + ccnName + ", filePath=" + filePath + ", type="
                + type + ", size=" + size + "]";
    }
    
}
