package com.github.yaogouh.common.util.annotation;

import java.lang.annotation.*;

/**
 * 判断枚举类中是否包含
 *
 * @author WYG
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InEnum {


    /**
     * 默认返回信息
     *
     * @return the string
     */
    String message() default "参数无效！";


    /**
     * 判断参数是否存在
     *
     * @return the boolean
     */
    boolean contains() default false;

}
