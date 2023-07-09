package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asset_tracking_technology_subcategory")
public class SubCategory 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_subcatid;
	@Column
	private String SubCategoryname;
	
	
	
	public SubCategory(int pk_subcatid, String subCategoryname) {
		super();
		this.pk_subcatid = pk_subcatid;
		SubCategoryname = subCategoryname;
	}



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

	@Override
	public String toString() {
		return "SubCategory [pk_subcatid=" + pk_subcatid + ", SubCategoryname=" + SubCategoryname + "]";
	}



	public SubCategory() {
		super();
		
	}

	
	
	
}
