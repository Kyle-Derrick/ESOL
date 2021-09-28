package cn.kyle.esol.repository.exam.repository;

import cn.kyle.esol.repository.exam.model.po.ExmTestQuestionSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 试卷数据访问层接口
 * @author Kyle
 */
public interface ExmTestQuestionRepository extends JpaRepository<ExmTestQuestionSource, Integer> {
    @Modifying
    @Query(value = "delete from exm_test_question where test_paper_id in (:id)", nativeQuery = true)
    Integer deleteByTestPaperId(@Param("id") Integer ...testPaperId);
}
