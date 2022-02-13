package cn.element.es.test;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestESClient {

    @Autowired
    RestHighLevelClient esClient;

    /**
     * 测试获取数据
     */
    @Test
    public void testGet() throws IOException {
        //构建请求
        GetRequest request = new GetRequest("book", "1");

        //执行
        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);

        //获取结果
        System.out.println(response.getIndex());
        System.out.println(response.getVersion());
        System.out.println(response.getSource());
    }

    /**
     * 测试条件查询
     */
    @Test
    public void testConditionQuery() throws IOException {
        GetRequest request = new GetRequest("book", "1");

        //构造参数,想要的字段和排除的字段
        String[] included = new String[]{"name", "description"};
        String[] excluded = Strings.EMPTY_ARRAY;

        FetchSourceContext context = new FetchSourceContext(true, included, excluded);

        request.fetchSourceContext(context);

        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);

        if (response.isExists()) {
            System.out.println(response.getIndex());
            System.out.println(response.getVersion());
            System.out.println(response.getSourceAsString());
            System.out.println(response.getSourceAsBytes());
            System.out.println(response.getSourceAsMap());
        } else {
            System.out.println("nothing");
        }
    }

    /**
     * 测试异步请求查询
     */
    @Test
    public void testAsyncQuery() {
        GetRequest request = new GetRequest("book", "1");

        //创建监听器
        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse response) {
                System.out.println(response.getIndex());
                System.out.println(response.getVersion());
                System.out.println(response.getSource());
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        };

        esClient.getAsync(request, RequestOptions.DEFAULT, listener);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试插入操作
     */
    @Test
    public void testCreate() throws IOException {
        //构建增删改请求对象
        IndexRequest request = new IndexRequest("book");

        //设置id
        request.id("5");

        //=====================构建文档数据========================
        //方式1: String
//        String json = "{\n" +
//                "\"name\": \"Bootstrap开发\",\n" +
//                "\"description\": \"Bootstrap是由Twitter推出的一个前台页面开发\n" +
//                "css框架，是一个非常流行的开发框架，此框架集成了多种页面效果。此开\n" +
//                "发框架包含了大量的CSS、JS程序代码，可以帮助开发者（尤其是不擅长\n" +
//                "css页面开发的程序人员）轻松的实现一个css，不受浏览器限制的精美界\n" +
//                "面css效果。\",\n" +
//                "\"studymodel\": \"201002\",\n" +
//                "\"price\":38.6,\n" +
//                "\"timestamp\":\"2019-08-25 19:11:35\",\n" +
//                "\"pic\":\"group1/M00/00/00/wKhlQFs6RCeAY0pHAAJx5ZjNDEM428\n" +
//                ".jpg\",\n" +
//                "\"tags\": [ \"bootstrap\", \"dev\"]\n" +
//                "}";
//
//        request.source(json);

        //方式2: 使用Map
        Map<String, Object> map = new HashMap<String, Object>() {{
           put("name", "Netty4核心原理");
           put("description", "讲述了Netty4的核心原理");
           put("tags", new String[]{"Netty", "Socket"});
        }};

        request.source(map);

        //方法3: 使用XContentBuilder
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject();
//        {
//            builder.field("name", "Mybatis技术内幕")
//                    .field("description", "Mybatis底层原理")
//                    .field("tags", new String[]{"mybatis", "spring"});
//
//        }
//        builder.endObject();
//
//        request.source(builder);

        //方法4: 直接使用source(field, value)方法 属性多不推荐
//        request.source("", "");

        //======================可选参数=========================
        request.timeout("1s");
//        request.timeout(TimeValue.timeValueSeconds(1));

        //手动维护版本号
        request.version(2);
        request.versionType(VersionType.EXTERNAL);

        //执行
        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);

        //获取结果
        System.out.println(response.getIndex());
        System.out.println(response.getId());
        System.out.println(response.getResult());

        if (response.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("CREATED: " + response.getResult());
        } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("UPDATED: " + response.getResult());
        } else {
            System.out.println("nothing!");
        }

        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();

        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("处理成功的分片数少于总分片!");
        }

        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println(reason);  //把每一个错误打印出来
            }
        }
    }

    /**
     * 测试更新数据
     */
    @Test
    public void testUpdate() throws IOException {
        UpdateRequest request = new UpdateRequest("book", "4");

        Map<String, Object> map = new HashMap<String, Object>(){{
           put("name", "JavaScript权威指南");
        }};
        request.doc(map);

        //========================可选参数=============================
        request.timeout("1s");
        request.retryOnConflict(3);  //重试次数

        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);

        System.out.println(response.getId());
        System.out.println(response.getIndex());

        if (response.getResult() == DocWriteResponse.Result.CREATED) {
            DocWriteResponse.Result result = response.getResult();
            System.out.println("CREATED: " + result);
        } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
            DocWriteResponse.Result result = response.getResult();
            System.out.println("UPDATED: " + result);
        } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
            DocWriteResponse.Result result = response.getResult();
            System.out.println("DELETED: " + result);
        } else if (response.getResult() == DocWriteResponse.Result.NOOP) {
            DocWriteResponse.Result result = response.getResult();
            System.out.println("NOOP: " + result);
        }
    }

    /**
     * 测试删除方法
     */
    @Test
    public void testDelete() throws IOException {
        DeleteRequest request = new DeleteRequest("book", "5");

        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);

        System.out.println(response.getId());
        System.out.println(response.getIndex());

        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);
    }

    /**
     * 测试批量操作
     */
    @Test
    public void testBulk() throws IOException {
        BulkRequest request = new BulkRequest();

        //添加操作
        request.add(new IndexRequest("demo").id("6").source(XContentType.JSON, "field", "1"));
        request.add(new IndexRequest("demo").id("7").source(XContentType.JSON, "field", "1"));

        //更新删除操作
//        request.add(new UpdateRequest("demo", "6").doc(XContentType.JSON, "field", "3"));
//        request.add(new DeleteRequest("demo").id("7"));

        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);

        for (BulkItemResponse itemResponse : response) {
            DocWriteResponse writeResponse = itemResponse.getResponse();

            switch (itemResponse.getOpType()) {
                case INDEX:
                    IndexResponse indexResponse = (IndexResponse) writeResponse;
                    System.out.println("INDEX: " + indexResponse.getResult());
                    break;
                case CREATE:
                    IndexResponse createResponse = (IndexResponse) writeResponse;
                    System.out.println("CREATE: " + createResponse.getResult());
                    break;
                case DELETE:
                    IndexResponse deleteResponse = (IndexResponse) writeResponse;
                    System.out.println("DELETE: " + deleteResponse.getResult());
                    break;
                case UPDATE:
                    IndexResponse updateResponse = (IndexResponse) writeResponse;
                    System.out.println("UPDATE: " + updateResponse.getResult());
                    break;
            }
        }
    }




}
