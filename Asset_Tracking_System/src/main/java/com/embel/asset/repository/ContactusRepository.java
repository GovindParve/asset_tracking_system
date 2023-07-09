package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.embel.asset.bean.ResponseContactusBean;
import com.embel.asset.entity.Contactus;

@Repository
public interface ContactusRepository extends JpaRepository<Contactus, Long>{
	
	
	
	//get all contactus for super admin
	@Query(value="SELECT new com.embel.asset.bean.ResponseContactusBean(contactid,contactname,contactnumber,email,description,fkUserId) FROM Contactus ")
	List<ResponseContactusBean> getAllContactusForSuperAdmin();
	
	
	//get all contactus for admin
	@Query(value="SELECT new com.embel.asset.bean.ResponseContactusBean(c.contactid,c.contactname,c.contactnumber,c.email,c.description,c.fkUserId) FROM Contactus c,AssetTag d WHERE c.fkUserId=d.fkUserId AND d.fkAdminId = ?1")
	List<ResponseContactusBean> getAllContactusForAdmin(long userid);

	//get all contactus for user
	@Query(value="SELECT new com.embel.asset.bean.ResponseContactusBean(c.contactid,c.contactname,c.contactnumber,c.email,c.description,c.fkUserId) FROM Contactus c,UserDetail ud WHERE  c.fkUserId = ud.pkuserId AND c.fkUserId = ?1 ")
	List<ResponseContactusBean> getAllContactusForUser(long userid);
//.....................................................................
	@Query(value="SELECT * FROM contact_us c WHERE c.category=?1 ", nativeQuery = true)
	Page<Contactus> getAllContactusListForSuperAdmin(String category, Pageable pageable);

	
	@Query(value="SELECT c.contactid,c.contactname,c.contactnumber,c.description,c.email,c.fk_user_id FROM contact_us c,asset_tag_generation a WHERE  c.fk_user_id=a.user_id  AND  c.category=?1 AND a.organization_id=?2 GROUP BY c.contactid desc", nativeQuery = true)
	Page<Contactus> getAllContactusListForOrganization(String category, long userid, Pageable pageable);
	
	
	@Query(value="SELECT c.contactid,c.contactname,c.contactnumber,c.description,c.email,c.fk_user_id FROM contact_us c,asset_tag_generation a WHERE  c.fk_user_id=a.user_id  AND c.category=?1 AND a.admin_id=?2 GROUP BY c.contactid desc", nativeQuery = true)
	Page<Contactus> getAllContactusListForAdmin(String category, long userid,Pageable pageable);

	@Query(value="SELECT * FROM contact_us c,asset_tag_generation a WHERE  c.fk_user_id=a.user_id  AND c.category=?1 AND c.fk_user_id=?2", nativeQuery = true)
	Page<Contactus> getAllContactusListForUser(String category, long userid,Pageable pageable);

	@Query(value="SELECT * FROM contact_us c WHERE c.category=?1 AND c.fk_user_id=?2",nativeQuery = true)
	Page<Contactus> getAllContactusListForEmpUser(String category, long userid, Pageable pageable);
	// 
	@Query(value="SELECT COUNT(c.contactid) FROM contact_us c", nativeQuery = true)
	String getallcontactuscountforSuperAdmin();

	@Query(value="SELECT COUNT(c.contactid) FROM contact_us c,device_details d WHERE c.fk_user_id=d.fk_user_id AND d.fk_admin_id=?1", nativeQuery = true)
	String getallcontactuscountforAdmin();

	
	@Query(value="SELECT COUNT(c.contactid) FROM contact_us c,device_details d WHERE c.fk_user_id=d.fk_user_id AND d.fk_user_id=?1", nativeQuery = true)
	String getallcontactuscountforUser();





	
	
	
	

}
