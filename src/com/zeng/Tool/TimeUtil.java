package com.zeng.Tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zeng on 2016-07-19.
 */
public class TimeUtil {
    public static String GetDateTime() {
        String tmpCurrentDateTime="";
        Date d = new Date();
        long longtime = d.getTime();
        //你获得的是上面的long型数据吧
        String time = d.toLocaleString();
        //也可以自己用SimpleDateFormat这个函数把它变成自己想要的格式,注意需要import java.text.SimpleDateFormat;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSSS");
        tmpCurrentDateTime = sdf.format(longtime);
        return  tmpCurrentDateTime;
    }
}
