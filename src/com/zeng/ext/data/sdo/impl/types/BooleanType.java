package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class BooleanType extends DataType
{
    public Class getInstanceClass()
    {
        return Boolean.TYPE;
    }

    public String getName()
    {
        return "Boolean";
    }
}
