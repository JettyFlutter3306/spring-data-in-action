package cn.element.jpa.dao;

import cn.element.jpa.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommentDao extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

}
