package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asset_tracking_technology_category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pkCatId;
	
	@Column(name = "asset_tracker_name")
	private String categoryName;

	public Category() {
		super();
	}

	public Category(Long pkCatId, String categoryName) {
		super();
		this.pkCatId = pkCatId;
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "Category [pkCatId=" + pkCatId + ", categoryName=" + categoryName + "]";
	}

	public Long getPkCatId() {
		return pkCatId;
	}

	public void setPkCatId(Long pkCatId) {
		this.pkCatId = pkCatId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
