package cn.kyle.esol.base.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * 拦截器模板
 * @author Kyle
 */
public interface BaseInterceptor extends HandlerInterceptor {
    /**
     * 设置拦截器拦截的请求地址
     * @param registration 注册器
     * @return 返回注册器（链式方法）
     */
    public InterceptorRegistration addInterceptor(InterceptorRegistry registration);

    /**
     * 获取拦截路径
     * @return 返回拦截路径
     */
    public String getIdent();
}
