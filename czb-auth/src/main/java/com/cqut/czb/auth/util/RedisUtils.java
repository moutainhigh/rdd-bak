package com.cqut.czb.auth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 创建者：杨强、曹渝
 * 创建时间：2019/04/18
 *
 * redis内存使用
 * */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将用户信息存入redis
     * */
    public void put(String key, Object object) {
        ValueOperations<String, Object> operations=redisTemplate.opsForValue();
        // 默认1天登录超时
        operations.set(key, object,60*60*24, TimeUnit.SECONDS);
    }

    /**
     * 将信息从redis取出
     * */
    public Object get(String key) {
        ValueOperations<String, Object> operations=redisTemplate.opsForValue();
        return operations.get(key);
    }

    public void remove(String key) {
        RedisOperations<String, Object> operations = redisTemplate.opsForValue().getOperations();
        operations.delete(key);
    }

    /**
     * 判断redis是否有该数据
     * */
    public Boolean hasKey(String key) {
        return redisTemplate.opsForValue().getOperations().hasKey(key);
    }
}
