package com.rmi.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity   
@Table(name = "roominfo_flowid")
public class RoomInfoFlowIdEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_")
	private Integer id; 
	
	@Column(name = "GUID_")
    private String guid;  
	
	@Column(name = "ProcessInstanceId_")
    private String processInstanceId;
    
	@Column(name = "Open_id_")
	private String openId;
	
	@Column(name = "CurrentOpen_id_")
	private String currentOpenId;
	
	@Column(name = "Type_")
	private String type;
	
	@Column(name = "ApplicationUser_")
	private String applicationUser;
	
	@Column(name = "Result_")
	private Integer result;
	
	@Column(name = "Address_")
	private String address;
	
	@Column(name = "Update_time_")
	private Date update_time;

	@Column(name = "Date_")
	private Date date;
	
	@Column(name = "State_")
	private Integer state;
	
    public Integer getId() {  
        return id;  
    }

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(String applicationUser) {
		this.applicationUser = applicationUser;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCurrentOpenId() {
		return currentOpenId;
	}

	public void setCurrentOpenId(String currentOpenId) {
		this.currentOpenId = currentOpenId;
	}
}
