package com.zeng.ext.data.sdo.impl.types;

import java.math.BigDecimal;

/**
 * Created by zeng on 2016-07-25.
 */
public class DecimalType extends DataType{
    public Class getInstanceClass()
    {
        return BigDecimal.class;
    }

    public String getName()
    {
        return "Decimal";
    }
}
