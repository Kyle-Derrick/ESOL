package cn.kyle.esol.repository.exam.model.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 考卷预设试题实体类
 * @author Kyle
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "exm_test_question")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ExmTestQuestionSource implements Serializable {
  /**
   * 编号
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer testQuestionId;
  /**
   * 对应试卷
   */
  @Column(name = "test_paper_id")
  private Integer testPaperId;
  /**
   * 对应试题
   */
  @Column(name = "question_lib_id")
  private Integer questionLibId;
  /**
   * 试题序号
   */
  private Integer sequence;
  /**
   * 版本号
   */
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer version = 1;
}
