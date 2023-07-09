package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="column_mapping")
public class columnmapping
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pkId;
	
	@Column(name = "column_name")
	private String columnName;
	
	@Column(name = "allocated_column_db")
	private String allocatedColumn_db;

	@Column(name = "allocated_column_ui")
	private String allocatedColumn_ui;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "category")
	private String category;
	
	public columnmapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public columnmapping(Long pkId, String columnName, String allocatedColumn_db, String allocatedColumn_ui,
			String unit, String category) {
		super();
		this.pkId = pkId;
		this.columnName = columnName;
		this.allocatedColumn_db = allocatedColumn_db;
		this.allocatedColumn_ui = allocatedColumn_ui;
		this.unit = unit;
		this.category = category;
	}

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAllocatedColumn_db() {
		return allocatedColumn_db;
	}

	public void setAllocatedColumn_db(String allocatedColumn_db) {
		this.allocatedColumn_db = allocatedColumn_db;
	}

	public String getAllocatedColumn_ui() {
		return allocatedColumn_ui;
	}

	public void setAllocatedColumn_ui(String allocatedColumn_ui) {
		this.allocatedColumn_ui = allocatedColumn_ui;
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

	@Override
	public String toString() {
		return "columnmapping [pkId=" + pkId + ", columnName=" + columnName + ", allocatedColumn_db="
				+ allocatedColumn_db + ", allocatedColumn_ui=" + allocatedColumn_ui + ", unit=" + unit + ", category="
				+ category + "]";
	}

	
	
	
}
