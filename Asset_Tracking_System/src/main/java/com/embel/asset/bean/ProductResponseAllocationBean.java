package com.embel.asset.bean;

public class ProductResponseAllocationBean {

	private Long productAllocationId;
	private String productName;
	private Long productId;
	private String allocatedTagName;
	private Long allocatedTagId;
	private String assetUniqueCodeOrMacId;
	private String user;
	private Long fkUserId;
	private String dispatchLocation;
	private String subproductName;
	public ProductResponseAllocationBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductResponseAllocationBean(Long productAllocationId, String productName, Long productId,
			String allocatedTagName, Long allocatedTagId, String assetUniqueCodeOrMacId, String user, Long fkUserId,
			String dispatchLocation, String subproductName) {
		super();
		this.productAllocationId = productAllocationId;
		this.productName = productName;
		this.productId = productId;
		this.allocatedTagName = allocatedTagName;
		this.allocatedTagId = allocatedTagId;
		this.assetUniqueCodeOrMacId = assetUniqueCodeOrMacId;
		this.user = user;
		this.fkUserId = fkUserId;
		this.dispatchLocation = dispatchLocation;
		this.subproductName = subproductName;
	}
	public Long getProductAllocationId() {
		return productAllocationId;
	}
	public void setProductAllocationId(Long productAllocationId) {
		this.productAllocationId = productAllocationId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getAllocatedTagName() {
		return allocatedTagName;
	}
	public void setAllocatedTagName(String allocatedTagName) {
		this.allocatedTagName = allocatedTagName;
	}
	public Long getAllocatedTagId() {
		return allocatedTagId;
	}
	public void setAllocatedTagId(Long allocatedTagId) {
		this.allocatedTagId = allocatedTagId;
	}
	public String getAssetUniqueCodeOrMacId() {
		return assetUniqueCodeOrMacId;
	}
	public void setAssetUniqueCodeOrMacId(String assetUniqueCodeOrMacId) {
		this.assetUniqueCodeOrMacId = assetUniqueCodeOrMacId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Long getFkUserId() {
		return fkUserId;
	}
	public void setFkUserId(Long fkUserId) {
		this.fkUserId = fkUserId;
	}
	public String getDispatchLocation() {
		return dispatchLocation;
	}
	public void setDispatchLocation(String dispatchLocation) {
		this.dispatchLocation = dispatchLocation;
	}
	public String getSubproductName() {
		return subproductName;
	}
	public void setSubproductName(String subproductName) {
		this.subproductName = subproductName;
	}
	@Override
	public String toString() {
		return "ProductResponseAllocationBean [productAllocationId=" + productAllocationId + ", productName="
				+ productName + ", productId=" + productId + ", allocatedTagName=" + allocatedTagName
				+ ", allocatedTagId=" + allocatedTagId + ", assetUniqueCodeOrMacId=" + assetUniqueCodeOrMacId
				+ ", user=" + user + ", fkUserId=" + fkUserId + ", dispatchLocation=" + dispatchLocation
				+ ", subproductName=" + subproductName + "]";
	}
	
	

}
