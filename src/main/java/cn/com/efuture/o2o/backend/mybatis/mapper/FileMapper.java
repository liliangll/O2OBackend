package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.ExportJob;
import cn.com.efuture.o2o.backend.mybatis.entity.ImportJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface FileMapper {

    void insertImportJob(ImportJob importJob);

    void insertExportJob(ExportJob exportJob);

    List<ImportJob> getImportJobList(Map<String, Object> map);

    List<ExportJob> getExportJobList(Map<String, Object> map);
}
