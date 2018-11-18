package org.activiti.manage.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


//@Table(name="T_VACATION")
//@Entity
//在Hibernate中可以利用@DynamicInsert和@DynamicUpdate生成动态SQL语句，
//即在插入和修改数据的时候,语句中只包括要插入或者修改的字段。
//@DynamicUpdate(true)
//@DynamicInsert(true)
public class Vacation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//标注id
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "ID", unique = true)
	private String uid;
	//@Basic
	//@Column(name="uname")
	private String uname;
	//@Basic
	private int age;
	
	//@Transient
	private String remark;//备注
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Users [uid=" + uid + ", uname=" + uname + ", age=" + age + "]";
	}
	public Vacation(String uid, String uname, int age) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.age = age;
	}
	public Vacation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
