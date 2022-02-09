package cn.element.data.mongo.service;

import cn.element.data.mongo.dao.CommentDao;
import cn.element.data.mongo.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存评论
     */
    public void saveComment(Comment comment) {
        // 如果需要自定义主键,可以在这里指定主键,如果不指定主键,Mongo会自动生成主键
        // 设置一些默认初始值
        commentDao.save(comment);
    }

    /**
     * 批量插入
     */
    public void saveCommentBatch(Collection<Comment> comments) {
        commentDao.saveAll(comments);
    }

    /**
     * 更新评论
     */
    public void updateComment(Comment comment) {
        commentDao.save(comment);
    }

    /**
     * 根据id删除评论
     */
    public void deleteComment(String id) {
        commentDao.deleteById(id);
    }

    /**
     * 根据id查询评论
     */
    public Comment selectComment(String id) {
        return commentDao.findById(id).orElse(null);
    }

    /**
     * 查询列表
     */
    public List<Comment> selectList() {
        return commentDao.findAll();
    }

    /**
     * 分页查询
     */
    public Page<Comment> findCommentListByParentId(String parentId, int pageNum, int pageSize) {
        return commentDao.findByParentId(parentId, PageRequest.of(pageNum - 1, pageSize));
    }

    /**
     * 实现点赞数+1
     */
    public void updateCommentLikeNum(String id) {
        // 查询条件
        Query query = Query.query(Criteria.where("_id").is(id));

        // 更新条件
        Update update = new Update();
        update.inc("likeNum");

        mongoTemplate.updateFirst(query, update, Comment.class);
    }

}
