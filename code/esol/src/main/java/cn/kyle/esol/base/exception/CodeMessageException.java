package cn.kyle.esol.base.exception;

/**
 * 带有返回信息的异常
 * @author Kyle
 */
public class CodeMessageException extends RuntimeException {

    private static final long serialVersionUID = 3207634247428338858L;

    /**
     * 该异常携带的数据信息
     */
    private Object data;
    /**
     * 该异常返回代码
     */
    private int code = -1;

    public CodeMessageException(String message) {
        this(message, null, null);
    }

    public CodeMessageException(String message, Object data) {
        this(message, null, data);
    }
    public CodeMessageException(String message, int code) {
        this(message, code, null);
    }
    public CodeMessageException(String message, Integer code, Object data) {
        super(message);
        this.data = data;
        this.code = code == null ? this.code : code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }
}
