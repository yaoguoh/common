package com.github.yaogouh.elasticsearch.annotation;



import java.lang.annotation.*;

/**
 * The interface Document.
 *
 * @author dqq
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Document {

    /**
     * Index name string.
     *
     * @return the string
     */
    String indexName();

    /**
     * Type string.
     *
     * @return the string
     */
    String type() default "doc";

    /**
     * Route route enum.
     *
     * @return the route enum
     */
    String route() default "1";
}
