package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class StringType extends DataType
{
    public Class getInstanceClass()
    {
        return String.class;
    }

    public String getName()
    {
        return "String";
    }
}
