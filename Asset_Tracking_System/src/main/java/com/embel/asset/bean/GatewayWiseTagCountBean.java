package com.embel.asset.bean;

public class GatewayWiseTagCountBean {

	private String gatewayName;
	private Integer tagCount;
	private String gatewayLocation;
	
	positions positions=new positions();
	public GatewayWiseTagCountBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GatewayWiseTagCountBean(String gatewayName, Integer tagCount, String gatewayLocation,
			com.embel.asset.bean.positions positions) {
		super();
		this.gatewayName = gatewayName;
		this.tagCount = tagCount;
		this.gatewayLocation = gatewayLocation;
		this.positions = positions;
	}
	public String getGatewayName() {
		return gatewayName;
	}
	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}
	public Integer getTagCount() {
		return tagCount;
	}
	public void setTagCount(Integer tagCount) {
		this.tagCount = tagCount;
	}
	public String getGatewayLocation() {
		return gatewayLocation;
	}
	public void setGatewayLocation(String gatewayLocation) {
		this.gatewayLocation = gatewayLocation;
	}
	public positions getPositions() {
		return positions;
	}
	public void setPositions(positions positions) {
		this.positions = positions;
	}
	@Override
	public String toString() {
		return "GatewayWiseTagCountBean [gatewayName=" + gatewayName + ", tagCount=" + tagCount + ", gatewayLocation="
				+ gatewayLocation + ", positions=" + positions + "]";
	}
	


}
