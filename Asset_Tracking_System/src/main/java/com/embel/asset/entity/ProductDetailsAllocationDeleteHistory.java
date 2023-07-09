package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_tag_allocation_Delete_History")
public class ProductDetailsAllocationDeleteHistory 
{
	
	
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
		
		@Column(name = "allocated_tag_unique_code")
		private String assetUniqueCodeOrMacId;

		@Column(name = "user_name")
		private String user;
		
		@Column(name = "user_id")
		private Long fkUserId;
		
		@Column(name = "dispatch_location")
		private String dispatchLocation;

		public ProductDetailsAllocationDeleteHistory(Long productAllocationId, String productName, Long productId,
				String allocatedTagName, Long allocatedTagId, String assetUniqueCodeOrMacId, String user, Long fkUserId,
				String dispatchLocation) {
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
		}

		public ProductDetailsAllocationDeleteHistory() {
			super();
			// TODO Auto-generated constructor stub
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

		
		


}
