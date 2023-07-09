package com.embel.asset.bean;

public class ResponseStayTimeListBean 
{
	String GetwayName;
	String staytime;
	public ResponseStayTimeListBean(String getwayName, String staytime) {
		super();
		GetwayName = getwayName;
		this.staytime = staytime;
	}
	public ResponseStayTimeListBean() {
		// TODO Auto-generated constructor stub
	}
	public String getGetwayName() {
		return GetwayName;
	}
	public void setGetwayName(String getwayName) {
		GetwayName = getwayName;
	}
	public String getStaytime() {
		return staytime;
	}
	public void setStaytime(String staytime) {
		this.staytime = staytime;
	}
	@Override
	public String toString() {
		return "ResponseStayTimeListBean [GetwayName=" + GetwayName + ", staytime=" + staytime + "]";
	}
	
	
	
	

}
