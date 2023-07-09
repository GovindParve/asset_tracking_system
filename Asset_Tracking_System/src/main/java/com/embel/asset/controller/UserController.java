
package com.embel.asset.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.embel.asset.bean.ResponseSigninBean;
import com.embel.asset.bean.UserDetailsResponsebean;
import com.embel.asset.bean.UserInfobean;
import com.embel.asset.bean.UserResponseBean;
import com.embel.asset.bean.UserResponseforissueBean;
import com.embel.asset.dto.RequestPermissionDto;
import com.embel.asset.dto.UserRequestDto;
import com.embel.asset.entity.EmployeeUser;
import com.embel.asset.entity.UserDetail;
import com.embel.asset.repository.UserRepository;
import com.embel.asset.service.EmployeUserService;
import com.embel.asset.service.UserService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin("*")
@RestController
public class UserController implements ErrorController{



	@Autowired
	UserService userService;
	@Autowired
	EmployeUserService employeUserService;
	@Autowired
	UserRepository userRepository;
	
	
	/**
	 * get all user list for validation
	 * @author Pratik Chaudhari
	 * @return UserDetail
	 */
	@GetMapping("/users/get-all-user-list-for-validation")
	public List<UserDetail> getuserListForValidation()
	{
		return userService.getuserListForValidation();
	}
	
	
/**
 * get admin List Created By SuperAdmin
 * @author Pratik Chaudhari
 * @param fkUserId
 * @param role
 * @param category
 * @return UserDetail
 */
	@GetMapping("/users/get-all-admin-list-created-by-super-admin")
	public List<UserDetail> getadminListCreatedBySuperAdmin(String fkUserId,String role,String category)
	{
		String userName =userRepository.getUserName(fkUserId);
		System.out.println("userName"+userName);
		return userService.getadminListCreatedBySuperAdmin(userName,role,category);
	}
	
	
	
	//----------------DAshBord Api
	/**
	 * get user List For With Organization Role
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return
	 */
	@GetMapping("/users/get-all-user-with-organization-role")
	public List<UserDetail> getuserListForWithOrganizationRole(String fkUserId,String role,String category)
	{
		return userService.getuserListForWithOrganizationRole(fkUserId,role,category);
	
	}
	
	/**
	 * get user List For With Admin Role
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail
	 */
	@GetMapping("/users/get-all-user-Admin-List")
	public List<UserDetail> getuserListForWithAdminRole(String fkUserId,String role,String category)
	{
		return userService.getuserListForWithAdminRole(fkUserId,role, category);
	}
	
	
	/**
	 * get all user User List
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail
	 */
	@GetMapping("/users/get-all-user-User-List")
	public List<UserDetail> getuserListForWithUserRole(String fkUserId,String role,String category)
	{
		return userService.getuserListForWithUserRole(fkUserId,role, category);
	}
	
	/**
	 * get Empuser List For With UserRole
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return EmployeeUser
	 */
	@GetMapping("/users/get-all-EmpUser-List")
	public List<EmployeeUser> getEmpuserListForWithUserRole(String fkUserId,String role,String category)
	{
		return employeUserService.getEmpuserListForWithUserRole(fkUserId,role, category);
	}
	//-----------------add user details---------------------------
	/**
	 * add user details
	 * @author Pratik Chaudhari
	 * @param requserDetails
	 * @return String
	 */
	@PostMapping("/users/signup")
	public String addUser(@RequestBody UserRequestDto requserDetails)
	{	
		UserDetail userDet = new UserDetail();
		userDet.setFirstName(requserDetails.getFirstName());
		userDet.setLastName(requserDetails.getLastName());
		userDet.setEmailId(requserDetails.getEmailId());
		userDet.setPhoneNumber(requserDetails.getPhoneNumber());
		userDet.setPassword(requserDetails.getPassword());
		userDet.setRole(requserDetails.getRole());
		userDet.setUsername(requserDetails.getUsername());
		userDet.setCompanyName(requserDetails.getCompanyName());
		userDet.setOrganization(requserDetails.getOrganization());
		userDet.setAdmin(requserDetails.getAdmin());
		userDet.setUser(requserDetails.getUser());
		userDet.setCreatedby(requserDetails.getCreatedby());
		userDet.setCategory(requserDetails.getCategory());
		userDet.setStatus(requserDetails.getStatus());
	System.out.println("requserDetails"+requserDetails);
		if(requserDetails.getAddress().equals(null))
		{
			userDet.setAddress("N/A");
		} else {
			userDet.setAddress(requserDetails.getAddress());
		}
		return userService.signup(userDet);
	}
	
	
	/**
	 * use to get refreshToken
	 * @param refreshToken
	 * @return Map 
	 */
	@PostMapping("/users/refreshToken")
	// @ApiOperation(value = "${UserController.signin}")
	
