package com.zeng.service;

import com.zeng.dao.loginImpl;
import com.zeng.validator.ExtValidator;

/**
 * Created by zeng on 2016-07-19.
 */
public class Login {

    /**
     * 登录函数
     *
     * @param login_para 登录数据对象
     * @return True : 登录成功 | False : 登录失败
     */
    @ExtValidator()
    public static Boolean Login(loginImpl login_para) {
        Boolean tmpLoginResult = false;

        System.out.println("Ext com.zeng.org.service.Login.Login()");

        return tmpLoginResult;
    }

    /**
     * 登录函数
     *
     * @param login_para 登录数据对象
     * @return True : 登录成功 | False : 登录失败
     */
    public Boolean Logout(loginImpl login_para) {
        Boolean tmpLoginResult = false;

        System.out.println("Ext com.zeng.org.service.Login.Login()");

        return tmpLoginResult;
    }
}
