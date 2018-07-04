package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.GenFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMongoMapper extends MongoRepository<GenFile, String> {
}
