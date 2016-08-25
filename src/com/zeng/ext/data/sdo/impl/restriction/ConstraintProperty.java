package com.zeng.ext.data.sdo.impl.restriction;

import commonj.sdo.Property;
import com.zeng.ext.data.sdo.impl.PropertyImpl;

/**
 * Created by zeng on 2016-07-25.
 */
public class ConstraintProperty extends PropertyImpl
{
    private Restriction constraint;

    public ConstraintProperty(Property containingProperty, Restriction cons)
    {
        this.constraint = cons;
        cons.setContainingProperty(containingProperty);
        cons.setSDOInstaceProperty(this);
    }

    public Object get(Property property)
    {
        return null;
    }

    public String getName()
    {
        return this.constraint.getName();
    }

    public boolean isMany()
    {
        return false;
    }

    public boolean isNullable()
    {
        return false;
    }

    public boolean isOpenContent()
    {
        return false;
    }

    public boolean isReadOnly()
    {
        return true;
    }

    public Restriction getConstraint()
    {
        return this.constraint;
    }

    public void setConstraint(Restriction constraint)
    {
        this.constraint = constraint;
    }
}