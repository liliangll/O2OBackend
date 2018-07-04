package cn.com.efuture.o2o.backend.mybatis.entity;

import java.io.Serializable;

public class Role implements Serializable {
	private int roleId;
	private String roleName;
	private int enable;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	
}
