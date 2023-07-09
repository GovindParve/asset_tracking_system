package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
;

@Entity
@Table(name="issue_details")
public class Issue
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long issueid;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="user_id")
	private Long FkUserId;
	
	@Column(name="contact")
	private long contact;
	
	@Column(name="mailid")
	private String mailId;
	
	@Column(name="issue")
	private String issue;
	
	
	@Column(name="category")
	private String category;
	
	@Column(name="status")
	private String status;

	public Issue() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Issue(long issueid, String userName, Long fkUserId, long contact, String mailId, String issue,
			String category, String status) {
		super();
		this.issueid = issueid;
		this.userName = userName;
		FkUserId = fkUserId;
		this.contact = contact;
		this.mailId = mailId;
		this.issue = issue;
		this.category = category;
		this.status = status;
	}

	public long getIssueid() {
		return issueid;
	}

	public void setIssueid(long issueid) {
		this.issueid = issueid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getFkUserId() {
		return FkUserId;
	}

	public void setFkUserId(Long fkUserId) {
		FkUserId = fkUserId;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
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
		return "Issue [issueid=" + issueid + ", userName=" + userName + ", FkUserId=" + FkUserId + ", contact="
				+ contact + ", mailId=" + mailId + ", issue=" + issue + ", category=" + category + ", status=" + status
				+ "]";
	}

	

}
