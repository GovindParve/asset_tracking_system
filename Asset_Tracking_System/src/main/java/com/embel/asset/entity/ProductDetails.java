package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_details")
public class ProductDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "sub_product_name")
	private String subproductName;
	
	
	@Column(name = "product_description")
	private String description;

	@Column(name = "createdby")
	private String createdby;
	
	@Column(name = "updatedby")
	private String updatedby;
	
	@Column(name = "admin_name")
	private String admin;
	
	@Column(name = "user_id")
	private Long fkUserId;

	@Column(name = "category")
	private String category;
	
	
	public ProductDetails() {
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


	public String getSubproductName() {
		return subproductName;
	}


	public void setSubproductName(String subproductName) {
		this.subproductName = subproductName;
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


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public ProductDetails(Long productId, String productName, String subproductName, String description,
			String createdby, String updatedby, String admin, Long fkUserId, String category) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.subproductName = subproductName;
		this.description = description;
		this.createdby = createdby;
		this.updatedby = updatedby;
		this.admin = admin;
		this.fkUserId = fkUserId;
		this.category = category;
	}


	@Override
	public String toString() {
		return "ProductDetails [productId=" + productId + ", productName=" + productName + ", subproductName="
				+ subproductName + ", description=" + description + ", createdby=" + createdby + ", updatedby="
				+ updatedby + ", admin=" + admin + ", fkUserId=" + fkUserId + ", category=" + category + "]";
	}


	
	


}
