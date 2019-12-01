package cn.lcsz.adam.pbems.exam.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * 考卷预设试题实体类
 * @author yufs
 */
@Data
@Entity
@Table(name = "exm_test_question")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ExmTestQuestion {
  /**
   * 编号
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer testQuestionId;
  /**
   * 对应试题
   */
  @JoinColumn(name = "question_lib_id")
  @ManyToOne(fetch = FetchType.EAGER)
  private ExmQuestionLib questionLib;
  /**
   * 试题序号
   */
  private Integer sequence;
  /**
   * 版本号
   */
  @JsonIgnore
  private Integer version;
}
