package com.zeng.dao;

/**
 * Created by zeng on 2016-07-22.
 */
public class loginImpl {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;

    /**
     * @return 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName 设置用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return 密码
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * @param passWord 设置密码
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
