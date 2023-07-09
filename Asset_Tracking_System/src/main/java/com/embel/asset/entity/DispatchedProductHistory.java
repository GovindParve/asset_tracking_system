package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dispatch_product_history")
public class DispatchedProductHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dispatchId;
	
	@Column(name = "category")
	private String tagCategoty;
	
	@Column(name = "tag_name")
	private String assetTagName;
	
	@Column(name = "gateway_name")
	private String assetGatewayName;
	
	@Column(name = "gateway_mac_id")
	private String assetGatewayMac_Id;
	
	@Column(name = "entry_location")
	private String tagEntryLocation;
	
	@Column(name = "barcode_or_serial_number")
	private String barcodeSerialNumber;
	
	@Column(name = "unique_code_or_mac_id")
	private String uniqueNumberMacId;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "exist_time")
	private String existTime;
	
	@Column(name = "entry_time")
	private String  entryTime;
	
	@Column(name = "exist_location")
	private String tagExistLocation;
	
	@Column(name = "dispatch_time")
	private String dispatchTime;

	@Column(name = "dispatched_reference_count")
	private int referenceCount;
	
	@Column(name = "product_id")
	private Long productId;
	
	@Column(name = "product_name")
	private String productName;

	public DispatchedProductHistory() {
		super();
	}

	public DispatchedProductHistory(Long dispatchId, String tagCategoty, String assetTagName, String assetGatewayName,
			String assetGatewayMac_Id, String tagEntryLocation, String barcodeSerialNumber, String uniqueNumberMacId,
			String latitude, String longitude, Date date, String existTime, String entryTime, String tagExistLocation,
			String dispatchTime, int referenceCount, Long productId, String productName) {
		super();
		this.dispatchId = dispatchId;
		this.tagCategoty = tagCategoty;
		this.assetTagName = assetTagName;
		this.assetGatewayName = assetGatewayName;
		this.assetGatewayMac_Id = assetGatewayMac_Id;
		this.tagEntryLocation = tagEntryLocation;
		this.barcodeSerialNumber = barcodeSerialNumber;
		this.uniqueNumberMacId = uniqueNumberMacId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
		this.existTime = existTime;
		this.entryTime = entryTime;
		this.tagExistLocation = tagExistLocation;
		this.dispatchTime = dispatchTime;
		this.referenceCount = referenceCount;
		this.productId = productId;
		this.productName = productName;
	}

	public Long getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(Long dispatchId) {
		this.dispatchId = dispatchId;
	}

	public String getTagCategoty() {
		return tagCategoty;
	}

	public void setTagCategoty(String tagCategoty) {
		this.tagCategoty = tagCategoty;
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

	public String getTagEntryLocation() {
		return tagEntryLocation;
	}

	public void setTagEntryLocation(String tagEntryLocation) {
		this.tagEntryLocation = tagEntryLocation;
	}

	public String getBarcodeSerialNumber() {
		return barcodeSerialNumber;
	}

	public void setBarcodeSerialNumber(String barcodeSerialNumber) {
		this.barcodeSerialNumber = barcodeSerialNumber;
	}

	public String getUniqueNumberMacId() {
		return uniqueNumberMacId;
	}

	public void setUniqueNumberMacId(String uniqueNumberMacId) {
		this.uniqueNumberMacId = uniqueNumberMacId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getExistTime() {
		return existTime;
	}

	public void setExistTime(String existTime) {
		this.existTime = existTime;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getTagExistLocation() {
		return tagExistLocation;
	}

	public void setTagExistLocation(String tagExistLocation) {
		this.tagExistLocation = tagExistLocation;
	}

	public String getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public int getReferenceCount() {
		return referenceCount;
	}

	public void setReferenceCount(int referenceCount) {
		this.referenceCount = referenceCount;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return "DispatchedProductHistory [dispatchId=" + dispatchId + ", tagCategoty=" + tagCategoty + ", assetTagName="
				+ assetTagName + ", assetGatewayName=" + assetGatewayName + ", assetGatewayMac_Id=" + assetGatewayMac_Id
				+ ", tagEntryLocation=" + tagEntryLocation + ", barcodeSerialNumber=" + barcodeSerialNumber
				+ ", uniqueNumberMacId=" + uniqueNumberMacId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", date=" + date + ", existTime=" + existTime + ", entryTime=" + entryTime + ", tagExistLocation="
				+ tagExistLocation + ", dispatchTime=" + dispatchTime + ", referenceCount=" + referenceCount
				+ ", productId=" + productId + ", productName=" + productName + "]";
	}

	
}
