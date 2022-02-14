package cn.element.redis.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestString {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试保存数据
     */
    @Test
    public void testSave() {
        // 获取操作简单字符串类型数据的数据句柄
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        value.set("name", "洛必达");
    }
}