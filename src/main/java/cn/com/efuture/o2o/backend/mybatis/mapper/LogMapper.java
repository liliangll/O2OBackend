package cn.com.efuture.o2o.backend.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import cn.com.efuture.o2o.backend.mybatis.entity.ChannelCategoryLog;
import cn.com.efuture.o2o.backend.mybatis.entity.ChannelShopLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oInventoryLog;

@Repository
@Mapper
public interface LogMapper {
	
      public List<ChannelCategoryLog> getChannelCategoryLog(Map<String, Object> map);
      
      public List<ChannelShopLog> getChannelShopLog(Map<String, Object> map);
      
      public List<O2oGoodsLog> getO2oGoodsLog(Map<String, Object> map);
      
      public List<O2oInventoryLog> getO2oInventoryLog(Map<String, Object> map);
}
