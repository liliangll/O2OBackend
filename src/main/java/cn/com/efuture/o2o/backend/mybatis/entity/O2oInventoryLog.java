package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

/**
 * 门店商品库存日志
 */

public class O2oInventoryLog {

	private Long logId; //
	private String operator; // 操作人员ID
	private Integer channelId; // 渠道ID
	private String channelShopId; // 渠道店编码
	private String shopId; // 家乐福门店编码

	private Long itemId; //
	private Long skuId; // 内部SKUID
	private String itemCode; // 家乐福商品编码
	private String o2oItemId; // 外卖商品编码
	private String o2oSkuId; // 外卖skuid

	private Integer p4Stock; // P4可卖数
	private Integer saleQty; // 当天外卖销售数量
	private Integer actualStock; // 可卖数
	private Date createTime; // 创建时间
	private Date processTime; // 发送时间

	private Integer processCount; // 可卖库存
	private Integer processStatus; // 状态0=未发送 99=失败 100=已发送
	private String processMsg; // 错误消息
	private String batchId; // 任务批

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
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

	public Integer getP4Stock() {
		return p4Stock;
	}

	public void setP4Stock(Integer p4Stock) {
		this.p4Stock = p4Stock;
	}

	public Integer getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(Integer saleQty) {
		this.saleQty = saleQty;
	}

	public Integer getActualStock() {
		return actualStock;
	}

	public void setActualStock(Integer actualStock) {
		this.actualStock = actualStock;
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
