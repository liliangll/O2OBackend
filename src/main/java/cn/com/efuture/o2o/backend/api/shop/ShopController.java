package cn.com.efuture.o2o.backend.api.shop;

import cn.com.efuture.o2o.backend.mybatis.entity.Shop;
import cn.com.efuture.o2o.backend.mybatis.service.ShopServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop")
public class ShopController {
    private final ShopServiceImpl shopServiceImpl;
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public ShopController(ShopServiceImpl shopServiceImpl) {
        this.shopServiceImpl = shopServiceImpl;
    }

    @ApiOperation(value = "获取家乐福门店列表", notes = "获取家乐福门店列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getShopList", method = RequestMethod.GET)
    public JsonResponse getShopList(@RequestParam(value = "data", required = false) String data) {
        logger.info("------------getShopList-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            List<Shop> list = shopServiceImpl.getShopList(map);
            PageInfo<Shop> pageInfo = new PageInfo<>(list);
            JSONObject city = new JSONObject();
            JSONArray cityList = new JSONArray();
            JSONArray shopList = new JSONArray();
            for (Shop shop : list) {
                if (city.get(shop.getCity()) == null) {
                    city.put(shop.getCity(), shop.getCity());
                    shopList = new JSONArray();
                    shopList.add(shop);
                    JSONObject tempJson = new JSONObject();
                    tempJson.put("city", shop.getCity());
                    tempJson.put("shopList", shopList);
                    cityList.add(tempJson);
                } else {
                    shopList.add(shop);
                }
            }
            return JsonResponse.ok(pageInfo.getTotal(), cityList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }


    @ApiOperation(value = "根据用户获取家乐福门店列表", notes = "根据用户获取家乐福门店列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getShopListByUserName", method = RequestMethod.GET)
    public JsonResponse getShopListByUserName(@RequestParam(value = "data", required = false) String data) {
        logger.info("------------getShopListByUserName-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        String userName = SessionHelper.getUserName();
        if (map.get("userName") == null) {
            map.put("userName", userName);
        }
        try {
            List<Shop> list = shopServiceImpl.getShopList(map);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "获取家乐福城市列表", notes = "获取家乐福城市列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", dataType = "String")
    })
    @RequestMapping(value = "/getCityList", method = RequestMethod.GET)
    public JsonResponse getCityList(@RequestParam(value = "data", required = false) String data) {
        logger.info("------------getCityList-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        String userName = SessionHelper.getUserName();
        map.put("userName", userName);
        try {
            List<Shop> list = shopServiceImpl.getCityList(map);
            JSONArray cityList = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", "全部");
            jsonObject.put("value", "全部");
            cityList.add(jsonObject);
            for (Shop shop : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("key", shop.getCity());
                jsonObject2.put("value", shop.getCity());
                cityList.add(jsonObject2);
            }
            return JsonResponse.ok(cityList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "根据城市获取家乐福门店列表", notes = "根据城市获取家乐福门店列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getShopListByCity", method = RequestMethod.GET)
    public JsonResponse getShopListByCity(@RequestParam(value = "data") String data) {
        logger.info("------------getShopListByCity-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        JSONArray retList = new JSONArray();
        try {
            if (!"全部".equals(map.get("city"))) {
                String userName = SessionHelper.getUserName();
                map.put("userName", userName);
                List<Shop> shopList = shopServiceImpl.getShopList(map);
                retList.addAll(shopList);
            }
            return JsonResponse.ok(retList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "根据城市(可以多选)获取家乐福门店列表", notes = "根据城市获取家乐福门店列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getShopListByCityList", method = RequestMethod.GET)
    public JsonResponse getShopListByCityList(@RequestParam(value = "data") String data) {
        logger.info("------------getShopListByCityList-----------");
        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONArray city = jsonObject.getJSONArray("city");
        JSONArray retList = new JSONArray();
        try {
            Map<String, Object> map = new HashMap<>();
            String userName = SessionHelper.getUserName();
            map.put("userName", userName);
            List<Shop> shopList;
            for (Object s : city) {
                if (!"全部".equals(s)) {
                    map.put("city", s);
                    shopList = shopServiceImpl.getShopList(map);
                    retList.addAll(shopList);
                }
            }
            return JsonResponse.ok(retList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }


    @ApiOperation(value = "根据门店id获取家乐福门店", notes = "根据门店ID获取家乐福门店")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getShopByShopId", method = RequestMethod.GET)
    public JsonResponse getShopByShopId(@RequestParam(value = "data") String data) {
        logger.info("------------getShopByShopId-----------");
        JSONObject map = JSONObject.parseObject(data);
        try {
            Shop shop = shopServiceImpl.getShopByShopId(map);
            return JsonResponse.ok(shop);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }
}
