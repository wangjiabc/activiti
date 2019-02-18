package com.rmi.server.entity;

import java.io.Serializable;
import java.util.List;

public class FlowData implements Serializable{
		
	/**
	* 
	*/
		private static final long serialVersionUID = 1L;

		private String Template_Id;
		
		private String Send_Type;
		
		private String url;
		
		private String first_data;
		
		private String keyword1_data;
		
		private String keyword2_data;
		
		private String keyword3_data;
		
		private String keyword4_data;
		
		private String keyword5_data;
		
		private String remark_data;

		private List<Deliveran> deliverans;
		
		private List<ImageData> imageDataList;
		
		public String getFirst_data() {
			return first_data;
		}

		public void setFirst_data(String first_data) {
			this.first_data = first_data;
		}

		public String getKeyword1_data() {
			return keyword1_data;
		}

		public void setKeyword1_data(String keyword1_data) {
			this.keyword1_data = keyword1_data;
		}

		public String getKeyword2_data() {
			return keyword2_data;
		}

		public void setKeyword2_data(String keyword2_data) {
			this.keyword2_data = keyword2_data;
		}

		public String getKeyword3_data() {
			return keyword3_data;
		}

		public void setKeyword3_data(String keyword3_data) {
			this.keyword3_data = keyword3_data;
		}

		public String getKeyword4_data() {
			return keyword4_data;
		}

		public void setKeyword4_data(String keyword4_data) {
			this.keyword4_data = keyword4_data;
		}

		public String getKeyword5_data() {
			return keyword5_data;
		}

		public void setKeyword5_data(String keyword5_data) {
			this.keyword5_data = keyword5_data;
		}

		public String getRemark_data() {
			return remark_data;
		}

		public void setRemark_data(String remark_data) {
			this.remark_data = remark_data;
		}

		public String getTemplate_Id() {
			return Template_Id;
		}

		public void setTemplate_Id(String template_Id) {
			Template_Id = template_Id;
		}

		public String getSend_Type() {
			return Send_Type;
		}

		public void setSend_Type(String send_Type) {
			Send_Type = send_Type;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public List<Deliveran> getDeliverans() {
			return deliverans;
		}

		public void setDeliverans(List<Deliveran> deliverans) {
			this.deliverans = deliverans;
		}

		public List<ImageData> getImageDataList() {
			return imageDataList;
		}

		public void setImageDataList(List<ImageData> imageDataList) {
			this.imageDataList = imageDataList;
		}
	
}
