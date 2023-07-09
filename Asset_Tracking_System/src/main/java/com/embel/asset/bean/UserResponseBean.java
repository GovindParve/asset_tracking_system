package com.embel.asset.bean;

public class UserResponseBean {

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
	
	public UserResponseBean() {
		super();
	}

	public UserResponseBean(Long pkuserId, String username, String role, String firstName, String lastName,
			Long phoneNumber, String emailId, String password, String companyName, String address) {
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
	}

	@Override
	public String toString() {
		return "UserResponseBean [pkuserId=" + pkuserId + ", username=" + username + ", role=" + role + ", firstName="
				+ firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", emailId=" + emailId
				+ ", password=" + password + ", companyName=" + companyName + ", address=" + address + "]";
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
	
}
