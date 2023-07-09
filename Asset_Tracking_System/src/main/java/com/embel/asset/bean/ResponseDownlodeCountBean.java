package com.embel.asset.bean;

public class ResponseDownlodeCountBean
{
	private long downlodeCount;
	private String username;
	public long getDownlodeCount() {
		return downlodeCount;
	}
	public void setDownlodeCount(long downlodeCount) {
		this.downlodeCount = downlodeCount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ResponseDownlodeCountBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseDownlodeCountBean(long downlodeCount, String username) {
		super();
		this.downlodeCount = downlodeCount;
		this.username = username;
	}
	@Override
	public String toString() {
		return "ResponseDownlodeCountBean [downlodeCount=" + downlodeCount + ", username=" + username + "]";
	}
	
	


}
