package cn.element.es.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "goods", indexStoreType = "docs", shards = 1, replicas = 0)
public class Item {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;  //标题

    @Field(type = FieldType.Keyword)
    private String category;  //分类

    @Field(type = FieldType.Keyword)
    private String brand;  //品牌

    @Field(type = FieldType.Double)
    private Double price;  //价格

    @Field(type = FieldType.Keyword, index = false)
    private String images;  //图片地址字符串



}
