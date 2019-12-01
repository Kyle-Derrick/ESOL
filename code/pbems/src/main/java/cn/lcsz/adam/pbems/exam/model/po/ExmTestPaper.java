package cn.lcsz.adam.pbems.exam.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 考卷实体类
 * @author yufs
 */
@Data
@Entity
@Table(name = "exm_test_paper")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ExmTestPaper {
    /**
     * 试卷Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testPaperId;
    /**
     * 试卷标题
     */
    private String title;
    /**
     * 开始日期
     */
    private Date startDate;
    /**
     * 截止日期
     */
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
    @JsonIgnore
    private Integer version;

    /**
     * 试卷包含的题,若为随机,则无数据
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_paper_id")
    @OrderBy("sequence ASC ")
    private List<ExmTestQuestion> exmTestQuestions;
}
