package cn.com.efuture.o2o.backend.mybatis.entity;

import java.io.Serializable;

public class UserShop implements Serializable {

    private Integer userId;

    private String userName;

    private String shopId;

    //业务字段返回
    private String shopName;
    private String shopNameAlias;
    private String city;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNameAlias() {
        return shopNameAlias;
    }

    public void setShopNameAlias(String shopNameAlias) {
        this.shopNameAlias = shopNameAlias;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
