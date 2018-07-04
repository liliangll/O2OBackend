package cn.com.efuture.o2o.backend.api.o2oGoodsLockPrice;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsLockPrice;
import cn.com.efuture.o2o.backend.mybatis.service.O2oGoodsLockPriceServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/o2oGoodsLockPrice")
public class o2oGoodsLockPriceController {

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final O2oGoodsLockPriceServiceImpl o2oGoodsLockPriceService;

    public o2oGoodsLockPriceController(O2oGoodsLockPriceServiceImpl o2oGoodsLockPriceService) {
        this.o2oGoodsLockPriceService = o2oGoodsLockPriceService;
    }

    @RequestMapping(value = "/getO2oGoodsLockPriceByItemCode", method = RequestMethod.GET)
    public JsonResponse getO2oGoodsLockPriceByItemCode(@RequestParam(value = "data") String data) {
        logger.info("------------getO2oGoodsLockPriceByItemCode-----------");
        try {
            JSONObject map = JSONObject.parseObject(data);
            List<O2oGoodsLockPrice> list = o2oGoodsLockPriceService.getO2oGoodsLockPriceByItemCode(map);
            //前端虚拟 dom 树需要 key
            int key = 0;
            for (O2oGoodsLockPrice o2oGoodsLockPrice : list) {
                o2oGoodsLockPrice.setKey(key++);
            }
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @RequestMapping(value = "/getO2oGoodsLockPriceList", method = RequestMethod.GET)
    public JsonResponse getO2oGoodsLockPriceList(@RequestParam(value = "data") String data) {
        logger.info("------------getO2oGoodsLockPriceList-----------");
        try {
           JSONObject map = JSONObject.parseObject(data);
            map.put("userName", SessionHelper.getUserName());
            long count = ParameterHelper.getCount(map, o2oGoodsLockPriceService);
            // 设置分页信息
            int page = map.getIntValue("page");
            int pageSize = map.getIntValue("pageSize");
            map.put("page",(page-1)*pageSize);
            map.put("pageSize",pageSize);

            List<O2oGoodsLockPrice> list = o2oGoodsLockPriceService.getO2oGoodsLockPriceList(map);

            //前端虚拟 dom 树需要 key
            int key = 0;
            for (O2oGoodsLockPrice o2oGoodsLockPrice : list) {
                o2oGoodsLockPrice.setKey(key++);
            }
            return JsonResponse.ok(count,list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }
}
