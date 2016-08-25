package com.zeng;

import com.zeng.validator.ExtValidator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zeng on 2016-07-20.
 */
public class hello extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tmpContentType = req.getHeader("Content-Type");
        String acceptjson = "";
        try {
            StringBuilder sb;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    (ServletInputStream) req.getInputStream(), "utf-8"))) {
                sb = new StringBuilder("");
                String temp;
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
            }
            acceptjson = sb.toString();
            if (acceptjson.isEmpty()) {
                JSONObject jo = JSONObject.fromObject(acceptjson);
                JSONArray imgArray = jo.getJSONArray("PartsImages");
                JSONArray infArray = jo.getJSONArray("BasicInfo");
                for (Object imgArray1 : imgArray) {
                    JSONObject imgObject = JSONObject.fromObject(imgArray1);
                    System.out.println(imgObject.get("PartsImg"));
                }
                JSONObject infObject = JSONObject.fromObject(infArray.get(0));
                System.out.println(infObject.get("Parts_cate"));
                System.out.println(infObject.get("Company"));
                System.out.println(infObject.get("Parts_name"));
                System.out.println(infObject.get("TEL"));
                System.out.println(infObject.get("Parts_price"));
                System.out.println(infObject.get("Suitable"));
                System.out.println(infObject.get("UsedStyle"));
                System.out.println(infObject.get("Supplement"));
                System.out.println(jo.toString());
            }
            resp.getWriter().write(acceptjson);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write(acceptjson);
        } finally {
            resp.flushBuffer();
        }
    }

    @Override
    @RequestMapping("/aaa")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setContentType("text/html");
        //req.setCharacterEncoding("gbk");
        String tmpusername = req.getParameter("userName");
        //resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String tmprtnstr = String.format("Hello World [%s]  \n Now :[%s]", tmpusername, new Date().toLocaleString());
        out.println(tmprtnstr);
        out.flush();
        System.out.println("doGet" + tmprtnstr);

    }

}
