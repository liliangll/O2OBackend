package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

public class ChannelCategory {
	
	/** 本地类别编码  **/
    private String categoryId;
    
    /** 渠道  **/
	private Integer channelId;
	
	/** 渠道店铺编码  **/	
	private String channelShopId;
	
	/** 外卖平台类别编码  **/
	private String o2oCategoryId;
	
	/** 外卖平台上级类别编码  **/
	private String parentO2OCategoryId;
	
	/** 是否需要同步到外卖平台 0=待同步 1=已同步  **/
	private Integer isSys;
	
	/** 创建时间  **/
	private Date lastModifyTime;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

	public String getO2oCategoryId() {
		return o2oCategoryId;
	}

	public void setO2oCategoryId(String o2oCategoryId) {
		this.o2oCategoryId = o2oCategoryId;
	}

	public String getParentO2OCategoryId() {
		return parentO2OCategoryId;
	}

	public void setParentO2OCategoryId(String parentO2OCategoryId) {
		this.parentO2OCategoryId = parentO2OCategoryId;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
 
	
}