	public Map<String,String> refreshToken(@RequestParam String refreshToken) {
		Map<String,String> map=new HashMap<>();
		map.put("accessToken",userService.refreshToken(refreshToken));
		return map;
		
	}
	
	//-------------------update user for login
	/**
	 * update user for login
	 * @author Pratik chaudhari
	 * @param status
	 * @param pkuserid
	 * @param userrole
	 * @return String
	 */
	@PostMapping("/users/enable-status")
	public String changeStatus(String status,String pkuserid, String userrole)
	{
		return userService.changestatusofuser(status,pkuserid,userrole);
		
	}
	//----------user signin-------------------------------
	
	/**
	 * user signin
	 * @author Pratik chaudhari
	 * @param username
	 * @param password
	 * @param request
	 * @return ResponseSigninBean
	 */
	@GetMapping("/users/signin")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Something went wrong"), 
			@ApiResponse(code = 422, message = "Invalid username/password supplied")})

	public ResponseSigninBean login(@ApiParam("username") @RequestParam String username, @ApiParam("password") @RequestParam String password,HttpServletRequest request) 
	{
		return userService.signin(username, password,request);

	}
//......................
	
	
	/**
	 * get User Details For Contact
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @return UserResponseforissueBean
	 */
	@GetMapping("/user/get-user-details-for-issue-and-contactus/{fkUserId}")
	public UserResponseforissueBean getUserDetailsForContact(@PathVariable String fkUserId )
	{
		UserResponseforissueBean bean = userService.getUserDetailsForContact(Long.parseLong(fkUserId));
		if(bean==null)
		{
			return new UserResponseforissueBean();
		}
		return bean ;
	}

	//........All user list-pending 
	/*
	 * @Query(value="SELECT * FROM user_details u",nativeQuery=true)
	List<UserDetailsResponsebean> getOraganizationListForSuperAdmin(String category);
	
	@Query(value="",nativeQuery=true)
	List<UserDetailsResponsebean> getOraganizationListForAdmin(long fkUserId,String category);
*/
//	
//	@GetMapping("/user/get-all-user-details/{pageNo}")	
//	public Page<UserDetail> getAllUserList1(@PathVariable String pageNo,String createdby,String loginrole,String searchrole,String fkUserId)
//	 {
//		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
//		return userService.getAllUserDList(createdby,loginrole,searchrole,fkUserId,pageable);
//	 }
	
	
	/**
	 * get All UserList 
	 * @author Pratik Chaudhari
	 * @param pageNo
	 * @param userName
	 * @param loginrole
	 * @param searchrole
	 * @param fkUserId
	 * @param category
	 * @return UserInfobean
	 */
	@GetMapping("/user/get-all-user-details/{pageNo}")
	public Page<UserInfobean> getAllUserList1(@PathVariable String pageNo,String  userName,String loginrole,@RequestParam (required =false) String searchrole,String fkUserId,String category)
	 {
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return userService.getAllUserListXl(userName,loginrole,searchrole,fkUserId,category,pageable);
	 }
	/**
	 * use to get user list with role wise 
	 * @param pageNo
	 * @param userName
	 * @param role
	 * @param searchrole
	 * @param category
	 * @return UserInfobean
	 */
	@GetMapping("/user/get-all-userNameWise-list/{pageNo}")
	public Page<UserInfobean> getAllUserListxl(@PathVariable String pageNo,@RequestParam (required =false) String  userName,String role,@RequestParam (required =false) String searchrole ,String category)
	 {
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return userService.getAllUserListXXl(userName,role,searchrole,category,pageable);
	 }
	
//	@GetMapping("/user/get-admin-list")
//	public List<UserInfobean> getAdmin(String userName)
//	 {
//
//		return userService.getAllUserlistl(userName);
//	 }
//..........................................................
	//-----------Get user list as per role------------------------------
	
	/**
	 * Get user list as per role
	 * @param fkUserId
	 * @param role
	 * @return UserResponseBean
	 */
	@GetMapping("/users/getAllUsersPerRole")
	public List<UserResponseBean> getUserList(String fkUserId, String role)
	{		
		return  userService.getUserList(fkUserId,role);		
	}
