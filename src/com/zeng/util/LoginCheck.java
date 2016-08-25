package com.zeng.util;

import com.zeng.validator.ExtClassValidator;
import javax.servlet.http.HttpSession;

/**
 * Created by zeng on 2016-07-19.
 */
@ExtClassValidator()
public class LoginCheck {

    public static String checklogin(loginData logindata_para){
        String tmpresult="";
        if(logindata_para.getUser().getUserName().equals("abc") && logindata_para.getUser().getPassword().equals("123456") ){
            tmpresult = "success:True";
        }else{
            tmpresult = "success:False";
        }
        return  tmpresult;
    }

    public static String checklogin2(loginData[] logindata_para){
        String tmpresult="";
        if(logindata_para[1].getUser().getUserName().equals("abc") && logindata_para[1].getUser().getPassword().equals("12345") ){
            tmpresult = "success:True";
        }else{
            tmpresult = "success:False";
        }
        return  tmpresult;
    }

    public static String checklogout(HttpSession session, loginData logindata_para,Integer userid){
        String tmpresult="";
        String empId = null;
        if(session.getAttribute("empId") != null){
            empId = session.getAttribute("empId").toString();
        }
        if( empId != null && !empId.isEmpty()){
            session.setAttribute("empId", null);
            tmpresult = "success:True";
        }else{
            tmpresult = "success:False";
        }
        return  tmpresult;
    }
}
