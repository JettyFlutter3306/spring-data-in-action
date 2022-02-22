package cn.element.web.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsArticleDao extends ElasticsearchRepository<EsArticle, Integer> {

}
