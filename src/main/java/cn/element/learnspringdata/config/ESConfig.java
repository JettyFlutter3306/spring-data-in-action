package cn.element.learnspringdata.config;

import cn.element.learnspringdata.property.ESProperty;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = ESProperty.class)
public class ESConfig {

    @Autowired
    private ESProperty esProperty;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        String hostString = esProperty.getHosts();

        String[] splits = hostString.split(",");
        HttpHost[] httpHosts = new HttpHost[splits.length];

        for (int i = 0; i < splits.length; i++) {
            String[] temp = splits[i].split(":");

            httpHosts[i] = new HttpHost(temp[0], Integer.parseInt(temp[1]), "http");
        }

        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
