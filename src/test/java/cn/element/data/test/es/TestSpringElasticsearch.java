package cn.element.data.test.es;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest
public class TestSpringElasticsearch {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Test
    public void test01() {

    }
}

