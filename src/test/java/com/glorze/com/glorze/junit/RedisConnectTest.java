package com.glorze.com.glorze.junit;

import com.glorze.config.RedisConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

/**
 * TODO
 *
 * @ClassName: RedisConnectTest
 * @author: glorze.com_高老四
 * @since: 2018/11/27 11:57
 */
public class RedisConnectTest {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(RedisConfig.class);
        RedisTemplate redisTemplate = ac.getBean(RedisTemplate.class);
        useSessionCallback(redisTemplate);
    }

    /**
     * 普遍使用SessionCallback接口对Redis进行同一连接的多次操作,除非涉及到Redis底层计算等操作才考虑使用RedisCallback接口
     * Java 8之前的写法
     * @Title: useRedisCallback
     * @param [redisTemplate]
     * @return void
     * @author: 高老四博客
     * @since: 2018/11/27 14:32
     */
    public static void useSessionCallback(RedisTemplate redisTemplate){
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.opsForValue().set("glorze", "高老四");
                operations.opsForHash().put("hash","blogName", "高老四Java技术博客");
                return null;
            }
        });
    }
}
