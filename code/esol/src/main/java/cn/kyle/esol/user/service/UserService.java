package cn.kyle.esol.user.service;

import cn.kyle.esol.base.model.constant.VaildMessage;
import cn.kyle.esol.base.model.dto.Message;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

/**
 * 用户相关服务接口
 * @author Kyle
 */
public interface UserService {
  /**
 * 用户登录逻辑
 * @param userName 用户名(不能为空)
 * @param passWord 用户密码(不能为空)
 * @param session 当前HttpSession
 * @return 返回是否允许登录
 */
    Message login(@NotBlank(message = VaildMessage.VAILD_LOGIN_USERNAME_BLANK) String userName,
                  @NotBlank(message = VaildMessage.VAILD_LOGIN_PASSWORD_BLANK) String passWord,
                  HttpSession session);

    /**
     * 获取当前登录用户信息
     * @param session 当前HttpSession
     * @return 返回用户信息数据传输对象
     */
    Message getUserInfo(HttpSession session);
}
