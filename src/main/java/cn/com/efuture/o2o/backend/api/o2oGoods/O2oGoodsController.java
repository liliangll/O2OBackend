package cn.com.efuture.o2o.backend.api.o2oGoods;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoods;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oInventoryLog;
import cn.com.efuture.o2o.backend.mybatis.service.O2oGoodsServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.NewHashMap;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/o2oGoods")
public class O2oGoodsController {

    protected Logger logger = Logger.getLogger(O2oGoodsController.class);

    private final O2oGoodsServiceImpl o2oGoodsServiceImpl;

    public O2oGoodsController(O2oGoodsServiceImpl o2oGoodsServiceImpl) {
        this.o2oGoodsServiceImpl = o2oGoodsServiceImpl;
    }

    @ApiOperation(value = "获取门店商品列表", notes = "获取门店商品列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "data", required = true, dataType = "String"),
            @ApiImplicitParam(name = "shopId", value = "门店", dataType = "String"),
            @ApiImplicitParam(name = "channelId", value = "渠道", dataType = "Int"),
            @ApiImplicitParam(name = "o2oSkuId", value = "O2O商品编码", dataType = "String"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", dataType = "String"),
            @ApiImplicitParam(name = "barcode", value = "商品条码", dataType = "String"),
            @ApiImplicitParam(name = "itemCode", value = "商品编码", dataType = "String"),
            @ApiImplicitParam(name = "shelved", value = "上下架状态", dataType = "Int"),
            @ApiImplicitParam(name = "status", value = "发布状态", dataType = "Int"),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示数", required = true, dataType = "Int")})
    @RequestMapping(value = "/getO2oGoodsList", method = RequestMethod.GET)
    public JsonResponse getO2oGoodsList(@RequestParam(value = "data") String data) {
        logger.info("------------getO2oGoodsList-----------");
        try {
//        	Map<String, Object> map = JSONObject.parseObject(data);
            final JSONObject map = JSONObject.parseObject(data);
            map.put("userName", SessionHelper.getUserName());

            ParameterHelper.cookPageInfo(map);
            ParameterHelper.cookCityInfo(map);
            long count = ParameterHelper.getCount(map, o2oGoodsServiceImpl);
            // 设置分页信息
            int page = map.getIntValue("page");
            int pageSize = map.getIntValue("pageSize");
            map.put("page",(page-1)*pageSize);
            map.put("pageSize",pageSize);
            // 执行查询
            List<O2oGoods> list = o2oGoodsServiceImpl.getO2oGoodsList(map);
            int key = 0; // 前台业务需要
            for (O2oGoods o2oGoods : list) {
                o2oGoods.setKey(key++);
            }
            return JsonResponse.ok(count, list);
        } catch (Exception e) {
            logger.error(e);
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "获取门店商品信息", notes = "获取门店商品信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "data", required = true, dataType = "Json"),
            @ApiImplicitParam(name = "itemId", value = "商品编码", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "channelId", value = "渠道编码", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "skuId", value = "内部编码", required = true, dataType = "Long")})
    @RequestMapping(value = "/getO2oGoods", method = RequestMethod.GET)
    public JsonResponse getO2oGoods(@RequestParam(value = "data") String data) {
        logger.info("------------getO2oGoods-----------");
        try {
            Map<String, Object> map = JSONObject.parseObject(data);
            map.put("userName", SessionHelper.getUserName());

            O2oGoods o2oGoods = o2oGoodsServiceImpl.getO2oGoods(map);
            return JsonResponse.ok(o2oGoods);
        } catch (Exception e) {
            logger.error(e);
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "修改门店商品信息", notes = "修改门店商品信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "o2oGoods", value = "门店商品", required = true, dataType = "Obj"),
            @ApiImplicitParam(name = "o2oGoods.skuId", value = "内部编码", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "o2oGoods.channelId", value = "渠道编码", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "o2oGoods.shopId", value = "对应家乐福门店编码", required = true, dataType = "String")})
    @RequestMapping(value = "/updateO2oGoods", method = RequestMethod.POST)
    public JsonResponse updateO2oGoods(@RequestParam(value = "data") String data) {
        logger.info("------------updateO2oGoods-----------");
        try {
            String userName = SessionHelper.getUserName();
            O2oGoods o2oGoods = JSONObject.parseObject(data, O2oGoods.class);

            Integer oldStatus = o2oGoods.getOldStatus();
            Integer newStatus = o2oGoods.getNewStatus();

            if (oldStatus != 0 && newStatus == 0) {
                return JsonResponse.notOk("已发布或禁用的门店商品不能修改成未发布");
            }
            if (oldStatus == -1 && newStatus == -1) {
                return JsonResponse.notOk("禁用商品只能修改状态列");
            }

            if (oldStatus == 1 && newStatus == 1 && o2oGoods.getIsSelf() == 1 && "0".equals(o2oGoods.getCategoryId())) {
                return JsonResponse.notOk("商品类别必须填值");
            }

            // 执行更新
            o2oGoodsServiceImpl.updateO2oGoods(o2oGoods, userName);
            return JsonResponse.ok("修改成功");
        } catch (Exception e) {
            logger.error(e, e);
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "发布商品", notes = "发布商品")
    @RequestMapping(value = "/updateO2oGoodsStatus", method = RequestMethod.POST)
    public JsonResponse updateO2oGoodsStatus(@RequestParam(value = "data") String data) {
        logger.info("------------updateO2oGoodsStatus-----------");
        try {
            String userName = SessionHelper.getUserName();
            List<O2oGoods> o2oGoodsStatus = JSONObject.parseArray(data, O2oGoods.class);

            for (O2oGoods o2oGoods : o2oGoodsStatus) {

                Integer oldStatus = o2oGoods.getOldStatus();
                Integer newStatus = o2oGoods.getNewStatus();

                if (oldStatus != 0 && newStatus == 0) {
                    return JsonResponse.notOk("已发布或禁用的门店商品不能修改成未发布");
                }
                // 为了区分锁定价格或锁定库存填空字符串的情况
                Map<String, Object> jsonObject = new NewHashMap<>();
                jsonObject.put("itemId", o2oGoods.getItemId());
                jsonObject.put("channelId", o2oGoods.getChannelId());
                jsonObject.put("skuId", o2oGoods.getSkuId());
                // 获取数据库中数据
                O2oGoods dbGoods = o2oGoodsServiceImpl.getO2oGoods(jsonObject);

                if (dbGoods == null) {
                    throw new ValidationException("商品不存在，skuid:" + o2oGoods.getSkuId());
                }

                // 拿到页面的锁定价格和锁定库存，方便管理
                o2oGoods.setLockPrice(dbGoods.getLockPrice());
                o2oGoods.setLockInventory(dbGoods.getLockInventory());

                o2oGoodsServiceImpl.updateO2oGoods(o2oGoods, userName);
            }
            return JsonResponse.ok("成功");
        } catch (Exception e) {
            logger.error(e);
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "商品价格锁定", notes = "商品价格锁定")
    @RequestMapping(value = "/updateO2oGoodsLockPrice", method = RequestMethod.POST)
    public JsonResponse updateO2oGoodsLockPrice(@RequestParam(value = "data") String data) {
        logger.info("------------updateO2oGoodsLockPrice-----------");
        try {
            String userName = SessionHelper.getUserName();
            List<O2oGoods> o2oGoodsLockPrice = JSONObject.parseArray(data, O2oGoods.class);

            for (O2oGoods o2oGoods : o2oGoodsLockPrice) {
                // 为了区分锁定价格或锁定库存填空字符串的情况
                Map<String, Object> jsonObject = new NewHashMap<>();
                jsonObject.put("itemId", o2oGoods.getItemId());
                jsonObject.put("channelId", o2oGoods.getChannelId());
                jsonObject.put("skuId", o2oGoods.getSkuId());
                // 获取数据库中数据
                O2oGoods dbGoods = o2oGoodsServiceImpl.getO2oGoods(jsonObject);

                if (dbGoods == null) {
                    throw new ValidationException("商品不存在，skuid:" + o2oGoods.getSkuId());
                }

                // 拿到页面的锁定库存，方便管理
                o2oGoods.setLockInventory(dbGoods.getLockInventory());

//                if (o2oGoods.getIsEvent() == 1) {
//                    return JsonResponse.notOk("活动商品的锁定价格不能修改！");
//                }
                o2oGoodsServiceImpl.updateO2oGoods(o2oGoods, userName);
            }
            return JsonResponse.ok("成功");
        } catch (Exception e) {
//        	e.printStackTrace();
            logger.error(e);
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "商品库存锁定", notes = "商品库存锁定")
    @RequestMapping(value = "/updateO2oGoodsLockInventory", method = RequestMethod.POST)
    public JsonResponse updateO2oGoodsLockInventory(@RequestParam(value = "data") String data) {
        logger.info("------------updateO2oGoodsLockInventory-----------");
        try {
            String userName = SessionHelper.getUserName();
            List<O2oGoods> o2oGoodsLockInventory = JSONObject.parseArray(data, O2oGoods.class);

            for (O2oGoods o2oGoods : o2oGoodsLockInventory) {
                // 为了区分锁定价格或锁定库存填空字符串的情况
                Map<String, Object> jsonObject = new NewHashMap<>();
                jsonObject.put("itemId", o2oGoods.getItemId());
                jsonObject.put("channelId", o2oGoods.getChannelId());
                jsonObject.put("skuId", o2oGoods.getSkuId());
                // 获取数据库中数据
                O2oGoods dbGoods = o2oGoodsServiceImpl.getO2oGoods(jsonObject);

                if (dbGoods == null) {
                    throw new ValidationException("商品不存在，skuid:" + o2oGoods.getSkuId());
                }

                // 拿到页面的锁定价格，方便管理
                o2oGoods.setLockPrice(dbGoods.getLockPrice());

                o2oGoodsServiceImpl.updateO2oGoods(o2oGoods, userName);
            }
            return JsonResponse.ok("成功");
        } catch (Exception e) {
//        	e.printStackTrace();
            logger.error(e);
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "商品上下架锁定", notes = "商品上下架锁定")
    @RequestMapping(value = "/updateO2oGoodsShelved", method = RequestMethod.POST)
    public JsonResponse updateO2oGoodsShelved(@RequestParam(value = "data") String data) {
        logger.info("------------updateO2oGoodsShelved-----------");
        try {
            String userName = SessionHelper.getUserName();
            List<O2oGoods> o2oGoodsShelved = JSONObject.parseArray(data, O2oGoods.class);

            for (O2oGoods o2oGoods : o2oGoodsShelved) {
                // 为了区分锁定价格或锁定库存填空字符串的情况
                Map<String, Object> jsonObject = new NewHashMap<>();
                jsonObject.put("itemId", o2oGoods.getItemId());
                jsonObject.put("channelId", o2oGoods.getChannelId());
                jsonObject.put("skuId", o2oGoods.getSkuId());
                // 获取数据库中数据
                O2oGoods dbGoods = o2oGoodsServiceImpl.getO2oGoods(jsonObject);

                if (dbGoods == null) {
                    throw new ValidationException("商品不存在，skuid:" + o2oGoods.getSkuId());
                }

                // 拿到页面的锁定价格和锁定库存，方便管理
                o2oGoods.setLockPrice(dbGoods.getLockPrice());
                o2oGoods.setLockInventory(dbGoods.getLockInventory());

//                if (o2oGoods.getIsEvent() == 1) {
//                    return JsonResponse.notOk("活动商品的上下架不能修改！");
//                }
                o2oGoodsServiceImpl.updateO2oGoods(o2oGoods, userName);
            }
            return JsonResponse.ok("成功");
        } catch (Exception e) {
//        	e.printStackTrace();
            logger.error(e);
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    // 手工触发更新锁定库存
    @ApiOperation(value = "手工触发更新锁定库存", notes = "手工触发更新锁定库存")
    @RequestMapping(value = "/insertGoodsLockInventory", method = RequestMethod.GET)
    public JsonResponse insertGoodsLockInventory(O2oInventoryLog o2oInventoryLog) {
        logger.info("------------insertGoodsLockInventory-----------");
        try {
            String userName = SessionHelper.getUserName();
            o2oGoodsServiceImpl.insertInventoryLog(o2oInventoryLog, userName);
            return JsonResponse.ok("成功");
        } catch (Exception e) {
            logger.error(e);
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }
}
