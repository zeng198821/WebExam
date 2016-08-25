package com.zeng.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zeng on 2016-07-19.
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtValidator {

    /**
     * Ajax请求名称注解，默认值为函数名称
     *
     * @return
     */
    public String value() default Constants.EXT_VALIDATOR_MEDTHOD_NAME;

}
