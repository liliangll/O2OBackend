package cn.com.efuture.o2o.backend.mybatis.entity;

/**
 * 物流时间
 */
public class LogisticsTime {
    private Integer logisticsTimeId;
    private String logisticsTimeName;
    private String logisticsType;
    private String timeStart;
    private String timeEnd;
    private Double fee;

    public Integer getLogisticsTimeId() {
        return logisticsTimeId;
    }

    public void setLogisticsTimeId(Integer logisticsTimeId) {
        this.logisticsTimeId = logisticsTimeId;
    }

    public String getLogisticsTimeName() {
        return logisticsTimeName;
    }

    public void setLogisticsTimeName(String logisticsTimeName) {
        this.logisticsTimeName = logisticsTimeName;
    }

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
