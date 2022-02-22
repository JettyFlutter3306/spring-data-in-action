package cn.element.web.service;

import cn.element.web.pojo.Article;

import java.util.List;

public interface ArticleService {

    /**
     * 保存文章
     */
    Article saveArticle(Article article);

    /**
     * 更新文章
     */
    Article updateArticle(Article article);

    /**
     * 删除文章
     */
    void deleteByAId(Integer aId);

    /**
     * 最新文章列表
     */
    List<Article> findLatestArticles();
}
