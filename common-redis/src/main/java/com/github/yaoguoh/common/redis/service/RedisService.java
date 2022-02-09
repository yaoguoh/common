package com.github.yaoguoh.common.redis.service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * The interface Redis service.
 *
 * @author WYG
 */
public interface RedisService {

    /**
     * Gets key. 通过 key 查询
     *
     * @param key the key
     * @return value by key
     */
    String getByKey(String key);

    /**
     * Delete . 通过 key 删除
     *
     * @param key the key
     */
    void delete(String key);

    /**
     * Delete. 通过 key集合 删除
     *
     * @param keys the keys
     */
    void delete(Collection<String> keys);

    /**
     * Set.
     *
     * @param key   the key
     * @param value the value
     */
    void set(String key, String value);

    /**
     * Set.
     *
     * @param key     the key
     * @param value   the value
     * @param timeout the timeout
     * @param unit    the unit
     */
    void set(String key, String value, long timeout, TimeUnit unit);
}
