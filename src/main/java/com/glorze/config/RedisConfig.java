package com.glorze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis数据源配置
 *
 * @ClassName: RedisConfig
 * @author: glorze.com_高老四
 * @since: 2018/11/27 11:28
 */
@Configuration
public class RedisConfig {

    private RedisConnectionFactory redisConnectionFactory = null;

    /**
     * jedisConnection实现RedisConnection从而对redis进行封装
     * @Title: initConnectionFactory
     * @param []
     * @return org.springframework.data.redis.connection.RedisConnectionFactory
     * @author: 高老四博客
     * @since: 2018/11/27 11:42
     */
    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory initConnectionFactory(){
        if(null != this.redisConnectionFactory){
            return this.redisConnectionFactory;
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(88);
        // 最大连接数
        jedisPoolConfig.setMaxTotal(188);
        // 最大等待毫秒数
        jedisPoolConfig.setMaxWaitMillis(2000);
        // 创建Jedis连接工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        // 配置Redis连接服务器
        RedisStandaloneConfiguration rsc = jedisConnectionFactory.getStandaloneConfiguration();
        // Redis 地址
        rsc.setHostName("127.0.0.1");
        // 端口
        rsc.setPort(6379);
        // 密码,由于老四没有设置密码,所以注释掉
        // rsc.setPassword(RedisPassword.of("123456"));
        this.redisConnectionFactory = jedisConnectionFactory;
        return redisConnectionFactory;
    }
    
    /**
     * 通过RedisTemplate管理Redis连接
     * @Title: initRedisTemplate
     * @param []
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.Object,java.lang.Object>
     * @author: 高老四博客
     * @since: 2018/11/27 11:50
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> initRedisTemplate(){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(initConnectionFactory());
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }

}
