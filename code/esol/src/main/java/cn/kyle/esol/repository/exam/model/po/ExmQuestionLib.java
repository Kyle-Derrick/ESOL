package cn.kyle.esol.repository.exam.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 题库实体类(不可返回答案和解析)
 * @author Kyle
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "exm_question_lib")
public class ExmQuestionLib implements Serializable {
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
  @JsonIgnore
  private String refAnswer;
  /**
   * 解析
   */
  @JsonIgnore
  private String analysis;
  /**
   * 版本号
   */
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(name = "vsrsion")
  private Integer version = 1;

}
