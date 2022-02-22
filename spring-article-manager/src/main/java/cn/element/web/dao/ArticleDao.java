package cn.element.web.dao;

import cn.element.web.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleDao extends JpaRepository<Article, Integer>,
                                    JpaSpecificationExecutor<Article> {

}
