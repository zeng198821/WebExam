package com.zeng.ext.data.sdo.impl.types;

import java.math.BigInteger;

/**
 * Created by zeng on 2016-07-25.
 */
public class IntegerType extends DataType{

    public Class getInstanceClass()
    {
        return BigInteger.class;
    }

    public String getName()
    {
        return "Integer";
    }
}
