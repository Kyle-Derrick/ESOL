package cn.kyle.esol.exam.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 历史答卷列表信息数据传输对象
 * @author Kyle
 */
@Data
@ToString
public class ExamHistoryTestList {
    /**
     * 答卷编号
     */
    private Integer resultId;
    /**
     * 考试时长
     */
    private Integer duration;
    /**
     * 交卷日期
     */
    private Date resultDate;
    /**
     * 试卷得分
     */
    private Integer sorce;
    /**
     * 正确题数
     */
    private Integer sucessNum;
    /**
     * 错误题数
     */
    private Integer errorNum;
}
