package com.embel.asset.bean;

public class UserInfobean 
{
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
	private String category;
	private String tagCount;
	private String gatewayCount;
	private String status;
	private String createdby;
	public UserInfobean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfobean(Long pkuserId, String username, String role, String firstName, String lastName,
			Long phoneNumber, String emailId, String password, String companyName, String address, String organization,
			String user, String admin, String category, String tagCount, String gatewayCount, String status,
			String createdby) {
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
		this.category = category;
		this.tagCount = tagCount;
		this.gatewayCount = gatewayCount;
		this.status = status;
		this.createdby = createdby;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTagCount() {
		return tagCount;
	}
	public void setTagCount(String tagCount) {
		this.tagCount = tagCount;
	}
	public String getGatewayCount() {
		return gatewayCount;
	}
	public void setGatewayCount(String gatewayCount) {
		this.gatewayCount = gatewayCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	@Override
	public String toString() {
		return "UserInfobean [pkuserId=" + pkuserId + ", username=" + username + ", role=" + role + ", firstName="
				+ firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", emailId=" + emailId
				+ ", password=" + password + ", companyName=" + companyName + ", address=" + address + ", organization="
				+ organization + ", user=" + user + ", admin=" + admin + ", category=" + category + ", tagCount="
				+ tagCount + ", gatewayCount=" + gatewayCount + ", status=" + status + ", createdby=" + createdby + "]";
	}
	
	
}
