package com.zeng.ext.data.sdo.type;

import com.zeng.ext.data.sdo.impl.types.BooleanType;
import com.zeng.ext.data.sdo.impl.types.ByteType;
import com.zeng.ext.data.sdo.impl.types.BytesType;
import com.zeng.ext.data.sdo.impl.types.CharacterType;
import com.zeng.ext.data.sdo.impl.types.DateType;
import com.zeng.ext.data.sdo.impl.types.DecimalType;
import com.zeng.ext.data.sdo.impl.types.DoubleType;
import com.zeng.ext.data.sdo.impl.types.ZENGDataType;
import com.zeng.ext.data.sdo.impl.types.FloatType;
import com.zeng.ext.data.sdo.impl.types.IntType;
import com.zeng.ext.data.sdo.impl.types.IntegerType;
import com.zeng.ext.data.sdo.impl.types.LongType;
import com.zeng.ext.data.sdo.impl.types.ShortType;
import com.zeng.ext.data.sdo.impl.types.StringType;
import com.zeng.ext.data.sdo.impl.types.TimeType;
import commonj.sdo.Type;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by zeng on 2016-07-25.
 */
public class DataTypes
{
    public static final int booleanType = 1;
    public static final String booleanTypeName = "Boolean";
    public static final Type booleanTypeObject = new BooleanType();
    public static final int byteType = 10;
    public static final String byteTypeName = "Byte";
    public static final Type byteTypeObject = new ByteType();
    public static final int bytes = 20;
    public static final String bytesTypeName = "Bytes";
    public static final Type bytesTypeObject = new BytesType();
    public static final int characterType = 30;
    public static final String characterTypeName = "Char";
    public static final Type characterTypeObject = new CharacterType();
    public static final int dateType = 40;
    public static final String dateTypeName = "Date";
    public static final Type dateTypeObject = new DateType();
    public static final int timeType = 41;
    public static final String timeTypeName = "Time";
    public static final Type timeTypeObject = new TimeType();
    public static final int decimalType = 50;
    public static final String decimalTypeName = "Decimal";
    public static final Type decimalTypeObject = new DecimalType();
    public static final int doubleType = 60;
    public static final String doubleTypeName = "Double";
    public static final Type doubleTypeObject = new DoubleType();
    public static final int floatType = 70;
    public static final String floatTypeName = "Float";
    public static final Type floatTypeObject = new FloatType();
    public static final int intType = 80;
    public static final String intTypeName = "Int";
    public static final Type intTypeObject = new IntType();
    public static final int integerType = 90;
    public static final String integerTypeName = "Integer";
    public static final Type integerTypeObject = new IntegerType();
    public static final int longType = 100;
    public static final String longTypeName = "Long";
    public static final Type longTypeObject = new LongType();
    public static final int shortType = 110;
    public static final String shortTypeName = "Short";
    public static final Type shortTypeObject = new ShortType();
    public static final int stringType = 120;
    public static final String stringTypeName = "String";
    public static final Type stringTypeObject = new StringType();

    private static HashMap<Integer, ZENGDataType> type2ZENGDataType = new HashMap();

    private static HashMap<String, ZENGDataType> name2ZENGDataType = new HashMap();

    private static HashMap<String, String> type2ContextFunction = new HashMap();

    private static Set<String> typeNames = new LinkedHashSet();

    public static Set<String> getTypeNameList()
    {
        return typeNames;
    }

    public static Class toJavaType(int type)
    {
        return ((ZENGDataType)type2ZENGDataType.get(Integer.valueOf(type))).getSdoType().getInstanceClass();
    }

    public static Class toJavaType(String typeName)
    {
        return ((ZENGDataType)name2ZENGDataType.get(typeName)).getSdoType().getInstanceClass();
    }

    public static String name2ContextGetFunc(String typeName)
    {
        return "get".concat((String)type2ContextFunction.get(typeName));
    }

