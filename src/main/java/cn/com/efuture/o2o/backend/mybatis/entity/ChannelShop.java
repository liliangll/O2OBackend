package cn.com.efuture.o2o.backend.mybatis.entity;

import java.util.Date;

public class ChannelShop {
    private Integer channelId;

    private String channelShopId;

    private String channelShopName;

    private String shopId;

    private Integer status;

    private Double stockRate;

    private Integer preOrder;

    private Integer isOpen;

    private String openTime;

    private String address;

    private String contactTel;

    private String serviceTel;

    private String notice;

    private Date lastModifyTime;

    private Date syncGoodsTime;

    private String syncGoodsNote;

    //业务字段
    private Integer key;
    private String channelName;
    private String regionId;
    private String regionName;
    private String city;
    private String ShopName;
    private String ShopNameAlias;

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getShopNameAlias() {
        return ShopNameAlias;
    }

    public void setShopNameAlias(String shopNameAlias) {
        ShopNameAlias = shopNameAlias;
    }

    public Integer getPreOrder() {
        return preOrder;
    }

    public void setPreOrder(Integer preOrder) {
        this.preOrder = preOrder;
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

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        this.channelShopId = channelShopId == null ? null : channelShopId.trim();
    }

    public String getChannelShopName() {
        return channelShopName;
    }

    public void setChannelShopName(String channelShopName) {
        this.channelShopName = channelShopName == null ? null : channelShopName.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getStockRate() {
        return stockRate;
    }

    public void setStockRate(Double stockRate) {
        this.stockRate = stockRate;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel == null ? null : contactTel.trim();
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel == null ? null : serviceTel.trim();
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Date getSyncGoodsTime() {
        return syncGoodsTime;
    }

    public void setSyncGoodsTime(Date syncGoodsTime) {
        this.syncGoodsTime = syncGoodsTime;
    }

    public String getSyncGoodsNote() {
        return syncGoodsNote;
    }

    public void setSyncGoodsNote(String syncGoodsNote) {
        this.syncGoodsNote = syncGoodsNote == null ? null : syncGoodsNote.trim();
    }
}