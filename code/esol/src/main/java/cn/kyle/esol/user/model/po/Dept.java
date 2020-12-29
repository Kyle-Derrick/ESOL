package cn.kyle.esol.user.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * 部门实体类
 * @author Kyle
 */
@Data
@Table(name = "sys_dept")
@Entity
public class Dept {
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
    @JsonIgnore
    private Integer version;
}
