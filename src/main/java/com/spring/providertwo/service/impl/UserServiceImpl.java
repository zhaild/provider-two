package com.spring.providertwo.service.impl;

import com.spring.providertwo.dao.UserDao;
import com.spring.providertwo.entity.User;
import com.spring.providertwo.service.IUserService;
import com.spring.providertwo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author zhaild
 * @date 2020/7/3110:37 AM
 * @Description: 用户层实现类
 */
@Service
@Component
@SuppressWarnings("unchecked")
public class UserServiceImpl implements IUserService {

    @Autowired
    public UserDao userDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Override

    public void createUser(User user) {

        /**
         * 更新操作--新增
         * 1、先查询数据库获取userList对象
         * 2、再删除redis缓存
         * 3、新增操作更新数据库
         */
        List userList = redisUtil.listGet("USER:ALL:ID:NAME", 0, -1);
        if(redisUtil.listDelete("USER:ALL:ID:NAME",0,userList.get(0))>0){
            //删除缓存成功，执行新增更新操作
            userDao.createUser(user);
        }
    }

    @Override
    public List<User> findAllUser() {

        //先判断redis缓存中是否有数据(根据key判断)；
        // 如果有，直接获取缓存中的数据直接返回；如果没有，访问数据库获取数据，然后再存入redis缓存。
        /**
         * 考虑并发条件下，缓存击穿的问题（同时10000+的并发请求，都来访问数据库）
         * 方法一：缓存中存储空值
         * 方法二：采用布隆过滤器
         */
        List userList = redisUtil.listGet("USER:ALL:ID:NAME", 0, -1);
        if (userList.size() > 0) {
            System.out.println("缓存查询。。。。。");
            return userList;
        }
        //缓存为空，查询数据库
        userList = userDao.findAllUser();
        if (userList.size() == 0) {
            //(缓存击穿/穿透)查询数据库结果为空，防止下次查询继续查询数据库，缓存空对象；超时时间设置5分钟以内 例：3分钟
            redisUtil.listSetWithTime("USER:ALL:ID:NAME", userList, 3 * 60);
            System.out.println("数据库空值查询。。。。。。");
            return userList;
        }
        //数据库查询非空时，将查询的结果(非空)放入缓存中,并且设置失效时间 单位：s 30分钟
        redisUtil.listSetWithTime("USER:ALL:ID:NAME", userList, 30 * 60);
        System.out.println("数据库非空值查询。。。。。。");

        return userList;
    }

    @Override

    public void delUser(String id) {
        /**
         * 更新操作--删除
         * 1、先删除redis缓存
         * 2、删除操作更新数据库
         */
        userDao.delUser(id);
    }

    @Override

    public void updateUser(User user) {
        /**
         * 更新操作--编辑
         * 1、先删除redis缓存
         * 2、编辑操作更新数据库
         */
        userDao.updateUser(user);
    }

}
