package cn.lcsz.adam.pbems.base.config;

/**
 * 包含所有返回Message的字符串
 */
public class ReturnMessage {
    //数据校验失败返回
    public static final String VALID_FAIL = "数据格式不正确";
    //登录成返回
    public static final String LOGIN_SUCCESS = "登录成功";
    //登录失败返回
    public static final String LOGIN_FAIL = "登录失败,用户名或电话号码错误";
    //成功默认返回
    public static final String RETURN_SUCCESS = "success";
    //失败默认返回
    public static final String RETURN_FAIL = "fail";
    //未登录访问受限返回
    public static final String NOT_LOGIN = "未登录";
}
