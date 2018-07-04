package cn.com.efuture.o2o.backend.api.product;

import cn.com.efuture.o2o.backend.mybatis.entity.Dept;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oCategory;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oProduct;
import cn.com.efuture.o2o.backend.mybatis.service.O2oCategoryServiceImpl;
import cn.com.efuture.o2o.backend.mybatis.service.ProductServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductServiceImpl productServiceImpl;
    private final O2oCategoryServiceImpl o2oCategoryServiceImpl;
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${FILE_URL}")
    private String URL;

    public ProductController(ProductServiceImpl productServiceImpl, O2oCategoryServiceImpl o2oCategoryServiceImpl) {
        this.productServiceImpl = productServiceImpl;
        this.o2oCategoryServiceImpl = o2oCategoryServiceImpl;
    }

    @ApiOperation(value = "获取主档商品列表", notes = "获取主档商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemCode", value = "商品编码", dataType = "String"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", dataType = "String"),
            @ApiImplicitParam(name = "barcode", value = "商品条码", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "page", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "Int")
    })
    @RequestMapping(value = "/getProductList", method = RequestMethod.GET)
    public JsonResponse getProductList(@RequestParam(name = "data") String data) {
        logger.info("------------getProductList-----------");
        JSONObject map = JSONObject.parseObject(data);
        try {
            ParameterHelper.cookPageInfo(map);
            map.put("userName", SessionHelper.getUserName());
            long count = ParameterHelper.getCount(map, productServiceImpl);
            // 设置分页信息
            int page = map.getIntValue("page");
            int pageSize = map.getIntValue("pageSize");
            map.put("page", (page - 1) * pageSize);
            map.put("pageSize", pageSize);
            //执行查询
            List<O2oProduct> list = productServiceImpl.getProductList(map);
            return JsonResponse.ok(count, list);
        } catch (Exception e) {
            logger.error(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }

    @ApiOperation(value = "获取主档商品信息", notes = "获取主档商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId", value = "内部货品编码", required = true, dataType = "Int")
    })
    @RequestMapping(value = "/getProduct", method = RequestMethod.GET)
    public JsonResponse getProduct(@RequestParam(name = "data") String data) {
        logger.info("------------getProduct-----------");
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            O2oProduct o2oProduct = productServiceImpl.getProduct(map);
            jsonObject.put("categoryId", o2oProduct.getCategoryId());
            //获取o2o类别
            O2oCategory o2oCategory = o2oCategoryServiceImpl.getCategory(jsonObject);
            if (o2oCategory != null) {
                StringBuilder categoryNames = new StringBuilder();
                o2oProduct.setParentCategoryId(o2oCategory.getParentCategoryId());
                o2oProduct.setCategoryName(o2oCategory.getCategoryName());
                categoryNames.append(o2oCategory.getCategoryName());
                while (!"0".equals(o2oCategory.getParentCategoryId())) {
                    categoryNames.insert(0, "->");
                    jsonObject.put("categoryId", o2oCategory.getParentCategoryId());
                    o2oCategory = o2oCategoryServiceImpl.getCategory(jsonObject);
                    categoryNames.insert(0, o2oCategory.getCategoryName());
                }
                o2oProduct.setCategoryNames(categoryNames.toString());
            }
            //获取业务系统类别
            jsonObject.put("deptId", o2oProduct.getErpCategoryId());
            Dept dept = o2oCategoryServiceImpl.getDept(jsonObject);
            if(dept != null){
                StringBuilder p4CategoryNames = new StringBuilder();
                p4CategoryNames.append(dept.getName());
                while (!"0".equals(dept.getParentId())) {
                    p4CategoryNames.insert(0, "->");
                    jsonObject.put("deptId", dept.getParentId());
                    dept = o2oCategoryServiceImpl.getDept(jsonObject);
                    p4CategoryNames.insert(0, dept.getName());
                }
                o2oProduct.setP4CategoryNames(p4CategoryNames.toString());
            }
            String url = URL + "/image/read/";
            o2oProduct.setImageUrl1(url + o2oProduct.getImageKey1());
            o2oProduct.setImageUrl2(url + o2oProduct.getImageKey2());
            return JsonResponse.ok(o2oProduct);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @ApiOperation(value = "批量修改主档商品信息", notes = "批量修改主档商品信息")
    @RequestMapping(value = "/updateProducts", method = RequestMethod.POST)
    public JsonResponse updateProducts(@RequestParam(value = "data") String data, HttpServletRequest request) {
        logger.info("------------updateProducts-----------");
        List<O2oProduct> O2oProducts = JSONObject.parseArray(data, O2oProduct.class);
        String userName = request.getSession().getAttribute("username").toString();
        try {
            for (O2oProduct o2oProduct : O2oProducts) {
                Integer oldStatus = o2oProduct.getOldStatus();
                Integer newStatus = o2oProduct.getNewStatus();
                if ("0".equals(o2oProduct.getCategoryId())) {
                    return JsonResponse.notOk(500, "[" + o2oProduct.getItemCode() + "]" + o2oProduct.getGoodsName() + "请选择类别");
                }
                if (oldStatus != 0 && newStatus == 0) {
                    return JsonResponse.notOk(500, "[" + o2oProduct.getItemCode() + "]" + o2oProduct.getGoodsName() + "已启用商品不能修改成未启用");
                }
                if (oldStatus == 0 && newStatus == -1) {
                    return JsonResponse.notOk(500, "[" + o2oProduct.getItemCode() + "]" + o2oProduct.getGoodsName() + "未启用商品不能修改成禁用");
                }
                if (oldStatus == -1 && newStatus == -1) {
                    return JsonResponse.notOk(500, "禁用商品只能修改状态列");
                }

                if (o2oProduct.getWeight() != null && o2oProduct.getWeight().toString().length() > 6) {
                    return JsonResponse.notOk(500, "商品重量最大长度为6");
                }
                if (o2oProduct.getLength() != null && o2oProduct.getLength().toString().length() > 4) {
                    return JsonResponse.notOk(500, "商品长度最大长度为4");
                }
                if (o2oProduct.getWidth() != null && o2oProduct.getWidth().toString().length() > 4) {
                    return JsonResponse.notOk(500, "商品宽度最大长度为4");
                }
                if (o2oProduct.getHeight() != null && o2oProduct.getHeight().toString().length() > 4) {
                    return JsonResponse.notOk(500, "商品高度最大长度为4");
                }
                if (o2oProduct.getPkSpec() != null && o2oProduct.getPkSpec().toString().length() > 6) {
                    return JsonResponse.notOk(500, "商品销售规格最大长度为6");
                }
                if (o2oProduct.getUnit() != null && o2oProduct.getUnit().length() > 6) {
                    return JsonResponse.notOk(500, "商品单位最大长度为6");
                }
                productServiceImpl.updateProduct(o2oProduct, userName);
            }
            return JsonResponse.ok("成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

}
