package com.embel.asset.bean;

import java.util.Date;

public class ResponseLoginHistoryBean {

	private Long historyId;
	private String loginIP;
	private Date loginDateTime;
	private String userName;
	private String userRole;
	private Long userId;
	
	public ResponseLoginHistoryBean() {
		super();
	}
	public ResponseLoginHistoryBean(Long historyId, String loginIP, Date loginDateTime, String userName,
			String userRole, Long userId) {
		super();
		this.historyId = historyId;
		this.loginIP = loginIP;
		this.loginDateTime = loginDateTime;
		this.userName = userName;
		this.userRole = userRole;
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ResponseLoginHistoryBean [historyId=" + historyId + ", loginIP=" + loginIP + ", loginDateTime="
				+ loginDateTime + ", userName=" + userName + ", userRole=" + userRole + ", userId=" + userId + "]";
	}
	public Long getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	public Date getLoginDateTime() {
		return loginDateTime;
	}
	public void setLoginDateTime(Date loginDateTime) {
		this.loginDateTime = loginDateTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
