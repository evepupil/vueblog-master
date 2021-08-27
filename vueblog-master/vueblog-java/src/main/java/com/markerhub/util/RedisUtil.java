package com.markerhub.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : RedisUtil
 * @Author : Administrator
 * @Date: 2021/8/20 9:49
 * @Description :
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    public void setWithTime(String key,Object value,int seconds){
        try {
            System.out.println(redisTemplate);
            redisTemplate.opsForValue().set(key,value,seconds, TimeUnit.SECONDS);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
