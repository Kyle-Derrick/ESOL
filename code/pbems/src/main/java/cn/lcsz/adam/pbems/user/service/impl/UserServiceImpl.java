package cn.lcsz.adam.pbems.user.service.impl;

import cn.lcsz.adam.pbems.base.model.constant.ReturnMessage;
import cn.lcsz.adam.pbems.base.model.constant.SessionKeys;
import cn.lcsz.adam.pbems.base.model.dto.Message;
import cn.lcsz.adam.pbems.user.model.dto.UserInfo;
import cn.lcsz.adam.pbems.user.model.po.User;
import cn.lcsz.adam.pbems.user.repository.UserRepository;
import cn.lcsz.adam.pbems.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

/**
 * 用户相关服务实现
 * @author yufs
 */
@Service
@Validated
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Message login(String userName, String tel, HttpSession session) {
        log.info(userName,tel);
        User user = userRepository.findOneByUserNameAndTelphone(userName, tel);
        Message message;
        if (!Objects.isNull(user)){
            session.setAttribute(SessionKeys.USER_INFO, user);
            message = Message.success(ReturnMessage.LOGIN_SUCCESS);
        }else {
            message = Message.error(ReturnMessage.LOGIN_FAIL);
        }
        log.info(userName+message.getMsg());
        return message;
    }

    @Override
    public Message getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(SessionKeys.USER_INFO);
        UserInfo userInfo = new UserInfo();
        userInfo.setDataByUser(user);

        Map<String, Object> userExamNumAndAnswerAccuracy =
                userRepository.getUserExamNumAndAnswerAccuracy(user.getUserId());
        userInfo.setExamNum(Integer.parseInt(userExamNumAndAnswerAccuracy.get("examNum").toString()));
        Object answerAccuracy = userExamNumAndAnswerAccuracy.get("answerAccuracy");
        double accuracy = 0.0;
        if (!Objects.isNull(answerAccuracy)){
            accuracy = Double.parseDouble(answerAccuracy.toString());
        }
        userInfo.setAnswerAccuracy((int)(accuracy*100));

        userInfo.setRank(userRepository.getUserExamRank(user.getUserId(), accuracy));
        return Message.success(ReturnMessage.RETURN_SUCCESS).setData(userInfo);
    }
}
