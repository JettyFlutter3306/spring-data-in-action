package cn.element.web.test;

import cn.element.web.mongo.Comment;
import cn.element.web.pojo.Article;
import cn.element.web.pojo.ArticleData;
import cn.element.web.service.ArticleService;
import cn.element.web.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestArticle {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Test
    public void testSaveArticle() {
        ArticleData articleData = new ArticleData();
        articleData.setId(null)
                   .setContent("洛必达法则是天下最牛逼的法则!");

        Article article = new Article();
        article.setAId(null)
               .setTitle("洛必达介绍")
               .setAuthor("洛必达")
               .setCreateTime(new Date());

        article.setArticleData(articleData);
        articleData.setArticle(article);

        Article temp = articleService.saveArticle(article);
        System.out.println(temp);
    }

    @Test
    public void testUpdateArticle() {
        ArticleData articleData = new ArticleData();
        articleData.setId(3)
                   .setContent("洛必达法则!");

        Article article = new Article();
        article.setAId(1)
               .setTitle("洛必达介绍")
               .setAuthor("洛必达")
               .setCreateTime(new Date());

        article.setArticleData(articleData);
        articleData.setArticle(article);

        Article temp = articleService.updateArticle(article);
        System.out.println(temp);
    }

    @Test
    public void testDeleteArticle() {
        articleService.deleteByAId(3);
    }

    @Test
    public void testInsertComment() {
        Comment comment = new Comment();
        comment.setCId(UUID.randomUUID().toString())
               .setAId(2)
               .setComment("文韬武略,天下第一!")
               .setNickName("洛必达");

        commentService.saveComment(comment);
    }

    @Test
    public void testDeleteComment() {
        commentService.deleteByCid("29122219-8e87-4f91-adb9-7607b6fe9416");
    }

    @Test
    public void testLatestArticle() {
        List<Article> list = articleService.findLatestArticles();
        list.forEach(System.out::println);
    }


}