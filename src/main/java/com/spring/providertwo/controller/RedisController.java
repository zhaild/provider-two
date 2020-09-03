package com.spring.providertwo.controller;

/**
 * @author zhaild
 * @date 2020/8/38:56 PM
 * @Description: redis控制层
 */
import com.spring.providertwo.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private IRedisService redisService;

    @RequestMapping("/redisString")
    public void redisString() {
        this.redisService.redisString();
    }

    @RequestMapping("/redisHash")
    public void redisHash() {
        this.redisService.redisHash();
    }

    @RequestMapping("/redisSet")
    public void redisSet() {
        this.redisService.redisSet();
    }

    @RequestMapping("/redisList")
    public void redisList() {
        this.redisService.redisList();
    }

    @RequestMapping("/redisSortedSet")
    public void redisSortedSet() {
        //有序的set，故而省略
    }

}