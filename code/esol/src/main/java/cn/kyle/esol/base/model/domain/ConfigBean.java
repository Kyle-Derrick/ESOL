package cn.kyle.esol.base.model.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 配置
 * @author Kyle
 */
@Component
@Data
@Configuration
@PropertySource(value = {"classpath:/application.yml"}, encoding = "utf-8")
@ConfigurationProperties(prefix = "kyle.config")
public class ConfigBean {
    //忽略路徑
    private Map<String, List<String>> ignoreUrls;
    //跨域相关设置
    private String allowedOrigin = "*";
    private String allowedHeader = "*";
    private String allowedMethod = "*";
    private Boolean allowCredentials = true;
    private Long maxAge = 3600L;

}
