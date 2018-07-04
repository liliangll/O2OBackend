package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.Shop;
import cn.com.efuture.o2o.backend.mybatis.mapper.ShopMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "shop")
public class ShopServiceImpl {

    private final ShopMapper shopMapper;

    public ShopServiceImpl(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Shop> getShopList(Map<String, Object> map) {
        return shopMapper.getShopList(map);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Shop> getCityList(Map<String, Object> map) {
        return shopMapper.getCityList(map);
    }


    public Shop getShopByShopId(Map<String, Object> map) {
        return shopMapper.getShopByShopId(map);
    }
}
