package cn.kyle.esol.base.model.constant;

import cn.kyle.esol.base.exception.CodeMessageException;

/**
 * 程序抛出的异常
 * @author yufs
 */
public class Exceptions {
    /**
     * 考试无效异常
     */
    public static final CodeMessageException EXAM_INVALID =
            new CodeMessageException(ReturnMessage.EXAM_INVALID);
    /**
     * 考试超时异常
     */
    public static final CodeMessageException EXAM_TIMEOUT =
            new CodeMessageException(ReturnMessage.EXAM_TIMEOUT);
    /**
     * 考试不存在异常
     */
    public static final CodeMessageException EXAM_NOT_EXIST =
            new CodeMessageException(ReturnMessage.EXAM_NOT_EXIST);
    /**
     * 考试设置有误异常
     */
    public static final CodeMessageException EXAM_SETTING_ERROR =
            new CodeMessageException(ReturnMessage.EXAM_SETTING_ERROR);
    /**
     * 答卷不存在异常
     */
    public static final CodeMessageException EXAM_RESULT_NOT_EXIST =
            new CodeMessageException(ReturnMessage.EXAM_RESULT_NOT_EXIST);
}
