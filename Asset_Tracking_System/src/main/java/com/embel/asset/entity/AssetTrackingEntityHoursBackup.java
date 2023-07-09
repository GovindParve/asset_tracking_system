package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "asset_tracking_detailsHoursBackup")
public class AssetTrackingEntityHoursBackup
{


		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long trackingId;
		
		@Column(name = "tag_tpye_or_category")
		private String tagCategoty;
		
		@Column(name = "asset_tag_name")
		private String assetTagName;
		
		@Column(name = "asset_gateway_name")
		private String assetGatewayName;
		
		@Column(name = "asset_gateway_mac_id")
		private String assetGatewayMac_Id;
		
		@Column(name = "entry_location")
		private String tagEntryLocation;
		
		@Column(name = "barcode_or_serial_number")
		private String barcodeSerialNumber;
		
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

		

		public AssetTrackingEntityHoursBackup() {
			super();
			// TODO Auto-generated constructor stub
		}



		public AssetTrackingEntityHoursBackup(long trackingId, String tagCategoty, String assetTagName,
				String assetGatewayName, String assetGatewayMac_Id, String tagEntryLocation, String barcodeSerialNumber,
				String imeiNumber, String uniqueNumberMacId, String latitude, String longitude, String date,
				String time, String existTime, String entryTime, String tagExistLocation, String dispatchTime,
				Integer battryPercentage) {
			super();
			this.trackingId = trackingId;
			this.tagCategoty = tagCategoty;
			this.assetTagName = assetTagName;
			this.assetGatewayName = assetGatewayName;
			this.assetGatewayMac_Id = assetGatewayMac_Id;
			this.tagEntryLocation = tagEntryLocation;
			this.barcodeSerialNumber = barcodeSerialNumber;
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
		}



		@Override
		public String toString() {
			return "AssetTrackingEntityHoursBackup [trackingId=" + trackingId + ", tagCategoty=" + tagCategoty
					+ ", assetTagName=" + assetTagName + ", assetGatewayName=" + assetGatewayName
					+ ", assetGatewayMac_Id=" + assetGatewayMac_Id + ", tagEntryLocation=" + tagEntryLocation
					+ ", barcodeSerialNumber=" + barcodeSerialNumber + ", imeiNumber=" + imeiNumber
					+ ", uniqueNumberMacId=" + uniqueNumberMacId + ", latitude=" + latitude + ", longitude=" + longitude
					+ ", date=" + date + ", time=" + time + ", existTime=" + existTime + ", entryTime=" + entryTime
					+ ", tagExistLocation=" + tagExistLocation + ", dispatchTime=" + dispatchTime
					+ ", battryPercentage=" + battryPercentage + "]";
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

		
	


}
