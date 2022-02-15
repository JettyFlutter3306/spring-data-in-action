package cn.element.redis.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestList {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ListOperations<String, Object> operations;

    @Before
    public void init() {
        operations = redisTemplate.opsForList();
    }

    /**
     * leftPush(key, value)         左插入
     * leftPushAll(key, ...values)  左批量插入
     * rightPush(key, value)        右插入
     * rightPushAll(key, ...values) 右批量插入
     */
    @Test
    public void testPush() {
        operations.leftPush("students", "洛必达");
        operations.rightPush("students", "阿基米德");
    }

    @Test
    public void testPushAll() {
        operations.leftPushAll("students", "牛顿", "莱布尼茨", "欧拉", "高斯");
    }

    /**
     * 0和正数代表从左边数
     * 负数表示从右边数,那么-1就代表从左数最后一个
     * index(key, i)            根据key和索引查找
     * range(key, start, end)   范围查找
     */
    @Test
    public void testIndex() {
        String name = (String) operations.index("students", 2);
        System.out.println(name);

        List<Object> list = operations.range("students", 0, -1);
        Optional.ofNullable(list).ifPresent(v -> v.forEach(System.out::println));
    }

    /**
     * leftPop(key)                 左弹栈
     * rightPop(key)                右弹栈
     * remove(key, count, value)    弹出指定元素
     *                              count > 0, 删除左边起第几个等于指定值的元素
     *                              count < 0, 删除右边起第几个等于指定值的元素
     *                              count = 0, 删除所有等于value的元素
     */
    @Test
    public void testPop() {
        operations.leftPop("students");
    }

    @Test
    public void testRemove() {
        operations.remove("students", 0, "洛必达");
    }
}