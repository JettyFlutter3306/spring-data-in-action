package cn.element.mongo.dao;

import cn.element.mongo.pojo.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleDao extends MongoRepository<Article, Integer> {

    /**
     * 根据标题查询
     */
    List<Article> findByTitleLike(String title);

    /**
     * 根据点击量查询 范围: (start, end)
     */
    List<Article> findByHitsIsBetween(Integer start, Integer end);
}
