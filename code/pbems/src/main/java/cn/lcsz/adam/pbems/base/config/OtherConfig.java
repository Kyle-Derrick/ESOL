package cn.lcsz.adam.pbems.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 其他配置
 * @author yufs
 */
@Configuration
public class OtherConfig {
    /**
     * 注入方法级别校验拦截器
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
