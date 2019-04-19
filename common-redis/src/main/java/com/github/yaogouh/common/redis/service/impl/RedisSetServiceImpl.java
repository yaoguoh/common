package com.github.yaogouh.common.redis.service.impl;

import com.github.yaogouh.common.redis.service.RedisSetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The class Redis set service.
 *
 * @author WYG
 */
@Slf4j
@Service
@AllArgsConstructor
public class RedisSetServiceImpl implements RedisSetService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * Gets all value.
     *
     * @param key the key
     * @return the all value
     */
    @Override
    public Set<String> getAllValue(@NotNull String key) {
        Set<String> result;
        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        result = setOps.members(key);
        log.info("getAllValue - 根据key获取元素. [OK] key={}, value={}", key, result);
        return result;
    }

    /**
     * Add long.
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    @Override
    public Long add(@NotNull String key, @NotNull String... value) {
        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        Long result = setOps.add(key, value);
        log.info("add - 向key里面添加元素, key={}, value={}, result={}", key, value, result);
        return result;
    }

    /**
     * Remove long.
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    @Override
    public Long remove(@NotNull String key, @NotNull String... value) {
        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        Long result = setOps.remove(key, (Object) value);
        log.info("remove - 根据key移除元素, key={}, value={}, result={}", key, value, result);
        return result;
    }
}
