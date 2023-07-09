package com.embel.asset.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.embel.asset.bean.GatewayAndTagCountbean;
import com.embel.asset.bean.ResponseSigninBean;
import com.embel.asset.bean.UserInfobean;
import com.embel.asset.bean.UserResponseBean;
import com.embel.asset.bean.UserResponseforissueBean;
import com.embel.asset.dto.RequestPermissionDto;
import com.embel.asset.entity.Category;
import com.embel.asset.entity.EmployeeUser;
import com.embel.asset.entity.UserDetail;
import com.embel.asset.entity.UserLoginHistory;
import com.embel.asset.exception.CustomException;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.helper.GenerateUserPDF;
import com.embel.asset.helper.UserReportForSuperAdminHelper;
import com.embel.asset.repository.AssetGatewayRepository;
import com.embel.asset.repository.AssetTagRepository;
import com.embel.asset.repository.CategoryRepository;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.ProductAllocationRepository;
import com.embel.asset.repository.ProductDetailRepository;
import com.embel.asset.repository.UserRepository;
import com.embel.asset.security.JwtTokenProvider;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	AssetGatewayRepository gatewayRepo;

	@Autowired
	AssetTagRepository tagRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private LoginHistoryService historyService;

	@Autowired
	private ProductAllocationRepository productRepo;
	@Autowired
	private EmployeUserRepository employeeRepo;
	@Autowired
	private ProductDetailRepository prodRepository;

	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	private JavaMailSender mailSender;
	private String url;

	/**
	 * use regester the user
	 * 
	 * @param user
	 * @return String
	 */
	public String signup(UserDetail user) {

		System.out.println(CommonConstants.user + user);
		// UserDetail dbUser = userRepo.findByEmailId(user.getEmailId());
//		TimeUnit.MINUTES.sleep(1);
		UserDetail dbUser = null;
		UserDetail dbUser1 = null;
		EmployeeUser dbEmpuserName = null;
		EmployeeUser dbEmpuserNameEmail = null;
		System.out.println("unique" + user.getEmailId() + user.getCategory().toUpperCase());
		try {
			dbUser = userRepo.getUserDetalsObject(user.getEmailId(), user.getCategory().toUpperCase());
			dbUser1 = userRepo.getUserDetalsOnUserName(user.getUsername(), user.getCategory().toUpperCase());

			dbEmpuserName = employeeRepo.getEmployeedbDataOnUserName(user.getUsername(),
					user.getCategory().toUpperCase());
			dbEmpuserNameEmail = employeeRepo.getEmployeedbDataOnEmail(user.getEmailId(),
					user.getCategory().toUpperCase());
			System.out.println("@@@dbUser#####" + dbUser);
			System.out.println("@@@dbUser1#####" + dbUser1);
			if (dbUser1.getUsername() == user.getUsername()) {
				System.out.println("username alrsdy exit ");
				return "User already exist...!";

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		if (dbEmpuserName != null || dbEmpuserNameEmail != null) {
			return "User already exist...!";
		}
		if (dbUser == null && dbUser1 == null) {
			System.out.println("save zalach pahij");
			UserDetail userDet = new UserDetail();
			userDet.setFirstName(user.getFirstName());
			userDet.setLastName(user.getLastName());
			userDet.setEmailId(user.getEmailId());
			userDet.setPhoneNumber(user.getPhoneNumber());
			userDet.setPassword(passwordEncoder.encode(user.getPassword()));
			userDet.setRole(user.getRole());
			userDet.setUsername(user.getUsername());
			userDet.setAddress(user.getAddress());
			userDet.setCompanyName(user.getCompanyName());
			userDet.setOrganization(user.getOrganization());
			userDet.setAdmin(user.getAdmin());
			userDet.setUser(user.getUser());
			userDet.setStatus("Active");
			userDet.setCreatedby(user.getCreatedby());
			userDet.setCategory(user.getCategory().toUpperCase());

			System.out.println("userDet" + userDet);
			userRepo.save(userDet);
			return "created";
		} else {
			System.out.println("User already exist...!");
			return "User already exist...!";
		}
		/*
		 * if (!userRepo.existsByUsername(user.getUsername())) {
		 * user.setPassword(passwordEncoder.encode(user.getPassword()));
		 * userRepo.save(user); return
		 * jwtTokenProvider.createToken(user.getUsername(),new ArrayList<>()); } else {
		 * throw new CustomException("Username is already in use",
		 * HttpStatus.UNPROCESSABLE_ENTITY); }
		 */
	}
	/*
	 * public ResponseSigninBean signin(String username, String password,
	 * HttpServletRequest request) { try { authenticationManager.authenticate(new
	 * UsernamePasswordAuthenticationToken(username, password));
	 * 
	 * List<RequestPermissionDto> reqPerDto =
	 * userRepo.getUserDetailsForAllocation(username); ResponseSigninBean result =
	 * new ResponseSigninBean(); result.setPerList(reqPerDto);
	 * result.setToken(jwtTokenProvider.createToken(username,new ArrayList<>()));
	 * 
	 * // User Login History Details for (RequestPermissionDto requestPermissionDto
	 * : reqPerDto) { String userNameForHistory =
	 * requestPermissionDto.getFirstName() + " "
	 * +requestPermissionDto.getLastName(); UserLoginHistory loginHistory = new
	 * UserLoginHistory(new Date());
	 * loginHistory.setUserId(requestPermissionDto.getPkuserId());
	 * loginHistory.setUserName(userNameForHistory);
	 * loginHistory.setLoginIP(request.getRemoteAddr());
	 * loginHistory.setUserRole(requestPermissionDto.getRole());
	 * 
	 * historyService.saveLoginHistory(loginHistory); } return result;
	 * 
	 * } catch (AuthenticationException e) { e.printStackTrace(); throw new
	 * CustomException("Invalid username/password supplied",
	 * HttpStatus.UNPROCESSABLE_ENTITY); } }
	 */

	/**
	 * use to login user
	 * 
	 * @author pratik chaudhari
	 * @param username
	 * @param password
	 * @param HttpServletRequest request
	 * 
	 * @return ResponseSigninBean
	 */

	public ResponseSigninBean signin(String username, String password, HttpServletRequest request) {
		String checkrole = null;
		try {
			checkrole = employeeRepo.getrole(username);
		} catch (Exception e) {
			System.out.println(e);
		}

		if (checkrole != null) {
			ResponseSigninBean result = new ResponseSigninBean();
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

				List<RequestPermissionDto> reqPerDto = employeeRepo.getUserDetailsForAllocation(username);

				String status = employeeRepo.getStatus(username);
				if (!status.equals("Active")) {
					return new ResponseSigninBean("User is Not Active", "Contact to your Admin");
				}
				List<EmployeeUser> EmployeeDetails = employeeRepo.findUsersByUsername(username);
				if (EmployeeDetails.size() != 1)
					throw new CustomException("More than one record found in user", HttpStatus.INTERNAL_SERVER_ERROR);
				result.setPerList(reqPerDto);
				EmployeeUser user = EmployeeDetails.get(0);
				String accessToken = jwtTokenProvider.createToken(user.getUsername(), new ArrayList<>());
				String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername());
				user.setAccessToken(accessToken);
				user.setRefreshToken(refreshToken);
				result.setToken(accessToken);
				result.setRefreshToken(refreshToken);
				for (RequestPermissionDto requestPermissionDto : reqPerDto) {
					String userNameForHistory = requestPermissionDto.getFirstName() + " "
							+ requestPermissionDto.getLastName();
					UserLoginHistory loginHistory = new UserLoginHistory(new Date());
					loginHistory.setUserId(requestPermissionDto.getPkuserId());
					loginHistory.setUserName(userNameForHistory);
					loginHistory.setLoginIP(request.getRemoteAddr());
					loginHistory.setUserRole(requestPermissionDto.getRole());

					historyService.saveLoginHistory(loginHistory);
				}
				return result;

			} catch (Exception e) {
				System.out.println(e);
			}
//			return	result;	

		} else {

			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

				List<RequestPermissionDto> reqPerDto = userRepo.getUserDetailsForAllocation(username);
				ResponseSigninBean result = new ResponseSigninBean();
				List<UserDetail> userDetail = userRepo.findUsersByUsername(username);
				if (userDetail.size() != 1)
					throw new CustomException("More than one record found in user", HttpStatus.INTERNAL_SERVER_ERROR);
				RequestPermissionDto newReqPerDto = new RequestPermissionDto();

				newReqPerDto.setFirstName(reqPerDto.get(0).getFirstName());
				newReqPerDto.setLastName(reqPerDto.get(0).getLastName());
				newReqPerDto.setPkuserId(reqPerDto.get(0).getPkuserId());
				newReqPerDto.setRole(reqPerDto.get(0).getRole());
				newReqPerDto.setUsername(reqPerDto.get(0).getUsername());

				if (reqPerDto.get(0).getOrganization() == null) {
					newReqPerDto.setOrganization("No");
				} else {
					newReqPerDto.setOrganization("Yes");
				}

				newReqPerDto.setCategory(reqPerDto.get(0).getCategory());
				List<RequestPermissionDto> newReqPerDtoList = new ArrayList<RequestPermissionDto>();
				newReqPerDtoList.add(newReqPerDto);

				result.setPerList(newReqPerDtoList);
				UserDetail user = userDetail.get(0);
				String accessToken = jwtTokenProvider.createToken(user.getUsername(), new ArrayList<>());
				String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername());
				user.setAccessToken(accessToken);
				user.setRefreshToken(refreshToken);
				result.setToken(accessToken);
				result.setRefreshToken(refreshToken);
				String status = userRepo.getStats(username);
				String role = userRepo.getRole(username);
				if (!status.equals("Active")) {

					return new ResponseSigninBean("User is Not Active", "Contact to your Admin");
				}
//				String role=userRepo.getRole(username);
				List<String> listcategory = null;
				if (role.equals(CommonConstants.superAdmin)) {
					listcategory = tagRepository.getcategoryListForSuperAdmin(username);
					// listcategory=categoryRepo.getcategoryfroSuperAdmin();
				}
				if (role.equals(CommonConstants.admin)) {
					listcategory = tagRepository.getcategoryListForAdmin(username);
					// listcategory=categoryRepo.getcategoryfroSuperAdmin();
				}
				if (role.equals(CommonConstants.organization)) {
//					List<String> adminList=null;
//					try {adminList=userRepo.getAdminList(username);} catch (Exception e) {}
//					
//						for(int i = 0; i <adminList.size(); i++) {
//							
//							listcategory=tagRepository.getcategoryListForOrganization(username);
//						}

					listcategory = tagRepository.getcategoryListForOrganization(username);
					// listcategory=categoryRepo.getcategoryfroSuperAdmin();
				}
				if (role.equals(CommonConstants.user)) {
					listcategory = tagRepository.getcategoryListForUser(username);

				}
				List<Category> catlist = new ArrayList<Category>();

				for (long i = 0; i < listcategory.size(); i++) {
					Category catbean = new Category();
					catbean.setPkCatId(i);
					catbean.setCategoryName(listcategory.get((int) i));
					catlist.add(catbean);
				}
				result.setCategory(catlist);

				for (RequestPermissionDto requestPermissionDto : newReqPerDtoList) {
					String userNameForHistory = requestPermissionDto.getFirstName() + " "
							+ requestPermissionDto.getLastName();
					UserLoginHistory loginHistory = new UserLoginHistory(new Date());
					loginHistory.setUserId(requestPermissionDto.getPkuserId());
					loginHistory.setUserName(userNameForHistory);
					loginHistory.setLoginIP(request.getRemoteAddr());
					loginHistory.setUserRole(requestPermissionDto.getRole());

					historyService.saveLoginHistory(loginHistory);
				}
				return result;

			} catch (AuthenticationException e) {
				e.printStackTrace();
				throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}
		return null;

	}

	/**
	 * use to validate refresh token
	 * 
	 * @author pratik chaudhari
	 * @param String
	 * 
	 * @return String
	 */
	public String refreshToken(String refreshToken) {
		UserDetail userDetails = userRepo.findByUsername(jwtTokenProvider.getUsername(refreshToken));
		if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
			System.out.println("getusername" + userDetails.getUsername());
			return jwtTokenProvider.createToken(userDetails.getUsername(), new ArrayList<>());
		} else
			throw new CustomException("User token detail not available", HttpStatus.UNAUTHORIZED);
	}

	/**
	 * use to get User List
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * 
	 * @return UserResponseBean
	 */
	public List<UserResponseBean> getUserList(String fkUserId, String role) {
		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getUserListForSuperAdmin();
		}
		if (role.equals(CommonConstants.admin)) {
			Long id = Long.parseLong(fkUserId);
			return userRepo.getUserListForAdmin(id);
		}
		if (role.equals(CommonConstants.user)) {
			Long id = Long.parseLong(fkUserId);
			return userRepo.getUserListForUser(id);
		}
		return null;
	}

	/**
	 * use to get User For Edit
	 * 
	 * @author pratik chaudhari
	 * @param id
	 *
	 * @return UserDetail
	 */
	public Optional<UserDetail> getUserForEdit(Long id) {
		// return userRepo.getUserForEdit(id);
		return userRepo.findById(id);
	}

	/**
	 * use to get update User
	 * 
	 * @author pratik chaudhari
	 * @param user object
	 */
	public void updateUser(UserDetail user) {
		UserDetail userToUpdate = userRepo.getOne(user.getPkuserId());

		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setEmailId(user.getEmailId());
		userToUpdate.setPhoneNumber(user.getPhoneNumber());
		// userToUpdate.setPassword(user.getPassword());
		userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
		userToUpdate.setRole(user.getRole());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setCompanyName(user.getCompanyName());
		userToUpdate.setAddress(user.getAddress());
		userToUpdate.setStatus(user.getStatus());
		userToUpdate.setCategory(user.getCategory());
		userToUpdate.setOrganization(user.getOrganization());
		userToUpdate.setAdmin(user.getAdmin());
		userToUpdate.setUser(user.getUser());

		userRepo.save(userToUpdate);

		Long id = user.getPkuserId();
		String role = user.getRole();
		System.out.println(role);
		String newUserName = user.getFirstName() + " " + user.getLastName();
		System.out.println("user Name : " + newUserName);

		if (role.equalsIgnoreCase(CommonConstants.admin)) {
			gatewayRepo.updateAdminDetails(newUserName, id);
			tagRepository.updateAdminDetails(newUserName, id);
			productRepo.updateUserDetails(newUserName, id);
			prodRepository.updateUserDetails(newUserName, id);
		} else if (role.equalsIgnoreCase(CommonConstants.user)) {
			gatewayRepo.updateUserDetails(newUserName, id);
			tagRepository.updateUserDetails(newUserName, id);
			productRepo.updateUserDetails(newUserName, id);
			prodRepository.updateUserDetails(newUserName, id);
		}
	}

	/**
	 * use to delete By Id
	 * 
	 * @author pratik chaudhari
	 * @param id
	 * 
	 */

	public void deleteById(long id) {

		UserDetail userentity = userRepo.getOne(id);

		userRepo.delete(userentity);

	}

	/**
	 * use to get Count User
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 */

	public long getCountUser(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getCountUserForSuperAdmin(category);
			// +(employeeRepo.getEmpUserCountForSuperAdmin(category))
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			// long temp=(Long.parseLong();
			String admin = userRepo.getAdminUserName(fkUserId);
			return userRepo.getCountUserForAdmin(admin, category);
			// )+(employeeRepo.getEmpUserCountForAdmin(fkID,category)))
		}

		if (role.equals(CommonConstants.organization)) {
			// Long fkID = Long.parseLong(fkUserId);
			String organization = userRepo.getOrganizationName(fkUserId);
			return userRepo.getCountUserForOrganization(organization, category);
			// +employeeRepo.getEmpUserCountForOrganization(organization,category))
		}
		return 0l;
	}

	/**
	 * use to get Count Admin
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 */
	public String getCountAdmin(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getCountSuperAdmin(category);
		}
		if (role.equals(CommonConstants.organization)) {
//			Long fkID = Long.parseLong(fkUserId);

			String admin = userRepo.getadminNamexl(fkUserId);
			return userRepo.getCountAdmin(admin, category);
		}
