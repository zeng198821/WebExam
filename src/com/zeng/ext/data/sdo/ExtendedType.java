package com.zeng.ext.data.sdo;

import commonj.sdo.Property;
import commonj.sdo.Type;
import java.util.List;

/**
 * Created by zeng on 2016-07-24.
 */
public abstract interface ExtendedType extends Type
{
    public abstract Object[] getDefaultValues();

    public abstract String getEntityName();

    public abstract int getPropertyIndex(String paramString);

    public abstract boolean isDataType();

    public abstract boolean isOpen();

    public abstract boolean isSequenced();

    public abstract boolean isAbstract();

    public abstract List getBaseTypes();

    public abstract List getDeclaredProperties();

    public abstract List getAliasNames();

    public abstract List getInstanceProperties();

    public abstract Object get(Property paramProperty);
}
