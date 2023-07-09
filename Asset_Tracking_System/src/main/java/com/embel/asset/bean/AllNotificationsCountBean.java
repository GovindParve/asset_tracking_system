package com.embel.asset.bean;

public class AllNotificationsCountBean 
{

	
private long allNotifyCount;
	
	
	public AllNotificationsCountBean() {
		super();
	
	}


	public AllNotificationsCountBean(long allNotifyCount) {
		super();
		this.allNotifyCount = allNotifyCount;
	}


	public long getAllNotifyCount() {
		return allNotifyCount;
	}


	public void setAllNotifyCount(long allNotifyCount) {
		this.allNotifyCount = allNotifyCount;
	}


	@Override
	public String toString() {
		return "AllNotificationsCountBean [allNotifyCount=" + allNotifyCount + "]";
	}

}
