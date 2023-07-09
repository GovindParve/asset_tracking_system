package com.embel.asset.bean;

public class ResponseTagFromTrackingBean {

	private Long tagId;
	private String tagName;
	
	public ResponseTagFromTrackingBean() {
		super();
	}
	
	public ResponseTagFromTrackingBean(Long tagId, String tagName) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
	}
	
	@Override
	public String toString() {
		return "ResponseTagFromTrackingBean [tagId=" + tagId + ", tagName=" + tagName + "]";
	}
	
	public Long getTagId() {
		return tagId;
	}
	
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	
	public String getTagName() {
		return tagName;
	}
	
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}
