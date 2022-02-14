package cn.element.jpa.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "tb_type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(
        // 代表中间表名称
        name = "inner_type_article",
        // 中间表的外键对应到当前表的主键名称
        joinColumns = {@JoinColumn(name = "tid", referencedColumnName = "id")},
        // 中间表的外键对应到的对方表的主键名称
        inverseJoinColumns = {@JoinColumn(name = "aid", referencedColumnName = "id")}
    )
    private Set<Article> articles;

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
