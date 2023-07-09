package com.embel.asset.dto;

public class IndoorImageDto
{
	
	private long indoorImageId;
	private long fkadminId;
	private String image;
	public IndoorImageDto(long indoorImageId, long fkadminId, String image) {
		super();
		this.indoorImageId = indoorImageId;
		this.fkadminId = fkadminId;
		this.image = image;
	}
	public IndoorImageDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getIndoorImageId() {
		return indoorImageId;
	}
	public void setIndoorImageId(long indoorImageId) {
		this.indoorImageId = indoorImageId;
	}
	public long getFkadminId() {
		return fkadminId;
	}
	public void setFkadminId(long fkadminId) {
		this.fkadminId = fkadminId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}


	

}
