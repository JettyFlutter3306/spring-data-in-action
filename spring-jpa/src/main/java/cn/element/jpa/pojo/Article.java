package cn.element.jpa.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity     // 表示这是一个实体类
@Table(name = "tb_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String author;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 声明类间关系
     * 在类中使用注解再声明表间关系
     * 声明主动放弃关系维护
     * mappedBy:        当前类在对方类中的属性名
     * cascade:         选择级联操作
     */
    @OneToOne(mappedBy = "article", cascade = CascadeType.PERSIST)
    private ArticleData articleData;

    @OneToMany(mappedBy = "article")
    private Set<Comment> comments = new HashSet<>(0);

    @ManyToMany(mappedBy = "articles")
    private Set<Type> types = new HashSet<>(0);

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
