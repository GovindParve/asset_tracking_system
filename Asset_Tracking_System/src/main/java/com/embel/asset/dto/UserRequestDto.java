package com.embel.asset.dto;

public class UserRequestDto {

	private Long pkuserId;
	private String username;
	private String role;
	private String firstName;
	private String lastName;
	private Long phoneNumber;
	private String emailId;
	private String password;
    private String companyName;
    private String address;
    private String organization;
	private String user;
	private String admin;
	private String createdby;
	private String category;
	private String status;
	public UserRequestDto() {
		super();
	}
	public UserRequestDto(Long pkuserId, String username, String role, String firstName, String lastName,
			Long phoneNumber, String emailId, String password, String companyName, String address, String organization,
			String user, String admin, String createdby, String category, String status) {
		super();
		this.pkuserId = pkuserId;
		this.username = username;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.password = password;
		this.companyName = companyName;
		this.address = address;
		this.organization = organization;
		this.user = user;
		this.admin = admin;
		this.createdby = createdby;
		this.category = category;
		this.status = status;
	}
	public Long getPkuserId() {
		return pkuserId;
	}
	public void setPkuserId(Long pkuserId) {
		this.pkuserId = pkuserId;
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
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "UserRequestDto [pkuserId=" + pkuserId + ", username=" + username + ", role=" + role + ", firstName="
				+ firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", emailId=" + emailId
				+ ", password=" + password + ", companyName=" + companyName + ", address=" + address + ", organization="
				+ organization + ", user=" + user + ", admin=" + admin + ", createdby=" + createdby + ", category="
				+ category + ", status=" + status + "]";
	}
	
	
	
}
