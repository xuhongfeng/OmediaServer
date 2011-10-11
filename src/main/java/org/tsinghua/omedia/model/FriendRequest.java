package org.tsinghua.omedia.model;

import java.util.Date;

public class FriendRequest {
    public static final int STATUS_INIT = 0;
    public static final int STATUS_ACCEPT = 1;
    public static final int STATUS_REJECT = 2;
    public static final int STATUS_DELETE = 3;
    
    private long accountId;
    private Date time;
    private long requesterId;
    private int status;
    private String msg;
    
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
        //¾«¶ÈÈ¡Ãë
        this.time = new Date();
        this.time.setTime((time.getTime()%1000)*1000);
        this.time = time;
    }
    public long getRequesterId() {
        return requesterId;
    }
    public void setRequesterId(long requesterId) {
        this.requesterId = requesterId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (accountId ^ (accountId >>> 32));
        result = prime * result + ((msg == null) ? 0 : msg.hashCode());
        result = prime * result + (int) (requesterId ^ (requesterId >>> 32));
        result = prime * result + status;
        result = prime * result + ((time == null) ? 0 : time.hashCode());
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
        FriendRequest other = (FriendRequest) obj;
        if (accountId != other.accountId)
            return false;
        if (msg == null) {
            if (other.msg != null)
                return false;
        } else if (!msg.equals(other.msg))
            return false;
        if (requesterId != other.requesterId)
            return false;
        if (status != other.status)
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "FriendRequest [accountId=" + accountId + ", time=" + time
                + ", requesterId=" + requesterId + ", status=" + status
                + ", msg=" + msg + "]";
    }
    
    
}
