package com.spring.providertwo.service.impl;

/**
 * @author zhaild
 * @date 2020/8/39:21 PM
 * @Description: redis配置实现类
 */


import com.spring.providertwo.service.IRedisService;
import com.spring.providertwo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service("redisService")
public class RedisServiceIml implements IRedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    public void redisString() {
        //添加值
        redisUtil.stringSet("AAA", "这是一个String类型的值");
        //取值
        Object value = redisUtil.stringGet("AAA");
        System.out.println(value);
    }

    public void redisHash() {
        //添加值
        redisUtil.hashSet("BBB", "test1", "原hash值1");
        redisUtil.hashSet("BBB", "test2", "新hash值1");
        redisUtil.hashSet("BBB", "test1", "原hash值2");
        redisUtil.hashSet("BBB", "test2", "新hash值2");
        //取值
        Object value1 = redisUtil.hashGet("BBB", "test1");
        Object value2 = redisUtil.hashGet("BBB", "test2");
        System.out.println(value1);
        System.out.println(value2);
    }

    public void redisSet() {
        //添值
        redisUtil.setSet("CCC", "这是一组Set集合的第一个");
        redisUtil.setSet("CCC", "这是一组Set集合的第二个");
        redisUtil.setSet("CCC", "这是一组Set集合的第三个");
        //取值
        Set vaule = redisUtil.setGet("CCC");
        System.out.println(vaule);
    }

    public void redisList() {
        //添加值
        redisUtil.listSet("DDD", "这是一组List集合的第一个");
        redisUtil.listSet("DDD", "这是一组List集合的第二个");
        redisUtil.listSet("DDD", "这是一组List集合的第三个");
        redisUtil.listSet("DDD", "这是一组List集合的第四个");
        //取值
        List list = redisUtil.listGet("DDD", 0, -1);
        long l = redisUtil.listDelete("DDD",0,"这是一组List集合的第四个");
        System.out.println(list);
    }

    public void redisSortedSet() {
        //有序的set，故而省略
    }

}