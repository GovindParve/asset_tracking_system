package com.embel.asset.bean;

public class ResponseGetwayWorkingNonWorkingbean 
{
	private long NonWorkingGatwayCount;
	private long WorkingGatwayCount;
	public ResponseGetwayWorkingNonWorkingbean(long nonWorkingGatwayCount, long workingGatwayCount) {
		super();
		NonWorkingGatwayCount = nonWorkingGatwayCount;
		WorkingGatwayCount = workingGatwayCount;
	}
	public ResponseGetwayWorkingNonWorkingbean() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ResponseGetwayWorkingNonWorkingbean [NonWorkingGatwayCount=" + NonWorkingGatwayCount
				+ ", WorkingGatwayCount=" + WorkingGatwayCount + "]";
	}
	public long getNonWorkingGatwayCount() {
		return NonWorkingGatwayCount;
	}
	public void setNonWorkingGatwayCount(long nonWorkingGatwayCount) {
		NonWorkingGatwayCount = nonWorkingGatwayCount;
	}
	public long getWorkingGatwayCount() {
		return WorkingGatwayCount;
	}
	public void setWorkingGatwayCount(long workingGatwayCount) {
		WorkingGatwayCount = workingGatwayCount;
	}
	
	
	
	
}
