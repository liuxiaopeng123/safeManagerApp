package com.xiaopeng.safemanager.bean;

/**
 * Created by liupeng on 2017/5/15.
 */

public class HealthDataBean {

    /**
     * userName : admin
     * userHealthNumber : 98
     * userSsy : 67.3
     * userSzy : 145.8
     * userXl : 88
     */

    private String userName;
    private String userHealthNumber;
    private String userSsy;
    private String userSzy;
    private String userXl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHealthNumber() {
        return userHealthNumber;
    }

    public void setUserHealthNumber(String userHealthNumber) {
        this.userHealthNumber = userHealthNumber;
    }

    public String getUserSsy() {
        return userSsy;
    }

    public void setUserSsy(String userSsy) {
        this.userSsy = userSsy;
    }

    public String getUserSzy() {
        return userSzy;
    }

    public void setUserSzy(String userSzy) {
        this.userSzy = userSzy;
    }

    public String getUserXl() {
        return userXl;
    }

    public void setUserXl(String userXl) {
        this.userXl = userXl;
    }
}
