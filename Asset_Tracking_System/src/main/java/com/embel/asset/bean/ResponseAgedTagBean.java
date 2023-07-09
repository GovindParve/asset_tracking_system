package com.embel.asset.bean;

public class ResponseAgedTagBean 
{
	private String agedTagName;

	public ResponseAgedTagBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseAgedTagBean(String agedTagName) {
		super();
		this.agedTagName = agedTagName;
	}

	public String getAgedTagName() {
		return agedTagName;
	}

	public void setAgedTagName(String agedTagName) {
		this.agedTagName = agedTagName;
	}

	@Override
	public String toString() {
		return "ResponseAgedTagBean [agedTagName=" + agedTagName + "]";
	}

	
}
