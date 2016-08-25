package com.zeng.ext.data.sdo.impl.restriction;

import commonj.sdo.Property;

/**
 * Created by zeng on 2016-07-24.
 */
public abstract class Restriction {
    private static boolean SUPPRESS_VALIDATION_ERROR = false;
    private Property containingProperty = null;
    private Property sdoProperty = null;

    public static void setSilentValidation(boolean silent)
    {
        SUPPRESS_VALIDATION_ERROR = silent;
    }

    public boolean validate(Object value) throws ValidationError{
        try
        {
            doValidate(value);
            return true;
        } catch (ValidationError e) {
            e.constraint = this;
            e.actual = value;

            if (SUPPRESS_VALIDATION_ERROR) {
                return false;
            }
            throw e;
        }
    }

    public abstract String getName();

    protected abstract void doValidate(Object paramObject) throws ValidationError;

    public Property getSDOInstaceProperty()
    {
        return this.sdoProperty;
    }
    public void setSDOInstaceProperty(Property property) {
        this.sdoProperty = property;
    }

    public Property getContainingProperty()
    {
        return this.containingProperty;
    }

    protected void setContainingProperty(Property containingProperty)
    {
        this.containingProperty = containingProperty;
    }

    public abstract Object getSetting();
}