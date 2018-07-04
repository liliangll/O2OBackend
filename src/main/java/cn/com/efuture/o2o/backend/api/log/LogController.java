package cn.com.efuture.o2o.backend.api.log;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.efuture.o2o.backend.mybatis.entity.ChannelCategoryLog;
import cn.com.efuture.o2o.backend.mybatis.entity.ChannelShopLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oInventoryLog;
import cn.com.efuture.o2o.backend.mybatis.service.LogServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/log")
public class LogController {
	protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final LogServiceImpl logServiceImpl;
	
	public LogController(LogServiceImpl logServiceImpl) {
		super();
		this.logServiceImpl = logServiceImpl;
	}


	@ApiOperation(value = "获取类别日志", notes = "获取类别日志")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
        @ApiImplicitParam(name = "logId", value = "自增长序列", required = true, dataType = "Int"),
        @ApiImplicitParam(name = "operator", value = "操作人员ID", required = true, dataType = "String"),
        @ApiImplicitParam(name = "channelId", value = "渠道", required = true,dataType = "Int"),
        @ApiImplicitParam(name = "shopId", value = "家乐福门店编码", dataType = "String"),
        @ApiImplicitParam(name = "channelShopId", value = "渠道店铺编码", dataType = "String"),
        @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "Int"),
})
	 @RequestMapping(value = "/getChannelCategoryLog", method = RequestMethod.GET)
	 public JsonResponse getChannelCategoryLog(@RequestParam(value = "data") String data) {
	        logger.info("------------getChannelCategoryLog-----------");
	        Map<String, Object> map = JSONObject.parseObject(data);
	        map.put("userName",SessionHelper.getUserName());
	        try {
	        	ParameterHelper.cookPageInfo(map);
	            //设置分页信息
	            PageHelper.startPage((int) map.get("page"), (int) map.get("pageSize"));
	            //执行查询
	            List<ChannelCategoryLog> list = logServiceImpl.getChannelCategoryLog(map);
	            //获取查询结果
	            PageInfo<ChannelCategoryLog> pageInfo = new PageInfo<>(list);
	            return JsonResponse.ok(pageInfo.getTotal(),list);
	        } catch (Exception e) {
	            logger.debug(e.toString());
	            return JsonResponse.SERVER_ERR;
	        }
	    }
	
	
	@ApiOperation(value = "获取门店日志", notes = "获取门店日志")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "logId", value = "自增长序列", required = true, dataType = "Int"),
        @ApiImplicitParam(name = "operator", value = "操作人员ID", required = true, dataType = "String"),
        @ApiImplicitParam(name = "channelId", value = "渠道", required = true,dataType = "Int"),
        @ApiImplicitParam(name = "shopId", value = "家乐福门店编码", dataType = "String"),
        @ApiImplicitParam(name = "channelShopId", value = "渠道店铺编码", dataType = "String"),
        @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "Int"),
})
	 @RequestMapping(value = "/getChannelShopLog", method = RequestMethod.GET)
	 public JsonResponse getChannelShopLog(@RequestParam(value = "data") String data) {
	        logger.info("------------getChannelShopLog-----------");
	        Map<String, Object> map = JSONObject.parseObject(data);
	        map.put("userName",SessionHelper.getUserName());
	        try {
	        	ParameterHelper.cookPageInfo(map);
	            //设置分页信息
	            PageHelper.startPage((int) map.get("page"), (int) map.get("pageSize"));
	            //执行查询
	            List<ChannelShopLog> list = logServiceImpl.getChannelShopLog(map);
	            //获取查询结果
	            PageInfo<ChannelShopLog> pageInfo = new PageInfo<>(list);
	            return JsonResponse.ok(pageInfo.getTotal(),list);
	        } catch (Exception e) {
	            logger.debug(e.toString());
	            return JsonResponse.SERVER_ERR;
	        }
	    }
	
	
	@ApiOperation(value = "获取商品日志", notes = "获取商品日志")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
        @ApiImplicitParam(name = "logId", value = "自增长序列", required = true, dataType = "Int"),
        @ApiImplicitParam(name = "operator", value = "操作人员ID", required = true, dataType = "String"),
        @ApiImplicitParam(name = "channelId", value = "渠道", required = true,dataType = "Int"),
        @ApiImplicitParam(name = "shopId", value = "家乐福门店编码", dataType = "String"),
        @ApiImplicitParam(name = "channelShopId", value = "渠道店铺编码", dataType = "String"),
        @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "Int"),
})
	 @RequestMapping(value = "/getO2oGoodsLog", method = RequestMethod.GET)
	 public JsonResponse getO2oGoodsLog(@RequestParam(value = "data") String data) {
	        logger.info("------------getO2oGoodsLog-----------");
	        Map<String, Object> map = JSONObject.parseObject(data);
	        map.put("userName",SessionHelper.getUserName());
	        try {
	        	ParameterHelper.cookPageInfo(map);
	            //设置分页信息
	            PageHelper.startPage((int) map.get("page"), (int) map.get("pageSize"));
	            //执行查询
	            List<O2oGoodsLog> list = logServiceImpl.getO2oGoodsLog(map);
	            //获取查询结果
	            PageInfo<O2oGoodsLog> pageInfo = new PageInfo<>(list);
	            return JsonResponse.ok(pageInfo.getTotal(),list);
	        } catch (Exception e) {
	            logger.debug(e.toString());
	            return JsonResponse.SERVER_ERR;
	        }
	    }
	
	
	@ApiOperation(value = "获取库存日志", notes = "获取库存日志")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
        @ApiImplicitParam(name = "logId", value = "自增长序列", required = true, dataType = "Int"),
        @ApiImplicitParam(name = "operator", value = "操作人员ID", required = true, dataType = "String"),
        @ApiImplicitParam(name = "channelId", value = "渠道", required = true,dataType = "Int"),
        @ApiImplicitParam(name = "shopId", value = "家乐福门店编码", dataType = "String"),
        @ApiImplicitParam(name = "channelShopId", value = "渠道店铺编码", dataType = "String"),
        @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "Int"),
})
	 @RequestMapping(value = "/getO2oInventoryLog", method = RequestMethod.GET)
	 public JsonResponse getO2oInventoryLog(@RequestParam(value = "data") String data) {
	        logger.info("------------getO2oInventoryLog-----------");
	        Map<String, Object> map = JSONObject.parseObject(data);
	        map.put("userName",SessionHelper.getUserName());
	        try {
	        	ParameterHelper.cookPageInfo(map);
	            //设置分页信息
	            PageHelper.startPage((int) map.get("page"), (int) map.get("pageSize"));
	            //执行查询
	            List<O2oInventoryLog> list = logServiceImpl.getO2oInventoryLog(map);
	            //获取查询结果
	            PageInfo<O2oInventoryLog> pageInfo = new PageInfo<>(list);
	            return JsonResponse.ok(pageInfo.getTotal(),list);
	        } catch (Exception e) {
	            logger.debug(e.toString());
	            return JsonResponse.SERVER_ERR;
	        }
	    }
}
