package cn.lcsz.adam.pbems.user.modle.dto;

import cn.lcsz.adam.pbems.user.modle.po.User;
import lombok.Data;

/**
 * 用户信息数据传输对象
 */
@Data
public class UserInfo {
    //用户名
    private String userName;
    //用户联系电话
    private String tel;
    //用户姓名
    private String name;
    //用户所在部门信息
    private String deptName;
    //用户考试次数
    private Integer examNum;
    //用户答题正确率
    private Integer answerAccuracy;
    //用户排名
    private Integer rank;

    public void setDataByUser(User user){
        this.userName = user.getUserName();
        this.tel = user.getTelphone();
        this.name = user.getName();
        this.deptName = user.getDept().getName();
    }
}
