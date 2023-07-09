package com.embel.asset.bean;

import java.util.List;

public class ResponseColumnBean 
{
	private String AdminName;
	private List<ResponseColumnMappingbean> list;

	public ResponseColumnBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseColumnBean(String adminName) {
		super();
		AdminName = adminName;
	}
	@Override
	public String toString() {
		return "ResponseColumnBean [AdminName=" + AdminName + "]";
	}
	public String getAdminName() {
		return AdminName;
	}
	public void setAdminName(String adminName) {
		AdminName = adminName;
	}
	public List<ResponseColumnMappingbean> getList() {
		return list;
	}
	public void setList(List<ResponseColumnMappingbean> list) {
		this.list = list;
	}
	public ResponseColumnMappingbean setList(ResponseColumnMappingbean responseColumnMappingbean) {
		return responseColumnMappingbean;
		
	}
	
	

	

}
