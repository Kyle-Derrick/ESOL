package cn.kyle.esol.manage.controller;

import cn.kyle.esol.base.model.constant.SessionKeys;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.manage.service.ManageUserService;
import cn.kyle.esol.repository.user.model.po.User;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * 管理员用户相关控制器
 * @author Kyle
 */
@RestController
@RequestMapping("/manage")
public class ManageUserController {
    private final
    ManageUserService userService;

    @Autowired
    public ManageUserController(ManageUserService userService) {
        this.userService = userService;
    }

    /**
     * 登录请求响应
     * @param userName 管理员用户名
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
     * 获取当前管理员登录用户信息
     * @param session HttpSession
     */
    @GetMapping("/user")
    public Message userInfo(HttpSession session){
        return userService.getUserInfo(session);
    }

    @PostMapping("/user/list")
    public Message list(@RequestParam(required = false) String username,
                        @RequestParam(required = false) Integer kind,
                        @RequestParam(defaultValue = "1") Integer pageIndex,
                        @RequestParam(defaultValue = "20") Integer pageSize,
                        HttpSession session) {
        return userService.list(username, kind, pageIndex, pageSize, session);
    }
    @GetMapping("/user/{id}")
    public Message get(@PathVariable Integer id) {
        return userService.get(id);
    }
    @PostMapping("/user/delete")
    public Message delete(@RequestBody JSONArray ids) {
        return userService.delete(ids.toJavaList(Integer.TYPE));
    }
    @PostMapping("/user/save")
    public Message save(@RequestBody User user) {
        return userService.save(user);
    }
}
