package cn.kyle.esol.exam.controller;

import cn.kyle.esol.exam.service.ExamTestService;
import cn.kyle.esol.base.model.constant.SessionKeys;
import cn.kyle.esol.base.model.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 考试相关控制器
 * @author Kyle
 */
@RestController
public class ExamController implements ExamBaseController {
    private final ExamTestService examTestService;

    @Autowired
    public ExamController(ExamTestService examTestService) {
        this.examTestService = examTestService;
    }

    /**
     * 获取考试列表
     * @param session HttpSession
     * @return 返回结果
     */
    @GetMapping
    public Message examList(HttpSession session){
        return examTestService.getExamList(session);
    }

    /**
     * 提交答卷
     * @param answers 答案
     * @param session HttpSession
     * @return 返回成绩结果
     */
    @PostMapping
    public Message examList(@RequestBody Map<Integer, Object> answers, HttpSession session){
        return examTestService.commit(answers, session);
    }

    /**
     * 获取试卷
     * @param examId 试卷编号
     * @param session HttpSession
     * @return 返回试卷
     */
    @GetMapping("/{examId:\\d+}")
    public Message examList(@PathVariable Integer examId, HttpSession session){
        return examTestService.getExam(examId, session);
    }

    /**
     * 退出考试
     * @param session HttpSession
     * @return 返回成功
     */
    @GetMapping("/back")
    public Message examBack(HttpSession session){
        session.removeAttribute(SessionKeys.EXAM_TEST_PAPER);
        return Message.success();
    }
    /**
     * 获取历史考试
     * @param session HttpSession
     * @return 返回成功
     */
    @GetMapping("/history")
    public Message examHistory(HttpSession session){
        return examTestService.examHistory(session);
    }
    /**
     * 获取历史考试试卷
     * @param session HttpSession
     * @return 返回成功
     */
    @GetMapping("/history/{examId:\\d+}")
    public Message examHistory(@PathVariable Integer examId, HttpSession session){
        return examTestService.examTestHistory(examId, session);
    }
    /**
     * 获取答卷具体答案
     * @param session HttpSession
     * @return 返回答卷具体答案
     */
    @GetMapping("/result/{resultId:\\d+}")
    public Message examResultAnswer(@PathVariable Integer resultId, HttpSession session) {
        return examTestService.examResultAnswer(resultId, session);
    }
}
