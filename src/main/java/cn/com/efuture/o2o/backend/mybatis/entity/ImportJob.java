package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

public class ImportJob {
    private Integer id;

    private String type;

    private String typeName;

    private String operator;

    private String fileId;

    private String fileName;

    private Integer status;

    private String processMsg;

    private Date processTime;

    private String processFileId;

    private Date requestTime;

    private String url1;
    private String url2;

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProcessMsg() {
        return processMsg;
    }

    public void setProcessMsg(String processMsg) {
        this.processMsg = processMsg;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public String getProcessFileId() {
        return processFileId;
    }

    public void setProcessFileId(String processFileId) {
        this.processFileId = processFileId;
    }
}