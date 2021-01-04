package cn.kyle.esol.base.config;

import cn.kyle.esol.base.config.interceptor.BaseInterceptor;
import cn.kyle.esol.base.model.domain.ConfigBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * web相关配置，比如跨域等
 * @author Kyle
 */
@Configuration
@Slf4j
public class WebConfigurer  implements WebMvcConfigurer {
    private final ConfigBean config;

    private final BaseInterceptor[] interceptors;

    public WebConfigurer(BaseInterceptor[] interceptors, ConfigBean config) {
        this.interceptors = interceptors;
        this.config = config;
    }

    private CorsConfiguration corsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
    /*
        请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
    */
        corsConfiguration.addAllowedOrigin(config.getAllowedOrigin());
        corsConfiguration.addAllowedHeader(config.getAllowedHeader());
        corsConfiguration.addAllowedMethod(config.getAllowedMethod());
        corsConfiguration.setAllowCredentials(config.getAllowCredentials());
        corsConfiguration.setMaxAge(config.getMaxAge());
        return corsConfiguration;
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig());
        return new CorsFilter(source);
    }

    /**
     * 配置登录认证拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (BaseInterceptor interceptor : interceptors) {
            InterceptorRegistration interceptorRegistration = interceptor.addInterceptor(registry);
            log.info(interceptor.getIdent());
            List<String> paths = config.getIgnoreUrls().get(interceptor.getIdent());
            for (String path : paths) {
                log.info(path);
                interceptorRegistration.excludePathPatterns(path);
            }
        }
    }
}
