package cn.element.es.dao;

import cn.element.es.pojo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDao extends ElasticsearchRepository<Article, Integer> {

}
