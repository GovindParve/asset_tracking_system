package com.embel.asset.dto;

public class RequestIssueDto 
{

	private long issueid;
	

	private String userName;
	
	
	private Long fkUserId;

	
	private long contact;
	
	
	private String mailId;
	
	private String category;
	private String issue;

	private String status;

	public RequestIssueDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestIssueDto(long issueid, String userName, Long fkUserId, long contact, String mailId, String category,
			String issue, String status) {
		super();
		this.issueid = issueid;
		this.userName = userName;
		this.fkUserId = fkUserId;
		this.contact = contact;
		this.mailId = mailId;
		this.category = category;
		this.issue = issue;
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
		return fkUserId;
	}

	public void setFkUserId(Long fkUserId) {
		this.fkUserId = fkUserId;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RequestIssueDto [issueid=" + issueid + ", userName=" + userName + ", fkUserId=" + fkUserId
				+ ", contact=" + contact + ", mailId=" + mailId + ", category=" + category + ", issue=" + issue
				+ ", status=" + status + "]";
	}

	
}
