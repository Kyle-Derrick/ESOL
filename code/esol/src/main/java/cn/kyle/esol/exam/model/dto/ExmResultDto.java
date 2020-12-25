package cn.kyle.esol.exam.model.dto;

import cn.kyle.esol.exam.model.po.ExmTestPaper;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 答卷数据传输对象
 * @author yufs
 */
@Data
public class ExmResultDto {
  /**
   * 答卷编号
   */
  private Integer resultId;
  /**
   * 答卷用时时长
   */
  private Integer duration;
  /**
   * 提交答卷时间
   */
  private Date resultDate;
  /**
   * 得分
   */
  private Integer sorce;
  /**
   * 正确数
   */
  private Integer sucessNum;
  /**
   * 错误数
   */
  private Integer errorNum;

  /**
   * 外键关联对应试卷
   */

  private ExmTestPaper testPaper;

  /**
   * 试卷试题对象数组（题型分类）
   */
  private List<ResultQuestions> examQuestions;

}
