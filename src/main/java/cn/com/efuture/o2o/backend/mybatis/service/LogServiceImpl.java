package cn.com.efuture.o2o.backend.mybatis.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.com.efuture.o2o.backend.mybatis.entity.ChannelCategoryLog;
import cn.com.efuture.o2o.backend.mybatis.entity.ChannelShopLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oInventoryLog;
import cn.com.efuture.o2o.backend.mybatis.mapper.LogMapper;

@Service
public class LogServiceImpl {

	 private final LogMapper logMapper; 
	 
	 public LogServiceImpl(LogMapper logMapper) {
		super();
		this.logMapper = logMapper;
	}

	public List<ChannelCategoryLog> getChannelCategoryLog(Map<String, Object> map){
		return logMapper.getChannelCategoryLog(map);	 
	 }
	 
	 public List<ChannelShopLog> getChannelShopLog(Map<String, Object> map){
			return logMapper.getChannelShopLog(map);	 
		 }
	 
	 public List<O2oGoodsLog> getO2oGoodsLog(Map<String, Object> map){
		 return logMapper.getO2oGoodsLog(map);
	 }
	 
	 public List<O2oInventoryLog> getO2oInventoryLog(Map<String, Object> map){
		 return logMapper.getO2oInventoryLog(map);
	 }
}
