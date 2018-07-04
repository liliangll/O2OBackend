package cn.com.efuture.o2o.backend.api.o2ocategory;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oCategory;
import cn.com.efuture.o2o.backend.mybatis.service.O2oCategoryServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import com.alibaba.fastjson.JSONArray;
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
@RequestMapping("/api/category")
public class O2oCategoryController {

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final O2oCategoryServiceImpl o2oCategoryServiceImpl;
    
    public O2oCategoryController(O2oCategoryServiceImpl o2oCategoryServiceImpl) {
		super();
		this.o2oCategoryServiceImpl = o2oCategoryServiceImpl;
	}


	@ApiOperation(value = "获取类别清单", notes = "获取类别清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "本地类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryName", value = "类别名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parentCategoryId", value = "上级类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "level", value = "类别级别", dataType = "Int"),
            @ApiImplicitParam(name = "seqNo", value = "排序级别", dataType = "Int"),
            @ApiImplicitParam(name = "flag", value = "状态", dataType = "Int"),
            @ApiImplicitParam(name = "lastModifyTime", value = "最后修改时间", dataType = "date")
    })
    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
    public JsonResponse getCategoryList(@RequestParam(value = "data") String data) {
        logger.info("------------getCategoryList-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            //执行查询
            List<O2oCategory> list = o2oCategoryServiceImpl.getCategoryList(map);
            //获取查询结果
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }


    @ApiOperation(value = "新增类别", notes = "新增类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "本地类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryName", value = "类别名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parentCategoryId", value = "上级类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "level", value = "类别级别", dataType = "Int"),
            @ApiImplicitParam(name = "seqNo", value = "排序级别", dataType = "Int"),
            @ApiImplicitParam(name = "flag", value = "状态", dataType = "Int"),
            @ApiImplicitParam(name = "lastModifyTime", value = "最后修改时间", dataType = "date")
    })
    @RequestMapping(value = "/insertCategory", method = RequestMethod.POST)
    public JsonResponse insertCategory(@RequestParam(value = "data") String data) {
        logger.info("------------insertCategory-----------");
        O2oCategory o2oCategory = JSONObject.parseObject(data, O2oCategory.class);
        String userName = SessionHelper.getUserName();
        try {
        	if(o2oCategory.getCategoryName() != null && o2oCategory.getCategoryName().length()>10) {
        		return JsonResponse.notOk(500, "类别名称最大长度为10");
        	}
        	if(o2oCategory.getMemo() != null && o2oCategory.getMemo().length()>20) {
        		return JsonResponse.notOk(500, "类别备注最大长度为20");
        	}
            //执行新增
            o2oCategoryServiceImpl.insertCategory(o2oCategory,userName);
            return JsonResponse.ok("新增成功");
        } catch (Exception e) {
        	logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "修改类别", notes = "修改类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "本地类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryName", value = "类别名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "seqNo", value = "排序级别", dataType = "Int"),
            @ApiImplicitParam(name = "flag", value = "状态", dataType = "Int"),
            @ApiImplicitParam(name = "lastModifyTime", value = "最后修改时间", dataType = "date")
    })
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public JsonResponse updateCategory(@RequestParam(value = "data") String data) {
        logger.info("------------updateCategory-----------");
        O2oCategory o2oCategory = JSONObject.parseObject(data, O2oCategory.class);
        String userName = SessionHelper.getUserName();
        
        try {
        	if(o2oCategory.getCategoryName() != null && o2oCategory.getCategoryName().length()>10) {
        		return JsonResponse.notOk(500, "类别名称最大长度为10");
        	}
        	if(o2oCategory.getMemo() != null && o2oCategory.getMemo().length()>20) {
        		return JsonResponse.notOk(500, "类别备注最大长度为20");
        	}
            //执行修改
            o2oCategoryServiceImpl.updateCategory(o2oCategory,userName);
            return JsonResponse.ok("修改成功");
        } catch (Exception e) {
        	logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "修改类别排序", notes = "修改类别排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "Json"),
            @ApiImplicitParam(name = "categoryId", value = "本地类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryName", value = "类别名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "seqNo", value = "排序级别", dataType = "Int"),
            @ApiImplicitParam(name = "lastModifyTime", value = "最后修改时间", dataType = "date")
    })
    @RequestMapping(value = "/updateCategoryList", method = RequestMethod.POST)
    public JsonResponse updateCategoryList(@RequestParam(value = "data") String data) {
        logger.info("------------updateCategoryList-----------");
        List<O2oCategory> o2oCategorys = JSONObject.parseArray(data, O2oCategory.class);
        String userName = SessionHelper.getUserName();
        try {
            //执行修改
            for (O2oCategory o2oCategory : o2oCategorys) {
                o2oCategoryServiceImpl.updateCategory(o2oCategory,userName);
            }
            return JsonResponse.ok("修改成功");
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }

    @ApiOperation(value = "获取类别树和类别下门店商品数量", notes = "获取类别树和类别下门店商品数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "本地类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryName", value = "类别名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parentCategoryId", value = "上级类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "level", value = "类别级别", dataType = "Int"),
            @ApiImplicitParam(name = "seqNo", value = "排序级别", dataType = "Int"),
            @ApiImplicitParam(name = "flag", value = "状态", dataType = "Int"),
            @ApiImplicitParam(name = "lastModifyTime", value = "最后修改时间", dataType = "date")
    })
    @RequestMapping(value = "/getChannelCategoryList", method = RequestMethod.GET)
    public JsonResponse getChannelCategoryList(@RequestParam(value = "data") String data) {
        logger.info("------------getChannelCategoryList-----------");
        JSONObject map = JSONObject.parseObject(data);
        try {
        	if(map.containsKey("shopId")){
                JSONArray shopId = map.getJSONArray("shopId");
                // 如果shopId为空数组，则为查询全部门店
                if (shopId.isEmpty()){
                    map.remove("shopId");
                }
            }
            //执行查询
            List<Map<String, Object>> list = o2oCategoryServiceImpl.getChannelCategoryList(map);
            //获取查询结果
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }

    
    @ApiOperation(value = "获取类别树和类别下主档商品数量", notes = "获取类别树和类别下主档商品数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "本地类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryName", value = "类别名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parentCategoryId", value = "上级类别编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "level", value = "类别级别", dataType = "Int"),
            @ApiImplicitParam(name = "seqNo", value = "排序级别", dataType = "Int"),
            @ApiImplicitParam(name = "flag", value = "状态", dataType = "Int"),
            @ApiImplicitParam(name = "lastModifyTime", value = "最后修改时间", dataType = "date")
    })
    @RequestMapping(value = "/getCategoryListProduct", method = RequestMethod.GET)
    public JsonResponse getCategoryListProduct(@RequestParam(value = "data") String data) {
        logger.info("------------getCategoryListProduct-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            //执行查询
            List<Map<String, Object>> list = o2oCategoryServiceImpl.getCategoryListProduct(map);
            //获取查询结果
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }
}
