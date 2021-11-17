package cn.element.learnspringdata.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "system.elasticsearch")
public class ESProperty {

    private String hosts;

}
