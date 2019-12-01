package cn.lcsz.adam.pbems.user.controller;

import cn.lcsz.adam.pbems.base.controller.BaseController;
import cn.lcsz.adam.pbems.base.model.constant.SessionKeys;
import cn.lcsz.adam.pbems.base.model.dto.Message;
import cn.lcsz.adam.pbems.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 用户相关控制器
 * @author yufs
 */
@RestController
public class UserController implements BaseController {
    private final
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录请求响应
     * @param userName 用户名
     * @param tel 用户电话号码
     * @param session HttpSession
     */
    @PostMapping("/login")
    public Message login(String userName, String tel, HttpSession session){
        return userService.login(userName, tel, session);
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
