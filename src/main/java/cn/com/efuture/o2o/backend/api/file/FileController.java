package cn.com.efuture.o2o.backend.api.file;

import cn.com.efuture.o2o.backend.mybatis.entity.ExportJob;
import cn.com.efuture.o2o.backend.mybatis.entity.ImportJob;
import cn.com.efuture.o2o.backend.mybatis.service.FileServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.FileIoHelper;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import cn.com.efuture.o2o.backend.util.SessionHelper;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mongodb.gridfs.GridFSDBFile;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileServiceImpl fileServiceImpl;
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${FILE_URL}")
    private String URL;

    public FileController(FileServiceImpl fileServiceImpl) {
        this.fileServiceImpl = fileServiceImpl;
    }


    //上传文件
    @PostMapping("/fileUpload")
    public JsonResponse fileUpload(MultipartHttpServletRequest request) {
        logger.info("------------insertImportJob-----------");
        String userName = request.getSession().getAttribute("username").toString();
        try {
            List<MultipartFile> multipartFiles = request.getFiles("file");
            if (multipartFiles.size() == 0) {
                return JsonResponse.notOk("文件不存在");
            }
            MultipartFile file = multipartFiles.get(0);
            if (file.isEmpty()) {
                return JsonResponse.notOk("文件为空");
            }
            String type = request.getParameter("type");
            if (StringUtils.isEmpty(type)) {
                return JsonResponse.notOk("type不能为空");
            }
            fileServiceImpl.insertImportJob(file, userName, type);

            return JsonResponse.ok("上传成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    //根据文件id 下载文件
    @GetMapping("/fileDownload/{id}")
    public ResponseEntity<?> fileDownloadById(@PathVariable String id, HttpServletRequest request) {
        logger.info("------------fileDownloadById-----------");
        HttpHeaders headers = new HttpHeaders();
        String agent = request.getHeader("User-Agent");
        try {
            GridFSDBFile gridFSDBFile = fileServiceImpl.fileDownloadById(id);
            String filename = gridFSDBFile.getFilename();
            if (null != agent) {
                agent = agent.toLowerCase();
                // 火狐浏览器文件中文名处理
                if (agent.contains("firefox")) {
                    headers.add("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(filename, "utf-8")));
                } else {
                    headers.add("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
                }
            }
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.MULTIPART_FORM_DATA).headers(headers).body(FileIoHelper.inputStreamToByteArray(gridFSDBFile.getInputStream()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonResponse.notOk(JsonResponse.ERR, e.getMessage()));
        }
    }

    //导出文件
    @RequestMapping(value = "/fileDownload", method = RequestMethod.POST)
    public JsonResponse fileDownload(@RequestParam(value = "data") String data, HttpServletRequest request) {
        logger.info("------------fileDownload-----------");
        String userName = request.getSession().getAttribute("username").toString();
        JSONObject jsonObject = JSONObject.parseObject(data);
        ExportJob exportJob = new ExportJob();
        exportJob.setType(jsonObject.getString("type"));
        exportJob.setParamers(data);
        try {
            if (StringUtils.isEmpty(exportJob.getType())) {
                return JsonResponse.notOk("type不能为空");
            }
            fileServiceImpl.insertExportJob(exportJob, userName);
            return JsonResponse.ok("正在导出");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @GetMapping("/getImportJobList")
    public JsonResponse getImportJobList(@RequestParam(value = "data") String data) {
        logger.info("------------getImportJobList-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            ParameterHelper.cookPageInfo(map);
            map.put("userName",SessionHelper.getUserName());
            //设置分页信息
            PageHelper.startPage((int) map.get("page"), (int) map.get("pageSize"));
            //执行查询
            List<ImportJob> list = fileServiceImpl.getImportJobList(map);
            //获取查询结果
            PageInfo<ImportJob> pageInfo = new PageInfo<>(list);
            String url = URL + "/file/fileDownload/";
            for (ImportJob importJob : list) {
                importJob.setUrl1(url + importJob.getFileId());
                importJob.setUrl2(url + importJob.getProcessFileId());
            }
            return JsonResponse.ok(pageInfo.getTotal(), list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @GetMapping("/getExportJobList")
    public JsonResponse getExportJobList(@RequestParam(value = "data") String data) {
        logger.info("------------getExportJobList-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            ParameterHelper.cookPageInfo(map);
            map.put("userName",SessionHelper.getUserName());
            //设置分页信息
            PageHelper.startPage((int) map.get("page"), (int) map.get("pageSize"));
            //执行查询
            List<ExportJob> list = fileServiceImpl.getExportJobList(map);
            String url = URL + "/file/fileDownload/";
            for (ExportJob exportJob : list) {
                exportJob.setUrl(url + exportJob.getFileId());
            }
            //获取查询结果
            PageInfo<ExportJob> pageInfo = new PageInfo<>(list);
            return JsonResponse.ok(pageInfo.getTotal(), list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }


    @ApiOperation(value = "导出主档商品列表模板", notes = "导出主档商品列表模板")
    @RequestMapping(value = "/exportProductList", method = RequestMethod.GET)
    public ModelAndView exportProductList(ModelMap model, @RequestParam(value = "data", required = false) String data) {
        Map<String, Object> map = JSONObject.parseObject(data);
        logger.info("------------exportProductList-----------");
        AbstractXlsView abstractxlsView = fileServiceImpl.exportProductList(map);
        return new ModelAndView(abstractxlsView, model);
    }

    @ApiOperation(value = "导出门店商品列表模板", notes = "导出门店商品列表模板")
    @RequestMapping(value = "/exportO2oGoodsList", method = RequestMethod.GET)
    public ModelAndView exportO2oGoodsList(ModelMap model, @RequestParam(value = "data", required = false) String data) {
        Map<String, Object> map = JSONObject.parseObject(data);
        logger.info("------------exportO2oGoodsList-----------");
        AbstractXlsView abstractxlsView = fileServiceImpl.exportO2oGoodsList(map);
        return new ModelAndView(abstractxlsView, model);
    }

    @ApiOperation(value = "导出商品锁价模板", notes = "导出商品锁价模板")
    @RequestMapping(value = "/exportLockPrice", method = RequestMethod.GET)
    public ModelAndView exportLockPrice(ModelMap model, @RequestParam(value = "data", required = false) String data) {
        Map<String, Object> map = JSONObject.parseObject(data);
        logger.info("------------exportLockPrice-----------");
        AbstractXlsView abstractxlsView = fileServiceImpl.exportLockPrice(map);
        return new ModelAndView(abstractxlsView, model);
    }


    @ApiOperation(value = "导出商品提报单明细模板", notes = "导出商品提报单明细模板")
    @RequestMapping(value = "/exportSheetDetail", method = RequestMethod.GET)
    public ModelAndView exportSheetDetail(ModelMap model, @RequestParam(value = "data", required = false) String data) {
        Map<String, Object> map = JSONObject.parseObject(data);
        logger.info("------------exportSheetDetail-----------");
        AbstractXlsView abstractxlsView = fileServiceImpl.exportSheetDetail(map);
        return new ModelAndView(abstractxlsView, model);
    }
/*
    //上传文件
    @PostMapping("/fileUpload")
    public JsonResponse fileUpload(MultipartHttpServletRequest request) {
        logger.info("------------insertImportJob-----------");
        String userName = request.getSession().getAttribute("username").toString();
        try {
            List<MultipartFile> multipartFiles = request.getFiles("file");
            if (multipartFiles.size() == 0) {
                return JsonResponse.notOk("文件不存在");
            }
            MultipartFile file = multipartFiles.get(0);
            if (file.isEmpty()) {
                return JsonResponse.notOk("文件为空");
            }
            String type = request.getParameter("type");
            if (StringUtils.isEmpty(type)) {
                return JsonResponse.notOk("type不能为空");
            }
            GenFile genFile = new GenFile(file.getOriginalFilename(), file.getBytes(), type);
            fileServiceImpl.insertImportJob(genFile,userName);
            return JsonResponse.ok("上传成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @GetMapping("/fileDownload/{id}")
    public ResponseEntity<?> fileDownloadById(@PathVariable String id, HttpServletRequest request) {
        logger.info("------------fileDownloadById-----------");
        HttpHeaders headers = new HttpHeaders();
        String agent = request.getHeader("User-Agent");
        try {
            GenFile genFile = fileServiceImpl.fileDownloadById(id);
            String filename = genFile.getFileName();
            if (null != agent) {
                agent = agent.toLowerCase();
                // 火狐浏览器文件中文名处理
                if (agent.contains("firefox")) {
                    headers.add("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(filename, "utf-8")));
                } else {
                    headers.add("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
                }
            }
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.MULTIPART_FORM_DATA).headers(headers).body(genFile.getBody());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonResponse.notOk(JsonResponse.ERR, e.getMessage()));
        }
    }
*/

}
