package cn.element.data.test.jpa;

import cn.element.data.jpa.dao.ArticleDao;
import cn.element.data.jpa.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void testDelete() {
        articleDao.deleteById(1);
    }





}
