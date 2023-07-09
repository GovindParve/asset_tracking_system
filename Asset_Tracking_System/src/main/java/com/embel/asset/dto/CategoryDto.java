package com.embel.asset.dto;

public class CategoryDto {

	private Long pkCatId;
	private String categoryName;
	
	public CategoryDto() {
		super();
	}
	public CategoryDto(Long pkCatId, String categoryName) {
		super();
		this.pkCatId = pkCatId;
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "CategoryDto [pkCatId=" + pkCatId + ", categoryName=" + categoryName + "]";
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
