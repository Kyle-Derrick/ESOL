package cn.kyle.esol.api.service.impl;

import cn.kyle.esol.base.model.constant.ReturnMessage;
import cn.kyle.esol.base.model.constant.SessionKeys;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.repository.user.model.dto.UserInfo;
import cn.kyle.esol.repository.user.model.po.User;
import cn.kyle.esol.repository.user.repository.UserRepository;
import cn.kyle.esol.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

/**
 * 用户相关服务实现
 * @author Kyle
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
    public Message login(String userName, String passWord, HttpSession session) {
        log.info(userName);
        User user = userRepository.findOneByUserNameAndPassWord(
                userName,
                DigestUtils.md5DigestAsHex(passWord.getBytes()));
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
