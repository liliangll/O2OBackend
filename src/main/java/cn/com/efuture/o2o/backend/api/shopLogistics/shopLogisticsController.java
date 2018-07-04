package cn.com.efuture.o2o.backend.api.shopLogistics;

import cn.com.efuture.o2o.backend.mybatis.entity.ShopLogistics;
import cn.com.efuture.o2o.backend.mybatis.service.ShopLogisticsServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 门店物流
 */
@RestController
@RequestMapping("/api/shopLogistics")
public class shopLogisticsController {

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ShopLogisticsServiceImpl shopLogisticsService;

    public shopLogisticsController(ShopLogisticsServiceImpl shopLogisticsService) {
        this.shopLogisticsService = shopLogisticsService;
    }

    /**
     *  获取门店物流列表
     *
     * @return shopLogisticsList
     */
    @RequestMapping(value = "/getShopLogisticsList", method = RequestMethod.GET)
    public JsonResponse getShopLogisticsList() {
        try {
            logger.info("------------getShopLogisticsList-----------");
            List<ShopLogistics> shopLogisticsList = shopLogisticsService.getShopLogisticsList();
            return JsonResponse.ok(shopLogisticsList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }


    @RequestMapping(value = "/insertShopLogistics", method = RequestMethod.POST)
    public JsonResponse insertShopLogistics(@RequestParam(value = "data") String data) {
        try {
            logger.info("------------insertShopLogistics-----------");
            Map<String, Object> map = JSONObject.parseObject(data);
            return JsonResponse.ok();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }



}
