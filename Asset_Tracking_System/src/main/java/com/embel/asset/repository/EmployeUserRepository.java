package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.bean.UserDetailsResponsebean;
import com.embel.asset.bean.UserResponseBean;
import com.embel.asset.dto.RequestPermissionDto;
import com.embel.asset.entity.EmployeeUser;
import com.embel.asset.entity.UserDetail;


@Repository
public interface EmployeUserRepository extends JpaRepository<EmployeeUser, Long>{

	
	@Query(value="SELECT * FROM employe_user_details e WHERE e.e_mail_id=?1",nativeQuery=true)
	EmployeeUser getByEmailId(String emailId);

	
	@Query(value="SELECT e.user_role FROM employe_user_details e WHERE e.username=?1",nativeQuery=true)
	String getrole(String username);

	@Query(value="SELECT NEW com.embel.asset.dto.RequestPermissionDto(pkuserId,username,role,firstName,lastName,category,organization) FROM EmployeeUser where username = ?1")
	List<RequestPermissionDto> getUserDetailsForAllocation(String username);

	@Query("From EmployeeUser where username=?1")
	List<EmployeeUser> findUsersByUsername(String username);


	EmployeeUser findByUsername(String username);

	@Query(value="SELECT  e.user_id FROM employe_user_details e WHERE e.pkuser_id=?1",nativeQuery=true)
	long getAdminId(String fkUserId);  
	
	@Query(value="SELECT  e.user_id FROM employe_user_details e WHERE e.pkuser_id=?1",nativeQuery=true)
	String getAdminId1(String fkUserId);

	@Query(value="SELECT * FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2",nativeQuery=true)
	Page<EmployeeUser> getALLListForAdmin(String fkUserId, String category, Pageable pageable);

	@Query(value="SELECT * FROM employe_user_details u WHERE u.user_id=?1 AND u.user_role=?2",nativeQuery=true)
	List<EmployeeUser> getAllUserDListForAdmin(long fkid,String searchrole,Pageable pageable);

	@Query(value="SELECT * FROM employe_user_details u WHERE u.user_id=?1 AND u.user_role=?2",nativeQuery=true)
	List<EmployeeUser> getAllUserDListForOrganization(long fkid, String searchrole,Pageable pageable);

//	@Query(value="SELECT * FROM employe_user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
//	EmployeeUser getByID(Long long1);

	@Query(value="SELECT * FROM employe_user_details u WHERE u.category=?1",nativeQuery=true)
	Page<EmployeeUser> getAllListForSuperAdmin(Pageable pageable, String category);

	@Query(value="SELECT * FROM employe_user_details u",nativeQuery=true)
	List<EmployeeUser> getAllUserDListForSuperAdmin(Pageable pageable);

	
	

	@Query(value="SELECT * FROM employe_user_details e WHERE e.user_id=?1",nativeQuery=true)
	List<EmployeeUser> getUserUnderAdmin(long adminid);

	@Query(value="SELECT * FROM employe_user_details",nativeQuery=true)
	List<EmployeeUser> getuserListForValidation();

	@Modifying
	@Transactional
	@Query(value="UPDATE employe_user_details SET `status`=?1 WHERE pkuser_id=?2", nativeQuery = true)
	void enableUserStatus(String status, String pkuserid);


	
	@Query(value="SELECT e.`status` FROM employe_user_details e WHERE e.username=?1",nativeQuery=true)
	String getStatus(String username);


	@Query(value="SELECT * FROM employe_user_details",nativeQuery=true)
	List<EmployeeUser> getUnderSuperAdmin(String category);




	@Query(value="SELECT * FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2",nativeQuery=true)//0
	List<EmployeeUser> getEmployeeUnderOrganization(String adminid, String category, Pageable pageable);


	@Query(value="SELECT * FROM employe_user_details e WHERE e.user_id=?1",nativeQuery=true)
	List<EmployeeUser> getUserUnderAdminxx(String admin);

