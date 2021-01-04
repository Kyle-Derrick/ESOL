package cn.kyle.esol.base.model.dto;

import lombok.Data;

/**
 * 分页
 * @author Kyle
 */
@Data
public abstract class Page {
    private Integer pageSize;
    private Integer pageIndex;
}
