package com.embel.asset.bean;

import java.util.Date;



public class ResponseTagstatusbean
{
	
	private Long assetTagId;
	
	private String assetTagName;
	
	private String user;
	
	private String admin;
	
	private Long fkUserId;
	
	private Long fkAdminId;
	
	private String wakeupTime;
	
	private String timeZone;
	
	private Date datetime;
	
	private String assetUniqueCodeMacId;

	private String assetSimNumber;
	
	private String assetIMSINumber;

	private String assetIMEINumber;

	private String assetBarcodeSerialNumber;
	
	private String assetLocation;
	
	private String assetTagCategory;

	private String status;

	private Long assetTagEnable;
	
	private String WorkingORNonWorking;

	public ResponseTagstatusbean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseTagstatusbean(Long assetTagId, String assetTagName, String user, String admin, Long fkUserId,
			Long fkAdminId, String wakeupTime, String timeZone, Date datetime, String assetUniqueCodeMacId,
			String assetSimNumber, String assetIMSINumber, String assetIMEINumber, String assetBarcodeSerialNumber,
			String assetLocation, String assetTagCategory, String status, Long assetTagEnable,
			String workingORNonWorking) {
		super();
		this.assetTagId = assetTagId;
		this.assetTagName = assetTagName;
		this.user = user;
		this.admin = admin;
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
		this.assetTagEnable = assetTagEnable;
		WorkingORNonWorking = workingORNonWorking;
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

	public Long getAssetTagEnable() {
		return assetTagEnable;
	}

	public void setAssetTagEnable(Long assetTagEnable) {
		this.assetTagEnable = assetTagEnable;
	}

	public String getWorkingORNonWorking() {
		return WorkingORNonWorking;
	}

	public void setWorkingORNonWorking(String workingORNonWorking) {
		WorkingORNonWorking = workingORNonWorking;
	}

	@Override
	public String toString() {
		return "ResponseTagstatusbean [assetTagId=" + assetTagId + ", assetTagName=" + assetTagName + ", user=" + user
				+ ", admin=" + admin + ", fkUserId=" + fkUserId + ", fkAdminId=" + fkAdminId + ", wakeupTime="
				+ wakeupTime + ", timeZone=" + timeZone + ", datetime=" + datetime + ", assetUniqueCodeMacId="
				+ assetUniqueCodeMacId + ", assetSimNumber=" + assetSimNumber + ", assetIMSINumber=" + assetIMSINumber
				+ ", assetIMEINumber=" + assetIMEINumber + ", assetBarcodeSerialNumber=" + assetBarcodeSerialNumber
				+ ", assetLocation=" + assetLocation + ", assetTagCategory=" + assetTagCategory + ", status=" + status
				+ ", assetTagEnable=" + assetTagEnable + ", WorkingORNonWorking=" + WorkingORNonWorking + "]";
	}

	
	
	
}
