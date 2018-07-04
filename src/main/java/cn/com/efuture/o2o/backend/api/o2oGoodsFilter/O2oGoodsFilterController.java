package cn.com.efuture.o2o.backend.api.o2oGoodsFilter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsFilterSheetDetail;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsFilterSheetHead;
import cn.com.efuture.o2o.backend.mybatis.service.O2oGoodsFilterServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/o2oGoodsFilter")
public class O2oGoodsFilterController {
	@Value("${FILE_URL}")
    private String URL;
	
	protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private final O2oGoodsFilterServiceImpl o2oGoodsFilterServiceImpl;
	
    public O2oGoodsFilterController(O2oGoodsFilterServiceImpl o2oGoodsFilterServiceImpl) {
		super();
		this.o2oGoodsFilterServiceImpl = o2oGoodsFilterServiceImpl;
	}
    
    @ApiOperation(value = "获取商品提报单", notes = "获取商品提报单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sheetId", value = "单据编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "flag", value = "状态标识", required = true, dataType = "int"),
            @ApiImplicitParam(name = "note", value = "单据备注", required = true, dataType = "String"),
            @ApiImplicitParam(name = "auditNote", value = "审核备注", dataType = "String"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "date"),
            @ApiImplicitParam(name = "creator", value = "创建人账号", dataType = "String"),
            @ApiImplicitParam(name = "auditTime", value = "审核时间", dataType = "date")
    })
    @RequestMapping(value = "/getO2oGoodsFilterSheetHead", method = RequestMethod.GET)
    public JsonResponse getO2oGoodsFilterSheetHead(@RequestParam(value = "data") String data) {
        logger.info("------------getO2oGoodsFilterSheetHead-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
        	ParameterHelper.cookPageInfo(map);
            //设置分页信息
            PageHelper.startPage((int) map.get("page"), (int) map.get("pageSize"));
            //执行查询
            List<Map<String,Object>> list = o2oGoodsFilterServiceImpl.getO2oGoodsFilterSheetHead(map);
            //获取查询结果
            PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(list);
            return JsonResponse.ok(pageInfo.getTotal(),list);
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }
    
    
    @ApiOperation(value = "获取商品提报单明细", notes = "获取商品提报单明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sheetId", value = "单据编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "rowno", value = "家乐福门店编码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "shopId", value = "门店编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "itemCode", value = "审核备注", dataType = "String"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", dataType = "String"),
            @ApiImplicitParam(name = "weight", value = "商品重量", dataType = "int"),
            @ApiImplicitParam(name = "spec", value = "销售规格", dataType = "String")
    })
    @RequestMapping(value = "/getO2oGoodsFilterSheetDetail", method = RequestMethod.GET)
    public JsonResponse getO2oGoodsFilterSheetDetail(@RequestParam(value = "data") String data) {
        logger.info("------------getO2oGoodsFilterSheetDetail-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        String userName = SessionHelper.getUserName();
        map.put("userName",userName);
        try {
        	JSONObject resultJson = new JSONObject();
            // 查询单据头
            List<Map<String, Object>> headList = o2oGoodsFilterServiceImpl.getO2oGoodsFilterSheetHead(map);
            // 查询单据明细
            ParameterHelper.cookPageInfo(map);
            //设置分页信息
            PageHelper.startPage((int) map.get("page"), (int) map.get("pageSize"));
            //执行查询
            List<Map<String, Object>> sheetDetailList = o2oGoodsFilterServiceImpl.getO2oGoodsFilterSheetDetail(map);
            String url = URL + "/image/read/";
            for(Map<String, Object> maps : sheetDetailList) {
            	maps.put("imageUrl",url+maps.get("imageKey1"));
            }
            //获取查询结果
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(sheetDetailList);
            resultJson.put("sheetHead", headList.get(0));
            resultJson.put("sheetDetail", sheetDetailList);

            return JsonResponse.ok(pageInfo.getTotal(), resultJson);
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }
    
       
    @ApiOperation(value = "新增商品提报单", notes = "新增商品提报单")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
        @ApiImplicitParam(name = "sheetId", value = "单据编码", required = true, dataType = "String"),
        @ApiImplicitParam(name = "flag", value = "状态标识", required = true, dataType = "int"),
        @ApiImplicitParam(name = "note", value = "单据备注", required = true, dataType = "String"),
        @ApiImplicitParam(name = "auditNote", value = "审核备注", dataType = "String"),
        @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "date"),
        @ApiImplicitParam(name = "creator", value = "创建人账号", dataType = "String"),
        @ApiImplicitParam(name = "auditTime", value = "审核时间", dataType = "date")
    })
    @RequestMapping(value = "/insertO2oGoodsFilterSheet", method = RequestMethod.POST)
    public JsonResponse insertO2oGoodsFilterSheetDetail(@RequestParam(value = "data") String data) {
        logger.info("------------insertO2oGoodsFilterSheet-----------");
        //O2oGoodsFilterSheetDetail o2oGoodsFilterSheetDetail = JSONObject.parseObject(data, O2oGoodsFilterSheetDetail.class);
          JSONObject map = JSONObject.parseObject(data);
          String userName = SessionHelper.getUserName();
        try {
            //执行新增
        	o2oGoodsFilterServiceImpl.insertO2oGoodsFilterSheet(map,userName);
            return JsonResponse.ok("新增成功");
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.notOk(e.getMessage());
        }
    }

    
    @ApiOperation(value = "获取商品名称", notes = "获取商品名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
            @ApiImplicitParam(name = "itemCode", value = "商品编码", dataType = "String"),
    })
    @RequestMapping(value = "/getSheetGoodsNameById", method = RequestMethod.GET)
    public JsonResponse getSheetGoodsNameById(@RequestParam(value = "data") String data) {
        logger.info("------------getSheetGoodsNameById-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        String itemCode = (String) map.get("itemCode");
        try {
            //执行查询
        	 Map<String, Object> maps = o2oGoodsFilterServiceImpl.getSheetGoodsNameById(itemCode);
        	 if(maps!=null){
        		 String url = URL + "/image/read/";
            	 maps.put("imageUrl", url + maps.get("imageKey1"));
        	 } 
            //获取查询结果
            return JsonResponse.ok(maps);
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }
    
 
    @ApiOperation(value = "修改商品提报单", notes = "修改商品提报单")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
        @ApiImplicitParam(name = "sheetId", value = "单据编码", required = true, dataType = "String"),
        @ApiImplicitParam(name = "rowNo", value = "家乐福门店编码", required = true, dataType = "int"),
        @ApiImplicitParam(name = "shopId", value = "门店编码", required = true, dataType = "String"),
        @ApiImplicitParam(name = "itemCode", value = "审核备注", dataType = "String"),
        @ApiImplicitParam(name = "goodsName", value = "商品名称", dataType = "String"),
        @ApiImplicitParam(name = "weight", value = "商品重量", dataType = "int"),
        @ApiImplicitParam(name = "spec", value = "销售规格", dataType = "String")
    })
    @RequestMapping(value = "/updateO2oGoodsFilterSheetHead", method = RequestMethod.POST)
    public JsonResponse updateO2oGoodsFilterSheetHead(@RequestParam(value = "data") String data) {
        logger.info("------------updateO2oGoodsFilterSheetHead-----------");
        O2oGoodsFilterSheetHead o2oGoodsFilterSheetHead = JSONObject.parseObject(data, O2oGoodsFilterSheetHead.class);
        //JSONObject map = JSONObject.parseObject(data);
        try {
            //执行修改
        	o2oGoodsFilterServiceImpl.updateO2oGoodsFilterSheetHead(o2oGoodsFilterSheetHead);
            return JsonResponse.ok("操作成功");
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.notOk(e.getMessage());
        }
    }
    
    
    @ApiOperation(value = "修改商品提报单明细", notes = "修改商品提报单明细")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
        @ApiImplicitParam(name = "sheetId", value = "单据编码", required = true, dataType = "String"),
        @ApiImplicitParam(name = "rowNo", value = "家乐福门店编码", required = true, dataType = "int"),
        @ApiImplicitParam(name = "shopId", value = "门店编码", required = true, dataType = "String"),
        @ApiImplicitParam(name = "itemCode", value = "审核备注", dataType = "String"),
        @ApiImplicitParam(name = "goodsName", value = "商品名称", dataType = "String"),
        @ApiImplicitParam(name = "weight", value = "商品重量", dataType = "int"),
        @ApiImplicitParam(name = "spec", value = "销售规格", dataType = "String")
    })
    @RequestMapping(value = "/updateO2oGoodsFilterSheetDetail", method = RequestMethod.POST)
    public JsonResponse updateO2oGoodsFilterSheetDetail(@RequestParam(value = "data") String data) {
        logger.info("------------updateO2oGoodsFilterSheetDetail-----------");
            JSONObject map = JSONObject.parseObject(data);
            String userName = SessionHelper.getUserName();
        try {
            //执行修改
        	o2oGoodsFilterServiceImpl.updateO2oGoodsFilterSheetDetail(map,userName);
            return JsonResponse.ok("操作成功");
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.notOk(e.getMessage());
        }
    }
    
    
    @ApiOperation(value = "获取商品主档筛选列表", notes = "获取商品主档筛选列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "门店编码", dataType = "String"),
            @ApiImplicitParam(name = "itemCode", value = "商品编码", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Int"),
            @ApiImplicitParam(name = "page", value = "page", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "Int")
    })
    @RequestMapping(value = "/getO2oGoodsFilterList", method = RequestMethod.GET)
    public JsonResponse getO2oGoodsFilterList(@RequestParam(name = "data") String data) {
        logger.info("------------getO2oGoodsFilterList-----------");
        JSONObject map = JSONObject.parseObject(data);
        try {
        	ParameterHelper.cookCityInfo(map);
            ParameterHelper.cookPageInfo(map);
            long count = ParameterHelper.getCount(map, o2oGoodsFilterServiceImpl);
            // 设置分页信息
            int page = map.getIntValue("page");
            int pageSize = map.getIntValue("pageSize");
            map.put("page",(page-1)*pageSize);
            map.put("pageSize",pageSize);
            //执行查询
            List<Map<String, Object>> list = o2oGoodsFilterServiceImpl.getO2oGoodsFilterList(map);
            String url = URL + "/image/read/";
            for(Map<String, Object> maps : list) {
            	maps.put("imageUrl",url+maps.get("imageKey1"));
            } 
            //获取查询结果
            return JsonResponse.ok(count, list);
        } catch (Exception e) {
            logger.error(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }

    @ApiOperation(value = "新增主档商品筛选信息", notes = "新增主档商品筛选信息")
    @RequestMapping(value = "/insertO2oGoodsFilter", method = RequestMethod.POST)
    public JsonResponse insertO2oGoodsFilter(@RequestParam(value = "data") String data) {
        logger.info("------------insertO2oGoodsFilter-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
        	ParameterHelper.cookCityInfo(map);
            //执行更新
        	o2oGoodsFilterServiceImpl.insertO2oGoodsFilters(map);
            return JsonResponse.ok("新增成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }
 
    
	 @ApiOperation(value = "导出商品提报单明细", notes = "导出商品提报单明细")
	    @ApiImplicitParams({
	            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
	        
	    })
	 @RequestMapping(value = "/exportO2oGoodsFilterSheetDetail", method = RequestMethod.GET)
	    public ModelAndView exportO2oGoodsFilterSheetDetail(ModelMap model, @RequestParam(value = "data") String data) {
	        Map<String, Object> map = JSONObject.parseObject(data);
	        logger.info("------------exportO2oGoodsFilterSheetDetail-----------");
	        AbstractXlsView abstractxlsView = o2oGoodsFilterServiceImpl.exportO2oGoodsFilterSheetDetail(map);
	        return new ModelAndView(abstractxlsView, model);
	    }
	 
	 @ApiOperation(value = "导入商品提报单明细", notes = "导入商品提报单明细")
	    @ApiImplicitParams({
	            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
	        
	    })
	 @PostMapping(value = "/importO2oGoodsFilterDetail")
	    public JsonResponse importO2oGoodsFilterDetail(@RequestParam(value = "data",required = false) String data,MultipartHttpServletRequest request) {
	        logger.info("------------importO2oGoodsFilterDetail-----------");
	        Map<String, Object> map = JSONObject.parseObject(data);
	        //String userName = SessionHelper.getUserName();
	        //String userName = request.getSession().getAttribute("username").toString();
	        //map.put("userName", userName);
	        try {
	            List<MultipartFile> multipartFiles = request.getFiles("file");
	            if (multipartFiles.size() == 0) {
	                throw new FileNotFoundException("file not exists");
	            }
	            MultipartFile file = multipartFiles.get(0);
	            Workbook wb = WorkbookFactory.create(file.getInputStream());
                List<O2oGoodsFilterSheetDetail> list = new ArrayList<>();
	            for(Sheet sheet : wb){
	                for(Row row : sheet){
	                	if(row.getRowNum()<1){
	                		continue;
	                		}
	                	row.getCell(0).setCellType(CellType.STRING);
	                	row.getCell(1).setCellType(CellType.STRING);
	                	row.getCell(2).setCellType(CellType.STRING);
	                	O2oGoodsFilterSheetDetail o2oGoodsFilterSheetDetail  = new O2oGoodsFilterSheetDetail();
	                    String shopId = row.getCell(0).getStringCellValue();
	                    String itemCode = row.getCell(1).getStringCellValue();
	                    String goodsName = row.getCell(2).getStringCellValue();
	                    if(row.getCell(3)!=null) {
		                	String status = row.getCell(3).getStringCellValue();
		                	if(status.equals("拒绝")) {
		                		o2oGoodsFilterSheetDetail.setStatus(0);
		                	}else {
		                		o2oGoodsFilterSheetDetail.setStatus(1);
		                	}
		                }else {
		                	o2oGoodsFilterSheetDetail.setStatus(1);
	                    }
	                    
	                    if(row.getCell(4)!=null) {
	                    	String statusMsg = row.getCell(4).getStringCellValue();
	                    	o2oGoodsFilterSheetDetail.setStatusMsg(statusMsg);
	                    }else {
		                	o2oGoodsFilterSheetDetail.setStatusMsg("");
	                    }
	                    o2oGoodsFilterSheetDetail.setShopId(shopId);
	                    o2oGoodsFilterSheetDetail.setItemCode(itemCode);
	                    o2oGoodsFilterSheetDetail.setGoodsName(goodsName);
	                    list.add(o2oGoodsFilterSheetDetail);
	                    
	                }
	            }
	            o2oGoodsFilterServiceImpl.importO2oGoodsFilterSheetDetail(list,map);
	            wb.close();
	            return JsonResponse.ok("导入成功");
	        } catch (Exception e) {
	        	e.printStackTrace();
	            logger.error(e.getMessage());
	            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
	        }
	    }

	 
	 
	    @ApiOperation(value = "获取拒绝信息", notes = "获取拒绝信息")
	    @ApiImplicitParams({
	            @ApiImplicitParam(name = "data", value = "{}", required = true, dataType = "String"),
	    })
	    @RequestMapping(value = "/getStatusMsg", method = RequestMethod.GET)
	    public JsonResponse getStatusMsg(@RequestParam(value = "data") String data) {
	        logger.info("------------getStatusMsg-----------");
	        Map<String,Object> map1 = new HashMap<>();
	        Map<String,Object> map2 = new HashMap<>();
	        Map<String,Object> map3 = new HashMap<>();
	        List<Map<String,Object>> list = new ArrayList<>();
	        try {
	            //执行查询
	        	map1.put("id", 1);
	        	map1.put("msg", "禁售商品");
	        	map2.put("id", 2);
	        	map2.put("msg", "下架商品");
	        	map3.put("id", 3);
	        	map3.put("msg", "违法商品");
	        	list.add(map1);
	        	list.add(map2);
	        	list.add(map3);
	            //获取查询结果
	            return JsonResponse.ok(list);
	        } catch (Exception e) {
	            logger.debug(e.toString());
	            return JsonResponse.SERVER_ERR;
	        }
	    } 
}
