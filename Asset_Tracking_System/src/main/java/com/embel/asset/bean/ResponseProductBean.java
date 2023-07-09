package com.embel.asset.bean;

public class ResponseProductBean {

	private Long productId;
	private String productName;
	
	public ResponseProductBean() {
		super();
	}
	
	public ResponseProductBean(Long productId, String productName) {
		super();
		this.productId = productId;
		this.productName = productName;
	}
	
	@Override
	public String toString() {
		return "ResponseProductBean [productId=" + productId + ", productName=" + productName + "]";
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
