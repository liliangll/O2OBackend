package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.LogisticsFreeShipping;
import cn.com.efuture.o2o.backend.mybatis.entity.LogisticsTime;
import cn.com.efuture.o2o.backend.mybatis.entity.LogisticsType;
import cn.com.efuture.o2o.backend.mybatis.entity.ShopLogistics;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LogisticsMapper {

    List<LogisticsType> getLogisticsTypeList();

    List<LogisticsTime> getLogisticsTimeList(String logisticsType);

    List<LogisticsFreeShipping> getLogisticsFreeShippingList(String logisticsType);
}