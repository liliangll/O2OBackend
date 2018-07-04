package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

public class O2oGoodsFilter {
	/** 家乐福门店编码  **/
	private String shopId;
	
	/** 家乐福商品编码  **/
	private String itemCode;
	
	/** 城市  **/
	private String city;
	
	/** 提报商品名称  **/
	private String goodsName;
	
	/** p4商品名称  **/
	private String name;

	/** 状态 **/
	private Integer status;
	
	/** 创建时间  **/
	private Date createTime;
	
	/** 单据编号  **/
	private String sheetId;
	

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}
}
