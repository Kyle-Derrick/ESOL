package cn.kyle.esol.manage.repository;

import cn.kyle.esol.manage.model.ManageExmQuestionLib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 题库数据访问层接口
 * @author Kyle
 */
public interface ManageExmQuestionLibRepository extends JpaRepository<ManageExmQuestionLib, Integer> {
    @Modifying
    @Query(value = "delete from exm_question_lib where question_lib_id in (:ids)", nativeQuery = true)
    Integer deleteByIds(@Param("ids") List<Integer> ids);
}
