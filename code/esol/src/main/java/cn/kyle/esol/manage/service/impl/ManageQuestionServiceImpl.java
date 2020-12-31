package cn.kyle.esol.manage.service.impl;

import cn.kyle.esol.base.exception.CodeMessageException;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.base.util.BaseUtils;
import cn.kyle.esol.manage.model.ManageExmQuestionLib;
import cn.kyle.esol.manage.model.PageResponse;
import cn.kyle.esol.manage.repository.ManageExmQuestionLibRepository;
import cn.kyle.esol.manage.service.ManageQuestionService;
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
public class ManageQuestionServiceImpl implements ManageQuestionService {
    private final ManageExmQuestionLibRepository questionRepository;

    @Autowired
    public ManageQuestionServiceImpl(ManageExmQuestionLibRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Message list(String title,
                        Integer kind,
                        Integer pageIndex,
                        Integer pageSize,
                        HttpSession session) {
        try {
            ManageExmQuestionLib questionDemo = new ManageExmQuestionLib().setTitle(title)
                    .setQuestionKind(kind);
            Page<ManageExmQuestionLib> page = questionRepository.findAll(
                    Example.of(questionDemo,
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
            questionRepository.deleteByIds(ids);
            return Message.success("删除成功");
        }catch (Exception e) {
            throw new CodeMessageException("删除失败！");
        }
    }

    @Override
    @Transactional
    public Message save(ManageExmQuestionLib question) {
        String prefix = question.getQuestionLibId() == null ? "添加" : "更新";
        try {
            question.setVersion(1);
            if (question.getQuestionLibId() != null) {
                Optional<ManageExmQuestionLib> tmp = questionRepository.findById(question.getQuestionLibId());
                if (!tmp.isPresent()){
                    throw new CodeMessageException(prefix.concat("所更新的信息不存在。"));
                }
                ManageExmQuestionLib questionTmp = tmp.get();
                BaseUtils.copyProperties(question, questionTmp);
                question = questionTmp;
            }
            questionRepository.save(question);
            return Message.success(prefix.concat("成功！"));
        } catch (DataIntegrityViolationException e) {
            throw new CodeMessageException("重复添加！");
        } catch (Exception e) {
            throw new CodeMessageException(prefix.concat("失败, 请检查信息。"));
        }
    }
}
