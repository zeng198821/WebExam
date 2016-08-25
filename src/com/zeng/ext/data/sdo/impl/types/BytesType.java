package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class BytesType extends DataType {
    public Class getInstanceClass()
    {
        return Byte.class;
    }

    public String getName()
    {
        return "Bytes";
    }
}
