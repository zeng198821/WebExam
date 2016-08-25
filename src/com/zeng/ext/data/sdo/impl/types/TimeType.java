package com.zeng.ext.data.sdo.impl.types;

import commonj.sdo.Type;

/**
 * Created by zeng on 2016-07-25.
 */
public class TimeType extends DataType implements Type
{
    public Class getInstanceClass()
    {
        return String.class;
    }

    public String getName()
    {
        return "Time";
    }
}
