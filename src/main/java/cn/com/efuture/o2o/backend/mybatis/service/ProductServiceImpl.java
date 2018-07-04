package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoods;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oProduct;
import cn.com.efuture.o2o.backend.mybatis.mapper.O2oGoodsMapper;
import cn.com.efuture.o2o.backend.mybatis.mapper.ProductMapper;
import cn.com.efuture.o2o.backend.util.PageCount;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements PageCount{

    private final ProductMapper productMapper;

    private final O2oGoodsMapper o2oGoodsMapper;

    public ProductServiceImpl(ProductMapper productMapper, O2oGoodsMapper o2oGoodsMapper) {
        this.productMapper = productMapper;
        this.o2oGoodsMapper = o2oGoodsMapper;
    }

    @Override
    public long getCount(Map<String, Object> map) {
        return productMapper.getProductListCount(map);
    }
    public List<O2oProduct> getProductList(Map<String, Object> map) {
        return productMapper.getProductList(map);
    }

    public O2oProduct getProduct(Map<String, Object> map) {
        return productMapper.getProduct(map);
    }

    @Transactional
    public void updateProduct(O2oProduct o2oProduct, String userName) {
        JSONObject jsonObject = new JSONObject();
        Long itemId = o2oProduct.getItemId();
        Integer retailFormatId = o2oProduct.getRetailFormatId();
        jsonObject.put("itemId", itemId);
        jsonObject.put("retailFormatId", retailFormatId);
        //从数据库中获取数据
        O2oProduct product = productMapper.getProduct(jsonObject);
        //从数据库中获取修改之前的状态
        int oldStatus = product.getStatus();

        int newStatus = o2oProduct.getNewStatus();
        String newGoodsName = o2oProduct.getGoodsName();
        String newGoodsEnName = o2oProduct.getGoodsEnName();
        String newCategoryId = o2oProduct.getCategoryId();
        String newBarcode = o2oProduct.getBarcode();
        String newSpec = o2oProduct.getSpec();
        String newUnit = o2oProduct.getUnit();
        Integer newWeight = o2oProduct.getWeight();
        String imageKey1 = o2oProduct.getImageKey1();

        O2oGoods o2oGoods = new O2oGoods();
        if (oldStatus == 1 && newStatus == -1 || oldStatus == -1 && newStatus == 1) {
            //只要状态含有禁用，门店商品只做下架处理
            o2oGoods.setShelved(0);
            o2oProduct = new O2oProduct();
        } else {
            //更新门店商品数据
            o2oGoods.setIsSelfZero(0); //只修改非自定义门店商品
            o2oGoods.setGoodsName(newGoodsName);
            o2oGoods.setGoodsEnName(newGoodsEnName);
            o2oGoods.setCategoryId(newCategoryId);
            o2oGoods.setBarcode(newBarcode);
            o2oGoods.setSpec(newSpec);
            o2oGoods.setUnit(newUnit);
            o2oGoods.setWeight(newWeight);
        }
        // 修改状态
        if (oldStatus != newStatus) {
            // 只有主档启用变禁用时,才修改门店商品状态
            if (oldStatus == 1 && newStatus == -1) {
                o2oGoods.setStatus(newStatus);
            }
            o2oProduct.setStatus(newStatus);
        }
        //更新商品主档
        //定位唯一商品 itemId + retailFormatId
        o2oProduct.setItemId(itemId);
        o2oProduct.setRetailFormatId(retailFormatId);
        productMapper.updateProduct(o2oProduct);

        //同时更新门店商品状态
        //定位商品 itemId + retailFormatId
        o2oGoods.setItemId(itemId);
        o2oGoods.setRetailFormatId(retailFormatId);
        // 确定用户门店范围 和 用户业态范围
        o2oGoods.setUserName(userName);
        o2oGoodsMapper.updateO2oGoods(o2oGoods);

        //开始日志处理
        O2oGoodsLog o2oGoodsLog = new O2oGoodsLog();
        if (oldStatus == 1 && newStatus == 1) {
            if (!StringUtils.isEmpty(imageKey1)) {
                if (imageKey1.equals(product.getImageKey1())) {
                    o2oGoodsLog.setIsUpdateImage(0);
                } else {
                    //图片更新 类型修改
                    o2oGoodsLog.setIsUpdateImage(1);
                }
            }
            //商品更新 类型修改
            o2oGoodsLog.setTaskType(2);
        } else if (oldStatus == 0 && newStatus == 1) {
            //新品发布 类型新增
            //不产生门店商品任务
//            o2oGoodsLog.setTaskType(1);
        } else if (oldStatus == 1 && newStatus == -1) {
            //商品禁用 类型下架处理
            o2oGoodsLog.setShelved(0);
            o2oGoodsLog.setTaskType(5);
        }
        if (o2oGoodsLog.getTaskType() != null) {
            o2oGoodsLog.setItemId(itemId);
            // 非禁用商品
            if (o2oGoodsLog.getTaskType() != 5) {
                o2oGoodsLog.setIsSelf(0);
            }
            o2oGoodsLog.setOperator(userName);
            o2oGoodsLog.setRetailFormatId(retailFormatId);
            o2oGoodsMapper.insertO2oGoodsLog(o2oGoodsLog);
        }
    }


}
