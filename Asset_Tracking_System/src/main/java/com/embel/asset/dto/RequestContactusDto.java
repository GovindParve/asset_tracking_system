package com.embel.asset.dto;


public class RequestContactusDto
{
			private int contactid;
			private String contactname;
			private String contactnumber;
			private String email;
			private String description;
			private String category;
			private long fkUserId;
			public RequestContactusDto() {
				super();
				// TODO Auto-generated constructor stub
			}
			public RequestContactusDto(int contactid, String contactname, String contactnumber, String email,
					String description, String category, long fkUserId) {
				super();
				this.contactid = contactid;
				this.contactname = contactname;
				this.contactnumber = contactnumber;
				this.email = email;
				this.description = description;
				this.category = category;
				this.fkUserId = fkUserId;
			}
			public int getContactid() {
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
			public String getCategory() {
				return category;
			}
			public void setCategory(String category) {
				this.category = category;
			}
			public long getFkUserId() {
				return fkUserId;
			}
			public void setFkUserId(long fkUserId) {
				this.fkUserId = fkUserId;
			}
			@Override
			public String toString() {
				return "RequestContactusDto [contactid=" + contactid + ", contactname=" + contactname
						+ ", contactnumber=" + contactnumber + ", email=" + email + ", description=" + description
						+ ", category=" + category + ", fkUserId=" + fkUserId + "]";
			}
			

			
			
}
