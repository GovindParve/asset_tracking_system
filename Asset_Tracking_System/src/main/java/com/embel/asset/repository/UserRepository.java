package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.bean.ResponseTrackingListBean;
import com.embel.asset.bean.UserDetailsResponsebean;
import com.embel.asset.bean.UserInfobean;
import com.embel.asset.bean.UserResponseBean;
import com.embel.asset.bean.UserResponseforissueBean;
import com.embel.asset.dto.RequestPermissionDto;
import com.embel.asset.entity.EmployeeUser;
import com.embel.asset.entity.UserDetail;

@Repository
public interface UserRepository extends JpaRepository<UserDetail, Long>{

	UserDetail findByUsername(String username);

	boolean existsByUsername(String userName);
	
//	@Query(value="SELECT * FROM user_details u WHERE u.e_mail_id=?1 AND u.category=?2",nativeQuery=true),String category
	UserDetail findByEmailId(String emailId);
	
	@Query(value="SELECT * FROM user_details u WHERE u.e_mail_id=?1 AND u.category=?2",nativeQuery=true)
	UserDetail getUserDetalsObject(String emailId,String category);
	
	
	@Query(value="SELECT * FROM user_details u WHERE u.username=?1 AND u.category=?2",nativeQuery=true)
	UserDetail getUserDetalsOnUserName(String username, String upperCase);
	//------------Get All User details For Superadmin role--------------------
	@Query(value = "SELECT NEW com.embel.asset.bean.UserResponseBean(pkuserId,username,role,firstName,lastName,phoneNumber,emailId,password,companyName,address) FROM UserDetail")
	List<UserResponseBean> getUserListForSuperAdmin();

	//-------------Get All User details For Admin role-------------------
	@Query(value = "SELECT DISTINCT NEW com.embel.asset.bean.UserResponseBean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address) FROM UserDetail u,AssetTag d WHERE u.pkuserId = d.fkUserId AND d.fkAdminId = ?1 ORDER BY u.pkuserId")
	List<UserResponseBean> getUserListForAdmin(Long id);

	//Get All User details For User role-----------------------------------
	@Query(value ="SELECT NEW com.embel.asset.bean.UserResponseBean(pkuserId,username,role,firstName,lastName,phoneNumber,emailId,password,companyName,address) FROM UserDetail WHERE pkuserId = ?1 AND role = 'user'")
	List<UserResponseBean> getUserListForUser(Long id);

	//Get User for Edit
	@Query(value ="SELECT NEW com.embel.asset.bean.UserResponseBean(pkuserId,username,role,firstName,lastName,phoneNumber,emailId,password,companyName,address) FROM UserDetail where pkuserId=?1")
	List<UserDetail> getUserForEdit(Long id);

	//Get User name and Role from user table
	@Query(value="SELECT NEW com.embel.asset.dto.RequestPermissionDto(pkuserId,username,role,firstName,lastName,category,organization) FROM UserDetail where username = ?1")
	List<RequestPermissionDto> getUserDetailsForAllocation(String username);

	//Get User count For Super Admin
	@Query(value="SELECT count(pkuserId) FROM UserDetail WHERE role = 'user' AND category=?1 ")
	long getCountUserForSuperAdmin(String category);

	//Get User count For User 
	@Query(value="SELECT COUNT(u.pkuser_id) FROM user_details u WHERE u.admin=?1 AND u.category=?2 AND u.user_role='user'",nativeQuery=true)
	long getCountUserForAdmin(String admin,String category);

	//Get SuperAdmin count 
	@Query(value="SELECT count(pkuserId) FROM UserDetail WHERE role = 'admin' AND category=?1 ")
	String getCountSuperAdmin(String category);

	//@Query(value="SELECT NEW com.embel.asset.dto.RequestPermissionDto(pkuserId,username,role,firstName,lastName,category) FROM UserDetail where  role = 'user'")
	//SELECT * FROM user_details u WHERE u.category='BLE' AND u.user_role='user'
	@Query(value="SELECT * FROM user_details u WHERE u.category=?1 AND u.user_role='user'",nativeQuery=true)
	List<UserDetail> getListOfUserRole(String category);
	

