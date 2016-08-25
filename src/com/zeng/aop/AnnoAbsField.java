package com.zeng.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zeng on 2016-07-19.
 */
public class AnnoAbsField {
    /**
     * 类名称
     */
    private String className;

    /**
     * 类信息
     */
    private Class classInfo;

    /**
     *  注解信息列表
     */
    private HashMap<String, AnnoField> classFieldList;

    public AnnoAbsField(String className_para){
        if(className_para != null || !className_para.isEmpty()){
            setClassName(className_para);
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
     *  添加属性值到注解类中
     * @param annoClassName_para 注解类名称
     * @param annoFieldName_para 注解属性名称
     * @param annoFieldValue_para  注解属性值
     */
    public void addAnnoClassFieldValue(String annoClassName_para ,String annoFieldName_para,String annoFieldValue_para){
        if(annoClassName_para == null || annoFieldName_para == null
                || annoClassName_para.isEmpty() || annoFieldName_para.isEmpty() ){
            return;
        }
        if(hasAnnoClass(annoClassName_para)){
            if(hasAnnoClassField(annoClassName_para, annoFieldName_para)){
                AnnoField tmpAnnoField = getClassFieldList().get(annoClassName_para);
                if(tmpAnnoField.hasAnnoField(annoFieldName_para)){
                    String tmpAnnoFieldValue = tmpAnnoField.getValue().get(annoFieldName_para);
                    tmpAnnoFieldValue = annoFieldValue_para;
                }else{
                    tmpAnnoField.addAnnoFieldInfo(annoFieldName_para, annoFieldValue_para);
                }
            }else{
                AnnoField tmpAnnoField = new AnnoField();
                getClassFieldList().put(annoFieldName_para, tmpAnnoField);
            }
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
}
