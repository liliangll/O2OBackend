package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoods;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oInventoryLog;
import cn.com.efuture.o2o.backend.mybatis.mapper.O2oGoodsMapper;
import cn.com.efuture.o2o.backend.util.NewHashMap;
import cn.com.efuture.o2o.backend.util.PageCount;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class O2oGoodsServiceImpl implements PageCount {

    private final O2oGoodsMapper o2oGoodsMapper;

    public O2oGoodsServiceImpl(O2oGoodsMapper o2oGoodsMapper) {
        this.o2oGoodsMapper = o2oGoodsMapper;
    }

    public List<O2oGoods> getO2oGoodsList(Map<String, Object> map) {
        return o2oGoodsMapper.getO2oGoodsList(map);
    }

    public O2oGoods getO2oGoods(Map<String, Object> map) {
        return o2oGoodsMapper.getO2oGoods(map);
    }

    @Transactional
    public void updateO2oGoods(O2oGoods uiGoods, String userName) {

        O2oGoodsLog o2oGoodsLog = new O2oGoodsLog();
        o2oGoodsLog.setItemId(uiGoods.getItemId());
        o2oGoodsLog.setSkuId(uiGoods.getSkuId());
        o2oGoodsLog.setChannelId(uiGoods.getChannelId());
        o2oGoodsLog.setShopId(uiGoods.getShopId());
        o2oGoodsLog.setOperator(userName);
        // 不修改图片
        o2oGoodsLog.setIsUpdateImage(0);

        Map<String, Object> jsonObject = new NewHashMap<>();
        jsonObject.put("itemId", uiGoods.getItemId());
        jsonObject.put("channelId", uiGoods.getChannelId());
        jsonObject.put("skuId", uiGoods.getSkuId());
        // 获取数据库中数据
        O2oGoods dbGoods = o2oGoodsMapper.getO2oGoods(jsonObject);

        // update对象
        O2oGoods updateGoods = new O2oGoods();
        // 更新字段
        updateGoods.setGoodsName(uiGoods.getGoodsName());
        updateGoods.setGoodsEnName(uiGoods.getGoodsEnName());
        updateGoods.setUnit(uiGoods.getUnit());
        updateGoods.setSpec(uiGoods.getSpec());
        updateGoods.setCategoryId(uiGoods.getCategoryId());
        updateGoods.setBarcode(uiGoods.getBarcode());
        updateGoods.setWeight(uiGoods.getWeight());

        if (uiGoods.getSafeInventory() == null) {// 页面批量修改锁定库存时，取数据库安全库存来计算可卖数
            updateGoods.setSafeInventory(dbGoods.getSafeInventory());
        } else {
            updateGoods.setSafeInventory(uiGoods.getSafeInventory());
        }
        updateGoods.setShelved(uiGoods.getShelved());
        updateGoods.setLockInventoryRefresh(uiGoods.getLockInventoryRefresh());

        // 价格处理
        boolean priceLog = false;
        // 销售价格处理
        if (uiGoods.getPriceRate() == null) { // 没有价格系数时
            updateGoods.setPriceRate(dbGoods.getPriceRate());
            updateGoods.setSalesPrice(dbGoods.getErpPrice() * dbGoods.getPriceRate());
        } else {
            updateGoods.setPriceRate(uiGoods.getPriceRate());
            if (uiGoods.getPriceRate() <= 0) {
                throw new RuntimeException(uiGoods.getPriceRate() + "价格系数必须大于0");
            } else if (!uiGoods.getPriceRate().equals(dbGoods.getPriceRate())) {
                updateGoods.setSalesPrice(dbGoods.getErpPrice() * uiGoods.getPriceRate());
            } else if (uiGoods.getPriceRate() == dbGoods.getPriceRate()) {
                updateGoods.setSalesPrice(dbGoods.getErpPrice() * dbGoods.getPriceRate());
            }
        }
        // 锁定价格填空字符串时处理
        if (uiGoods.getLockPrice() == null) {
            if (dbGoods.getLockPrice() != null) {
                updateGoods.setLockPrice(-1.0);
                priceLog = true;
                o2oGoodsLog.setPrice(updateGoods.getSalesPrice());
            }
        } else {
            if (uiGoods.getLockPrice() <= 0) {
                throw new RuntimeException(" 锁定价格必须大于0");
            }
            if (!uiGoods.getLockPrice().equals(dbGoods.getLockPrice())) {
                updateGoods.setLockPrice(uiGoods.getLockPrice());
                priceLog = true;
                o2oGoodsLog.setPrice(updateGoods.getLockPrice());
            }
        }

        O2oInventoryLog o2oInventoryLog = new O2oInventoryLog();
        o2oInventoryLog.setItemId(uiGoods.getItemId());
        o2oInventoryLog.setSkuId(uiGoods.getSkuId());
        o2oInventoryLog.setChannelId(uiGoods.getChannelId());
        o2oInventoryLog.setShopId(uiGoods.getShopId());
        o2oInventoryLog.setOperator(userName);
        //库存处理
        boolean inventoryLog = false;
        // 锁定库存填空字符串时，则认为锁定库存无效，更新为null
        if (uiGoods.getLockInventory() == null) {
            if (dbGoods.getLockInventory() != null) {
                updateGoods.setLockInventory(-1);
                inventoryLog = true;
                o2oInventoryLog.setActualStock(dbGoods.getP4Inventory() - dbGoods.getSafeInventory() - dbGoods.getSaleQty());
            }
        } else {
            if (uiGoods.getLockInventory() < 0) {
                //用户设置锁定库存为负数，则认为锁定库存无效，更新为null
                updateGoods.setLockInventory(-1);
                inventoryLog = true;
                o2oInventoryLog.setActualStock(dbGoods.getP4Inventory() - dbGoods.getSafeInventory() - dbGoods.getSaleQty());
            }
            if (//uiGoods.getSysInventory() != dbGoods.getSysInventory()|| 页面还没有SysInventory
//				(uiGoods.getLockInventory() != dbGoods.getLockInventory() && uiGoods.getSysInventory()==0)
                    uiGoods.getLockInventory() != dbGoods.getLockInventory()) {
                updateGoods.setLockInventory(uiGoods.getLockInventory());
                inventoryLog = true;
                o2oInventoryLog.setActualStock(updateGoods.getLockInventory());
            }
        }

        // 商品状态处理
        Integer oldStatus = uiGoods.getOldStatus();
        Integer newStatus = uiGoods.getNewStatus();

        if (oldStatus != 0 && newStatus == 0) {
            throw new RuntimeException("已启用或禁用的门店商品不能修改成未启用");
        } else if (oldStatus == 0 && newStatus == 0) {// 0--0  不管了
        } else if (oldStatus == 0 && newStatus == -1) {
            throw new RuntimeException("未启用商品不能修改成禁用");
        } else if (oldStatus == -1 && newStatus == -1) {
            throw new RuntimeException("禁用商品只能修改状态列");
        } else if (oldStatus == 1 && newStatus == -1) {
            // 商品禁用 --类型--下架--5
//        	updateGoods.setShelved(0);
            o2oGoodsLog.setTaskType(5);
            o2oGoodsLog.setShelved(0);
        } else if (oldStatus == -1 && newStatus == 1) {
            // 商品从禁用到发布
//        	updateGoods.setShelved(0);
        } else if (oldStatus == 0 && newStatus == 1) {
            // 新品发布--类型 --新增 --1
            o2oGoodsLog.setTaskType(1);
        } else if (oldStatus == 1 && newStatus == 1) {
        } else {
            throw new RuntimeException("状态异常，原状态：" + oldStatus + ",新状态：" + newStatus);
        }

        // 商品自定义--没拿到处理
        Integer oldIsSelf = dbGoods.getIsSelf();
        if (uiGoods.getIsSelf() == null) {// 如果没有自定义字段，设为dbGoods.getIsSelf()
            updateGoods.setIsSelf(oldIsSelf);
            uiGoods.setIsSelf(oldIsSelf);
        } else {
            updateGoods.setIsSelf(uiGoods.getIsSelf());
        }

        // 商品从发布--禁用  或     禁用--发布时，仅能更新状态和上下架
        if (oldStatus == 1 && newStatus == -1 || oldStatus == -1 && newStatus == 1) {
            updateGoods = new O2oGoods();
            updateGoods.setShelved(0);
            // 商品从发布--禁用，原本就是下架的商品不产生下架日志
            if (o2oGoodsLog.getTaskType() != null && updateGoods.getShelved().equals(dbGoods.getShelved())) {
                o2oGoodsLog.setTaskType(null);
            }
        }
        // 更新门店商品状态
        updateGoods.setStatus(newStatus);

        // update对象更新条件
        updateGoods.setItemId(dbGoods.getItemId());
        updateGoods.setSkuId(dbGoods.getSkuId());
        updateGoods.setChannelId(dbGoods.getChannelId());
        updateGoods.setShopId(dbGoods.getShopId());
        updateGoods.setUserName(userName);

        // 更新门店商品信息
        o2oGoodsMapper.updateO2oGoods(updateGoods);

        // 从自定义变为不自定义
        if (dbGoods.getIsSelf() == 1 && uiGoods.getIsSelf() == 0) {
            // 强制名称等字段和商品主档一致
            o2oGoodsMapper.updateO2oGoodsByProduct(updateGoods);
        }

        // 门店商品日志----状态日志
        if (o2oGoodsLog.getTaskType() != null) {
            // 写入门店商品日志表
            o2oGoodsMapper.insertO2oGoodsLog(o2oGoodsLog);
        }

        //已发布商品更新
        if (oldStatus == 1 && newStatus == 1) {
            // 编辑日志
            if (uiGoods.getGoodsName() != null && !uiGoods.getGoodsName().equals(dbGoods.getGoodsName()) ||
                    uiGoods.getGoodsEnName() != null && !uiGoods.getGoodsEnName().equals(dbGoods.getGoodsEnName()) ||
                    uiGoods.getSpec() != null && !uiGoods.getSpec().equals(dbGoods.getSpec()) ||
                    uiGoods.getUnit() != null && !uiGoods.getUnit().equals(dbGoods.getUnit()) ||
                    uiGoods.getBarcode() != null && !uiGoods.getBarcode().equals(dbGoods.getBarcode()) ||
                    uiGoods.getWeight() != null && !uiGoods.getWeight().equals(dbGoods.getWeight()) ||
                    uiGoods.getCategoryId() != null && !uiGoods.getCategoryId().equals(dbGoods.getCategoryId()) ||
                    uiGoods.getIsSelf() != null && !uiGoods.getIsSelf().equals(dbGoods.getIsSelf())) {
                // 门店商品信息更新--类型--修改
                o2oGoodsLog.setTaskType(2);
                // 写入门店商品日志表
                o2oGoodsMapper.insertO2oGoodsLog(o2oGoodsLog);
            }

            // 已发布商品锁定价格
            if (priceLog) {
                // 锁定价格--类型--价格--4
                o2oGoodsLog.setTaskType(4);
                // 写入门店商品日志表
                o2oGoodsMapper.insertO2oGoodsLog(o2oGoodsLog);
            }

            // 已发布商品更新上下架状态
            if (uiGoods.getShelved() != null && !uiGoods.getShelved().equals(dbGoods.getShelved())) {
                // 更新上下架--类型--上下架--5
                o2oGoodsLog.setTaskType(5);
                // 写入门店商品日志表
                o2oGoodsMapper.insertO2oGoodsLog(o2oGoodsLog);
            }

            // 已发布商品锁定库存
            if (inventoryLog) {
                // 锁定库存--库存--写入门店商品库存日志表
                o2oGoodsMapper.insertO2oInventoryLog(o2oInventoryLog);
            }
        }
    }

    // 手工触发时，写入门店商品库存日志
    public void insertInventoryLog(O2oInventoryLog o2oInventoryLog, String userName) {
        o2oInventoryLog.setOperator(userName);
        o2oGoodsMapper.insertInventoryLog(o2oInventoryLog);
    }


    @Override
    public long getCount(Map<String, Object> map) {
        return o2oGoodsMapper.getO2oGoodsListCount(map);
    }
}
