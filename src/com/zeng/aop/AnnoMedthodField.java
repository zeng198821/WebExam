package com.zeng.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javassist.CtMethod.ConstParameter.string;

/**
 * Created by zeng on 2016-07-19.
 */
public class AnnoMedthodField {
    /**
     * 类名称
     */
    private String className;

    /**
     * 类信息
     */
    private Class classInfo;

    /**
     * 函数名称
     */
    private  String medthodName;

    /**
     * 函数信息
     */
    private  Method medthodInfo;

    /**
     *  注解信息列表
     */
    private HashMap<String, AnnoField> classFieldList;
    /**
     * 构造函数自定义注解信息对象
     * @param className_para 类名称
     * @param methodName_para 函数名称
     * @param methodInfo_para  函数对象
     */
    public AnnoMedthodField(String className_para,String methodName_para,Method methodInfo_para){
        if(className_para != null && !className_para.isEmpty() && methodName_para != null && !methodName_para.isEmpty()
                && methodInfo_para != null){
            setClassName(className_para);
            setMedthodName(methodName_para);
            setMedthodInfo(methodInfo_para);
            initMethodsAnnoFieldValues();
            System.out.println("");
        }
    }
    /**
     * 获取类名称
     * @return 类名称
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置类名称
     * @param className 类名称
     */
    public void setClassName(String className) {
        Class tmpClass = null;
        try {
            tmpClass = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AnnoAbsField.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(tmpClass != null){
            this.className = className;
            setClassInfo(tmpClass);
            classFieldList = new HashMap<String, AnnoField>();
        }

    }

    /**
     * 获取类信息
     * @return 类信息
     */
    public Class getClassInfo() {
        return classInfo;
    }

    /**
     *  设置类信息
     * @param classInfo 类信息
     */
    public void setClassInfo(Class classInfo) {
        this.classInfo = classInfo;
    }

    /**
     * 获取函数名称
     * @return 函数名称
     */
    public String getMedthodName() {
        return medthodName;
    }

    /**
     * 设置函数名称
     * @param medthodName 函数名称
     */
    public void setMedthodName(String medthodName) {
        this.medthodName = medthodName;
    }

    /**
     * 获取函数信息
     * @return 函数信息
     */
    public Method getMedthodInfo() {
        return medthodInfo;
    }

    /**
     * 设置函数信息
     * @param medthodInfo 函数信息
     */
    public void setMedthodInfo(Method medthodInfo) {
        this.medthodInfo = medthodInfo;
    }

    /**
     * @return 获取类注解信息列表
     */
    public HashMap<String, AnnoField> getClassFieldList() {
        return classFieldList;
    }

    /**
     * @param classFieldList 设置类注解信息列表
     */
    public void setClassFieldList(HashMap<String, AnnoField> classFieldList) {
        this.classFieldList = classFieldList;
    }

    /**
     *  判断注解类当中是否存在该注解类
     * @param annoClassName_para 注解类名称
     * @return
     */
    public boolean  hasAnnoClass(String annoClassName_para){
        boolean tmphasAnnoClass = false;
        if(getClassFieldList()!=null && !classFieldList.isEmpty() &&
                annoClassName_para != null && !annoClassName_para.isEmpty()){
            tmphasAnnoClass = getClassFieldList().containsKey(annoClassName_para);
        }
        return tmphasAnnoClass;
    }

    /**
     *  判断注解类当中是否存在该注解类
     * @param annoClassName_para 注解类名称
     * @param annoFieldName_para 注解属性名称
     * @return
     */
    public boolean  hasAnnoClassField(String annoClassName_para ,String annoFieldName_para){
        boolean tmphasAnnoClass = false;
        if(hasAnnoClass(annoClassName_para)  && getClassFieldList().containsKey(annoClassName_para)){
            AnnoField tmpAnnoField = getClassFieldList().get(annoClassName_para);
            tmphasAnnoClass = tmpAnnoField.hasAnnoField(annoFieldName_para);
        }
        return tmphasAnnoClass;
    }

    /**
     *  判断注解类当中是否存在该注解类
     * @param annoClassName_para 注解类名称
     * @param annoFieldName_para 注解属性名称
     * @param annoFieldValue_para 注解属性值
     * @return
     */
    public boolean  hasAnnoClassFieldValue(String annoClassName_para ,String annoFieldName_para,String annoFieldValue_para){
        boolean tmphasAnnoClass = false;
        if(hasAnnoClass(annoClassName_para)  && getClassFieldList().containsKey(annoClassName_para)){
            AnnoField tmpAnnoField = getClassFieldList().get(annoClassName_para);
            tmphasAnnoClass = tmpAnnoField.hasAnnoFieldValue(annoFieldName_para,annoFieldValue_para);
        }
        return tmphasAnnoClass;
    }

    /**
     * 添加属性值到注解类中
     *
     * @param annoClassName_para 注解类名称
     * @param annoFieldName_para 注解属性名称
     * @param annoFieldValue_para 注解属性值
     */
    public void addAnnoClassFieldValue(String annoClassName_para, String annoFieldName_para, String annoFieldValue_para) {
        if (annoClassName_para == null || annoFieldName_para == null
                || annoClassName_para.isEmpty() || annoFieldName_para.isEmpty()) {
            return;
        }
        if (hasAnnoClass(annoClassName_para)) { // 发现注解类
            AnnoField tmpAnnoField = getClassFieldList().get(annoClassName_para);
            if (hasAnnoClassField(annoClassName_para, annoFieldName_para)) { // 发现注解字段
                String tmpFieldValue = tmpAnnoField.getAnnoFieldValue(annoFieldName_para);
                tmpFieldValue = annoFieldValue_para;
            } else { // 未发现注解字段
                tmpAnnoField.addAnnoFieldInfo(annoFieldName_para, annoFieldValue_para);
            }
        } else { //添加自定义注解类至列表中
            AnnoField tmpAnnoField = new AnnoField();
            tmpAnnoField.setAnnoName(annoClassName_para);
            try {
                tmpAnnoField.setClassInfo(Class.forName(annoClassName_para));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AnnoMedthodField.class.getName()).log(Level.SEVERE, null, ex);
            }
            tmpAnnoField.addAnnoFieldInfo(annoFieldName_para, annoFieldValue_para);
            getClassFieldList().put(annoClassName_para, tmpAnnoField);
        }
    }

