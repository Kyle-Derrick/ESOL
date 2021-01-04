package cn.kyle.esol.repository.user.model.dto;

import cn.kyle.esol.repository.user.model.po.User;
import lombok.Data;

/**
 * 用户信息数据传输对象
 * @author Kyle
 */
@Data
public class UserInfo {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
//    /**
//     * 用户密码
//     */
//    private String passWord;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户类型
     */
    private Integer kind;
    /**
     * 用户所在部门Id
     */
    private Integer deptId;
    /**
     * 用户所在部门信息
     */
    private String deptName;
    /**
     * 用户考试次数
     */
    private Integer examNum;
    /**
     * 用户答题正确率
     */
    private Integer answerAccuracy;
    /**
     * 用户排名
     */
    private Integer rank;

    /**
     * 根据用户实体类来设置用户信息参数
     * @param user 用户实体类
     */
    public void setDataByUser(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
//        this.passWord = user.getPassWord();
        this.name = user.getName();
        this.kind = user.getUserKind();
        if (user.getDept() != null) {
            this.deptName = user.getDept().getName();
            this.deptId = user.getDept().getDeptId();
        }else {
            this.deptName = "无";
        }
    }
}
