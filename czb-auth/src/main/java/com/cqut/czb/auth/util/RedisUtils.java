package com.cqut.czb.auth.util;

import com.cqut.czb.bn.entity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void put(String key, User user) {
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        operations.set(key, user,60*60*24, TimeUnit.SECONDS);
    }

    /**
     * 将用户信息从redis取出
     * */
    public User get(String key) {
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        return operations.get(key);
    }
}
