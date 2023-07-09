package com.embel.asset.bean;

public class ResponseAssetTagWorkingNonWorkinCountBean
{
	long AssetTagWorkingCount;
	long AssetTagNonWorkingCount;
	public ResponseAssetTagWorkingNonWorkinCountBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseAssetTagWorkingNonWorkinCountBean(long assetTagWorkingCount, long assetTagNonWorkingCount) {
		super();
		AssetTagWorkingCount = assetTagWorkingCount;
		AssetTagNonWorkingCount = assetTagNonWorkingCount;
	}
	public long getAssetTagWorkingCount() {
		return AssetTagWorkingCount;
	}
	public void setAssetTagWorkingCount(long assetTagWorkingCount) {
		AssetTagWorkingCount = assetTagWorkingCount;
	}
	public long getAssetTagNonWorkingCount() {
		return AssetTagNonWorkingCount;
	}
	public void setAssetTagNonWorkingCount(long assetTagNonWorkingCount) {
		AssetTagNonWorkingCount = assetTagNonWorkingCount;
	}
	@Override
	public String toString() {
		return "ResponseAssetTagWorkingNonWorkinCountBean [AssetTagWorkingCount=" + AssetTagWorkingCount
				+ ", AssetTagNonWorkingCount=" + AssetTagNonWorkingCount + "]";
	}
	
	
	

}
