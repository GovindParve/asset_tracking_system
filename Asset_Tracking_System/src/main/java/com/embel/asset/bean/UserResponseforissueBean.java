package com.embel.asset.bean;

public class UserResponseforissueBean 
{
	private Long pkUserDetails; 	
	private String firstname;	
	private String lastname;	
	private String email;	
	private Long phoneNumber;
	public UserResponseforissueBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserResponseforissueBean(Long pkUserDetails, String firstname, String lastname, String email,
			Long phoneNumber) {
		super();
		this.pkUserDetails = pkUserDetails;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	public Long getPkUserDetails() {
		return pkUserDetails;
	}
	public void setPkUserDetails(Long pkUserDetails) {
		this.pkUserDetails = pkUserDetails;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "UserResponseforissueBean [pkUserDetails=" + pkUserDetails + ", firstname=" + firstname + ", lastname="
				+ lastname + ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
	}
	
	
}
