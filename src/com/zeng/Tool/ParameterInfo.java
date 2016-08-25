package com.zeng.Tool;

/**
 * Created by zeng on 2016-07-19.
 */
public class ParameterInfo {
    private Integer index;
    private String paramaterName;
    private Class<?> arameterClass;

    /**
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * @return the paramaterName
     */
    public String getParamaterName() {
        return paramaterName;
    }

    /**
     * @param paramaterName the paramaterName to set
     */
    public void setParamaterName(String paramaterName) {
        this.paramaterName = paramaterName;
    }

    /**
     * @return the arameterClass
     */
    public Class<?> getArameterClass() {
        return arameterClass;
    }

    /**
     * @param arameterClass the arameterClass to set
     */
    public void setArameterClass(Class<?> arameterClass) {
        this.arameterClass = arameterClass;
    }
}
