package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.Channel;
import cn.com.efuture.o2o.backend.mybatis.mapper.ChannelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "channel")
public class ChannelServiceImpl {

    private final ChannelMapper channelMapper;

    public ChannelServiceImpl(ChannelMapper channelMapper) {
        this.channelMapper = channelMapper;
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Channel> getChannelList(Map<String, Object> map) {
        return channelMapper.getChannelList(map);
    }

    public Channel getChannel(Map<String, Object> map) {
        return channelMapper.getChannel(map);
    }
}
