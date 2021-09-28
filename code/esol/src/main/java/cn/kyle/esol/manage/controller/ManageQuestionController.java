package cn.kyle.esol.manage.controller;

import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.manage.model.ManageExmQuestionLib;
import cn.kyle.esol.manage.service.ManageQuestionService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 管理员用户相关控制器
 * @author Kyle
 */
@RestController
@RequestMapping("/manage/question")
public class ManageQuestionController {
    private final
    ManageQuestionService questionService;

    @Autowired
    public ManageQuestionController(ManageQuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/list")
    public Message list(@RequestParam(required = false) String title,
                        @RequestParam(required = false, defaultValue = "0") Integer kind,
                        @RequestParam(defaultValue = "1") Integer pageIndex,
                        @RequestParam(defaultValue = "20") Integer pageSize,
                        HttpSession session) {
        return questionService.list(title, kind, pageIndex, pageSize, session);
    }
    @PostMapping("/delete")
    public Message delete(@RequestBody JSONArray ids) {
        return questionService.delete(ids.toJavaList(Integer.TYPE));
    }
    @PostMapping("/save")
    public Message save(@RequestBody ManageExmQuestionLib question) {
        return questionService.save(question);
    }
}
