package cn.com.efuture.o2o.backend.api.channelShop;

import cn.com.efuture.o2o.backend.mybatis.entity.Channel;
import cn.com.efuture.o2o.backend.mybatis.entity.ChannelShop;
import cn.com.efuture.o2o.backend.mybatis.entity.ChannelShopTime;
import cn.com.efuture.o2o.backend.mybatis.entity.Shop;
import cn.com.efuture.o2o.backend.mybatis.service.ChannelServiceImpl;
import cn.com.efuture.o2o.backend.mybatis.service.ChannelShopServiceImpl;
import cn.com.efuture.o2o.backend.mybatis.service.ShopServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/channelShop")
public class ChannelShopController {
    private final ChannelShopServiceImpl channelShopServiceImpl;
    private final ChannelServiceImpl channelServiceImpl;
    private final ShopServiceImpl shopServiceImpl;
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public ChannelShopController(ChannelShopServiceImpl channelShopServiceImpl, ChannelServiceImpl channelServiceImpl, ShopServiceImpl shopServiceImpl) {
        this.channelShopServiceImpl = channelShopServiceImpl;
        this.channelServiceImpl = channelServiceImpl;
        this.shopServiceImpl = shopServiceImpl;
    }

    @ApiOperation(value = "获取渠道和门店列表", notes = "获取渠道和门店列表")
    @RequestMapping(value = "/getChannelShopsList", method = RequestMethod.GET)
    public JsonResponse getChannelShopsList(@RequestParam(value = "data", required = false) String data) {
        Map<String, Object> map = JSONObject.parseObject(data);
        logger.info("------------getChannelShopsList-----------");
        JSONObject returnJson = new JSONObject();
        JSONObject jsonObject;
        try {
            List<Channel> channelList = channelServiceImpl.getChannelList(map);
            for (Channel channel : channelList) {
                jsonObject = JSONObject.parseObject(JSON.toJSON(channel).toString());
                List<Shop> shopList = shopServiceImpl.getShopList(jsonObject);
                jsonObject.put("shopList", shopList);
                returnJson.put(channel.getChannelId().toString(), jsonObject);
            }
            return JsonResponse.ok(returnJson);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }
    /**
     *  注解@ApiOperation、@ApiImplicitParams、@ApiImplicitParam为API说明；
     *  paramType:查询参数类型 ,query:直接跟参数完成自动映射赋值; path以地址的形式提交数据;
     *  body:流形式提交（POST）;header:参数在request headers 里边提交;form:以form表单的形式提交仅支持POST
     *  @PathVariable获得请求url中的动态参数
     */
    @ApiOperation(value = "新增店铺", notes = "新增店铺")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelId", value = "渠道编码", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "shopId", value = "对应家乐福门店编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "channelShopId", value = "渠道商店编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "channelShopName", value = "渠道商店名称", required = true, dataType = "String")
    })
    @RequestMapping(value = "/insertChannelShop", method = RequestMethod.POST)
    public JsonResponse insertChannelShop(@RequestParam(value = "data") String data) {
        logger.info("------------insertChannelShop-----------");
        ChannelShop channelShop = JSONObject.parseObject(data, ChannelShop.class);
        String userName = SessionHelper.getUserName();
        try {
            if (channelShop.getChannelId() == null || channelShop.getShopId() == null || channelShop.getChannelShopId() == null || channelShop.getChannelShopName() == null) {
                return JsonResponse.notOk(500, "必要数据字段不能为空");
            }
            if (channelShop.getChannelShopName().length() > 20) {
                return JsonResponse.notOk(500, "店铺名称最大长度为20");
            }
            if (channelShop.getChannelShopId().length() > 30) {
                return JsonResponse.notOk(500, "外卖店编码最大长度为30");
            }
            channelShopServiceImpl.insertChannelShop(channelShop, userName);
            return JsonResponse.ok();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "获取店铺列表", notes = "获取店铺列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "data", required = true, dataType = "String"),
            @ApiImplicitParam(name = "isOpen", value = "门店状态", dataType = "Int"),
            @ApiImplicitParam(name = "channelId", value = "渠道", dataType = "Int"),
            @ApiImplicitParam(name = "regionId", value = "区域", dataType = "String"),
            @ApiImplicitParam(name = "city", value = "城市", dataType = "String"),
            @ApiImplicitParam(name = "channelShopName", value = "门店名称", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示数", required = true, dataType = "Int")
    })
    @RequestMapping(value = "/getChannelShopList", method = RequestMethod.GET)
    public JsonResponse getChannelShopList(@RequestParam(value = "data") String data) {
        logger.info("------------getChannelShopList-----------");
        JSONObject map = JSONObject.parseObject(data);
        map.put("userName", SessionHelper.getUserName());
        try {
            ParameterHelper.cookPageInfo(map);
            ParameterHelper.cookCityInfo(map);
            //设置分页信息
            PageHelper.startPage((int) map.get("page"), (int) map.get("pageSize"));
            //执行查询
            List<ChannelShop> list = channelShopServiceImpl.getChannelShopList(map);
            //前端虚拟 dom 树需要 key
            int key = 0;
            for (ChannelShop channelShop : list) {
                channelShop.setKey(key++);
            }
            //获取查询结果
            PageInfo<ChannelShop> pageInfo = new PageInfo<>(list);
            return JsonResponse.ok(pageInfo.getTotal(), list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "获取店铺信息", notes = "获取店铺信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "data", required = true, dataType = "Json"),
            @ApiImplicitParam(name = "channelId", value = "渠道编码", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "channelShopId", value = "渠道门店编码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getChannelShop", method = RequestMethod.GET)
    public JsonResponse getChannelShop(@RequestParam(value = "data") String data) {
        logger.info("------------getChannelShop-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            ChannelShop channelShop = channelShopServiceImpl.getChannelShop(map);
            return JsonResponse.ok(channelShop);
        } catch (Exception e) {

            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "修改店铺信息", notes = "修改店铺信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelShop", value = "店铺", required = true, dataType = "Obj"),
            @ApiImplicitParam(name = "channelShop.channelId", value = "渠道编码", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "channelShop.channelShopId", value = "渠道商店编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "channelShop.shopId", value = "对应家乐福门店编码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/updateChannelShop", method = RequestMethod.POST)
    public JsonResponse updateChannelShop(@RequestParam(value = "data") String data) {
        logger.info("------------updateChannelShop-----------");
        ChannelShop channelShop = JSONObject.parseObject(data, ChannelShop.class);
        String userName = SessionHelper.getUserName();
        try {
            if (channelShop.getAddress() != null && channelShop.getAddress().length() > 80) {
                return JsonResponse.notOk(500, "地址最大长度为80");
            }
            if (channelShop.getNotice() != null && channelShop.getNotice().length() > 200) {
                return JsonResponse.notOk(500, "公告最大长度为200");
            }
            //执行更新
            channelShopServiceImpl.updateChannelShop(channelShop, userName);
            return JsonResponse.ok("修改成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "批量修改店铺信息", notes = "批量修改店铺信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "data", required = true, dataType = "Json"),
            @ApiImplicitParam(name = "channelShop.channelId", value = "渠道编码", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "channelShop.channelShopId", value = "渠道商店编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "channelShop.shopId", value = "对应家乐福门店编码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/updateChannelShops", method = RequestMethod.POST)
    public JsonResponse updateChannelShops(@RequestParam(value = "data") String data) {
        logger.info("------------updateChannelShops-----------");
        List<ChannelShop> channelShops = JSONObject.parseArray(data, ChannelShop.class);
        String userName = SessionHelper.getUserName();
        try {
            for (ChannelShop channelShop : channelShops) {
                if (channelShop.getNotice() != null && channelShop.getNotice().length() > 200) {
                    return JsonResponse.notOk(500, "公告最大长度为200");
                }
                channelShopServiceImpl.updateChannelShop(channelShop, userName);
            }
            return JsonResponse.ok("成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "获取店铺营业时间", notes = "获取营业时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ChannelShopTime.channelId", value = "渠道编码", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "ChannelShopTime.channelShopId", value = "渠道商店编码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getChannelShopTime", method = RequestMethod.GET)
    public JsonResponse getChannelShopTime(@RequestParam(value = "data") String data) {
        logger.info("------------getChannelShopTime-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            List<ChannelShopTime> list = channelShopServiceImpl.getChannelShopTime(map);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "设置营业时间", notes = "设置营业时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ChannelShopTime.channelId", value = "渠道编码", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "ChannelShopTime.channelShopId", value = "渠道商店编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ChannelShopTime.startDate", value = "开始日期", required = true, dataType = "date"),
            @ApiImplicitParam(name = "ChannelShopTime.endDate", value = "结束日期", required = true, dataType = "date"),
            @ApiImplicitParam(name = "ChannelShopTime.openTime", value = "营业时间", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/updateChannelShopTime", method = RequestMethod.POST)
    public JsonResponse updateChannelShopTime(@RequestParam(value = "data") String data) {
        logger.info("------------updateChannelShopTime-----------");
        List<ChannelShopTime> channelShopTimes = JSONObject.parseArray(data, ChannelShopTime.class);
        String userName = SessionHelper.getUserName();
        try {
            for (ChannelShopTime channelShopTime : channelShopTimes) {
                channelShopServiceImpl.updateChannelShopTime(channelShopTime, userName);
            }
            return JsonResponse.ok("成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }

    }
/*
    @ApiOperation(value = "设置营业时间", notes = "设置营业时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ChannelShopTime.channelId", value = "渠道编码", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "ChannelShopTime.channelShopId", value = "渠道商店编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ChannelShopTime.startDate", value = "营业日期", required = true, dataType = "date"),
            @ApiImplicitParam(name = "ChannelShopTime.openTime", value = "营业时间", required = true, dataType = "datetime"),
    })
    @RequestMapping(value = "/updateChannelShopTimes", method = RequestMethod.POST)
    public JsonResponse updateChannelShopTimes(@RequestParam(value = "data") String data) {
        logger.info("------------updateChannelShopTimes-----------");
        List<ChannelShopTime> channelShopTimes = JSONObject.parseArray(data, ChannelShopTime.class);
        String userName = SessionHelper.getUserName();
        try {
            for (ChannelShopTime channelShopTime : channelShopTimes) {
                List<Date> lDate = findDates(new SimpleDateFormat("yyyy-MM-dd").parse(channelShopTime.getStartDate()), new SimpleDateFormat("yyyy-MM-dd").parse(channelShopTime.getEndDate()));
                for (Date date : lDate) {
                    channelShopTime.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
                    channelShopServiceImpl.updateChannelShopTime(channelShopTime, userName);
                }
            }
            return JsonResponse.ok("成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    //获取时间段内的所以日期
    private static List<Date> findDates(Date dBegin, Date dEnd) {
        List<Date> lDate = new ArrayList<>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    @ApiOperation(value = "导出门店列表", notes = "导出门店列表")
    @RequestMapping(value = "/exportChannelShopList", method = RequestMethod.GET)
    public ModelAndView exportChannelShopList(ModelMap model, @RequestParam(value = "data") String data) {
        Map<String, Object> map = JSONObject.parseObject(data);
        logger.info("------------exportChannelShopList-----------");
        AbstractXlsView abstractxlsView = channelShopServiceImpl.exportChannelShopList(map);
        return new ModelAndView(abstractxlsView, model);
    }
 */
}
