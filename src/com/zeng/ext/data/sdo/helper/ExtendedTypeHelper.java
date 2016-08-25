package com.zeng.ext.data.sdo.helper;

import com.zeng.ext.data.sdo.impl.restriction.Restriction;
import com.zeng.ext.data.sdo.impl.PropertyImpl;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.impl.HelperProvider;
import java.util.Map;
import javax.xml.namespace.QName;

/**
 * Created by zeng on 2016-07-25.
 */
public abstract interface ExtendedTypeHelper extends TypeHelper
{
    public static final ExtendedTypeHelper eINSTANCE = (ExtendedTypeHelper)HelperProvider.getTypeHelper();

    public abstract long getLastUpdatedVersion();

    public abstract Type getType(String paramString);

    public abstract Restriction[] getPropertyConstraints(Property paramProperty);

    public abstract Property defineOpenContentProperty(String paramString1, String paramString2, Object paramObject, DataObject paramDataObject);

    public abstract void registerOpenContentProperty(PropertyImpl paramPropertyImpl);

    public abstract Type getType(QName paramQName);

    public abstract Map<String, Map<String, Type>> getAllTypes();
}