package cn.com.efuture.o2o.backend.mybatis.entity;

public class LogisticsFreeShipping {

    private Integer freeShippingId;

    private String freeShippingName;

    private String logisticsType;

    private  Double amount;

    private  Double amountMax;

    private Integer weight;


    public Integer getFreeShippingId() {
        return freeShippingId;
    }

    public void setFreeShippingId(Integer freeShippingId) {
        this.freeShippingId = freeShippingId;
    }

    public String getFreeShippingName() {
        return freeShippingName;
    }

    public void setFreeShippingName(String freeShippingName) {
        this.freeShippingName = freeShippingName;
    }

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(Double amountMax) {
        this.amountMax = amountMax;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
