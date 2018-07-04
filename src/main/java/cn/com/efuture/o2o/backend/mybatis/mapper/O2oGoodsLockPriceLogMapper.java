package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsLockPrice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface O2oGoodsLockPriceLogMapper {

	List<O2oGoodsLockPrice> getO2oGoodsLockPriceList(Map<String, Object> map);

	long getO2oGoodsLockPriceListCount(Map<String, Object> map);

	List<O2oGoodsLockPrice> getO2oGoodsLockPriceByItemCode(Map<String, Object> map);
}
