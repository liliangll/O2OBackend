package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.RetailFormat;
import cn.com.efuture.o2o.backend.mybatis.mapper.RetailFormatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RetailFormatServiceImpl {
    private final RetailFormatMapper retailFormatMapper;

    @Autowired
    public RetailFormatServiceImpl(RetailFormatMapper retailFormatMapper) {
        this.retailFormatMapper = retailFormatMapper;
    }

    public List<RetailFormat> getRetailFormatList(){
        return retailFormatMapper.getRetailFormatList();
    }

    public List<RetailFormat> getRetailFormatListByUserId(Map<String, Object> map) {
        return retailFormatMapper.getRetailFormatListByUserId(map);
    }

    public List<RetailFormat> getRetailFormatListByUserName(Map<String,Object> map) {
        return retailFormatMapper.getRetailFormatListByUserName(map);
    }
}
