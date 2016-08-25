package com.zeng.ext.data.sdo;

import com.zeng.ext.data.common.ValueConvertException;
import com.zeng.ext.data.sdo.type.DataTypes;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Created by zeng on 2016-07-24.
 */
public class DataUtil {
    private static Map<Class, String> javaToSdoMappings = new HashMap();

    private static Map<Class, IConvertor> convertors = new HashMap();
    private static Map<String, String> javaToSdoMappings2;
    private static Map<String, String> xsdToSdoMappings;
    private static final String EOS_DATETIME1_PATTERN = "yyyy-MM-dd HH:mm:ss.S";
    private static final String EOS_DATETIME2_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String EOS_TIME1_PATTERN = "HH:mm:ss.S";
    private static final String EOS_TIME2_PATTERN = "HH:mm:ss";
    private static final String EOS_YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";
    private static final String EOS_YEAR_PATTERN = "yyyy";
    private static final String EOS_YEAR_MONTH_PATTERN = "yyyy-MM";
    private static final String EOS_DAY_PATTERN = "dd";
    private static final String EOS_MONTH_PATTERN = "MM";
    private static final String EOS_MONTH_DAY_PATTERN = "MM-dd";
    private static SimpleDateFormat[] DATE_PATTERNS = { new SimpleDateFormat("yyyyMMdd"), new SimpleDateFormat("yyyy/MM/dd"), new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS z"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm z"), new SimpleDateFormat("'--'MM'-'dd z"), new SimpleDateFormat("'--'MM z"), new SimpleDateFormat("'---'dd zzzz"), new SimpleDateFormat("HH:mm:ss'.'SSS z"), new SimpleDateFormat("HH:mm:ss z"), new SimpleDateFormat("yyyy-MM-dd z"), new SimpleDateFormat("yyyy-MM z"), new SimpleDateFormat("yyyy z"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"), new SimpleDateFormat("'--'MM'-'dd"), new SimpleDateFormat("'--'MM"), new SimpleDateFormat("'---'dd"), new SimpleDateFormat("HH:mm:ss'.'SSS"), new SimpleDateFormat("HH:mm:ss"), new SimpleDateFormat("yyyy-MM"), new SimpleDateFormat("yyyy") };

    private static SimpleDateFormat[] SDO_DATE_PATTERNS = { new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'") };

    public static void clearAll()
    {
        javaToSdoMappings.clear();
        javaToSdoMappings2.clear();
        xsdToSdoMappings.clear();
        convertors.clear();
        DATE_PATTERNS = null;
        SDO_DATE_PATTERNS = null;
    }

    public static final void registerValueConvertor(Class targetClass, IConvertor conv)
    {
        if ((null != targetClass) && (null != conv))
            convertors.put(targetClass, conv);
    }

    public static final Object toType(Object parent, String propertyName, Object value)
    {
        try
        {
            Class elementType = getElementType(parent, propertyName);
            return toType(elementType, value);
        } catch (Exception e) {
        }
        throw new RuntimeException("cannot convert the value to the desiraed type");
    }

    public static Class getElementType(Object parent, String propertyName)
            throws Exception
    {
        return getElementType(parent, propertyName, Object.class);
    }

    public static Class getElementType(Object parent, String propertyName, Class defaultRet)
            throws Exception
    {
        Class parentClass = null;
        if (parent != null)
            parentClass = parent.getClass();
        Class clz = null;
        ParameterizedType genericType = null;
        try {
            Field field = getField(parentClass, propertyName);
            clz = field.getType();
            genericType = (ParameterizedType)field.getGenericType();
        }
        catch (Exception e) {
        }
        if (null == clz) {
            return defaultRet;
        }

        if (DataObject.class.isAssignableFrom(parentClass)) {
            ExtendedDataObject sdo = (ExtendedDataObject)parent;
            Class sdoClass = sdo.getInstanceProperty(propertyName).getType().getInstanceClass();

            if (null != sdoClass)
                clz = sdoClass;
        } else if (clz.isArray()) {
            clz = clz.getComponentType();
        } else if ((Collection.class.isAssignableFrom(clz)) &&
                (null != genericType)) {
            clz = (Class)genericType.getActualTypeArguments()[0];
        }

        return clz;
    }

    private static Field getField(Class parentClass, String propertyName)
    {
        try {
            Field field = null;
            field = parentClass.getDeclaredField(propertyName);
            if (null != field)
                return field;
        }
        catch (Exception e) {
        }
        parentClass = parentClass.getSuperclass();
        if (!Object.class.equals(parentClass))
            return getField(parentClass, propertyName);
        return null;
    }

    public static final Object toType(Class toClass, Object obj)
    {
        if ((null == toClass) || (toClass == Object.class))
            return obj;
        Class srcClz;
        if (obj != null) {
            srcClz = obj.getClass();
            if (toClass.isAssignableFrom(srcClz))
                return obj;
            for (Class key : convertors.keySet()) {
                if (key.isAssignableFrom(srcClz)) {
                    IConvertor conv = (IConvertor)convertors.get(key);
                    Object o = conv.convertValue(obj, toClass);
                    if (null != o)
                        return o;
                }
            }
        }
        if ((Character.class.equals(toClass)) || (Character.TYPE.equals(toClass)))
            return Character.valueOf(toChar(obj));
        if ((Boolean.class.equals(toClass)) || (Boolean.TYPE.equals(toClass)))
        {
            return Boolean.valueOf(toBoolean(obj));
        }if ((Byte.class.equals(toClass)) || (Byte.TYPE.equals(toClass)))
        return Byte.valueOf(toByte(obj));
        if ((Integer.class.equals(toClass)) || (Integer.TYPE.equals(toClass)))
        {
            return Integer.valueOf(toInt(obj));
        }if ((Double.class.equals(toClass)) || (Double.TYPE.equals(toClass)))
    {
        return Double.valueOf(toDouble(obj));
    }if ((Float.class.equals(toClass)) || (Float.TYPE.equals(toClass)))
    {
        return Float.valueOf(toFloat(obj));
    }if ((Short.class.equals(toClass)) || (Short.TYPE.equals(toClass)))
    {
        return Short.valueOf(toShort(obj));
    }if ((Long.class.equals(toClass)) || (Long.TYPE.equals(toClass)))
        return Long.valueOf(toLong(obj));
        if (BigDecimal.class.equals(toClass))
            return toBigDecimal(obj);
        if (BigInteger.class.equals(toClass))
            return toBigInteger(obj);
        if (Byte.class.equals(toClass))
        return toBytes(obj);
        if (DataObject.class.equals(toClass))
            return toDataObject(obj);
        if (Date.class.equals(toClass))
            return toDate(obj);
        if (String.class.equals(toClass)) {
            return toString(obj);
        }
        throw new IllegalArgumentException("Cannot convert type '" + (obj == null ? "null" : obj.getClass().getName()) + "' to the desired type '" + toClass.getName() + "'");
    }

    public static final Object toSDOValue(String typeName, Object obj)
    {
        String sdoType = null;
        sdoType = (String)javaToSdoMappings2.get(typeName);
        if (null == sdoType)
            sdoType = (String)xsdToSdoMappings.get(typeName);
        if ((null == sdoType) && (DataTypes.getTypeNameList().contains(typeName))) {
            sdoType = typeName;
        }

        if ("Boolean".equals(typeName))
            return Boolean.valueOf(toBoolean(obj));
        if ("Byte".equals(typeName))
            return Byte.valueOf(toByte(obj));
        if ("Bytes".equals(typeName))
            return toBytes(obj);
        if ("Character".equals(typeName))
            return Character.valueOf(toChar(obj));
        if ("Char".equals(typeName))
            return Character.valueOf(toChar(obj));
        if ("Date".equals(typeName))
            return toDate(obj);
        if ("DateTime".equals(typeName))
            try
            {
                return toDateString(obj, "yyyy-MM-dd HH:mm:ss.S");
            } catch (Exception e) {
                return toDateString(obj, "yyyy-MM-dd HH:mm:ss");
            }
        if ("Day".equals(typeName))
        {
            return toDateString(obj, "dd");
        }if ("Decimal".equals(typeName))
        return toBigDecimal(obj);
        if ("Double".equals(typeName))
            return Double.valueOf(toDouble(obj));
        if ("Duration".equals(typeName))
            return toString(obj);
        if ("Float".equals(typeName))
            return Float.valueOf(toFloat(obj));
        if ("Int".equals(typeName))
            return Integer.valueOf(toInt(obj));
        if ("Integer".equals(typeName))
            return toBigInteger(obj);
        if ("Long".equals(typeName))
            return Long.valueOf(toLong(obj));
        if ("Month".equals(typeName))
        {
            return toDateString(obj, "MM");
        }if ("MonthDay".equals(typeName))
    {
        return toDateString(obj, "MM-dd");
    }if ("Object".equals(typeName))
        return obj;
        if ("Short".equals(typeName))
            return Short.valueOf(toShort(obj));
        if ("String".equals(typeName))
            return toString(obj);
        if ("Strings".equals(typeName))
        {
            return Arrays.asList(toString(obj).split("\\s"));
        }if ("Time".equals(typeName))
        try
        {
            return toDateString(obj, "HH:mm:ss.S");
        } catch (Exception e) {
            return toDateString(obj, "HH:mm:ss");
        }
        if ("URI".equals(typeName))
            return toString(obj);
        if ("Year".equals(typeName))
        {
            return toDateString(obj, "yyyy");
        }if ("YearMonth".equals(typeName))
    {
        return toDateString(obj, "yyyy-MM");
    }if ("YearMonthDay".equals(typeName))
    {
        return toDateString(obj, "yyyy-MM-dd");
    }if ("BooleanObject".equals(typeName))
        return Boolean.valueOf(toBoolean(obj));
        if ("ByteObject".equals(typeName))
            return new Byte(toByte(obj));
        if ("CharacterObject".equals(typeName))
            return new Character(toChar(obj));
        if ("DoubleObject".equals(typeName))
            return new Double(toDouble(obj));
        if ("FloatObject".equals(typeName))
            return new Float(toFloat(obj));
        if ("IntObject".equals(typeName))
            return new Integer(toInt(obj));
        if ("LongObject".equals(typeName))
            return new Long(toLong(obj));
        if ("ShortObject".equals(typeName)) {
            return new Short(toShort(obj));
        }
        throw new IllegalArgumentException("Cannot regcognize or support the desired type '" + typeName + "'");
    }

    public static final Object toSDOValue(Type type, Object obj)
    {
        if (!type.isDataType()) {
            throw new IllegalArgumentException("the type '" + type + "' is not data type,cannot use this method!");
        }

        if (type.getBaseTypes().size() != 0) {
            return toSDOValue((ExtendedType)type.getBaseTypes().get(0), obj);
        }
        return toSDOValue(type.getName(), obj);
    }

    public static final String toDateString(Object value, String pattern)
    {
        if (value == null)
            return null;
        try {
            Date date = toDate(value, pattern);
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            String typeName = "YearMonthDay";
            if ("yyyy-MM-dd".equals(pattern))
                typeName = "YearMonthDay";
            else if ("yyyy".equals(pattern))
                typeName = "Year";
            else if ("yyyy-MM".equals(pattern))
                typeName = "YearMonth";
            else if ("dd".equals(pattern))
                typeName = "Day";
            else if ("MM".equals(pattern))
                typeName = "Month";
            else if ("MM-dd".equals(pattern))
                typeName = "MonthDay";
            else if (("HH:mm:ss.S".equals(pattern)) || ("HH:mm:ss".equals(pattern)))
                typeName = "Time";
            else if (("yyyy-MM-dd HH:mm:ss.S".equals(pattern)) || ("yyyy-MM-dd HH:mm:ss".equals(pattern))) {
                typeName = "DateTime";
            }
            throw new ValueConvertException("The value '" + value + "' cannot be converted to " + typeName);
        }
    }

    public static final String toString2(Object value)
    {
        if ((value instanceof String)) {
            return (String)value;
        }

        if (((value instanceof Number)) || ((value instanceof Boolean)) || ((value instanceof Character)))
        {
            return String.valueOf(value);
        }

        if ((value instanceof Date))
        {
            return new SimpleDateFormat("yyyy'-'MM'-'dd'T'H':'mm':'ss.S'Z'").format((Date)value);
        }

        if ((value instanceof byte[])) {
            return bytesToHexStr((byte[])value);
        }

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public static final String toString(Object value)
    {
        if ((value instanceof String)) {
            return (String)value;
        }

        if (((value instanceof Number)) || ((value instanceof Boolean))) {
            return String.valueOf(value);
        }

        if ((value instanceof Character))
        {
            if (((Character)value).equals(Character.valueOf('\000')))
            {
                return " ";
            }

            return String.valueOf(value);
        }

        if ((value instanceof Date)) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format((Date)value);
        }

        if ((value instanceof byte[])) {
            return new String((byte[])value);
        }

        if (value == null) {
            return null;
        }

        if ((value.getClass().isArray()) || (Collection.class.isAssignableFrom(value.getClass())))
        {
            throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to String");
        }

        return value.toString();
    }

    public static final List toList(Object value)
    {
        if (null == value)
            return null;
        if ((value instanceof List)) {
            return (List)value;
        }
        if (value.getClass().isArray()) {
            int size = Array.getLength(value);
            List list = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                list.add(Array.get(value, i));
            }
            return list;
        }
        if (Collection.class.isAssignableFrom(value.getClass())) {
            List ret = new ArrayList();
            ret.addAll((Collection)value);
            return ret;
        }
        String s = toString(value);
        String[] parts = s.split("\\s");
        if (parts.length < 2)
            parts = s.split(",");
        List list = Arrays.asList(parts);
        return list;
    }

    public static final boolean toBoolean(Object value)
    {
        if ((value instanceof Boolean)) {
            return ((Boolean)value).booleanValue();
        }

        if ((value instanceof String)) {
            String string = (String)value;
            if (string.equalsIgnoreCase("true"))
                return true;
            if (string.equalsIgnoreCase("false"))
                return false;
            throw new ValueConvertException("The value '" + value + "' cannot be converted to boolean");
        }

        if (value == null) {
            return false;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to boolean");
    }

    public static final BigDecimal toBigDecimal(Object value)
    {
        if ((value instanceof BigDecimal)) {
            return (BigDecimal)value;
        }

        if ((value instanceof BigInteger)) {
            return new BigDecimal((BigInteger)value);
        }

        if ((value instanceof Number)) {
            if ((value instanceof Long)) {
                return new BigDecimal(((Long)value).longValue());
            }
            return new BigDecimal(((Number)value).doubleValue());
        }

        if ((value instanceof String)) {
            String s = (String)value;
            if ("".equals(s.trim())) {
                return null;
            }
            return new BigDecimal(s);
        }

        if (value == null) {
            return null;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to BigDecimal");
    }

    public static final BigInteger toBigInteger(Object value)
    {
        if ((value instanceof BigInteger)) {
            return (BigInteger)value;
        }

        if ((value instanceof BigDecimal)) {
            return ((BigDecimal)value).toBigInteger();
        }

        if ((value instanceof Number)) {
            return BigInteger.valueOf(((Number)value).longValue());
        }

        if ((value instanceof String)) {
            return new BigInteger((String)value);
        }

        if ((value instanceof byte[])) {
            return new BigInteger((byte[])value);
        }

        if (value == null) {
            return null;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to BigInteger");
    }

    public static final int toInt(Object value)
    {
        if ((value instanceof Number)) {
            return ((Number)value).intValue();
        }

        if ((value instanceof Character)) {
            return ((Character)value).charValue();
        }

        if ((value instanceof String)) {
            String s = (String)value;
            if ("".equals(s.trim())) {
                return 0;
            }
            return Integer.parseInt(s);
        }

        if (value == null) {
            return 0;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to int");
    }

    public static final float toFloat(Object value)
    {
        if ((value instanceof Number)) {
            return ((Number)value).floatValue();
        }

        if ((value instanceof String)) {
            String s = (String)value;
            if ("".equals(s.trim())) {
                return 0.0F;
            }
            return Float.parseFloat(s);
        }

        if (value == null) {
            return 0.0F;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to float");
    }

    public static final double toDouble(Object value)
    {
        if ((value instanceof Number)) {
            return ((Number)value).doubleValue();
        }

        if ((value instanceof String)) {
            String s = (String)value;
            if ("".equals(s.trim())) {
                return 0.0D;
            }
            return Double.parseDouble(s);
        }

        if (value == null) {
            return 0.0D;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to double");
    }

    public static final char toChar(Object value)
    {
        if ((value instanceof Character)) {
            return ((Character)value).charValue();
        }

        if ((value instanceof String)) {
            if (((String)value).length() == 0)
                return '\000';
            return ((String)value).charAt(0);
        }

        if ((value instanceof Integer)) {
            return (char)((Integer)value).intValue();
        }

        if (value == null) {
            return '\000';
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to char");
    }

    public static final byte toByte(Object value)
    {
        if ((value instanceof Number)) {
            return ((Number)value).byteValue();
        }

        if ((value instanceof String)) {
            String s = (String)value;
            if ("".equals(s.trim())) {
                return 0;
            }
            return Byte.parseByte(s);
        }

        if (value == null) {
            return 0;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to byte");
    }

    public static final byte[] toBytes2(Object value)
    {
        if ((value instanceof byte[])) {
            return (byte[])value;
        }

        if ((value instanceof BigInteger)) {
            return ((BigInteger)value).toByteArray();
        }
        if ((value instanceof Integer)) {
            byte[] bytes = new byte[1];
            bytes[0] = ((Integer)value).byteValue();
            return bytes;
        }
        if ((value instanceof String)) {
            return hexStrToBytes((String)value);
        }
        if (value == null) {
            return null;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to byte array");
    }

    public static final byte[] toBytes(Object value)
    {
        if ((value instanceof byte[])) {
            return (byte[])value;
        }

        if ((value instanceof BigInteger)) {
            return ((BigInteger)value).toByteArray();
        }

        if ((value instanceof Integer)) {
            byte[] bytes = new byte[1];
            bytes[0] = ((Integer)value).byteValue();
            return bytes;
        }

        if ((value instanceof String)) {
            try {
                return ((String)value).getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                return ((String)value).getBytes();
            }
        }
        if (value == null) {
            return null;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to byte array");
    }

    public static final long toLong(Object value)
    {
        if ((value instanceof Number)) {
            return ((Number)value).longValue();
        }

        if ((value instanceof String)) {
            String s = (String)value;
            if ("".equals(s.trim())) {
                return 0L;
            }
            return Long.parseLong(s);
        }

        if ((value instanceof Date)) {
            return ((Date)value).getTime();
        }

        if (value == null) {
            return 0L;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to long");
    }

    public static final short toShort(Object value)
    {
        if ((value instanceof Number)) {
            return ((Number)value).shortValue();
        }

        if ((value instanceof String)) {
            String s = (String)value;
            if ("".equals(s.trim())) {
                return 0;
            }
            return Short.parseShort(s);
        }

        if (value == null) {
            return 0;
        }

        throw new ValueConvertException("The value of type '" + value.getClass().getName() + "' cannot be converted to short");
    }

    public static final Date toDate(Object obj)
    {
        return toDate(obj, null);
    }

    public static final Date toDate(Object obj, String pattern)
    {
        if (obj == null)
            return null;
        if ((obj instanceof Date))
            return (Date)obj;
        if ((obj instanceof Long)) {
            return new Date(((Long)obj).longValue());
        }
        if ((obj instanceof Time)) {
            return new Date(((Time)obj).getTime());
        }
        if ((obj instanceof Timestamp)) {
            return new Date(((Timestamp)obj).getTime());
        }
        if ((obj instanceof Calendar)) {
            return ((Calendar)obj).getTime();
        }
        if ((obj instanceof String)) {
            try
            {
                Date result = null;
                if (pattern != null)
                    result = parseDate((String)obj, new SimpleDateFormat[] { new SimpleDateFormat(pattern) });
                else {
                    result = parseDate((String)obj, new SimpleDateFormat[] { new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), new SimpleDateFormat("yyyy-MM-dd") });
                }

                if (result != null)
                    return result;
            } catch (Exception e) {
                throw new ValueConvertException("The value '" + obj + "' cannot be converted to Date");
            }
        }

        throw new ValueConvertException("The value '" + obj + "' cannot be converted to Date");
    }

    private static Date parseDate(String dateString, SimpleDateFormat[] format_array)
    {
        for (int i = 0; i < format_array.length; i++)
            try {
                format_array[i].setLenient(false);
                return format_array[i].parse(dateString);
            }
            catch (ParseException parseException)
            {
            }
        return null;
    }

    public static Object convertToSDOPropertyType(DataObject sdo, String propertyName, Object value)
    {
        Property p = ((ExtendedDataObject)sdo).getInstanceProperty(propertyName);
        if ((null != p) && (!p.isOpenContent()))
        {
            if (p.isMany()) {
                return toList(value);
            }

            ExtendedType type = (ExtendedType)p.getType();
            if (type != null) {
                if ((type.isDataType()) && (type.getBaseTypes().size() == 0) && (!List.class.isAssignableFrom(type.getInstanceClass()))) {
                    return toSDOValue(type.getName(), value);
                }
                Class clz = type.getInstanceClass();
                if (null != clz) {
                    try {
                        return toType(clz, value);
                    }
                    catch (Exception e) {
                        return null;
                    }
                }
            }
        }

        return value;
    }

    public static DataObject toDataObject(Object object, Type type)
    {
        if (object == null)
            return null;
        if (type == null)
            return null;
        //return (DataObject)DataObjectConvertorRegistry.lookup(object).convert(new ConvertContext(), object, null, type);
        return null;
    }

    public static final DataObject toDataObject(Object object)
    {
        if (null == object) {
            return null;
        }
        if ((object instanceof DataObject)) {
            return (DataObject)object;
        }
        DataObject ret = null;
        ret = DataFactory.INSTANCE.create(object.getClass());
        if (((ExtendedType)ret.getType()).isDataType()) {
            throw new ValueConvertException("the object class is '" + object.getClass().getName() + "' , so cannot convert the object to DataObject.");
        }

        if (null != ret) {
            try {
                Map<String, String> values = BeanUtils.describe(object);
                Set entry = values.keySet();

                for (Iterator aa = entry.iterator(); aa.hasNext(); ) { Object o = aa.next();
                    if (!"class".equals(o))
                        ret.set((String)o, values.get(o));
                }
            }
            catch (Exception e)
            {
                Map values;
                Iterator i$;
                e.printStackTrace();
            }
            return ret;
        }
        throw new ValueConvertException("No corresponding SDO Type for class '" + object.getClass() + "'");
    }

    public static final String bytesToHexStr(byte[] bcd)
    {
        StringBuffer s = new StringBuffer(bcd.length * 2);
        char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4 & 0xF)]);
            s.append(bcdLookup[(bcd[i] & 0xF)]);
        }
        return s.toString();
    }

    public static final byte[] hexStrToBytes(String s)
    {
        byte[] bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = ((byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16));
        }

        return bytes;
    }

    public static final String getSDOTypeByJavaClass(Class clz)
    {
        if (clz == null)
            return null;
        return (String)javaToSdoMappings.get(clz);
    }

    public static final String getSDOTypeByJavaType(String type)
    {
        if ((type == null) || ("".equals(type)))
            return null;
        return (String)javaToSdoMappings2.get(type);
    }

    public static final String getSDOTypeByJavaFullType(String type) {
        if ((type == null) || ("".equals(type)))
            return null;
        int idx = type.lastIndexOf(".");
        if (idx != -1) {
            type = type.substring(idx);
        }
        return (String)javaToSdoMappings2.get(type);
    }

    public static final String getSDOTypeByXSDType(String type)
    {
        return (String)xsdToSdoMappings.get(type);
    }

    public static boolean isPrimitiveObject(Class cls)
    {
        if (cls.isPrimitive())
            return true;
        if (Boolean.class.equals(cls))
            return true;
        if (Byte.class.equals(cls))
            return true;
        if (Long.class.equals(cls))
            return true;
        if (Short.class.equals(cls))
            return true;
        if (BigDecimal.class.equals(cls))
            return true;
        if (Integer.class.equals(cls))
            return true;
        if (Float.class.equals(cls))
            return true;
        if (Double.class.equals(cls))
            return true;
        if (Character.class.equals(cls))
            return true;
        if (String.class.equals(cls))
            return true;
        if (BigInteger.class.equals(cls))
            return true;
        if (Date.class.isAssignableFrom(cls))
            return true;
        return false;
    }

    public static List<Map> extractStates(Object value)
    {
        if (value == null)
            return new ArrayList(0);
        Class clazz = value.getClass();
        if (isPrimitiveObject(clazz)) {
            return new ArrayList(0);
        }
        if ((Collection.class.isAssignableFrom(clazz)) || (Map.class.isAssignableFrom(clazz)) || (clazz.isArray()))
        {
            return new ArrayList(0);
        }
        List states = new ArrayList();
        PropertyDescriptor[] descs = PropertyUtils.getPropertyDescriptors(value.getClass());

        for (PropertyDescriptor desc : descs) {
            Map map = new HashMap();
            String propertyName = desc.getName();
            if (!propertyName.equals("class"))
            {
                Object propertyValue = null;
                try {
                    propertyValue = PropertyUtils.getProperty(value, propertyName);
                } catch (Exception e) {
                }
                map.put(propertyName, propertyValue);
                states.add(map);
            }
        }
        return states;
    }

    public static final synchronized String createID()
    {
        return UUID.createID();
    }

    static
    {
        javaToSdoMappings.put(Boolean.TYPE, "Boolean");
        javaToSdoMappings.put(Byte.TYPE, "Byte");
        javaToSdoMappings.put(Byte.class, "Bytes");
        javaToSdoMappings.put(Character.TYPE, "Char");
        javaToSdoMappings.put(Date.class, "Date");
        javaToSdoMappings.put(BigDecimal.class, "Decimal");
        javaToSdoMappings.put(Double.TYPE, "Double");
        javaToSdoMappings.put(Float.TYPE, "Float");
        javaToSdoMappings.put(Integer.TYPE, "Int");
        javaToSdoMappings.put(Long.TYPE, "Long");
        javaToSdoMappings.put(Object.class, "Object");
        javaToSdoMappings.put(Short.TYPE, "Short");
        javaToSdoMappings.put(String.class, "String");

        javaToSdoMappings.put(BigInteger.class, "Integer");

        javaToSdoMappings.put(Boolean.class, "Boolean");
        javaToSdoMappings.put(Byte.class, "Byte");
        javaToSdoMappings.put(Character.class, "Char");
        javaToSdoMappings.put(Double.class, "Double");
        javaToSdoMappings.put(Float.class, "Float");
        javaToSdoMappings.put(Integer.class, "Int");
        javaToSdoMappings.put(Long.class, "Long");
        javaToSdoMappings.put(Short.class, "Short");

        javaToSdoMappings2 = new HashMap();

        javaToSdoMappings2.put("boolean", "Boolean");
        javaToSdoMappings2.put("byte", "Byte");
        javaToSdoMappings2.put("char", "Char");
        javaToSdoMappings2.put("Date", "Date");
        javaToSdoMappings2.put("BigDecimal", "Decimal");
        javaToSdoMappings2.put("double", "Double");
        javaToSdoMappings2.put("float", "Float");
        javaToSdoMappings2.put("int", "Int");
        javaToSdoMappings2.put("BigInteger", "Integer");
        javaToSdoMappings2.put("long", "Long");
        javaToSdoMappings2.put("Object", "Object");
        javaToSdoMappings2.put("short", "Short");
        javaToSdoMappings2.put("String", "String");

        javaToSdoMappings2.put("Boolean", "Boolean");
        javaToSdoMappings2.put("Byte", "Byte");
        javaToSdoMappings2.put("Character", "Char");
        javaToSdoMappings2.put("Double", "Double");
        javaToSdoMappings2.put("Float", "Float");
        javaToSdoMappings2.put("Integer", "Int");
        javaToSdoMappings2.put("Long", "Long");
        javaToSdoMappings2.put("Short", "Short");

        xsdToSdoMappings = new HashMap();

        xsdToSdoMappings.put("anySimpleType", "Object");
        xsdToSdoMappings.put("anyType", "DataObject");

        xsdToSdoMappings.put("anyURI", "String");

        xsdToSdoMappings.put("boolean", "Boolean");
        xsdToSdoMappings.put("byte", "Byte");
        xsdToSdoMappings.put("date", "YearMonthDay");

        xsdToSdoMappings.put("date", "Date");
        xsdToSdoMappings.put("dateTime", "Date");
        xsdToSdoMappings.put("decimal", "Decimal");
        xsdToSdoMappings.put("double", "Double");
        xsdToSdoMappings.put("duration", "Duration");
        xsdToSdoMappings.put("ENTITIES", "Strings");
        xsdToSdoMappings.put("ENTITY", "String");
        xsdToSdoMappings.put("float", "Float");
        xsdToSdoMappings.put("gDay", "Day");
        xsdToSdoMappings.put("gMonth", "Month");
        xsdToSdoMappings.put("gMonthDay", "MonthDay");
        xsdToSdoMappings.put("gYear", "Year");
        xsdToSdoMappings.put("gYearMonth", "YearMonth");
        xsdToSdoMappings.put("hexBinary", "Bytes");
        xsdToSdoMappings.put("base64Binary", "Bytes");
        xsdToSdoMappings.put("ID", "String");
        xsdToSdoMappings.put("IDREF", "String");
        xsdToSdoMappings.put("IDREFS", "Strings");
        xsdToSdoMappings.put("int", "Int");
        xsdToSdoMappings.put("integer", "Integer");
        xsdToSdoMappings.put("language", "String");
        xsdToSdoMappings.put("long", "Long");
        xsdToSdoMappings.put("Name", "String");
        xsdToSdoMappings.put("NCName", "String");
        xsdToSdoMappings.put("negativeInteger", "Integer");
        xsdToSdoMappings.put("NMTOKEN", "String");
        xsdToSdoMappings.put("NMTOKENS", "Strings");
        xsdToSdoMappings.put("nonNegativeInteger", "Integer");
        xsdToSdoMappings.put("nonPositiveInteger", "Integer");
        xsdToSdoMappings.put("normalizedString", "String");
        xsdToSdoMappings.put("NOTATION", "String");
        xsdToSdoMappings.put("positiveInteger", "Integer");
        xsdToSdoMappings.put("QName", "URI");
        xsdToSdoMappings.put("short", "Short");
        xsdToSdoMappings.put("string", "String");
        xsdToSdoMappings.put("time", "Time");
        xsdToSdoMappings.put("token", "String");
        xsdToSdoMappings.put("unsignedByte", "Short");
        xsdToSdoMappings.put("unsignedShort", "Int");
        xsdToSdoMappings.put("unsignedInt", "Long");
        xsdToSdoMappings.put("unsignedLong", "Integer");
    }

    private static class UUID
    {
        private static final int IP = 0;

        private static short counter = 0;

        private static final int JVM = (int)(System.currentTimeMillis() >>> 8);

        private static short getCount() {
            synchronized (UUID.class) {
                if (counter < 0)
                    counter = 0;
                return counter++;
            }
        }

        private static short getHiTime() {
            return (short)(int)(System.currentTimeMillis() >>> 32);
        }

        private static int getLoTime() {
            return (int)System.currentTimeMillis();
        }

        private static String format(int intval) {
            String formatted = Integer.toHexString(intval);
            StringBuffer buf = new StringBuffer("00000000");
            buf.replace(8 - formatted.length(), 8, formatted);
            return buf.toString();
        }

        private static String format(short shortval) {
            String formatted = Integer.toHexString(shortval);
            StringBuffer buf = new StringBuffer("0000");
            buf.replace(4 - formatted.length(), 4, formatted);
            return buf.toString();
        }

        static final synchronized String createID() {
            return 40 + format(IP) + format(JVM) + format(getHiTime()) + format(getLoTime()) + format(getCount());
        }

        static
        {
            int ipadd;
            try
            {
                int result = 0;
                for (int i = 0; i < 4; i++) {
                    result = (result << 8) - -128 + java.net.InetAddress.getLocalHost().getAddress()[i];
                }

                ipadd = result;
            } catch (Exception e) {
                ipadd = 0;
            }
        }
    }

    public static abstract interface IConvertor
    {
        public abstract Object convertValue(Object paramObject, Class paramClass);
    }
}
