package com.voucher.manage.model;

import java.util.Date;

public class MessageList {
    private Integer id;

    private String openId;

    private Integer campusId;

    private String nickname;
    
    private String context;

    private String type;

    private Integer state;

    private Date sendTime;

    private String Charter;

    private String IDNo;
    
    private String hirePhone;
    
    private String name;
    
    private String phone;
    
    private String headImgUrl;
    
    private String chartGuid;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCharter() {
		return Charter;
	}

	public void setCharter(String charter) {
		Charter = charter;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
	public String getImgUrl() {
    	
        return "<img src="+headImgUrl+" width='25px' height='25px'>";
        
    }

    public void setImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }

	public String getChartGuid() {
		return chartGuid;
	}

	public void setChartGuid(String chartGuid) {
        this.chartGuid = chartGuid == null ? null : chartGuid.trim();
    }
}