package cn.element.mongo.test;

import cn.element.mongo.dao.ArticleDao;
import cn.element.mongo.pojo.Article;
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
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMongoArticle {

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void testCreateMockData() {
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setId(i)
                   .setTitle("来了来了" + i)
                   .setContent("我霍啸林文韬武略,天下第一!" + i)
                   .setHits(100 + i);

            list.add(article);
        }

        articleDao.saveAll(list);
    }

    @Test
    public void testSave() {
        Article article = new Article();
        article.setId(1)
               .setTitle("来了来了")
               .setContent("我霍啸林文韬武略,天下第一!")
               .setHits(100);

        articleDao.save(article);
    }

    @Test
    public void testUpdate() {
        Article article = new Article();
        article.setId(1)
               .setTitle("来了来了")
               .setContent("我霍啸林文韬武略,二逼卵子!")
               .setHits(10000);

        articleDao.save(article);
    }

    @Test
    public void testDelete() {
        articleDao.deleteById(1);
    }

    @Test
    public void testFind() {
        articleDao.findAll().forEach(System.out::println);
    }

    @Test
    public void testFindById() {
        Article article = articleDao.findById(1).orElse(null);
        System.out.println(article);
    }

    @Test
    public void testFindWithPage() {
        Sort sort = Sort.by(Sort.Order.desc("hits"));
        Pageable pageable = PageRequest.of(1, 3, sort);
        Page<Article> page = articleDao.findAll(pageable);
        page.get().forEach(System.out::println);
    }

    @Test
    public void testFindByTitle() {
        List<Article> list = articleDao.findByTitleLike("来了来了");
        list.forEach(System.out::println);
    }

    @Test
    public void testFindBetween() {
        List<Article> list = articleDao.findByHitsIsBetween(100, 105);
        list.forEach(System.out::println);
    }
}