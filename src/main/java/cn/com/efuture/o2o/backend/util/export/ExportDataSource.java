package cn.com.efuture.o2o.backend.util.export;

import java.util.List;
import java.util.Map;

public interface ExportDataSource {

	List<Map<String, Object>> getData(int pageSize, int page);

}