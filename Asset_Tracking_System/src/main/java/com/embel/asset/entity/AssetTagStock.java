package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "asset_tag_stock")
public class AssetTagStock 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assetTagId;
	
	@Column(name = "asset_tag_name")
	private String assetTagName;
	
	
	@Column(name="date_time")
	private Date datetime;
	
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

	@Column(name = "organization_id")
	private Long organization_id;
	
	@Column(name = "asset_tag_category")
	private String assetTagCategory;

	@Column(name = "status")
	private String status;
	
	public AssetTagStock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssetTagStock(Long assetTagId, String assetTagName, Date datetime, String assetUniqueCodeMacId,
			String assetSimNumber, String assetIMSINumber, String assetIMEINumber, String assetBarcodeSerialNumber,
			Long organization_id, String assetTagCategory, String status) {
		super();
		this.assetTagId = assetTagId;
		this.assetTagName = assetTagName;
		this.datetime = datetime;
		this.assetUniqueCodeMacId = assetUniqueCodeMacId;
		this.assetSimNumber = assetSimNumber;
		this.assetIMSINumber = assetIMSINumber;
		this.assetIMEINumber = assetIMEINumber;
		this.assetBarcodeSerialNumber = assetBarcodeSerialNumber;
		this.organization_id = organization_id;
		this.assetTagCategory = assetTagCategory;
		this.status = status;
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

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
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

	@Override
	public String toString() {
		return "AssetTagStock [assetTagId=" + assetTagId + ", assetTagName=" + assetTagName + ", datetime=" + datetime
				+ ", assetUniqueCodeMacId=" + assetUniqueCodeMacId + ", assetSimNumber=" + assetSimNumber
				+ ", assetIMSINumber=" + assetIMSINumber + ", assetIMEINumber=" + assetIMEINumber
				+ ", assetBarcodeSerialNumber=" + assetBarcodeSerialNumber + ", organization_id=" + organization_id
				+ ", assetTagCategory=" + assetTagCategory + ", status=" + status + "]";
	}

	

}
