package com.cqut.czb.auth.redis;

import com.cqut.czb.bn.entity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    public void put(String key, User user) {
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        operations.set(key, user,60*60*24, TimeUnit.SECONDS);
    }

    public User get(String key) {
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        return operations.get(key);
    }
}
