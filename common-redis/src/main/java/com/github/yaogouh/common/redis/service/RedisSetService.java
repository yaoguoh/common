
package com.github.yaogouh.redis.service;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The interface Redis set service.
 *
 * @author WYG
 */
public interface RedisSetService {
    /**
     * 返回集合中的所有成员
     *
     * @param key the key
     * @return the all value
     */
    Set<String> getAllValue(@NotNull String key);

    /**
     * 向集合添加一个或多个成员
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    Long add(@NotNull String key, @NotNull String... value);

    /**
     * 移除集合中一个或多个成员
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    Long remove(@NotNull String key, @NotNull String... value);

}