//		if(role.equals(CommonConstants.user)) {
//			Long fkID = Long.parseLong(fkUserId);
//			return userRepo.getCountUser(fkID);
//		}*/
		return null;
	}

	/**
	 * use to get All User List
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail
	 */
	public List<UserDetail> getAllUserList(String fkUserId, String role, String category) {
		Long fkID = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getListOfUserRole(category);
		}
		if (role.equals(CommonConstants.organization)) {
			String userName = userRepo.getAdminUserNameforOrganization(fkUserId);
			List<String> adminList = userRepo.getAdminListOnOrganization(userName);
			List<UserDetail> userbean = new ArrayList<>();
			for (int i = 0; i < adminList.size(); i++) {

				String adminUserName = userRepo.getAdminUserNameforOrganization(adminList.get(i));
				List<UserDetail> useList = null;
				try {
					useList = userRepo.getListOfUserRoleforOrganization(adminUserName, category);
				} catch (Exception e) {
					System.out.println();
				}
				System.out.println("userbean" + userbean);
				if (useList.isEmpty()) {
					System.out.println("useList" + useList.size());
				} else {
					System.out.println("useList" + useList.size());
					System.out.println("userbean" + userbean.size());
					int temp = 0;
					temp = userbean.size();
					for (int j = 0; j < useList.size(); j++) {
						temp++;
						userbean.add(useList.get(j));

					}
				}

			}

			return userbean;
		}
		if (role.equals(CommonConstants.admin)) {
			String userName = userRepo.getAdminUserNameforOrganization(fkUserId);
			return userRepo.getListOfUserRoleforAdmin(userName, category);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeRepo.getAdminId(fkUserId);
			String userName = userRepo.getAdminUserNameforOrganization(fkUserId);
			return userRepo.getListOfUserRoleforAdmin(userName, category);
		}
		return null;
	}
//	public List<RequestPermissionDto> getAllAdminListforGps(String fkUserId, String role)
//	{
//		Long fkID = Long.parseLong(fkUserId);
//		if(role.equals(CommonConstants.superAdmin)) 
//		{
//			List<RequestPermissionDto> list=userRepo.getAlladminListforSuperAdminRoleforGps();
//			System.out.println("list"+list);
//			return list;
//		}
//		if(role.equals(CommonConstants.organization))
//		{
//			List<RequestPermissionDto> list= userRepo.getAlladminListforOrganizationRoleforGps(fkID);
//			System.out.println("list"+list);
//			return list;
//		}
//		if(role.equals(CommonConstants.admin))
//		{
//			List<RequestPermissionDto> list=userRepo.getListOfUserRoleforAdminforGps(fkID);
//			System.out.println("list"+list);
//			return list;
//		}
//		return null;
//		
//		
//	}
//	

	/**
	 * use to get All Admin List
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail list
	 */
	public List<UserDetail> getAllAdminList(String fkUserId, String role, String category) {
		String username = userRepo.getuserNaame(fkUserId);
		Long fkID = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getListOfAdminRoleForSuperadmin(category);
		}

		if (role.equals(CommonConstants.organization)) {
			List<UserDetail> list = new ArrayList<UserDetail>();

			List<UserDetail> ulist = userRepo.getListOfAdminRoleForOrganization(username, category);
			for (int i = 0; i < ulist.size(); i++) {
//				RequestPermissionDto dto=new RequestPermissionDto();
//				dto.setFirstName(ulist.get(i).getFirstName());
//				dto.setLastName(ulist.get(i).getLastName());
//				dto.setPkuserId(ulist.get(i).getPkuserId());
//				dto.setRole(ulist.get(i).getRole());
//				dto.setUsername(ulist.get(i).getUsername());

				list.add(ulist.get(i));
			}
			return list;

		}

		return null;

	}

	/**
	 * use to get All Organization List
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param category
	 * @return RequestPermissionDto list
	 */
	public List<RequestPermissionDto> getAllOrganizationList(String fkUserId, String category) {
		Long fkID = Long.parseLong(fkUserId);
		return userRepo.getListOfOrganizationRole(category);

	}

	/**
	 * use to add Excel File Data To Database For Direct Admin
	 * 
	 * @author pratik chaudhari
	 * @param files
	 * @param username1
	 * @return String
	 */
	public String addExcelFileDataToDatabaseForDirectAdmin(MultipartFile files, String username1) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
