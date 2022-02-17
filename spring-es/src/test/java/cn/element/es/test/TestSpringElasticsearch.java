package cn.element.es.test;

import cn.element.es.dao.ArticleDao;
import cn.element.es.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringElasticsearch {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void testCreateMockData() {
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setId(i)
                   .setTitle("Spring Cloud" + i)
                   .setContent("老子文韬武略天下第一!" + i)
                   .setHits(100 + i);

            list.add(article);
        }

        articleDao.saveAll(list);
    }

    /**
     * 创建索引,默认自动创建,不需要手动创建
     */
    @Test
    public void testSave() {
        Article article = new Article();
        article.setId(1)
               .setTitle("Spring Cloud")
               .setContent("老子文韬武略天下第一!");

        articleDao.save(article);
    }

    /**
     * 存在就是修改,不存在就是插入
     */
    @Test
    public void testUpdate() {
        Article article = new Article();
        article.setId(1)
               .setTitle("勇敢的心")
               .setContent("我霍啸林文韬武略天下第一!");

        articleDao.save(article);
    }

    @Test
    public void testDelete() {
        articleDao.deleteById(1);
    }

    /**
     * 查询所有并且分页
     */
    @Test
    public void testFindAllWithPage() {
        Pageable pageable = PageRequest.of(1, 3);
        Page<Article> page = articleDao.findAll(pageable);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        page.getContent().forEach(System.out::println);
    }

    @Test
    public void testFindById() {
        Article article = articleDao.findById(0).orElse(null);
        System.out.println(article);
    }

    @Test
    public void testFindAllSort() {
        Sort sort = Sort.by(Sort.Order.desc("hits"));
        Iterable<Article> articles = articleDao.findAll(sort);
        articles.forEach(System.out::println);
    }




}

