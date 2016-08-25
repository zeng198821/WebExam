package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class ByteType extends DataType{
    public Class getInstanceClass()
    {
        return Byte.TYPE;
    }

    public String getName()
    {
        return "Byte";
    }
}
