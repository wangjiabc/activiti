package com.voucher.manage.model;

import java.util.Date;

public class CampusAdmin {
	private String campusAdmin;
	private String password;
	private Short type;
	private Integer ctiyId;
	private String campusName;
	private Date lastLoginDate;
	public String getCampusAdmin() {
		return campusAdmin;
	}
	public void setCampusAdmin(String campusAdmin) {
		this.campusAdmin = campusAdmin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public Integer getCampusId() {
		return ctiyId;
	}
	public void setCampusId(Integer cityId) {
		this.ctiyId = cityId;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	
}
