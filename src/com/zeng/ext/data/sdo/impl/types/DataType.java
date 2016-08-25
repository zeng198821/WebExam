package com.zeng.ext.data.sdo.impl.types;

import com.zeng.ext.data.sdo.ExtendedType;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import java.util.List;


/**
 * Created by zeng on 2016-07-25.
 */
public abstract class DataType extends AbstractType
        implements ExtendedType
{
    public boolean isDataType()
    {
        return true;
    }

    public boolean isInstance(Object object)
    {
        if ((object instanceof DataObject)) {
            DataObject obj = (DataObject)object;
            return obj.getType().equals(this);
        }
        return getInstanceClass().isAssignableFrom(object.getClass());
    }

    protected boolean isBuildinType()
    {
        return true;
    }

    public String toString()
    {
        return getName() + "<" + getInstanceClass() + ">";
    }

    public Object get(Property property)
    {
        return null;
    }

    public List getAliasNames()
    {
        return ConstantObjects.NULL_LIST;
    }

    public List getDeclaredProperties()
    {
        return ConstantObjects.NULL_LIST;
    }

    public List getInstanceProperties()
    {
        return ConstantObjects.NULL_LIST;
    }

    public List getProperties()
    {
        return ConstantObjects.NULL_LIST;
    }

    public Property getProperty(String propertyName)
    {
        return null;
    }

    public String getURI()
    {
        return "commonj.sdo";
    }

    public boolean isAbstract()
    {
        return false;
    }

    public boolean isOpen()
    {
        return false;
    }

    public boolean isSequenced()
    {
        return false;
    }

    public List getBaseTypes() {
        return ConstantObjects.NULL_LIST;
    }

    public Object[] getDefaultValues() {
        return new Object[0];
    }

    public String getEntityName() {
        return getURI() + '.' + getName();
    }

    public int getPropertyIndex(String propertyName)
    {
        return -1;
    }
}