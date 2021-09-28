package cn.kyle.esol.manage.service.impl;

import cn.kyle.esol.base.exception.CodeMessageException;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.base.util.BaseUtils;
import cn.kyle.esol.manage.model.PageResponse;
import cn.kyle.esol.manage.service.ManageExamService;
import cn.kyle.esol.repository.exam.model.enumeration.QuestionRule;
import cn.kyle.esol.repository.exam.model.enumeration.QuestionType;
import cn.kyle.esol.repository.exam.model.po.*;
import cn.kyle.esol.repository.exam.model.po.ManageExamPaper;
import cn.kyle.esol.repository.exam.repository.ExmQuestionLibRepository;
import cn.kyle.esol.repository.exam.repository.ExmTestPaperRepository;
import cn.kyle.esol.repository.exam.repository.ExmTestQuestionRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
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
    private final ExmQuestionLibRepository exmQuestionLibRepository;
    private final ExmTestQuestionRepository exmTestQuestionRepository;

    @Autowired
    public ManageExamServiceImpl(ExmTestPaperRepository examRepository, ExmQuestionLibRepository exmQuestionLibRepository, ExmTestQuestionRepository exmTestQuestionRepository) {
        this.examRepository = examRepository;
        this.exmQuestionLibRepository = exmQuestionLibRepository;
        this.exmTestQuestionRepository = exmTestQuestionRepository;
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
            exmTestQuestionRepository.deleteByTestPaperId(ids.toArray(new Integer[ids.size()]));
            examRepository.deleteByIds(ids);
            return Message.success("删除成功");
        }catch (Exception e) {
            throw new CodeMessageException("删除失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message save(ManageExamPaper exam) {
        String prefix = exam.getTestPaperId() == null ? "添加" : "更新";
        ExmTestPaper examTmp = new ExmTestPaper();
        try {
            exam.setVersion(1);
            if (exam.getTestPaperId() != null) {
                Optional<ExmTestPaper> tmp = examRepository.findById(exam.getTestPaperId());
                if (!tmp.isPresent()){
                    throw new CodeMessageException(prefix.concat("所更新的信息不存在。"));
                }
//                BaseUtils.copyProperties(tmp.get(), examTmp);
                examTmp = tmp.get();
//                exam = examTmp;
            }
            BaseUtils.copyProperties(exam, examTmp);

            if (examTmp.getQuestionRule() == QuestionRule.RANDOM) {
                examTmp.setTotalNum(
                        (exam.getJudgeNum() == null ? 0 : exam.getJudgeNum()) +
                                (exam.getMulitpleNum() == null ? 0 : exam.getMulitpleNum()) +
                                (exam.getRadioNum() == null ? 0 : exam.getRadioNum())
                );
            } else {
                if (examTmp.getTestPaperId() == null){
                    examTmp = examRepository.save(examTmp);
                }
                exmTestQuestionRepository.deleteByTestPaperId(examTmp.getTestPaperId());
//                examTmp.setExmTestQuestions(null);
                List<ExmQuestionLib> questionLibs = exmQuestionLibRepository.findAllById(Arrays.asList(exam.getQuestionLibIds()));
                int i = 1, jNum = 0, mNum = 0, rNum = 0;
                List<ExmTestQuestionSource> exmTestQuestions = new ArrayList<>(questionLibs.size());
                for (ExmQuestionLib questionLib : questionLibs) {
                    ExmTestQuestionSource question = new ExmTestQuestionSource();
                    question.setQuestionLibId(questionLib.getQuestionLibId())
                            .setSequence(i++)
                            .setTestPaperId(examTmp.getTestPaperId());
                    exmTestQuestions.add(question);
                    switch (questionLib.getQuestionKind()) {
                        case QuestionType
                                .RADIO:
                            rNum++;
                            break;
                        case QuestionType
                                .MULITPLE:
                            mNum++;
                            break;
                        case QuestionType
                                .JUDGE:
                            jNum++;
                            break;
                    }
                }

                examTmp.setJudgeNum(jNum)
                        .setMulitpleNum(mNum)
                        .setRadioNum(rNum)
                        .setTotalNum(questionLibs.size()-1);
                exmTestQuestionRepository.saveAll(exmTestQuestions);
            }
//            examRepository.save(examTmp);
            return Message.success(prefix.concat("成功！"));
        } catch (DataIntegrityViolationException e) {
            throw new CodeMessageException("重复添加！");
        } catch (Exception e) {
            throw new CodeMessageException(prefix.concat("失败, 请检查信息。"));
        }
    }
}
