package com.embel.asset.bean;

public class ResponsesingleGatewayTagCountBean 
{
	
	String gatewayName;
	Integer totalTagCount;
	Integer agedTagCount;
	Integer newTagCount;
	public ResponsesingleGatewayTagCountBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponsesingleGatewayTagCountBean(String gatewayName, Integer totalTagCount, Integer agedTagCount,
			Integer newTagCount) {
		super();
		this.gatewayName = gatewayName;
		this.totalTagCount = totalTagCount;
		this.agedTagCount = agedTagCount;
		this.newTagCount = newTagCount;
	}
	public String getGatewayName() {
		return gatewayName;
	}
	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}
	public Integer getTotalTagCount() {
		return totalTagCount;
	}
	public void setTotalTagCount(Integer totalTagCount) {
		this.totalTagCount = totalTagCount;
	}
	public Integer getAgedTagCount() {
		return agedTagCount;
	}
	public void setAgedTagCount(Integer agedTagCount) {
		this.agedTagCount = agedTagCount;
	}
	public Integer getNewTagCount() {
		return newTagCount;
	}
	public void setNewTagCount(Integer newTagCount) {
		this.newTagCount = newTagCount;
	}
	@Override
	public String toString() {
		return "ResponsesingleGatewayTagCountBean [gatewayName=" + gatewayName + ", totalTagCount=" + totalTagCount
				+ ", agedTagCount=" + agedTagCount + ", newTagCount=" + newTagCount + "]";
	}

	
	
}
