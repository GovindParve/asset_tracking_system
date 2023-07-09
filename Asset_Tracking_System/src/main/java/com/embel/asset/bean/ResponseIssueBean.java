package com.embel.asset.bean;


public class ResponseIssueBean 
{
	
	private long issueid;
	
	
	private String userName;
	

	private long FkUserId;

	private long contact;
	
	private String mailId;
	

	private String issue;


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


	public ResponseIssueBean(long issueid, String userName, long fkUserId, long contact, String mailId, String issue) {
		super();
		this.issueid = issueid;
		this.userName = userName;
		FkUserId = fkUserId;
		this.contact = contact;
		this.mailId = mailId;
		this.issue = issue;
	}


	public ResponseIssueBean() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "ResponseIssueBean [issueid=" + issueid + ", userName=" + userName + ", FkUserId=" + FkUserId
				+ ", contact=" + contact + ", mailId=" + mailId + ", issue=" + issue + "]";
	}


}
