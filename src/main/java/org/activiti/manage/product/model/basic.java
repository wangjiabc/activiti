package org.activiti.manage.product.model;

import java.io.Serializable;

public abstract class basic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6165121688276341503L;
	
	// 运行中的流程表示
	private Integer running;
	
	// 已结束任务标识
	private Integer finished;

	//审批中
	private Integer pending;
	
	//待审批
	private Integer waiting;
	
	//审批结果
	private Integer approvalResult;
	
	// 申请人id
	private Integer userId;
	
	// 申请人名称
	private String userName;
	
	// 申请的标题
	private String title;

	// 业务类型
	private String businessType;
	
	//对应业务的id
	private String businessKey;

	public Integer getRunning() {
		return running;
	}

	public void setRunning(Integer running) {
		this.running = running;
	}

	public Integer getFinished() {
		return finished;
	}

	public void setFinished(Integer finished) {
		this.finished = finished;
	}

	public Integer getPending() {
		return pending;
	}

	public void setPending(Integer pending) {
		this.pending = pending;
	}

	public Integer getWaiting() {
		return waiting;
	}

	public void setWaiting(Integer waiting) {
		this.waiting = waiting;
	}

	public Integer getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(Integer approvalResult) {
		this.approvalResult = approvalResult;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
}
