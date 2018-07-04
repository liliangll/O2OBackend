package cn.com.efuture.o2o.backend.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoods;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oInventoryLog;

@Repository
@Mapper
public interface O2oGoodsMapper {

	List<O2oGoods> getO2oGoodsList(Map<String, Object> map); // 获取门店商品列表

	O2oGoods getO2oGoods(Map<String, Object> map); // 获取门店商品信息

	void updateO2oGoods(O2oGoods uiGoods); // 修改门店商品信息

	void insertO2oGoodsLog(O2oGoodsLog o2oGoodsLog); // 写入门店商品日志
	
	void insertO2oInventoryLog(O2oInventoryLog o2oInventoryLog); // 写入门店商品库存日志
	
	void insertInventoryLog(O2oInventoryLog o2oInventoryLog); // 手工触发时，写入门店商品库存日志

	void updateO2oGoodsByProduct(O2oGoods uiGoods); // 根据主档商品来更新门店商品

    long getO2oGoodsListCount(Map<String, Object> map);
}
