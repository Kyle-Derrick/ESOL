package cn.kyle.esol.manage.service.impl;

import cn.kyle.esol.base.exception.CodeMessageException;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.base.util.BaseUtils;
import cn.kyle.esol.manage.model.PageResponse;
import cn.kyle.esol.manage.service.ManageExamService;
import cn.kyle.esol.repository.exam.model.po.ExmTestPaper;
import cn.kyle.esol.repository.exam.repository.ExmTestPaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * 服务实现
 * @author Kyle
 */
@Service
@Validated
@Slf4j
public class ManageExamServiceImpl implements ManageExamService {
    private final ExmTestPaperRepository examRepository;

    @Autowired
    public ManageExamServiceImpl(ExmTestPaperRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Message list(String title,
                        Integer pageIndex,
                        Integer pageSize,
                        HttpSession session) {
        try {
            ExmTestPaper examDemo = new ExmTestPaper().setTitle(title);
            Page<ExmTestPaper> page = examRepository.findAll(
                    Example.of(examDemo,
                        ExampleMatcher.matching().withMatcher("title",
                                ExampleMatcher.GenericPropertyMatchers.contains()
                        )
                    ),
                    PageRequest.of(pageIndex < 1 ? 0 : pageIndex - 1, pageSize));

            return Message.success().setData(PageResponse.identify(page));
        }catch (Exception e) {
            throw new CodeMessageException("查询失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = CodeMessageException.class)
    public Message delete(List<Integer> ids) {
        try {
            if (ids.size() < 1) {
                throw new CodeMessageException("参数为空！");
            }
            examRepository.deleteByIds(ids);
            return Message.success("删除成功");
        }catch (Exception e) {
            throw new CodeMessageException("删除失败！");
        }
    }

    @Override
    @Transactional
    public Message save(ExmTestPaper exam) {
        String prefix = exam.getTestPaperId() == null ? "添加" : "更新";
        try {
            exam.setVersion(1);
            if (exam.getTestPaperId() != null) {
                Optional<ExmTestPaper> tmp = examRepository.findById(exam.getTestPaperId());
                if (!tmp.isPresent()){
                    throw new CodeMessageException(prefix.concat("所更新的信息不存在。"));
                }
                ExmTestPaper examTmp = tmp.get();
                BaseUtils.copyProperties(exam, examTmp);
                exam = examTmp;
            }
            exam.setTotalNum(
                            (exam.getJudgeNum() == null ? 0 : exam.getJudgeNum()) +
                            (exam.getMulitpleNum() == null ? 0 : exam.getMulitpleNum()) +
                            (exam.getRadioNum() == null ? 0 : exam.getRadioNum())
                    );
            examRepository.save(exam);
            return Message.success(prefix.concat("成功！"));
        } catch (DataIntegrityViolationException e) {
            throw new CodeMessageException("重复添加！");
        } catch (Exception e) {
            throw new CodeMessageException(prefix.concat("失败, 请检查信息。"));
        }
    }
}
