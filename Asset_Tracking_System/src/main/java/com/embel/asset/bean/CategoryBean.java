package com.embel.asset.bean;

public class CategoryBean {

	private Long pkCatId;
	private String categoryName;
	
	public CategoryBean() {
		super();
	}
	public CategoryBean(Long pkCatId, String categoryName) {
		super();
		this.pkCatId = pkCatId;
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "CategoryBean [pkCatId=" + pkCatId + ", categoryName=" + categoryName + "]";
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