    public static String name2ContextSetFunc(String typeName)
    {
        return "set".concat((String)type2ContextFunction.get(typeName));
    }

    public static String type2Name(int type)
    {
        return ((ZENGDataType)type2ZENGDataType.get(Integer.valueOf(type))).getSdoType().getName();
    }

    public static int name2Type(String typeName)
    {
        ZENGDataType type = (ZENGDataType)name2ZENGDataType.get(typeName);
        if (type == null)
            return -1;
        return ((ZENGDataType)name2ZENGDataType.get(typeName)).getType();
    }

    public static String toXSDType(int type)
    {
        if (type2ZENGDataType.get(Integer.valueOf(type)) == null)
            return null;
        return ((ZENGDataType)type2ZENGDataType.get(Integer.valueOf(type))).getXSDTypeName();
    }

    public static String toXSDType(String typeName)
    {
        if (typeName == null)
            return null;
        if (name2ZENGDataType.get(typeName) == null)
            return null;
        return ((ZENGDataType)name2ZENGDataType.get(typeName)).getXSDTypeName();
    }

    public static boolean isJavaPrimitive(int type)
    {
        if ((type == 1) || (type == 30) || (isNumber(type)))
        {
            return true;
        }
        return false;
    }

    private static boolean isNumber(int type)
    {
        if ((type == 10) || (type == 60) || (type == 70) || (type == 80) || (type == 100) || (type == 110))
        {
            return true;
        }
        return false;
    }

    private static boolean isLongNumber(int type)
    {
        if ((type == 60) || (type == 70) || (type == 80) || (type == 100))
        {
            return true;
        }
        return false;
    }

    public static boolean isJavaPrimitive(String typeName)
    {
        int type = name2Type(typeName);
        if (type == -1)
            return false;
        return isJavaPrimitive(type);
    }

    public static boolean canConvert(int fromType, int toType)
    {
        if ((fromType == toType) || (fromType == 120) || (toType == 120))
            return true;
        switch (toType)
        {
            case 10:
                if (isNumber(fromType))
                    return true;
                return false;
            case 30:
                return false;
            case 60:
                if ((fromType == 50) || (fromType == 90) || (isNumber(fromType)))
                    return true;
                return false;
            case 70:
                if ((fromType == 50) || (fromType == 90) || (isNumber(fromType)))
                    return true;
                return false;
            case 80:
                if ((fromType == 50) || (fromType == 90) || (isNumber(fromType)))
                    return true;
                return false;
            case 100:
                if ((fromType == 50) || (fromType == 90) || (isNumber(fromType)) || (fromType == 40))
                    return true;
                return false;
            case 90:
                if ((fromType == 50) || (fromType == 90) || (fromType == 20) || (isLongNumber(fromType)))
                    return true;
                return false;
            case 50:
                if ((fromType == 50) || (fromType == 90) || (isLongNumber(fromType)))
                    return true;
                return false;
            case 110:
                if (isNumber(fromType))
                    return true;
                return false;
            case 20:
                if ((fromType == 20) || (fromType == 90) || (fromType == 120)) {
                    return true;
                }
                return false;
            case 40:
                if ((fromType == 120) || (fromType == 40) || (fromType == 100)) {
                    return true;
                }
                return false;
            case 41:
                if ((fromType == 120) || (fromType == 41)) {
                    return true;
                }
                return false;
        }
        return false;
    }

    public static boolean canConvert(String fromTypeName, String toTypeName)
    {
        int type1 = name2Type(fromTypeName);
        int type2 = name2Type(toTypeName);
        return canConvert(type1, type2);
    }

