package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ShopMapper {
    List<Shop> getShopList(Map<String, Object> map);

    List<Shop> getCityList(Map<String, Object> map);

    Shop getShopByShopId(Map<String, Object> map);
}
