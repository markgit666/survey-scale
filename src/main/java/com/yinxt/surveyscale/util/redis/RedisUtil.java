package com.yinxt.surveyscale.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

}
