package com.github.yaoguoh.common.redis.service.impl;

import com.github.yaoguoh.common.redis.service.RedisService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * The type Redis service.
 *
 * @author WYG
 */
@Slf4j
@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public String getByKey(@NotNull String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        log.info("getByKey. [OK] key={}, value={}", key, value);
        return value;
    }

    @Override
    public void delete(@NotNull String key) {
        stringRedisTemplate.delete(key);
        log.info("delete. [OK] key={}", key);
    }

    @Override
    public void delete(@NotNull Collection<String> keys) {
        stringRedisTemplate.delete(keys);
        log.info("delete. [OK] keys={}", keys);
    }

    @Override
    public void set(@NotNull String key, @NotNull String value) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value);
        stringRedisTemplate.expire(key, 1L, TimeUnit.MINUTES);
        log.info("set. [OK] key={}, value={}, expire=默认超时时间1分钟", key, value);
    }

    @Override
    public void set(@NotNull String key, @NotNull String value, @NotNull long timeout, @NotNull TimeUnit unit) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value);
        stringRedisTemplate.expire(key, timeout, unit);
        log.info("set. [OK] key={}, value={}, timeout={}, unit={}", key, value, timeout, unit);

    }
}
