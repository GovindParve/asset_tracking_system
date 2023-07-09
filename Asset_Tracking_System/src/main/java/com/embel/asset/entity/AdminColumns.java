package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "admin_columns")
public class AdminColumns 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pkadmincolumnsId;
	
	@Column(name = "admin_name")
	private String adminName;
	
	@Column(name = "columns_names")
	private String columns;
	
	@Column(name = "columns_unit")
	private String unit;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "fk_admin_id")
	private String fkAdminId;

	
	@Column(name = "fk_organization_id")
	private String fkOrganizationId;
	
	@Column(name = "organization")
	private String organization;
	
	@Column(name = "status")
	private String status;
	public AdminColumns() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdminColumns(Long pkadmincolumnsId, String adminName, String columns, String unit, String category,
			String fkAdminId, String fkOrganizationId, String organization, String status) {
		super();
		this.pkadmincolumnsId = pkadmincolumnsId;
		this.adminName = adminName;
		this.columns = columns;
		this.unit = unit;
		this.category = category;
		this.fkAdminId = fkAdminId;
		this.fkOrganizationId = fkOrganizationId;
		this.organization = organization;
		this.status = status;
	}
	public Long getPkadmincolumnsId() {
		return pkadmincolumnsId;
	}
	public void setPkadmincolumnsId(Long pkadmincolumnsId) {
		this.pkadmincolumnsId = pkadmincolumnsId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFkAdminId() {
		return fkAdminId;
	}
	public void setFkAdminId(String fkAdminId) {
		this.fkAdminId = fkAdminId;
	}
	public String getFkOrganizationId() {
		return fkOrganizationId;
	}
	public void setFkOrganizationId(String fkOrganizationId) {
		this.fkOrganizationId = fkOrganizationId;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
	

}