//		String sheetname=workbook.getSheetName(0);
//		System.out.println(sheetname);
		// String creator=username1.
		String[] creator = username1.split("/", 2);
		System.out.println(Array.get(creator, 0));
		System.out.println(Array.get(creator, 1));
		// System.out.println("worksheet.getPhysicalNumberOfRows()"+worksheet.getPhysicalNumberOfRows());
		int icnt = 0;
		for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				UserDetail userDet = new UserDetail();
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
				// String User =null;
				String Admin = null;

				String category = row.getCell(9, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				Admin = row.getCell(10, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

//				try {
//					 User = row.getCell(11, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
//				} catch (Exception e) {
//					System.out.println(User);
//				}

				UserDetail user = userRepo.findByEmailId(emailId);
				icnt++;
				// if(username1.equals(Organization)) {}
				if (user == null) {
					userDet.setFirstName(firstname);
					userDet.setLastName(lastname);
					userDet.setEmailId(emailId);
					userDet.setPhoneNumber(PhoneNo);
					userDet.setPassword(passwordEncoder.encode(password));
					userDet.setRole(role);
					userDet.setUsername(username);
					userDet.setAddress(address);
					userDet.setCompanyName(companyName);
					// userDet.setUser(User);
					userDet.setAdmin(Admin);

					userDet.setCategory(category);
					userDet.setStatus("Active");
					userDet.setCreatedby(username1);
					userRepo.save(userDet);

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
	 * add bulk user using excel file
	 * 
	 * @param files
	 * @param username1
	 * @return String
	 * @throws IOException
	 */

	public String addExcelFileDataToDatabase(MultipartFile files, String username1) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		String sheetname = workbook.getSheetName(0);
		System.out.println(sheetname);
		// String creator=username1.

		String[] creator = username1.split("/", 2);
		System.out.println(Array.get(creator, 0));
		System.out.println(Array.get(creator, 1));
		int icnt = 0;

		for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				UserDetail userDet = new UserDetail();
				XSSFRow row = worksheet.getRow(index);

				String firstname = null, lastname = null, role = null, emailId = null, username = null, password = null,
						User = null, Admin = null, Organization = null, category = null, address = null,
						companyName = null;
				long PhoneNo = 0l;

				if (Array.get(creator, 1).toString().equals(CommonConstants.superAdmin)) {
					System.out.println(CommonConstants.superAdmin);
					firstname = row.getCell(0, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					lastname = row.getCell(1, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					emailId = row.getCell(2, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					PhoneNo = (long) row
							.getCell(3, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getNumericCellValue();
					username = row.getCell(4, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					password = row.getCell(5, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					role = row.getCell(6, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					address = row.getCell(7, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					companyName = row.getCell(8, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();

					User = row.getCell(9, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();

					Admin = row.getCell(10, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();

					Organization = row
							.getCell(11, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();

					category = row.getCell(12, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					System.out.println("User->" + User);

					System.out.println("Admin->" + Admin);

					System.out.println("Organization->" + Organization);

				} else {

					firstname = row.getCell(0, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					lastname = row.getCell(1, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					emailId = row.getCell(2, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					PhoneNo = (long) row
							.getCell(3, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getNumericCellValue();
					username = row.getCell(4, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					password = row.getCell(5, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					role = row.getCell(6, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					address = row.getCell(7, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					companyName = row.getCell(8, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					User = null;
					Admin = null;
					Organization = null;
					category = row.getCell(9, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					if (Array.get(creator, 1).toString().equals(CommonConstants.admin)) {
						Admin = row.getCell(10, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
								.getStringCellValue();

					} else {
						Organization = row
								.getCell(10, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
								.getStringCellValue();
						Admin = row.getCell(11, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
								.getStringCellValue();
					}

					try {
						User = row.getCell(12, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
								.getStringCellValue();
					} catch (Exception e) {
						System.out.println(User);
					}

				}

				if (firstname == "") {
					break;
				}

				UserDetail user = userRepo.findByEmailId(emailId);
				icnt++;
				// if(username1.equals(Organization)) {}
				if (user == null) {
					userDet.setFirstName(firstname);
					userDet.setLastName(lastname);
					userDet.setEmailId(emailId);
					userDet.setPhoneNumber(PhoneNo);
					userDet.setPassword(passwordEncoder.encode(password));
					userDet.setRole(role);
					userDet.setUsername(username);
					userDet.setAddress(address);
					userDet.setCompanyName(companyName);
					userDet.setUser(User);
					userDet.setAdmin(Admin);
					userDet.setOrganization(Organization);
					userDet.setCategory(category);
					userDet.setStatus("Active");
					userDet.setCreatedby(username1);
					userRepo.save(userDet);

				}
//					else
//					{
//						return "User already exist...!";
//					}
			}
		}

		int lastrow = worksheet.getPhysicalNumberOfRows();
		if (icnt == lastrow - 1) {
			return " Users sucessfully added:" + icnt + "Record are saved";
		} else {

			return "only" + icnt + "Users Record are saved OR User already exist...!..";
		}
	}

	// .............

	/**
	 * 
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @return UserDetail
	 */
	public Page<UserDetail> getUserListWithPaginationxl(String fkUserId, String role, Pageable pageable) {

		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getUserListForSuperAdminxl(pageable);
		}
		if (role.equals(CommonConstants.organization)) {

			// Long id = Long.parseLong(fkUserId);
			String userName = userRepo.getuserNaame(fkUserId);
			System.out.println(userName);
			return userRepo.getUserListForOrganizationxl(userName, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			// Long id = Long.parseLong(fkUserId);
			String userName = userRepo.getuserNaame(fkUserId);
			return userRepo.getUserListForAdminXl(userName, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			Long id = Long.parseLong(fkUserId);
			String userName = userRepo.getuserNaame(fkUserId);
			return userRepo.getUserListForUserxl(userName, pageable);
		}

		return null;
	}

//............................pagination................
	/**
	 * use to get user List from db with pagination
	 * 
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @param category
	 * @return Page<UserResponseBean>
	 */
	public Page<UserResponseBean> getUserListWithPagination(String fkUserId, String role, Pageable pageable,
			String category) {// UserListbean
		if (role.equals(CommonConstants.superAdmin)) {
//			long gatewayCount=gatewayRepo.getgatewayCountUserWise(fkUserId);
			Page<UserResponseBean> page = userRepo.getUserListForSuperAdminWithPagination(category, pageable);

		}
		if (role.equals(CommonConstants.organization)) {

			Long id = Long.parseLong(fkUserId);
			String userName = userRepo.getuserNaame(fkUserId);
			System.out.println(userName);
			return userRepo.getUserListForOrganizationWithPagination(category, id, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			Long id = Long.parseLong(fkUserId);
			return userRepo.getUserListForAdminWithPagination(category, id, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			Long id = Long.parseLong(fkUserId);
			return userRepo.getUserListForUserWithPagination(category, id, pageable);
		}
		return null;
	}
	// ...................for gps

	/**
	 * use to get User List With Pagination For Gps
	 * 
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @param category
	 * @return UserDetail
	 */
	public Page<UserDetail> getUserListWithPaginationForGps(String fkUserId, String role, Pageable pageable,
			String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getUserListForSuperAdminWithPaginationForGPS(category, pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			Long id = Long.parseLong(fkUserId);
			return userRepo.getUserListForOrganizationWithPaginationForGPS(category, id, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			Long id = Long.parseLong(fkUserId);
			return userRepo.getUserListForAdminWithPaginationForGPS(category, id, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			Long id = Long.parseLong(fkUserId);
			return userRepo.getUserListForUserWithPaginationForGPS(category, id, pageable);
		}

		return null;
	}

	/**
	 * use to update ResetPassword Token
	 * 
	 * @author pratik chaudhari
	 * @param token
	 * @param email
	 */

	// ................................................................................................
	public void updateResetPasswordToken(String token, String email) {
		UserDetail customer = userRepo.findByEmail(email);
		if (customer != null) {
			customer.setResetPasswordToken(token);
			userRepo.save(customer);
		} else {
			throw new CustomException("Could not find any customer with the email " + email, null);
		}

	}

	/**
	 * use to get By Reset Password Token
	 * 
	 * @param token
	 * @return UserDetail
	 */

	public UserDetail getByResetPasswordToken(String token) {
		return userRepo.findByResetPasswordToken(token);

	}

	/**
	 * use to update Password
	 * 
	 * @param customer
	 * @param newPassword
	 */
	public void updatePassword(UserDetail customer, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		customer.setPassword(encodedPassword);

		customer.setResetPasswordToken(null);
		userRepo.save(customer);

	}
//	public UserDetail findByid(Long userid) {
//		
//		return userRepo.getById(userid);
//	}

	/**
	 * use to find By Email
	 * 
	 * @param email
	 * @return UserDetail
	 */
	public UserDetail findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

	/**
	 * use to find By Email Address
	 * 
	 * @param email
	 * @return String
	 */
	public Optional<String> findByEmailAddress(String email) {

//		Optional<String> check = Optional.ofNullable(a[1]);
		Optional<String> emaildb = Optional.ofNullable(userRepo.findByEmailAddress(email));

		return emaildb;
	}

	/**
	 * use to process Forgot Password
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	public String processForgotPassword(HttpServletRequest request) throws Exception {
		String token = RandomString.make(30);
//		String userid=request.getParameter("userid");
		String email = request.getParameter("email");

//		Long userid1=Long.parseLong(userid);

//		UserDetail user=new UserDetail();
		Optional<String> emaildb = findByEmailAddress(email);

		if (!emaildb.get().contains(email))// userid1 != user.getPkuserId() ||// || user == null
		{

//				System.out.println("dbemail"+emaildb);
//				System.out.println(CommonConstants.user+email);

			return "Invalid Email  ";

		} else {

			try {

				updateResetPasswordToken(token, email);

//				url = "http://15.206.217.121/register";     //65.1.68.67
//				"/users/reset-password?token=" +

				url = "http://34.231.193.123/register/" + token;
//					String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
				String resetPasswordLink = url;

				System.out.println("&&&&&&&&befour sending mail");
				try {
					sendEmail(email, resetPasswordLink);
					return "Reset Pasword Link sending on your Email..";
				} catch (UnsupportedEncodingException | MessagingException | javax.mail.MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (CustomException ex) {
				System.out.println(ex.getMessage());
			}

		}

		return null;

	}

	/**
	 * use to send email
	 * 
	 * @param recipientEmail
	 * @param link
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws javax.mail.MessagingException
	 * 
	 */
	public void sendEmail(String recipientEmail, String link)
			throws MessagingException, UnsupportedEncodingException, javax.mail.MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("embelbackup@gmail.com", "Embel Support");
		helper.setTo(recipientEmail);

		String subject = "Here's the link to reset your password";

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
				+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);
		System.out.println("massage:" + message);
		mailSender.send(message);
	}

	/**
	 * use to delete By Id
	 * 
	 * @param pkID
	 */
	public void deleteById(List<Long> pkID) {
		UserDetail user;
		for (int i = 0; i < pkID.size(); i++) {
			user = userRepo.getOne(pkID.get(i));

			userRepo.delete(user);

		}

	}

	/**
	 * use to get User Details For Contact
	 * 
	 * @param fkUserId
	 * @return UserResponseforissueBean
	 */
	public UserResponseforissueBean getUserDetailsForContact(long fkUserId) {

		return userRepo.getUserDetailsForContactandissue(fkUserId);
	}

	/**
	 * use to User Report For Organization Export DownloadPro
	 * 
	 * @param fkID
	 * @param category
	 * @return UserResponseBean list
	 */
	public List<UserResponseBean> UserReportForOrganizationExportDownloadPro(String fkID, String category) {
		String organizationUserName = userRepo.getUserName(fkID);
		List<String> adminList = userRepo.getAdminUnderOrganizationx(organizationUserName, category);
		List<UserResponseBean> list = new ArrayList<UserResponseBean>();
		for (int i = 0; i < adminList.size(); i++) {
			List<UserResponseBean> userlist = null;
			String adminName = userRepo.getUserName(adminList.get(i));
			try {
				userlist = userRepo.UserReportForOrganizationExportDownload(adminName, category);
			} catch (Exception e) {
				System.out.println(e);
			}
			if (userlist.isEmpty()) {
			} else {
				for (int j = 0; j < userlist.size(); j++) {
					System.out.println("userlist.get(j)" + userlist.get(j));
					list.add(userlist.get(j));
				}
			}

			List<EmployeeUser> empuserlist = null;
			try {
				empuserlist = employeeRepo.empUserReportForOrganizationExportDownload(adminList.get(i), category);
			} catch (Exception e) {
				System.out.println(e);
			}
			if (empuserlist.isEmpty()) {
			} else {
				for (int j = 0; j < empuserlist.size(); j++) {
					System.out.println("empuserlist.get(j)" + empuserlist.get(j));
//e.pkuser_id,e.username,e.user_role,e.first_name,e.last_name,e.phone_number,e.e_mail_id,e.`password`,e.company_name,e.user_address					
					UserResponseBean empuserbean = new UserResponseBean();
					empuserbean.setFirstName(empuserlist.get(j).getFirstName());
					empuserbean.setLastName(empuserlist.get(j).getLastName());
					empuserbean.setPkuserId(empuserlist.get(j).getPkuserId());
					empuserbean.setUsername(empuserlist.get(j).getUsername());
					empuserbean.setRole(empuserlist.get(j).getRole());
					empuserbean.setEmailId(empuserlist.get(j).getEmailId());
					empuserbean.setPhoneNumber(empuserlist.get(j).getPhoneNumber());
					empuserbean.setPassword(empuserlist.get(j).getPassword());
					empuserbean.setCompanyName(empuserlist.get(j).getCompanyName());
					empuserbean.setAddress(empuserlist.get(j).getAddress());
					list.add(empuserbean);
				}
			}

		}
		List<UserResponseBean> adminListNew = null;
		try {
			adminListNew = userRepo.getAdminUnderOrganizationxx(organizationUserName, category);
		} catch (Exception e) {
		}
		if (adminListNew.isEmpty()) {
		} else {
			for (int k = 0; k < adminListNew.size(); k++) {

				list.add(adminListNew.get(k));
			}
		}

		return list;
	}

	/**
	 * use to load User Data Excel
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 */

	public InputStream loadUserDataExcel(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			List<UserResponseBean> userList = userRepo.UserReportForSuperAdminExportDownload(category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = UserReportForSuperAdminHelper.UserExcelForSuperAdmin(userList);
				return in;
			}
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);

//				String organizationUserName=userRepo.getOganizationOnUserName(fkUserId);
//				List<String> adminList=userRepo.getAdminList(organizationUserName);
//					for(int i = 0; i < adminList.size(); i++)
//					{
//						
//					}
//				
			List<UserResponseBean> userList = this.UserReportForOrganizationExportDownloadPro(fkUserId, category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = UserReportForSuperAdminHelper.UserExcelForAdminAndUser(userList);
				return in;
			}
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);

			String adminName = userRepo.getuserNaame(fkUserId);
			List<UserResponseBean> userList = userRepo.UserReportForAdminExportDownload(adminName, category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = UserReportForSuperAdminHelper.UserExcelForAdminAndUser(userList);
				return in;
			}
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			List<UserResponseBean> userList = userRepo.UserReportForUserExportDownload(fkID, category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = UserReportForSuperAdminHelper.UserExcelForUser(userList);
				return in;
			}

		}
		return null;
	}
//		

	/**
	 * use to load All User Data PDF
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 */
	public InputStream loadAllUserDataPDF(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			List<UserResponseBean> userList = userRepo.UserReportForSuperAdminExportDownload(category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = GenerateUserPDF.UserPDF(userList);
				return in;
			}
		}

		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			List<UserResponseBean> userList = this.UserReportForOrganizationExportDownloadPro(fkUserId, category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = GenerateUserPDF.UserPDF(userList);
				return in;
			}
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			String adminName = userRepo.getUserName(fkUserId);
			List<UserResponseBean> userList = userRepo.UserReportForAdminExportDownload(adminName, category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = GenerateUserPDF.UserPDF(userList);
				return in;
			}
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			List<UserResponseBean> userList = userRepo.UserReportForUserExportDownload(fkID, category);
			if (userList.size() > 0) {
				ByteArrayInputStream in = GenerateUserPDF.UserPDF(userList);
				return in;
			}
		}
		return null;
	}

	/**
	 * use to get Count Organization
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	public String getCountOrganization(String fkUserId, String role, String category) {

		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getOraganizationCount(category);
		}
		return "0";

	}

	/**
	 * use to get User Wise Tag And Gateway Count
	 * 
	 * @param fkUserId
	 * @param role
	 * @return GatewayAndTagCountbean
	 */
	public GatewayAndTagCountbean getUserWiseTagAndGatewayCount(String fkUserId, String role) {
		if (role.equals(CommonConstants.superAdmin)) {
			String tagcount = tagRepository.getUserWiseTagCountForSuperAdmin();
			String gatewayCount = gatewayRepo.getUserWiseGatewayCountForSuperAdmin();
			GatewayAndTagCountbean bean = new GatewayAndTagCountbean();
			bean.setGatewayCount(gatewayCount);
			bean.setTagCountString(tagcount);

			return bean;

		}

		if (role.equals(CommonConstants.organization)) {
			String tagcount = tagRepository.getUserWiseTagCountForOrganization(fkUserId);
			String gatewayCount = gatewayRepo.getUserWiseGatewayCountForOrganization(fkUserId);
			GatewayAndTagCountbean bean = new GatewayAndTagCountbean();
			bean.setGatewayCount(gatewayCount);
			bean.setTagCountString(tagcount);

			return bean;

		}
		if (role.equals(CommonConstants.admin)) {
			String tagcount = tagRepository.getUserWiseTagCountForAdmin(fkUserId);
			String gatewayCount = gatewayRepo.getUserWiseGatewayCountForAdmin(fkUserId);
			GatewayAndTagCountbean bean = new GatewayAndTagCountbean();
			bean.setGatewayCount(gatewayCount);
			bean.setTagCountString(tagcount);

			return bean;
		}
		if (role.equals(CommonConstants.user)) {
			String tagcount = tagRepository.getUserWiseTagCountForUser(fkUserId);
			String gatewayCount = gatewayRepo.getUserWiseGatewayCountForUser(fkUserId);
			GatewayAndTagCountbean bean = new GatewayAndTagCountbean();
			bean.setGatewayCount(gatewayCount);
			bean.setTagCountString(tagcount);

			return bean;
		}
		return null;

	}

	/**
	 * use to get All User List
	 * 
	 * @param userName
	 * @param loginrole
	 * @param searchrole
	 * @param fkUserId
	 * @param category
	 * @param pageable
	 * @return
	 */
	public Page<UserInfobean> getAllUserListXl(String userName, String loginrole, String searchrole, String fkUserId,
			String category, Pageable pageable) {
		if (!searchrole.equals("")) {

			if (loginrole.equals(CommonConstants.superAdmin)) {

				if (searchrole.equals(CommonConstants.empUser)) {
					/// Long fkid=Long.parseLong(fkUserId);
					// List<EmployeeUser>
					/// employeeRepo.getAllUserDListForAdmin(Long.parseLong(fkUserId), searchrole,
					/// pageable);
					List<EmployeeUser> empobj = employeeRepo.getAllUserDListForSuperAdmin(pageable);
					List<UserInfobean> list = new ArrayList<UserInfobean>();
					for (int i = 0; i < empobj.size(); i++) {
						UserInfobean userDet = new UserInfobean();
						String adminid = employeeRepo.getAdminId1(fkUserId);
						GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(adminid, CommonConstants.admin);
						userDet.setPkuserId(empobj.get(i).getPkuserId());
						userDet.setFirstName(empobj.get(i).getFirstName());
						userDet.setLastName(empobj.get(i).getLastName());
						userDet.setEmailId(empobj.get(i).getEmailId());
						userDet.setPhoneNumber(empobj.get(i).getPhoneNumber());
						userDet.setPassword(empobj.get(i).getPassword());
						userDet.setRole(empobj.get(i).getRole());
						userDet.setUsername(empobj.get(i).getUsername());
						userDet.setCompanyName(empobj.get(i).getCompanyName());
						userDet.setGatewayCount(countobj.getGatewayCount());
						userDet.setTagCount(countobj.getTagCountString());
						userDet.setAddress(empobj.get(i).getAddress());
						list.add(userDet);
					}

					final Page<UserInfobean> page = new PageImpl<>(list);
					return page;

				} else {

					List<UserDetail> userobj = userRepo.getAllUserDListForSuperAdmin(searchrole, category, pageable);
					List<UserInfobean> list = new ArrayList<UserInfobean>();
					for (int i = 0; i < userobj.size(); i++) {
						UserInfobean ubean = new UserInfobean();
						GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
								String.valueOf(userobj.get(i).getPkuserId()), userobj.get(i).getRole());
						ubean.setPkuserId(userobj.get(i).getPkuserId());
						ubean.setFirstName(userobj.get(i).getFirstName());
						ubean.setLastName(userobj.get(i).getLastName());
						ubean.setCompanyName(userobj.get(i).getCompanyName());
						ubean.setEmailId(userobj.get(i).getEmailId());
						ubean.setPassword(userobj.get(i).getPassword());
						ubean.setPhoneNumber(userobj.get(i).getPhoneNumber());
						ubean.setCategory(userobj.get(i).getCategory());

						ubean.setAddress(userobj.get(i).getAddress());
						ubean.setAdmin(userobj.get(i).getAdmin());
						ubean.setOrganization(userobj.get(i).getOrganization());
						ubean.setUser(userobj.get(i).getUser());
						ubean.setUsername(userobj.get(i).getUsername());
						ubean.setRole(userobj.get(i).getRole());
						ubean.setGatewayCount(countobj.getGatewayCount());
						ubean.setTagCount(countobj.getTagCountString());

						list.add(ubean);

					}

					final Page<UserInfobean> page = new PageImpl<>(list);
					return page;

				}

			}

			if (loginrole.equals(CommonConstants.organization)) {
				if (searchrole.equals(CommonConstants.empUser)) {
					Long fkid = Long.parseLong(fkUserId);
					// List<EmployeeUser>
					// employeeRepo.getAllUserDListForAdmin(Long.parseLong(fkUserId), searchrole,
					// pageable);
					List<EmployeeUser> empobj = employeeRepo.getAllUserDListForOrganization(fkid, searchrole, pageable);
					List<UserInfobean> list = new ArrayList<UserInfobean>();
					for (int i = 0; i < empobj.size(); i++) {
						UserInfobean userDet = new UserInfobean();
						GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
								String.valueOf(empobj.get(i).getPkuserId()), loginrole);
						userDet.setPkuserId(empobj.get(i).getPkuserId());
						userDet.setFirstName(empobj.get(i).getFirstName());
						userDet.setLastName(empobj.get(i).getLastName());
						userDet.setEmailId(empobj.get(i).getEmailId());
						userDet.setPhoneNumber(empobj.get(i).getPhoneNumber());
						userDet.setPassword(empobj.get(i).getPassword());
						userDet.setRole(empobj.get(i).getRole());
						userDet.setUsername(empobj.get(i).getUsername());
						userDet.setCompanyName(empobj.get(i).getCompanyName());
						userDet.setGatewayCount(countobj.getGatewayCount());
						userDet.setTagCount(countobj.getTagCountString());
						userDet.setAddress(empobj.get(i).getAddress());
						list.add(userDet);
					}

					final Page<UserInfobean> page = new PageImpl<>(list);
					return page;

				} else {
					Long fkid = Long.parseLong(fkUserId);
					// List<EmployeeUser>
					// employeeRepo.getAllUserDListForAdmin(Long.parseLong(fkUserId), searchrole,
					// pageable);
					List<UserDetail> userobj = userRepo.getAllUserListForOrganization(userName, searchrole, category,
							pageable);
					List<UserInfobean> list = new ArrayList<UserInfobean>();
					for (int i = 0; i < userobj.size(); i++) {
						UserInfobean userDet = new UserInfobean();
						GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
								String.valueOf(userobj.get(i).getPkuserId()), loginrole);
						userDet.setPkuserId(userobj.get(i).getPkuserId());
						userDet.setFirstName(userobj.get(i).getFirstName());
						userDet.setLastName(userobj.get(i).getLastName());
						userDet.setEmailId(userobj.get(i).getEmailId());
						userDet.setPhoneNumber(userobj.get(i).getPhoneNumber());
						userDet.setPassword(userobj.get(i).getPassword());
						userDet.setRole(userobj.get(i).getRole());
						userDet.setUsername(userobj.get(i).getUsername());
						userDet.setCompanyName(userobj.get(i).getCompanyName());
						userDet.setGatewayCount(countobj.getGatewayCount());
						userDet.setTagCount(countobj.getTagCountString());
						userDet.setAddress(userobj.get(i).getAddress());
						list.add(userDet);
					}

					final Page<UserInfobean> page = new PageImpl<>(list);
					return page;

				}

			}
			if (loginrole.equals(CommonConstants.admin)) {
				if (searchrole.equals(CommonConstants.empUser)) {
					Long fkid = Long.parseLong(fkUserId);

					List<EmployeeUser> empobj = employeeRepo.getAllUserDListForAdmin(fkid, searchrole, pageable);
					List<UserInfobean> list = new ArrayList<UserInfobean>();
					for (int i = 0; i < empobj.size(); i++) {
						UserInfobean userDet = new UserInfobean();
						GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
								String.valueOf(empobj.get(i).getPkuserId()), loginrole);
						userDet.setPkuserId(empobj.get(i).getPkuserId());
						userDet.setFirstName(empobj.get(i).getFirstName());
						userDet.setLastName(empobj.get(i).getLastName());
						userDet.setEmailId(empobj.get(i).getEmailId());
						userDet.setPhoneNumber(empobj.get(i).getPhoneNumber());
						userDet.setPassword(empobj.get(i).getPassword());
						userDet.setRole(empobj.get(i).getRole());
						userDet.setUsername(empobj.get(i).getUsername());
						userDet.setCompanyName(empobj.get(i).getCompanyName());
						userDet.setGatewayCount(countobj.getGatewayCount());
						userDet.setTagCount(countobj.getTagCountString());
						userDet.setAddress(empobj.get(i).getAddress());
						list.add(userDet);
					}

					final Page<UserInfobean> page = new PageImpl<>(list);
					return page;

				} else {

					Long fkid = Long.parseLong(fkUserId);

					List<UserDetail> userobj = userRepo.getAllUserListForAdmin(userName, searchrole, category,
							pageable);
					List<UserInfobean> list = new ArrayList<UserInfobean>();
					for (int i = 0; i < userobj.size(); i++) {
						UserInfobean userDet = new UserInfobean();
						GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
								String.valueOf(userobj.get(i).getPkuserId()), userobj.get(i).getRole());
						userDet.setPkuserId(userobj.get(i).getPkuserId());
						userDet.setFirstName(userobj.get(i).getFirstName());
						userDet.setLastName(userobj.get(i).getLastName());
						userDet.setEmailId(userobj.get(i).getEmailId());
						userDet.setPhoneNumber(userobj.get(i).getPhoneNumber());
						userDet.setPassword(userobj.get(i).getPassword());
						userDet.setRole(userobj.get(i).getRole());
						userDet.setUsername(userobj.get(i).getUsername());
						userDet.setCompanyName(userobj.get(i).getCompanyName());
						userDet.setGatewayCount(countobj.getGatewayCount());
						userDet.setOrganization(userobj.get(i).getOrganization());
						userDet.setAdmin(userobj.get(i).getAdmin());
						userDet.setTagCount(countobj.getTagCountString());
						userDet.setAddress(userobj.get(i).getAddress());
						userDet.setUser("N/A");
						userDet.setCategory(userobj.get(i).getCategory());
						list.add(userDet);
					}

					final Page<UserInfobean> page = new PageImpl<>(list);
					return page;

				}

			}
//				if(loginrole.equals(CommonConstants.user))
//				{
//						return userRepo.getAllUserListForUser(userName,searchrole,pageable);
//				}
			if (loginrole.equals(CommonConstants.empUser)) {
				String adminid = employeeRepo.getAdminId1(fkUserId);
				String adminName = userRepo.getadminName(adminid);
				List<UserDetail> userobj = userRepo.getAllUserListForAdmin(userName, adminName, category, pageable);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean userDet = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj.get(i).getPkuserId()), loginrole);
					userDet.setPkuserId(userobj.get(i).getPkuserId());
					userDet.setFirstName(userobj.get(i).getFirstName());
					userDet.setLastName(userobj.get(i).getLastName());
					userDet.setEmailId(userobj.get(i).getEmailId());
					userDet.setPhoneNumber(userobj.get(i).getPhoneNumber());
					userDet.setPassword(userobj.get(i).getPassword());
					userDet.setRole(userobj.get(i).getRole());
					userDet.setUsername(userobj.get(i).getUsername());
					userDet.setCompanyName(userobj.get(i).getCompanyName());
					userDet.setGatewayCount(countobj.getGatewayCount());
					userDet.setTagCount(countobj.getTagCountString());
					userDet.setAddress(userobj.get(i).getAddress());

					list.add(userDet);
				}

				final Page<UserInfobean> page = new PageImpl<>(list);
				return page;

			}
		} else {
			if (loginrole.equals(CommonConstants.superAdmin)) {

				List<UserDetail> userobj = userRepo.getAllUserListForSuperAdminxl(category, pageable);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean userDet = new UserInfobean();
					GatewayAndTagCountbean countobj = null;
					try {
						countobj = getUserWiseTagAndGatewayCount(String.valueOf(userobj.get(i).getPkuserId()),
								userobj.get(i).getRole());
						if (countobj.equals(null)) {
							countobj.setGatewayCount("N/A");
							countobj.setTagCountString("N/A");
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					userDet.setPkuserId(userobj.get(i).getPkuserId());
					userDet.setFirstName(userobj.get(i).getFirstName());
					userDet.setLastName(userobj.get(i).getLastName());
					userDet.setEmailId(userobj.get(i).getEmailId());
					userDet.setPhoneNumber(userobj.get(i).getPhoneNumber());
					userDet.setPassword(userobj.get(i).getPassword());
					userDet.setRole(userobj.get(i).getRole());
					userDet.setUsername(userobj.get(i).getUsername());
					userDet.setCompanyName(userobj.get(i).getCompanyName());
					userDet.setGatewayCount(countobj.getGatewayCount());
					userDet.setTagCount(countobj.getTagCountString());
					userDet.setAddress(userobj.get(i).getAddress());
					list.add(userDet);
				}

				final Page<UserInfobean> page = new PageImpl<>(list);
				return page;
			}
			if (loginrole.equals(CommonConstants.organization)) {

				List<UserDetail> userobj = userRepo.getAllUserListForOrganizationDefault(userName, category, pageable);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean userDet = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj.get(i).getPkuserId()), loginrole);
					userDet.setPkuserId(userobj.get(i).getPkuserId());
					userDet.setFirstName(userobj.get(i).getFirstName());
					userDet.setLastName(userobj.get(i).getLastName());
					userDet.setEmailId(userobj.get(i).getEmailId());
					userDet.setPhoneNumber(userobj.get(i).getPhoneNumber());
					userDet.setPassword(userobj.get(i).getPassword());
					userDet.setRole(userobj.get(i).getRole());
					userDet.setUsername(userobj.get(i).getUsername());
					userDet.setCompanyName(userobj.get(i).getCompanyName());
					userDet.setGatewayCount(countobj.getGatewayCount());
					userDet.setTagCount(countobj.getTagCountString());
					userDet.setAddress(userobj.get(i).getAddress());
					list.add(userDet);
				}

				final Page<UserInfobean> page = new PageImpl<>(list);
				return page;
			}
			if (loginrole.equals(CommonConstants.admin)) {

				List<UserDetail> userobj = userRepo.getAllUserListForAdminDefault(userName, category, pageable);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean userDet = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj.get(i).getPkuserId()), loginrole);
					userDet.setPkuserId(userobj.get(i).getPkuserId());
					userDet.setFirstName(userobj.get(i).getFirstName());
					userDet.setLastName(userobj.get(i).getLastName());
					userDet.setEmailId(userobj.get(i).getEmailId());
					userDet.setPhoneNumber(userobj.get(i).getPhoneNumber());
					userDet.setPassword(userobj.get(i).getPassword());
					userDet.setRole(userobj.get(i).getRole());
					userDet.setUsername(userobj.get(i).getUsername());
					userDet.setCompanyName(userobj.get(i).getCompanyName());
					userDet.setGatewayCount(countobj.getGatewayCount());
					userDet.setTagCount(countobj.getTagCountString());
					userDet.setAddress(userobj.get(i).getAddress());
					list.add(userDet);
				}

				final Page<UserInfobean> page = new PageImpl<>(list);
				return page;
			}
//				if(loginrole.equals(CommonConstants.user))
//				{
//						return userRepo.getAllUserListForUserDefault(userName,pageable);
//				}
			if (loginrole.equals(CommonConstants.empUser)) {
				String adminid = employeeRepo.getAdminId1(fkUserId);
				String adminName = userRepo.getadminName(adminid);

				List<UserDetail> userobj = userRepo.getAllUserListForAdminDefault(adminName, category, pageable);

				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean userDet = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj.get(i).getPkuserId()), loginrole);
					userDet.setPkuserId(userobj.get(i).getPkuserId());
					userDet.setFirstName(userobj.get(i).getFirstName());
					userDet.setLastName(userobj.get(i).getLastName());
					userDet.setEmailId(userobj.get(i).getEmailId());
					userDet.setPhoneNumber(userobj.get(i).getPhoneNumber());
					userDet.setPassword(userobj.get(i).getPassword());
					userDet.setRole(userobj.get(i).getRole());
					userDet.setUsername(userobj.get(i).getUsername());
					userDet.setCompanyName(userobj.get(i).getCompanyName());
					userDet.setGatewayCount(countobj.getGatewayCount());
					userDet.setTagCount(countobj.getTagCountString());
					userDet.setAddress(userobj.get(i).getAddress());
					list.add(userDet);
				}

				final Page<UserInfobean> page = new PageImpl<>(list);
				return page;
			}
		}

		return null;
	}

	/**
	 * use to get get All Admin List for drop
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	public List<String> getAllAdminListfordrop(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getallAdminListForSuperAdmin(category);
		}
		if (role.equals(CommonConstants.organization)) {
			String userName = userRepo.getUserName(fkUserId);
			return userRepo.getOrganizationWiseAdmin(userName, category);
		}

		return null;
	}

	/**
	 * use to get All User List XXl
	 * 
	 * @param userName
	 * @param role
	 * @param searchrole
	 * @param category
	 * @param pageable
	 * @return
	 */
	public Page<UserInfobean> getAllUserListXXl(String userName, String role, String searchrole, String category,
			Pageable pageable) {
		if (role.equals(CommonConstants.superAdmin)) {
			if (searchrole.equals("")) {
				List<UserDetail> userobj = userRepo.getUserForSuperAdmin(category);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				System.out.println("befour size" + userobj.size());
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = null;
					int icnt = 0;
					try {
						countobj = getUserWiseTagAndGatewayCount(String.valueOf(userobj.get(i).getPkuserId()),
								userobj.get(i).getRole());
						if (countobj == null) {
							icnt++;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					ubean.setPkuserId(userobj.get(i).getPkuserId());
					ubean.setFirstName(userobj.get(i).getFirstName());
					ubean.setLastName(userobj.get(i).getLastName());
					ubean.setCompanyName(userobj.get(i).getCompanyName());
					ubean.setEmailId(userobj.get(i).getEmailId());
					ubean.setPassword(userobj.get(i).getPassword());
					ubean.setPhoneNumber(userobj.get(i).getPhoneNumber());
					ubean.setCategory(userobj.get(i).getCategory());
					ubean.setStatus(userobj.get(i).getStatus());
					ubean.setAddress(userobj.get(i).getAddress());
					ubean.setAdmin(userobj.get(i).getAdmin());
					ubean.setOrganization(userobj.get(i).getOrganization());
					ubean.setUser(userobj.get(i).getUser());
					ubean.setUsername(userobj.get(i).getUsername());
					ubean.setRole(userobj.get(i).getRole());
					if (icnt == 0) {
						ubean.setGatewayCount(countobj.getGatewayCount());
						ubean.setTagCount(countobj.getTagCountString());
					} else {
						ubean.setGatewayCount("0");
						ubean.setTagCount("0");
					}
					ubean.setCreatedby(userobj.get(i).getCreatedby());
					list.add(ubean);
				}
				System.out.println("after size" + list.size());
				List<EmployeeUser> userobj1 = employeeRepo.getUnderSuperAdmin(category);
				List<UserInfobean> list1 = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj1.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = null;
					int icnt11 = 0;
					try {
						countobj = getUserWiseTagAndGatewayCount(String.valueOf(userobj1.get(i).getPkuserId()),
								CommonConstants.admin);
						if (countobj == null) {
							icnt11++;
						}

					} catch (Exception e) {

					}
					ubean.setPkuserId(userobj1.get(i).getPkuserId());
					ubean.setFirstName(userobj1.get(i).getFirstName());
					ubean.setLastName(userobj1.get(i).getLastName());
					ubean.setCompanyName(userobj1.get(i).getCompanyName());
					ubean.setEmailId(userobj1.get(i).getEmailId());
					ubean.setPassword(userobj1.get(i).getPassword());
					ubean.setPhoneNumber(userobj1.get(i).getPhoneNumber());
					// ubean.setCategory(userobj.get(i).get);
					ubean.setStatus(userobj1.get(i).getStatus());
					ubean.setAddress(userobj1.get(i).getAddress());
					ubean.setAdmin(userobj1.get(i).getAdmin());
					ubean.setOrganization(userobj1.get(i).getOrganization());
					// ubean.setUser(userobj.get(i).getUser());
					ubean.setUsername(userobj1.get(i).getUsername());
					ubean.setRole(userobj1.get(i).getRole());
					if (icnt11 == 0) {
						ubean.setGatewayCount(countobj.getGatewayCount());
						ubean.setTagCount(countobj.getTagCountString());
					} else {
						ubean.setGatewayCount("0");
						ubean.setTagCount("0");
					}
					ubean.setCategory(userobj1.get(i).getCategory());
					ubean.setCreatedby(userobj1.get(i).getCreatedby());
					list.add(ubean);
				}
				System.out.println("after size" + list);
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), userobj.size());
				final Page<UserInfobean> page = new PageImpl<>(list.subList(start, end), pageable, userobj.size());
				// final Page<UserInfobean> page = new PageImpl<>(list);

				return page;
			}

			if (searchrole.equals(CommonConstants.admin)) {
				List<UserDetail> userobj = userRepo.getUserSuperAdmin(category);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				System.out.println("befour size" + userobj.size());
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj.get(i).getPkuserId()), userobj.get(i).getRole());
					ubean.setPkuserId(userobj.get(i).getPkuserId());
					ubean.setFirstName(userobj.get(i).getFirstName());
					ubean.setLastName(userobj.get(i).getLastName());
					ubean.setCompanyName(userobj.get(i).getCompanyName());
					ubean.setEmailId(userobj.get(i).getEmailId());
					ubean.setPassword(userobj.get(i).getPassword());
					ubean.setPhoneNumber(userobj.get(i).getPhoneNumber());
					ubean.setCategory(userobj.get(i).getCategory());
					ubean.setStatus(userobj.get(i).getStatus());
					ubean.setAddress(userobj.get(i).getAddress());
					ubean.setAdmin(userobj.get(i).getAdmin());
					ubean.setOrganization(userobj.get(i).getOrganization());
					ubean.setUser(userobj.get(i).getUser());
					ubean.setUsername(userobj.get(i).getUsername());
					ubean.setRole(userobj.get(i).getRole());
					ubean.setCreatedby(userobj.get(i).getCreatedby());
					ubean.setGatewayCount(countobj.getGatewayCount());
					ubean.setTagCount(countobj.getTagCountString());

					list.add(ubean);
				}
				System.out.println("after size" + list.size());
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), userobj.size());
				final Page<UserInfobean> page = new PageImpl<>(list.subList(start, end), pageable, userobj.size());
				// final Page<UserInfobean> page = new PageImpl<>(list);
				System.out.println("page" + page);
				return page;
			}

			// return userRepo.getallAdminListForSuperAdmin();

			if (searchrole.equals(CommonConstants.empUser))// pending employee category
			{
				List<String> adminids = userRepo.getAllUserNameOfAdminUnderOrganization(userName);
				List<EmployeeUser> userobj1 = employeeRepo.getUnderSuperAdmin(category);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj1.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj1.get(i).getPkuserId()), CommonConstants.admin);
					ubean.setPkuserId(userobj1.get(i).getPkuserId());
					ubean.setFirstName(userobj1.get(i).getFirstName());
					ubean.setLastName(userobj1.get(i).getLastName());
					ubean.setCompanyName(userobj1.get(i).getCompanyName());
					ubean.setEmailId(userobj1.get(i).getEmailId());
					ubean.setPassword(userobj1.get(i).getPassword());
					ubean.setPhoneNumber(userobj1.get(i).getPhoneNumber());
					// ubean.setCategory(userobj.get(i).get);
					ubean.setStatus(userobj1.get(i).getStatus());
					ubean.setAddress(userobj1.get(i).getAddress());
					ubean.setAdmin(userobj1.get(i).getAdmin());
					ubean.setOrganization(userobj1.get(i).getOrganization());
					// ubean.setUser(userobj1.get(i).getUser());
					ubean.setUsername(userobj1.get(i).getUsername());
					ubean.setRole(userobj1.get(i).getRole());
					ubean.setGatewayCount(countobj.getGatewayCount());
					ubean.setTagCount(countobj.getTagCountString());
					ubean.setCreatedby(userobj1.get(i).getCreatedby());
					ubean.setCategory(userobj1.get(i).getCategory());
					list.add(ubean);
				}
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), userobj1.size());
				final Page<UserInfobean> page = new PageImpl<>(list.subList(start, end), pageable, userobj1.size());
				// final Page<UserInfobean> page = new PageImpl<>(list);
				return page;

			}

			if (searchrole.equals(CommonConstants.user)) {
				List<UserDetail> userobj = userRepo.getUserUnderSuperAdmin(category);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj.get(i).getPkuserId()), userobj.get(i).getRole());
					ubean.setPkuserId(userobj.get(i).getPkuserId());
					ubean.setFirstName(userobj.get(i).getFirstName());
					ubean.setLastName(userobj.get(i).getLastName());
					ubean.setCompanyName(userobj.get(i).getCompanyName());
					ubean.setEmailId(userobj.get(i).getEmailId());
					ubean.setPassword(userobj.get(i).getPassword());
					ubean.setPhoneNumber(userobj.get(i).getPhoneNumber());
					ubean.setCategory(userobj.get(i).getCategory());
					ubean.setStatus(userobj.get(i).getStatus());
					ubean.setAddress(userobj.get(i).getAddress());
					ubean.setAdmin(userobj.get(i).getAdmin());
					ubean.setOrganization(userobj.get(i).getOrganization());
					ubean.setUser(userobj.get(i).getUser());
					ubean.setUsername(userobj.get(i).getUsername());
					ubean.setRole(userobj.get(i).getRole());
					ubean.setGatewayCount(countobj.getGatewayCount());
					ubean.setTagCount(countobj.getTagCountString());
					ubean.setCreatedby(userobj.get(i).getCreatedby());
					list.add(ubean);
				}
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), userobj.size());
				final Page<UserInfobean> page = new PageImpl<>(list.subList(start, end), pageable, userobj.size());
				// final Page<UserInfobean> page = new PageImpl<>(list);
				return page;

			}

		}
		if (role.equals(CommonConstants.organization)) {
			// all visible
			if (searchrole.equals("")) {
				List<UserInfobean> list1 = new ArrayList<UserInfobean>();
				List<UserInfobean> list3 = new ArrayList<UserInfobean>();
				List<String> adminidList = userRepo.getAminIdUnderOrganization(userName);

				for (int icnt = 0; icnt < adminidList.size(); icnt++) {

					List<EmployeeUser> userobj1 = employeeRepo.getEmpUserUnderAdmin(adminidList.get(icnt), category);

					for (int i = 0; i < userobj1.size(); i++) {
						UserInfobean ubean = new UserInfobean();
						GatewayAndTagCountbean countobj = null;
						int icnt1 = 0;
						try {
							countobj = getUserWiseTagAndGatewayCount(String.valueOf(userobj1.get(i).getPkuserId()),
									CommonConstants.admin);
							if (countobj == null) {
								icnt1++;
							}
						} catch (Exception e) {
							// TODO: handle exception
						}

						ubean.setPkuserId(userobj1.get(i).getPkuserId());
						ubean.setFirstName(userobj1.get(i).getFirstName());
						ubean.setLastName(userobj1.get(i).getLastName());
						ubean.setCompanyName(userobj1.get(i).getCompanyName());
						ubean.setEmailId(userobj1.get(i).getEmailId());
						ubean.setPassword(userobj1.get(i).getPassword());
						ubean.setPhoneNumber(userobj1.get(i).getPhoneNumber());
						// ubean.setCategory(userobj.get(i).get);
						ubean.setStatus(userobj1.get(i).getStatus());
						ubean.setAddress(userobj1.get(i).getAddress());
						ubean.setAdmin(userobj1.get(i).getAdmin());
						ubean.setOrganization(userobj1.get(i).getOrganization());
						// ubean.setUser(userobj1.get(i).getUser());
						ubean.setUsername(userobj1.get(i).getUsername());
						ubean.setRole(userobj1.get(i).getRole());
						if (icnt1 == 0) {
							ubean.setGatewayCount(countobj.getGatewayCount());
							ubean.setTagCount(countobj.getTagCountString());
						} else {
							ubean.setGatewayCount("0");
							ubean.setTagCount("0");
						}

						ubean.setCreatedby(userobj1.get(i).getCreatedby());
						ubean.setCategory(userobj1.get(i).getCategory());
						list1.add(ubean);
					}

				}

				for (int icnt1 = 0; icnt1 < adminidList.size(); icnt1++) {

					// List<UserInfobean> list2 = new ArrayList<UserInfobean>();
					String username = userRepo.getAdminUserNameforOrganization(adminidList.get(icnt1));
					List<UserDetail> userobj = userRepo.getUserUnderAdminForOrganization(username, category);
					for (int i = 0; i < userobj.size(); i++) {
						UserInfobean ubean = new UserInfobean();
						GatewayAndTagCountbean countobj = null;
						int icnt = 0;
						try {
							countobj = getUserWiseTagAndGatewayCount(String.valueOf(userobj.get(i).getPkuserId()),
									userobj.get(i).getRole());
							if (countobj == null) {
								icnt++;
							}
						} catch (Exception e) {
							System.out.println(countobj);
						}

						ubean.setPkuserId(userobj.get(i).getPkuserId());
						ubean.setFirstName(userobj.get(i).getFirstName());
						ubean.setLastName(userobj.get(i).getLastName());
						ubean.setCompanyName(userobj.get(i).getCompanyName());
						ubean.setEmailId(userobj.get(i).getEmailId());
						ubean.setPassword(userobj.get(i).getPassword());
						ubean.setPhoneNumber(userobj.get(i).getPhoneNumber());
						ubean.setCategory(userobj.get(i).getCategory());
						ubean.setStatus(userobj.get(i).getStatus());
						ubean.setAddress(userobj.get(i).getAddress());
						ubean.setAdmin(userobj.get(i).getAdmin());
						ubean.setOrganization(userobj.get(i).getOrganization());
						ubean.setUser(userobj.get(i).getUser());
						ubean.setUsername(userobj.get(i).getUsername());
						ubean.setRole(userobj.get(i).getRole());
						ubean.setCreatedby(userobj.get(i).getCreatedby());
						if (icnt == 0) {
							ubean.setGatewayCount(countobj.getGatewayCount());
							ubean.setTagCount(countobj.getTagCountString());
						} else {
							ubean.setGatewayCount("0");
							ubean.setTagCount("0");
						}

						list1.add(ubean);
					}

				}
				List<UserDetail> userobj2 = userRepo.getAdminUnderOrganization(userName, category);
				/// List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj2.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj2.get(i).getPkuserId()), userobj2.get(i).getRole());
					ubean.setPkuserId(userobj2.get(i).getPkuserId());
					ubean.setFirstName(userobj2.get(i).getFirstName());
					ubean.setLastName(userobj2.get(i).getLastName());
					ubean.setCompanyName(userobj2.get(i).getCompanyName());
					ubean.setEmailId(userobj2.get(i).getEmailId());
					ubean.setPassword(userobj2.get(i).getPassword());
					ubean.setPhoneNumber(userobj2.get(i).getPhoneNumber());
					ubean.setCategory(userobj2.get(i).getCategory());
					ubean.setStatus(userobj2.get(i).getStatus());
					ubean.setAddress(userobj2.get(i).getAddress());
					ubean.setAdmin(userobj2.get(i).getAdmin());
					ubean.setOrganization(userobj2.get(i).getOrganization());
					ubean.setUser(userobj2.get(i).getUser());
					ubean.setUsername(userobj2.get(i).getUsername());
					ubean.setRole(userobj2.get(i).getRole());
					ubean.setCreatedby(userobj2.get(i).getCreatedby());
					ubean.setGatewayCount(countobj.getGatewayCount());
					ubean.setTagCount(countobj.getTagCountString());

					list1.add(ubean);
				}

				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), list1.size());// userobj.size()+userobj1.size()
				final Page<UserInfobean> page = new PageImpl<>(list1.subList(start, end), pageable, list1.size());
				// final Page<UserInfobean> page = new PageImpl<>(list1);
				return page;

			}

			if (searchrole.equals(CommonConstants.admin)) {
				List<UserDetail> userobj = userRepo.getAdminUnderOrganization(userName, category);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj.get(i).getPkuserId()), userobj.get(i).getRole());
					ubean.setPkuserId(userobj.get(i).getPkuserId());
					ubean.setFirstName(userobj.get(i).getFirstName());
					ubean.setLastName(userobj.get(i).getLastName());
					ubean.setCompanyName(userobj.get(i).getCompanyName());
					ubean.setEmailId(userobj.get(i).getEmailId());
					ubean.setPassword(userobj.get(i).getPassword());
					ubean.setPhoneNumber(userobj.get(i).getPhoneNumber());
					ubean.setCategory(userobj.get(i).getCategory());
					ubean.setStatus(userobj.get(i).getStatus());
					ubean.setAddress(userobj.get(i).getAddress());
					ubean.setAdmin(userobj.get(i).getAdmin());
					ubean.setOrganization(userobj.get(i).getOrganization());
					ubean.setUser(userobj.get(i).getUser());
					ubean.setUsername(userobj.get(i).getUsername());
					ubean.setRole(userobj.get(i).getRole());
					ubean.setCreatedby(userobj.get(i).getCreatedby());
					ubean.setGatewayCount(countobj.getGatewayCount());
					ubean.setTagCount(countobj.getTagCountString());

					list.add(ubean);
				}
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), userobj.size());
				final Page<UserInfobean> page = new PageImpl<>(list.subList(start, end), pageable, userobj.size());
				// final Page<UserInfobean> page = new PageImpl<>(list);
				return page;
			}

			if (searchrole.equals(CommonConstants.empUser))// pending employee category
			{
				List<String> adminid = userRepo.getAllAminIdOnUserNameforOrganization(userName);

				List<UserInfobean> list = new ArrayList<UserInfobean>();
				List<UserInfobean> list1 = new ArrayList<UserInfobean>();

				List<EmployeeUser> userobj1 = null;
				int tsize = 0;
				for (int icnt = 0; icnt < adminid.size(); icnt++) {
					System.out.println("adminid.get(icnt)-" + adminid.get(icnt));

					userobj1 = employeeRepo.getUserUnderAdminxx(adminid.get(icnt));

					for (int i = 0; i < userobj1.size(); i++) {
						UserInfobean ubean = new UserInfobean();
						GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
								String.valueOf(userobj1.get(i).getPkuserId()), CommonConstants.admin);
						ubean.setPkuserId(userobj1.get(i).getPkuserId());
						ubean.setFirstName(userobj1.get(i).getFirstName());
						ubean.setLastName(userobj1.get(i).getLastName());
						ubean.setCompanyName(userobj1.get(i).getCompanyName());
						ubean.setEmailId(userobj1.get(i).getEmailId());
						ubean.setPassword(userobj1.get(i).getPassword());
						ubean.setPhoneNumber(userobj1.get(i).getPhoneNumber());
						// ubean.setCategory(userobj.get(i).get);
						ubean.setStatus(userobj1.get(i).getStatus());
						ubean.setAddress(userobj1.get(i).getAddress());
						ubean.setAdmin(userobj1.get(i).getAdmin());
						ubean.setOrganization(userobj1.get(i).getOrganization());
						// ubean.setUser(userobj1.get(i).get);
						ubean.setUsername(userobj1.get(i).getUsername());
						ubean.setRole(userobj1.get(i).getRole());
						ubean.setGatewayCount(countobj.getGatewayCount());
						ubean.setTagCount(countobj.getTagCountString());
						ubean.setCategory(userobj1.get(i).getCategory());
						ubean.setCreatedby(userobj1.get(i).getCreatedby());
						list.add(ubean);
					}
					int icnt2 = 0;
					for (int i = tsize; i < userobj1.size(); i++) {
						list1.add(i, list.get(icnt2));
						icnt2++;
					}
					tsize = tsize + list1.size() - 1;
					System.out.println("list.size()-" + list.size());
				}
				System.out.println("list.size() final -" + list1);
				System.out.println("tsize -" + tsize);
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), list1.size());
				final Page<UserInfobean> page = new PageImpl<>(list1.subList(start, end), pageable, list1.size());
				// final Page<UserInfobean> page = new PageImpl<>(list);
				return page;

			}

			if (searchrole.equals(CommonConstants.user)) {
				List<String> adminid = userRepo.getAllAminIdOnUserNameforOrganization(userName);

				List<UserInfobean> list = new ArrayList<UserInfobean>();
				List<UserInfobean> list1 = new ArrayList<UserInfobean>();
				List<UserDetail> userobj1 = null;
				int tsize = 0;
				for (int icnt = 0; icnt < adminid.size(); icnt++) {

					System.out.println("adminid.get(icnt)->" + adminid.get(icnt));
					String adminName = userRepo.getAdminUserName(adminid.get(icnt));
					userobj1 = userRepo.getUserUnderAdminxx(adminName);
					for (int i = 0; i < userobj1.size(); i++) {
						UserInfobean ubean = new UserInfobean();
						GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
								String.valueOf(userobj1.get(i).getPkuserId()), userobj1.get(i).getRole());
						ubean.setPkuserId(userobj1.get(i).getPkuserId());
						ubean.setFirstName(userobj1.get(i).getFirstName());
						ubean.setLastName(userobj1.get(i).getLastName());
						ubean.setCompanyName(userobj1.get(i).getCompanyName());
						ubean.setEmailId(userobj1.get(i).getEmailId());
						ubean.setPassword(userobj1.get(i).getPassword());
						ubean.setPhoneNumber(userobj1.get(i).getPhoneNumber());
						ubean.setCategory(userobj1.get(i).getCategory());
						ubean.setStatus(userobj1.get(i).getStatus());
						ubean.setAddress(userobj1.get(i).getAddress());
						ubean.setAdmin(userobj1.get(i).getAdmin());
						ubean.setOrganization(userobj1.get(i).getOrganization());
						ubean.setUser(userobj1.get(i).getUser());
						ubean.setUsername(userobj1.get(i).getUsername());
						ubean.setRole(userobj1.get(i).getRole());
						ubean.setCreatedby(userobj1.get(i).getCreatedby());
						ubean.setGatewayCount(countobj.getGatewayCount());
						ubean.setTagCount(countobj.getTagCountString());

						list.add(ubean);
					}

					int icnt2 = 0;
					if (tsize < 0) {
						tsize = 0;
					}
					for (int i = tsize; i < userobj1.size(); i++) {
						list1.add(i, list.get(icnt2));
						icnt2++;
					}
					tsize = tsize + list1.size() - 1;
					System.out.println("list.size()-" + list.size());
				}
				System.out.println("list.size() final -" + list1);
				System.out.println("tsize -" + tsize);
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), list1.size());
				final Page<UserInfobean> page = new PageImpl<>(list1.subList(start, end), pageable, list1.size());
				// final Page<UserInfobean> page = new PageImpl<>(list);
				return page;

			}

		}

		if (role.equals(CommonConstants.admin)) {
			if (searchrole.equals("")) {
				long adminid = userRepo.getAminIdOnUserName(userName);
				List<EmployeeUser> userobj1 = employeeRepo.getUserUnderAdmin(adminid);
				List<UserInfobean> list1 = new ArrayList<UserInfobean>();
				List<UserInfobean> list3 = new ArrayList<UserInfobean>();
				System.out.println("userobj1.size()@@@@@@@@@@@@@@@@@@@@" + userobj1.size());
				for (int i = 0; i < userobj1.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj1.get(i).getPkuserId()), CommonConstants.admin);
					ubean.setPkuserId(userobj1.get(i).getPkuserId());
					ubean.setFirstName(userobj1.get(i).getFirstName());
					ubean.setLastName(userobj1.get(i).getLastName());
					ubean.setCompanyName(userobj1.get(i).getCompanyName());
					ubean.setEmailId(userobj1.get(i).getEmailId());
					ubean.setPassword(userobj1.get(i).getPassword());
					ubean.setPhoneNumber(userobj1.get(i).getPhoneNumber());
					// ubean.setCategory(userobj.get(i).get);
					ubean.setStatus(userobj1.get(i).getStatus());
					ubean.setAddress(userobj1.get(i).getAddress());
					ubean.setAdmin(userobj1.get(i).getAdmin());
					ubean.setOrganization(userobj1.get(i).getOrganization());
					// ubean.setUser(userobj1.get(i).getUser());
					ubean.setUsername(userobj1.get(i).getUsername());
					ubean.setRole(userobj1.get(i).getRole());
					ubean.setGatewayCount(countobj.getGatewayCount());
					ubean.setTagCount(countobj.getTagCountString());
					ubean.setCreatedby(userobj1.get(i).getCreatedby());
					ubean.setCategory(userobj1.get(i).getCategory());
					list1.add(ubean);
				}
				List<UserDetail> userobj = userRepo.getUserUnderAdmin(userName, category);
				List<UserInfobean> list2 = new ArrayList<UserInfobean>();
				System.out.println("list1@@@@@@@@@@@@@@@@@@@@" + list1.size());
				System.out.println("userobj.size()@@@@@@@@@@@@@@@@@@@@" + userobj.size());
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj.get(i).getPkuserId()), userobj.get(i).getRole());
					ubean.setPkuserId(userobj.get(i).getPkuserId());
					ubean.setFirstName(userobj.get(i).getFirstName());
					ubean.setLastName(userobj.get(i).getLastName());
					ubean.setCompanyName(userobj.get(i).getCompanyName());
					ubean.setEmailId(userobj.get(i).getEmailId());
					ubean.setPassword(userobj.get(i).getPassword());
					ubean.setPhoneNumber(userobj.get(i).getPhoneNumber());
					ubean.setCategory(userobj.get(i).getCategory());
					ubean.setStatus(userobj.get(i).getStatus());
					ubean.setAddress(userobj.get(i).getAddress());
					ubean.setAdmin(userobj.get(i).getAdmin());
					ubean.setOrganization(userobj.get(i).getOrganization());
					ubean.setUser(userobj.get(i).getUser());
					ubean.setUsername(userobj.get(i).getUsername());
					ubean.setRole(userobj.get(i).getRole());
					ubean.setCreatedby(userobj.get(i).getCreatedby());
					ubean.setGatewayCount(countobj.getGatewayCount());
					ubean.setTagCount(countobj.getTagCountString());

					list1.add(ubean);
				}
				System.out.println("list1@@@@@@@@@@@@@@@@@@@@@@@" + list1.size());
				list3 = list1;

				int icnt = 0;
