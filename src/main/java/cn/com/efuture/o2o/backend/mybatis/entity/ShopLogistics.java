package cn.com.efuture.o2o.backend.mybatis.entity;



public class ShopLogistics {
    private String shopId;

    private String logisticsType;

    private Integer firstWeight;

    private Double firstFee;

    private Double nextFee;

    private Integer limitWeight;

    private Integer freeShippingId;

    private Integer logisticsTimeId;

    private Integer isToday;

    private Integer usedTime;

    private Integer choseDays;

    private String gid;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public Integer getFirstWeight() {
        return firstWeight;
    }

    public void setFirstWeight(Integer firstWeight) {
        this.firstWeight = firstWeight;
    }

    public Double getFirstFee() {
        return firstFee;
    }

    public void setFirstFee(Double firstFee) {
        this.firstFee = firstFee;
    }

    public Double getNextFee() {
        return nextFee;
    }

    public void setNextFee(Double nextFee) {
        this.nextFee = nextFee;
    }

    public Integer getLimitWeight() {
        return limitWeight;
    }

    public void setLimitWeight(Integer limitWeight) {
        this.limitWeight = limitWeight;
    }

    public Integer getFreeShippingId() {
        return freeShippingId;
    }

    public void setFreeShippingId(Integer freeShippingId) {
        this.freeShippingId = freeShippingId;
    }

    public Integer getLogisticsTimeId() {
        return logisticsTimeId;
    }

    public void setLogisticsTimeId(Integer logisticsTimeId) {
        this.logisticsTimeId = logisticsTimeId;
    }

    public Integer getIsToday() {
        return isToday;
    }

    public void setIsToday(Integer isToday) {
        this.isToday = isToday;
    }

    public Integer getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Integer usedTime) {
        this.usedTime = usedTime;
    }

    public Integer getChoseDays() {
        return choseDays;
    }

    public void setChoseDays(Integer choseDays) {
        this.choseDays = choseDays;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}