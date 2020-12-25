package cn.kyle.esol.user.repository;

import cn.kyle.esol.user.model.po.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 部门数据访问层接口
 * @author yufs
 */
public interface DeptRepository extends JpaRepository<Dept, Integer> {
}
