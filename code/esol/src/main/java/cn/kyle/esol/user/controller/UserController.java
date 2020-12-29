package cn.kyle.esol.user.controller;

import cn.kyle.esol.base.controller.ApiBaseController;
import cn.kyle.esol.base.model.constant.SessionKeys;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 用户相关控制器
 * @author Kyle
 */
@RestController
public class UserController implements ApiBaseController {
    private final
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录请求响应
     * @param userName 用户名
     * @param passWord 用户密码
     * @param session HttpSession
     */
    @PostMapping("/login")
    public Message login(@RequestParam("username") String userName,
                         @RequestParam("password") String passWord,
                         HttpSession session){
        return userService.login(userName, passWord, session);
    }

    /**
     * 注销
     * @param session HttpSession
     */
    @PostMapping("/logout")
    public Message logout(HttpSession session){
        session.removeAttribute(SessionKeys.USER_INFO);
        session.removeAttribute(SessionKeys.EXAM_TEST_PAPER);
        session.invalidate();
        return Message.success();
    }
    /**
     * 获取当前登录用户信息
     * @param session HttpSession
     */
    @GetMapping("/user")
    public Message userInfo(HttpSession session){
        return userService.getUserInfo(session);
    }
}
