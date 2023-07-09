package com.embel.asset.bean;

public class ResponseAssetTagBean {

	private Long assetTagId;
	private String assetTagName;
	private String assetUniqueCodeMacId;
	private String assetIMEINumber;
	
	public ResponseAssetTagBean() {
		super();
	}

	public ResponseAssetTagBean(Long assetTagId, String assetTagName, String assetUniqueCodeMacId,
			String assetIMEINumber) {
		super();
		this.assetTagId = assetTagId;
		this.assetTagName = assetTagName;
		this.assetUniqueCodeMacId = assetUniqueCodeMacId;
		this.assetIMEINumber = assetIMEINumber;
	}

	public Long getAssetTagId() {
		return assetTagId;
	}

	public void setAssetTagId(Long assetTagId) {
		this.assetTagId = assetTagId;
	}

	public String getAssetTagName() {
		return assetTagName;
	}

	public void setAssetTagName(String assetTagName) {
		this.assetTagName = assetTagName;
	}

	public String getAssetUniqueCodeMacId() {
		return assetUniqueCodeMacId;
	}

	public void setAssetUniqueCodeMacId(String assetUniqueCodeMacId) {
		this.assetUniqueCodeMacId = assetUniqueCodeMacId;
	}

	public String getAssetIMEINumber() {
		return assetIMEINumber;
	}

	public void setAssetIMEINumber(String assetIMEINumber) {
		this.assetIMEINumber = assetIMEINumber;
	}

	@Override
	public String toString() {
		return "ResponseAssetTagBean [assetTagId=" + assetTagId + ", assetTagName=" + assetTagName
				+ ", assetUniqueCodeMacId=" + assetUniqueCodeMacId + ", assetIMEINumber=" + assetIMEINumber + "]";
	}
	
	
}
