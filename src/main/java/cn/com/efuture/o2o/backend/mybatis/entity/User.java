package cn.com.efuture.o2o.backend.mybatis.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 2017-01-17.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3035303850009432731L;

    private int userId;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String regist_time;
    private String last_act_ip;
    private String last_act_time;
    private String status;
    private String authorities;

    //业务字段
    private List cityList;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List getCityList() {
        return cityList;
    }

    public void setCityList(List cityList) {
        this.cityList = cityList;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(String regist_time) {
        this.regist_time = regist_time;
    }

    public String getLast_act_ip() {
        return last_act_ip;
    }

    public void setLast_act_ip(String last_act_ip) {
        this.last_act_ip = last_act_ip;
    }

    public String getLast_act_time() {
        return last_act_time;
    }

    public void setLast_act_time(String last_act_time) {
        this.last_act_time = last_act_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
