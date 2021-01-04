package cn.kyle.esol.api.service.impl;

import cn.kyle.esol.base.model.constant.Exceptions;
import cn.kyle.esol.base.model.constant.SessionKeys;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.repository.exam.model.dto.Exam;
import cn.kyle.esol.repository.exam.model.dto.ExmResultDto;
import cn.kyle.esol.repository.exam.model.dto.Questions;
import cn.kyle.esol.repository.exam.model.dto.ResultQuestions;
import cn.kyle.esol.repository.exam.model.enumeration.QuestionRule;
import cn.kyle.esol.repository.exam.model.enumeration.QuestionType;
import cn.kyle.esol.repository.exam.model.po.*;
import cn.kyle.esol.repository.exam.repository.ExmQuestionLibRepository;
import cn.kyle.esol.repository.exam.repository.ExmResultRepository;
import cn.kyle.esol.repository.exam.repository.ExmTestPaperRepository;
import cn.kyle.esol.api.service.ExamTestService;
import cn.kyle.esol.repository.user.model.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 考试相关服务实现
 * @author Kyle
 */
@Service
@Slf4j
public class ExamTestServiceImpl implements ExamTestService {
    @Value("${exam.fault-tolerant-time:30}")
    private int ftTime;
    private final ExmTestPaperRepository exmTestPaperRepository;
    private final ExmResultRepository exmResultRepository;
    private final ExmQuestionLibRepository exmQuestionLibRepository;

    @Autowired
    public ExamTestServiceImpl(ExmTestPaperRepository exmTestPaperRepository,
                               ExmResultRepository exmResultRepository,
                               ExmQuestionLibRepository exmQuestionLibRepository) {
        this.exmTestPaperRepository = exmTestPaperRepository;
        this.exmResultRepository = exmResultRepository;
        this.exmQuestionLibRepository = exmQuestionLibRepository;
    }

    /**
     * 获取当前登录用户考试列表
     * @return 返回考试列表数据
     */
    @Override
    public Message getExamList(HttpSession session) {
        User user = (User) session.getAttribute(SessionKeys.USER_INFO);
        List<Map<String, Object>> list = exmResultRepository.getExamList(user.getUserId());

        return Message.success().setData(list);
    }

    /**
     * 提交答卷处理
     * @param answers 考生答卷
     * @param session HttpSession
     * @return 返回结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message commit(Map<Integer, Object> answers, HttpSession session) {

        Integer startTime = (Integer) session.getAttribute(SessionKeys.EXAM_START_TIME);
        int nowTime = getNowTime();
        Exam exam = (Exam) session.getAttribute(SessionKeys.EXAM_TEST);
        ExmTestPaper exmTestPaper =
                (ExmTestPaper) session.getAttribute(SessionKeys.EXAM_TEST_PAPER);
//        校验考试试卷相关
        if (Objects.isNull(startTime)||
                Objects.isNull(exam)||
                Objects.isNull(exmTestPaper)) { throw Exceptions.EXAM_INVALID; }
        int examDuration = exam.getExamDuration() * 60;
        int duration = nowTime - startTime;

//        如果答题时间超过考试时长
        if (duration - examDuration > ftTime){
            throw Exceptions.EXAM_TIMEOUT;
        }

        ExmResult exmResult = examCalculation(session, answers, exmTestPaper, exam, nowTime, duration);

        return Message.success().setData(getResultQuestionByType(exmResult));
    }

    /**
     * 获取当前用户的考试历史记录
     * @param session HttpSession
     * @return 返回历史记录信息
     */
    @Override
    public Message examHistory(HttpSession session) {
        User user = (User) session.getAttribute(SessionKeys.USER_INFO);
        List<Map<String, Object>> historyExam = exmResultRepository.getHistoryExam(user.getUserId());

        return Message.success().setData(historyExam);
    }
    /**
     * 获取当前用户的考试的试卷历史记录
     * @param session HttpSession
     * @return 返回历史记录信息
     */
    @Override
    public Message examTestHistory(Integer examId, HttpSession session) {
        User user = (User) session.getAttribute(SessionKeys.USER_INFO);
        ExmResult exmResultExample = new ExmResult();
        ExmTestPaper exmTestPaperExample = new ExmTestPaper();
        exmTestPaperExample.setTestPaperId(examId);
        exmResultExample.setTestPaper(exmTestPaperExample);
        List<Map<String, Object>> list = exmResultRepository.examTestHistory(examId, user.getUserId());
        if (list == null || list.size()==0){
            throw Exceptions.EXAM_RESULT_NOT_EXIST;
        }
        return Message.success().setData(list);
    }

