package cn.lcsz.adam.pbems.exam.model.dto;

import cn.lcsz.adam.pbems.exam.model.po.ExmTestPaper;
import lombok.Data;

import java.util.List;

/**
 * 试卷数据传输对象
 * @author yufs
 */
@Data
public class Exam {
    /**
     * 考试编号
     */
    private Integer examId;
    /**
     * 考试标题
     */
    private String examTitle;
    /**
     * 考试时长
     */
    private Integer examDuration;
    /**
     * 考试总分
     */
    private Integer examScore;
    /**
     * 试题总数
     */
    private Integer examQuestionCount;
    /**
     * 试卷试题对象数组（题型分类）
     */
    private List<Questions> examQuestions;

    public static Exam get(ExmTestPaper exmTestPaper, List<Questions> questions){
        Exam exam = new Exam();
        exam.setExamId(exmTestPaper.getTestPaperId());
        exam.setExamTitle(exmTestPaper.getTitle());
        exam.setExamDuration(exmTestPaper.getDuration());
        exam.setExamScore(exmTestPaper.getTotalSorce());
        exam.setExamQuestionCount(exmTestPaper.getTotalNum());
        exam.setExamQuestions(questions);
        return exam;
    }
}
