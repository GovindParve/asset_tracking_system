package com.embel.asset.bean;

import java.util.Date;

public class ResponseTrackingBean {

	private Long trackingId;	
	private String assetTagName;
	private String tagEntryLocation;
	private String date;	
	private String entryTime;	
	//private Long status;	

	public ResponseTrackingBean() {
		super();
	}
	public ResponseTrackingBean(Long trackingId, String assetTagName, String tagEntryLocation, String date,
			String entryTime) {
		super();
		this.trackingId = trackingId;
		this.assetTagName = assetTagName;
		this.tagEntryLocation = tagEntryLocation;
		this.date = date;
		this.entryTime = entryTime;
	}
	@Override
	public String toString() {
		return "ResponseTrackingBean [trackingId=" + trackingId + ", assetTagName=" + assetTagName
				+ ", tagEntryLocation=" + tagEntryLocation + ", date=" + date + ", entryTime=" + entryTime + "]";
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
	public String getTagEntryLocation() {
		return tagEntryLocation;
	}
	public void setTagEntryLocation(String tagEntryLocation) {
		this.tagEntryLocation = tagEntryLocation;
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
	
}
