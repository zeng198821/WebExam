package com.zeng.ext.data.sdo.impl.types;

/**
 * Created by zeng on 2016-07-25.
 */
public class CharacterType extends DataType{
    public Class getInstanceClass()
    {
        return Character.TYPE;
    }

    public String getName()
    {
        return "Char";
    }
}
