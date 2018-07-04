package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

/**
 * @author chenp 门店商品
 */

public class O2oGoods {

	private Long itemId; // cs_product-Itemid
	private Long skuId; // 内部编码
	private String internalItemCode; //
	private String internalItemSubcode; //
	private String itemCode; // 家乐福商品编码

	private String o2oItemId; // 外卖商品编码
	private String o2oSkuId; // 外卖skuid
	private Integer channelId; // 渠道
	private String shopId; // 家乐福门店编码
	private String o2oCategoryId; // 外卖类别编码

	private String goodsName; // 商品中文名
	private String goodsEnName; // 商品英文名
	private String barcode; // 条码
	private Integer weight; // 重量
	private Integer p4Inventory; // p4库存数

	private Integer lockInventory; // 锁定库存-不受P4库存影响
	private Integer lockInventoryRefresh; // 锁定库存刷新方式0=手工触发 1=每天 2=每周 3=每月
	private Integer saleQty; // 外卖在途销售数量
	private Integer actualInventory; // 可卖库存
	private Integer safeInventory; // 安全库存

	private Integer sysInventory; // 是否同步库存 1=同步 0=不同步
	private Double marketPrice; // 市场价
	private Double erpPrice; // 业务系统价格
	private Double priceRate; // 售卖价格系数，售价=系数*业务系统价格
	private Double salesPrice; // 销售价=erpPrice * priceRate

	private Double lockPrice; // 用户锁定价格，当此列有值时，以此列价格上传，否则以salesprice上传
	private Double costPrice; // 成本价
	private Integer isEvent; // 是否活动商品 0=否 1=是
	private String vatCode; // 税率
	private Integer shelved; // 上下架状态 1=上架 0=下架

	private Integer status; // 状态 0=未启用（新品） 1=发布 -1=禁用
	private Integer isSelf; // 是否为自定义商品 0：否；1：是
	private Date createTime; // 创建时间
	private Date statusModifyTime; // 状态改变最后时间
	private Date shelvedModifyTime; // 上下架改变最后时间

	private Date priceModifyTime; // 价格改变最后时间
	private Date inventoryModifyTime; // 库存改变最后时间
	private Integer sysPrice; // 是否同步价格 1=同步 0=不同步
	private String categoryId; // 门店商品分类
	private String unit; // 销售单位

	private String spec; // 销售规格
	private Integer pkSpec; // 包装数量

	// 业务需求字段
	private Integer oldStatus;
	private Integer newStatus;
	private Integer key;
	private String channelName;
	private String shopNameAlias;
	private String categoryName;
	private String city;
	private Integer retailFormatId;

	private Integer isSelfZero;


	private String userName;

	public Integer getRetailFormatId() {
		return retailFormatId;
	}

	public void setRetailFormatId(Integer retailFormatId) {
		this.retailFormatId = retailFormatId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getPkSpec() {
		return pkSpec;
	}

	public void setPkSpec(Integer pkSpec) {
		this.pkSpec = pkSpec;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getIsSelfZero() {
		return isSelfZero;
	}

	public void setIsSelfZero(Integer isSelfZero) {
		this.isSelfZero = isSelfZero;
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

	public String getInternalItemCode() {
		return internalItemCode;
	}

	public void setInternalItemCode(String internalItemCode) {
		this.internalItemCode = internalItemCode;
	}

	public String getInternalItemSubcode() {
		return internalItemSubcode;
	}

	public void setInternalItemSubcode(String internalItemSubcode) {
		this.internalItemSubcode = internalItemSubcode;
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
		this.shopId = shopId;
	}

	public String getO2oCategoryId() {
		return o2oCategoryId;
	}

	public void setO2oCategoryId(String o2oCategoryId) {
		this.o2oCategoryId = o2oCategoryId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsEnName() {
		return goodsEnName;
	}

	public void setGoodsEnName(String goodsEnName) {
		this.goodsEnName = goodsEnName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getP4Inventory() {
		return p4Inventory;
	}

	public void setP4Inventory(Integer p4Inventory) {
		this.p4Inventory = p4Inventory;
	}

	public Integer getLockInventory() {
		return lockInventory;
	}

	public void setLockInventory(Integer lockInventory) {
		this.lockInventory = lockInventory;
	}

	public Integer getLockInventoryRefresh() {
		return lockInventoryRefresh;
	}

	public void setLockInventoryRefresh(Integer lockInventoryRefresh) {
		this.lockInventoryRefresh = lockInventoryRefresh;
	}

	public Integer getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(Integer saleQty) {
		this.saleQty = saleQty;
	}

	public Integer getActualInventory() {
		return actualInventory;
	}

	public void setActualInventory(Integer actualInventory) {
		this.actualInventory = actualInventory;
	}

	public Integer getSafeInventory() {
		return safeInventory;
	}

	public void setSafeInventory(Integer safeInventory) {
		this.safeInventory = safeInventory;
	}

	public Integer getSysInventory() {
		return sysInventory;
	}

	public void setSysInventory(Integer sysInventory) {
		this.sysInventory = sysInventory;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getErpPrice() {
		return erpPrice;
	}

	public void setErpPrice(Double erpPrice) {
		this.erpPrice = erpPrice;
	}

	public Double getPriceRate() {
		return priceRate;
	}

	public void setPriceRate(Double priceRate) {
		this.priceRate = priceRate;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Double getLockPrice() {
		return lockPrice;
	}

	public void setLockPrice(Double lockPrice) {
		this.lockPrice = lockPrice;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Integer getIsEvent() {
		return isEvent;
	}

	public void setIsEvent(Integer isEvent) {
		this.isEvent = isEvent;
	}

	public String getVatCode() {
		return vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}

	public Integer getShelved() {
		return shelved;
	}

	public void setShelved(Integer shelved) {
		this.shelved = shelved;
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

	public Date getStatusModifyTime() {
		return statusModifyTime;
	}

	public void setStatusModifyTime(Date statusModifyTime) {
		this.statusModifyTime = statusModifyTime;
	}

	public Date getShelvedModifyTime() {
		return shelvedModifyTime;
	}

	public void setShelvedModifyTime(Date shelvedModifyTime) {
		this.shelvedModifyTime = shelvedModifyTime;
	}

	public Date getPriceModifyTime() {
		return priceModifyTime;
	}

	public void setPriceModifyTime(Date priceModifyTime) {
		this.priceModifyTime = priceModifyTime;
	}

	public Date getInventoryModifyTime() {
		return inventoryModifyTime;
	}

	public void setInventoryModifyTime(Date inventoryModifyTime) {
		this.inventoryModifyTime = inventoryModifyTime;
	}

	public Integer getSysPrice() {
		return sysPrice;
	}

	public void setSysPrice(Integer sysPrice) {
		this.sysPrice = sysPrice;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Integer getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Integer newStatus) {
		this.newStatus = newStatus;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getShopNameAlias() {
		return shopNameAlias;
	}

	public void setShopNameAlias(String shopNameAlias) {
		this.shopNameAlias = shopNameAlias;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(Integer isSelf) {
		this.isSelf = isSelf;
	}

}
