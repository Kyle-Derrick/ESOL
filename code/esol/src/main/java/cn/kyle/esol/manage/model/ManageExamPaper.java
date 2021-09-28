package cn.kyle.esol.repository.exam.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 考卷实体类
 * @author Kyle
 */
@Data
@Accessors(chain = true)
public class ManageExamPaper implements Serializable {
  /**
   * 试卷Id
   */
  private Integer testPaperId;
  /**
   * 试卷标题
   */
  private String title;
  /**
   * 开始日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date startDate;
  /**
   * 截止日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date endDate;
  /**
   * 考试时长
   */
  private Integer duration;
  /**
   * 最大考试数
   */
  private Integer maxNum;
  /**
   * 分数规则
   */
  private Integer sorceRule;
  /**
   * 总分
   */
  private Integer totalSorce;
  /**
   * 试卷生成规则
   */
  private Integer questionRule;
  /**
   * 单选题数
   */
  private Integer radioNum;
  /**
   * 多选题数
   */
  private Integer mulitpleNum;
  /**
   * 选择题数
   */
  private Integer judgeNum;
  /**
   * 总题数
   */
  private Integer totalNum;
  /**
   * 版本号
   */
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer version = 1;

  /**
   * 试卷包含的题,若为随机,则无数据
   */
  private Integer[] questionLibIds;
}
