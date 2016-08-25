package com.zeng.Tool;

import com.zeng.util.URIInfo;

/**
 * Created by zeng on 2016-07-19.
 */
public class URIParse {
    public static URIInfo ParseURIStr(String URIInfo_para) {
        URIInfo tmpInfo = new URIInfo();
        tmpInfo.parseURI(URIInfo_para);
        return tmpInfo;
    }
}
