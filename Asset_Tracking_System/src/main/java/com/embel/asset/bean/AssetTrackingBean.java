

package com.embel.asset.bean;

import java.util.Date;

public class AssetTrackingBean {

	private Long trackingId;
	private String tagCategoty;
	private String assetTagName;
	private String assetGatewayName;
	private String tagEntryLocation;
	private String barcodeSerialNumber;
	private String latitude;
	private String longitude;
	private Date date;
	private Date entryTime;
	private Date existTime;
	private String tagExistLocation;
	private String dispatchTime;
	private int battryPercentage;
	//private Double distance;
	public Long getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(Long trackingId) {
		this.trackingId = trackingId;
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
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public Date getExistTime() {
		return existTime;
	}
	public void setExistTime(Date existTime) {
		this.existTime = existTime;
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
	public int getBattryPercentage() {
		return battryPercentage;
	}
	public void setBattryPercentage(int battryPercentage) {
		this.battryPercentage = battryPercentage;
	}
	public AssetTrackingBean(Long trackingId, String tagCategoty, String assetTagName, String assetGatewayName,
			String tagEntryLocation, String barcodeSerialNumber, String latitude, String longitude, Date date,
			Date entryTime, Date existTime, String tagExistLocation, String dispatchTime, int battryPercentage) {
		super();
		this.trackingId = trackingId;
		this.tagCategoty = tagCategoty;
		this.assetTagName = assetTagName;
		this.assetGatewayName = assetGatewayName;
		this.tagEntryLocation = tagEntryLocation;
		this.barcodeSerialNumber = barcodeSerialNumber;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
		this.entryTime = entryTime;
		this.existTime = existTime;
		this.tagExistLocation = tagExistLocation;
		this.dispatchTime = dispatchTime;
		this.battryPercentage = battryPercentage;
	}
	public AssetTrackingBean() {
		super();
		
	}
	
	
	
	}
