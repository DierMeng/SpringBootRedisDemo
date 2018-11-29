package com.glorze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Redis在Spring Boot中的常用操作
 *
 * @ClassName: RedisOperationController
 * @author: glorze.com_高老四
 * @since: 2018/11/27 14:49
 */
@RestController
@RequestMapping("/redis")
public class RedisOperationController {

    @Autowired
    private RedisTemplate redisTemplate = null;

    @Autowired
    private StringRedisTemplate stringRedisTemplate = null;

    /**
     * Redis操作字符串和哈希数据类型的基本操作
     * @Title: testStringAndHash
     * @param []
     * @return java.lang.String
     * @author: 高老四博客
     * @since: 2018/11/27 15:05
     */
    @RequestMapping("/stringAndHash")
    public String testStringAndHash() {
        redisTemplate.opsForValue().set("redis", "redis常规操作举例");
        // 默认使用的是JDK的序列化器,所以Redis保存的时候不是整数,即不能做运算操作
        redisTemplate.opsForValue().set("int_key", "1");
        // 使用stringRedisTemplate可以进行运算操作
        stringRedisTemplate.opsForValue().set("int", "1");
        stringRedisTemplate.opsForValue().increment("int", 1);
        // 使用Redis底层进行减一操作,RedisTemplate不支持decr命令
        Jedis jedis = (Jedis) stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        jedis.decr("int");

        // hash操作
        Map<String, String> hash = new HashMap<>(16);
        hash.put("site", "http://www.glorze.com");
        hash.put("siteName", "高老四程序员技术博客");
        // 存入一个散列数据类型
        stringRedisTemplate.opsForHash().putAll("hash", hash);
        // 新增一个字段
        stringRedisTemplate.opsForHash().put("hash", "siteAuthor", "高老四");
        // 绑定散列操作的key,这样可以连续对同一个散列数据类型进行操作
        BoundHashOperations hashOps = stringRedisTemplate.boundHashOps("hash");
        // 删除两个个字段
        // hashOps.delete("site", "siteName");
        // 新增一个字段
        hashOps.put("siteEnglish", "Glorze");
        return "success";
    }

    /**
     * 操作Redis中的列表(链表)类型
     * @Title: testList
     * @param []
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author: 高老四博客
     * @since: 2018/11/27 15:08
     */
    @RequestMapping("/list")
    public String testList() {
        // 链表从左到右顺序为10,8,6,4,2
        stringRedisTemplate.opsForList().leftPushAll("list1", "2", "4", "6", "8", "10");
        // 链表从左到右顺序为1,3,5,7,9
        stringRedisTemplate.opsForList().rightPushAll("list2", "1", "3", "5", "7", "9", "10");
        // 绑定list2链表操作
        BoundListOperations listOps = stringRedisTemplate.boundListOps("list2");
        // 从右边弹出一个成员
        Object result1 = listOps.rightPop();
        // 获取定位元素，Redis从0开始计算,这里值为"2"
        Object result2 = listOps.index(1);
        // 从左边插入链表
        listOps.leftPush("1");
        // 求链表长度
        Long size = listOps.size();
        // 求链表下标区间成员,整个链表下标范围为0到size-1,这里不取最后一个元素
        List elements = listOps.range(0, size - 2);
        return "success";
    }

    /**
     * 操作集合 散列表无须不重复 set
     * @Title: testSet
     * @param []
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author: 高老四博客
     * @since: 2018/11/27 15:14
     */
    @RequestMapping("/set")
    public String testSet() {
        // 集合不允许重复,这里只会插入5个成员到集合中
        stringRedisTemplate.opsForSet().add("set1", "1", "1", "2", "3", "4", "5");
        stringRedisTemplate.opsForSet().add("set2", "2", "4", "6", "8");
        // 绑定set1集合操作
        BoundSetOperations setOps = stringRedisTemplate.boundSetOps("set1");
        // 增加两个元素
        setOps.add("6", "7");
        // 删除两个元素
        setOps.remove("1", "7");
        // 返回所有元素
        Set<String> set1 = setOps.members();
        // 求成员数
        Long size = setOps.size();
        // 求交集
        Set<String> inter = setOps.intersect("set2");
        // 求交集，并且用新集合inter保存
        setOps.intersectAndStore("set2", "inter");
        // 求差集
        Set diff = setOps.diff("set2");
        // 求差集，并且用新集合diff保存
        setOps.diffAndStore("set2", "diff");
        // 求并集
        Set union = setOps.union("set2");
        // 求并集，并且用新集合union保存
        setOps.unionAndStore("set2", "union");
        return "success";
    }

    /**
     * 通过TypedTuple接口操作Redis有序集合
     * @Title: testZset
     * @param []
     * @return java.lang.String
     * @author: 高老四博客
     * @since: 2018/11/27 15:20
     */
    @RequestMapping("/zset")
    public String testZset() {
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<>();
        int magicNum = 9;
        for (int i = 1; i <= magicNum; i++) {
            // 分数
            double score = i * 0.1;
            // 创建一个TypedTuple对象，存入值和分数
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<String>("value" + i, score);
            typedTupleSet.add(typedTuple);
        }
        // 往有序集合插入元素
        stringRedisTemplate.opsForZSet().add("zset1", typedTupleSet);
        // 绑定zset1有序集合操作
        BoundZSetOperations<String, String> zsetOps = stringRedisTemplate.boundZSetOps("zset1");
        // 增加一个元素
        zsetOps.add("value10", 0.26);
        Set<String> setRange = zsetOps.range(1, 6);
        // 按分数排序获取有序集合
        Set<String> setScore = zsetOps.rangeByScore(0.2, 0.6);
        // 定义值范围
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        // 大于value3
        range.gt("value3");
        // 大于等于value3
        // range.gte("value3");
        // 小于value8
        // range.lt("value8");
        // 小于等于value8
        range.lte("value8");
        // 按值排序,按字符串排序
        Set<String> setLex = zsetOps.rangeByLex(range);
        // 删除元素
        zsetOps.remove("value9", "value2");
        // 求分数
        Double score = zsetOps.score("value8");
        // 在下标区间下,按分数排序，同时返回value和score
        Set<ZSetOperations.TypedTuple<String>> rangeSet = zsetOps.rangeWithScores(1, 6);
        // 在分数区间下,按分数排序，同时返回value和score
        Set<ZSetOperations.TypedTuple<String>> scoreSet = zsetOps.rangeByScoreWithScores(1, 6);
        // 按从大到小排序
        Set<String> reverseSet = zsetOps.reverseRange(2, 8);
        return "success";
    }

}
