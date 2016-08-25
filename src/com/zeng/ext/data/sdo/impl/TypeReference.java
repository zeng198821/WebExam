package com.zeng.ext.data.sdo.impl;

import com.zeng.ext.data.sdo.ExtendedType;
import com.zeng.ext.data.sdo.helper.ExtendedTypeHelper;
import commonj.sdo.Property;
import commonj.sdo.Type;
import java.util.List;

/**
 * Created by zeng on 2016-07-25.
 */
public class TypeReference extends AbstractType
{
    public long lastUpdatedVersion = 0L;
    private static final boolean isDebug = Boolean.parseBoolean(System.getProperty("EOS_DEBUG"));
    private String uri;
    private String name;
    private ExtendedType cachedType;

    public TypeReference(String uri, String name)
    {
        this.uri = uri;
        this.name = name;
    }

    public TypeReference(Type type)
    {
        this(type.getURI(), type.getName());
        this.cachedType = ((ExtendedType)type);
    }

    public ExtendedType getActualType() {
        if (this.lastUpdatedVersion < ExtendedTypeHelper.eINSTANCE.getLastUpdatedVersion())
        {
            ExtendedType type = (ExtendedType)ExtendedTypeHelper.eINSTANCE.getType(this.uri, this.name);

            if (null != type)
                this.cachedType = type;
            this.lastUpdatedVersion = ExtendedTypeHelper.eINSTANCE.getLastUpdatedVersion();
        }

        if (null == this.cachedType) {
            ExtendedType type = (ExtendedType)ExtendedTypeHelper.eINSTANCE.getType(this.uri, this.name);

            if (null != type)
                this.cachedType = type;
        }
        return this.cachedType;
    }

    public Object get(Property property)
    {
        return getActualType().get(property);
    }

    public List getAliasNames()
    {
        return getActualType().getAliasNames();
    }

    public List getBaseTypes()
    {
        return getActualType().getBaseTypes();
    }

    public List getDeclaredProperties()
    {
        return getActualType().getDeclaredProperties();
    }

    public Class getInstanceClass()
    {
        return getActualType().getInstanceClass();
    }

    public List getInstanceProperties()
    {
        return getActualType().getInstanceProperties();
    }

    public String getName()
    {
        Type type = getActualType();
        return null == type ? this.name : type.getName();
    }

    public List getProperties()
    {
        return getActualType().getProperties();
    }

    public Property getProperty(String propertyName)
    {
        return getActualType().getProperty(propertyName);
    }

    public String getURI()
    {
        Type type = getActualType();
        return null == type ? this.uri : type.getURI();
    }

    public boolean isAbstract()
    {
        return getActualType().isAbstract();
    }

    public boolean isDataType()
    {
        return getActualType().isDataType();
    }

    public boolean isInstance(Object object)
    {
        return getActualType().isInstance(object);
    }

    public boolean isOpen()
    {
        return getActualType().isOpen();
    }

    public boolean isSequenced()
    {
        return getActualType().isSequenced();
    }

    public Object[] getDefaultValues()
    {
        return getActualType().getDefaultValues();
    }

    public String getEntityName()
    {
        return getActualType().getEntityName();
    }

    public String toString()
    {
        return getActualType().toString();
    }

    public int getPropertyIndex(String propertyName)
    {
        return getActualType().getPropertyIndex(propertyName);
    }
}