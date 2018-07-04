package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

public class O2oGoodsFilterSheetHead {
	
	private String sheetId;
	
	private Integer flag;
	
	private String note;
	
	private String auditNote;
	
	private Date createTime;
	
	private String creator;
	
	private Date auditTime;

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	} 
	
	
}
