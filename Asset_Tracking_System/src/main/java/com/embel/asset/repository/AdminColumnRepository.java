package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.entity.AdminColumns;



@Repository
public interface AdminColumnRepository extends JpaRepository<AdminColumns, Long>{

	
	@Query(value="select a.admin_name from admin_columns a where a.admin_name=?1 AND a.columns_names=?2",nativeQuery=true)
	String getadminName(String adminName, String columnName);

	@Query(value="select a.organization from admin_columns a where a.organization=?1 AND a.columns_names=?2",nativeQuery=true)
	String getOrgnizationName(String organization, String columns);

	@Query(value="SELECT * FROM admin_columns where category=?1",nativeQuery=true)
	Page<AdminColumns> getallListForSuperadmin(String category, Pageable pageable);

	//@Query(value="SELECT * FROM admin_columns a,asset_gateway_creation g WHERE a.category=?1 AND g.organization_id=?2 AND a.fk_admin_id=g.admin_id GROUP BY a.pkadmincolumns_id",nativeQuery=true)

	@Query(value="SELECT * FROM admin_columns a WHERE a.category=?1 AND a.fk_organization_id=?2 AND a.admin_name=''",nativeQuery=true)
	List<AdminColumns> getAllOrganizationList(String category, long fkid);
	
	@Query(value="SELECT * FROM admin_columns a WHERE a.category=?1 AND a.fk_admin_id=?2",nativeQuery=true)
	Page<AdminColumns> getAllAdminsList(String category, long fkid, Pageable pageable);

	@Query(value="SELECT * FROM admin_columns a,asset_gateway_creation g WHERE a.category=?1 AND g.user_id=?2 AND a.fk_admin_id=g.admin_id GROUP BY a.pkadmincolumns_id",nativeQuery=true)
	Page<AdminColumns> getAllUserList(String category, long fkid, Pageable pageable);

	@Query(value="SELECT * FROM admin_columns a WHERE a.fk_admin_id=?1 AND a.category=?2",nativeQuery=true)
	List<AdminColumns> getListOfAdminColumn(String fkUserId, String category);
	
	@Query(value="SELECT * FROM admin_columns a WHERE a.fk_admin_id=?1 AND a.category=?2",nativeQuery=true)
	List<AdminColumns> getListOfAdminColumnForUser(long fkUserId, String category);

	@Query(value="SELECT a.columns_names FROM admin_columns a",nativeQuery=true)
	List<String> getUicolumnListforSuperAdmin();


	@Query(value="SELECT a.columns_names FROM admin_columns a WHERE a.fk_admin_id=?1",nativeQuery=true)
	List<String> getUicolumnListforAdmin(long fkid);

	@Query(value="SELECT a.columns_names FROM admin_columns a,asset_gateway_creation g WHERE a.fk_admin_id=g.admin_id AND g.user_id=?1 GROUP BY a.columns_names",nativeQuery=true)
	List<String> getUicolumnListforUser(long fkid);

	@Query(value="SELECT a.columns_names FROM admin_columns a WHERE a.fk_organization_id=?1 AND a.category=?2 AND a.`status`='Active' GROUP BY a.columns_names",nativeQuery=true)
	List<String> getUicolumnForOrganization(long fkid, String category);

	@Query(value="SELECT a.columns_names FROM admin_columns a WHERE a.fk_admin_id=?1 AND a.category=?2",nativeQuery=true)
	List<String> getUicolusmnListforAdmin(long fkid, String category);

	
	@Query(value="SELECT * FROM admin_columns a WHERE a.category=?1 AND a.fk_admin_id=?2 GROUP BY a.pkadmincolumns_id",nativeQuery=true)
	List<AdminColumns> getAllAdminsListForOrganizationAdmin(String category, String admin);

	
	@Modifying
	@Transactional
	@Query(value="UPDATE admin_columns SET status='Active' WHERE pkadmincolumns_id=?1",nativeQuery=true)
	void updateStatusOfAdminParameterActivate(long pkadminparameterid);

	@Modifying
	@Transactional
	@Query(value="UPDATE admin_columns SET status='DeActive' WHERE pkadmincolumns_id=?1",nativeQuery=true)
	void updateStatusOfAdminParameterDeActivate(long pkadminparameterid);

	@Query(value="SELECT * FROM admin_columns where category=?1",nativeQuery=true)
	List<AdminColumns> getallListForSuperadminforstatus(String category);

	
	@Query(value="SELECT * FROM admin_columns a WHERE a.category=?1 AND a.fk_admin_id=?2",nativeQuery=true)
	List<AdminColumns> getAllAdminsListforstatus(String category, long fkid);

	@Query(value="SELECT * FROM admin_columns a,asset_gateway_creation g WHERE a.category=?1 AND g.user_id=?2 AND a.fk_admin_id=g.admin_id GROUP BY a.pkadmincolumns_id",nativeQuery=true)
	List<AdminColumns> getAllUserListforstatus(String category, long fkid);

	
	
	@Query(value="SELECT a.columns_names FROM admin_columns a WHERE a.columns_names=?1 AND a.admin_name=?2",nativeQuery=true)
	String getcolumn(String columns,String adminName);

	
	@Query(value="SELECT * FROM admin_columns",nativeQuery=true)
	List<AdminColumns> getAdminColumn();

	
	
	
	@Query(value="SELECT a.columns_names FROM admin_columns a WHERE a.columns_names=?1 AND a.organization=?2",nativeQuery=true)
	String getuicolumnofOraganization(String columns, String organization);


	



	


//	List<String> getUicolumnListforOrganization(long fkid);





	


	





	


}
