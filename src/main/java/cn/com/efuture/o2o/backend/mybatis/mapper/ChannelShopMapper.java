package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.ChannelShop;
import cn.com.efuture.o2o.backend.mybatis.entity.ChannelShopLog;
import cn.com.efuture.o2o.backend.mybatis.entity.ChannelShopTime;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ChannelShopMapper {

    List<ChannelShop> getChannelShopList(Map<String, Object> map);

    ChannelShop getChannelShop(Map<String, Object> map);

    void insertChannelShop(ChannelShop channelShop);

    void updateChannelShop(ChannelShop channelShop);

    List<ChannelShopTime> getChannelShopTime(Map<String, Object> map);
    
    void updateChannelShopTime(ChannelShopTime channelShopTime);

    void insertChannelShopLog(ChannelShopLog channelShopLog);


}
