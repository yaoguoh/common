package com.github.yaoguoh.common.log.annotation;

import com.github.yaoguoh.common.log.enums.BusinessType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author yaoguohh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Log {
    /**
     * 模块
     *
     * @return string string
     */
    String module() default "";

    /**
     * 业务操作类型
     *
     * @return the business type
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求的参数
     *
     * @return the boolean
     */
    boolean saveRequestData() default true;

    /**
     * 是否保存响应的参数
     *
     * @return the boolean
     */
    boolean saveResponseData() default true;
}
