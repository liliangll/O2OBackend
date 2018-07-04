package cn.com.efuture.o2o.backend.mybatis.mapper;

import java.util.List;
import java.util.Map;

import cn.com.efuture.o2o.backend.mybatis.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import cn.com.efuture.o2o.backend.mybatis.entity.ChannelCategory;
import cn.com.efuture.o2o.backend.mybatis.entity.ChannelCategoryLog;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oCategory;

@Repository
@Mapper
public interface O2oCategoryMapper {

	List<O2oCategory> getCategoryList(Map<String, Object> map);
	
	List<Map<String, Object>> getChannelCategoryList(Map<String, Object> map);
	
	List<Map<String, Object>> getCategoryListProduct(Map<String, Object> map);
	
	O2oCategory getCategory(Map<String, Object> map);
	
	void updateCategory(O2oCategory o2oCategory);
	
	void insertCategory(O2oCategory o2oCategory);
	
	void insertChannelCategory(ChannelCategory channelCategory);
	
	void insertChannelCategoryLog(ChannelCategoryLog channelCategoryLog);
	
	int getCategoryIdCount(O2oCategory o2oCategory);
	
	int getSeqNoMax(O2oCategory o2oCategory);
	
	
	//获取业务系统类别
    Dept getDept(Map<String, Object> map);
}
