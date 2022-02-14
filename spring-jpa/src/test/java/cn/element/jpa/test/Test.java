package cn.element.jpa.test;

import cn.element.jpa.pojo.Article;
import cn.element.jpa.pojo.Comment;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
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
    }

}
