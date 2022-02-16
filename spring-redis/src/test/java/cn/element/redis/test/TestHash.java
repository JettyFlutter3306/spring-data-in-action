package cn.element.redis.test;

import cn.element.redis.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHash {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, Article> operations;

    @Before
    public void init() {
        operations = redisTemplate.opsForHash();
    }

    /**
     * put(key, hashKey, hashValue)         保存单个hash
     * putAll(key, hashMap)                 批量插入
     * putIfAbsent(key, hashKey, value)     不存在就插入
     */
    @Test
    public void testPut() {
        Article article = new Article();
        article.setAuthor("洛必达")
               .setTitle("SpringBoot")
               .setCreateTime(new Date());

        operations.put("article", "2", article);
    }

    /**
     * hasKey(key, hashKey)         判断是否存在
     * get(key, hashKey)            获取哈希键值
     * keys(key)                    获取哈希键集合
     * values(key)                  获取哈希值集合
     * entries(key)                 获取哈希键值集合
     */
    @Test
    public void testGet() {
        Boolean flag = operations.hasKey("article", "2");
        System.out.println(flag);

        Article article = operations.get("article", "1");
        System.out.println(article);

        Set<String> set = operations.keys("article");
        set.forEach(System.out::println);

        List<Article> list = operations.values("article");
        list.forEach(System.out::println);

        Map<String, Article> map = operations.entries("article");
        map.entrySet().forEach(System.out::println);
    }

    /**
     * delete(key, ...hashKeys)             删除hashKey
     * 当hash中的数据为空时,hash会被自动销毁
     */
    @Test
    public void testDeleteHash() {
        operations.delete("article", "1", "2");
    }





}