package cn.element.redis.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestZSet {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ZSetOperations<String, Object> operations;

    @Before
    public void init() {
        operations = redisTemplate.opsForZSet();
    }

    /**
     * add(key, value, score)           添加到集合并设置分数
     */
    @Test
    public void testAdd() {
        operations.add("students", "张三", 100);
        operations.add("students", "李四", 80);
        operations.add("students", "王五", 70);
    }

    /**
     * incrementScore(key, value, delta)         给value加delta分,返回现在的分数
     * decrementScore(key, value, delta)         给value减delta分,返回现在的分数
     */
    @Test
    public void testIncrement() {
        Double score = operations.incrementScore("students", "张三", 20);
        System.out.println(score);
    }

    /**
     * score(key, value)        查询一个元素的分数
     * rank(key, value)         查询一个元素的排名,从0开始计数
     */
    @Test
    public void testScore() {
        Double score = operations.score("students", "王五");
        System.out.println(score);

        Long rank = operations.rank("students", "王五");
        System.out.println(rank);
    }

    /**
     * range(key, start, end)               根据排名区间获取元素列表
     * rangeByScore(key, start, end)        根据分数区间获取元素列表
     * rangeWithScores(key, start, end)     根据分数区间获取元素列表,返回值带上分数
     */
    @Test
    public void testRange() {
        Set<Object> set = operations.range("students", 0, 1);
        System.out.println(set);

        Set<ZSetOperations.TypedTuple<Object>> set1 = operations.rangeByScoreWithScores("students", 50, 100);
        assert set1 != null;
        set1.forEach(tuple -> System.out.println(tuple.getValue() + "=" + tuple.getScore()));
    }

    /**
     * zCard(key)               统计元素个数
     * count(key, min, max)     统计区间元素个数
     */
    @Test
    public void testCount() {
        Long card = operations.zCard("students");
        System.out.println(card);

        Long count = operations.count("students", 50, 100);
        System.out.println(count);
    }

    /**
     * remove(key, ...values)               根据key-value删除
     * removeRange(key, 0, 1)               根据排名区间删除元素
     * removeRangeByScore(key, min, max)    根据分数区间删除元素
     */
    @Test
    public void testRemove() {
        Long a = operations.remove("students", "张三", "李四");
        System.out.println(a);
    }

}