package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsLockPrice;
import cn.com.efuture.o2o.backend.mybatis.mapper.O2oGoodsLockPriceLogMapper;
import cn.com.efuture.o2o.backend.util.PageCount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class O2oGoodsLockPriceServiceImpl implements PageCount {

    private final  O2oGoodsLockPriceLogMapper o2oGoodsLockPriceMapper;

    public O2oGoodsLockPriceServiceImpl(O2oGoodsLockPriceLogMapper o2oGoodsLockPriceMapper) {
        this.o2oGoodsLockPriceMapper = o2oGoodsLockPriceMapper;
    }

    public List<O2oGoodsLockPrice> getO2oGoodsLockPriceList(Map<String,Object> map) {
        return o2oGoodsLockPriceMapper.getO2oGoodsLockPriceList(map);
    }

    @Override
    public long getCount(Map<String, Object> map) {
        return o2oGoodsLockPriceMapper.getO2oGoodsLockPriceListCount(map);
    }

    public List<O2oGoodsLockPrice> getO2oGoodsLockPriceByItemCode(Map<String,Object> map) {
        return o2oGoodsLockPriceMapper.getO2oGoodsLockPriceByItemCode(map);
    }
}
