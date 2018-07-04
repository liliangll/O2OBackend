package cn.com.efuture.o2o.backend.mybatis.entity;

/**
 * 物流类型
 */
public class LogisticsType {
    private String logisticsType;
    private String logisticsName;
    private Integer isFresh;
    private String district;


    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public Integer getIsFresh() {
        return isFresh;
    }

    public void setIsFresh(Integer isFresh) {
        this.isFresh = isFresh;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
