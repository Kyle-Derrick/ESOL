package cn.lcsz.adam.pbems.base.config.interceptor;

import cn.lcsz.adam.pbems.base.model.constant.ReturnMessage;
import cn.lcsz.adam.pbems.base.model.constant.SessionKeys;
import cn.lcsz.adam.pbems.base.exception.CodeMessageException;
import cn.lcsz.adam.pbems.user.model.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 简单登录认证拦截器
 * @author yufs
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        User user = (User) request.getSession().getAttribute(SessionKeys.USER_INFO);
        if (StringUtils.isEmpty(user)) {
            log.error(ReturnMessage.NOT_LOGIN+request.getRequestURI());
            throw new CodeMessageException("未登录", 403, null);
        }
        return true;
    }
}
