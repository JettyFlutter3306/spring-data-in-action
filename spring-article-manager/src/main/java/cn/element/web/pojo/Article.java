package cn.element.web.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "tb_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aId;

    private String title;

    private String author;

    private Date createTime;

    @OneToOne(mappedBy = "article")
    @Transient
    private ArticleData articleData;

    @Override
    public String toString() {
        return "Article{" +
                "aId=" + aId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
