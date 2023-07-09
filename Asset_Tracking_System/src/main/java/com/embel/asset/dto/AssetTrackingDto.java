package com.embel.asset.dto;

import java.util.Date;

public class AssetTrackingDto {

	private Long trackingId;
	private String gSrNo;
	private String tagCategoty;
	private String assetTagName;
	private String assetGatewayName;
	private String gMacId;
	
	private String tagEntryLocation;
	private String bSN;
	private String imeiNumber;
	private String tMacId;
	private float tagDist;
	private String latitude;
	private String longitude;
	private Date date;
	private  String time;
	
	private String  entryTime;
	private String existTime;
	
	private String tagExistLocation;
	private String dispatchTime;
	private Integer batStat;
	private Date TS;
	
//	private String lb1;
//	private String lb2;
//	private String lb3;
//	private String lb4;
//	private String lb5;
//	private String lb6;
//	private String lb7;
//	private String lb8;
//	private String lb9;
//	private String lb10;
//	private String lb11;
//	private String lb12;
//	private String lb13;
//	private String lb14;
//	private String lb15;
	
	private String lb1="0.0";
	private String lb2="0.0";
	private String lb3="0.0";
	private String lb4="0.0";
	private String lb5="0.0";
	private String lb6="0.0";
	private String lb7="0.0";
	private String lb8="0.0";
	private String lb9="0.0";
	private String lb10="0.0";
	private String lb11="0.0";
	private String lb12="0.0";
	private String lb13="0.0";
	private String lb14="0.0";
	private String lb15="0.0";
	public AssetTrackingDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AssetTrackingDto(Long trackingId, String gSrNo, String tagCategoty, String assetTagName,
			String assetGatewayName, String gMacId, String tagEntryLocation, String bSN, String imeiNumber,
			String tMacId, float tagDist, String latitude, String longitude, Date date, String time, String entryTime,
			String existTime, String tagExistLocation, String dispatchTime, Integer batStat, Date tS, String lb1,
			String lb2, String lb3, String lb4, String lb5, String lb6, String lb7, String lb8, String lb9, String lb10,
			String lb11, String lb12, String lb13, String lb14, String lb15) {
		super();
		this.trackingId = trackingId;
		this.gSrNo = gSrNo;
		this.tagCategoty = tagCategoty;
		this.assetTagName = assetTagName;
		this.assetGatewayName = assetGatewayName;
		this.gMacId = gMacId;
		this.tagEntryLocation = tagEntryLocation;
		this.bSN = bSN;
		this.imeiNumber = imeiNumber;
		this.tMacId = tMacId;
		this.tagDist = tagDist;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
		this.time = time;
		this.entryTime = entryTime;
		this.existTime = existTime;
		this.tagExistLocation = tagExistLocation;
		this.dispatchTime = dispatchTime;
		this.batStat = batStat;
		TS = tS;
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
	public Long getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(Long trackingId) {
		this.trackingId = trackingId;
	}
	public String getgSrNo() {
		return gSrNo;
	}
	public void setgSrNo(String gSrNo) {
		this.gSrNo = gSrNo;
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
	public String getgMacId() {
		return gMacId;
	}
	public void setgMacId(String gMacId) {
		this.gMacId = gMacId;
	}
	public String getTagEntryLocation() {
		return tagEntryLocation;
	}
	public void setTagEntryLocation(String tagEntryLocation) {
		this.tagEntryLocation = tagEntryLocation;
	}
	public String getbSN() {
		return bSN;
	}
	public void setbSN(String bSN) {
		this.bSN = bSN;
	}
	public String getImeiNumber() {
		return imeiNumber;
	}
	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	public String gettMacId() {
		return tMacId;
	}
	public void settMacId(String tMacId) {
		this.tMacId = tMacId;
	}
	public float getTagDist() {
		return tagDist;
	}
	public void setTagDist(float tagDist) {
		this.tagDist = tagDist;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public Integer getBatStat() {
		return batStat;
	}
	public void setBatStat(Integer batStat) {
		this.batStat = batStat;
	}
	public Date getTS() {
		return TS;
	}
	public void setTS(Date tS) {
		TS = tS;
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
		return "AssetTrackingDto [trackingId=" + trackingId + ", gSrNo=" + gSrNo + ", tagCategoty=" + tagCategoty
				+ ", assetTagName=" + assetTagName + ", assetGatewayName=" + assetGatewayName + ", gMacId=" + gMacId
				+ ", tagEntryLocation=" + tagEntryLocation + ", bSN=" + bSN + ", imeiNumber=" + imeiNumber + ", tMacId="
				+ tMacId + ", tagDist=" + tagDist + ", latitude=" + latitude + ", longitude=" + longitude + ", date="
				+ date + ", time=" + time + ", entryTime=" + entryTime + ", existTime=" + existTime
				+ ", tagExistLocation=" + tagExistLocation + ", dispatchTime=" + dispatchTime + ", batStat=" + batStat
				+ ", TS=" + TS + ", lb1=" + lb1 + ", lb2=" + lb2 + ", lb3=" + lb3 + ", lb4=" + lb4 + ", lb5=" + lb5
				+ ", lb6=" + lb6 + ", lb7=" + lb7 + ", lb8=" + lb8 + ", lb9=" + lb9 + ", lb10=" + lb10 + ", lb11="
				+ lb11 + ", lb12=" + lb12 + ", lb13=" + lb13 + ", lb14=" + lb14 + ", lb15=" + lb15 + "]";
	}
	
	
}