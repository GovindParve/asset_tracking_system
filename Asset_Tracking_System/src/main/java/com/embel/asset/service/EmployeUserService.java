package com.embel.asset.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.embel.asset.entity.EmployeeUser;
import com.embel.asset.entity.UserDetail;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.helper.EmployeeUserReportForSuperAdminHelper;
import com.embel.asset.helper.GenerateEmpUserPDF;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.UserRepository;

@Service
public class EmployeUserService {
	@Autowired
	EmployeUserRepository employeeuserRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepo;

	/**
	 * use to add EmployeUser
	 * 
	 * @author Pratik chaudhari
	 * @param employeeuser
	 * @return String
	 */
	public String addEmployeUser(EmployeeUser employeeuser) {
		EmployeeUser dbData = null;
		UserDetail userdbData = null;
		long adminid = userRepo.getAdminId(employeeuser.getAdmin());
		String organization = userRepo.getorganizationId(adminid);
		try {
			dbData = employeeuserRepo.getByEmailId(employeeuser.getEmailId());
			userdbData = userRepo.getemailinfo(employeeuser.getEmailId());
			if (dbData == null && userdbData == null) {
				EmployeeUser userDet = new EmployeeUser();

				userDet.setAdmin(employeeuser.getAdmin());
				userDet.setFirstName(employeeuser.getFirstName());
				userDet.setLastName(employeeuser.getLastName());
				userDet.setEmailId(employeeuser.getEmailId());
				userDet.setPhoneNumber(employeeuser.getPhoneNumber());
				userDet.setPassword(passwordEncoder.encode(employeeuser.getPassword()));
				userDet.setRole(employeeuser.getRole());
				userDet.setUsername(employeeuser.getUsername());
				userDet.setCompanyName(employeeuser.getCompanyName());
				userDet.setFkuserId(adminid);// admin
				userDet.setStatus("Active");
				userDet.setCreatedby(employeeuser.getCreatedby());
				if (employeeuser.getAddress().equals("")) {
					userDet.setAddress("N/A");
				} else {
					userDet.setAddress(employeeuser.getAddress());
				}

				userDet.setCategory(employeeuser.getCategory());
				userDet.setOrganization(organization);
				employeeuserRepo.save(userDet);
				return "User Created ";
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		if (dbData.getEmailId().equals(employeeuser.getEmailId())
				|| dbData.getUsername().equals(employeeuser.getUsername())) {
			return " User Alrady Exits ";
		} else {
			EmployeeUser userDet = new EmployeeUser();

			userDet.setFirstName(employeeuser.getFirstName());
			userDet.setLastName(employeeuser.getLastName());
			userDet.setEmailId(employeeuser.getEmailId());
			userDet.setPhoneNumber(employeeuser.getPhoneNumber());
			userDet.setPassword(passwordEncoder.encode(employeeuser.getPassword()));
			userDet.setRole(employeeuser.getRole());
			userDet.setUsername(employeeuser.getUsername());
			userDet.setCompanyName(employeeuser.getCompanyName());
			userDet.setFkuserId(employeeuser.getFkuserId());
			userDet.setCategory(employeeuser.getCategory());
			userDet.setFkuserId(adminid);
			userDet.setAdmin(employeeuser.getAdmin());
			userDet.setStatus("Active");
			userDet.setCreatedby(employeeuser.getCreatedby());
			if (employeeuser.getAddress().equals("")) {
				userDet.setAddress("N/A");
			} else {
				userDet.setAddress(employeeuser.getAddress());
			}
			userDet.setAddress(employeeuser.getAddress());
			userDet.setOrganization(organization);
			employeeuserRepo.save(userDet);
			return "User Created ";
		}

	}

	/**
	 * use to get All EmployeUser
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return EmployeeUser
	 */
	public Page<EmployeeUser> getAllEmployeUser(String fkUserId, String role, String category, Pageable pageable) {
		long fkid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return employeeuserRepo.getAllListForSuperAdmin(pageable, category);
		}
		if (role.equals(CommonConstants.admin)) {
			return employeeuserRepo.getALLListForAdmin(fkUserId, category, pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			String getuserName = userRepo.getUserNameOfOrganization(fkUserId);
			List<UserDetail> AdminListobj = userRepo.getAlladminListforOrganization(getuserName);
			List<EmployeeUser> list1 = new ArrayList<EmployeeUser>();
			System.out.println("uobj" + AdminListobj);
			int sizetemp = 0;
			for (int i = 0; i < AdminListobj.size(); i++) {
				System.out.println("size:" + AdminListobj.size());
				List<EmployeeUser> eobj = employeeuserRepo.getALLListForOrganization(AdminListobj.get(i).getPkuserId(),
						category);
				System.out.println("eobj:" + eobj);
				int count = 0;
				for (int j = 0; j < eobj.size(); j++) {
					EmployeeUser emplist = new EmployeeUser();
					emplist.setFirstName(eobj.get(j).getFirstName());
					emplist.setLastName(eobj.get(j).getLastName());
					emplist.setEmailId(eobj.get(j).getEmailId());
					emplist.setFkuserId(eobj.get(j).getFkuserId());
					emplist.setAddress(eobj.get(j).getAddress());
					emplist.setCategory(eobj.get(j).getCategory());
					emplist.setPhoneNumber(eobj.get(j).getPhoneNumber());
					emplist.setPkuserId(eobj.get(j).getPkuserId());
					emplist.setUsername(eobj.get(j).getUsername());
					emplist.setPassword(eobj.get(j).getPassword());
					emplist.setStatus(eobj.get(j).getStatus());
					emplist.setAccessToken(eobj.get(j).getAccessToken());
					emplist.setRefreshToken(eobj.get(j).getRefreshToken());
					emplist.setRole(eobj.get(j).getRole());
					emplist.setCreatedby(eobj.get(j).getCreatedby());
					emplist.setCompanyName(eobj.get(j).getCompanyName());
					System.out.println("userNmae" + eobj.get(j).getUsername());
					list1.add(sizetemp, emplist);
					count++;
				}
				sizetemp = sizetemp + count;

			}
			final int start = (int) pageable.getOffset();
			final int end = Math.min((start + pageable.getPageSize()), list1.size());
			final Page<EmployeeUser> page = new PageImpl<>(list1.subList(start, end), pageable, list1.size());

			return page;

		}
		return null;

	}

	/**
	 * Bulk Delete Employee
	 * 
	 * @author pratik chaudhari
	 * @param fkUserList
	 * @return String
	 */
	public String BulkDeleteEmployee(List<Long> fkUserList) {
		for (int i = 0; i < fkUserList.size(); i++) {
			EmployeeUser eobj = employeeuserRepo.getOne(fkUserList.get(i));
			employeeuserRepo.delete(eobj);

		}

		return "Deleted:";

	}

	/**
	 * Update Employee
	 * 
	 * @param employeeuser
	 * @return String
	 */
	public String UpdateEmployee(EmployeeUser employeeuser) {
		String organization = null;
		long fkid = 0;
		try {
			organization = userRepo.getOganizationOnUserName(employeeuser.getAdmin());
		} catch (Exception e) {
		}
		try {
			fkid = userRepo.getfkuserIdOnAdminName(employeeuser.getAdmin());
		} catch (Exception e) {
		}

		EmployeeUser userDet = new EmployeeUser();
		userDet.setPkuserId(employeeuser.getPkuserId());
		userDet.setFirstName(employeeuser.getFirstName());
		userDet.setLastName(employeeuser.getLastName());
		userDet.setEmailId(employeeuser.getEmailId());
		userDet.setPhoneNumber(employeeuser.getPhoneNumber());
		userDet.setPassword(passwordEncoder.encode(employeeuser.getPassword()));
		userDet.setRole(employeeuser.getRole());
		userDet.setUsername(employeeuser.getUsername());
		userDet.setCompanyName(employeeuser.getCompanyName());
		userDet.setFkuserId(fkid);
		userDet.setCategory(employeeuser.getCategory());
		userDet.setAddress(employeeuser.getAddress());
		userDet.setOrganization(organization);
		userDet.setStatus(employeeuser.getStatus());
		userDet.setCreatedby(employeeuser.getCreatedby());
		userDet.setAdmin(employeeuser.getAdmin());
		employeeuserRepo.save(userDet);

		return "updated";
	}

	/**
	 * get All EmployeUser
	 * 
	 * @author Pratik chaudhari
	 * @param parseLong
	 * @return EmployeeUser
	 */
	public Optional<EmployeeUser> getAllEmployeUser(long parseLong) {

		return employeeuserRepo.findById(parseLong);
	}

	/**
	 * use to get load User Data Excel
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 */
	public InputStream loadUserDataExcel(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			List<EmployeeUser> userList = employeeuserRepo.EmployeeUserReportForSuperAdminExportDownload(category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = EmployeeUserReportForSuperAdminHelper
						.EmployeeUserExcelForSuperAdmin(userList);
				return in;
			}
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);

			String orgamizationUserName = userRepo.getOrganizationName(fkUserId);
			List<String> adminList = userRepo.getAdminList(orgamizationUserName);
			List<EmployeeUser> empUserBean = new ArrayList<EmployeeUser>();

			for (int i = 0; i < adminList.size(); i++) {
				List<EmployeeUser> userList = null;
				try {
					userList = employeeuserRepo.EmployeeUserReportForOrganizationExportDownload(adminList.get(i),
							category);
				} catch (Exception e) {
				}
				if (userList.isEmpty()) {
				} else {
					for (int j = 0; j < userList.size(); j++) {
						empUserBean.add(userList.get(j));
					}
				}
			}

			if (empUserBean.size() > 0) {
				ByteArrayInputStream in = EmployeeUserReportForSuperAdminHelper
						.EmployeeUserExcelForSuperAdmin(empUserBean);
				return in;
			}
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			List<EmployeeUser> userList = employeeuserRepo.EmployeeUserReportForOrganizationExportDownload(fkUserId,
					category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = EmployeeUserReportForSuperAdminHelper
						.EmployeeUserExcelForSuperAdmin(userList);
				return in;
			}
		}
//		if(role.equals(CommonConstants.user)) {
//			Long fkID = Long.parseLong(fkUserId);
//			List<UserResponseBean> userList = employeeuserRepo.EmployeeUserReportForUserExportDownload(fkID,category);
//			if(userList.size() > 0)
//			{
//			ByteArrayInputStream in = UserReportForSuperAdminHelper.UserExcelForUser(userList);
//			return in;
//			}
//			
//			
//		}
		return null;
	}

//	
	/**
	 * get load All User Data PDF
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 */
	public InputStream loadAllUserDataPDF(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			List<EmployeeUser> userList = employeeuserRepo.EmployeeUserReportForSuperAdminExportDownload(category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = GenerateEmpUserPDF.EmployeeUserPDF(userList);
				return in;
			}
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);

