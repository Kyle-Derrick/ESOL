package cn.kyle.esol.base.controller;

import cn.kyle.esol.base.exception.CodeMessageException;
import cn.kyle.esol.base.model.constant.VaildMessage;
import cn.kyle.esol.base.model.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 异常处理控制器
 * @author yufs
 */
@ControllerAdvice
@Slf4j
public class ExceptionController {
    /**
     * 方法级别数据校验出错处理
     * @param e 捕获的异常
     * @return 返回错误信息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Message handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        String[] mess = new String[constraintViolations.size()];
        int i = 0;
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            mess[i++] = constraintViolation.getMessage();
        }
        Message error = Message.error(VaildMessage.VALID_FAIL+ Arrays.toString(mess));
        log.error(error.getMsg());

        return error;
    }

    /**
     *
     * @param e 捕获的异常
     * @return 返回错误信息
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Message handleBindException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String[] mess = new String[fieldErrors.size()];
        int i = 0;
        for (FieldError fieldError : fieldErrors) {
            mess[i++] = fieldError.getDefaultMessage();
        }
        Message error = Message.error(VaildMessage.VALID_FAIL+ Arrays.toString(mess));
        log.error(error.getMsg());
        return error;
    }

    /**
     * 包含返回信息的异常处理
     * @param e 捕获的异常
     * @return 返回错误信息
     */
    @ExceptionHandler(CodeMessageException.class)
    @ResponseBody
    public Message handleHaveMessageException(CodeMessageException e){
        Message error = new Message(e.getCode(), e.getMessage());
        error.setData(e.getData());
        log.error(error.getMsg());
        return error;
    }
}
