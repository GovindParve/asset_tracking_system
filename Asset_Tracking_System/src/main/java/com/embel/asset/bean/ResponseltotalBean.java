package com.embel.asset.bean;

public class ResponseltotalBean 
{
	long lTotal;

	public long getlTotal() {
		return lTotal;
	}

	public void setlTotal(long lTotal) {
		this.lTotal = lTotal;
	}

	public ResponseltotalBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseltotalBean(long lTotal) {
		super();
		this.lTotal = lTotal;
	}

	@Override
	public String toString() {
		return "ResponseltotalBean [lTotal=" + lTotal + "]";
	}
	

}
