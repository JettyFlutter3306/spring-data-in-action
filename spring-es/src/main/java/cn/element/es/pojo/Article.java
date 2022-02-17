package cn.element.es.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "in-article")
public class Article {

    @Id
    @Field(index = false, type = FieldType.Integer)
    private Integer id;

    /**
     * index:           是否设置分词,默认true
     * analyzer:        存储时使用的分词器
     * searchAnalyzer:  搜索时使用的分词器
     * store:           是否存储,默认false
     * type:            数据存储,默认FieldType.Auto
     */
    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", store = true, type = FieldType.Text)
    private String title;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", store = true, type = FieldType.Text)
    private String content;

    @Field(store = true, type = FieldType.Integer)
    private Integer hits;

}