    static
    {
        ZENGDataType type1 = new ZENGDataType(1, "boolean", booleanTypeObject);

        type2ZENGDataType.put(Integer.valueOf(1), type1);
        name2ZENGDataType.put("Boolean", type1);
        type2ContextFunction.put("Boolean", "Boolean");
        ZENGDataType type2 = new ZENGDataType(10, "byte", byteTypeObject);

        type2ZENGDataType.put(Integer.valueOf(10), type2);
        name2ZENGDataType.put("Byte", type2);
        type2ContextFunction.put("Byte", "Byte");
        ZENGDataType type3 = new ZENGDataType(20, "hexBinary", bytesTypeObject);

        type2ZENGDataType.put(Integer.valueOf(20), type3);
        name2ZENGDataType.put("Bytes", type3);
        type2ContextFunction.put("Bytes", "Bytes");
        ZENGDataType type4 = new ZENGDataType(30, "string", characterTypeObject);

        type2ZENGDataType.put(Integer.valueOf(30), type4);
        name2ZENGDataType.put("Char", type4);
        type2ContextFunction.put("Char", "Char");
        ZENGDataType type5 = new ZENGDataType(40, "dateTime", dateTypeObject);

        type2ZENGDataType.put(Integer.valueOf(40), type5);
        name2ZENGDataType.put("Date", type5);
        type2ContextFunction.put("Date", "Date");
        ZENGDataType type6 = new ZENGDataType(41, "time", timeTypeObject);

        type2ZENGDataType.put(Integer.valueOf(41), type6);
        name2ZENGDataType.put("Time", type6);
        type2ContextFunction.put("Time", "Date");

        ZENGDataType type8 = new ZENGDataType(50, "decimal", decimalTypeObject);

        type2ZENGDataType.put(Integer.valueOf(50), type8);
        name2ZENGDataType.put("Decimal", type8);
        type2ContextFunction.put("Decimal", "BigDecimal");
        ZENGDataType type9 = new ZENGDataType(60, "double", doubleTypeObject);

        type2ZENGDataType.put(Integer.valueOf(60), type9);
        name2ZENGDataType.put("Double", type9);
        type2ContextFunction.put("Double", "Double");
        ZENGDataType type10 = new ZENGDataType(70, "float", floatTypeObject);

        type2ZENGDataType.put(Integer.valueOf(70), type10);
        name2ZENGDataType.put("Float", type10);
        type2ContextFunction.put("Float", "Float");
        ZENGDataType type11 = new ZENGDataType(80, "int", intTypeObject);

        type2ZENGDataType.put(Integer.valueOf(80), type11);
        name2ZENGDataType.put("Int", type11);
        type2ContextFunction.put("Int", "Int");
        ZENGDataType type12 = new ZENGDataType(90, "integer", integerTypeObject);

        type2ZENGDataType.put(Integer.valueOf(90), type12);
        name2ZENGDataType.put("Integer", type12);
        type2ContextFunction.put("Integer", "BigInteger");
        ZENGDataType type13 = new ZENGDataType(100, "long", longTypeObject);

        type2ZENGDataType.put(Integer.valueOf(100), type13);
        name2ZENGDataType.put("Long", type13);
        type2ContextFunction.put("Long", "Long");
        ZENGDataType type14 = new ZENGDataType(110, "short", shortTypeObject);

        type2ZENGDataType.put(Integer.valueOf(110), type14);
        name2ZENGDataType.put("Short", type14);
        type2ContextFunction.put("Short", "Short");
        ZENGDataType type15 = new ZENGDataType(120, "string", stringTypeObject);

        type2ZENGDataType.put(Integer.valueOf(120), type15);
        name2ZENGDataType.put("String", type15);
        type2ContextFunction.put("String", "String");

        typeNames.add("String");
        typeNames.add("Int");
        typeNames.add("Integer");
        typeNames.add("Float");
        typeNames.add("Long");
        typeNames.add("Double");
        typeNames.add("Decimal");
        typeNames.add("Boolean");
        typeNames.add("Date");
        typeNames.add("Time");
        typeNames.add("Bytes");
        typeNames.add("Byte");
        typeNames.add("Short");
        typeNames.add("Char");
    }
}
