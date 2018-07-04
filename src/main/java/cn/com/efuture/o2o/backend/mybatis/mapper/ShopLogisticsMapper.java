package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.ShopLogistics;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ShopLogisticsMapper {

    List<ShopLogistics> getShopLogisticsList();
}