package com.embel.asset.dto;

import java.util.Date;

public class AssetGatewayDto {
	
	private Long gatewayId;
	private String gatewayName;
	private String gatewayUniqueCodeMacId;
	private String gatewayBarcodeOrSerialNumber;
	private String gatewayLocation;
	private String assetTagCategory;
	private String assetSubCategory;
	private String user;
	private String admin;
	private String organization;
	private Long fkOrganizationId;
	private Long fkUserId;
	private Long fkAdminId;
	private String wakeupTime;
	private String timeZone;
	private Date datetime;
	private String createdBy;
	
	private long xCoordinate=0;
	private long yCoordinate=0;
	
	
//	private String time;
	public AssetGatewayDto() {
		super();
		// TODO Auto-generated constructor stub
	}


public AssetGatewayDto(Long gatewayId, String gatewayName, String gatewayUniqueCodeMacId,
		String gatewayBarcodeOrSerialNumber, String gatewayLocation, String assetTagCategory, String assetSubCategory,
		String user, String admin, String organization, Long fkOrganizationId, Long fkUserId, Long fkAdminId,
		String wakeupTime, String timeZone, Date datetime, String createdBy, long xCoordinate, long yCoordinate) {
	super();
	this.gatewayId = gatewayId;
	this.gatewayName = gatewayName;
	this.gatewayUniqueCodeMacId = gatewayUniqueCodeMacId;
	this.gatewayBarcodeOrSerialNumber = gatewayBarcodeOrSerialNumber;
	this.gatewayLocation = gatewayLocation;
	this.assetTagCategory = assetTagCategory;
	this.assetSubCategory = assetSubCategory;
	this.user = user;
	this.admin = admin;
	this.organization = organization;
	this.fkOrganizationId = fkOrganizationId;
	this.fkUserId = fkUserId;
	this.fkAdminId = fkAdminId;
	this.wakeupTime = wakeupTime;
	this.timeZone = timeZone;
	this.datetime = datetime;
	this.createdBy = createdBy;
	this.xCoordinate = xCoordinate;
	this.yCoordinate = yCoordinate;
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


public String getGatewayBarcodeOrSerialNumber() {
	return gatewayBarcodeOrSerialNumber;
}


public void setGatewayBarcodeOrSerialNumber(String gatewayBarcodeOrSerialNumber) {
	this.gatewayBarcodeOrSerialNumber = gatewayBarcodeOrSerialNumber;
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


public String getAssetSubCategory() {
	return assetSubCategory;
}


public void setAssetSubCategory(String assetSubCategory) {
	this.assetSubCategory = assetSubCategory;
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


public String getOrganization() {
	return organization;
}


public void setOrganization(String organization) {
	this.organization = organization;
}


public Long getFkOrganizationId() {
	return fkOrganizationId;
}


public void setFkOrganizationId(Long fkOrganizationId) {
	this.fkOrganizationId = fkOrganizationId;
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


public String getCreatedBy() {
	return createdBy;
}


public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}


public long getxCoordinate() {
	return xCoordinate;
}


public void setxCoordinate(long xCoordinate) {
	this.xCoordinate = xCoordinate;
}


public long getyCoordinate() {
	return yCoordinate;
}


public void setyCoordinate(long yCoordinate) {
	this.yCoordinate = yCoordinate;
}


@Override
public String toString() {
	return "AssetGatewayDto [gatewayId=" + gatewayId + ", gatewayName=" + gatewayName + ", gatewayUniqueCodeMacId="
			+ gatewayUniqueCodeMacId + ", gatewayBarcodeOrSerialNumber=" + gatewayBarcodeOrSerialNumber
			+ ", gatewayLocation=" + gatewayLocation + ", assetTagCategory=" + assetTagCategory + ", assetSubCategory="
			+ assetSubCategory + ", user=" + user + ", admin=" + admin + ", organization=" + organization
			+ ", fkOrganizationId=" + fkOrganizationId + ", fkUserId=" + fkUserId + ", fkAdminId=" + fkAdminId
			+ ", wakeupTime=" + wakeupTime + ", timeZone=" + timeZone + ", datetime=" + datetime + ", createdBy="
			+ createdBy + ", xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + "]";
}



	

}
