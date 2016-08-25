package com.zeng.aop;

import java.util.HashMap;

/**
 * Created by zeng on 2016-07-19.
 */
public class AnnoField {
    /**
     *  注解类名称
     */
    private String annoName;
    /**
     * 注解类
     */
    private Class classInfo;
    /**
     * 注解值列表
     */
    private HashMap<String,String> valueList = new HashMap<String,String>();

    /**
     * @return 获取注解名称
     */
    public String getAnnoName() {
        return annoName;
    }

    /**
     * @param annoName 设置注解名称
     */
    public void setAnnoName(String annoName) {
        this.annoName = annoName;
    }

    /**
     * @return 获取注解类信息
     */
    public Class getClassInfo() {
        return classInfo;
    }

    /**
     * @param classInfo 设置注解类信息
     */
    public void setClassInfo(Class classInfo) {
        this.classInfo = classInfo;
    }

    /**
     * @return 获取注解值
     */
    public HashMap<String,String> getValue() {
        return valueList;
    }

    /**
     * @param value 设置注解值
     */
    public void setValue(HashMap<String,String> value) {
        this.valueList = value;
    }

    /**
     *  判断注解类当中是否存在该注解属性
     * @param annoFieldName_para 注解属性名称
     * @return True : 存在对应注解属性   False : 不存在对应注解属性
     */
    public boolean hasAnnoField(String annoFieldName_para){
        boolean tmphasAnnoField = false;
        if(valueList !=null && !valueList.isEmpty() && annoFieldName_para != null && !annoFieldName_para.isEmpty()){
            tmphasAnnoField = valueList.containsKey(annoFieldName_para);
        }
        return tmphasAnnoField;
    }

    /**
     *  判断注解类当中是否存在该注解属性值
     * @param annoFieldName_para 注解属性名称
     * @param annoFieldValue_para 注解属性值
     * @return True : 存在对应注解属性值   False : 不存在对应注解属性值
     */
    public boolean hasAnnoFieldValue(String annoFieldName_para,String annoFieldValue_para){
        boolean tmphasAnnoValue = false;
        if(hasAnnoField(annoFieldName_para) && annoFieldValue_para != null && !annoFieldValue_para.isEmpty()){
            String tmpAnnoFieldValue = valueList.get(annoFieldName_para);
            if(tmpAnnoFieldValue != null && !tmpAnnoFieldValue.isEmpty() && tmpAnnoFieldValue.equals(annoFieldValue_para)){
                tmphasAnnoValue = true;
            }
        }
        return tmphasAnnoValue;
    }

    /**
     *  添加注解属性值列表
     * @param annoFieldName_para 注解属性名称
     * @param annoFieldValue_para  注解属性值
     */
    public void addAnnoFieldInfo(String annoFieldName_para,String annoFieldValue_para){
        if(annoFieldName_para == null || annoFieldValue_para == null || annoFieldName_para.isEmpty()){
            return;
        }
        if(valueList.containsKey(annoFieldName_para)){
            String tmpAnnoFieldValue = valueList.get(annoFieldName_para);
            tmpAnnoFieldValue = annoFieldValue_para;
        }else{
            valueList.put(annoFieldName_para, annoFieldValue_para);
        }
    }
    /**
     *  从注解属性值列表中删除对应注解
     * @param annoFieldName_para 注解属性名称
     * @param annoFieldValue_para  注解属性值
     */
    public void removeAnnoFieldInfo(String annoFieldName_para,String annoFieldValue_para){
        if(annoFieldName_para == null || annoFieldValue_para == null || annoFieldName_para.isEmpty()){
            return;
        }
        if(valueList.containsKey(annoFieldName_para)){
            valueList.remove(annoFieldName_para);
        }
    }
    /**
     * 获取注解值
     * @param annoFieldName_para 注解属性名称
     * @return 注解属性值
     */
    public String getAnnoFieldValue(String annoFieldName_para){
        String tmpAnnoFieldValue = null;
        if(hasAnnoField(annoFieldName_para)){
            tmpAnnoFieldValue = valueList.get(annoFieldName_para);
        }
        return tmpAnnoFieldValue;
    }
}