//					for(int i=userobj1.size()-1;i<(userobj1.size()+userobj.size()-2);i++)
//					{
//						list3.add(i,list2.get(icnt));
//						icnt++;
//					}
				System.out.println("list1" + list3);
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), list1.size());
				final Page<UserInfobean> page = new PageImpl<>(list1.subList(start, end), pageable, list1.size());
				// final Page<UserInfobean> page = new PageImpl<>(list1);
				return page;

			}

			if (searchrole.equals(CommonConstants.empUser)) {
				long adminid = userRepo.getAminIdOnUserName(userName);
				List<EmployeeUser> userobj1 = employeeRepo.getUserUnderAdmin(adminid);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj1.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj1.get(i).getPkuserId()), CommonConstants.admin);
					ubean.setPkuserId(userobj1.get(i).getPkuserId());
					ubean.setFirstName(userobj1.get(i).getFirstName());
					ubean.setLastName(userobj1.get(i).getLastName());
					ubean.setCompanyName(userobj1.get(i).getCompanyName());
					ubean.setEmailId(userobj1.get(i).getEmailId());
					ubean.setPassword(userobj1.get(i).getPassword());
					ubean.setPhoneNumber(userobj1.get(i).getPhoneNumber());
					ubean.setCategory(userobj1.get(i).getCategory());
					ubean.setStatus(userobj1.get(i).getStatus());
					ubean.setAddress(userobj1.get(i).getAddress());
					ubean.setAdmin(userobj1.get(i).getAdmin());
					ubean.setOrganization(userobj1.get(i).getOrganization());
					// ubean.setUser(userobj1.get(i).getUser());
					ubean.setUsername(userobj1.get(i).getUsername());
					ubean.setRole(userobj1.get(i).getRole());
					ubean.setGatewayCount(countobj.getGatewayCount());
					ubean.setTagCount(countobj.getTagCountString());
					ubean.setCreatedby(userobj1.get(i).getCreatedby());
					list.add(ubean);
				}
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), userobj1.size());
				final Page<UserInfobean> page = new PageImpl<>(list.subList(start, end), pageable, userobj1.size());
				// final Page<UserInfobean> page = new PageImpl<>(list);
				return page;

			}
			if (searchrole.equals(CommonConstants.user)) {
				List<UserDetail> userobj = userRepo.getUserUnderAdmin(userName, category);
				List<UserInfobean> list = new ArrayList<UserInfobean>();
				for (int i = 0; i < userobj.size(); i++) {
					UserInfobean ubean = new UserInfobean();
					GatewayAndTagCountbean countobj = getUserWiseTagAndGatewayCount(
							String.valueOf(userobj.get(i).getPkuserId()), userobj.get(i).getRole());
					ubean.setPkuserId(userobj.get(i).getPkuserId());
					ubean.setFirstName(userobj.get(i).getFirstName());
					ubean.setLastName(userobj.get(i).getLastName());
					ubean.setCompanyName(userobj.get(i).getCompanyName());
					ubean.setEmailId(userobj.get(i).getEmailId());
					ubean.setPassword(userobj.get(i).getPassword());
					ubean.setPhoneNumber(userobj.get(i).getPhoneNumber());
					ubean.setCategory(userobj.get(i).getCategory());
					ubean.setStatus(userobj.get(i).getStatus());
					ubean.setAddress(userobj.get(i).getAddress());
					ubean.setAdmin(userobj.get(i).getAdmin());
					ubean.setOrganization(userobj.get(i).getOrganization());
					ubean.setUser(userobj.get(i).getUser());
					ubean.setUsername(userobj.get(i).getUsername());
					ubean.setRole(userobj.get(i).getRole());
					ubean.setCreatedby(userobj.get(i).getCreatedby());
					ubean.setGatewayCount(countobj.getGatewayCount());
					ubean.setTagCount(countobj.getTagCountString());

					list.add(ubean);
				}
				System.out.println("list" + list.size());
				final int start = (int) pageable.getOffset();
				final int end = Math.min((start + pageable.getPageSize()), userobj.size());
				final Page<UserInfobean> page = new PageImpl<>(list.subList(start, end), pageable, userobj.size());
				// final Page<UserInfobean> page = new PageImpl<>(list);
				return page;

			}
		}
		return null;

	}
