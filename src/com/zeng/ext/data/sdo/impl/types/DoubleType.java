package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class DoubleType extends DataType{

    public Class getInstanceClass()
    {
        return Double.TYPE;
    }

    public String getName()
    {
        return "Double";
    }
}
