package com.zeng.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zeng on 2016-07-19.
 */
public class URIInfo {
    /**
     * 请求路径
     */
    private String webPath;
    /**
     * 请求地址
     */
    private String reqAddr;
    /**
     * 请求路径
     */
    private String reqPath;

    /**
     * 请求方法
     */
    private String reqMethod;

    /**
     * @return the webPath
     */
    public String getWebPath() {
        return webPath;
    }

    /**
     * @param webPath the webPath to set
     */
    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    /**
     * @return the reqAddr
     */
    public String getReqAddr() {
        return reqAddr;
    }

    /**
     * @param reqAddr the reqAddr to set
     */
    public void setReqAddr(String reqAddr) {
        this.reqAddr = reqAddr;
    }

    /**
     * @return the reqPath
     */
    public String getReqPath() {
        return reqPath;
    }

    /**
     * @param reqPath the reqPath to set
     */
    public void setReqPath(String reqPath) {
        this.reqPath = reqPath;
    }

    public void parseURI(String UriStr_para) {
        Pattern pattern = Pattern.compile("^\\/.*\\.ext");
        Matcher matcher = pattern.matcher(UriStr_para);
        String tmpURI = "";
        if (matcher.find()) {
            tmpURI = matcher.group();
        }
        pattern = Pattern.compile("^\\/.*\\/");
        matcher = pattern.matcher(tmpURI);
        String tmpwebpath = "";
        if (matcher.find()) {
            tmpwebpath = matcher.group();
            tmpwebpath = tmpwebpath.substring(0, tmpwebpath.length() - 1);
        }
        setWebPath(tmpwebpath);
        String tmpreqAddr = "";
        String[] tmpaddr=tmpURI.split("/");
        if(tmpaddr.length >0){
            tmpreqAddr = tmpaddr[tmpaddr.length -1];
        }
        setReqAddr(tmpreqAddr);
        String[] tmppag = tmpreqAddr.split("\\.");
        String tmpreqpath = "";
        for (int i = 0; i < tmppag.length; i++) {
            if (i < tmppag.length - 2) {
                tmpreqpath = tmpreqpath + tmppag[i] + ".";
            } else if (i == tmppag.length - 2) {
                setReqMethod(tmppag[i]);
            }
        }
        setReqPath(tmpreqpath.substring(0, tmpreqpath.length() - 1));
    }

    /**
     * @return the reqMethod
     */
    public String getReqMethod() {
        return reqMethod;
    }

    /**
     * @param reqMethod the reqMethod to set
     */
    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }
}
