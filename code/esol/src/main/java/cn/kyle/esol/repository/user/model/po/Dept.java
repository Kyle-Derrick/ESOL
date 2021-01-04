package cn.kyle.esol.repository.user.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 部门实体类
 * @author Kyle
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_dept")
@Entity
public class Dept implements Serializable {
    /**
     * 部门编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deptId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门负责人
     */
    private String leader;
    /**
     * 部门简介
     */
    private String info;
    /**
     * 上级部门编号
     */
    private Integer parentId;
    /**
     * 父级编号全路径
     */
    private String fullId;
    /**
     * 版本号
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer version = 1;
}
