package com.yinxt.surveyscale;

import com.yinxt.surveyscale.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testGetRedisKeys() {
        String me = (String) redisTemplate.opsForValue().get("me");
        log.info("me={}", me);

        String str = (String) RedisUtil.getKey("me");
        log.info("str={}", str);

        String id = RedisUtil.getGenericId(RedisTest.class, "SC");
        log.info("id={}", id);
    }
}

