package cn.com.efuture.o2o.backend.api.salesReport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.efuture.o2o.backend.mybatis.service.SalesReportServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/salesReport")
public class SalesReportController {
	protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	 private final SalesReportServiceImpl salesReportServiceImpl;
	 
	 public SalesReportController(SalesReportServiceImpl salesReportServiceImpl) {
		super();
		this.salesReportServiceImpl = salesReportServiceImpl;
	}

	@ApiOperation(value = "获取销售报表", notes = "获取销售报表")
	    @ApiImplicitParams({
	            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
	        
	    })
	 @RequestMapping(value = "/getSalesReport", method = RequestMethod.GET)
	 public JsonResponse getSalesReport(@RequestParam(value = "data") String data, HttpServletRequest request) {
	        logger.info("------------getSalesReport-----------");
	        JSONObject map = JSONObject.parseObject(data);
	        map.put("userName",request.getSession().getAttribute("username").toString());
	        try {
	        	ParameterHelper.cookCityInfo(map);
	        	ParameterHelper.cookPageInfo(map);
				long count = ParameterHelper.getCount(map, salesReportServiceImpl);
				// 设置分页信息
				int page = map.getIntValue("page");
				int pageSize = map.getIntValue("pageSize");
				map.put("page",(page-1)*pageSize);
				map.put("pageSize",pageSize);
				//执行查询
	        	List<Map<String,String>> list = salesReportServiceImpl.getSalesReport(map);

	            return JsonResponse.ok(count,list);
	        } catch (Exception e) {
	            logger.debug(e.toString());
	            return JsonResponse.SERVER_ERR;
	        }
	    }

}
