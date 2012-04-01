package org.tsinghua.omedia.model;

public class Account {
    private long accountId = 0L;
    private String username = "";
    private String password = "";
    private String email = "";
    private String address = "";
    private String phone = "";
    private String realName = "";
    private long token = -1L;
    private long version = -1L;
    private long friendsVersion = -1L;
    private long friendRequestVersion = -1L;
    private long groupVersion = -1L;
    
    public long getGroupVersion() {
        return groupVersion;
    }
    public void setGroupVersion(long groupVersion) {
        this.groupVersion = groupVersion;
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
    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", username=" + username
                + ", password=" + password + ", email=" + email + ", address="
                + address + ", phone=" + phone + ", realName=" + realName
                + ", token=" + token + ", version=" + version
                + ", friendsVersion=" + friendsVersion
                + ", friendRequestVersion=" + friendRequestVersion
                + ", groupVersion=" + groupVersion + "]";
    }
    
    
}
