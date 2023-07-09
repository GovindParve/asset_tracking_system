package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_tag_allocation")
public class ProductDetailForAllocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productAllocationId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_id")
	private Long productId;
	
	@Column(name = "allocated_tag")
	private String allocatedTagName;
	
	@Column(name = "allocated_tag_id")
	private Long allocatedTagId;
	
	@Column(name = "assetimei")
	private String assetimei;
	
	@Column(name = "allocated_tag_unique_code")
	private String assetUniqueCodeOrMacId;

	@Column(name = "user_name")
	private String user;
	
	@Column(name = "admin_name")
	private String admin;
	
	
	@Column(name = "user_id")
	private Long fkUserId;
	
	@Column(name = "dispatch_location")
	private String dispatchLocation;
	
	@Column(name = "sub_product_name")
	private String subproductName;

	@Column(name = "dispatch_time")
	private Long dispatchtime;


	@Column(name = "category")
	private String category;
	
	


	public ProductDetailForAllocation() {
		super();
		// TODO Auto-generated constructor stub
	}




	public ProductDetailForAllocation(Long productAllocationId, String productName, Long productId,
			String allocatedTagName, Long allocatedTagId, String assetimei, String assetUniqueCodeOrMacId, String user,
			String admin, Long fkUserId, String dispatchLocation, String subproductName, Long dispatchtime,
			String category) {
		super();
		this.productAllocationId = productAllocationId;
		this.productName = productName;
		this.productId = productId;
		this.allocatedTagName = allocatedTagName;
		this.allocatedTagId = allocatedTagId;
		this.assetimei = assetimei;
		this.assetUniqueCodeOrMacId = assetUniqueCodeOrMacId;
		this.user = user;
		this.admin = admin;
		this.fkUserId = fkUserId;
		this.dispatchLocation = dispatchLocation;
		this.subproductName = subproductName;
		this.dispatchtime = dispatchtime;
		this.category = category;
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




	public String getAssetimei() {
		return assetimei;
	}




	public void setAssetimei(String assetimei) {
		this.assetimei = assetimei;
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




	public Long getDispatchtime() {
		return dispatchtime;
	}




	public void setDispatchtime(Long dispatchtime) {
		this.dispatchtime = dispatchtime;
	}




	public String getCategory() {
		return category;
	}




	public void setCategory(String category) {
		this.category = category;
	}




	@Override
	public String toString() {
		return "ProductDetailForAllocation [productAllocationId=" + productAllocationId + ", productName=" + productName
				+ ", productId=" + productId + ", allocatedTagName=" + allocatedTagName + ", allocatedTagId="
				+ allocatedTagId + ", assetimei=" + assetimei + ", assetUniqueCodeOrMacId=" + assetUniqueCodeOrMacId
				+ ", user=" + user + ", admin=" + admin + ", fkUserId=" + fkUserId + ", dispatchLocation="
				+ dispatchLocation + ", subproductName=" + subproductName + ", dispatchtime=" + dispatchtime
				+ ", category=" + category + "]";
	}



	
}
