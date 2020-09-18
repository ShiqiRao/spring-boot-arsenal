package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @Test
    void testString() {
        //设置键值对
        redisTemplate.opsForValue().set("num", "123");
        //根据name获取值
        redisTemplate.opsForValue().get("num");
        //设置带有效期的键值对
        redisTemplate.opsForValue().set("fade-num", "321", 10, TimeUnit.SECONDS);
        //十秒后结果为null
        redisTemplate.opsForValue().get("fade-num");
    }

    @Test
    void testList() {
        //通过leftPush更新列表,也可以选择rightPush方法
        redisTemplate.opsForList().leftPush("languages", "java");
        redisTemplate.opsForList().leftPush("languages", "python");
        redisTemplate.opsForList().leftPush("languages", "c++");
        //查询列表长度
        assert redisTemplate.opsForList().size("languages") >= 3;
        //弹出列表左边的元素，之后结果在数据库中不复存在
        assert Objects.equals(redisTemplate.opsForList().leftPop("languages"), "c++");
        //弹出列表右边的元素，之后结果在数据库中不复存在
        assert Objects.equals(redisTemplate.opsForList().rightPop("languages"), "java");
    }

    @Test
    void testHash() {
        //更新hash 第一个参数为hash的键值，第二个参数为该hash内键值对的键值
        redisTemplate.opsForHash().put("hash", "red", "小红");
        redisTemplate.opsForHash().put("hash", "elephant", "小象");
        redisTemplate.opsForHash().put("hash", "red-elephant", "小红象");
        //根据hash的键值以及hash内建筑队的键值检索对应信息
        assert Objects.equals(redisTemplate.opsForHash().get("hash", "red"), "小红");
        //获取hash内所有键值对的键值
        Set<Object> hash = redisTemplate.opsForHash().keys("hash");
        hash.forEach(System.out::println);
    }

    @Test
    void testSet() {
        //新增set
        redisTemplate.opsForSet().add("set", "sir", "yes", "sir", "madam");
        //随机弹出一个元素
        redisTemplate.opsForSet().pop("set");
        //获取set中所有内容
        redisTemplate.opsForSet().members("set").forEach(System.out::println);
    }

    @Test
    void testZSet() {
        //新增zset内容
        redisTemplate.opsForZSet().add("zset", "sir", -1);
        redisTemplate.opsForZSet().add("zset", "sir", 9);
        redisTemplate.opsForZSet().add("zset", "yes", 3);
        redisTemplate.opsForZSet().add("zset", "madam", 10);
        //获取该zset中sir的权重
        System.out.println(redisTemplate.opsForZSet().score("zset", "sir"));
        //根据权重区间遍历zset
        redisTemplate.opsForZSet().range("zset", 0, 9).forEach(System.out::println);
    }

}
