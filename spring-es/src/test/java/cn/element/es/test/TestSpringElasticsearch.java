package cn.element.es.test;

import cn.element.es.dao.ArticleDao;
import cn.element.es.pojo.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringElasticsearch {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Autowired
    private ArticleDao articleDao;

    /**
     * 创建索引
     */
    @Test
    public void testCreateIndex() {

    }

    @Test
    public void testSave() {
        Article article = new Article();
        article.setId(1)
               .setTitle("Spring Cloud")
               .setContent("老子文韬武略天下第一!");

        articleDao.save(article);
    }
}

