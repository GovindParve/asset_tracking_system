package com.embel.asset.bean;

import java.util.Date;

public class AssetGatewayBean {
	
	private Long gatewayId;
	private String gatewayName;
	private String gatewayUniqueCodeMacId;
	private String gatewayBarcodeOrSerialNumber;
	private String gatewayLocation;
	private String assetTagCategory;
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
	//	private String time;
	public AssetGatewayBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AssetGatewayBean(Long gatewayId, String gatewayName, String gatewayUniqueCodeMacId,
			String gatewayBarcodeOrSerialNumber, String gatewayLocation, String assetTagCategory, String user,
			String admin, String organization, Long fkOrganizationId, Long fkUserId, Long fkAdminId, String wakeupTime,
			String timeZone, Date datetime, String createdBy) {
		super();
		this.gatewayId = gatewayId;
		this.gatewayName = gatewayName;
		this.gatewayUniqueCodeMacId = gatewayUniqueCodeMacId;
		this.gatewayBarcodeOrSerialNumber = gatewayBarcodeOrSerialNumber;
		this.gatewayLocation = gatewayLocation;
		this.assetTagCategory = assetTagCategory;
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
	@Override
	public String toString() {
		return "AssetGatewayBean [gatewayId=" + gatewayId + ", gatewayName=" + gatewayName + ", gatewayUniqueCodeMacId="
				+ gatewayUniqueCodeMacId + ", gatewayBarcodeOrSerialNumber=" + gatewayBarcodeOrSerialNumber
				+ ", gatewayLocation=" + gatewayLocation + ", assetTagCategory=" + assetTagCategory + ", user=" + user
				+ ", admin=" + admin + ", organization=" + organization + ", fkOrganizationId=" + fkOrganizationId
				+ ", fkUserId=" + fkUserId + ", fkAdminId=" + fkAdminId + ", wakeupTime=" + wakeupTime + ", timeZone="
				+ timeZone + ", datetime=" + datetime + ", createdBy=" + createdBy + "]";
	}
	
	

}
