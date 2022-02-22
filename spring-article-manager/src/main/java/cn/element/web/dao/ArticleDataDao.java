package cn.element.web.dao;

import cn.element.web.pojo.ArticleData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleDataDao extends JpaRepository<ArticleData, Integer>,
                                        JpaSpecificationExecutor<ArticleData> {

    /**
     * 根据aId修改ArticleData中的content
     * JPA规定如果想使用JPQL进行更新或者删除操作,必须使用@Modifying注解
     */
    @Transactional
    @Modifying
    @Query("update ArticleData as a set a.content = ?1 where a.article.aId = ?2")
    void updateContentByAId(String content, Integer aId);

    @Transactional
    @Modifying
    @Query("delete from ArticleData as a where a.article.aId = ?1")
    void deleteByAId(Integer aId);
}
