package cn.kyle.esol.repository.user.repository;

import cn.kyle.esol.repository.user.model.po.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 部门数据访问层接口
 * @author Kyle
 */
public interface DeptRepository extends JpaRepository<Dept, Integer> {
    @Modifying
    @Query(value = "delete from sys_dept where dept_id in (:ids)", nativeQuery = true)
    Integer deleteByIds(@Param("ids") List<Integer> ids);
}
