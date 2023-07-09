package com.embel.asset.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.sun.istack.NotNull;

@Entity
@Table(name = "contact_us")
public class Contactus 
{

	//@Min(value = 1, message = "Id can't be less than 1 or bigger than 999999")
//	@Max(999999)
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long contactid;
	
//	@Size(max = 100)
//	@NotNull
	@Column
	private String contactname;
//	@NotNull
	@Column
	private String contactnumber;
	
	private long fkUserId;
//	@Email
//	@NotNull
	@Column
	private String email;
	
	@Column
	private String description;
	
	private String category;

	public Contactus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contactus(long contactid, String contactname, String contactnumber, long fkUserId, String email,
			String description, String category) {
		super();
		this.contactid = contactid;
		this.contactname = contactname;
		this.contactnumber = contactnumber;
		this.fkUserId = fkUserId;
		this.email = email;
		this.description = description;
		this.category = category;
	}

	public long getContactid() {
		return contactid;
	}

	public void setContactid(long contactid) {
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

	public long getFkUserId() {
		return fkUserId;
	}

	public void setFkUserId(long fkUserId) {
		this.fkUserId = fkUserId;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Contactus [contactid=" + contactid + ", contactname=" + contactname + ", contactnumber=" + contactnumber
				+ ", fkUserId=" + fkUserId + ", email=" + email + ", description=" + description + ", category="
				+ category + "]";
	}

	

}
