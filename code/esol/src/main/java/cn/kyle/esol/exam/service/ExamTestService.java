package cn.kyle.esol.exam.service;

import cn.kyle.esol.base.model.dto.Message;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 考试相关服务接口
 * @author yufs
 */
public interface ExamTestService {
    /**
     * 获取当前登录用户考试列表
     * @param session HttpSession
     * @return 返回考试列表数据
     */
    Message getExamList(HttpSession session);

    /**
     * 获取试卷
     * @param examId 考试Id
     * @param session HttpSession
     * @return 返回详细试卷
     */
    Message getExam(Integer examId, HttpSession session);

    /**
     * 交卷
     * @param answers 考生答卷
     * @param session HttpSession
     * @return 返回考试结果
     */
    Message commit(Map<Integer, Object> answers, HttpSession session);

    /**
     * 获取当前用户的考试历史记录
     * @param session HttpSession
     * @return 返回历史记录信息
     */
    Message examHistory(HttpSession session);
    /**
     * 获取当前用户的考试的试卷历史记录
     * @param session HttpSession
     * @param examId 考试编号
     * @return 返回历史记录信息
     */
    Message examTestHistory(Integer examId, HttpSession session);
    /**
     * 获取当前用户一次考试的试卷答案详情
     * @param session HttpSession
     * @param resultId 答卷编号
     * @return 返回试卷答案详情信息
     */
    Message examResultAnswer(Integer resultId, HttpSession session);
}
