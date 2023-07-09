package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asset_gateway_creation")
public class AssetGateway {
	
	@Id	
 	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "gateway_id")
	//@JsonProperty("data")
	private Long gatewayId;
	
	@Column(name = "gateway_name")
	private String gatewayName;
	
	@Column(name = "gateway_barcode_number")
	private Long gatewayBarcodeNumber;
	
	@Column(name = "gateway_unique_code_or_mac_id")
	private String gatewayUniqueCodeMacId;
	
	@Column(name = "gateway_barcode_number_or_serial_number")
	private String gatewayBarcodeSerialNumber;
	
	@Column(name = "gateway_location" , nullable = true)
	private String gatewayLocation;
	
	@Column(name = "asset_tag_category" , nullable = true)
	private String assetTagCategory;
	
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
	
	@Column(name="date_time" , nullable = true)
	private Date datetime;
	
//	@Column(name="time" , nullable = true)
//	private String time;
	
	@Column(name="assetGetway_enable")
	private Long assetGetwayEnable;

	@Column(name="x_coordinate")
	private long xCoordinate=0;
	
	@Column(name="y_coordinate")
	private long yCoordinate=0;
	
	@Column(name="created_by")
	private String createdBy;
	
	public AssetGateway() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssetGateway(Long gatewayId, String gatewayName, Long gatewayBarcodeNumber, String gatewayUniqueCodeMacId,
			String gatewayBarcodeSerialNumber, String gatewayLocation, String assetTagCategory, String user,
			String admin, String organization, Long fkOrganizationId, Long fkUserId, Long fkAdminId, String wakeupTime,
			String timeZone, Date datetime, Long assetGetwayEnable, long xCoordinate, long yCoordinate,
			String createdBy) {
		super();
		this.gatewayId = gatewayId;
		this.gatewayName = gatewayName;
		this.gatewayBarcodeNumber = gatewayBarcodeNumber;
		this.gatewayUniqueCodeMacId = gatewayUniqueCodeMacId;
		this.gatewayBarcodeSerialNumber = gatewayBarcodeSerialNumber;
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
		this.assetGetwayEnable = assetGetwayEnable;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
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

	public Long getGatewayBarcodeNumber() {
		return gatewayBarcodeNumber;
	}

	public void setGatewayBarcodeNumber(Long gatewayBarcodeNumber) {
		this.gatewayBarcodeNumber = gatewayBarcodeNumber;
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

	public Long getAssetGetwayEnable() {
		return assetGetwayEnable;
	}

	public void setAssetGetwayEnable(Long assetGetwayEnable) {
		this.assetGetwayEnable = assetGetwayEnable;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "AssetGateway [gatewayId=" + gatewayId + ", gatewayName=" + gatewayName + ", gatewayBarcodeNumber="
				+ gatewayBarcodeNumber + ", gatewayUniqueCodeMacId=" + gatewayUniqueCodeMacId
				+ ", gatewayBarcodeSerialNumber=" + gatewayBarcodeSerialNumber + ", gatewayLocation=" + gatewayLocation
				+ ", assetTagCategory=" + assetTagCategory + ", user=" + user + ", admin=" + admin + ", organization="
				+ organization + ", fkOrganizationId=" + fkOrganizationId + ", fkUserId=" + fkUserId + ", fkAdminId="
				+ fkAdminId + ", wakeupTime=" + wakeupTime + ", timeZone=" + timeZone + ", datetime=" + datetime
				+ ", assetGetwayEnable=" + assetGetwayEnable + ", xCoordinate=" + xCoordinate + ", yCoordinate="
				+ yCoordinate + ", createdBy=" + createdBy + "]";
	}
	
}
