package cn.kyle.esol.base.controller;

import cn.kyle.esol.base.model.constant.ReturnMessage;
import cn.kyle.esol.base.model.dto.Message;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 一些简单数据返回,例如广告
 * @author yufs
 */
@RestController
@Slf4j
public class OtherController implements BaseController{
    @GetMapping("/ads")
    public Message ads(){
        String tmp = "img/2-1.png";
        String ads = "[{\"title\":\"祝考试顺利\",\"imgUrl\":\"" + tmp + "\"" +
                ",\"herfUrl\":\"javascript:void(0);\"}" +
                ",{\"title\":\"祝考试顺利" +
                "\",\"imgUrl\":\"" + tmp + "\",\"herfUrl\":\"javascript:void(0);\"}]";
        List list = JSONObject.parseObject(ads, List.class);
        return Message.success(ReturnMessage.RETURN_SUCCESS).setData(list);
    }
}
