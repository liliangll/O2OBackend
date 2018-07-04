package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oProduct;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProductMapper {

    long getProductListCount(Map<String, Object> map);

    List<O2oProduct> getProductList(Map<String, Object> map);

    O2oProduct getProduct(Map<String, Object> map);

    void updateProduct(O2oProduct o2oProduct);

}
