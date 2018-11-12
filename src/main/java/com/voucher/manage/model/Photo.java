package com.voucher.manage.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo {

	private int campusId;
	
	private String openId;
	
	private String imageUrl;
	
	private Date createTime;

	private String nickname;

    private String headImgUrl;
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPhoto() {
	  return "<img src="+imageUrl+" width='80px' height='80px'>";
	}
	
	public String getCreateTime() {
		 SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

	       Long time=new Long(createTime.getTime());
	       String date = format.format(time);
	       
	       return date;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCampusId() {
		return campusId;
	}

	public void setCampusId(int campusId) {
		this.campusId = campusId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImgUrl() {
		return "<img src="+headImgUrl+" width='25px' height='25px'>";
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
}
