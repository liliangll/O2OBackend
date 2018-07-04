package cn.com.efuture.o2o.backend.mybatis.entity;

public class RetailFormat {
    private Integer retailFormatId;

    private String retailFormatName;

    public Integer getRetailFormatId() {
        return retailFormatId;
    }

    public void setRetailFormatId(Integer retailFormatId) {
        this.retailFormatId = retailFormatId;
    }

    public String getRetailFormatName() {
        return retailFormatName;
    }

    public void setRetailFormatName(String retailFormatName) {
        this.retailFormatName = retailFormatName == null ? null : retailFormatName.trim();
    }
}