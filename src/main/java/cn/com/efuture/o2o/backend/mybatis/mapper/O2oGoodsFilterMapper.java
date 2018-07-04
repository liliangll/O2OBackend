package cn.com.efuture.o2o.backend.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsFilterSheetDetail;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsFilterSheetHead;

@Repository
@Mapper
public interface O2oGoodsFilterMapper {
	
	List<Map<String,Object>>getO2oGoodsFilterSheetHead(Map<String, Object> map);
	
	List<Map<String,Object>>getO2oGoodsFilterSheetDetail(Map<String, Object> map);
	
	Map<String,Object> getSheetGoodsNameById(String itemCode);
	
	int getSheetRowNoMax(String sheetId);
	
	void insertO2oGoodsFilterSheetHead(O2oGoodsFilterSheetHead o2oGoodsFilterSheetHead);
	
	void insertO2oGoodsFilterSheetDetail(O2oGoodsFilterSheetDetail o2oGoodsFilterSheetDetail);
	
	void updateO2oGoodsFilterSheetHead(O2oGoodsFilterSheetHead o2oGoodsFilterSheetHead);
	
	void updateO2oGoodsFilterSheetDetail(O2oGoodsFilterSheetDetail o2oGoodsFilterSheetDetail);
	
	void deleteO2oGoodsFilterSheetDetail(O2oGoodsFilterSheetDetail o2oGoodsFilterSheetDetail);
	
	void importO2oGoodsFilterSheetDetail(List<O2oGoodsFilterSheetDetail> list);
	
	long getO2oGoodsFilterListCount(Map<String, Object> map);

    List<Map<String, Object>> getO2oGoodsFilterList(Map<String, Object> map);

    void insertO2oGoodsFilter(Map<String, Object> map);
    
    void insertO2oGoodsFilters(Map<String, Object> map);

    List<String> getShopIdByName(String userName);
}
