package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.ExportJob;
import cn.com.efuture.o2o.backend.mybatis.entity.ImportJob;
import cn.com.efuture.o2o.backend.mybatis.mapper.FileMapper;
import cn.com.efuture.o2o.backend.util.export.CommonExportExcel;
import cn.com.efuture.o2o.backend.util.export.ExportDataSource;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceImpl {

    private final FileMapper fileMapper;

    private final MongoDbFactory mongoDbFactory;

    public FileServiceImpl(FileMapper fileMapper, MongoDbFactory mongoDbFactory) {
        this.fileMapper = fileMapper;
        this.mongoDbFactory = mongoDbFactory;
    }

    @Transactional
    public void insertImportJob(MultipartFile file, String userName, String type) throws Exception {
        GridFS gridFS = new GridFS(mongoDbFactory.getDb());
        String fileId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        // 存入mongodb
        GridFSInputFile fsFile = gridFS.createFile(inputStream);
        fsFile.setId(fileId);
        fsFile.setFilename(fileName);
        fsFile.save();

        //插入ImportJob
        ImportJob importJob = new ImportJob();
        importJob.setOperator(userName);
        importJob.setFileId(fileId);
        importJob.setFileName(fileName);
        importJob.setType(type);
        if ("ImportProduct".equals(type)) {
            importJob.setTypeName("导入商品");
        } else if ("ImportGoods".equals(type)) {
            importJob.setTypeName("导入门店商品");
        } else if ("ImportImage".equals(type)) {
            importJob.setTypeName("导入图片");
        } else if ("ImportO2oGoodsFilter".equals(type)) {
            importJob.setTypeName("导入主档商品筛选");
        } else if("ImportLockPrice".equals(type)){
            importJob.setTypeName("导入商品锁价");
        }
        fileMapper.insertImportJob(importJob);
    }

    public GridFSDBFile fileDownloadById(String id) {
        GridFS gridFS = new GridFS(mongoDbFactory.getDb());
        DBObject query = new BasicDBObject("_id", id);
        return gridFS.findOne(query);
    }


    public List<ImportJob> getImportJobList(Map<String, Object> map) {
        return fileMapper.getImportJobList(map);
    }

    public List<ExportJob> getExportJobList(Map<String, Object> map) {
        return fileMapper.getExportJobList(map);
    }

    public void insertExportJob(ExportJob exportJob, String userName) {
        exportJob.setOperator(userName);
        exportJob.setStatus(0);
        if ("ExportProduct".equals(exportJob.getType())) {
            exportJob.setTypeName("导出商品");
        } else if ("ExportGoods".equals(exportJob.getType())) {
            exportJob.setTypeName("导出门店商品");
        } else if ("ExportGoodsLog".equals(exportJob.getType())) {
            exportJob.setTypeName("导出商品操作日志");
        } else if ("ExportO2oInventoryLog".equals(exportJob.getType())) {
            exportJob.setTypeName("导出商品库存日志");
        } else if ("ExportChannelCategoryLog".equals(exportJob.getType())) {
            exportJob.setTypeName("导出类别日志");
        } else if ("ExportChannelShopLog".equals(exportJob.getType())) {
            exportJob.setTypeName("导出门店日志");
        } else if ("ExportO2oGoodsFilter".equals(exportJob.getType())) {
            exportJob.setTypeName("导出主档商品筛选");
        } else if ("ExportSales".equals(exportJob.getType())) {
            exportJob.setTypeName("导出销售报表");
        }
        fileMapper.insertExportJob(exportJob);
    }


    public AbstractXlsView exportProductList(final Map<String, Object> map) {
        // execl 表头数据
        Object[][] title = {
                {"*商品编码", "itemCode", 0, null}, {"商品名称", "goodsName", 0, null}, {"英文名称", "goodsEnName", 0, null},
                {"商品状态", "status", 0, null}, {"分类编码", "categoryId", 0, null}, {"分类名称", "categoryName", 0, null}, {"商品条码", "barcode", 0, null},
                {"商品规格", "spec", 0, null}, {"销售单位", "unit", 0, null}, {"包装数量", "pkSpec", 0, null}, {"属性", "itemType", 0, null},
                {"长(厘米)", "length", 0, null}, {"宽(厘米)", "width", 0, null}, {"高(厘米)", "height", 0, null}, {"重量(克)", "weight", 0, null},
                {"商品描述", "memo", 0, null}};

        return new CommonExportExcel(title, "商品主档表", new ExportDataSource() {
            @Override
            public List<Map<String, Object>> getData(int pageSize, int page) {
                return null;
            }
        });
    }


    // 导出门店商品列表
    public AbstractXlsView exportO2oGoodsList(final Map<String, Object> map) {
        // execl 表头数据
        Object[][] title = {{"*商品编码", "itemCode", 0, null}, {"O2O商品编码", "o2oSkuId", 0, null}, {"门店编码", "shopId", 0, null},
                {"家乐福门店", "shopNameAlias", 0, null}, {"渠道", "channelName", 0, null},
                {"商品名称", "goodsName", 0, null}, {"英文名称", "goodsEnName", 0, null}, {"商品规格", "spec", 0, null},
                {"销售单位", "unit", 0, null}, {"商品条码", "barcode", 0, null}, {"分类编码", "categoryId", 0, null},
                {"分类名称", "categoryName", 0, null}, {"安全库存", "safeInventory", 0, null}, {"锁定库存", "lockInventory", 0, null},
                {"可卖数", "actualInventory", 0, null}, {"业务系统价格", "erpPrice", 0, null},
                {"售卖价格系数", "priceRate", 0, null}, {"销售价", "salesPrice", 0, null}, {"锁定价", "lockPrice", 0, null},
                {"成本价", "costPrice", 0, null}, {"上架状态", "shelved", 0, null}, {"是否活动商品", "isEvent", 0, null},
                {"是否为自定义商品", "isSelf", 0, null}, {"商品状态", "status", 0, null}, {"重量(克)", "weight", 0, null},
                {"锁定库存更新间隔", "lockInventoryRefresh", 0, null}, {"最近价格同步时间", "priceModifyTime", 0, null},
                {"最近库存同步时间", "inventoryModifyTime", 0, null}};

        return new CommonExportExcel(title, "门店商品列表", new ExportDataSource() {
            @Override
            public List<Map<String, Object>> getData(int pageSize, int page) {
                return null;
            }
        });
    }

    // 导出商品锁价模板
    public AbstractXlsView exportLockPrice(final Map<String, Object> map) {
        // execl 表头数据
        Object[][] title = {{"商品编码", "itemCode", 0, null}, {"门店编码", "shopId", 0, null}, {"渠道", "channelName", 0, null},
                {"锁定价", "lockPrice", 0, null},{"开始日期","startTime",0,null},{"结束日期","endTime",0,null}};
        return new CommonExportExcel(title, "商品锁价列表", new ExportDataSource() {
            @Override
            public List<Map<String, Object>> getData(int pageSize, int page) {
                return null;
            }
        });
    }

    // 导出商品提报单明细模板
    public AbstractXlsView exportSheetDetail(final Map<String, Object> map) {
        // execl 表头数据
        Object[][] title = {
                {"门店编码", "shopId", 0, null},{"商品编码", "itemCode", 0, null}, 
                {"商品名称", "goodsName", 0, null}, {"状态", "status", 0, null}, 
                {"拒绝时的原因记录", "statusMsg", 0, null}};

        return new CommonExportExcel(title, "商品提报单明细", new ExportDataSource() {
            @Override
            public List<Map<String, Object>> getData(int pageSize, int page) {
                return null;
            }
        });
    }
/*
    @Transactional
    public void insertImportJob(GenFile genFile, String userName) {
        //存入mongoDB
        fileMongoMapper.save(genFile);
        //插入ImportJob
        ImportJob importJob = new ImportJob();
        importJob.setOperator(userName);
        importJob.setFileId(genFile.getFileId());
        importJob.setFileName(genFile.getFileName());
        importJob.setType(genFile.getType());
        if ("ImportProduct".equals(genFile.getType())) {
            importJob.setTypeName("导入商品");
        } else if ("ImportGoods".equals(genFile.getType())) {
            importJob.setTypeName("导入门店商品");
        } else if ("ImportImage".equals(genFile.getType())) {
            importJob.setTypeName("导入图片");
        } else if ("ImportO2oGoodsFilter".equals(genFile.getType())) {
            importJob.setTypeName("导入主档商品筛选");
        }
        fileMapper.insertImportJob(importJob);
    }

     public GenFile fileDownloadById(String id) {
        return fileMongoMapper.findOne(id);
    }
*/


}
