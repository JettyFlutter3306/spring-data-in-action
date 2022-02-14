package cn.element.jpa.test;

import cn.element.jpa.dao.ArticleDao;
import cn.element.jpa.dao.CommentDao;
import cn.element.jpa.dao.TypeDao;
import cn.element.jpa.pojo.Article;
import cn.element.jpa.pojo.ArticleData;
import cn.element.jpa.pojo.Comment;
import cn.element.jpa.pojo.Type;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCorrelatedQuery {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TypeDao typeDao;

    @Test
    public void testSaveArticleData() {
        Article article = new Article();
        article.setTitle("黑马好文章")
               .setAuthor("黑马")
               .setCreateTime(new Date());

        // 创建文章内容对象
        ArticleData articleData = new ArticleData();
        articleData.setContent("这是一片好文章");

        // 建立两个对象间的关系
        article.setArticleData(articleData);
        articleData.setArticle(article);

        // 保存
        articleDao.save(article);
    }

    @Test
    public void testSaveComment() {
        Article article = new Article();
        article.setTitle("黑马好文章")
               .setAuthor("黑马")
               .setCreateTime(new Date());

        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setComment("真不戳!");
        comment2.setComment("很棒!");

        comment1.setArticle(article);
        comment2.setArticle(article);

        Set<Comment> set = new HashSet<>();
        set.add(comment1);
        set.add(comment2);

        article.setComments(set);

        articleDao.save(article);
        commentDao.save(comment1);
        commentDao.save(comment2);
    }

    @Test
    public void testSaveType() {
        Article article1 = new Article();
        article1.setTitle("黑马好文章11")
               .setAuthor("黑马11")
               .setCreateTime(new Date());
        Article article2 = new Article();
        article2.setTitle("黑马好文章22")
               .setAuthor("黑马2")
               .setCreateTime(new Date());

        Type type1 = new Type();
        type1.setName("军事");
        Type type2 = new Type();
        type2.setName("政治");

        Set<Type> set = new HashSet<>();
        set.add(type1);
        set.add(type2);
        article1.setTypes(set);
        article2.setTypes(set);

        Set<Article> set1 = new HashSet<>();
        set1.add(article1);
        set1.add(article2);
        type1.setArticles(set1);
        type2.setArticles(set1);

        articleDao.save(article1);
        articleDao.save(article2);
        typeDao.save(type1);
        typeDao.save(type2);
    }
}