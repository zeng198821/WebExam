package com.zeng.ext.data.sdo.impl;


import com.zeng.ext.data.sdo.ExtendedProperty;
import com.zeng.ext.data.sdo.impl.restriction.Restriction;
import com.zeng.ext.data.sdo.impl.restriction.ConstraintProperty;
import commonj.sdo.Property;
import commonj.sdo.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by zeng on 2016-07-25.
 */
public class PropertyImpl implements ExtendedProperty, Cloneable, Comparable<PropertyImpl>{
    protected List<String> aliasNames = new ArrayList();

    protected TypeReference containingType = null;

    protected Object defaultValue = null;

    protected List<Property> instanceProperties = new ArrayList();
    protected String name;
    protected Property opposite = null;

    protected TypeReference type = null;

    protected boolean containment = true;

    protected boolean many = false;

    protected boolean nullable = true;

    protected boolean openContent = false;

    protected boolean readOnly = false;

    protected boolean attribute = false;
    protected boolean element = false;

    private Restriction[] constraints = new Restriction[0];

    protected String URI = null;

    public PropertyImpl()
    {
    }

    public PropertyImpl(PropertyImpl impl)
    {
        try
        {
            BeanUtils.copyProperties(this, impl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getAliasNames()
    {
        return this.aliasNames;
    }

    public void setAliasNames(List<String> aliasNames)
    {
        this.aliasNames = Collections.unmodifiableList(aliasNames);
    }

    public Type getContainingType()
    {
        return this.containingType;
    }

    public void setContainingType(Type containingType)
    {
        this.containingType = new TypeReference(containingType);
    }

    public boolean isContainment()
    {
        return this.containment;
    }

    public void setContainment(boolean containment)
    {
        this.containment = containment;
    }

    public Object getDefault() {
        return this.defaultValue;
    }

    public void setDefault(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<Property> getInstanceProperties()
    {
        return this.instanceProperties;
    }

    public void setInstanceProperties(List<Property> instanceProperties)
    {
        this.instanceProperties = instanceProperties;
    }

    public boolean isMany()
    {
        return this.many;
    }

    public void setMany(boolean many)
    {
        this.many = many;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isNullable()
    {
        return this.nullable;
    }

    public void setNullable(boolean nullable)
    {
        this.nullable = nullable;
    }

    public boolean isOpenContent()
    {
        return this.openContent;
    }

    public void setOpenContent(boolean openContent)
    {
        this.openContent = openContent;
    }

    public Property getOpposite()
    {
        return this.opposite;
    }

    public void setOpposite(Property opposite)
    {
        this.opposite = opposite;
    }

    public boolean isReadOnly()
    {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly)
    {
        this.readOnly = readOnly;
    }

    public Type getType()
    {
        return this.type;
    }

    public void setType(Type type)
    {
        if ((type instanceof TypeReference)) {
            this.type = ((TypeReference)type);
            return;
        }
        this.type = new TypeReference(type);
    }

    public Object get(Property property)
    {
        if ((property instanceof ConstraintProperty)) {
            ConstraintProperty cp = (ConstraintProperty)property;
            return cp.getConstraint().getSetting();
        }
        return null;
    }

    public Restriction[] getConstraints()
    {
        return this.constraints;
    }

    public void setConstraints(Restriction[] cons)
    {
        this.constraints = cons;
        List constrainedProperty = new LinkedList();
        for (Restriction con : cons) {
            constrainedProperty.add(new ConstraintProperty(this, con));
        }
        this.instanceProperties = Collections.unmodifiableList(constrainedProperty);
    }

    public boolean equals(Object obj)
    {
        if (super.equals(obj))
            return true;
        if ((obj instanceof PropertyImpl)) {
            PropertyImpl p = (PropertyImpl)obj;

            return (ObjectUtils.equals(this.containingType, p.containingType)) && (ObjectUtils.equals(this.URI, p.URI)) && (ObjectUtils.equals(this.name, p.name));
        }

        return false;
    }

    public int hashCode()
    {
        return new HashCodeBuilder().append(this.URI).append(this.name).toHashCode();
    }

    public String getURI()
    {
        return this.URI;
    }

    public void setURI(String uri)
    {
        this.URI = uri;
    }

    public String toString()
    {
        return this.name + "<" + this.type + ">";
    }

    public boolean isAttribute() {
        return this.attribute;
    }

    public void setAttribute(boolean attribute) {
        this.attribute = attribute;
    }

    public boolean isElement() {
        return this.element;
    }

    public void setElement(boolean element) {
        this.element = element;
    }

    public int compareTo(PropertyImpl o)
    {
        return this.name.compareTo(o.name);
    }
}