package cn.com.efuture.o2o.backend.mybatis.service;

import java.util.List;
import java.util.Map;

import cn.com.efuture.o2o.backend.util.PageCount;
import org.springframework.stereotype.Service;
import cn.com.efuture.o2o.backend.mybatis.mapper.SalesReportMapper;

@Service
public class SalesReportServiceImpl implements PageCount{

	private final SalesReportMapper salesReportMapper;
	
	public SalesReportServiceImpl(SalesReportMapper salesReportMapper) {
		super();
		this.salesReportMapper = salesReportMapper;
	}

	public List<Map<String,String>> getSalesReport(Map<String, Object> p){	
		return salesReportMapper.getSalesReport(p);
	}

	@Override
	public long getCount(Map<String, Object> map) {
		return salesReportMapper.getSalesReportCount(map);
	}
}