			String orgamizationUserName = userRepo.getOrganizationName(fkUserId);
			List<String> adminList = userRepo.getAdminList(orgamizationUserName);
			List<EmployeeUser> empUserBean = new ArrayList<EmployeeUser>();

			for (int i = 0; i < adminList.size(); i++) {
				List<EmployeeUser> userList = null;
				try {
					userList = employeeuserRepo.EmployeeUserReportForOrganizationExportDownload(adminList.get(i),
							category);
				} catch (Exception e) {
				}
				if (userList.isEmpty()) {
				} else {
					for (int j = 0; j < userList.size(); j++) {
						empUserBean.add(userList.get(j));
					}
				}
			}

			if (empUserBean.size() > 0) {
				ByteArrayInputStream in = GenerateEmpUserPDF.EmployeeUserPDF(empUserBean);
				return in;
			}
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			List<EmployeeUser> userList = employeeuserRepo.EmployeeUserReportForOrganizationExportDownload(fkUserId,
					category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = GenerateEmpUserPDF.EmployeeUserPDF(userList);
				return in;
			}
		}
		return null;
	}

	/**
	 * add Excel File Data To Database Bulk For Empuser
	 * 
	 * @param file
	 * @return String
	 * @throws IOException
	 */
	public String addExcelFileDataToDatabaseBulkForEmpuser(MultipartFile file) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		int icnt = 0;
		for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				EmployeeUser userDet = new EmployeeUser();
				XSSFRow row = worksheet.getRow(index);

				String firstname = row
						.getCell(0, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String lastname = row.getCell(1, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String emailId = row.getCell(2, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				long PhoneNo = (long) row
						.getCell(3, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getNumericCellValue();
				String username = row.getCell(4, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String password = row.getCell(5, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String role = row.getCell(6, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String address = row.getCell(7, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String companyName = row
						.getCell(8, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String Admin = row.getCell(9, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String Organization = row
						.getCell(10, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String category = row
						.getCell(11, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String createdby = row
						.getCell(12, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				EmployeeUser empuser = employeeuserRepo.getEmployee(emailId, category);
				UserDetail user = userRepo.getemailinfo(emailId);
				icnt++;

				long adminid = userRepo.getAdminId(Admin);
				String organization = userRepo.getorganizationId(adminid);

				if (empuser == null && user == null) {
					userDet.setFirstName(firstname);
					userDet.setLastName(lastname);
					userDet.setEmailId(emailId);
					userDet.setPhoneNumber(PhoneNo);
					userDet.setPassword(passwordEncoder.encode(password));
					userDet.setRole(role);
					userDet.setUsername(username);
					userDet.setAddress(address);
					userDet.setCompanyName(companyName);
					userDet.setFkuserId(adminid);
					userDet.setAdmin(Admin);
					userDet.setOrganization(organization);
					userDet.setCategory(category);
					userDet.setCreatedby(createdby);
					userDet.setStatus("Active");

					employeeuserRepo.save(userDet);

				}
//				else
//				{
//					return "User already exist...!";
//				}
			}
		}
		int lastrow = worksheet.getPhysicalNumberOfRows();
		if (icnt == lastrow - 1) {
			return " Users sucessfully added:" + icnt + "Record are saved";
		} else {

			return "only" + icnt + "Users Record are saved OR User already exist...!..";
		}

	}

	/**
	 * get Employee User Count
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return long
	 */
	public long getEmployeeUserCount(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			return employeeuserRepo.getCountEmpUserForSuperAdmin(category);
			// +(employeeRepo.getEmpUserCountForSuperAdmin(category))
		}
		if (role.equals(CommonConstants.admin)) {

			return userRepo.getCountEmpUserForAdmin(fkUserId, category);

		}

		if (role.equals(CommonConstants.organization)) {
			// Long fkID = Long.parseLong(fkUserId);
			String organization = userRepo.getOrganizationName(fkUserId);
			List<String> adminList = userRepo.getAdminListOnOrganization(organization);
			long icnt = 0;
			for (int i = 0; i < adminList.size(); i++) {
				long count = 0;
				try {
					count = userRepo.getCountEmpUserForAdmin(adminList.get(i), category);
				} catch (Exception e) {
				}

				icnt = icnt + count;
			}
			return icnt;
//			return userRepo.getCountUserForOrganization(organization,category);
			// +employeeRepo.getEmpUserCountForOrganization(organization,category))
		}
		return 0l;

	}

	/**
	 * get Empuser List For With User Role
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return EmployeeUser
	 */
	public List<EmployeeUser> getEmpuserListForWithUserRole(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			return employeeuserRepo.getAllEmpUserListRole(category);

		}

		if (role.equals(CommonConstants.organization)) {
			String username = userRepo.getuserNaame(fkUserId);
			List<String> adminList = userRepo.getAdminList(username);
			List<EmployeeUser> list = new ArrayList<>();
			for (int i = 0; i < adminList.size(); i++) {
				List<EmployeeUser> empuserList = null;
				try {
					empuserList = employeeuserRepo.getAllEmpUserListRoleForOrganization(adminList.get(i), category);
				} catch (Exception e) {
				}
				for (int j = 0; j < empuserList.size(); j++) {
					list.add(empuserList.get(j));
				}

			}
			return list;

		}
		if (role.equals(CommonConstants.admin)) {
//			String username=userRepo.getuserNaame(fkUserId);
			return employeeuserRepo.getAllEmpUserListRoleForOrganization(fkUserId, category);

		}
		if (role.equals(CommonConstants.empUser)) {
//			String username=userRepo.getuserNaame(fkUserId);
			return employeeuserRepo.getAllEmpUserListRoleForOrganization(fkUserId, category);

		}
		return null;
	}

}
