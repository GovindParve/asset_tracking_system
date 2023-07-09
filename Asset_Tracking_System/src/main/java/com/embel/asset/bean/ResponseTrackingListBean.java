package com.embel.asset.bean;

import java.util.Date;

public class ResponseTrackingListBean {

	private long trackingId;
	private String assetTagName;
	private String assetGatewayName;
	private String tagEntryLocation;
	private String tagExistLocation;
	private String date;
	private String entryTime;
	private String existTime;
	
	private String dispatchTime;
	private int battryPercentage;
	private String time;
	
	public ResponseTrackingListBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseTrackingListBean(long trackingId, String assetTagName, String assetGatewayName,
			String tagEntryLocation, String tagExistLocation, String date, String entryTime, String existTime,
			String dispatchTime, int battryPercentage, String time) {
		super();
		this.trackingId = trackingId;
		this.assetTagName = assetTagName;
		this.assetGatewayName = assetGatewayName;
		this.tagEntryLocation = tagEntryLocation;
		this.tagExistLocation = tagExistLocation;
		this.date = date;
		this.entryTime = entryTime;
		this.existTime = existTime;
		this.dispatchTime = dispatchTime;
		this.battryPercentage = battryPercentage;
		this.time = time;
	}

	public long getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(long trackingId) {
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

	public String getTagEntryLocation() {
		return tagEntryLocation;
	}

	public void setTagEntryLocation(String tagEntryLocation) {
		this.tagEntryLocation = tagEntryLocation;
	}

	public String getTagExistLocation() {
		return tagExistLocation;
	}

	public void setTagExistLocation(String tagExistLocation) {
		this.tagExistLocation = tagExistLocation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getExistTime() {
		return existTime;
	}

	public void setExistTime(String existTime) {
		this.existTime = existTime;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ResponseTrackingListBean [trackingId=" + trackingId + ", assetTagName=" + assetTagName
				+ ", assetGatewayName=" + assetGatewayName + ", tagEntryLocation=" + tagEntryLocation
				+ ", tagExistLocation=" + tagExistLocation + ", date=" + date + ", entryTime=" + entryTime
				+ ", existTime=" + existTime + ", dispatchTime=" + dispatchTime + ", battryPercentage="
				+ battryPercentage + ", time=" + time + "]";
	}

	
}
