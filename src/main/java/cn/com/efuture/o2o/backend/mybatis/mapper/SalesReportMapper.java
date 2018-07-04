package cn.com.efuture.o2o.backend.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



@Repository
@Mapper
public interface SalesReportMapper {
	
    List<Map<String,String>> getSalesReport(Map<String, Object> p);

    long getSalesReportCount(Map<String, Object> map);
}
