package com.github.yaogouh.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * The interface Join.
 *
 * @author dqq
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Join {

    /**
     * Child name string.
     *
     * @return the string
     */
    String childName();
}
