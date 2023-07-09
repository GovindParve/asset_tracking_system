package com.embel.asset.bean;

public class ResponseGpsTagBean
{
	private String tagName;
	private String tagimeiNumber;
	private String latitude;
	private String longitude;
	private String TimeStamp;
	
	
	
	public ResponseGpsTagBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ResponseGpsTagBean(String tagName, String tagimeiNumber, String latitude, String longitude,
			String timeStamp) {
		super();
		this.tagName = tagName;
		this.tagimeiNumber = tagimeiNumber;
		this.latitude = latitude;
		this.longitude = longitude;
		TimeStamp = timeStamp;
	}


	@Override
	public String toString() {
		return "ResponseGpsTagBean [tagName=" + tagName + ", tagimeiNumber=" + tagimeiNumber + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", TimeStamp=" + TimeStamp + "]";
	}


	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagimeiNumber() {
		return tagimeiNumber;
	}
	public void setTagimeiNumber(String tagimeiNumber) {
		this.tagimeiNumber = tagimeiNumber;
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
	public String getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
	
	

}
