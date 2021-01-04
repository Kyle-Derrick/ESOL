package cn.kyle.esol.manage.service.impl;

import cn.kyle.esol.base.exception.CodeMessageException;
import cn.kyle.esol.base.model.constant.ReturnMessage;
import cn.kyle.esol.base.model.constant.SessionKeys;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.base.util.BaseUtils;
import cn.kyle.esol.manage.model.PageResponse;
import cn.kyle.esol.manage.service.ManageUserService;
import cn.kyle.esol.repository.user.model.constant.UserType;
import cn.kyle.esol.repository.user.model.dto.UserInfo;
import cn.kyle.esol.repository.user.model.po.User;
import cn.kyle.esol.repository.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户相关服务实现
 * @author Kyle
 */
@Service
@Validated
@Slf4j
public class ManageUserServiceImpl implements ManageUserService {
    private final UserRepository userRepository;

    @Autowired
    public ManageUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Message login(String userName, String passWord, HttpSession session) {
        log.info(userName);
        User user = userRepository.findOneByUserNameAndPassWord(
                userName,
                DigestUtils.md5DigestAsHex(passWord.getBytes()));
        Message message;
        if (!Objects.isNull(user) &&
                (user.getUserKind() == UserType.ADMIN || user.getUserKind() == UserType.TEACHER)){
            session.setAttribute(SessionKeys.MANAGE_USER_INFO, user);
            message = Message.success(ReturnMessage.LOGIN_SUCCESS);
            UserInfo userInfo = new UserInfo();
            userInfo.setDataByUser(user);
            message.setData(userInfo);
        }else {
            message = Message.error(ReturnMessage.LOGIN_FAIL);
        }
        log.info(userName+message.getMsg());
        return message;
    }

    @Override
    public Message getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(SessionKeys.MANAGE_USER_INFO);
        UserInfo userInfo = new UserInfo();
        userInfo.setDataByUser(user);

        return Message.success(ReturnMessage.RETURN_SUCCESS).setData(userInfo);
    }

    @Override
    public Message list(String username,
                        Integer kind,
                        Integer pageIndex,
                        Integer pageSize,
                        HttpSession session) {
        try {
            User userDemo = new User().setUserName(username)
                    .setUserKind(kind);
            Page<User> page = userRepository.findAll(
                    Example.of(userDemo,
                        ExampleMatcher.matching().withMatcher("userName",
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
    public Message get(Integer id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                throw new CodeMessageException("未找到指定信息！");
            }
            return Message.success("查询成功").setData(user.get());
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
            userRepository.deleteByIds(ids);
            return Message.success("删除成功");
        }catch (Exception e) {
            throw new CodeMessageException("删除失败！");
        }
    }

    @Override
    @Transactional
    public Message save(User user) {
        String prefix = user.getUserId() == null ? "添加" : "更新";
        try {
            user.setVersion(1);
            if (user.getUserId() != null) {
                Optional<User> tmp = userRepository.findById(user.getUserId());
                if (!tmp.isPresent()){
                    throw new CodeMessageException(prefix.concat("所更新的信息不存在。"));
                }
                User userTmp = tmp.get();
                BaseUtils.copyProperties(user, userTmp);
                user = userTmp;
            }
            if (user.getPassWord() != null) {
                user.setPassWord(DigestUtils.md5DigestAsHex(user.getPassWord().getBytes()));
            }
            userRepository.save(user);
            return Message.success(prefix.concat("成功！"));
        } catch (DataIntegrityViolationException e) {
            throw new CodeMessageException("重复添加！");
        } catch (Exception e) {
            throw new CodeMessageException(prefix.concat("失败, 请检查信息。"));
        }
    }
}
