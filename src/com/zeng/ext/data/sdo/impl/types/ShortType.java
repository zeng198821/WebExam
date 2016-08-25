package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class ShortType extends DataType
{
    public Class getInstanceClass()
    {
        return Short.TYPE;
    }

    public String getName()
    {
        return "Short";
    }
}
