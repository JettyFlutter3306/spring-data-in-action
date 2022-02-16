package cn.element.redis.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSet {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private SetOperations<String, Object> operations;

    @Before
    public void init() {
        operations = redisTemplate.opsForSet();
    }

    /**
     * add(key, ...values)          插入
     */
    @Test
    public void testAdd() {
        operations.add("students", "张三", "李四", "王五", "张三");
    }

    /**
     * members(key)         查询所有元素
     */
    @Test
    public void testMembers() {
        Set<Object> students = operations.members("students");
        assert students != null;
        students.forEach(System.out::println);
    }

    /**
     * randomMember(key)            随机获取一个元素
     * randomMembers(key, count)    随机获取count个数的元素,可能会重复
     */
    @Test
    public void testRandomMember() {
        Object o = operations.randomMember("students");
        System.out.println(o);

        List<Object> list = operations.randomMembers("students", 2);
        assert list != null;
        list.forEach(System.out::println);
    }

    /**
     * remove(key, ...values)        批量删除元素,返回移除成功元素的个数
     * pop(key)                      随机移除1个元素,返回移除的元素
     * pop(key, count)               随机移除count个数的元素
     */
    @Test
    public void testRemove() {
        Long count = operations.remove("students", "张三", "李四", "洛必达");
        System.out.println(count);

        Object o = operations.pop("students");
        System.out.println(o);
    }

    /**
     * intersect(key1, key2)            取交集
     * union(key1, key2)                取并集
     * difference(key1, key2)           取差集
     */
    @Test
    public void testMoreSet() {
        operations.add("names1", "张三", "李四", "王五", "牛顿", "莱布尼茨");
        operations.add("names2", "张三", "李四", "王五", "洛必达", "阿基米德");

        Set<Object> set1 = operations.intersect("names1", "names2");
        System.out.println(set1);

        Set<Object> set2 = operations.union("names1", "names2");
        System.out.println(set2);

        Set<Object> set3 = operations.difference("names1", "names2");
        System.out.println(set3);
    }
}