//		public List<UserInfobean> getAllUserlistl(String userName) {
//			
//			return userRepo.getAdminListOnUserName(userName);
//		}

	/**
	 * 
	 * @return UserDetail list
	 */
	public List<UserDetail> getuserListForValidation() {
		List<UserDetail> userlistDetails = userRepo.getuserListForValidation();

		List<UserDetail> list1 = new ArrayList<UserDetail>();
		list1 = userlistDetails;
		System.out.println("list1" + list1);
		List<EmployeeUser> userlists = employeeRepo.getuserListForValidation();

		for (int i = 0; i < userlists.size(); i++) {
			UserDetail list = new UserDetail();
			list.setPkuserId(userlists.get(i).getPkuserId());
			list.setUsername(userlists.get(i).getUsername());
			list.setEmailId(userlists.get(i).getEmailId());
			list.setFirstName(userlists.get(i).getFirstName());
			list.setRole(userlists.get(i).getRole());

			list1.add(list);

		}
		System.out.println("userlistDetails" + userlistDetails);
		return userlistDetails;
	}

	/**
	 * use to change status of user
	 * 
	 * @param status
	 * @param pkuserid
	 * @param userrole
	 * @return String
	 */
	public String changestatusofuser(String status, String pkuserid, String userrole) {

		if (userrole.equals(CommonConstants.empUser)) {
			employeeRepo.enableUserStatus(status, pkuserid);
			return "User is :" + status + " Now";
		} else {
			if (userrole.equals(CommonConstants.organization)) {
				String organizationUserName = userRepo.getAdminUserNameforOrganization(pkuserid);
				List<String> adminList = userRepo.getAdminList(organizationUserName);
				for (int i = 0; i < adminList.size(); i++) {
					userRepo.enableUserStatus(status, adminList.get(i));
					String adminUserName = userRepo.getAdminUserNameforOrganization(adminList.get(i));
					List<String> userList = userRepo.getUserList(adminUserName);
					employeeRepo.enableUserStatusOnAdminid(status, adminList.get(i));
					for (int j = 0; j < userList.size(); j++) {
						userRepo.enableUserStatus(status, userList.get(j));
					}
				}
			}
			if (userrole.equals(CommonConstants.admin)) {
				String adminName = userRepo.getAdminUserNameforOrganization(pkuserid);
				userRepo.enableUserStatusOnUnderAdmin(status, adminName);
				employeeRepo.enableUserStatusOnAdminid(status, pkuserid);
			}
			userRepo.enableUserStatus(status, pkuserid);
			return "User is :" + status + " Now";
		}

	}

	/**
	 * use to get user List For With Organization Role
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail list
	 */
	public List<UserDetail> getuserListForWithOrganizationRole(String fkUserId, String role, String category) {

		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getAllOraganizationRole(category);
		}
		return null;

	}

	/**
	 * use to get user List For With Admin Role
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail list
	 */
	public List<UserDetail> getuserListForWithAdminRole(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getAllAdminListRole(category);

		}

		if (role.equals(CommonConstants.organization)) {
			String username = userRepo.getuserNaame(fkUserId);
			return userRepo.getAllOrganizationListRole(username, category);

		}
		return null;
	}

	/**
	 * use to get user List For With User Role
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail list
	 */
	public List<UserDetail> getuserListForWithUserRole(String fkUserId, String role, String category) {

		if (role.equals(CommonConstants.superAdmin)) {
			return userRepo.getAllUserListRole(category);

		}

		if (role.equals(CommonConstants.organization)) {
			String username = userRepo.getuserNaame(fkUserId);
			return userRepo.getAllUserListRoleForOrganization(username, category);

		}
		if (role.equals(CommonConstants.admin)) {
			String username = userRepo.getuserNaame(fkUserId);
			return userRepo.getAllUserListRoleForAdmin(username, category);

		}
		return null;

	}
