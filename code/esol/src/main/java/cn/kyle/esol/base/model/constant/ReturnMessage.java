package cn.kyle.esol.base.model.constant;

/**
 * 包含所有返回Message的字符串
 * @author Kyle
 */
public class ReturnMessage {
    /**
     * 登录成返回
     */
    public static final String LOGIN_SUCCESS = "登录成功";
    /**
     * 登录失败返回
     */
    public static final String LOGIN_FAIL = "登录失败,用户名或电话号码错误";
    /**
     * 成功默认返回
     */
    public static final String RETURN_SUCCESS = "success";
    /**
     * 失败默认返回
     */
    public static final String RETURN_FAIL = "fail";
    /**
     * 未登录访问受限返回信息
     */
    public static final String NOT_LOGIN = "未登录";
    /*
     * 获取考试列表出错返回信息
     public static final String EXAM_LIST_MAP_TO_BEAN_ERROR = "获取考试列表出错";
     */
    /**
     * 获取试卷不存在返回信息
     */
    static final String EXAM_NOT_EXIST = "试卷不存在";
    /**
     * 无效考试
     */
    static final String EXAM_INVALID = "无效考试";
    /**
     * 试卷不符合设定返回信息
     */
    static final String EXAM_SETTING_ERROR = "试卷设定有误";
    /**
     * 试卷答题时间超过考试时长返回信息
     */
    static final String EXAM_TIMEOUT = "答题时间超过考试时长";
    /**
     * 答卷信息不存在返回信息
     */
    static final String EXAM_RESULT_NOT_EXIST = "答卷信息不存在";
}
