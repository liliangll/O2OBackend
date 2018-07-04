package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.ShopLogistics;
import cn.com.efuture.o2o.backend.mybatis.mapper.ShopLogisticsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopLogisticsServiceImpl {

    private final ShopLogisticsMapper shopLogisticsMapper;

    public ShopLogisticsServiceImpl(ShopLogisticsMapper shopLogisticsMapper) {
        this.shopLogisticsMapper = shopLogisticsMapper;
    }


    public List<ShopLogistics> getShopLogisticsList(){
        return  shopLogisticsMapper.getShopLogisticsList();
    }
}
