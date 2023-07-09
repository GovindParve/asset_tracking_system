package com.embel.asset.bean;

import java.util.Date;

public class AssetTagBean {

	private Long assetTagId;
	private String assetTagName;
	private String user;
	private String admin;
	private String organization;
	private Long fkOrganizationId;
	private Long fkUserId;
	private Long fkAdminId;
	private String wakeupTime;
	private String timeZone;
	private Date datetime;
//	private Date date;
//	private String time;
	private String assetUniqueCodeMacId;
	private String assetSimNumber;
	private String assetIMSINumber;
	private String assetIMEINumber;
	private String assetBarcodeSerialNumber;
	private String assetLocation;
	private String assetTagCategory;
	private String status;
	private String createdBy;
	public AssetTagBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AssetTagBean(Long assetTagId, String assetTagName, String user, String admin, String organization,
			Long fkOrganizationId, Long fkUserId, Long fkAdminId, String wakeupTime, String timeZone, Date datetime,
			String assetUniqueCodeMacId, String assetSimNumber, String assetIMSINumber, String assetIMEINumber,
			String assetBarcodeSerialNumber, String assetLocation, String assetTagCategory, String status,
			String createdBy) {
		super();
		this.assetTagId = assetTagId;
		this.assetTagName = assetTagName;
		this.user = user;
		this.admin = admin;
		this.organization = organization;
		this.fkOrganizationId = fkOrganizationId;
		this.fkUserId = fkUserId;
		this.fkAdminId = fkAdminId;
		this.wakeupTime = wakeupTime;
		this.timeZone = timeZone;
		this.datetime = datetime;
		this.assetUniqueCodeMacId = assetUniqueCodeMacId;
		this.assetSimNumber = assetSimNumber;
		this.assetIMSINumber = assetIMSINumber;
		this.assetIMEINumber = assetIMEINumber;
		this.assetBarcodeSerialNumber = assetBarcodeSerialNumber;
		this.assetLocation = assetLocation;
		this.assetTagCategory = assetTagCategory;
		this.status = status;
		this.createdBy = createdBy;
	}
	public Long getAssetTagId() {
		return assetTagId;
	}
	public void setAssetTagId(Long assetTagId) {
		this.assetTagId = assetTagId;
	}
	public String getAssetTagName() {
		return assetTagName;
	}
	public void setAssetTagName(String assetTagName) {
		this.assetTagName = assetTagName;
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
	public String getAssetUniqueCodeMacId() {
		return assetUniqueCodeMacId;
	}
	public void setAssetUniqueCodeMacId(String assetUniqueCodeMacId) {
		this.assetUniqueCodeMacId = assetUniqueCodeMacId;
	}
	public String getAssetSimNumber() {
		return assetSimNumber;
	}
	public void setAssetSimNumber(String assetSimNumber) {
		this.assetSimNumber = assetSimNumber;
	}
	public String getAssetIMSINumber() {
		return assetIMSINumber;
	}
	public void setAssetIMSINumber(String assetIMSINumber) {
		this.assetIMSINumber = assetIMSINumber;
	}
	public String getAssetIMEINumber() {
		return assetIMEINumber;
	}
	public void setAssetIMEINumber(String assetIMEINumber) {
		this.assetIMEINumber = assetIMEINumber;
	}
	public String getAssetBarcodeSerialNumber() {
		return assetBarcodeSerialNumber;
	}
	public void setAssetBarcodeSerialNumber(String assetBarcodeSerialNumber) {
		this.assetBarcodeSerialNumber = assetBarcodeSerialNumber;
	}
	public String getAssetLocation() {
		return assetLocation;
	}
	public void setAssetLocation(String assetLocation) {
		this.assetLocation = assetLocation;
	}
	public String getAssetTagCategory() {
		return assetTagCategory;
	}
	public void setAssetTagCategory(String assetTagCategory) {
		this.assetTagCategory = assetTagCategory;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "AssetTagBean [assetTagId=" + assetTagId + ", assetTagName=" + assetTagName + ", user=" + user
				+ ", admin=" + admin + ", organization=" + organization + ", fkOrganizationId=" + fkOrganizationId
				+ ", fkUserId=" + fkUserId + ", fkAdminId=" + fkAdminId + ", wakeupTime=" + wakeupTime + ", timeZone="
				+ timeZone + ", datetime=" + datetime + ", assetUniqueCodeMacId=" + assetUniqueCodeMacId
				+ ", assetSimNumber=" + assetSimNumber + ", assetIMSINumber=" + assetIMSINumber + ", assetIMEINumber="
				+ assetIMEINumber + ", assetBarcodeSerialNumber=" + assetBarcodeSerialNumber + ", assetLocation="
				+ assetLocation + ", assetTagCategory=" + assetTagCategory + ", status=" + status + ", createdBy="
				+ createdBy + "]";
	}
	
	
}
