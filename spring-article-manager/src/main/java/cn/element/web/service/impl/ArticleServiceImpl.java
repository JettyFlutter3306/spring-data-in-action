package cn.element.web.service.impl;

import cn.element.web.dao.ArticleDao;
import cn.element.web.dao.ArticleDataDao;
import cn.element.web.es.EsArticle;
import cn.element.web.es.EsArticleDao;
import cn.element.web.mongo.Comment;
import cn.element.web.mongo.CommentDao;
import cn.element.web.pojo.Article;
import cn.element.web.service.ArticleService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleDataDao articleDataDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EsArticleDao esArticleDao;

    @Autowired
    private CommentDao commentDao;

    /**
     * 先想Mysql保存文章和文章详情
     * 清空Redis缓存
     * 向ES中保存数据
     */
    @Override
    public Article saveArticle(Article article) {
        articleDataDao.save(article.getArticleData());
        articleDao.save(article);

        redisTemplate.delete("articles");

        EsArticle esArticle = new EsArticle();
        esArticle.setCreateTime(article.getCreateTime())
                 .setContent(article.getArticleData().getContent())
                 .setTitle(article.getTitle())
                 .setAuthor(article.getAuthor())
                 .setId(article.getAId());

        esArticleDao.save(esArticle);

        return article;
    }

    /**
     * 更新Article
     * 更新ArticleData
     * 清空Redis缓存
     * 向ES中保存数据
     */
    @Override
    public Article updateArticle(Article article) {
        articleDao.save(article);
        articleDataDao.updateContentByAId(article.getArticleData().getContent(), article.getAId());

        redisTemplate.delete("articles");

        EsArticle esArticle = new EsArticle();
        esArticle.setCreateTime(article.getCreateTime())
                 .setContent(article.getArticleData().getContent())
                 .setTitle(article.getTitle())
                 .setAuthor(article.getAuthor())
                 .setId(article.getAId());

        esArticleDao.save(esArticle);
        return article;
    }

    /**
     * 删除articleData
     * 删除article
     * 删除mongodb中相关的评论
     * 清空redis缓存
     * 清除es数据
     */
    @Override
    public void deleteByAId(Integer aId) {
        articleDataDao.deleteByAId(aId);
        articleDao.deleteById(aId);

        // 先根据aId查询到一个Comment列表
        List<Comment> comments = commentDao.findByaId(aId);
        commentDao.deleteAll(comments);

        redisTemplate.delete("articles");

        esArticleDao.deleteById(aId);
    }

    /**
     * 先从Redis中获取
     * 若没有再从Mysql中获取
     */
    @Override
    public List<Article> findLatestArticles() {
        String value = redisTemplate.opsForValue()
                                    .get("articles");

        if (StrUtil.isEmpty(value)) {
            Sort sort = Sort.by(Sort.Order.desc("createTime"));
            Pageable pageable = PageRequest.of(0, 10, sort);
            Page<Article> page = articleDao.findAll(pageable);
            List<Article> list = page.getContent();

            if (!CollectionUtils.isEmpty(list)) {
                value = JSONUtil.toJsonStr(list);
                redisTemplate.opsForValue().set("articles", value);
            }
        }

        return JSONUtil.parseArray(value).toList(Article.class);
    }


}