///.........................................pagination.................................
	/**
	 * get all user with pagination 
	 * @param pageNo
	 * @param fkUserId
	 * @param role
	 * @return UserDetail
	 */
	@GetMapping("/users/getAllUsers-Pagination/{pageNo}")
	 public Page<UserDetail> getAllUserListWithPagination(@PathVariable String pageNo,String fkUserId, String role)
	{		
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return  userService.getUserListWithPaginationxl(fkUserId,role,pageable);		
	}
	
	/**
	 * get User List With Pagination
	 * @author Pratikchaudhari
	 * @param pageNo
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserResponseBean
	 */
	@GetMapping("/users/getAllUsersPerRole-Pagination/{pageNo}")
	 public Page<UserResponseBean> getUserListWithPagination(@PathVariable String pageNo,String fkUserId, String role,String category)
	{		
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return  userService.getUserListWithPagination(fkUserId,role,pageable,category);		
	}

	
	/**
	 * get User List With Pagination For Gps
	 * @author Pratik chaudhari
	 * @param pageNo
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail
	 */
	@GetMapping("/users/getAllUsersPerRoleCategoryWise-Pagination/{pageNo}")
	 public Page<UserDetail> getUserListWithPaginationForGps(@PathVariable String pageNo,String fkUserId, String role,String category)
	{		
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return  userService.getUserListWithPaginationForGps(fkUserId,role,pageable,category);		
	}
	
