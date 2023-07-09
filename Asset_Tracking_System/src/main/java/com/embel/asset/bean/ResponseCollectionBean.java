package com.embel.asset.bean;

import java.util.Arrays;

public class ResponseCollectionBean {

	private String[] gatewayNames;
	private Integer[] totalTagCount;
	private Integer[] agedTagCount;
	private Integer[] newTagCount;
	
	public ResponseCollectionBean() {
		super();
	}

	public ResponseCollectionBean(String[] gatewayNames, Integer[] totalTagCount, Integer[] agedTagCount,Integer[] newTagCount) {
		super();
		this.gatewayNames = gatewayNames;
		this.totalTagCount = totalTagCount;
		this.agedTagCount = agedTagCount;
		this.newTagCount = newTagCount;
	}

	public ResponseCollectionBean(String[] gatewayNames, Integer[] agedTagCount, Integer[] newTagCount) {
		super();
		this.gatewayNames = gatewayNames;
		this.agedTagCount = agedTagCount;
		this.newTagCount = newTagCount;
	}

	@Override
	public String toString() {
		return "ResponseCollectionBean [gatewayNames=" + Arrays.toString(gatewayNames) + ", totalTagCount="
				+ Arrays.toString(totalTagCount) + ", agedTagCount=" + Arrays.toString(agedTagCount) + ", newTagCount="
				+ Arrays.toString(newTagCount) + "]";
	}

	public String[] getGatewayNames() {
		return gatewayNames;
	}

	public void setGatewayNames(String[] gatewayNames) {
		this.gatewayNames = gatewayNames;
	}

	public Integer[] getTotalTagCount() {
		return totalTagCount;
	}

	public void setTotalTagCount(Integer[] totalTagCount) {
		this.totalTagCount = totalTagCount;
	}

	public Integer[] getAgedTagCount() {
		return agedTagCount;
	}

	public void setAgedTagCount(Integer[] agedTagCount) {
		this.agedTagCount = agedTagCount;
	}

	public Integer[] getNewTagCount() {
		return newTagCount;
	}

	public void setNewTagCount(Integer[] newTagCount) {
		this.newTagCount = newTagCount;
	}

}
