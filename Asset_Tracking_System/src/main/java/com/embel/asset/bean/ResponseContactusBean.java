package com.embel.asset.bean;

public class ResponseContactusBean 
{
	private long contactid;
	
	private String contactname;
	
	private String contactnumber;
	
	private String email;
	
	private String description;
	
	private long fkUserId;
	
	
	public long getContactid() {
		return contactid;
	}

	public void setContactid(int contactid) {
		this.contactid = contactid;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public long getFkUserId() {
		return fkUserId;
	}

	public void setFkUserId(long fkUserId) {
		this.fkUserId = fkUserId;
	}

	public ResponseContactusBean(long contactid, String contactname, String contactnumber, String email,
			String description, long fkUserId) {
		super();
		this.contactid = contactid;
		this.contactname = contactname;
		this.contactnumber = contactnumber;
		this.email = email;
		this.description = description;
		this.fkUserId = fkUserId;
	}

	public ResponseContactusBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ResponseContactusBean [contactid=" + contactid + ", contactname=" + contactname + ", contactnumber="
				+ contactnumber + ", email=" + email + ", description=" + description + ", fkUserId=" + fkUserId + "]";
	}

	

}
