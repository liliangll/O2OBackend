package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.RetailFormat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RetailFormatMapper {
    List<RetailFormat> getRetailFormatList();

    List<RetailFormat> getRetailFormatListByUserId(Map<String, Object> map);

    List<RetailFormat> getRetailFormatListByUserName(Map<String,Object> map);
}
