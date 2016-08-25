package com.zeng.ext.data.sdo.impl.restriction;

/**
 * Created by zeng on 2016-07-24.
 */
public class ValidationError extends Error
{
    public Restriction constraint;
    public Object actual;

    public ValidationError(String message)
    {
        super(message);
    }

    public String toString()
    {
        if (null != this.constraint) {
            return "<" + this.constraint.getName() + "> expecting " + this.constraint.getSetting() + ", but it was " + this.actual;
        }

        return super.toString();
    }
}