	//@Query(value="SELECT DISTINCT NEW com.embel.asset.dto.RequestPermissionDto(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.category) FROM UserDetail u,AssetTag atg where u.pkuserId=atg.fkUserId AND atg.fkAdminId=?1 AND u.role = 'user' ORDER BY u.pkuserId")
	@Query(value="SELECT * FROM user_details u WHERE u.admin=?1 AND u.category=?2 AND u.user_role='user'",nativeQuery=true)
	List<UserDetail> getListOfUserRoleforAdmin(String userName,String category);
	
	//@Query(value="SELECT DISTINCT NEW com.embel.asset.dto.RequestPermissionDto(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.category) FROM UserDetail u,AssetTag atg where u.pkuserId=atg.fkUserId AND atg.fkOrganizationId=?1 AND u.role = 'user' ORDER BY u.pkuserId")
	@Query(value="SELECT * FROM user_details u WHERE u.admin=?1 AND u.category=?2 AND u.user_role='user'",nativeQuery=true)
	List<UserDetail> getListOfUserRoleforOrganization(String userName,String category);
	
	
	

	//@Query(value="SELECT NEW com.embel.asset.dto.RequestPermissionDto(pkuserId,username,role,firstName,lastName,category) FROM UserDetail where role = 'admin'")
	@Query(value="SELECT * FROM user_details u WHERE  u.user_role='admin' AND u.category=?1 GROUP BY u.pkuser_id",nativeQuery=true)

	List<UserDetail> getListOfAdminRoleForSuperadmin(String category);
	
	//@Query(value="SELECT * FROM user_details u,asset_tag_generation a WHERE  u.user_role='admin' AND u.pkuser_id=a.admin_id AND a.organization_id=?1 GROUP BY u.pkuser_id",nativeQuery=true)
	@Query(value="SELECT * FROM user_details u WHERE  u.user_role='admin' AND u.organization=?1 AND u.category=?2 GROUP BY u.pkuser_id",nativeQuery=true)
	List<UserDetail> getListOfAdminRoleForOrganization(String username,String category);
	
	
	
	@Query(value="SELECT NEW com.embel.asset.dto.RequestPermissionDto(pkuserId,username,role,firstName,lastName,category,organization) FROM UserDetail  where role = 'organization' AND category=?1")
	List<RequestPermissionDto> getListOfOrganizationRole(String category);
	
	
//.................................................pagination.........................................
	@Query(value = "SELECT DISTINCT NEW com.embel.asset.bean.UserResponseBean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address) FROM UserDetail u,AssetTag d WHERE d.assetTagCategory=?1")
//	@Query(value = "SELECT pkuser_id,username,user_role,first_name,last_name,phone_number,e_mail_id,password,company_name,user_address FROM user_details",nativeQuery=true)
	Page<UserResponseBean> getUserListForSuperAdminWithPagination(String category, Pageable pageable);
	
	@Query(value = "SELECT DISTINCT NEW com.embel.asset.bean.UserResponseBean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address) FROM UserDetail u , AssetTag d WHERE u.pkuserId = d.fkUserId AND d.assetTagCategory=?1 AND d.fkOrganizationId= ?2 ORDER BY u.pkuserId")
	Page<UserResponseBean> getUserListForOrganizationWithPagination(String category, Long id, Pageable pageable);
//						distinct kadhala
	@Query(value = "SELECT DISTINCT NEW com.embel.asset.bean.UserResponseBean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address) FROM UserDetail u , AssetTag d WHERE u.pkuserId = d.fkUserId AND d.assetTagCategory=?1 AND d.fkAdminId = ?2 ORDER BY u.pkuserId")
//	@Query(value = "SELECT u.pkuser_id,u.username,u.user_role,u.first_name,u.last_name,u.phone_number,u.e_mail_id,u.password,u.company_name,u.user_address FROM user_details u,asset_tag_generation d WHERE u.pkuser_id=d.user_id AND d.admin_id=?1",nativeQuery=true)
	Page<UserResponseBean> getUserListForAdminWithPagination(String category, Long id,Pageable pageable);
	
