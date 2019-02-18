package com.rmi.server.entity;

import java.io.Serializable;
import java.util.Date;

public class ImageData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String Type;
	
	private String URI;
	
	private String FileBelong;
	
	private Integer FileIndex;
	
	private Date date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public String getFileBelong() {
		return FileBelong;
	}

	public void setFileBelong(String fileBelong) {
		FileBelong = fileBelong;
	}

	public Integer getFileIndex() {
		return FileIndex;
	}

	public void setFileIndex(Integer fileIndex) {
		FileIndex = fileIndex;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
