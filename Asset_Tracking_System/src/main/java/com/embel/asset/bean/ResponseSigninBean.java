package com.embel.asset.bean;

import java.util.ArrayList;
import java.util.List;

import com.embel.asset.dto.RequestPermissionDto;
import com.embel.asset.entity.Category;

public class ResponseSigninBean {
	
	private String token;
	private String refreshToken; 
	List<RequestPermissionDto> perList = new ArrayList<RequestPermissionDto>();
	List<Category> category= new ArrayList<Category>();
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public List<RequestPermissionDto> getPerList() {
		return perList;
	}
	public void setPerList(List<RequestPermissionDto> perList) {
		this.perList = perList;
	}
	public List<Category> getCategory() {
		return category;
	}
	public void setCategory(List<Category> category) {
		this.category = category;
	}
	public ResponseSigninBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseSigninBean(String token, String refreshToken, List<RequestPermissionDto> perList,
			List<Category> category) {
		super();
		this.token = token;
		this.refreshToken = refreshToken;
		this.perList = perList;
		this.category = category;
	}
	public ResponseSigninBean(String string, String string2) {
		this.token = string;
		this.refreshToken = string2;
	}
	@Override
	public String toString() {
		return "ResponseSigninBean [token=" + token + ", refreshToken=" + refreshToken + ", perList=" + perList
				+ ", category=" + category + "]";
	}
	
	
}

