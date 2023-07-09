package com.embel.asset.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.embel.asset.entity.ExcelPDFDownloadCountReport;

public interface UserDownloadRepo extends JpaRepository<ExcelPDFDownloadCountReport,Long> {

	@Query(value="SELECT d.dwnld_count From excel_pdf_download_count_report d",nativeQuery=true)
	long findByDownlodingCount();
	
	
	@Query(value="SELECT COUNT(e.dwnld_count) FROM excel_pdf_download_count_report e",nativeQuery=true)
	long getdownlodeingCountforSuperAdmin();

	@Query(value="SELECT COUNT(e.dwnld_count) FROM excel_pdf_download_count_report e,user_details u WHERE e.fk_user_id=u.pkuser_id AND e.fk_user_id=?1",nativeQuery=true)
	long getdownlodeingCountforSupeUser(long l);

	@Query(value="SELECT u.username FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getdownlodeUserNameforSuperAdmin(long parseLong);

	@Query(value="SELECT u.username FROM user_details u WHERE u.pkuser_id=?1",nativeQuery=true)
	String getdownlodeUserNameforSupeUser(long parseLong);

	@Query(value="SELECT COUNT(ag.user_id) FROM excel_pdf_download_count_report e,asset_gateway_creation ag WHERE e.fk_user_id=ag.admin_id AND e.fk_user_id=?1",nativeQuery=true)
	long getdownlodeingCountforAdmin(long userid);
	
	
	
	
	@Query(value="SELECT COUNT(ag.user_id) FROM excel_pdf_download_count_report e,asset_gateway_creation ag WHERE e.fk_user_id=ag.admin_id AND e.fk_user_id=?1 AND e.time_stamp between ?2 AND ?3",nativeQuery=true)
	long getdownlodeingCountforAdminnotification(long userid, LocalDateTime formdate, LocalDateTime currentdate);

	@Query(value="SELECT COUNT(e.dwnld_count) FROM excel_pdf_download_count_report e,user_details u WHERE e.fk_user_id=u.pkuser_id AND e.fk_user_id=?1",nativeQuery=true)
	long getdownlodeingCountforOrganization(long parseLong);
}
