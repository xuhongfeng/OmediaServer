package org.tsinghua.omedia.model;

public class Account {
    private long accountId = 0L;
    private String username = "";
    private String password = "";
    private String email = "";
    private String address = "";
    private String phone = "";
    private String realName = "";
    private long token = 0L;
    private long version = 0L;
    private long friendsVersion = 0L;
    private long friendRequestVersion = 0L;
    
    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", username=" + username
                + ", password=" + password + ", email=" + email + ", address="
                + address + ", phone=" + phone + ", realName=" + realName
                + ", token=" + token + ", version=" + version
                + ", friendsVersion=" + friendsVersion
                + ", friendRequestVersion=" + friendRequestVersion + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (accountId ^ (accountId >>> 32));
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result
                + (int) (friendRequestVersion ^ (friendRequestVersion >>> 32));
        result = prime * result
                + (int) (friendsVersion ^ (friendsVersion >>> 32));
        result = prime * result
                + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result
                + ((realName == null) ? 0 : realName.hashCode());
        result = prime * result + (int) (token ^ (token >>> 32));
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
        result = prime * result + (int) (version ^ (version >>> 32));
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
        Account other = (Account) obj;
        if (accountId != other.accountId)
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (friendRequestVersion != other.friendRequestVersion)
            return false;
        if (friendsVersion != other.friendsVersion)
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (realName == null) {
            if (other.realName != null)
                return false;
        } else if (!realName.equals(other.realName))
            return false;
        if (token != other.token)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (version != other.version)
            return false;
        return true;
    }
    
    public long getFriendsVersion() {
        return friendsVersion;
    }
    public void setFriendsVersion(long friendsVersion) {
        this.friendsVersion = friendsVersion;
    }
    public long getFriendRequestVersion() {
        return friendRequestVersion;
    }
    public void setFriendRequestVersion(long friendRequestVersion) {
        this.friendRequestVersion = friendRequestVersion;
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
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public long getToken() {
        return token;
    }
    public void setToken(long token) {
        this.token = token;
    }
    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long id) {
        this.accountId = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}
