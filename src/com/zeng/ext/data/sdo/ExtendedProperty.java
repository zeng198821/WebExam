package com.zeng.ext.data.sdo;

import com.zeng.ext.data.sdo.impl.restriction.Restriction;
import commonj.sdo.Property;
import java.util.List;

/**
 * Created by zeng on 2016-07-24.
 */
public abstract interface ExtendedProperty extends Property
{
    public abstract Restriction[] getConstraints();

    public abstract void setConstraints(Restriction[] paramArrayOfRestriction);

    public abstract List getInstanceProperties();

    public abstract List getAliasNames();

    public abstract Property getOpposite();

    public abstract boolean isNullable();

    public abstract boolean isOpenContent();

    public abstract boolean isReadOnly();
}
