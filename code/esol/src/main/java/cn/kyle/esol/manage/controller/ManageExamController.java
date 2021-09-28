package cn.kyle.esol.manage.controller;

import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.manage.service.ManageExamService;
import cn.kyle.esol.repository.exam.model.po.ManageExamPaper;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 管理员用户相关控制器
 * @author Kyle
 */
@RestController
@RequestMapping("/manage/exam")
public class ManageExamController {
    private final
    ManageExamService examService;

    @Autowired
    public ManageExamController(ManageExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/list")
    public Message list(@RequestParam(required = false) String title,
                        @RequestParam(defaultValue = "1") Integer pageIndex,
                        @RequestParam(defaultValue = "20") Integer pageSize,
                        HttpSession session) {
        return examService.list(title, pageIndex, pageSize, session);
    }
    @PostMapping("/delete")
    public Message delete(@RequestBody JSONArray ids) {
        return examService.delete(ids.toJavaList(Integer.TYPE));
    }
    @PostMapping("/save")
    public Message save(@RequestBody ManageExamPaper exam) {
        return examService.save(exam);
    }
}
