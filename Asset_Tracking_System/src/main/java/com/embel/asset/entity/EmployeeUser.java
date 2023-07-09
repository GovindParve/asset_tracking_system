package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employe_user_details")
public class EmployeeUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pkuserId;
	
	
	@Column(name ="user_id")
	private Long fkuserId;
	
	@Column(name ="username")
	private String username;
	
	@Column(name ="user_role")
	private String role;
	
	@Column(name ="first_name")
	private String firstName;
	
	@Column(name ="last_name")
	private String lastName;
	
	@Column(name ="phone_number")
	private Long phoneNumber;
	
	@Column(name = "e_mail_id")
	private String emailId;
	
	@Column(name ="password")
	private String password;

	@Column(name ="company_name")
	private String companyName;
	
	@Column(name = "user_address")
	private String address;
	
	@Column(name = "reset_password_token")
	private String resetPasswordToken;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "status")
	private String status;
	
	@Column(name = "organization")
	private String organization;

	@Column(name = "admin")
	private String admin;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "createdby")
	private String createdby;
	
	public EmployeeUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmployeeUser(Long pkuserId, Long fkuserId, String username, String role, String firstName, String lastName,
			Long phoneNumber, String emailId, String password, String companyName, String address,
			String resetPasswordToken, String accessToken, String refreshToken, String status, String organization,
			String admin, String category, String createdby) {
		super();
		this.pkuserId = pkuserId;
		this.fkuserId = fkuserId;
		this.username = username;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.password = password;
		this.companyName = companyName;
		this.address = address;
		this.resetPasswordToken = resetPasswordToken;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.status = status;
		this.organization = organization;
		this.admin = admin;
		this.category = category;
		this.createdby = createdby;
	}
	public Long getPkuserId() {
		return pkuserId;
	}
	public void setPkuserId(Long pkuserId) {
		this.pkuserId = pkuserId;
	}
	public Long getFkuserId() {
		return fkuserId;
	}
	public void setFkuserId(Long fkuserId) {
		this.fkuserId = fkuserId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}
	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	@Override
	public String toString() {
		return "EmployeeUser [pkuserId=" + pkuserId + ", fkuserId=" + fkuserId + ", username=" + username + ", role="
				+ role + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", emailId=" + emailId + ", password=" + password + ", companyName=" + companyName + ", address="
				+ address + ", resetPasswordToken=" + resetPasswordToken + ", accessToken=" + accessToken
				+ ", refreshToken=" + refreshToken + ", status=" + status + ", organization=" + organization
				+ ", admin=" + admin + ", category=" + category + ", createdby=" + createdby + "]";
	}

	

}
