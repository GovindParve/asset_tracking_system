package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class AssetTestjson 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String gSrNo;
	private String gMacId;
	private String tName;
	private String tMacId;
	private String bSN;
	private String batStatus;
	public AssetTestjson(int id, String gSrNo, String gMacId, String tName, String tMacId, String bSN,
			String batStatus) {
		super();
		this.id = id;
		this.gSrNo = gSrNo;
		this.gMacId = gMacId;
		this.tName = tName;
		this.tMacId = tMacId;
		this.bSN = bSN;
		this.batStatus = batStatus;
	}
	public AssetTestjson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getgSrNo() {
		return gSrNo;
	}
	public void setgSrNo(String gSrNo) {
		this.gSrNo = gSrNo;
	}
	public String getgMacId() {
		return gMacId;
	}
	public void setgMacId(String gMacId) {
		this.gMacId = gMacId;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String gettMacId() {
		return tMacId;
	}
	public void settMacId(String tMacId) {
		this.tMacId = tMacId;
	}
	public String getbSN() {
		return bSN;
	}
	public void setbSN(String bSN) {
		this.bSN = bSN;
	}
	public String getBatStatus() {
		return batStatus;
	}
	public void setBatStatus(String batStatus) {
		this.batStatus = batStatus;
	}
	
	
	

}
