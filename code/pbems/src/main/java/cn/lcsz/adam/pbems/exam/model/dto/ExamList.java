package cn.lcsz.adam.pbems.exam.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 考试列表信息数据传输对象
 * @author yufs
 */
@Data
@ToString
public class ExamList {
    /**
     * 考试编号
     */
    private Integer examId;
    /**
     * 考试标题
     */
    private String examTitle;
    /**
     * 考试开始时间
     */
    private Date examStartTime;
    /**
     * 考试戒指时间
     */
    private Date examEndTime;
    /**
     * 考试时长
     */
    private Integer examDuration;
    /**
     * 剩余考试次数
     */
    private Integer examResNum;
    /**
     * 已考次数
     */
    private Integer examDoneNum;
}