	@Query(value = "SELECT NEW com.embel.asset.bean.UserResponseBean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address) FROM UserDetail u,AssetTag d WHERE  d.assetTagCategory=?1 AND pkuserId = ?2 AND role = 'user'")
//	@Query(value = "SELECT u.pkuser_id,u.username,u.user_role,u.first_name,u.last_name,u.phone_number,u.e_mail_id,u.password,u.company_name,u.user_address FROM user_details u WHERE u.pkuser_id=?1 AND u.user_role = 'user'",nativeQuery=true)
	Page<UserResponseBean> getUserListForUserWithPagination(String category, Long id,Pageable pageable);
	
	
	@Query(value="SELECT * FROM user_details u WHERE u.e_mail_id=?1",nativeQuery=true)
	UserDetail findByEmail(String email);

	UserDetail findByResetPasswordToken(String token);

	@Query("From UserDetail where username=?1")
	List<UserDetail> findUsersByUsername(String username);

	@Query(value="SELECT u.e_mail_id FROM user_details u WHERE u.e_mail_id=?1",nativeQuery=true)
	String findByEmailAddress(String email);

	@Query(value="SELECT new com.embel.asset.bean.UserResponseforissueBean(pkuserId,firstName,lastName,emailId,phoneNumber) FROM UserDetail WHERE pkuserId = ?1")
	UserResponseforissueBean getUserDetailsForContactandissue(long fkUserId);

	
	@Query(value = "SELECT NEW com.embel.asset.bean.UserResponseBean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address) FROM UserDetail u WHERE u.category=?1")
	List<UserResponseBean> UserReportForSuperAdminExportDownload(String category);

	@Query(value = "SELECT DISTINCT NEW com.embel.asset.bean.UserResponseBean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address) FROM UserDetail u WHERE u.admin =?1 AND u.category=?2 AND u.role='user' ORDER BY u.pkuserId")
	List<UserResponseBean> UserReportForAdminExportDownload(String admin, String category);
	
	@Query(value = "SELECT NEW com.embel.asset.bean.UserResponseBean(pkuserId,username,role,firstName,lastName,phoneNumber,emailId,password,companyName,address) FROM UserDetail WHERE pkuserId = ?1 AND role = 'user' AND category=?2 ")
	List<UserResponseBean> UserReportForUserExportDownload(Long fkID, String category);

	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	long getbyuserName(String username);

	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	long getpkid(String user);

	
	//SELECT COUNT(u.pkuser_id) FROM user_details u,asset_gateway_creation a WHERE a.organization_id=u.pkuser_id AND a.organization_id=?1 AND a.asset_tag_category=?2 GROUP BY u.pkuser_id
	@Query(value="SELECT COUNT(u.pkuser_id) FROM user_details u WHERE u.organization=?1 AND  u.category=?2 AND u.user_role='admin'",nativeQuery=true)
	String getCountAdmin(String admin,String category);

	//SELECT COUNT(DISTINCT a.user_id) FROM user_details u,asset_gateway_creation a WHERE u.pkuser_id=a.organization_id AND a.organization_id=?1 AND a.asset_tag_category=?2 GROUP BY u.pkuser_id
	@Query(value="SELECT COUNT(u.pkuser_id) FROM user_details u WHERE u.organization=?1 AND  u.category=?2 AND u.user_role='user'",nativeQuery=true)
	long getCountUserForOrganization(String organization,String category);

	
	

	@Query(value = "SELECT DISTINCT NEW com.embel.asset.bean.UserResponseBean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address) FROM UserDetail u WHERE  u.admin=?1 AND u.category=?2 ORDER BY u.pkuserId")
	List<UserResponseBean> UserReportForOrganizationExportDownload(String admin, String category);

	@Query(value = "SELECT DISTINCT NEW com.embel.asset.bean.UserResponseBean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address) FROM UserDetail u WHERE  u.organization=?1 AND u.category=?2 AND u.role='admin' ORDER BY u.pkuserId")
	List<UserResponseBean> getAdminUnderOrganizationxx(String organizationUserName, String category);
	@Query(value="SELECT COUNT(u.pkuser_id) FROM user_details u WHERE u.user_role='organization' AND u.category=?1",nativeQuery=true)
	String getOraganizationCount(String category);

	

