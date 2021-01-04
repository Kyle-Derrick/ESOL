package cn.kyle.esol.repository.exam.model.po;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 考生答案实体类
 * @author Kyle
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "exm_result_question")
public class ExmResultQuestion implements Serializable {
  /**
   * 试题答案编号
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer resultQuestionId;
  /**
   * 试题类型,1单选,2多选,3判断
   */
  @Column(name = "question_kind")
  private String questionKind;

  /**
   * 对应题库ID
   */
  private Integer questionLibId;
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
   * 考生答案
   */
  private String answer;
  /**
   * 解析
   */
  private String analysis;
  /**
   * 版本号
   */
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer version = 1;

  public Integer getQuestionKindInt() {
    if (this.questionKind == null){
      return null;
    }
    return Integer.parseInt(this.getQuestionKind());
  }
}
