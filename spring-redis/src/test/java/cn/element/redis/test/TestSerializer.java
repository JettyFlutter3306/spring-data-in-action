package cn.element.redis.test;

import cn.element.redis.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSerializer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testSerialization() {
        Article article = new Article();
        article.setAuthor("洛必达")
               .setTitle("论洛必达法则的原理")
               .setCreateTime(new Date());

        redisTemplate.opsForValue()
                     .set("article", article);
    }
}