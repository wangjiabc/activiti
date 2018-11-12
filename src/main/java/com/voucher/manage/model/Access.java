package com.voucher.manage.model;

import java.util.Date;

public class Access {
    private Integer id;

    private String openId;

    private Integer campusId;

    private String page;
    
    private String guid;

    private Date accessTime;
    
    private float allConsume;
	
	private short subScribe;
	
	private String language;
	
	private String groupId;
	
	private String city;
	
	private String country;
	
	private String remark;
	
	private Float totalAmount;
	
	private String province;

    private String nickname;

    private String headImgUrl;

    private Date subscribeTime;
        
    private short sex;
    
    private String name;
    
    private Integer place;
    
    private String phone;
    
    private String headship;
    
    private String email;
    
    private String address;
    
    private String rank;
    
    private Date upTime;

    private String Charter;

    private String IDNo;
    
    private String hirePhone;
    

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page == null ? null : page.trim();
    }
    
    public float getAllConsume() {
		return allConsume;
	}

	public void setAllConsume(float allConsume) {
		this.allConsume = allConsume;
	}
    
    public Integer getId() {
		return id;
	}
    
    public void setId(Integer id) {
		this.id=id;
	}

	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }
    
    public String getImgUrl() {
    	
        return "<img src="+headImgUrl+" width='25px' height='25px'>";
        
    }

    public void setImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public short getSubScribe() {
		return subScribe;
	}

	public void setSubScribe(short subScribe) {
		this.subScribe = subScribe;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public String getHeadship() {
		return headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCharter() {
		return Charter;
	}

	public void setCharter(String Charter) {
		this.Charter = Charter;
	}

	public String getIDNo() {
		return IDNo;
	}

	public void setIDNo(String iDNo) {
		IDNo = iDNo;
	}

	public String getHirePhone() {
		return hirePhone;
	}

	public void setHirePhone(String hirePhone) {
		this.hirePhone = hirePhone;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}