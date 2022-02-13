package cn.element.jpa.test;

import cn.element.jpa.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

@Slf4j
public class TestJPA {

    /**
     * 测试保存操作
     * Persistence              通过读取持久化单元名称,根据读取名称得到的配置创建持久化管理器工厂
     * EntityTransaction        进行事务管理begin commit rollback
     * EntityManagerFactory     这是一个工厂类,目的是为了创建EntityManager,对于这种工厂类,它的创建和销毁特别耗费资源
     *                          一般在一个项目中为单例
     * EntityManager            基于这个API可以实现数据库的CRUD
     *
     * persist(T)               保存
     * find(Class, T)           查询
     * merge(Class)             修改
     * remove(T)                删除
     */
    @Test
    public void testSave() {
        // 创建实体
        Article article = new Article();
        article.setTitle("测试文章")
               .setAuthor("洛必达")
               .setCreateTime(new Date());

        // 使用JPA将文章保存到数据库
        // 1.创建一个持久化管理器工厂
        String unitName = "myPersistenceUnit";
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(unitName);

        // 2.创建持久化管理器,基于此API可以完成获取事务以及对数据库的CRUD操作
        EntityManager manager = factory.createEntityManager();

        // 3.得到事务,并且开启
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        // 4.操作
        manager.persist(article);

        // 5.提交事务
        transaction.commit();

        // 6.关闭资源
        manager.close();
    }

    /**
     * 测试修改
     */
    @Test
    public void testUpdate() {
        String unitName = "myPersistenceUnit";
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(unitName);
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        Article article = manager.find(Article.class, 2);
        article.setAuthor("牛顿");
        manager.merge(article);
        log.info("{}", article);

        transaction.commit();
        manager.close();
    }

    /**
     * 测试查询
     */
    @Test
    public void testFind() {
        String unitName = "myPersistenceUnit";
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(unitName);
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        Article article = manager.find(Article.class, 1);
        log.info("{}", article);

        transaction.commit();
        manager.close();
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        String unitName = "myPersistenceUnit";
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(unitName);
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        Article article = manager.find(Article.class, 1);
        manager.remove(article);
        log.info("{}", article);

        transaction.commit();
        manager.close();
    }
}
