package com.embel.asset.dto;

public class RequestSubCategoryDto
{
	private int pk_subcatid;
	
	private String SubCategoryname;
	
	public int getPk_subcatid() {
		return pk_subcatid;
	}

	public void setPk_subcatid(int pk_subcatid) {
		this.pk_subcatid = pk_subcatid;
	}

	public String getSubCategoryname() {
		return SubCategoryname;
	}

	public void setSubCategoryname(String subCategoryname) {
		SubCategoryname = subCategoryname;
	}

	public RequestSubCategoryDto(int pk_subcatid, String subCategoryname) {
		super();
		this.pk_subcatid = pk_subcatid;
		SubCategoryname = subCategoryname;
	}

	public RequestSubCategoryDto() {
		super();

	}

	@Override
	public String toString() {
		return "RequestSubCategoryDto [pk_subcatid=" + pk_subcatid + ", SubCategoryname=" + SubCategoryname + "]";
	}


}
