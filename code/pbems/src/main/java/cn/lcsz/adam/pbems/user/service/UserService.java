package cn.lcsz.adam.pbems.user.service;

import cn.lcsz.adam.pbems.base.model.constant.VaildMessage;
import cn.lcsz.adam.pbems.base.model.dto.Message;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

/**
 * 用户相关服务接口
 * @author yufs
 */
public interface UserService {
  /**
 * 用户登录逻辑
 * @param userName 用户名(不能为空)
 * @param tel 用户电话号码(不能为空)
 * @param session 当前HttpSession
 * @return 返回是否允许登录
 */
    Message login(@NotBlank(message = VaildMessage.VAILD_LOGIN_USERNAME_BLANK) String userName,
                  @NotBlank(message = VaildMessage.VAILD_LOGIN_TELPHONE_BLANK) String tel,
                  HttpSession session);

    /**
     * 获取当前登录用户信息
     * @param session 当前HttpSession
     * @return 返回用户信息数据传输对象
     */
    Message getUserInfo(HttpSession session);
}
