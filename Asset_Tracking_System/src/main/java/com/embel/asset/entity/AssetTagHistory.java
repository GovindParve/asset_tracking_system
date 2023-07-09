package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asset_tag_generation_history")
public class AssetTagHistory 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assetTagId;
	
	@Column(name = "asset_tag_name")
	private String assetTagName;
	
	@Column(name = "user_name")
	private String user;
	
	@Column(name = "admin_name")
	private String admin;
	
	@Column(name = "organization_name")
	private String organization;
	
	@Column(name = "organization_id")
	private Long fkOrganizationId;
	
	@Column(name = "user_id")
	private Long fkUserId;
	
	@Column(name = "admin_id")
	private Long fkAdminId;
	
	@Column(name="wakeup_time")
	private String wakeupTime;
	
	@Column(name="time_zone")
	private String timeZone;
	
	@Column(name="date")
	private Date date;
	
	
	@Column(name="time")
	private Date time;
	
	
	
	@Column(name = "asset_unique_code_or_mac_id")
	private String assetUniqueCodeMacId;

	@Column(name = "asset_sim_number")
	private String assetSimNumber;
	
	@Column(name = "asset_imsi_number")
	private String assetIMSINumber;
	
	@Column(name = "asset_imei_number")
	private String assetIMEINumber;
	
	//---FOR GPS/GSM---------------
	@Column(name = "asset_barcode_number_or_serial_number")
	private String assetBarcodeSerialNumber;
	
	@Column(name = "asset_location")
	private String assetLocation;
	
	@Column(name = "asset_tag_category")
	private String assetTagCategory;
	
	@Column(name = "tag_status")
	private String status;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="asset_enable")
	private Long assetTagEnable;

	public AssetTagHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssetTagHistory(Long assetTagId, String assetTagName, String user, String admin, String organization,
			Long fkOrganizationId, Long fkUserId, Long fkAdminId, String wakeupTime, String timeZone, Date date,
			Date time, String assetUniqueCodeMacId, String assetSimNumber, String assetIMSINumber,
			String assetIMEINumber, String assetBarcodeSerialNumber, String assetLocation, String assetTagCategory,
			String status, String createdBy, Long assetTagEnable) {
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
		this.date = date;
		this.time = time;
		this.assetUniqueCodeMacId = assetUniqueCodeMacId;
		this.assetSimNumber = assetSimNumber;
		this.assetIMSINumber = assetIMSINumber;
		this.assetIMEINumber = assetIMEINumber;
		this.assetBarcodeSerialNumber = assetBarcodeSerialNumber;
		this.assetLocation = assetLocation;
		this.assetTagCategory = assetTagCategory;
		this.status = status;
		this.createdBy = createdBy;
		this.assetTagEnable = assetTagEnable;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	public Long getAssetTagEnable() {
		return assetTagEnable;
	}

	public void setAssetTagEnable(Long assetTagEnable) {
		this.assetTagEnable = assetTagEnable;
	}

	@Override
	public String toString() {
		return "AssetTagHistory [assetTagId=" + assetTagId + ", assetTagName=" + assetTagName + ", user=" + user
				+ ", admin=" + admin + ", organization=" + organization + ", fkOrganizationId=" + fkOrganizationId
				+ ", fkUserId=" + fkUserId + ", fkAdminId=" + fkAdminId + ", wakeupTime=" + wakeupTime + ", timeZone="
				+ timeZone + ", date=" + date + ", time=" + time + ", assetUniqueCodeMacId=" + assetUniqueCodeMacId
				+ ", assetSimNumber=" + assetSimNumber + ", assetIMSINumber=" + assetIMSINumber + ", assetIMEINumber="
				+ assetIMEINumber + ", assetBarcodeSerialNumber=" + assetBarcodeSerialNumber + ", assetLocation="
				+ assetLocation + ", assetTagCategory=" + assetTagCategory + ", status=" + status + ", createdBy="
				+ createdBy + ", assetTagEnable=" + assetTagEnable + "]";
	}


}
