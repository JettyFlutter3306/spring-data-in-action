package cn.element.jpa.test;

import cn.element.jpa.dao.ArticleDao;
import cn.element.jpa.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringJPA {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 测试保存
     */
    @Test
    public void testSave() {
        Article article = new Article();
        article.setTitle("测试文章")
               .setAuthor("洛必达")
               .setCreateTime(new Date());

        articleDao.save(article);
        log.info("{}", article);
    }

    /**
     * 测试批量插入
     */
    @Test
    public void testSaveBatch() {
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Article article = new Article();
            article.setTitle("测试文章" + i)
                   .setAuthor("程序员" + i)
                   .setCreateTime(new Date());

            list.add(article);
        }

        articleDao.saveAll(list);
    }

    /**
     * 测试单条查询
     */
    @Test
    public void testFindById() {
        Article article = articleDao.findById(1).orElse(null);
        log.info("{}", article);
    }

    /**
     * 测试查询列表
     */
    @Test
    public void testFinAll() {
        List<Article> articleList = articleDao.findAll();
        articleList.forEach(System.out::println);
    }

    /**
     * 测试修改
     * 有主键表示要修改
     * 无主键表示要保存
     */
    @Test
    public void testUpdate() {
        Article article = new Article();
        article.setId(1)
               .setAuthor("欧拉");

        articleDao.save(article);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        articleDao.deleteById(1);
    }

    /**
     * 测试分页查询
     * page  当前页码从0开始计数
     * size  每页大小
     */
    @Test
    public void testFindAllPageRequest() {
        // 按照id降序排序
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(1, 3, sort);
        Page<Article> page = articleDao.findAll(pageable);

        log.info("总记录数: {}", page.getTotalElements());
        log.info("总页数: {}", page.getTotalPages());
        log.info("每页条数: {}", page.getSize());
        log.info("当前页元素: {}", page.getContent());
    }

    /**
     * 根据标题查询
     */
    @Test
    public void testFindByTitle() {
        List<Article> list = articleDao.findByTitle("测试文章0");
        log.info("{}", list);
    }

    @Test
    public void testFindByTitleLike() {
        List<Article> list = articleDao.findByTitleLike("测试文章");
        log.info("{}", list);
    }

    /**
     * JPQL:类似于SQL语句,但是要使用实体类名代替表名,使用属性名称代替字段名称[面向对象查询]
     */
    @Test
    public void testFindCondition1() {
        List<Article> list = articleDao.findByCondition1("测试文章", "洛必达");
        log.info("{}", list);
    }

    @Test
    public void testFindCondition2() {
        List<Article> list = articleDao.findByCondition2("测试文章", "洛必达");
        log.info("{}", list);
    }

    @Test
    public void testFindCondition3() {
        List<Article> list = articleDao.findByCondition3("测试文章");
        log.info("{}", list);
    }

    @Test
    public void testFindPage() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Article> list = articleDao.findByCondition4(pageable, "测试文章");
        log.info("{}", list);
    }

    @Test
    public void testFindCondition5() {
        List<Integer> list = Stream.of(2, 3, 4).collect(Collectors.toList());
        List<Article> articles = articleDao.findByCondition5(list);
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindCondition6() {
        Article article = new Article();
        article.setTitle("测试文章")
               .setAuthor("洛必达");

        List<Article> articles = articleDao.findByCondition6(article);
        articles.forEach(System.out::println);
    }





}
