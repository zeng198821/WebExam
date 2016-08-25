package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class LongType extends DataType
{
    public Class getInstanceClass()
    {
        return Long.TYPE;
    }

    public String getName()
    {
        return "Long";
    }
}
