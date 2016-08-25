package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class IntType extends DataType{
    public Class getInstanceClass()
    {
        return Integer.TYPE;
    }

    public String getName()
    {
        return "Int";
    }
}
