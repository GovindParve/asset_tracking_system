package com.embel.asset.service;

import java.util.Date;

import org.apache.xmlbeans.impl.xb.ltgfmt.FileDesc.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.bean.ResponseDownlodeCountBean;
import com.embel.asset.entity.ExcelPDFDownloadCountReport;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.UserDownloadRepo;

@Service
public class UserDownlodService {

	@Autowired
	UserDownloadRepo userdownlodRepo;
	@Autowired
	EmployeUserRepository employeeUserRepo;

	public long findByDownloadCount() {

		return userdownlodRepo.findByDownlodingCount();
	}

	public void addDownlodingDetails(String fileType, String fkUserId, String role, Date date, long ldownloadcount) {

		ExcelPDFDownloadCountReport userdownload = new ExcelPDFDownloadCountReport();

		userdownload.setFileType(fileType);
		userdownload.setFkUserId(fkUserId);
		userdownload.setRole(role);

		userdownload.setTimeStamp(date);
		userdownload.setDwnldCount(ldownloadcount);

		userdownlodRepo.save(userdownload);

	}

	public ResponseDownlodeCountBean getDownlodeCount(String fkUserId, String role) {
		if (role.equals(CommonConstants.superAdmin)) {
			long count = userdownlodRepo.getdownlodeingCountforSuperAdmin();
			ResponseDownlodeCountBean bean = new ResponseDownlodeCountBean();
			bean.setDownlodeCount(count);
			String uName = userdownlodRepo.getdownlodeUserNameforSuperAdmin(Long.parseLong(fkUserId));
			bean.setUsername(uName);
			return bean;
		}
		if (role.equals(CommonConstants.organization)) {
			long count = userdownlodRepo.getdownlodeingCountforOrganization(Long.parseLong(fkUserId));
			ResponseDownlodeCountBean bean = new ResponseDownlodeCountBean();
			bean.setDownlodeCount(count);

			String uName = userdownlodRepo.getdownlodeUserNameforSupeUser(Long.parseLong(fkUserId));
			bean.setUsername(uName);
			return bean;

		}
		if (role.equals(CommonConstants.admin)) {
			long count = userdownlodRepo.getdownlodeingCountforSupeUser(Long.parseLong(fkUserId));
			ResponseDownlodeCountBean bean = new ResponseDownlodeCountBean();
			bean.setDownlodeCount(count);

			String uName = userdownlodRepo.getdownlodeUserNameforSupeUser(Long.parseLong(fkUserId));
			bean.setUsername(uName);
			return bean;

		}
		if (role.equals(CommonConstants.empUser)) {

			long adminId = employeeUserRepo.getAdminId(fkUserId);
			long count = userdownlodRepo.getdownlodeingCountforSupeUser(adminId);
			ResponseDownlodeCountBean bean = new ResponseDownlodeCountBean();
			bean.setDownlodeCount(count);

			String uName = userdownlodRepo.getdownlodeUserNameforSupeUser(adminId);
			bean.setUsername(uName);
			return bean;

		}
		return null;

	}

}
