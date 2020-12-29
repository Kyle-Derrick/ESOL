package cn.kyle.esol.exam.model.dto;

import cn.kyle.esol.exam.model.po.ExmQuestionLib;
import lombok.Data;

import java.util.List;

/**
 * 考试试题(分题型)数据传输对象
 * @author Kyle
 */
@Data
public class Questions {
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
    private List<ExmQuestionLib> questions;

    public Questions(Integer questionType, Integer questionScore, Integer questionCount, List<ExmQuestionLib> questions) {
        this.questionType = questionType;
        this.questionScore = questionScore;
        this.questionCount = questionCount;
        this.questionCount = questions.size();
        this.questions = questions;
    }

    public Questions() {
    }
}
