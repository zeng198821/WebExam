package com.zeng.Tool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.beanutils.MethodUtils;

/**
 * Created by zeng on 2016-07-19.
 */
public class reflect {
    public static void getMethods(String[] args) {

    }


    /**
     * 获取参数列表
     * @param className_para 类名
     * @param methondName_para 函数名
     * @return  函数的参数列表
     */
    public static ParameterInfo[] getParamaters(String className_para, String methondName_para) {
        ParameterInfo[] tmprtn = null;

        Class<?> clazz = null;
        int tmpParameterListLength=0;
        try {
            clazz = Class.forName(className_para);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(reflect.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(clazz == null){
            return tmprtn;
        }
        Method[] methods = clazz.getMethods();
        Method m =null;
        for (Method method : methods) {
            if(methondName_para.equals(method.getName())){
                m = method;
                break;
            }
        }
        if(m==null){
            return tmprtn;
        }
        Class[] ParameterList =  m.getParameterTypes();

        if(ParameterList == null ||  ParameterList.length == 0){
            return tmprtn;
        }
        tmprtn = new  ParameterInfo[ParameterList.length];
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.getOrNull(clazz.getName());
            if(cc == null){
                pool.insertClassPath(new ClassClassPath(clazz));
                cc = pool.get(clazz.getName());
            }
            CtMethod cm = cc.getDeclaredMethod(methondName_para);
            // 使用javaassist的反射方法获取方法的参数名
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
                return tmprtn;
            }
            tmpParameterListLength = cm.getParameterTypes().length;
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < tmpParameterListLength; i++) {
                ParameterInfo tmpinfo = new ParameterInfo();
                tmpinfo.setIndex(i);
                tmpinfo.setParamaterName(attr.variableName(i + pos));
                tmpinfo.setArameterClass(ParameterList[i]);
                tmprtn[i] =  tmpinfo;
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return  tmprtn;
    }

    /**
     *  回调服务函数
     * @param className_para 类名
     * @param methondName_para 方法名
     * @param parameterList 参数列表
     * @return  执行结果
     */
    public static Object InvokeStaticMethod(String className_para, String methondName_para ,Object[] parameterList) {
        Object rtn=null;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className_para);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(reflect.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(clazz == null){
            return rtn;
        }
        try {
            rtn = MethodUtils.invokeStaticMethod(clazz, methondName_para, parameterList);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(reflect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(reflect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(reflect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rtn;
    }
}
