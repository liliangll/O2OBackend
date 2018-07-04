package cn.com.efuture.o2o.backend.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

public class Shop implements Serializable {
    private String shopId;

    private String shopName;

    private Integer shopType;

    private String regionId;

    private String posIp;

    private String tpUserName;

    private String tpPassWord;

    private String shopNameAlias;

    private Integer pickupLongTime;

    private Integer sendLongTime;

    private String shopPrintUrl;

    private Date stockLastTime;

    private Integer sendLongTimeBBT;

    private String nodal_point;

    private String phone;

    private String region;

    private String address;

    private String service_tel;

    private String logistics_name;

    private String home_url;

    private Short isoverseas;

    private String city;

    private String province;

    private String zip;

    private Integer onlyO2O;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getPosIp() {
        return posIp;
    }

    public void setPosIp(String posIp) {
        this.posIp = posIp == null ? null : posIp.trim();
    }

    public String getTpUserName() {
        return tpUserName;
    }

    public void setTpUserName(String tpUserName) {
        this.tpUserName = tpUserName == null ? null : tpUserName.trim();
    }

    public String getTpPassWord() {
        return tpPassWord;
    }

    public void setTpPassWord(String tpPassWord) {
        this.tpPassWord = tpPassWord == null ? null : tpPassWord.trim();
    }

    public String getShopNameAlias() {
        return shopNameAlias;
    }

    public void setShopNameAlias(String shopNameAlias) {
        this.shopNameAlias = shopNameAlias == null ? null : shopNameAlias.trim();
    }

    public Integer getPickupLongTime() {
        return pickupLongTime;
    }

    public void setPickupLongTime(Integer pickupLongTime) {
        this.pickupLongTime = pickupLongTime;
    }

    public Integer getSendLongTime() {
        return sendLongTime;
    }

    public void setSendLongTime(Integer sendLongTime) {
        this.sendLongTime = sendLongTime;
    }

    public String getShopPrintUrl() {
        return shopPrintUrl;
    }

    public void setShopPrintUrl(String shopPrintUrl) {
        this.shopPrintUrl = shopPrintUrl == null ? null : shopPrintUrl.trim();
    }

    public Date getStockLastTime() {
        return stockLastTime;
    }

    public void setStockLastTime(Date stockLastTime) {
        this.stockLastTime = stockLastTime;
    }

    public Integer getSendLongTimeBBT() {
        return sendLongTimeBBT;
    }

    public void setSendLongTimeBBT(Integer sendLongTimeBBT) {
        this.sendLongTimeBBT = sendLongTimeBBT;
    }

    public String getNodal_point() {
        return nodal_point;
    }

    public void setNodal_point(String nodal_point) {
        this.nodal_point = nodal_point == null ? null : nodal_point.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getService_tel() {
        return service_tel;
    }

    public void setService_tel(String service_tel) {
        this.service_tel = service_tel == null ? null : service_tel.trim();
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name == null ? null : logistics_name.trim();
    }

    public String getHome_url() {
        return home_url;
    }

    public void setHome_url(String home_url) {
        this.home_url = home_url == null ? null : home_url.trim();
    }

    public Short getIsoverseas() {
        return isoverseas;
    }

    public void setIsoverseas(Short isoverseas) {
        this.isoverseas = isoverseas;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip == null ? null : zip.trim();
    }

    public Integer getOnlyO2O() {
        return onlyO2O;
    }

    public void setOnlyO2O(Integer onlyO2O) {
        this.onlyO2O = onlyO2O;
    }
}