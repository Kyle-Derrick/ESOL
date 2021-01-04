package cn.kyle.esol.repository.exam.repository;

import cn.kyle.esol.repository.exam.model.po.ExmQuestionLib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 题库数据访问层接口
 * @author Kyle
 */
public interface ExmQuestionLibRepository extends JpaRepository<ExmQuestionLib, Integer> {
    /**
     * 随机获取随机生成的试题
     * @param radioNum 单选试题类型
     * @param mulitpleNum 多选试题类型
     * @param judgeNum 判断试题类型
     * @return  返回试题信息
     */
    @Query(value = "(SELECT * FROM exm_question_lib AS teql WHERE teql.question_kind=1 ORDER BY RAND() LIMIT ?1) " +
            "UNION ALL " +
            "(SELECT * FROM exm_question_lib AS teql WHERE teql.question_kind=2 ORDER BY RAND() LIMIT ?2) " +
            "UNION ALL " +
            "(SELECT * FROM exm_question_lib AS teql WHERE teql.question_kind=3 ORDER BY RAND() LIMIT ?3) " +
            "ORDER BY question_kind ;", nativeQuery = true)
    List<ExmQuestionLib> getRandQuestionByKind(int radioNum, int mulitpleNum, int judgeNum);
}
