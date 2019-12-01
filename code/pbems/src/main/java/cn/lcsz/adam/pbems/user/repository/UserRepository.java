package cn.lcsz.adam.pbems.user.repository;

import cn.lcsz.adam.pbems.user.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

/**
 * 用户数据访问层接口
 * @author yufs
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 获取用户信息
     * @param userName 用户名
     * @param tel 用户电话号码
     * @return 返回用户实体类
     */
    User findOneByUserNameAndTelphone(String userName, String tel);

    /**
     * 获取用户考试次数以及正确率
     * @param userId 用户ID
     * @return 返回考试次数以及正确率map
     */
    @Query(value = "SELECT " +
            "COUNT(er.result_id) AS examNum,IFNULL(AVG(sucess_num/(sucess_num+error_num)),0) AS answerAccuracy " +
            "FROM exm_result AS er " +
            "WHERE er.user_id = ?1 ;", nativeQuery = true)
    Map<String, Object> getUserExamNumAndAnswerAccuracy(Integer userId);

    /**
     * 获取用户考试排名
     * @param userId 用户编号
     * @param rankNum 传入用户平均正确率
     * @return 返回排名
     */
    @Query(value = "SELECT (COUNT(tmp.num)+1) AS 'rank' FROM (SELECT " +
            "AVG(er.sucess_num/(er.sucess_num+er.error_num)) AS num " +
            "FROM exm_result AS er " +
            "WHERE er.user_id!=?1 " +
            "GROUP BY er.user_id " +
            "HAVING num > ?2) AS tmp ;", nativeQuery = true)
    int getUserExamRank(Integer userId, Double rankNum);
}
