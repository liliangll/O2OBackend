package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

public class O2oCategory {
	
	/** 本地类别编码  **/
	private String categoryId;
	
	/** 类别名称  **/
	private String categoryName;
	
	/** 上级类别编码  **/
	private String parentCategoryId;
	
	/** 类别级别，0为最高级，最大到2  **/
	private Integer level;
	
	/** 排序级别，0为最高优先级  **/
	private Integer seqNo;
	
	/** 状态：1启用 0 禁用（标记删除）  **/
	private Integer flag;
	
	/** 最后修改时间  **/
	private Date lastModifyTime;
	
	/** 备注  **/
	private String memo;
	
	/** 业态编码  **/
	private int retailFormatId;

	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getRetailFormatId() {
		return retailFormatId;
	}

	public void setRetailFormatId(int retailFormatId) {
		this.retailFormatId = retailFormatId;
	}

	
	
}
