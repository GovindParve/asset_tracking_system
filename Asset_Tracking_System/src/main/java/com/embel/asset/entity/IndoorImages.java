package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="indoor_image")
public class IndoorImages
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long indoorImageId;
	
	@Column(name = "user_id")
	private long fkadminId;
	
	
	@Column(name = "image")
	private String image;


	public IndoorImages() {
		super();
		// TODO Auto-generated constructor stub
	}


	public IndoorImages(long indoorImageId, long fkadminId, String image) {
		super();
		this.indoorImageId = indoorImageId;
		this.fkadminId = fkadminId;
		this.image = image;
	}


	@Override
	public String toString() {
		return "IndoorImages [indoorImageId=" + indoorImageId + ", fkadminId=" + fkadminId + ", image=" + image + "]";
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
