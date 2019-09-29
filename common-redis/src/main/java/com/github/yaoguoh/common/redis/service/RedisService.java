package com.github.yaoguoh.common.redis.service;

import javax.validation.constraints.NotNull;
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
    String getByKey(@NotNull String key);

    /**
     * Delete . 通过 key 删除
     *
     * @param key the key
     */
    void delete(@NotNull String key);

    /**
     * Delete. 通过 key集合 删除
     *
     * @param keys the keys
     */
    void delete(@NotNull Collection<String> keys);

    /**
     * Set.
     *
     * @param key   the key
     * @param value the value
     */
    void set(@NotNull String key, @NotNull String value);

    /**
     * Set.
     *
     * @param key     the key
     * @param value   the value
     * @param timeout the timeout
     * @param unit    the unit
     */
    void set(@NotNull String key, @NotNull String value, @NotNull long timeout, @NotNull TimeUnit unit);
}