	@Query(value="SELECT * FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2",nativeQuery=true)
	List<EmployeeUser> getALLListForOrganization(Long pkuserId, String category);


	@Query(value="SELECT * FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2",nativeQuery=true)
	List<EmployeeUser> getEmpUserUnderAdmin(String string, String category);


	@Query(value="SELECT * FROM employe_user_details e WHERE e.category=?1",nativeQuery=true)
	List<EmployeeUser> EmployeeUserReportForSuperAdminExportDownload(String category);


//	@Query(value="SELECT * FROM employe_user_details e WHERE e.organization=?1 AND e.category=?2",nativeQuery=true)
//	List<UserResponseBean> EmployeeUserReportForOrganizationExportDownload(Long fkID, String category);


	@Query(value="SELECT * FROM employe_user_details e WHERE e.admin=?1 AND e.category=?2",nativeQuery=true)
	List<UserResponseBean> EmployeeUserReportForAdminExportDownload(Long fkID, String category);


	@Query(value="SELECT Count(e.pkuser_id) FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2",nativeQuery=true)
	long getEmpUserCountForAdmin(Long fkID, String category);


	@Query(value="SELECT Count(e.pkuser_id) FROM employe_user_details e WHERE e.category=?1",nativeQuery=true)
	long getEmpUserCountForSuperAdmin(String category);


	@Query(value="SELECT Count(e.pkuser_id) FROM employe_user_details e WHERE e.organization=?1 AND  e.category=?2",nativeQuery=true)
	long getEmpUserCountForOrganization(String organization, String category);

	@Query(value="SELECT * FROM employe_user_details e WHERE e.category=?1",nativeQuery=true)
	List<EmployeeUser> getAllempUsers(String category);


	@Query(value="SELECT * FROM employe_user_details e WHERE e.e_mail_id=?1 AND e.category=?2",nativeQuery=true)
	EmployeeUser getEmployee(String emailId, String category);

	@Query(value ="SELECT * FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2 ORDER BY e.pkuser_id",nativeQuery=true)
	List<EmployeeUser> empUserReportForOrganizationExportDownload(String admin,String category);

	@Query(value="SELECT COUNT(e.pkuser_id) FROM employe_user_details e WHERE e.category=?1",nativeQuery=true)
	long getCountEmpUserForSuperAdmin(String category);

	@Modifying
	@Transactional
	@Query(value="UPDATE employe_user_details SET `status`=?1 WHERE user_id=?2", nativeQuery = true)
	void enableUserStatusOnAdminid(String status, String adminid);


	@Query(value ="SELECT * FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2 ORDER BY e.pkuser_id",nativeQuery=true)
	List<EmployeeUser> EmployeeUserReportForOrganizationExportDownload(String adminid, String category);

	@Query(value="SELECT u.pkuser_id FROM user_details u WHERE u.username=?1",nativeQuery=true)
	String getAdminIdxx(String fkUserId);


	@Query(value ="SELECT * FROM employe_user_details e WHERE e.category=?1 ORDER BY e.pkuser_id",nativeQuery=true)
	List<EmployeeUser> getAllEmpUserListRole(String category);


	@Query(value ="SELECT * FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2 ORDER BY e.pkuser_id",nativeQuery=true)
	List<EmployeeUser> getAllEmpUserListRoleForOrganization(String adminid, String category);

	@Query(value ="SELECT * FROM employe_user_details e WHERE e.username=?1 AND e.category=?2",nativeQuery=true)
	EmployeeUser getEmployeedbDataOnUserName(String username,String category);

	@Query(value ="SELECT * FROM employe_user_details e WHERE e.e_mail_id=?1 AND e.category=?2",nativeQuery=true)
	EmployeeUser getEmployeedbDataOnEmail(String emailId, String upperCase);


	





//	@Query(value="SELECT * FROM employe_user_details e WHERE e.user_id=?1 AND e.category=?2",nativeQuery=true)//0
//	Page<EmployeeUser> getALLListForOrganization(String getuserName, String category);
//	






	
}
