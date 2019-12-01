package cn.lcsz.adam.pbems.exam.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * 题库实体类(可将答案返回)
 * @author yufs
 */
@Data
@Entity
@Table(name = "exm_question_lib")
public class ExmQuestionLibAndAnswer {
  /**
   * 试题编号
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer questionLibId;
  /**
   * 试题类型,1单选,2多选,3判断
   */
  private Integer questionKind;
  /**
   * 题目
   */
  private String title;
  /**
   * 选项A(判断题该字段为空)
   */
  @Column(name = "item_a")
  private String itemA;
  /**
   * 选项B(判断题该字段为空)
   */
  @Column(name = "item_b")
  private String itemB;
  /**
   * 选项C(判断题该字段为空)
   */
  @Column(name = "item_c")
  private String itemC;
  /**
   * 选项D(判断题该字段为空)
   */
  @Column(name = "item_d")
  private String itemD;
  /**
   * 参考答案
   */
  private String refAnswer;
  /**
   * 解析
   */
  private String analysis;
  /**
   * 版本号
   */
  @JsonIgnore
  private Integer vsrsion;

}
