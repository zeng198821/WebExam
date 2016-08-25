package com.zeng.ext.data.sdo;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Sequence;
import java.util.List;

/**
 * Created by zeng on 2016-07-24.
 */
public abstract interface ExtendedDataObject extends DataObject{

    public abstract List getInstanceProperties();

    public abstract Property getInstanceProperty(String paramString);

    public abstract DataObject getRootObject();

    public abstract void detach();

    public abstract Sequence getSequence();

    public abstract void setContainmentProperty(Property paramProperty);
}
