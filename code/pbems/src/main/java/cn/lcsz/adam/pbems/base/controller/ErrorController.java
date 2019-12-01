package cn.lcsz.adam.pbems.base.controller;

import cn.lcsz.adam.pbems.base.model.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * 重写原本的异常处理控制器，在其中调用默认错误处理的方法，并加以包装
 * 达到返回自定义错误格式的目的
 * @author yufs
 */
@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    private final BasicErrorController basicErrorController;

    public ErrorController(ErrorAttributes errorAttributes, ServerProperties errorProperties) {
        super(errorAttributes, Collections.emptyList());
        this.basicErrorController = new BasicErrorController(errorAttributes, errorProperties.getError());
    }

    @RequestMapping
    public ResponseEntity<Message> myError(HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> error = basicErrorController.error(request);
        Map<String, Object> body = error.getBody();
        assert body != null;
        Message message = new Message(
                (int) body.get("status"),
                body.get("message").toString()
        );

        return new ResponseEntity<>(message, getStatus(request));
    }

    @Override
    public String getErrorPath() {
        return basicErrorController.getErrorPath();
    }
}