	@Query(value="SELECT * FROM user_details u,asset_tag_generation atg WHERE u.pkuser_id=atg.user_id AND atg.asset_tag_category=?1 GROUP BY u.pkuser_id",nativeQuery=true)
	Page<UserDetail> getUserListForSuperAdminWithPaginationForGPS(String category, Pageable pageable);

	@Query(value="SELECT * FROM user_details u,asset_tag_generation atg WHERE u.pkuser_id=atg.user_id AND atg.asset_tag_category=?1 AND atg.organization_id=?2 GROUP BY u.pkuser_id",nativeQuery=true)
	Page<UserDetail> getUserListForOrganizationWithPaginationForGPS(String category,Long id, Pageable pageable);


	@Query(value="SELECT * FROM user_details u,asset_tag_generation atg WHERE u.pkuser_id=atg.user_id AND atg.asset_tag_category=?1 AND atg.admin_id=?2 GROUP BY u.pkuser_id",nativeQuery=true)
	Page<UserDetail> getUserListForAdminWithPaginationForGPS(String category,Long id, Pageable pageable);

	@Query(value="SELECT * FROM user_details u,asset_tag_generation atg WHERE u.pkuser_id=atg.user_id AND atg.asset_tag_category=?1 AND atg.user_id=?2 GROUP BY u.pkuser_id",nativeQuery=true)
	Page<UserDetail> getUserListForUserWithPaginationForGPS(String category,Long id, Pageable pageable);




	
//	@Query(value="SELECT NEW com.embel.asset.dto.RequestPermissionDto(u.pkuserId,u.username,u.role,u.firstName,u.lastName,category)FROM UserDetail u,AssetTag t where  u.role = 'admin' AND t.assetTagCategory='GPS'")
//	List<RequestPermissionDto> getAlladminListforSuperAdminRoleforGps();
//
//	
//	@Query(value="SELECT NEW com.embel.asset.dto.RequestPermissionDto(u.pkuserId,u.username,u.role,u.firstName,u.lastName,category)FROM UserDetail u,AssetTag t where  u.role = 'admin' AND t.assetTagCategory='GPS' AND t.fkOrganizationId=?1")
//	List<RequestPermissionDto> getAlladminListforOrganizationRoleforGps(Long fkID);
//
//	
//	@Query(value="SELECT NEW com.embel.asset.dto.RequestPermissionDto(u.pkuserId,u.username,u.role,u.firstName,u.lastName,category)FROM UserDetail u,AssetTag t where  u.role = 'admin' AND t.assetTagCategory='GPS' AND t.fkAdminId=?1")
//	List<RequestPermissionDto> getListOfUserRoleforAdminforGps(Long fkID);

	
	@Query(value="SELECT u.user_role FROM user_details u WHERE u.username=?1",nativeQuery=true)
	String getRole(String username);

	
	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id = a.admin_id AND  a.asset_tag_category='GPS'",nativeQuery=true)
	String getCountAdmingpsForSuperAdmin();

	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id = a.user_id AND  a.asset_tag_category='GPS'",nativeQuery=true)
	String getCountUsergpsForSuperAdmin();

	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id = a.organization_id AND  a.asset_tag_category='GPS'",nativeQuery=true)
	String getCountOrganizationgpsForSuperAdmin();

	
	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.admin_id AND a.asset_tag_category='GPS' AND a.organization_id=?1",nativeQuery=true)
	String getCountAdmingpsForOrganization();

	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.user_id AND a.asset_tag_category='GPS' AND a.organization_id=?1",nativeQuery=true)
	String getCountUsergpsForOrganization();

	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.organization_id AND a.asset_tag_category='GPS' AND a.organization_id=?1",nativeQuery=true)
	String getCountOrganizationgpsForOrganization();

	
	
	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.admin_id AND a.asset_tag_category='GPS' AND a.admin_id=?1",nativeQuery=true)
	String getCountAdmingpsForAdmin();

	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.user_id AND a.asset_tag_category='GPS' AND a.admin_id=?1",nativeQuery=true)
	String getCountUsergpsForAdmin();

	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.organization_id AND a.asset_tag_category='GPS' AND a.admin_id=?1",nativeQuery=true)
	String getCountOrganizationgpsForAdmin();

	
	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.user_id AND a.asset_tag_category='GPS' AND a.user_id=?1",nativeQuery=true)
	String getCountUsergpsForUser();

	
	