    /**
     * 获取某答卷的答案详情
     * @param resultId 答卷编号
     * @param session HttpSession
     * @return 返回答案详情
     */
    @Override
    public Message examResultAnswer(Integer resultId, HttpSession session) {
        Optional<ExmResult> byId = exmResultRepository.findById(resultId);
        if (!byId.isPresent()){
            throw Exceptions.EXAM_RESULT_NOT_EXIST;
        }
        ExmResult exmResult = byId.get();
        return Message.success().setData(getResultQuestionByType(exmResult));
    }

    /**
     * 获取答卷,包含试题和结果(按题型分开)
     * @param exmResult 传入答卷
     * @return 返回分组后的结果,用ExmResultDto对象存储
     */
    private ExmResultDto getResultQuestionByType(ExmResult exmResult){
        ExmResultDto result = new ExmResultDto();
        BeanUtils.copyProperties(exmResult, result);

        ExmTestPaper exmTestPaper = exmResult.getTestPaper();
        int radioNum = exmTestPaper.getRadioNum();
        int mulitpleNum = exmTestPaper.getMulitpleNum();
        int judgeNum = exmTestPaper.getJudgeNum();

        Map<Integer, List<ExmResultQuestion>> groupMap = new HashMap<>(radioNum+mulitpleNum+judgeNum);
        groupMap.put(QuestionType.RADIO, new ArrayList<>(radioNum));
        groupMap.put(QuestionType.MULITPLE, new ArrayList<>(mulitpleNum));
        groupMap.put(QuestionType.JUDGE, new ArrayList<>(judgeNum));

        for (ExmResultQuestion question : exmResult.getQuestions()) {
            groupMap.get(question.getQuestionKindInt()).add(question);
        }
        List<ResultQuestions> resultQuestionsArrayList = new ArrayList<>();
        int oneScore = exmTestPaper.getTotalSorce()/exmTestPaper.getTotalNum();
        resultQuestionsArrayList.add(new ResultQuestions(
                QuestionType.RADIO, oneScore, radioNum, groupMap.get(QuestionType.RADIO)));
        resultQuestionsArrayList.add(new ResultQuestions(
                QuestionType.MULITPLE, oneScore, mulitpleNum, groupMap.get(QuestionType.MULITPLE)));
        resultQuestionsArrayList.add(new ResultQuestions(
                QuestionType.JUDGE, oneScore, judgeNum, groupMap.get(QuestionType.JUDGE)));

        result.setExamQuestions(resultQuestionsArrayList);
        return result;
    }

    /**
     * 答卷成绩计算
     * @param session HttpSession
     * @param answers 考生答案
     * @param exmTestPaper 考卷
     * @param exam 考卷内容
     * @param time 提交时间
     * @param duration 答题时长
     * @return 返回计算结果
     */
    private ExmResult examCalculation(
            HttpSession session, Map<Integer, Object> answers,
            ExmTestPaper exmTestPaper,
            Exam exam, int time, int duration){
        User user  = (User) session.getAttribute(SessionKeys.USER_INFO);
        ExmResult exmResult = new ExmResult();
        exmResult.setTestPaper(exmTestPaper);
        exmResult.setResultDate(new Date(time*1000L));
        exmResult.setUserId(user.getUserId());
        exmResult.setVersion(1);
        exmResult.setDuration(duration);

        List<ExmResultQuestion> exmResultQuestionList = new ArrayList<>();
        int successNum = 0;
        int score = 0;
        for (Questions examQuestion : exam.getExamQuestions()) {
            for (ExmQuestionLib question : examQuestion.getQuestions()) {
                String refAnswer = question.getRefAnswer();
                Integer questionLibId = question.getQuestionLibId();
                Object answer = answers.get(questionLibId);
                if (answer != null && StringUtils.isEmpty(answer)){
                    answer = null;
                }
                Integer questionKind = question.getQuestionKind();

                if (answer != null && QuestionType.MULITPLE == questionKind) {
                    answer =  ((List) answer).toArray(new String[0]);
                }
                if (
                    QuestionType.MULITPLE == questionKind ?
                            checkMulitple(answer, refAnswer) : refAnswer.equals(answer)){
                    successNum++;
                    score += examQuestion.getQuestionScore();
                }
                if (answer != null && QuestionType.MULITPLE == questionKind) {
                    answer = Arrays.toString((String[])answer);
                }
                addAnswerToList(exmResultQuestionList, question, answer);
            }
        }

        exmResult.setErrorNum(exmTestPaper.getTotalNum()-successNum);
        exmResult.setSorce(score);
        exmResult.setSucessNum(successNum);
        exmResult.setQuestions(exmResultQuestionList);
        exmResultRepository.save(exmResult);

        return exmResult;
    }

