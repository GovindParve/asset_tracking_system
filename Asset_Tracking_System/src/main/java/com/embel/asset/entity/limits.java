package com.embel.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="limits")
public class limits 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long limitid;
	
	@Column(name="hrs_limits_nonworking_tag")
	private long hLimitForNonWorkingTag;
	
	@Column(name="hrs_limits_nonworking_gateway")
	private long hLimitForNonWorkingGateway;
	
	@Column(name="days_limits_aged_and_recent_tag")
	private long daysLimitAgedAndRecentTag;
	
	@Column(name="admin_id")
	private Long FkadminId;

	public long getLimitid() {
		return limitid;
	}

	public void setLimitid(long limitid) {
		this.limitid = limitid;
	}

	public long gethLimitForNonWorkingTag() {
		return hLimitForNonWorkingTag;
	}

	public void sethLimitForNonWorkingTag(long hLimitForNonWorkingTag) {
		this.hLimitForNonWorkingTag = hLimitForNonWorkingTag;
	}

	public long gethLimitForNonWorkingGateway() {
		return hLimitForNonWorkingGateway;
	}

	public void sethLimitForNonWorkingGateway(long hLimitForNonWorkingGateway) {
		this.hLimitForNonWorkingGateway = hLimitForNonWorkingGateway;
	}

	public long getDaysLimitAgedAndRecentTag() {
		return daysLimitAgedAndRecentTag;
	}

	public void setDaysLimitAgedAndRecentTag(long daysLimitAgedAndRecentTag) {
		this.daysLimitAgedAndRecentTag = daysLimitAgedAndRecentTag;
	}

	public Long getFkadminId() {
		return FkadminId;
	}

	public void setFkadminId(Long fkadminId) {
		FkadminId = fkadminId;
	}

	public limits() {
		super();
		// TODO Auto-generated constructor stub
	}

	public limits(long limitid, long hLimitForNonWorkingTag, long hLimitForNonWorkingGateway,
			long daysLimitAgedAndRecentTag, Long fkadminId) {
		super();
		this.limitid = limitid;
		this.hLimitForNonWorkingTag = hLimitForNonWorkingTag;
		this.hLimitForNonWorkingGateway = hLimitForNonWorkingGateway;
		this.daysLimitAgedAndRecentTag = daysLimitAgedAndRecentTag;
		FkadminId = fkadminId;
	}

	@Override
	public String toString() {
		return "limits [limitid=" + limitid + ", hLimitForNonWorkingTag=" + hLimitForNonWorkingTag
				+ ", hLimitForNonWorkingGateway=" + hLimitForNonWorkingGateway + ", daysLimitAgedAndRecentTag="
				+ daysLimitAgedAndRecentTag + ", FkadminId=" + FkadminId + "]";
	}

	
	
	
	
}
