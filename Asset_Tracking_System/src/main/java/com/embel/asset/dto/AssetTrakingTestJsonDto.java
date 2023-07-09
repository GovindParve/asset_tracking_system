package com.embel.asset.dto;

public class AssetTrakingTestJsonDto 
{
	private String gSrNo;
	private String gMacId;
	private String tName;
	private String tMacId;
	private String bSN;
	private String batStatus;
	public String getgSrNo() {
		return gSrNo;
	}
	public void setgSrNo(String gSrNo) {
		this.gSrNo = gSrNo;
	}
	public String getgMacId() {
		return gMacId;
	}
	public void setgMacId(String gMacId) {
		this.gMacId = gMacId;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String gettMacId() {
		return tMacId;
	}
	public void settMacId(String tMacId) {
		this.tMacId = tMacId;
	}
	public String getbSN() {
		return bSN;
	}
	public void setbSN(String bSN) {
		this.bSN = bSN;
	}
	public String getBatStatus() {
		return batStatus;
	}
	public void setBatStatus(String batStatus) {
		this.batStatus = batStatus;
	}
	@Override
	public String toString() {
		return "AssetTrakingTestJsonDto [gSrNo=" + gSrNo + ", gMacId=" + gMacId + ", tName=" + tName + ", tMacId="
				+ tMacId + ", bSN=" + bSN + ", batStatus=" + batStatus + "]";
	}
	public AssetTrakingTestJsonDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AssetTrakingTestJsonDto(String gSrNo, String gMacId, String tName, String tMacId, String bSN,
			String batStatus) {
		super();
		this.gSrNo = gSrNo;
		this.gMacId = gMacId;
		this.tName = tName;
		this.tMacId = tMacId;
		this.bSN = bSN;
		this.batStatus = batStatus;
	}
	
	
	
	
	
	
	
	
}
