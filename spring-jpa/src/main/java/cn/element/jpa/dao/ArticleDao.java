package cn.element.jpa.dao;

import cn.element.jpa.pojo.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ArticleDao extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {

    /**
     * 根据标题查询
     */
    List<Article> findByTitle(String title);

    /**
     * 根据标题模糊查询
     */
    List<Article> findByTitleLike(String title);

//    /**
//     * 根据标题和作者查询
//     */
//    List<Article> findByTitleAndAuthor(String title, String author);
//
//    /**
//     * 根据id范围查询
//     */
//    List<Article> findByIdBetween(Integer start, Integer end);
//
//    /**
//     * 查询小于id
//     */
//    List<Article> findByIdLessThan(Integer id);
//
//    /**
//     * 查询id在列表内的实体
//     */
//    List<Article> findByIdIn(List<Integer> list);
//
//    /**
//     * 根据创作时间之后查询
//     */
//    List<Article> findByCreateTimeAfter(Date createTime);

    @Query("from Article as a where a.title = ?1 and a.author = ?2")
    List<Article> findByCondition1(String title, String author);

    @Query("from Article as a where a.title = :title and a.author = :author")
    List<Article> findByCondition2(@Param("title") String title, @Param("author") String author);

    @Query("from Article as a where a.title like %:title% order by a.id desc")
    List<Article> findByCondition3(@Param("title") String title);

    @Query("from Article as a where a.title like %:title%")
    List<Article> findByCondition4(Pageable pageable, @Param("title") String title);

    @Query("from Article as a where a.id in :list")
    List<Article> findByCondition5(@Param("list") List<Integer> list);

    @Query("from Article as a where a.title = :#{#article.title} and a.author = :#{#article.author}")
    List<Article> findByCondition6(@Param("article") Article article);


}
