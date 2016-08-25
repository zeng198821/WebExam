package com.zeng.ext.data.sdo.impl.types;

import java.util.Date;

/**
 * Created by zeng on 2016-07-25.
 */
public class DateType extends DataType{

    public Class getInstanceClass()
    {
        return Date.class;
    }

    public String getName()
    {
        return "Date";
    }
}
