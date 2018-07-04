package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

public class ChannelShopLog {
    private Integer logId;

    private String operator;

    private Integer channelId;

    private String shopId;

    private String channelShopId;

    private Integer taskType;

    private Integer isOpen;

    private String notice;

    private String openTime;

    private Date createTime;

    private Date processTime;

    private Integer processCount;

    private Integer processStatus;

    private String processMsg;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getChannelShopId() {
        return channelShopId;
    }

    public void setChannelShopId(String channelShopId) {
        this.channelShopId = channelShopId == null ? null : channelShopId.trim();
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime == null ? null : openTime.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Integer getProcessCount() {
        return processCount;
    }

    public void setProcessCount(Integer processCount) {
        this.processCount = processCount;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessMsg() {
        return processMsg;
    }

    public void setProcessMsg(String processMsg) {
        this.processMsg = processMsg == null ? null : processMsg.trim();
    }
}