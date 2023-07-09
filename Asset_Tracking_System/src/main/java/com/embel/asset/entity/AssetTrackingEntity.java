package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asset_tracking_details")
public class AssetTrackingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long trackingId;
	
	@Column(name = "tag_tpye_or_category")
	private String tagCategoty;
	
	@Column(name = "asset_tag_name")
	private String assetTagName;
	
	@Column(name = "asset_gateway_serial_number")
	private String gSrNo;
	
	@Column(name = "asset_gateway_name")
	private String assetGatewayName;
	
	@Column(name = "asset_gateway_mac_id")
	private String assetGatewayMac_Id;
	
	@Column(name = "entry_location")
	private String tagEntryLocation;
	
	@Column(name = "barcode_or_serial_number")
	private String barcodeSerialNumber;
	
	@Column(name = "tagdist")
	private float tagDist;
	
	@Column(name = "imei_number")
	private String imeiNumber;
	
	@Column(name = "unique_code_or_mac_id")
	private String uniqueNumberMacId;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "exist_time")
	private String  existTime;
	
	@Column(name = "entry_time")
	private String entryTime;
	
	@Column(name = "tag_exist_location")
	private String tagExistLocation;
	
	@Column(name = "dispatch_time")
	private String dispatchTime;
	
	@Column(name = "battry_percentage")
	private Integer battryPercentage;
	
	@Column(name = "timestamp")
	private String timestamp;
	
	
	private String lb1;
	private String lb2;
	private String lb3;
	private String lb4;
	private String lb5;
	private String lb6;
	private String lb7;
	private String lb8;
	private String lb9;
	private String lb10;
	private String lb11;
	private String lb12;
	private String lb13;
	private String lb14;
	private String lb15;
	public AssetTrackingEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AssetTrackingEntity(long trackingId, String tagCategoty, String assetTagName, String gSrNo,
			String assetGatewayName, String assetGatewayMac_Id, String tagEntryLocation, String barcodeSerialNumber,
			float tagDist, String imeiNumber, String uniqueNumberMacId, String latitude, String longitude, String date,
			String time, String existTime, String entryTime, String tagExistLocation, String dispatchTime,
			Integer battryPercentage, String timestamp, String lb1, String lb2, String lb3, String lb4, String lb5,
			String lb6, String lb7, String lb8, String lb9, String lb10, String lb11, String lb12, String lb13,
			String lb14, String lb15) {
		super();
		this.trackingId = trackingId;
		this.tagCategoty = tagCategoty;
		this.assetTagName = assetTagName;
		this.gSrNo = gSrNo;
		this.assetGatewayName = assetGatewayName;
		this.assetGatewayMac_Id = assetGatewayMac_Id;
		this.tagEntryLocation = tagEntryLocation;
		this.barcodeSerialNumber = barcodeSerialNumber;
		this.tagDist = tagDist;
		this.imeiNumber = imeiNumber;
		this.uniqueNumberMacId = uniqueNumberMacId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
		this.time = time;
		this.existTime = existTime;
		this.entryTime = entryTime;
		this.tagExistLocation = tagExistLocation;
		this.dispatchTime = dispatchTime;
		this.battryPercentage = battryPercentage;
		this.timestamp = timestamp;
		this.lb1 = lb1;
		this.lb2 = lb2;
		this.lb3 = lb3;
		this.lb4 = lb4;
		this.lb5 = lb5;
		this.lb6 = lb6;
		this.lb7 = lb7;
		this.lb8 = lb8;
		this.lb9 = lb9;
		this.lb10 = lb10;
		this.lb11 = lb11;
		this.lb12 = lb12;
		this.lb13 = lb13;
		this.lb14 = lb14;
		this.lb15 = lb15;
	}
	public long getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(long trackingId) {
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
	public String getgSrNo() {
		return gSrNo;
	}
	public void setgSrNo(String gSrNo) {
		this.gSrNo = gSrNo;
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
	public float getTagDist() {
		return tagDist;
	}
	public void setTagDist(float tagDist) {
		this.tagDist = tagDist;
	}
	public String getImeiNumber() {
		return imeiNumber;
	}
	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public Integer getBattryPercentage() {
		return battryPercentage;
	}
	public void setBattryPercentage(Integer battryPercentage) {
		this.battryPercentage = battryPercentage;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getLb1() {
		return lb1;
	}
	public void setLb1(String lb1) {
		this.lb1 = lb1;
	}
	public String getLb2() {
		return lb2;
	}
	public void setLb2(String lb2) {
		this.lb2 = lb2;
	}
	public String getLb3() {
		return lb3;
	}
	public void setLb3(String lb3) {
		this.lb3 = lb3;
	}
	public String getLb4() {
		return lb4;
	}
	public void setLb4(String lb4) {
		this.lb4 = lb4;
	}
	public String getLb5() {
		return lb5;
	}
	public void setLb5(String lb5) {
		this.lb5 = lb5;
	}
	public String getLb6() {
		return lb6;
	}
	public void setLb6(String lb6) {
		this.lb6 = lb6;
	}
	public String getLb7() {
		return lb7;
	}
	public void setLb7(String lb7) {
		this.lb7 = lb7;
	}
	public String getLb8() {
		return lb8;
	}
	public void setLb8(String lb8) {
		this.lb8 = lb8;
	}
	public String getLb9() {
		return lb9;
	}
	public void setLb9(String lb9) {
		this.lb9 = lb9;
	}
	public String getLb10() {
		return lb10;
	}
	public void setLb10(String lb10) {
		this.lb10 = lb10;
	}
	public String getLb11() {
		return lb11;
	}
	public void setLb11(String lb11) {
		this.lb11 = lb11;
	}
	public String getLb12() {
		return lb12;
	}
	public void setLb12(String lb12) {
		this.lb12 = lb12;
	}
	public String getLb13() {
		return lb13;
	}
	public void setLb13(String lb13) {
		this.lb13 = lb13;
	}
	public String getLb14() {
		return lb14;
	}
	public void setLb14(String lb14) {
		this.lb14 = lb14;
	}
	public String getLb15() {
		return lb15;
	}
	public void setLb15(String lb15) {
		this.lb15 = lb15;
	}
	@Override
	public String toString() {
		return "AssetTrackingEntity [trackingId=" + trackingId + ", tagCategoty=" + tagCategoty + ", assetTagName="
				+ assetTagName + ", gSrNo=" + gSrNo + ", assetGatewayName=" + assetGatewayName + ", assetGatewayMac_Id="
				+ assetGatewayMac_Id + ", tagEntryLocation=" + tagEntryLocation + ", barcodeSerialNumber="
				+ barcodeSerialNumber + ", tagDist=" + tagDist + ", imeiNumber=" + imeiNumber + ", uniqueNumberMacId="
				+ uniqueNumberMacId + ", latitude=" + latitude + ", longitude=" + longitude + ", date=" + date
				+ ", time=" + time + ", existTime=" + existTime + ", entryTime=" + entryTime + ", tagExistLocation="
				+ tagExistLocation + ", dispatchTime=" + dispatchTime + ", battryPercentage=" + battryPercentage
				+ ", timestamp=" + timestamp + ", lb1=" + lb1 + ", lb2=" + lb2 + ", lb3=" + lb3 + ", lb4=" + lb4
				+ ", lb5=" + lb5 + ", lb6=" + lb6 + ", lb7=" + lb7 + ", lb8=" + lb8 + ", lb9=" + lb9 + ", lb10=" + lb10
				+ ", lb11=" + lb11 + ", lb12=" + lb12 + ", lb13=" + lb13 + ", lb14=" + lb14 + ", lb15=" + lb15 + "]";
	}

	
	
}