    /**
     * 获取注解属性值
     * @param annoClassName_para
     * @param annoFieldName_para
     * @return 注解属性值
     */
    public String getAnnoFieldValue(String annoClassName_para ,String annoFieldName_para){
        String tmpAnnoFieldValue = null;
        if(hasAnnoClassField(annoClassName_para, annoFieldName_para)){
            tmpAnnoFieldValue = getClassFieldList().get(annoClassName_para).getAnnoFieldValue(annoFieldName_para);
        }
        return  tmpAnnoFieldValue;
    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param class_para
     * @param medthodName_para
     * @param <error>
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public String getServiceMthodDescription(Class class_para,String medthodName_para) throws Exception {
        //String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = medthodName_para;
        //Object[] arguments = joinPoint.getArgs();
        Class targetClass = class_para;
        String tmpAnnoClassName = "com.zeng.org.validator.ExtValidator";
        Class tmpAnnoClass = Class.forName(tmpAnnoClassName);
        String[] tmpMedthodNameList = {"equals","toString","hashCode","annotationType",
                "isProxyClass","getInvocationHandler","getProxyClass","newProxyInstance",
                "wait","getClass","notify","notifyAll"};
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                Annotation tmpAnnotation = method.getAnnotation(tmpAnnoClass);
                Class annotationclass = method.getAnnotation(tmpAnnoClass).getClass();
                Method[] annotationMedthodList =  annotationclass.getMethods();
                for(Method annotationMedthod : annotationMedthodList){
                    String tmpMedthodName = annotationMedthod.getName();
                    boolean inMedthodList =false;
                    for(String tmpmedthodName : tmpMedthodNameList){
                        if(tmpmedthodName.equals(tmpMedthodName)){
                            inMedthodList = true;
                            break;
                        }
                    }
                    if(inMedthodList){
                        continue;
                    }
                    System.out.println(tmpMedthodName);
                    Object invoke = annotationMedthod.invoke(tmpAnnotation);
                    System.out.println("AnnoFieldName: " + tmpMedthodName + "\tAnnoFieldValue: " + invoke.toString());
                }
            }
        }
        return description;
    }

    /**
     * 获取类注解信息
     */
    private void getAnnoInfoList(){
        //循环寻找


    }

    /**
     * 初始化函数的自定义注解值列表
     */
    private void initMethodsAnnoFieldValues() {
        Method tmpMethod = getMedthodInfo();
        if (tmpMethod == null) {
            return; // 若函数信息为空，则不进行初始化操作
        }
        //获取该函数的自定义注解列表
        Annotation[] tmpAnnotations = tmpMethod.getAnnotations();
        for (Annotation tmpAnnotation : tmpAnnotations) { // 遍历自定义注解
            //获取注解中声明的函数
            Method[] tmpAnnoMethods = tmpAnnotation.getClass().getDeclaredMethods();
            String tmpAnnoClassName = tmpAnnotation.annotationType().getName();
            for (Method tmpAnnoMethod : tmpAnnoMethods) { // 遍历自定义注解字段
                try {
                    if(isSysMethod(tmpAnnoMethod.getName())){
                        // 为系统函数时跳过
                        continue;
                    }
                    // 获取自定义注解字段值
                    Object tmpAnnoFieldValue = tmpAnnoMethod.invoke(tmpAnnotation);
                    if (tmpAnnoFieldValue != null) {
                        // 若不为空，则将自定义注解字段值添加至列表
                        System.out.println("medthodName: "+ tmpAnnoClassName +"\t FieldName: " +tmpAnnoMethod.getName() +"\t FieldValue: "+tmpAnnoFieldValue.toString());
                        addAnnoClassFieldValue(tmpAnnoClassName,tmpAnnoMethod.getName(), tmpAnnoFieldValue.toString());
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(AnnoMedthodField.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(AnnoMedthodField.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(AnnoMedthodField.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * 判断是否为系统函数
     * @param methodName_para 待判断的函数名称
     * @return
     */
    private boolean isSysMethod(String methodName_para){
        ArrayList<String> tmpMedthodNameList = new ArrayList(Arrays.asList("equals",
                "toString","hashCode","annotationType","isProxyClass",
                "getInvocationHandler","getProxyClass","newProxyInstance",
                "wait","getClass","notify","notifyAll"));
        return tmpMedthodNameList.contains(methodName_para);
    }
}
