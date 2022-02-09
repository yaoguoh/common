package com.github.yaoguoh.common.log.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author WYG
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLogging {

    /**
     * 是否保存请求信息
     *
     * @return the boolean
     */
    boolean save() default false;
}
