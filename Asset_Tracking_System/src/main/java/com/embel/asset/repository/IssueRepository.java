package com.embel.asset.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.bean.ResponseIssueBean;
import com.embel.asset.entity.Issue;


@Repository
public interface IssueRepository extends  JpaRepository<Issue, Long>{
	//Get Issue Count for super admin
	@Query(value="SELECT Count(i.issueid) FROM issue_details i", nativeQuery = true)
	long getIssueCountforSuperadmin();
	
	//Get Issue Count for  admin
//	SELECT COUNT( DISTINCT i.mailid) FROM issue_details i,device_details d WHERE  i.user_id=d.fk_user_id  AND d.fk_admin_id=3
	@Query(value="SELECT COUNT( DISTINCT i.mailid) FROM issue_details i,asset_tag_generation a WHERE  i.user_id=a.user_id AND a.admin_id=?1", nativeQuery = true)
	long getIssueCountforadmin(long userid);
	//Get Issue Count for  user
	@Query(value="SELECT Count(i.issueid) FROM issue_details i,user_details ud WHERE  i.user_id = ud.pkuser_id AND i.user_id=?1", nativeQuery = true)
	long getIssueCountforUser();
	
	
	@Query(value="SELECT new com.embel.asset.bean.ResponseIssueBean(i.issueid,i.userName,i.FkUserId,i.contact,i.mailId,i.issue) FROM Issue i ORDER BY i.issueid desc")
	List<ResponseIssueBean> getAllIssueForSuperAdmin();
//	SELECT * FROM issue_details;
	//get all issue for admin
	@Query(value="SELECT new com.embel.asset.bean.ResponseIssueBean(i.issueid,i.userName,i.FkUserId,i.contact,i.mailId,i.issue) FROM Issue i ,AssetTag a WHERE i.FkUserId=a.fkUserId AND a.fkAdminId = ?1 ORDER BY i.issueid ")
	List<ResponseIssueBean> getAllIssueForAdmin(Long userid);

	@Query(value="SELECT new com.embel.asset.bean.ResponseIssueBean(i.issueid,i.userName,i.FkUserId,i.contact,i.mailId,i.issue) FROM Issue i WHERE i.FkUserId = ?1  ORDER BY i.issueid desc")
	List<ResponseIssueBean> getAllIssueForUser(Long userid);
//	, DeviceDetails dd WHERE dd.deviceFkUserId
/////////////////////////////////////////////////////////////////////////////////////////
	@Query(value="SELECT * FROM issue_details i WHERE i.category=?1", nativeQuery = true)
	Page<Issue> getAllIssueListForSuperAdmin(String category, Pageable pageable);
	
	@Query(value="SELECT * FROM issue_details i,asset_tag_generation a WHERE  i.user_id=a.user_id AND i.category=?1 AND a.organization_id=?2 GROUP BY i.issueid desc", nativeQuery = true)
	Page<Issue> getAllIssueListForOrganization(String category, long userid, Pageable pageable);
	
	@Query(value="SELECT * FROM issue_details i,asset_tag_generation a WHERE  i.user_id=a.user_id AND i.category=?1  AND a.admin_id=?2 GROUP BY i.issueid desc", nativeQuery = true)
	Page<Issue> getAllIssueListForAdmin(String category, long userid,Pageable pageable);
	
	@Query(value="SELECT * FROM issue_details i,asset_tag_generation a WHERE  i.user_id=a.user_id AND i.category=?1 AND i.user_id=?2 GROUP BY i.issueid desc", nativeQuery = true)
	Page<Issue> getAllIssueListForUser(String category, long userid,Pageable pageable);

	@Query(value="SELECT * FROM issue_details i where  i.category=?1 AND i.user_id=?2 GROUP BY i.issueid desc", nativeQuery = true)
	Page<Issue> getAllIssueListForEmpUser(String category, long userid, Pageable pageable);
	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE issue_details SET `status`=?1 WHERE issueid=?2", nativeQuery = true)
    void getissuebyissueid(String status,String issueid);

	



	
	
	
	

	




	


	


	


}
