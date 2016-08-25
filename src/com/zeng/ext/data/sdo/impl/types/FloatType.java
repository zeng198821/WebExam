package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class FloatType extends DataType{
    public Class getInstanceClass()
    {
        return Float.TYPE;
    }

    public String getName()
    {
        return "Float";
    }
}
