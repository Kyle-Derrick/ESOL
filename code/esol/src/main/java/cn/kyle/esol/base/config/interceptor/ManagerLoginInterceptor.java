package cn.kyle.esol.base.config.interceptor;

import cn.kyle.esol.base.exception.CodeMessageException;
import cn.kyle.esol.base.model.constant.ReturnMessage;
import cn.kyle.esol.base.model.constant.SessionKeys;
import cn.kyle.esol.repository.user.model.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理端登录认证拦截器
 * @author Kyle
 */
@Slf4j
@Component
public class ManagerLoginInterceptor implements BaseInterceptor {
    private static final String INTERCEPTOR_PATH = "/manage/**";
    private static final String IDENT = "manage";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        User user = (User) request.getSession().getAttribute(SessionKeys.MANAGE_USER_INFO);
        if (StringUtils.isEmpty(user)) {
            log.error(ReturnMessage.NOT_LOGIN+request.getRequestURI());
            throw new CodeMessageException("未登录", 403, null);
        }
        return true;
    }

    @Override
    public InterceptorRegistration addInterceptor(InterceptorRegistry registration) {
        return registration
                .addInterceptor(this)
                .addPathPatterns(INTERCEPTOR_PATH);
    }

    @Override
    public String getIdent() {
        return IDENT;
    }
}
