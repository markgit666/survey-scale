package com.yinxt.surveyscale.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Component
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 序列号生成器
     *
     * @param clazz
     * @param prefix
     * @return
     */
    public static String getGenericId(Class clazz, String prefix) {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        long i = redisTemplate.opsForValue().increment(clazz.getSimpleName() + date);
        return prefix + date + new DecimalFormat("0000").format(i);
    }

    /**
     * id生成器
     *
     * @param prefix
     * @return
     */
    public static String getSequenceId(String prefix) {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        long i = redisTemplate.opsForValue().increment(prefix + date);
        return prefix + date + new DecimalFormat("0000").format(i);
    }

    /**
     * 设置key
     *
     * @param key
     * @param value
     */
    public static void setKey(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置有过期时间的key
     *
     * @param key
     * @param value
     * @param time
     */
    public static void setKey(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 删除key
     *
     * @param key
     */
    public static void deleteKey(String key) {
        redisTemplate.delete(key);
    }

}
