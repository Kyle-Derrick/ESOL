package cn.kyle.esol.manage.service;

import cn.kyle.esol.base.model.constant.VaildMessage;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.repository.user.model.po.Dept;
import cn.kyle.esol.repository.user.model.po.User;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 管理员相关服务接口
 * @author Kyle
 */
public interface ManageDeptService {
    Message list(String name,
                 Integer pageIndex,
                 Integer pageSize,
                 HttpSession session);
    Message get(Integer id);
    Message delete(List<Integer> ids);
    Message save(Dept dept);
}
