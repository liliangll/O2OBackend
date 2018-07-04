package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

/**
 * @author 门店商品日志
 */

public class O2oGoodsLog {

	private Long logId; //
	private String operator; // 操作人员ID
	private Integer channelId; // 渠道id
	private String channelShopId; // 渠道店编码
	private String shopId; // 家乐福门店

	private Integer taskType; // 任务类型1=新增 2=修改 3=删除 4=价格 5=上下架
	private Long itemId; // 内部商品编码
	private Long skuId; // 内部SKU编码
	private String itemCode; // 家乐福商品编码
	private String o2oItemId; // 外卖商品编码

	private String o2oSkuId; // 外卖skuid
	private Integer shelved; // 上下架状态 0=下架 1=上架
	private Double price; // 售价
	private Date createTime; // 任务创建时间
	private Date processTime; // 最近处理时间

	private Integer processCount; // 处理次数
	private Integer processStatus; // 0待处理 100完成 99异常
	private String processMsg; // 状态消息

	private Integer isUpdateImage;
	private Integer isSelf;
	private String batchId; // 任务批

	private Integer retailFormatId;

	public Integer getRetailFormatId() {
		return retailFormatId;
	}

	public void setRetailFormatId(Integer retailFormatId) {
		this.retailFormatId = retailFormatId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Integer getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(Integer isSelf) {
		this.isSelf = isSelf;
	}

	public Integer getIsUpdateImage() {
		return isUpdateImage;
	}

	public void setIsUpdateImage(Integer isUpdateImage) {
		this.isUpdateImage = isUpdateImage;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
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

	public String getChannelShopId() {
		return channelShopId;
	}

	public void setChannelShopId(String channelShopId) {
		this.channelShopId = channelShopId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getO2oItemId() {
		return o2oItemId;
	}

	public void setO2oItemId(String o2oItemId) {
		this.o2oItemId = o2oItemId;
	}

	public String getO2oSkuId() {
		return o2oSkuId;
	}

	public void setO2oSkuId(String o2oSkuId) {
		this.o2oSkuId = o2oSkuId;
	}

	public Integer getShelved() {
		return shelved;
	}

	public void setShelved(Integer shelved) {
		this.shelved = shelved;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
		this.processMsg = processMsg;
	}

}
