package cn.element.jpa.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "tb_article_data")
public class ArticleData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    /**
     * 让这个实体来维护这个关系
     * name:                    当前表中的外键名
     * referencedColumnName:    指向的对方表中的主键名
     */
    @OneToOne
    @JoinColumn(name = "articleId", referencedColumnName = "id", unique = true)
    private Article article;

    @Override
    public String toString() {
        return "ArticleData{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
