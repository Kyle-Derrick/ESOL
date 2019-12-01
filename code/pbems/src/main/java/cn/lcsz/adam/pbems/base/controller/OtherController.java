package cn.lcsz.adam.pbems.base.controller;

import cn.lcsz.adam.pbems.base.model.constant.ReturnMessage;
import cn.lcsz.adam.pbems.base.model.dto.Message;
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
        String tmp = "https://lanhu.oss-cn-beijing.aliyuncs.com/ps7fe7aa92049542bf-8303-4e08-b12c-bc4f241638e3";
        String ads = "[{\"title\":\"学习路上\",\"imgUrl\":\"" + tmp + "\"" +
                ",\"herfUrl\":\"http://cpc.people.com.cn/xuexi/\"}" +
                ",{\"title\":\"习近平在中央和国家机关党的建设工作会议上的讲话" +
                "\",\"imgUrl\":\"" + tmp + "\",\"herfUrl\":\"http://jh" +
                "sjk.people.cn/article/31433005\"}]";
        List list = JSONObject.parseObject(ads, List.class);
        return Message.success(ReturnMessage.RETURN_SUCCESS).setData(list);
    }
}
