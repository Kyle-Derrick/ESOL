package cn.kyle.esol.repository.exam.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 答卷实体类
 * @author Kyle
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "exm_result")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ExmResult implements Serializable {
  /**
   * 答卷编号
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
   * 版本号
   */
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer version = 1;

  /**
   * 外键关联对应用户
   */
//  @JoinColumn(name = "user_id")
//  @ManyToOne(fetch = FetchType.LAZY)
//  private User user;
  private Integer userId;
  /**
   * 外键关联对应试卷
   */
  @JoinColumn(name = "test_paper_id" )
  @ManyToOne(fetch = FetchType.LAZY)
  private ExmTestPaper testPaper;

  @JoinColumn(name = "resultId" )
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ExmResultQuestion> questions;
}
