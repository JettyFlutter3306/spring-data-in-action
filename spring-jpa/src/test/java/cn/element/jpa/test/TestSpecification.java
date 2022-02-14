package cn.element.jpa.test;

import cn.element.jpa.dao.ArticleDao;
import cn.element.jpa.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpecification {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 按照标题和作者进行查询,以不为空的属性作为查询条件
     */
    @Test
    public void testSpecification() {
        // 模拟从外面传入的变量
        String title = "";
        String author = "洛必达";

        // 分页和排序
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("id")));

        Page<Article> articles = articleDao.findAll(new Specification<Article>() {
            /**
             *
             * @param root              代表实体对象,我们可以通过它获取属性值
             * @param criteriaQuery     用于生成SQL语句
             * @param builder   用于拼接查询
             */
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(title)) {
                    // 拼接作为查询条件
                    Predicate predicate = builder.equal(root.get("title").as(String.class), title);
                    list.add(predicate);
                }

                if (!StringUtils.isEmpty(author)) {
                    Predicate predicate = builder.equal(root.get("author").as(String.class), author);
                    list.add(predicate);
                }

                return builder.and(list.toArray(new Predicate[]{}));
            }
        }, pageable);

        articles.getContent().forEach(System.out::println);
    }

    /**
     * 测试动态分页查询
     */
    @Test
    public void testSpecificationPage() {

    }
}