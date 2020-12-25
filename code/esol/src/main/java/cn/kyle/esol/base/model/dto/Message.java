package cn.kyle.esol.base.model.dto;

import cn.kyle.esol.base.model.constant.ReturnMessage;
import lombok.Getter;

/**
 * 返回给前端数据时需要用到的数据传输对象
 * @author yufs
 */
@Getter
public class Message {
    /**
     * 返回代码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回时间
     */
    private Integer timestamp;
    /**
     * 返回具体数据
     */
    private Object data;

    public Message(int code, String msg) {
        this.code = code;
        this.msg = msg;
        long timestamp = System.currentTimeMillis()/1000;
        this.timestamp = (int) timestamp;
    }

    public static Message error(String info){
        return new Message(-1, info);
    }
    public static Message error(){
        return new Message(-1, ReturnMessage.RETURN_FAIL);
    }

    public static Message success(String info){
        return new Message(1, info);
    }
    public static Message success(){
        return new Message(1, ReturnMessage.RETURN_SUCCESS);
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Message setData(Object data) {
        this.data = data;
        return this;
    }
}
