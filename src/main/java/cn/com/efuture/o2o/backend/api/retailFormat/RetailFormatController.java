package cn.com.efuture.o2o.backend.api.retailFormat;

import cn.com.efuture.o2o.backend.mybatis.entity.RetailFormat;
import cn.com.efuture.o2o.backend.mybatis.service.RetailFormatServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/retailFormat")
public class RetailFormatController {
    private final RetailFormatServiceImpl retailFormatService;
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public RetailFormatController(RetailFormatServiceImpl retailFormatService) {
        this.retailFormatService = retailFormatService;
    }


    /**
     * 无条件获取业态
     *
     * @return json
     */
    @GetMapping(value = "/getRetailFormatList")
    public JsonResponse getRetailFormatList() {
        logger.info("------------getRetailFormatList-----------");
        try {
            List<RetailFormat> list = retailFormatService.getRetailFormatList();
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /**
     * 根据用户Id获取用户的业态
     * @param data {"userId":"xxx"}
     * @return json
     */
    @GetMapping(value = "/getRetailFormatListByUserId")
    public JsonResponse getRetailFormatListByUserId(@RequestParam(value = "data") String data) {
        logger.info("------------getRetailFormatListByUserId-----------");
        try {
            Map<String, Object> map = JSONObject.parseObject(data);
            List<RetailFormat> list = retailFormatService.getRetailFormatListByUserId(map);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }




    /**
     * 获取用户默认业态
     * @return json
     */
    @GetMapping(value = "/getRetailFormatListByUser")
    public JsonResponse getRetailFormatListByUser() {
        logger.info("------------getRetailFormatListByUser-----------");
        try {
            Map<String, Object> map = new HashMap<>();
            String userName = SessionHelper.getUserName();
            map.put("userName", userName);
            List<RetailFormat> list = retailFormatService.getRetailFormatListByUserName(map);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }
}
