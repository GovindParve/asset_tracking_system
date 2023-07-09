package com.embel.asset.bean;

import java.util.Date;

public class ResponseLowBatteryListBean 
{
	
   private long TotalCount;
   private String Status;
   
public ResponseLowBatteryListBean() {
	super();
	// TODO Auto-generated constructor stub
}

public ResponseLowBatteryListBean(long totalCount, String status) {
	super();
	TotalCount = totalCount;
	Status = status;
}

public long getTotalCount() {
	return TotalCount;
}

public void setTotalCount(long totalCount) {
	TotalCount = totalCount;
}

public String getStatus() {
	return Status;
}

public void setStatus(String status) {
	Status = status;
}

@Override
public String toString() {
	return "ResponseLowBatteryListBean [TotalCount=" + TotalCount + ", Status=" + Status + "]";
}
	

		
}
