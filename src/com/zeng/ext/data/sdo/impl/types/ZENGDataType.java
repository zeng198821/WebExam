package com.zeng.ext.data.sdo.impl.types;

import commonj.sdo.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeng on 2016-07-25.
 */
public class ZENGDataType {
    private int type;
    private String xsTypeName;
    private Type sdoType;
    private List<String> dasTypeNames = new ArrayList(5);

    public ZENGDataType(int type, String xsTypeName, Type sdoType)
    {
        this.type = type;
        this.xsTypeName = xsTypeName;
        this.sdoType = sdoType;
    }

    public ZENGDataType(String dasTypeName) {
        this.dasTypeNames.add(dasTypeName);
    }

    public ZENGDataType(String dasTypeName, String dasTypeName2)
    {
        this.dasTypeNames.add(dasTypeName);
        this.dasTypeNames.add(dasTypeName2);
    }

    public Type getSdoType()
    {
        return this.sdoType;
    }

    public void setSdoType(Type sdoType)
    {
        this.sdoType = sdoType;
    }

    public int getType()
    {
        return this.type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getXSDTypeName()
    {
        return this.xsTypeName;
    }

    public void setTypeName(String typeName)
    {
        this.xsTypeName = typeName;
    }

    public String[] getDasTypeNames()
    {
        return (String[])this.dasTypeNames.toArray(new String[this.dasTypeNames.size()]);
    }

    public void addDasTypeName(String dasTypeName) {
        this.dasTypeNames.add(dasTypeName);
    }
}
