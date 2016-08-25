package com.zeng.log;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * Created by zeng on 2016-07-19.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    String operateModelNm() default "ModelNm";

    String operateFuncNm() default "FuncNm";

    String operateDescribe() default "Describe";
}