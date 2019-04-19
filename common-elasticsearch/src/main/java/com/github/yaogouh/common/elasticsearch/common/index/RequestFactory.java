package com.github.yaogouh.elasticsearch.common.index;

import org.elasticsearch.action.ActionRequest;

/**
 * 构建ES Request 的父接口.
 *
 * @param <T> the type parameter
 */
public interface RequestFactory<T> {

    /**
     * 构建索引Request.
     *
     * @param t the t
     * @return the action request
     */
    ActionRequest createRequest(T t);

}