//.....................................................................................
	/**
	 *  Forgot Password functionality 
	 *  @author Pratik chaudhari
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@PostMapping("/users/forgot-password")
	public  ResponseEntity<Optional<String>> processForgotPassword(HttpServletRequest request) throws Exception
	{
	
		System.out.println("request"+request);
		String status=null;
		try {
			
			status= userService.processForgotPassword(request);
			System.out.println(status);
		return ResponseEntity.ok(Optional.of(status));
		} catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
		}
		
		
	}
	

	
	/**
	 * reset password functionality 
	 * @author Pratik chaudhari
	 * @param request
	 * @return  String
	 */
	@PostMapping("/users/reset-password")
	public String processResetPassword(HttpServletRequest request) {
		String token = request.getParameter("token");
		System.out.println("token is :"+token);
		String password = request.getParameter("password");
		System.out.println("password is : "+password);
		UserDetail customer = userService.getByResetPasswordToken(token);
		System.out.println("customer"+customer);
		if (customer != null) 
		{
			
			System.out.println("befour update");
			userService.updatePassword(customer, password);
			System.out.println("customer"+customer+"password"+password);
			return "You have successfully changed your password.";
		
		
		} 
		else 
		{    
			System.out.println("Invalid Token");
			return"Invalid Token";
		}
	}
	
	
	
	
	
	
	
	//-----------Get User for Edit------------------------------
	/**
	 * Get User for Edit
	 * @author Pratik Chaudhari
	 * @param userid
	 * @return UserDetail
	 */
	@GetMapping("/users/get-user-for-edit/{userid}")
	public Optional<UserDetail> getUserForEdit(@PathVariable Long userid)
	{
		return userService.getUserForEdit(userid);
	}

	//-------------update User details-------------------------------
	/**
	 * update User details\
	 * @author Pratik Chaudhari
	 * @param user
	 * @return String
	 */
	@PutMapping("/users/update-user")
	public String updateUser(@RequestBody UserDetail user)
	{
		userService.updateUser(user);
		System.out.println("User Updated Successfully.... ");
		return "User Updated successfully...!";
	}

	
	
	/**
	 * use to delete user by id 
	 * @param id
	 * @return Object
	 */
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable String  id) 
	{
		try{
			userService.deleteById(Long.parseLong(id));
			 return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	//bulk delete user 
/**
 * use to delete bulk user 
 * @param PkID
 * @return Object
 */
	@PostMapping("/user/bulkdelete")
	public ResponseEntity<Object> deleteUser(@RequestBody List<Long> PkID ) 
	{
		
		try{
			userService.deleteById(PkID);
			 return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	
	
	//Get User Count 
	/**
	 * Get User Count 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return long  use to retunr count 
	 */
	@GetMapping("/user/get-user_count")
	public long getCountUser(String fkUserId, String role,String category)
	{		
		return  userService.getCountUser(fkUserId,role,category);		
	}
	/**
	 * use to get Count Organization
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	@GetMapping("/user/get-organization_count")
	public String getCountOrganization(String fkUserId, String role,String category)
	{		
		return  userService.getCountOrganization(fkUserId,role,category);		
	}
	

	//Get Admin Count 
	/**
	 * get Count Admin
	 * @author Pratik chaudhari 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	@GetMapping("/user/get-admin_count")
	public String getCountAdmin(String fkUserId, String role,String category)
	{		
		return  userService.getCountAdmin(fkUserId,role,category);		
	}

	//
	/**
	 * Get User list-update new
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail
	 */
	@GetMapping("/user/get-user_list")
	public List<UserDetail> getAllUserList(String fkUserId,String role,String category)
	{		
		return  userService.getAllUserList(fkUserId,role,category);		
	}

	
	/**
	 * get All User List
	 * @param adminname
	 * @return RequestPermissionDto
	 */
	@GetMapping("/user/get-user_adminwise_list")
	public List<RequestPermissionDto> getAllUserList(String adminname)
	{		
		return  userService.getAllUserUnderPerticularAdmin(adminname);		
	}

	
//	@GetMapping("/user/get-admin_listforGps")
//	public List<RequestPermissionDto> getAllAdminListforGps(String fkUserId, String role)
//	{		
//		return  userService.getAllAdminListforGps(fkUserId,role);		
//	}
	//..........get admin list for drop down list
	
	/**
	 * get admin list for drop down list
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	@GetMapping("/user/get-adminlist_for_dropdown")
	public List<String> getAllAdminListxl(String fkUserId, String role,String category)
	{		
		return  userService.getAllAdminListfordrop(fkUserId,role,category);		
	}
	/**
	 * get All Admin List 
	 * @param username
	 * @param category
	 * @return String 
	 */
	@GetMapping("/user/get-adminlist_for_dropdown_userNamewise")
	public List<String> getAllAdminListxxxl(String username,String category)
	{		
		return  userService.getAllAdminListfordropUserNameWise(username,category);		
	}
	
	
	//Get Admin list 
	/**
	 * Get Admin list 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return UserDetail
	 */
	@GetMapping("/user/get-admin_list")
	public List<UserDetail> getAllAdminList(String fkUserId, String role,String category)
	{	
		System.out.println(fkUserId);
		return  userService.getAllAdminList(fkUserId,role,category);		
	}
	//Get organization list 
	
	/**
	 * Get organization list 
	 * @param fkUserId
	 * @param category
	 * @return RequestPermissionDto
	 */
		@GetMapping("/user/get-organization_list")
		public List<RequestPermissionDto> getAllOrganizationList(String fkUserId,String category)
		{		
			return  userService.getAllOrganizationList(fkUserId,category);		
		}
		/**
		 * import Excel File Bulk For DirectAdmin
		 * @author Pratik chaudhari
		 * @param file
		 * @param username
		 * @return String 
		 * @throws IOException
		 * @throws EncryptedDocumentException
		 * @throws InvalidFormatException
		 * @throws ParseException
		 */
		@PostMapping("/user/import-excel-file-with-user-details-to-database-for-direct-Admin")
		public String importExcelFileBulkForDirectAdmin(@RequestParam("file") MultipartFile file,String username) throws IOException, EncryptedDocumentException, InvalidFormatException, ParseException {

			
			String input = userService.addExcelFileDataToDatabaseForDirectAdmin(file,username);

			return input;
		}
		
		/**
		 * import Excel File Bulk
		 * @author Pratik chaudhari
		 * @param file
		 * @param username
		 * @return String 
		 * @throws IOException
		 * @throws EncryptedDocumentException
		 * @throws InvalidFormatException
		 * @throws ParseException
		 */
	@PostMapping("/user/import-excel-file-with-user-details-to-database")
	public String importExcelFileBulk(@RequestParam("file") MultipartFile file,String username) throws IOException, EncryptedDocumentException, InvalidFormatException, ParseException {

		//HttpStatus status = HttpStatus.OK;
		String input = userService.addExcelFileDataToDatabase(file,username);
//
//		if(input.equals("created")) 
//		{
//			status = HttpStatus.FOUND; 
//			return status; 
//		}

		return input;
	}
	/**
	 * import Excel File Bulk For Empuser
	 * @param file
	 * @return String
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws ParseException
	 */
	@PostMapping("/user/import-excel-file-with-empuser-details-to-database")
	public String importExcelFileBulkForEmpuser(@RequestParam("file") MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException, ParseException {

//		HttpStatus status = HttpStatus.;
		String input = employeUserService.addExcelFileDataToDatabaseBulkForEmpuser(file);

//		if(input.equals("created")) 
//		{
//			status = HttpStatus.FOUND; 
//			return status; 
//		}

		return input;
	}

	/*
	 * //---------------------API to get location and distance from latitude and
	 * longitude------------------------------
	 * 
	 * @RequestMapping(value = "/location/get-location-and-distance-from-lat-long")
	 * public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
	 * String strAdd = ""; Geocoder geocoder = new Geocoder(this,
	 * Locale.getDefault()); try { List<Address> addresses =
	 * geocoder.getFromLocation(LATITUDE, LONGITUDE, 1); if (addresses != null) {
	 * Address returnedAddress = addresses.get(0); StringBuilder strReturnedAddress
	 * = new StringBuilder("");
	 * 
	 * for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
	 * strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n"); }
	 * strAdd = strReturnedAddress.toString();
	 * System.out.println("My Current loction address"+
	 * strReturnedAddress.toString()); } else {
	 * System.out.println("My Current loction address"+ "No Address returned!"); } }
	 * catch (Exception e) { e.printStackTrace();
	 * System.out.println("My Current loction address"+ "Canont get Address!"); }
	 * return strAdd; }
	 */
	


}
