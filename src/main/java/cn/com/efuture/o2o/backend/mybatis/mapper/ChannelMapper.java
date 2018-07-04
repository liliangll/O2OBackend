package cn.com.efuture.o2o.backend.mybatis.mapper;



import cn.com.efuture.o2o.backend.mybatis.entity.Channel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ChannelMapper {

    List<Channel> getChannelList(Map<String, Object> map);

    Channel getChannel(Map<String, Object> map);
}
