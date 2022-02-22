package cn.element.web.service;

import cn.element.web.mongo.Comment;

public interface CommentService {

    void saveComment(Comment comment);

    void deleteByCid(String cId);


}
