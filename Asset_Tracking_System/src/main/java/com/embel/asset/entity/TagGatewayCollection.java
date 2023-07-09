package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asset_tag_and_gateway_tracking_collection")
public class TagGatewayCollection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trackingId;
	
	@Column(name = "asset_tag_name")
	private String assetTagName;
	
	@Column(name = "asset_gateway_name")
	private String assetGatewayName;
	
	@Column(name = "asset_gateway_mac_id")
	private String assetGatewayMac_Id;
	
	@Column(name = "unique_code_or_mac_id")
	private String uniqueNumberMacId;
	
	@Column(name = "first_scanning_date")
	private Date scanningDate;
	
	@Column(name = "date")
	private String date;

	public TagGatewayCollection() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TagGatewayCollection(Long trackingId, String assetTagName, String assetGatewayName,
			String assetGatewayMac_Id, String uniqueNumberMacId, Date scanningDate, String date) {
		super();
		this.trackingId = trackingId;
		this.assetTagName = assetTagName;
		this.assetGatewayName = assetGatewayName;
		this.assetGatewayMac_Id = assetGatewayMac_Id;
		this.uniqueNumberMacId = uniqueNumberMacId;
		this.scanningDate = scanningDate;
		this.date = date;
	}

	@Override
	public String toString() {
		return "TagGatewayCollection [trackingId=" + trackingId + ", assetTagName=" + assetTagName
				+ ", assetGatewayName=" + assetGatewayName + ", assetGatewayMac_Id=" + assetGatewayMac_Id
				+ ", uniqueNumberMacId=" + uniqueNumberMacId + ", scanningDate=" + scanningDate + ", date=" + date
				+ "]";
	}

	public Long getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(Long trackingId) {
		this.trackingId = trackingId;
	}

	public String getAssetTagName() {
		return assetTagName;
	}

	public void setAssetTagName(String assetTagName) {
		this.assetTagName = assetTagName;
	}

	public String getAssetGatewayName() {
		return assetGatewayName;
	}

	public void setAssetGatewayName(String assetGatewayName) {
		this.assetGatewayName = assetGatewayName;
	}

	public String getAssetGatewayMac_Id() {
		return assetGatewayMac_Id;
	}

	public void setAssetGatewayMac_Id(String assetGatewayMac_Id) {
		this.assetGatewayMac_Id = assetGatewayMac_Id;
	}

	public String getUniqueNumberMacId() {
		return uniqueNumberMacId;
	}

	public void setUniqueNumberMacId(String uniqueNumberMacId) {
		this.uniqueNumberMacId = uniqueNumberMacId;
	}

	public Date getScanningDate() {
		return scanningDate;
	}

	public void setScanningDate(Date scanningDate) {
		this.scanningDate = scanningDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	

	
	
}