	@Query(value="SELECT DISTINCT NEW com.embel.asset.bean.UserDetailsResponsebean(u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address,d.admin,d.organization) FROM UserDetail u,AssetTag d WHERE u.pkuserId = d.fkOrganizationId AND d.fkOrganizationId=?1 AND d.assetTagCategory=?2 ORDER BY u.pkuserId")
	List<UserDetailsResponsebean> getOraganizationListForUser(long fkUserId, String category);

	
	
	@Query(value="SELECT * FROM user_details u WHERE u.user_role=?1 AND u.category=?2",nativeQuery=true)
	List<UserDetail> getAllUserDListForSuperAdmin(String searchrole,String category, Pageable pageable);


	@Query(value="SELECT * FROM user_details u WHERE u.created_by=?1 AND u.user_role=?2",nativeQuery=true)
	Page<UserDetail> getAllUserDListForAdmin(String createdby,String searchrole, Pageable pageable);

	@Query(value="SELECT * FROM user_details u WHERE u.created_by=?1 AND u.user_role=?2",nativeQuery=true)
	Page<UserDetail> getAllUserDListForOrganization(String createdby, String searchrole, Pageable pageable);

	
	@Query(value="SELECT u.username FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getuserNaame(String fkUserId);

	
	@Query(value="SELECT * FROM user_details u",nativeQuery=true)
	Page<UserDetail> getUserListForSuperAdminxl(Pageable pageable);

	@Query(value="SELECT * FROM user_details u where u.created_by=?1",nativeQuery=true)
	Page<UserDetail> getUserListForOrganizationxl(String userName, Pageable pageable);

	@Query(value="SELECT * FROM user_details u where u.created_by=?1",nativeQuery=true)
	Page<UserDetail> getUserListForAdminXl(String userName, Pageable pageable);

	@Query(value="SELECT * FROM user_details u where u.created_by=?1",nativeQuery=true)
	Page<UserDetail> getUserListForUserxl(String userName, Pageable pageable);

	@Query(value="SELECT * FROM user_details u WHERE u.category=?1",nativeQuery=true)
	List<UserDetail> getAllUserListForSuperAdminxl(String category, Pageable pageable);

	
	@Query(value="SELECT * FROM user_details u where u.organization=?1 AND u.category=?2",nativeQuery=true)
	List<UserDetail> getAllUserListForOrganizationDefault(String userName, String category, Pageable pageable);

	
	
	@Query(value="SELECT * FROM user_details u where u.admin=?1 AND u.category=?2",nativeQuery=true)
	List<UserDetail> getAllUserListForAdminDefault(String userName, String category, Pageable pageable);
	
	@Query(value="SELECT * FROM user_details u where u.`user`=?1",nativeQuery=true)
	Page<UserDetail> getAllUserListForUserDefault(String userName, Pageable pageable);

	
	@Query(value="SELECT * FROM user_details u where u.organization=?1 AND u.user_role=?2 AND u.category=?3",nativeQuery=true)
	List<UserDetail> getAllUserListForOrganization(String userName, String searchrole, String category, Pageable pageable);

	@Query(value="SELECT * FROM user_details u where u.admin=?1 AND u.user_role=?2 AND u.category=?3",nativeQuery=true)
	List<UserDetail> getAllUserListForAdmin(String userName, String searchrole, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM user_details u where u.use=?1 AND u.user_role=?2",nativeQuery=true)
	Page<UserDetail> getAllUserListForUser(String userName, String searchrole, Pageable pageable);

	
	@Query(value="SELECT u.admin FROM user_details u where u.pkuser_id=?1",nativeQuery=true)
	String getadminName(String adminid);

	@Query(value="SELECT u.username FROM user_details u WHERE u.category=?1 AND u.user_role='admin'",nativeQuery=true)
	List<String> getallAdminListForSuperAdmin(String category);

	@Query(value="SELECT u.username FROM user_details u WHERE u.organization=?1 AND u.category=?2 AND u.user_role='admin'",nativeQuery=true)
	List<String> getOrganizationWiseAdmin(String userName, String category);

	@Query(value="SELECT u.username FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getUserName(String fkUserId);

	
	@Query(value="SELECT * FROM user_details u WHERE u.category=?1 AND u.user_role='organization'",nativeQuery=true)
	List<UserDetail> getAllOrganizationForSuperAdmin(String category);

	@Query(value="SELECT * FROM user_details u WHERE  u.category=?1 AND u.user_role='admin'",nativeQuery=true)
	List<UserDetail> getUserSuperAdmin(String category);

	
	@Query(value="SELECT * FROM user_details u WHERE u.organization=?1 AND u.category=?2 AND u.user_role='admin' ",nativeQuery=true)
	List<UserDetail> getAdminUnderOrganization(String userName, String category);

	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	long getAminIdOnUserName(String userName);

	
	
	@Query(value="SELECT * FROM user_details u WHERE u.admin=?1 AND u.category=?2 AND u.user_role='user' ",nativeQuery=true)
	List<UserDetail> getUserUnderAdmin(String userName, String category);

	
	@Query(value="SELECT u.username FROM user_details u WHERE u.admin=?1 ",nativeQuery=true)
	List<String> getAdminListOnUserName(String userName);

	
	@Query(value="SELECT * FROM user_details",nativeQuery=true)
	List<UserDetail> getuserListForValidation();

	
	
	

	@Modifying
	@Transactional
	@Query(value="UPDATE user_details SET `status`=?1 WHERE pkuser_id=?2", nativeQuery = true)
	void enableUserStatus(String status, String pkuserid);

	
	@Query(value="SELECT u.status FROM user_details u WHERE u.username=?1", nativeQuery = true)
	String getStats(String username);

	
	@Query(value="SELECT * FROM user_details u WHERE u.user_role='organization' AND u.category=?1", nativeQuery = true)
	List<UserDetail> getAllOraganizationRole(String category);

	@Query(value="SELECT * FROM user_details u WHERE u.user_role='admin' AND u.category=?1", nativeQuery = true)
	List<UserDetail> getAllAdminListRole(String category);

	@Query(value="SELECT * FROM user_details u WHERE u.user_role='admin'AND u.organization=?1 AND u.category=?2", nativeQuery = true)
	List<UserDetail> getAllOrganizationListRole(String username, String category);

	
	@Query(value="SELECT * FROM user_details u WHERE u.user_role='user' AND u.category=?1", nativeQuery = true)
	List<UserDetail> getAllUserListRole(String category);

	
	@Query(value="SELECT * FROM user_details u WHERE u.user_role='user' AND u.organization=?1 AND u.category=?2", nativeQuery = true)
	List<UserDetail> getAllUserListRoleForOrganization(String username, String category);

	
	
	@Query(value="SELECT * FROM user_details u WHERE u.user_role='user' AND u.admin=?1 AND u.category=?2", nativeQuery = true)
	List<UserDetail> getAllUserListRoleForAdmin(String username, String category);

	
	
	
	@Query(value="SELECT * FROM user_details u WHERE u.user_role='user' AND u.category=?1", nativeQuery = true)
	List<UserDetail> getUserUnderSuperAdmin( String category);

	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.organization=?1", nativeQuery = true)
	List<String> getAllUserNameOfAdminUnderOrganization(String userName);

	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.organization=?1", nativeQuery = true)
	List<String> getAllAminIdOnUserNameforOrganization(String userName);

	
	@Query(value="SELECT * FROM user_details u WHERE u.admin=?1",nativeQuery=true)
	List<UserDetail> getUserUnderAdminxx(String string);

	
	@Query(value="SELECT u.username FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getAdminUserName(String string);

	@Query(value="SELECT u.username FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getUserNameOfOrganization(String fkUserId);

	@Query(value="SELECT * FROM user_details u WHERE u.organization=?1",nativeQuery=true)
	List<UserDetail> getAlladminListforOrganization(String getuserName);

	
	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.organization=?1",nativeQuery=true)
	List<String> getAminIdUnderOrganization(String userName);

	
	
	@Query(value="SELECT * FROM user_details u WHERE u.admin=?1 AND u.category=?2",nativeQuery=true)
	List<UserDetail> getUserUnderAdminForOrganization(String adminid, String category);

	
	
	
	@Query(value="SELECT u.username FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getAdminUserNameforOrganization(String string);

	

	

	@Query(value="SELECT * FROM user_details u WHERE  u.category=?1",nativeQuery=true)
	List<UserDetail> getUserForSuperAdmin(String category);

	@Query(value="SELECT u.organization FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getorganizationId(Long fkuserId);
	
	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	long getAdminId(String admin);

	
	@Query(value="SELECT u.username FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getadminNamexl(String fkUserId);

	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.organization=?1 AND u.user_role='admin'",nativeQuery=true)
	List<Long> getAllAdminListForOrg(String userName);

	
	
	@Query(value="SELECT NEW com.embel.asset.dto.RequestPermissionDto(pkuserId,username,role,firstName,lastName,category,organization) FROM UserDetail where admin=?1 AND role = 'user'")
	List<RequestPermissionDto> getAllUserUnderPerticularAdmin(String adminname);

	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.organization=?1",nativeQuery=true)
	List<String> getAdminListOnOrganization(String organization);

	
	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	long getadminFkid(String admin);

	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	long getfkuserIdOnAdminName(String admin);

	
	
	@Query(value="SELECT u.organization FROM user_details u WHERE u.admin=?1",nativeQuery=true)
	String getOganizationOnUserName(String admin);

	
	@Query(value="SELECT u.username FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getOrganizationName(String fkUserId);

	
	@Query(value="SELECT * FROM user_details u WHERE u.createdby='superadmin' AND u.category='BLE' AND u.user_role='admin' AND u.organization=''",nativeQuery=true)
	List<UserDetail> getadminListCreatedBySuperAdmin(String userName,String category);

	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	long getpkuserIdOnUserName(String user);

	
	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.organization=?1 AND u.category=?2",nativeQuery=true)
	List<String> getAdminUnderOrganizationx(String organizationUserName, String category);

	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	String getOrganizationId(String organization);

	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	String getAdminIdInString(String adminName);

	@Query(value="SELECT * FROM user_details u WHERE u.e_mail_id=?1",nativeQuery=true)
	UserDetail getemailinfo(String emailId);
	
	@Query(value="SELECT COUNT(e.pkuser_id) FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2",nativeQuery=true)
	long getCountEmpUserForAdmin(String fkUserId, String category);

	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.organization=?1",nativeQuery=true)
	List<String> getAdminList(String organizationUserName);

	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.admin=?1",nativeQuery=true)
	List<String> getUserList(String adminUserName);

	
	@Modifying
	@Transactional
	@Query(value="UPDATE user_details SET `status`=?1 WHERE admin=?2", nativeQuery = true)
	void enableUserStatusOnUnderAdmin(String status, String admin);

	
	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	long getpkidOnUserName(String adminName);



	




	

	


	
	
	
	
//	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.admin_id AND a.asset_tag_category='GPS' AND a.admin_id=?1",nativeQuery=true)
//	String getCountAdmingps(String fkUserId);
//
//	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.user_id AND a.asset_tag_category='GPS' AND a.user_id=?1",nativeQuery=true)
//	String getCountUsergps(String fkUserId);
//
//	@Query(value="SELECT Count(DISTINCT u.username) FROM user_details u,asset_tag_generation a WHERE u.pkuser_id =a.organization_id AND a.asset_tag_category='GPS' AND a.organization_id=?1",nativeQuery=true)
//
//	String getCountOrganizationgps(String fkUserId);

	
	


	
	



	


	

	
	

	



	
	


	
	
	

}
