package cn.kyle.esol.manage.service;

import cn.kyle.esol.base.model.constant.VaildMessage;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.repository.user.model.po.User;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 管理员相关服务接口
 * @author Kyle
 */
public interface ManageUserService {
  /**
 * 管理员用户登录逻辑
 * @param userName 管理员用户名(不能为空)
 * @param passWord 用户密码(不能为空)
 * @param session 当前HttpSession
 * @return 返回是否允许登录
 */
    Message login(@NotBlank(message = VaildMessage.VAILD_LOGIN_USERNAME_BLANK) String userName,
                  @NotBlank(message = VaildMessage.VAILD_LOGIN_PASSWORD_BLANK) String passWord,
                  HttpSession session);

    /**
     * 获取当前登录管理员用户信息
     * @param session 当前HttpSession
     * @return 返回用户信息数据传输对象
     */
    Message getUserInfo(HttpSession session);

    Message list(String username,
                 Integer kind,
                 Integer pageIndex,
                 Integer pageSize,
                 HttpSession session);
    Message get(Integer id);
    Message delete(List<Integer> ids);
    Message save(User user);
}
