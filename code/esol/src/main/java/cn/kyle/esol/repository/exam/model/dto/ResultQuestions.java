package cn.kyle.esol.repository.exam.model.dto;

import cn.kyle.esol.repository.exam.model.po.ExmResultQuestion;
import lombok.Data;

import java.util.List;

/**
 * 考试试题(分题型)数据传输对象
 * @author Kyle
 */
@Data
public class ResultQuestions {
    /**
     * 题型
     */
    private Integer questionType;
    /**
     * 该题型试题分数
     */
    private Integer questionScore;
    /**
     * 该题型试题数目
     */
    private Integer questionCount;
    /**
     * 该题型所有题列表
     */
    private List<ExmResultQuestion> questions;

    public ResultQuestions(Integer questionType, Integer questionScore, Integer questionCount, List<ExmResultQuestion> questions) {
        this.questionType = questionType;
        this.questionScore = questionScore;
        this.questionCount = questionCount;
        this.questionCount = questions.size();
        this.questions = questions;
    }

    public ResultQuestions() {
    }
}
