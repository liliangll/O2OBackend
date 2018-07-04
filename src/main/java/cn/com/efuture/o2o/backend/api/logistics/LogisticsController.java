package cn.com.efuture.o2o.backend.api.logistics;

import cn.com.efuture.o2o.backend.mybatis.entity.LogisticsFreeShipping;
import cn.com.efuture.o2o.backend.mybatis.entity.LogisticsTime;
import cn.com.efuture.o2o.backend.mybatis.entity.LogisticsType;
import cn.com.efuture.o2o.backend.mybatis.service.LogisticsServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  物流相关api
 */
@RestController
@RequestMapping("/api/logistics")
public class LogisticsController {
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LogisticsServiceImpl logisticsService;

    public LogisticsController(LogisticsServiceImpl logisticsService) {
        this.logisticsService = logisticsService;
    }

    // 获取所有物流类型
    @RequestMapping(value = "/getLogisticsTypeList", method = RequestMethod.GET)
    public JsonResponse getLogisticsTypeList() {
        try {
            logger.info("------------getLogisticsTypeList-----------");
            List<LogisticsType> logisticsTypeList = logisticsService.getLogisticsTypeList();
            return JsonResponse.ok(logisticsTypeList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    // 根据物流类型获取物流时间
    @RequestMapping(value = "/getLogisticsTimeList", method = RequestMethod.GET)
    public JsonResponse getLogisticsTimeList(@RequestParam(value = "data")String data) {
        try {
            logger.info("------------getLogisticsTimeList-----------");
            List<LogisticsTime> logisticsTypeList = logisticsService.getLogisticsTimeList(data);
            return JsonResponse.ok(logisticsTypeList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }


    // 根据物流类型获取免邮规则
    @RequestMapping(value = "/geLogisticsFreeShippingList", method = RequestMethod.GET)
    public JsonResponse geLogisticsFreeShippingList(@RequestParam(value = "data")String data) {
        try {
            logger.info("------------geLogisticsFreeShippingList-----------");
            List<LogisticsFreeShipping> logisticsTypeList = logisticsService.getLogisticsFreeShippingList(data);
            return JsonResponse.ok(logisticsTypeList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }
}
