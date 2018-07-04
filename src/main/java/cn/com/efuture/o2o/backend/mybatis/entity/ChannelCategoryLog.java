package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

public class ChannelCategoryLog {
	
	/** 自增长序列  **/
	private Long logId; 
	
	/** 操作人员ID  **/
	private String operator;
	
	/** 渠道  **/
	private Integer channelId; 
	
	/** 对应家乐福门店编码(百度需要)  **/
	private String shopId;
	
	/** 渠道店铺编码  **/
	private String channelShopId;
	
	/** 任务类型1=新增 2=修改 3=禁用  **/
	private Integer taskType;
	
	/** 类别编码  **/
	private String categoryId; 
	
	/** 类别名称  **/
	private String categoryName; 

	/** 任务创建时间  **/
	private Date createTime;
	
	/** 最近处理时间  **/
	private Date processTime;
	
	/** 处理次数  **/
	private Integer processCount;
	
	/** 0待处理 100完成 99异常  **/
	private Integer processStatus; 
	
	/** 状态消息  **/
	private String processMsg;

	
	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
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

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
}
