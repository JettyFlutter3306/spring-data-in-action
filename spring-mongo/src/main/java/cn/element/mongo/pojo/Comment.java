package cn.element.mongo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(collection = "comment")
//@CompoundIndex(def = "{'userId: 1', 'nickName': -1}")  // 复合索引
public class Comment implements Serializable {

    /**
     * 主键标识,该属性的值会自动对应mongodb的主键字段"_id",如果该属性名
     * 就叫"id",则该注解可以省略,否则必须写
     */
    @Id
    private String id;

    /**
     * 内容
     */
    @Field("content")
    private String content;

    /**
     * 发布日期
     */
    private Date publishTime;

    /**
     * 添加一个单字段索引
     */
    @Indexed
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 评论日期时间
     */
    private LocalDateTime createTime;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 回复数
     */
    private Integer replyNum;

    /**
     * 状态
     */
    private String state;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 文章id
     */
    private String articleId;



}
