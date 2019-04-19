package com.github.yaogouh.common.elasticsearch.support;

import org.elasticsearch.action.index.IndexResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * ES查询接口
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
public interface IESRepository<T, ID extends Serializable> {

    /**
     * 保存实体
     *
     * @param t 实体参数
     * @return IndexResponse index response
     * @throws IOException the io exception
     */
    IndexResponse save(T t) throws IOException;

    /**
     * 指定route值，保存实体.
     *
     * @param t     the t
     * @param route the route 路由值
     * @return the index response
     * @throws IOException the io exception
     */
    IndexResponse save(T t, String route) throws IOException;

    /**
     * Find by id optional.
     *
     * @param t  the t
     * @param id the id
     * @return the optional
     * @throws IOException the io exception
     */
    Optional<T> findById(T t, ID id) throws IOException;

    /**
     * Delete.
     *
     * @param t  the t
     * @param id the id
     * @throws IOException the io exception
     */
    void delete(T t, ID id) throws IOException;
}
