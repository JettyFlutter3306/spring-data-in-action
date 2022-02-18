package cn.element.es.dao;

import cn.element.es.pojo.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleDao extends ElasticsearchRepository<Article, Integer> {

    /**
     * 根据文章标题查询
     */
    List<Article> findByTitle(String title, Pageable pageable);





}
