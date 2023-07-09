package com.embel.asset.dto;

import javax.persistence.Column;

public class ProductDetailsDto {

	private Long productId;
	private String productName;
	private String description;
	private String createdby;
	private String updatedby;
	private String admin;
	private Long fkUserId;
	private String subproductname;
	
	private  String category;

	public ProductDetailsDto() {
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

	public String getSubproductname() {
		return subproductname;
	}

	public void setSubproductname(String subproductname) {
		this.subproductname = subproductname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ProductDetailsDto [productId=" + productId + ", productName=" + productName + ", description="
				+ description + ", createdby=" + createdby + ", updatedby=" + updatedby + ", admin=" + admin
				+ ", fkUserId=" + fkUserId + ", subproductname=" + subproductname + ", category=" + category + "]";
	}


	
}
