package cn.kyle.esol.manage.controller;

import cn.kyle.esol.base.model.dto.Message;
import cn.kyle.esol.manage.service.ManageDeptService;
import cn.kyle.esol.repository.user.model.po.Dept;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 管理员用户相关控制器
 * @author Kyle
 */
@RestController
@RequestMapping("/manage/dept")
public class ManageDeptController {
    private final ManageDeptService deptService;

    @Autowired
    public ManageDeptController(ManageDeptService deptService) {
        this.deptService = deptService;
    }

    @PostMapping("/list")
    public Message list(@RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "1") Integer pageIndex,
                        @RequestParam(defaultValue = "20") Integer pageSize,
                        HttpSession session) {
        return deptService.list(name, pageIndex, pageSize, session);
    }
    @GetMapping("/find/{id}")
    public Message get(@PathVariable Integer id) {
        return deptService.get(id);
    }
    @PostMapping("/delete")
    public Message delete(@RequestBody JSONArray ids) {
        return deptService.delete(ids.toJavaList(Integer.TYPE));
    }
    @PostMapping("/save")
    public Message save(@RequestBody Dept dept) {
        return deptService.save(dept);
    }
}
