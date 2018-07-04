package cn.com.efuture.o2o.backend.mybatis.entity;

import org.springframework.data.annotation.Id;

public class GenFile {

    @Id
    private String fileId;
    private String fileName;
    private String type;
    private byte[] body;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }


    public GenFile() {
    }

    public GenFile(String fileName, byte[] body, String type) {
        this.fileName = fileName;
        this.body = body;
        this.type = type;
    }
}
