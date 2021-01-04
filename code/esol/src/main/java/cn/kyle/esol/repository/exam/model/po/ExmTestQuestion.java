package cn.kyle.esol.repository.exam.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ExmTestQuestion implements Serializable {
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
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer version = 1;
}
