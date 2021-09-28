package cn.kyle.esol.manage.model;

import cn.kyle.esol.base.model.dto.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Kyle
 */
@Data
public class PageResponse extends Page {
    private Long total;
    private List<?> list;

    public static PageResponse identify(org.springframework.data.domain.Page<?> page) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setPageIndex(page.getNumber());
        pageResponse.setPageSize(page.getSize());
//        pageResponse.setTotal(page.getTotalPages());
        pageResponse.setTotal(page.getTotalElements());
        pageResponse.setList(page.getContent());
        return pageResponse;
    }
}
