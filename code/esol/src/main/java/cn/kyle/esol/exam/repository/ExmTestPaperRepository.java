package cn.kyle.esol.exam.repository;

import cn.kyle.esol.exam.model.po.ExmTestPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 试卷数据访问层接口
 * @author yufs
 */
public interface ExmTestPaperRepository extends JpaRepository<ExmTestPaper, Integer> {
    /**
     * 获取考试,前提是还有考试次数
     * @param userId 用户编号
     * @param examId 考试编号
     * @return 返回考试信息
     */
    @Query(value = "SELECT  * FROM exm_test_paper AS etp LEFT JOIN exm_result AS er " +
            "ON (etp.test_paper_id=er.test_paper_id " +
            "AND er.user_id=?1) " +
            "WHERE etp.test_paper_id=?2 HAVING etp.max_num>COUNT(er.result_id);", nativeQuery = true)
    ExmTestPaper getExam(int userId, int examId);
}
