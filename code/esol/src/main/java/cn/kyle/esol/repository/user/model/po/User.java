package cn.kyle.esol.repository.user.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户表实体类
 * @author Kyle
 */
@Data
@Table(name = "sys_user")
@Entity
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class User implements Serializable {
    /**
     * 用户编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passWord;
    /**
     * 用户类型 1管理员2考试用户
     */
    private Integer userKind;
    /**
     * 版本号
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer version = 1;

    /**
     * 对应的部门实体类
     */
    @JoinColumn(name = "dept_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Dept dept;
}
