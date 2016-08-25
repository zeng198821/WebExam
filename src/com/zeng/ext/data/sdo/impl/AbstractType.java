package com.zeng.ext.data.sdo.impl;

import com.zeng.ext.data.sdo.ExtendedType;
import commonj.sdo.Type;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by zeng on 2016-07-25.
 */
public abstract class AbstractType    implements ExtendedType
{
    public boolean equals(Object obj)
    {
        if (super.equals(obj))
            return true;
        if ((obj instanceof Type)) {
            Type tr = (Type)obj;
            return (ObjectUtils.equals(getURI(), tr.getURI())) && (ObjectUtils.equals(getName(), tr.getName()));
        }

        return false;
    }

    public int hashCode()
    {
        return new HashCodeBuilder().append(getURI()).append(getName()).toHashCode();
    }
}