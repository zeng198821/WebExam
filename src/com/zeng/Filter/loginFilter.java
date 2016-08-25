package com.zeng.Filter;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zeng.util.JackSonTest;
import com.zeng.util.LoginCheck;
import com.zeng.Tool.JsonSplit;
import com.zeng.Tool.ParameterInfo;
import com.zeng.Tool.TimeUtil;
import com.zeng.Tool.URIParse;
import com.zeng.Tool.reflect;
import com.zeng.util.URIInfo;
import com.zeng.util.loginData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
/**
 * Created by zeng on 2016-07-19.
 */
public class loginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获得在下面代码中要用的request,response,session对象
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
        // 获得用户请求的Web应用路径
        String path = servletRequest.getContextPath();
        // 获得用户请求的URI
        String tmpURI = servletRequest.getRequestURI();
        URIInfo tmpInfo = URIParse.ParseURIStr(tmpURI);
        ObjectMapper mapper1 = new ObjectMapper();
        String data1 = "{\"logindata_para\":{\"user\":{\"userName\":\"abc\",\"password\":\"123456\"}},\"userid\":13545}";
        HashMap<String,String> json = JsonSplit.splitJson(data1);
        System.out.println(json.get("logindata_para"));
        ParameterInfo[] tmpparalist = reflect.getParamaters(tmpInfo.getReqPath(), tmpInfo.getReqMethod());
        if (tmpparalist == null || tmpparalist.length == 0) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        System.out.println(tmpparalist.length);
        Object[] paralist = new Object[tmpparalist.length];
        try {
            ParameterInfo tmpParameterInfo = null;
            for (int i = 0; i < tmpparalist.length; i++) {
                tmpParameterInfo = tmpparalist[i];
                if (tmpParameterInfo.getArameterClass() == HttpSession.class) {
                    paralist[i] = session;
                } else if (tmpParameterInfo.getArameterClass() == HttpServletRequest.class) {
                    paralist[i] = servletRequest;
                } else if (tmpParameterInfo.getArameterClass() == HttpServletResponse.class) {
                    paralist[i] = servletResponse;
                } else if (tmpParameterInfo.getArameterClass() == ServletRequest.class) {
                    paralist[i] = request;
                } else if (tmpParameterInfo.getArameterClass() == ServletResponse.class) {
                    paralist[i] = response;
                } else {
                    mapper1.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
                    paralist[i] = mapper1.readValue(json.get(tmpParameterInfo.getParamaterName()), tmpParameterInfo.getArameterClass());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(JackSonTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(paralist.length);
        try {
            Class<?> o = Class.forName("com.zeng.LoginCheck");
            //o.getMethods()[0].in
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(loginFilter.class.getName()).log(Level.SEVERE, null, ex);

        }

        if (tmpURI.equals("/webexam/index.jsp") || tmpURI.equals("/webexam/")) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 从session里取员工工号信息
        String empId = (String) session.getAttribute("empId");
        Logger.getLogger(loginFilter.class.getName()).log(Level.SEVERE, TimeUtil.GetDateTime() + " Start reflect.InvokeStaticMethod");
        reflect.InvokeStaticMethod("com.zeng.LoginCheck", "checklogout", paralist);
        Logger.getLogger(loginFilter.class.getName()).log(Level.SEVERE, TimeUtil.GetDateTime() + " End reflect.InvokeStaticMethod");

        /*创建类Constants.java，里面写的是无需过滤的页面
         for (int i = 0; i < Constants.NoFilter_Pages.length; i++) {

         if (path.indexOf(Constants.NoFilter_Pages[i]) > -1) {
         chain.doFilter(servletRequest, servletResponse);
         return;
         }
         }*/
        /*
         // 登陆页面无需过滤
         if(path.indexOf("/index.jsp") > -1) {
         chain.doFilter(servletRequest, servletResponse);
         return;
         }
         */

        // 判断如果没有取到员工信息,就跳转到登陆页面
        if (empId == null || "".equals(empId)) {
            // 跳转到登陆页面
            BufferedReader reader = request.getReader();
            String line;
            StringBuffer data = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            loginData s = mapper.readValue(data.toString(), loginData.class);
            System.out.println("username = " + s.getUser().getUserName() + "  passwd = " + s.getUser().getPassword());
            String tmpres = "";
            if (s.getUser().getUserName().equals("abc") && s.getUser().getPassword().equals("123456")) {
                tmpres = "success:True";
                session.setAttribute("empId", s.getUser().getUserName());
            } else {
                tmpres = "success:False";
            }
            PrintWriter pw = response.getWriter();
            pw.print(tmpres);
            response.flushBuffer();
            //servletResponse.sendRedirect(path + "/index.jsp");
        } else {
            // 已经登陆,继续此次请求
            //chain.doFilter(request, response);
            servletResponse.sendRedirect(servletRequest.getContextPath() + "/index.jsp");
        }

    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
