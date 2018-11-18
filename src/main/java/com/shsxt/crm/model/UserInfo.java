package com.shsxt.crm.model;

/**
 * ID加密串 userIdStr
 * @author xlf
 * @date 2018/7/19
 */
public class UserInfo {
    private String userName;
    private String trueName;
    private String userIdStr;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }
}
