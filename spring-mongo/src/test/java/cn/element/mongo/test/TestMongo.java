package cn.element.mongo.test;

import cn.element.mongo.pojo.Comment;
import cn.element.mongo.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMongo {

    @Autowired
    private CommentService commentService;

    /**
     * 测试单条插入
     */
    @Test
    public void testInsert() {
        Comment comment = new Comment();
        comment.setId(null)
               .setContent("你个傻逼玩意儿")
               .setPublishTime(new Date())
               .setUserId("1000")
               .setNickName("洛必达")
               .setCreateTime(LocalDateTime.now())
               .setLikeNum(1000)
               .setReplyNum(500)
               .setState("1")
               .setParentId("0")
               .setArticleId("100");

        commentService.saveComment(comment);
    }

    /**
     * 测试批量插入
     */
    @Test
    public void testInsertBatch() {
        String[] names = new String[]{"洛必达", "牛顿", "莱布尼茨", "伯努利", "欧拉", "高斯", "毕达哥拉斯", "笛卡尔"};
        String[] articleIdArray = new String[]{"100", "101", "102", "103", "104", "105"};
        List<Comment> list = new ArrayList<>();
        Map<String, String> map = new HashMap<String, String>() {{
            put("洛必达", "1000");
            put("牛顿", "1001");
            put("莱布尼茨", "1002");
            put("伯努利", "1003");
            put("欧拉", "1004");
            put("高斯", "1005");
            put("毕达哥拉斯", "1006");
            put("笛卡尔", "1007");
        }};
        Random random = new Random();

        for (int i = 0; i < 150; i++) {
            int nextInt = random.nextInt(8);
            Comment comment = new Comment();
            comment.setId(null)
                   .setContent("你个傻逼玩意儿")
                   .setPublishTime(new Date())
                   .setUserId(map.get(names[nextInt]))
                   .setNickName(names[nextInt])
                   .setCreateTime(LocalDateTime.now())
                   .setLikeNum(random.nextInt(1000))
                   .setReplyNum(random.nextInt(500))
                   .setState("1")
                   .setParentId("0")
                   .setArticleId(articleIdArray[random.nextInt(6)]);

            list.add(comment);
        }

        commentService.saveCommentBatch(list);
    }

    /**
     * 查询列表
     */
    @Test
    public void testFindList() {
        List<Comment> list = commentService.selectList();
        list.forEach(System.out::println);
    }

    /**
     * 单条查询
     */
    @Test
    public void testFindOne() {
        Comment comment = commentService.selectComment("620385749ff0982ac50d363b");
        System.out.println(comment);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        commentService.deleteComment("620385749ff0982ac50d363b");
    }

    @Test
    public void testUpdate() {
        Comment comment = new Comment();
        comment.setId("620385749ff0982ac50d363b")
               .setLikeNum(5000);

        commentService.updateComment(comment);
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testSelectPage() {
        Page<Comment> page = commentService.findCommentListByParentId("620385749ff0982ac50d363b", 1, 3);
        page.get().forEach(System.out::println);
    }

    /**
     * 测试点赞数+1
     */
    @Test
    public void testLikeNumIncrement() {
        commentService.updateCommentLikeNum("620385749ff0982ac50d363b");
    }

}
