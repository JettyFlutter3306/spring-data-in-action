package cn.element.web.service.impl;

import cn.element.web.mongo.Comment;
import cn.element.web.mongo.CommentDao;
import cn.element.web.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Transactional
    @Override
    public void saveComment(Comment comment) {
        commentDao.save(comment);
    }

    @Transactional
    @Override
    public void deleteByCid(String cId) {
        commentDao.deleteById(cId);
    }
}
