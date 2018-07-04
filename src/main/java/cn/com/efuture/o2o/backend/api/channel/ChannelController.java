package cn.com.efuture.o2o.backend.api.channel;

import cn.com.efuture.o2o.backend.mybatis.entity.Channel;
import cn.com.efuture.o2o.backend.mybatis.service.ChannelServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
    private final ChannelServiceImpl channelServiceImpl;
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public ChannelController(ChannelServiceImpl channelServiceImpl) {
        this.channelServiceImpl = channelServiceImpl;
    }

    @ApiOperation(value = "获取渠道(channelId>=10)列表", notes = "获取渠道列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getChannelList", method = RequestMethod.GET)
    public JsonResponse getChannelList(@RequestParam(value = "data",required = false) String data) {
        logger.info("------------getChannelList-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            List<Channel> list = channelServiceImpl.getChannelList(map);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @RequestMapping(value = "/getChannel", method = RequestMethod.GET)
    public JsonResponse getChannel(@RequestParam(value = "data") String data) {
        logger.info("------------getChannel-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            Channel channel =  channelServiceImpl.getChannel(map);
            return JsonResponse.ok(channel);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }
}
