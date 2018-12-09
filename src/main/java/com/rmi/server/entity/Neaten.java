package com.rmi.server.entity;

import java.io.Serializable;
import java.util.Date;

public class Neaten implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private String GUID;

	private Integer exist;

	private String neaten_id;

	private String neaten_name;

	private String principal;

	private Date happen_time;

	private String check_circs;
    
	private String neaten_item;
    
	private String neaten_instance;

	private Date update_time;

	private Date date;

	private String remark;

	private String campusAdmin;

	private String UserName;

	private String terminal;

	private String progress;

	private Integer is_repair;
    
	private String RoomGUID;
    
	private Float Area;

	private String Type;

	private Float AmountTotal;

	private Float Amount;

	private Float AuditingAmount;

	private String WorkUnit;

	private String AvailabeLength;

	private String address;
	
	private String addComp;
	
	private Double lng;
	
	private Double lat;
	
	private String checkItemDate;
	
	private String applicationUser;
	
	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setGUID(String GUID){
		this.GUID = GUID;
	}

	public String getGUID(){
		return GUID;
	}

	public void setExist(Integer exist){
		this.exist = exist;
	}

	public Integer getExist(){
		return exist;
	}

	public void setIs_repair(Integer is_repair){
		this.is_repair = is_repair;
	}

	public Integer getIs_repair(){
		return is_repair;
	}
	
	public void setNeaten_id(String neaten_id){
		this.neaten_id = neaten_id;
	}

	public String getNeaten_id(){
		return neaten_id;
	}

	public void setNeaten_name(String neaten_name){
		this.neaten_name = neaten_name;
	}

	public String getNeaten_name(){
		return neaten_name;
	}

	public void setPrincipal(String principal){
		this.principal = principal;
	}

	public String getPrincipal(){
		return principal;
	}

	public void setHappen_time(Date happen_time){
		this.happen_time = happen_time;
	}

	public Date getHappen_time(){
		return happen_time;
	}

	public void setCheck_circs(String check_circs){
		this.check_circs = check_circs;
	}

	public String getCheck_circs(){
		return check_circs;
	}
	
	public void setNeaten_item(String neaten_item){
		this.neaten_item = neaten_item;
	}

	public String getNeaten_item(){
		return neaten_item;
	}
	
	public void setNeaten_instance(String neaten_instance){
		this.neaten_instance = neaten_instance;
	}

	public String getNeaten_instance(){
		return neaten_instance;
	}

	public void setUpdate_time(Date update_time){
		this.update_time = update_time;
	}

	public Date getUpdate_time(){
		return update_time;
	}

	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return date;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setCampusAdmin(String campusAdmin){
		this.campusAdmin = campusAdmin;
	}

	public String getCampusAdmin(){
		return campusAdmin;
	}

	public void setUserName(String UserName){
		this.UserName = UserName;
	}

	public String getUserName(){
		return UserName;
	}

	public void setTerminal(String terminal){
		this.terminal = terminal;
	}

	public String getTerminal(){
		return terminal;
	}

	public void setProgress(String progress){
		this.progress = progress;
	}

	public String getProgress(){
		return progress;
	}

	public void setRoomGUID(String RoomGUID){
		this.RoomGUID = RoomGUID;
	}

	public String getRoomGUID(){
		return RoomGUID;
	}
	
	public void setArea(Float Area){
		this.Area = Area;
	}

	public Float getArea(){
		return Area;
	}

	public void setType(String Type){
		this.Type = Type;
	}

	public String getType(){
		return Type;
	}

	public void setAmountTotal(Float AmountTotal){
		this.AmountTotal = AmountTotal;
	}

	public Float getAmountTotal(){
		return AmountTotal;
	}

	public void setAmount(Float Amount){
		this.Amount = Amount;
	}

	public Float getAmount(){
		return Amount;
	}

	public void setAuditingAmount(Float AuditingAmount){
		this.AuditingAmount = AuditingAmount;
	}

	public Float getAuditingAmount(){
		return AuditingAmount;
	}

	public void setWorkUnit(String WorkUnit){
		this.WorkUnit = WorkUnit;
	}

	public String getWorkUnit(){
		return WorkUnit;
	}

	public void setAvailabeLength(String AvailabeLength){
		this.AvailabeLength = AvailabeLength;
	}

	public String getAvailabeLength(){
		return AvailabeLength;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddComp() {
		return addComp;
	}

	public void setAddComp(String addComp) {
		this.addComp = addComp;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getCheckItemDate() {
		return checkItemDate;
	}

	public void setCheckItemDate(String checkItemDate) {
		this.checkItemDate = checkItemDate;
	}

	public String getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(String applicationUser) {
		this.applicationUser = applicationUser;
	}


}

