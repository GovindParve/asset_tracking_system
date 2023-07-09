package com.embel.asset.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="Excel_PDF_Download_Count_Report")
public class ExcelPDFDownloadCountReport {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long pkDownloadId;
	
	private String fileType;
	private String fkUserId;	
	private String role;
	private Date timeStamp;
	private long dwnldCount;
	
	public ExcelPDFDownloadCountReport() {
		super();
	}

	public ExcelPDFDownloadCountReport(long pkDownloadId, String fileType, String fkUserId, String role, Date timeStamp,
			long dwnldCount) {
		super();
		this.pkDownloadId = pkDownloadId;
		this.fileType = fileType;
		this.fkUserId = fkUserId;
		this.role = role;
		this.timeStamp = timeStamp;
		this.dwnldCount = dwnldCount;
	}

	public long getPkDownloadId() {
		return pkDownloadId;
	}

	public void setPkDownloadId(long pkDownloadId) {
		this.pkDownloadId = pkDownloadId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFkUserId() {
		return fkUserId;
	}

	public void setFkUserId(String fkUserId) {
		this.fkUserId = fkUserId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getDwnldCount() {
		return dwnldCount;
	}

	public void setDwnldCount(long dwnldCount) {
		this.dwnldCount = dwnldCount;
	}

	@Override
	public String toString() {
		return "ExcelPDFDownloadCountReport [pkDownloadId=" + pkDownloadId + ", fileType=" + fileType + ", fkUserId="
				+ fkUserId + ", role=" + role + ", timeStamp=" + timeStamp + ", dwnldCount=" + dwnldCount + "]";
	}

	
	
	
}