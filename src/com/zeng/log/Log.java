package com.zeng.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zeng on 2016-07-19.
 * <p>
 * <p>
 * 类的方法描述注解
 *
 * @author LuoYu
 */

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {

    /**
     * 要执行的操作类型比如：add操作
     **/
    public String operationType() default "add";

    /**
     * 要执行的具体操作比如：【添加仓库】
     **/
    public String operationName() default "oop";

}