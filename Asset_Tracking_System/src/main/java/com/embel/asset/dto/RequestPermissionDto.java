package com.embel.asset.dto;

public class RequestPermissionDto {
	
	private Long pkuserId;
	private String username;
	private String role;
	private String firstName;
	private String lastName;
	private String category;
	private String organization;
	
	public RequestPermissionDto() {
		super();
	}

	public RequestPermissionDto(Long pkuserId, String username, String role, String firstName, String lastName,
			String category, String organization) {
		super();
		this.pkuserId = pkuserId;
		this.username = username;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.category = category;
		this.organization = organization;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		return "RequestPermissionDto [pkuserId=" + pkuserId + ", username=" + username + ", role=" + role
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", category=" + category + ", organization="
				+ organization + "]";
	}

	
	
}
