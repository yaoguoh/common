package com.github.yaoguoh.common.elasticsearch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Ignore.
 *
 * @author dqq
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {

    /**
     * Is ignore boolean.
     *
     * @return the boolean
     */
    boolean isIgnore() default true;

}
