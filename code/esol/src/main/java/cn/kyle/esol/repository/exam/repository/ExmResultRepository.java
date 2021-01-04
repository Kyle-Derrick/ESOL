package cn.kyle.esol.repository.exam.repository;

import cn.kyle.esol.repository.exam.model.po.ExmResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * 答卷数据访问层接口
 * @author Kyle
 */
public interface ExmResultRepository extends JpaRepository<ExmResult, Integer> {
    /**
     * 获取当前登录用户的考试列表信息
     * examId 考试编号
     * examTitle 考试标题
     * examStartTime 考试开始日期
     * examEndTime 考试结束日期
     * examDuration 考试时长
     * examResNum 剩余考试次数
     * examDoneNum 已交卷次数
     * @param userId 用户编号
     * @return 返回考试列表信息
     */
    @Query(value = "SELECT " +
            "etp.test_paper_id AS examId,etp.title AS examTitle," +
            "etp.start_date AS examStartTime,etp.end_date AS examEndTime," +
            "etp.duration AS examDuration," +
            "(etp.max_num - COUNT(er.test_paper_id)) AS examResNum," +
            "(COUNT(er.test_paper_id)) AS examDoneNum " +
            "FROM exm_result AS er " +
                "RIGHT JOIN exm_test_paper AS etp " +
                    "ON (er.test_paper_id=etp.test_paper_id " +
                        "AND er.user_id=?1) " +
            "GROUP BY etp.test_paper_id", nativeQuery = true)
    List<Map<String, Object>> getExamList(Integer userId);

    /**
     * 获取当前用户历史考试列表信息
     * 返回集合中
     * examSorce表示一场考试最终成绩(过期未考算0)
     * examLastDate最后一次考试时间
     * examResNum剩余考试次数
     * testNum考了多少次(有0次情况,比如过期未考试)
     * @param userId 用户编号
     * @return 返回历史考试列表信息
     */
    @Query(value = "SELECT " +
            "etp.test_paper_id AS examId, " +
            "etp.title AS examTitle, " +
            "IFNULL(IF(etp.sorce_rule = 1,MAX(er.sorce),AVG(er.sorce)), 0) AS examSorce, " +
            "etp.total_sorce AS totalSorce, " +
            "IFNULL(MAX(er.result_date), etp.end_date) AS examLastDate, " +
            "etp.max_num-COUNT(er.result_id) AS examResNum," +
            "COUNT(er.sorce) AS testNum " +
            "FROM exm_result AS er RIGHT JOIN exm_test_paper AS etp " +
            "ON (er.test_paper_id=etp.test_paper_id AND er.user_id=?1) " +
            "WHERE !(er.sorce IS NULL AND etp.end_date>NOW()) " +
            "GROUP BY er.test_paper_id ", nativeQuery = true)
    List<Map<String, Object>> getHistoryExam(Integer userId);

    /**
     * 获取考试答卷信息列表
     * @param examId 考试编号
     * @param userId 用户编号
     * @return 返回结果
     */
    @Query(value = "SELECT er.result_id AS resultId, er.duration AS duration," +
            "er.result_date AS resultDate,er.sorce AS sorce," +
            "er.sucess_num AS sucessNum,er.error_num AS errorNum " +
            "FROM exm_result AS er WHERE er.test_paper_id=?1 AND er.user_id=?2", nativeQuery = true)
    List<Map<String, Object>> examTestHistory(Integer examId, Integer userId);
}