    /**
     * 检查多选题选项是否正确
     * @param answer 考生答案
     * @param refAnswer 参考答案
     * @return 返回是否正确
     */
    private boolean checkMulitple(Object answer, String refAnswer){
        if (answer == null) { return false; }
        String[] answerArr = (String[]) answer;

        String[] refAnswerArr = castStringToArray(refAnswer);

        StringUtils.sortStringArray(answerArr);
        StringUtils.sortStringArray(refAnswerArr);
        return Arrays.deepEquals(answerArr, refAnswerArr);
    }

    /**
     * 将字符串转为数组
     * @param str 数组字符串
     * @return 返回数组
     */
    private String[] castStringToArray(String str){
        return str.replaceAll("[\\s\\[\\]]+", "").split(",");
    }

    /**
     * 添加答案到答案集合
     * @param list 答案集合
     * @param question 试题
     * @param answer 考生答案
     */
    private void addAnswerToList(List<ExmResultQuestion>  list, ExmQuestionLib question, Object answer){
        ExmResultQuestion exmResultQuestion = new ExmResultQuestion();
//        拷贝
        BeanUtils.copyProperties(question, exmResultQuestion);
//        修改转型
        exmResultQuestion.setQuestionKind(question.getQuestionKind().toString());
        exmResultQuestion.setAnswer((String) answer);
        exmResultQuestion.setQuestionLibId(question.getQuestionLibId());
        list.add(exmResultQuestion);
    }

    /**
     * 获取试卷
     * @param examId 考试Id
     * @return 返回详细试卷
     */
    @Override
    public Message getExam(Integer examId, HttpSession session) {
        User user = (User) session.getAttribute(SessionKeys.USER_INFO);
        ExmTestPaper exmTestPaper = exmTestPaperRepository.getExam(user.getUserId(), examId);
        Date now = new Date();
        if (Objects.isNull(exmTestPaper)||
                !now.after(exmTestPaper.getStartDate()) || now.after(exmTestPaper.getEndDate())) {
            throw Exceptions.EXAM_NOT_EXIST;
        }
        List<Questions> questions;
        int radioNum = exmTestPaper.getRadioNum();
        int mulitpleNum = exmTestPaper.getMulitpleNum();
        int judgeNum = exmTestPaper.getJudgeNum();
        int oneScore = exmTestPaper.getTotalSorce()/exmTestPaper.getTotalNum();
        switch (exmTestPaper.getQuestionRule()){
            case QuestionRule.PRESET:
                questions = getPresetExamQuestions(exmTestPaper,
                        radioNum, mulitpleNum, judgeNum, oneScore);
                break;
            case QuestionRule.RANDOM:
                questions = getRandomExamQuestions(
                        radioNum, mulitpleNum, judgeNum, oneScore);
                break;
            default:
                throw Exceptions.EXAM_SETTING_ERROR;
        }
        Exam exam = Exam.get(exmTestPaper, questions);

        session.setAttribute(SessionKeys.EXAM_TEST, exam);
        Message result = Message.success().setData(exam);
        int date = getNowTime();
//        date += 5;
        session.setAttribute(SessionKeys.EXAM_START_TIME, date);
        session.setAttribute(SessionKeys.EXAM_TEST_PAPER, exmTestPaper);
        result.setTimestamp(date);
        return result;
    }

