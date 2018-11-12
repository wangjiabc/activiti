package com.voucher.manage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeiXin {

	private Integer campusId;

	private String campusName;

	private Integer cityId;
	
	private String customService;
	
	private String homePage;
	
	private String userName; //���ں�ԭʼID
	
	private String appId;
	
	private String appSecret;
	
	private String accessToken;
	
	private String token;
	
	private Date createTime;
	
	private String url;
	
	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName == null ? null : campusName.trim();
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	public Integer getCityId() {
		return cityId;
	}
	
	public void setCustomService(String customService) {
		this.customService=customService;
	}
	
	public String getCustomService() {
		return customService;
	}
	
	public void setAppId(String appId) {
		this.appId=appId;
	}
	
	public String getAppId() {
		return appId;
	}
	
	
	public void setAppSecret(String appSecret) {
		this.appSecret=appSecret;
	}
	
	public String getAppSecret() {
		return appSecret;
	}
	
	
	public void setAccessToken(String accessToken) {
		this.accessToken=accessToken;
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public String getHomePage() {
		return "<a href='"+homePage+"'> http://"+homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreateTime() {
		 SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

	       Long time=new Long(createTime.getTime());
	       String date = format.format(time);
	       
	       return date;
	}

	public Date getOverTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
