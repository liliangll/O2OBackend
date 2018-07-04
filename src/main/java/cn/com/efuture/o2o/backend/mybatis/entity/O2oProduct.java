package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

public class O2oProduct {
    private Integer retailFormatId;

    private Long itemId;

    private String internalItemCode;

    private String itemCode;

    private Integer status;

    private String goodsName;

    private String goodsEnName;

    private String categoryId;

    private String erpCategoryId;

    private String barcode;

    private String unit;

    private String spec;

    private Integer pkSpec;

    private Integer weight;

    private Double height;

    private Double width;

    private Double length;

    private Integer itemType;

    private Integer sqeNo;

    private String imageKey1;

    private String imageKey2;

    private String memo;

    private Date createTime;

    //业务需求字段
    private Integer oldStatus;
    private Integer newStatus;

    private String imageUrl1;
    private String imageUrl2;

    private String categoryName;
    private String categoryNames;
    private String parentCategoryId;

    private String p4CategoryNames; //业务类别名称，p4类别

    private String retailFormatName;

    public String getP4CategoryNames() {
        return p4CategoryNames;
    }

    public String getErpCategoryId() {
        return erpCategoryId;
    }

    public void setErpCategoryId(String erpCategoryId) {
        this.erpCategoryId = erpCategoryId;
    }

    public void setP4CategoryNames(String p4CategoryNames) {
        this.p4CategoryNames = p4CategoryNames;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String categoryNames) {
        this.categoryNames = categoryNames;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
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

    public Integer getRetailFormatId() {
        return retailFormatId;
    }

    public void setRetailFormatId(Integer retailFormatId) {
        this.retailFormatId = retailFormatId;
    }

    public String getRetailFormatName() {
        return retailFormatName;
    }

    public void setRetailFormatName(String retailFormatName) {
        this.retailFormatName = retailFormatName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getInternalItemCode() {
        return internalItemCode;
    }

    public void setInternalItemCode(String internalItemCode) {
        this.internalItemCode = internalItemCode;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public Integer getPkSpec() {
        return pkSpec;
    }

    public void setPkSpec(Integer pkSpec) {
        this.pkSpec = pkSpec;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getSqeNo() {
        return sqeNo;
    }

    public void setSqeNo(Integer sqeNo) {
        this.sqeNo = sqeNo;
    }

    public String getImageKey1() {
        return imageKey1;
    }

    public void setImageKey1(String imageKey1) {
        this.imageKey1 = imageKey1;
    }

    public String getImageKey2() {
        return imageKey2;
    }

    public void setImageKey2(String imageKey2) {
        this.imageKey2 = imageKey2;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}