//		public List<String> getAllAdminListfordropUserNameWise(String username, String category) {
//			if(role.equals(CommonConstants.superAdmin))
//			{
//				return userRepo.getallAdminListForSuperAdmin(category);
//			}
//			if(role.equals(CommonConstants.organization))
//			{
//				String userName=userRepo.getUserName(fkUserId);
//				return userRepo.getOrganizationWiseAdmin(userName,category);
//			}
//			return null;
//		}

	/**
	 * use to get All Admin List for drop User Name Wise
	 * 
	 * @param username
	 * @param category
	 * @return String
	 */
	public List<String> getAllAdminListfordropUserNameWise(String username, String category) {
//				if(role.equals(CommonConstants.superAdmin))
//				{
//					return userRepo.getallAdminListForSuperAdmin(category);
//				}
//				''

		return userRepo.getOrganizationWiseAdmin(username, category);

	}

	/**
	 * use to get All User Under Perticular Admin
	 * 
	 * @param adminname
	 * @return RequestPermissionDto list
	 */
	public List<RequestPermissionDto> getAllUserUnderPerticularAdmin(String adminname) {

		return userRepo.getAllUserUnderPerticularAdmin(adminname);
	}

	/**
	 * use to get admin List Created By SuperAdmin
	 * 
	 * @param userName
	 * @param role
	 * @param category
	 * @return UserDetail list
	 */
	public List<UserDetail> getadminListCreatedBySuperAdmin(String userName, String role, String category) {

		System.out.println("userName" + userName);
		return userRepo.getadminListCreatedBySuperAdmin(userName, category);

	}

}
