package com.github.yaogouh.common.log.annotation;

import com.github.yaogouh.common.log.enums.TargetType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author WYG
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 描述
     *
     * @return the string
     */
    String description() default "";

    /**
     * 操作类型
     *
     * @return the business type
     */
    TargetType targetType() default TargetType.OTHER;

    /**
     * 是否保存请求的参数
     *
     * @return the boolean
     */
    boolean saveRequestParam() default true;
}
