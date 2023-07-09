package com.embel.asset.bean;

import java.util.List;

public class ResponseColumnMappingbean
{
	private String columnName;
	private String allocatedColumn_ui;
	private String unit;
	public ResponseColumnMappingbean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseColumnMappingbean(String columnName, String allocatedColumn_ui, String unit) {
		super();
		this.columnName = columnName;
		this.allocatedColumn_ui = allocatedColumn_ui;
		this.unit = unit;
	}
	public ResponseColumnMappingbean(String columnName, String allocatedColumn_ui) {
		
		this.columnName = columnName;
		this.allocatedColumn_ui = allocatedColumn_ui;
		this.unit = "N/A";
	}
	@Override
	public String toString() {
		return "ResponseColumnMappingbean [columnName=" + columnName + ", allocatedColumn_ui=" + allocatedColumn_ui
				+ ", unit=" + unit + "]";
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
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
	
	
	
	
}
