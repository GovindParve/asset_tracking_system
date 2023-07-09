package com.embel.asset.bean;

public class ProductDetailsBean {

	private Long productId;
	private String productName;
	private String description;
	private String createdby;
	private String updatedby;
	private Long fkUserId;
	private String admin;
	private String subproductName;
	public ProductDetailsBean(Long productId, String productName, String description, String createdby,
			String updatedby, Long fkUserId, String admin, String subproductName) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.createdby = createdby;
		this.updatedby = updatedby;
		this.fkUserId = fkUserId;
		this.admin = admin;
		this.subproductName = subproductName;
	}
	public ProductDetailsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public Long getFkUserId() {
		return fkUserId;
	}
	public void setFkUserId(Long fkUserId) {
		this.fkUserId = fkUserId;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getSubproductName() {
		return subproductName;
	}
	public void setSubproductName(String subproductName) {
		this.subproductName = subproductName;
	}
	@Override
	public String toString() {
		return "ProductDetailsBean [productId=" + productId + ", productName=" + productName + ", description="
				+ description + ", createdby=" + createdby + ", updatedby=" + updatedby + ", fkUserId=" + fkUserId
				+ ", admin=" + admin + ", subproductName=" + subproductName + "]";
	}
	
	
	
	
	
}
