package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_login_history")
public class UserLoginHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long historyId;
	
	@Column(name = "login_ip")
	private String loginIP;
	
	@Column(name = "login_date_and_time")
	private Date loginDateTime;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_role")
	private String userRole;
	
	@Column(name = "user_id")
	private Long userId;

	public UserLoginHistory() {
		super();
	}

	public UserLoginHistory(Long historyId, String loginIP, Date loginDateTime, String userName, String userRole,
			Long userId) {
		super();
		this.historyId = historyId;
		this.loginIP = loginIP;
		this.loginDateTime = loginDateTime;
		this.userName = userName;
		this.userRole = userRole;
		this.userId = userId;
	}

	public UserLoginHistory(Date loginDateTime) {
		super();
		this.loginDateTime = loginDateTime;
	}

	@Override
	public String toString() {
		return "UserLoginHistory [historyId=" + historyId + ", loginIP=" + loginIP + ", loginDateTime=" + loginDateTime
				+ ", userName=" + userName + ", userRole=" + userRole + ", userId=" + userId + "]";
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
