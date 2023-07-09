package com.embel.asset.bean;

public class GatewayAndTagCountbean 
{
	private String tagCountString;
	private String gatewayCount;
	public GatewayAndTagCountbean(String tagCountString, String gatewayCount) {
		super();
		this.tagCountString = tagCountString;
		this.gatewayCount = gatewayCount;
	}
	public GatewayAndTagCountbean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTagCountString() {
		return tagCountString;
	}
	public void setTagCountString(String tagCountString) {
		this.tagCountString = tagCountString;
	}
	public String getGatewayCount() {
		return gatewayCount;
	}
	public void setGatewayCount(String gatewayCount) {
		this.gatewayCount = gatewayCount;
	}
	@Override
	public String toString() {
		return "GatewayAndTagCount [tagCountString=" + tagCountString + ", gatewayCount=" + gatewayCount + "]";
	}
	

}
