package com.embel.asset.bean;

import java.util.Date;



public class ResponseworkingNonworkinGatewaybean 
{
	
	private Long gatewayId;
	
	private String gatewayName;

	private String gatewayUniqueCodeMacId;

	private String gatewayBarcodeSerialNumber;

	private String gatewayLocation;

	private String assetTagCategory;
	
	private String user;

	private String admin;

	private Long fkUserId;
	
	private Long fkAdminId;

	private String wakeupTime;

	private String timeZone;
	
	private Date datetime;

	private Long assetGetwayEnable;
	
	private String WorkingorNonworking;

	public ResponseworkingNonworkinGatewaybean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseworkingNonworkinGatewaybean(Long gatewayId, String gatewayName, String gatewayUniqueCodeMacId,
			String gatewayBarcodeSerialNumber, String gatewayLocation, String assetTagCategory, String user,
			String admin, Long fkUserId, Long fkAdminId, String wakeupTime, String timeZone, Date datetime,
			Long assetGetwayEnable, String workingorNonworking) {
		super();
		this.gatewayId = gatewayId;
		this.gatewayName = gatewayName;
		this.gatewayUniqueCodeMacId = gatewayUniqueCodeMacId;
		this.gatewayBarcodeSerialNumber = gatewayBarcodeSerialNumber;
		this.gatewayLocation = gatewayLocation;
		this.assetTagCategory = assetTagCategory;
		this.user = user;
		this.admin = admin;
		this.fkUserId = fkUserId;
		this.fkAdminId = fkAdminId;
		this.wakeupTime = wakeupTime;
		this.timeZone = timeZone;
		this.datetime = datetime;
		this.assetGetwayEnable = assetGetwayEnable;
		WorkingorNonworking = workingorNonworking;
	}

	public Long getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Long gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getGatewayUniqueCodeMacId() {
		return gatewayUniqueCodeMacId;
	}

	public void setGatewayUniqueCodeMacId(String gatewayUniqueCodeMacId) {
		this.gatewayUniqueCodeMacId = gatewayUniqueCodeMacId;
	}

	public String getGatewayBarcodeSerialNumber() {
		return gatewayBarcodeSerialNumber;
	}

	public void setGatewayBarcodeSerialNumber(String gatewayBarcodeSerialNumber) {
		this.gatewayBarcodeSerialNumber = gatewayBarcodeSerialNumber;
	}

	public String getGatewayLocation() {
		return gatewayLocation;
	}

	public void setGatewayLocation(String gatewayLocation) {
		this.gatewayLocation = gatewayLocation;
	}

	public String getAssetTagCategory() {
		return assetTagCategory;
	}

	public void setAssetTagCategory(String assetTagCategory) {
		this.assetTagCategory = assetTagCategory;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public Long getFkUserId() {
		return fkUserId;
	}

	public void setFkUserId(Long fkUserId) {
		this.fkUserId = fkUserId;
	}

	public Long getFkAdminId() {
		return fkAdminId;
	}

	public void setFkAdminId(Long fkAdminId) {
		this.fkAdminId = fkAdminId;
	}

	public String getWakeupTime() {
		return wakeupTime;
	}

	public void setWakeupTime(String wakeupTime) {
		this.wakeupTime = wakeupTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Long getAssetGetwayEnable() {
		return assetGetwayEnable;
	}

	public void setAssetGetwayEnable(Long assetGetwayEnable) {
		this.assetGetwayEnable = assetGetwayEnable;
	}

	public String getWorkingorNonworking() {
		return WorkingorNonworking;
	}

	public void setWorkingorNonworking(String workingorNonworking) {
		WorkingorNonworking = workingorNonworking;
	}

	@Override
	public String toString() {
		return "ResponseworkingNonworkinGatewaybean [gatewayId=" + gatewayId + ", gatewayName=" + gatewayName
				+ ", gatewayUniqueCodeMacId=" + gatewayUniqueCodeMacId + ", gatewayBarcodeSerialNumber="
				+ gatewayBarcodeSerialNumber + ", gatewayLocation=" + gatewayLocation + ", assetTagCategory="
				+ assetTagCategory + ", user=" + user + ", admin=" + admin + ", fkUserId=" + fkUserId + ", fkAdminId="
				+ fkAdminId + ", wakeupTime=" + wakeupTime + ", timeZone=" + timeZone + ", datetime=" + datetime
				+ ", assetGetwayEnable=" + assetGetwayEnable + ", WorkingorNonworking=" + WorkingorNonworking + "]";
	}

	
}
