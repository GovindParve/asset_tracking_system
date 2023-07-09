package com.embel.asset.dto;

import javax.persistence.Column;

public class ProductTagAllocationDto {

	private Long productAllocationId;
	private String productName;
	private Long productId;
	private String allocatedTagName;
	private Long allocatedTagId;
	private String assetUniqueCodeOrMacId;
	private String assetimei;
	private String user;
	private String admin;
	private Long fkUserId;
	private String dispatchLocation;
	private String subproductName;
	private String category;
	private Long dispatchtime;

	public ProductTagAllocationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductTagAllocationDto(Long productAllocationId, String productName, Long productId,
			String allocatedTagName, Long allocatedTagId, String assetUniqueCodeOrMacId, String assetimei, String user,
			String admin, Long fkUserId, String dispatchLocation, String subproductName, String category,
			Long dispatchtime) {
		super();
		this.productAllocationId = productAllocationId;
		this.productName = productName;
		this.productId = productId;
		this.allocatedTagName = allocatedTagName;
		this.allocatedTagId = allocatedTagId;
		this.assetUniqueCodeOrMacId = assetUniqueCodeOrMacId;
		this.assetimei = assetimei;
		this.user = user;
		this.admin = admin;
		this.fkUserId = fkUserId;
		this.dispatchLocation = dispatchLocation;
		this.subproductName = subproductName;
		this.category = category;
		this.dispatchtime = dispatchtime;
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

	public String getAssetimei() {
		return assetimei;
	}

	public void setAssetimei(String assetimei) {
		this.assetimei = assetimei;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getDispatchtime() {
		return dispatchtime;
	}

	public void setDispatchtime(Long dispatchtime) {
		this.dispatchtime = dispatchtime;
	}

	@Override
	public String toString() {
		return "ProductTagAllocationDto [productAllocationId=" + productAllocationId + ", productName=" + productName
				+ ", productId=" + productId + ", allocatedTagName=" + allocatedTagName + ", allocatedTagId="
				+ allocatedTagId + ", assetUniqueCodeOrMacId=" + assetUniqueCodeOrMacId + ", assetimei=" + assetimei
				+ ", user=" + user + ", admin=" + admin + ", fkUserId=" + fkUserId + ", dispatchLocation="
				+ dispatchLocation + ", subproductName=" + subproductName + ", category=" + category + ", dispatchtime="
				+ dispatchtime + "]";
	}

	
	
	
}
