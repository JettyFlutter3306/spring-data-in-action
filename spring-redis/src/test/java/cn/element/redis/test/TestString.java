package cn.element.redis.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestString {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> operations;

    @Before
    public void init() {
        // 获取操作简单字符串类型数据的数据句柄
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        operations = redisTemplate.opsForValue();
    }

    /**
     * 测试保存数据
     * set(String, String)                      向Redis中保存一条数据
     * set(String, String, long, TimeUnit)      保存数据并设置过期时间
     * set(String, String, long)                替换
     */
    @Test
    public void testSet() {
        operations.set("name", "luobida");
        operations.set("name", "XX", 2);
    }

    @Test
    public void testSetExpired() {
        operations.set("name", "洛必达", 20, TimeUnit.SECONDS);
    }

    /**
     * setIdAbsent(key, value)      不存在就创建
     */
    @Test
    public void testSetIfAbsent() {
        operations.setIfAbsent("name", "Newton");
    }

    /**
     * multiSet(hashMap)        使用HashMap批量插入
     */
    @Test
    public void testMultiSet() {
        Map<String, String> map = new HashMap<>();
        map.put("name1", "牛顿");
        map.put("name2", "莱布尼茨");
        map.put("name3", "洛必达");
        map.put("name4", "欧拉");
        map.put("name5", "高斯");

        operations.multiSet(map);
    }

    /**
     * append(key, value)       追加
     */
    @Test
    public void testAppend() {
        operations.append("name", "8080");
    }

    /**
     * get(key)                 获取键值
     * get(key, start, end)     获取键值并截取[start, end]
     */
    @Test
    public void testGet() {
        String value = operations.get("name");
        String value1 = operations.get("name", 2, 5);
        System.out.println(value);
        System.out.println(value1);
    }

    /**
     * 批量获取键值
     * multiGet(collection)
     */
    @Test
    public void testMultiGet() {
        List<String> list = Stream.of("name", "name1", "name2", "name3")
                                  .collect(Collectors.toList());
        List<String> values = operations.multiGet(list);
        Optional.ofNullable(values).ifPresent(v -> v.forEach(System.out::println));
    }

    /**
     * increment(key)           自增1
     * increment(key, delta)    自增指定大小
     * decrement(key)           自减1
     * decrement(key, delta)    自减指定大小
     */
    @Test
    public void testIncrement() {
        operations.set("age", "18");
        operations.increment("age");  // 自增1
        operations.increment("age", 5);
        System.out.println(operations.get("age"));
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        redisTemplate.delete("name");

        List<String> keys = Stream.of("name1", "name2", "name3").collect(Collectors.toList());

        redisTemplate.delete(keys);
    }








}