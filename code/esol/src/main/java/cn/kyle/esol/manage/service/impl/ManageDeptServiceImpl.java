package cn.kyle.esol.manage.service.impl;

import cn.kyle.esol.base.exception.CodeMessageException;
import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.base.util.BaseUtils;
import cn.kyle.esol.manage.model.PageResponse;
import cn.kyle.esol.manage.service.ManageDeptService;
import cn.kyle.esol.repository.user.model.po.Dept;
import cn.kyle.esol.repository.user.repository.DeptRepository;
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
 * 用户相关服务实现
 * @author Kyle
 */
@Service
@Validated
@Slf4j
public class ManageDeptServiceImpl implements ManageDeptService {
    private final DeptRepository deptRepository;

    @Autowired
    public ManageDeptServiceImpl(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }


    @Override
    public Message list(String name,
                        Integer pageIndex,
                        Integer pageSize,
                        HttpSession session) {
        try {
            Dept deptDemo = new Dept().setName(name);
            Example<Dept> example = Example.of(deptDemo,
                    ExampleMatcher.matching().withMatcher("name",
                            ExampleMatcher.GenericPropertyMatchers.contains()
                    )
            );
            Message message = Message.success();
            if (pageIndex == -1) {
                List<Dept> list = deptRepository.findAll(example);
                message.setData(list);
            }else {
                Page<Dept> page = deptRepository.findAll(example,
                        PageRequest.of(pageIndex < 1 ? 0 : pageIndex - 1, pageSize));
                message.setData(PageResponse.identify(page));
            }
            return message;
        }catch (Exception e) {
            throw new CodeMessageException("查询失败！");
        }
    }

    @Override
    public Message get(Integer id) {
        try {
            Optional<Dept> user = deptRepository.findById(id);
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
            deptRepository.deleteByIds(ids);
            return Message.success("删除成功");
        }catch (Exception e) {
            throw new CodeMessageException("删除失败！");
        }
    }

    @Override
    @Transactional
    public Message save(Dept dept) {
        String prefix = dept.getDeptId() == null ? "添加" : "更新";
        try {
            dept.setVersion(1);
            if (dept.getDeptId() != null) {
                Optional<Dept> tmp = deptRepository.findById(dept.getDeptId());
                if (!tmp.isPresent()){
                    throw new CodeMessageException(prefix.concat("所更新的信息不存在。"));
                }
                Dept deptTmp = tmp.get();
                BaseUtils.copyProperties(dept, deptTmp);
                dept = deptTmp;
            }
            if (dept.getParentId() != null) {
                dept.setParentId(0);
                dept.setFullId("/0");
            }else {
                Optional<Dept> parent = deptRepository.findById(dept.getParentId());
                if (!parent.isPresent()) {
                    throw new CodeMessageException("父级部门不存在！");
                }
                Dept tmp = parent.get();
                dept.setFullId(tmp.getFullId().concat("/").concat(tmp.getDeptId().toString()));
            }
            deptRepository.save(dept);
            return Message.success(prefix.concat("成功！"));
        } catch (DataIntegrityViolationException e) {
            throw new CodeMessageException("重复添加！");
        } catch (Exception e) {
            throw new CodeMessageException(prefix.concat("失败, 请检查信息。"));
        }
    }
}
