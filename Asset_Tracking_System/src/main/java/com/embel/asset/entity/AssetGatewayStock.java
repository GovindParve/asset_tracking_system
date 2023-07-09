package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asset_gateway_stock")
public class AssetGatewayStock 
{
	@Id	
 	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "gateway_id")
	//@JsonProperty("data")
	private Long gatewayId;
	
	@Column(name = "gateway_name")
	private String gatewayName;
	
	@Column(name = "gateway_unique_code_or_mac_id")
	private String gatewayUniqueCodeMacId;
	
	@Column(name = "gateway_barcode_number_or_serial_number")
	private String gatewayBarcodeSerialNumber;

	
	@Column(name = "asset_tag_category" , nullable = true)
	private String assetTagCategory;

	@Column(name = "organization_id")
	private long organizationid;
	
	@Column(name = "status")
	private String status;

	public AssetGatewayStock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssetGatewayStock(Long gatewayId, String gatewayName, String gatewayUniqueCodeMacId,
			String gatewayBarcodeSerialNumber, String assetTagCategory, long organizationid, String status) {
		super();
		this.gatewayId = gatewayId;
		this.gatewayName = gatewayName;
		this.gatewayUniqueCodeMacId = gatewayUniqueCodeMacId;
		this.gatewayBarcodeSerialNumber = gatewayBarcodeSerialNumber;
		this.assetTagCategory = assetTagCategory;
		this.organizationid = organizationid;
		this.status = status;
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

	public String getAssetTagCategory() {
		return assetTagCategory;
	}

	public void setAssetTagCategory(String assetTagCategory) {
		this.assetTagCategory = assetTagCategory;
	}

	public long getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(long organizationid) {
		this.organizationid = organizationid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AssetGatewayStock [gatewayId=" + gatewayId + ", gatewayName=" + gatewayName
				+ ", gatewayUniqueCodeMacId=" + gatewayUniqueCodeMacId + ", gatewayBarcodeSerialNumber="
				+ gatewayBarcodeSerialNumber + ", assetTagCategory=" + assetTagCategory + ", organizationid="
				+ organizationid + ", status=" + status + "]";
	}

	
	
	
}
