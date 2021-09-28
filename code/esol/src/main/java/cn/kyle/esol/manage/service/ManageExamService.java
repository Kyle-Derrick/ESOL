package cn.kyle.esol.manage.service;

import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.manage.model.ManageExmQuestionLib;
import cn.kyle.esol.repository.exam.model.po.ExmTestPaper;
import cn.kyle.esol.repository.exam.model.po.ManageExamPaper;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 管理员相关服务接口
 * @author Kyle
 */
public interface ManageExamService {
    Message list(String title,
                 Integer pageIndex,
                 Integer pageSize,
                 HttpSession session);
    Message delete(List<Integer> ids);
    Message save(ManageExamPaper exam);
}
