package com.embel.asset.bean;

public class ResponseDashbordCountbean
{
	private String totalAdmin;
	
	private long totalUser;
	
	private long totalEmpUser;
	
	private String totalGateways;
	
	private String totalTags;
	
	private String totalOrganigation;
	
	private long workingTags;
	
	private long nonWorkingTags;
	
	private long workingGateways;
	
	private long nonWorkingGateways;

	public ResponseDashbordCountbean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseDashbordCountbean(String totalAdmin, long totalUser, long totalEmpUser, String totalGateways,
			String totalTags, String totalOrganigation, long workingTags, long nonWorkingTags, long workingGateways,
			long nonWorkingGateways) {
		super();
		this.totalAdmin = totalAdmin;
		this.totalUser = totalUser;
		this.totalEmpUser = totalEmpUser;
		this.totalGateways = totalGateways;
		this.totalTags = totalTags;
		this.totalOrganigation = totalOrganigation;
		this.workingTags = workingTags;
		this.nonWorkingTags = nonWorkingTags;
		this.workingGateways = workingGateways;
		this.nonWorkingGateways = nonWorkingGateways;
	}

	public String getTotalAdmin() {
		return totalAdmin;
	}

	public void setTotalAdmin(String totalAdmin) {
		this.totalAdmin = totalAdmin;
	}

	public long getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(long totalUser) {
		this.totalUser = totalUser;
	}

	public long getTotalEmpUser() {
		return totalEmpUser;
	}

	public void setTotalEmpUser(long totalEmpUser) {
		this.totalEmpUser = totalEmpUser;
	}

	public String getTotalGateways() {
		return totalGateways;
	}

	public void setTotalGateways(String totalGateways) {
		this.totalGateways = totalGateways;
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

	public long getWorkingGateways() {
		return workingGateways;
	}

	public void setWorkingGateways(long workingGateways) {
		this.workingGateways = workingGateways;
	}

	public long getNonWorkingGateways() {
		return nonWorkingGateways;
	}

	public void setNonWorkingGateways(long nonWorkingGateways) {
		this.nonWorkingGateways = nonWorkingGateways;
	}

	@Override
	public String toString() {
		return "ResponseDashbordCountbean [totalAdmin=" + totalAdmin + ", totalUser=" + totalUser + ", totalEmpUser="
				+ totalEmpUser + ", totalGateways=" + totalGateways + ", totalTags=" + totalTags
				+ ", totalOrganigation=" + totalOrganigation + ", workingTags=" + workingTags + ", nonWorkingTags="
				+ nonWorkingTags + ", workingGateways=" + workingGateways + ", nonWorkingGateways=" + nonWorkingGateways
				+ "]";
	}

	
	
	
}
