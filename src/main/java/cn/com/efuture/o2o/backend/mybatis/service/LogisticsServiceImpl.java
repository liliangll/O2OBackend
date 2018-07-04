package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.LogisticsFreeShipping;
import cn.com.efuture.o2o.backend.mybatis.entity.LogisticsTime;
import cn.com.efuture.o2o.backend.mybatis.entity.LogisticsType;
import cn.com.efuture.o2o.backend.mybatis.entity.ShopLogistics;
import cn.com.efuture.o2o.backend.mybatis.mapper.LogisticsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsServiceImpl {

    private final LogisticsMapper logisticsMapper;

    public LogisticsServiceImpl(LogisticsMapper logisticsMapper) {
        this.logisticsMapper = logisticsMapper;
    }


    public List<LogisticsType> getLogisticsTypeList(){
        return  logisticsMapper.getLogisticsTypeList();
    }

    public List<LogisticsTime> getLogisticsTimeList(String data) {
        return  logisticsMapper.getLogisticsTimeList(data);
    }

    public List<LogisticsFreeShipping> getLogisticsFreeShippingList(String data) {
        return  logisticsMapper.getLogisticsFreeShippingList(data);
    }
}
