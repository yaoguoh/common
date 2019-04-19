package com.github.yaogouh.redis.service;

import javax.validation.constraints.NotNull;
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
     * @return value
     */
    String getByKey(@NotNull String key);

    /**
     * Delete key. 通过 key 删除
     *
     * @param key the key
     */
    void deleteByKey(@NotNull String key);

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
