package com.glorze;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 *  Spring Boot 启动类
 *
 * @ClassName:  SpringbootRedisApplication
 * @author: glorze.com_高老四
 * @since: 2018/11/27 11:28
 */
@EnableCaching
@SpringBootApplication
@MapperScan(basePackages = "com.glorze", annotationClass = Repository.class)
public class SpringbootRedisApplication {

    @Autowired
    private RedisTemplate redisTemplate = null;

    /**
     * 在RedisTemplate初始化之后进行自定义操作,redisTemplate都会默认创建一个stringRedisSerializer
     * @Title: init
     * @param []
     * @return void
     * @author: 高老四博客
     * @since: 2018/11/27 14:44
     */
    @PostConstruct
    public void  init(){
        initRedisTemplate();
    }

    public  void initRedisTemplate(){
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisApplication.class, args);
    }
}