    /**
     * 获取预设试卷试题
     * @param exmTestPaper 试卷对象
     * @param radioNum 单选题数目
     * @param mulitpleNum 多选题数目
     * @param judgeNum 判断题数目
     * @param oneScore 单个题分数
     * @return 返回试卷试题
     */
    private List<Questions> getPresetExamQuestions(ExmTestPaper exmTestPaper,
                                                   int radioNum, int mulitpleNum,
                                                   int judgeNum, int oneScore){
        List<ExmTestQuestion> exmTestQuestions = exmTestPaper.getExmTestQuestions();

        //排序分组(按题型)
        Map<Integer, List<ExmQuestionLib>> map = getQuestionsMapByType(
                exmTestQuestions, radioNum, mulitpleNum, judgeNum);
        List<Questions> result = new ArrayList<>();

        addToResultFromGroupMap(map,result,oneScore,radioNum,mulitpleNum,judgeNum);
        return result;
    }

    /**
     * 将预设试卷的所有试题进行排序,然后按题型分组
     * @param exmTestQuestions 所有试题
     * @param radioNum 单选题数目
     * @param mulitpleNum 多选题数目
     * @param judgeNum 判断题数目
     * @return 返回分组后的试题集合
     */
    private Map<Integer, List<ExmQuestionLib>> getQuestionsMapByType(
            List<ExmTestQuestion> exmTestQuestions,
            int radioNum, int mulitpleNum, int judgeNum
        ){
        Map<Integer, List<ExmQuestionLib>> map = getGroupMap(radioNum, mulitpleNum, judgeNum);

        //分组
        for (ExmTestQuestion exmTestQuestion : exmTestQuestions) {
            ExmQuestionLib questionLib = exmTestQuestion.getQuestionLib();
            map.get(questionLib.getQuestionKind()).add(questionLib);
        }
        return map;
    }
    /**
     * 获取随机生成试卷试题
     * @param radioNum 单选题数目
     * @param mulitpleNum 多选题数目
     * @param judgeNum 判断题数目
     * @param oneScore 单个题分数
     * @return 返回试卷试题
     */
    private List<Questions> getRandomExamQuestions(
            int radioNum, int mulitpleNum, int judgeNum, int oneScore){
        List<Questions> result = new ArrayList<>();

        List<ExmQuestionLib> randQuestionByKind =
                exmQuestionLibRepository.getRandQuestionByKind(radioNum, mulitpleNum, judgeNum);

        Map<Integer, List<ExmQuestionLib>> map = getGroupMap(radioNum, mulitpleNum, judgeNum);
        for (ExmQuestionLib exmQuestionLib : randQuestionByKind) {
            map.get(exmQuestionLib.getQuestionKind()).add(exmQuestionLib);
        }

        addToResultFromGroupMap(map,result,oneScore,radioNum,mulitpleNum,judgeNum);
        return result;
    }

    /**
     * 将分组map添加到服务层返回集合
     * @param map 分组的map
     * @param result 返回的集合
     * @param oneScore 单个题分数
     * @param radioNum 单选题数目
     * @param mulitpleNum 多选题数目
     * @param judgeNum 判断题数目
     */
    private void addToResultFromGroupMap(
            Map<Integer, List<ExmQuestionLib>> map, List<Questions> result,
            int oneScore, int radioNum, int mulitpleNum, int judgeNum){
        result.add(new Questions(
                QuestionType.RADIO, oneScore, radioNum, map.get(QuestionType.RADIO)));
        result.add(new Questions(
                QuestionType.MULITPLE, oneScore, mulitpleNum, map.get(QuestionType.MULITPLE)));
        result.add(new Questions(
                QuestionType.JUDGE, oneScore, judgeNum, map.get(QuestionType.JUDGE)));
    }

    /**
     * 获取分组Map
     * @param radioNum 单选题数目
     * @param mulitpleNum 多选题数目
     * @param judgeNum 判断题数目
     * @return 返回分组用的map
     */
    private Map<Integer, List<ExmQuestionLib>> getGroupMap(
            int radioNum, int mulitpleNum, int judgeNum){
        Map<Integer, List<ExmQuestionLib>> map = new HashMap<>(radioNum+mulitpleNum+judgeNum);
        map.put(1, new ArrayList<>(radioNum));
        map.put(2, new ArrayList<>(mulitpleNum));
        map.put(3, new ArrayList<>(judgeNum));
        return map;
    }

    /**
     * 获取当前时间戳(秒级)
     * @return 返回当前时间戳(秒级)
     */
    private int getNowTime(){
         return (int) (System.currentTimeMillis() / 1000L);
    }
}
