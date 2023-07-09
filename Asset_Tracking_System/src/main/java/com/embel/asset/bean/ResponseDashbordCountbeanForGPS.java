package com.embel.asset.bean;

public class ResponseDashbordCountbeanForGPS 
{
	private String totalAdmin;
	
	private String totalUser;
	
	private String totalTags;
	
	private String totalOrganigation;
	
	private long workingTags;
	
	private long nonWorkingTags;

	public ResponseDashbordCountbeanForGPS() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseDashbordCountbeanForGPS(String totalAdmin, String totalUser, String totalTags,
			String totalOrganigation, long workingTags, long nonWorkingTags) {
		super();
		this.totalAdmin = totalAdmin;
		this.totalUser = totalUser;
		this.totalTags = totalTags;
		this.totalOrganigation = totalOrganigation;
		this.workingTags = workingTags;
		this.nonWorkingTags = nonWorkingTags;
	}

	public String getTotalAdmin() {
		return totalAdmin;
	}

	public void setTotalAdmin(String totalAdmin) {
		this.totalAdmin = totalAdmin;
	}

	public String getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(String totalUser) {
		this.totalUser = totalUser;
	}

	public String getTotalTags() {
		return totalTags;
	}

	public void setTotalTags(String totalTags) {
		this.totalTags = totalTags;
	}

	public String getTotalOrganigation() {
		return totalOrganigation;
	}

	public void setTotalOrganigation(String totalOrganigation) {
		this.totalOrganigation = totalOrganigation;
	}

	public long getWorkingTags() {
		return workingTags;
	}

	public void setWorkingTags(long workingTags) {
		this.workingTags = workingTags;
	}

	public long getNonWorkingTags() {
		return nonWorkingTags;
	}

	public void setNonWorkingTags(long nonWorkingTags) {
		this.nonWorkingTags = nonWorkingTags;
	}

	@Override
	public String toString() {
		return "ResponseDashbordCountbeanForGPS [totalAdmin=" + totalAdmin + ", totalUser=" + totalUser + ", totalTags="
				+ totalTags + ", totalOrganigation=" + totalOrganigation + ", workingTags=" + workingTags
				+ ", nonWorkingTags=" + nonWorkingTags + "]";
	}
	
	
}
