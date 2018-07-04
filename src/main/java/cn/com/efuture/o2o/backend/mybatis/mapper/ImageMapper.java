package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.ImageFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMapper extends MongoRepository<ImageFile, String> {
//    ImageFile saveImage(ImageFile imageFile);
}
