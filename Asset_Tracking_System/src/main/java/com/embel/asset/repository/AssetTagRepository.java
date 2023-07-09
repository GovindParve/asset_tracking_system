package com.embel.asset.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.bean.AssetTagBean;
import com.embel.asset.bean.ResponseAssetTagBean;
import com.embel.asset.entity.AssetTag;


@Repository
public interface AssetTagRepository extends JpaRepository<AssetTag, Long>{

	@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status,createdBy) FROM AssetTag  ORDER BY assetTagId asc")
	List<AssetTagBean> gettagListForSuperAdmin();

	@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status,createdBy) FROM AssetTag WHERE fkAdminId = ?1 ORDER BY assetTagId asc")
	List<AssetTagBean> gettagListForAdmin(Long fkID);

	@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status,createdBy) FROM AssetTag  WHERE fkUserId = ?1 ORDER BY assetTagId asc")	
	List<AssetTagBean> getTagListForUser(Long fkID);

	@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status,createdBy) FROM AssetTag WHERE assetTagId = ?1")	
	List<AssetTagBean> gettagForEdit(Long id);

	//-------------------Get gateway count for Super-admin-------------------
	@Query(value="SELECT count(admin) FROM AssetTag WHERE assetTagCategory=?1")
	String getCountForSuperAdmin(String category);

	//-------------------Get gateway count for admin-------------------
	@Query(value="SELECT count(admin) FROM AssetTag WHERE fkAdminId = ?1 AND assetTagCategory=?2")
	String getCountForAdmin(Long fkID,String category);
	
	@Query(value="SELECT count(admin) FROM AssetTag WHERE fkAdminId = ?1 AND assetTagCategory=?2")
	String getCountForEmpuser(long adminId,String category);

	//-------------------Get gateway count for admin-------------------
	@Query(value="SELECT count(user) FROM AssetTag WHERE fkUserId = ?1 AND assetTagCategory=?2")
	String getCountForUser(Long fkID,String category);

	@Modifying
	@Query(value = "UPDATE AssetTag g set g.user = ?1 where g.fkUserId = ?2")
	@Transactional(rollbackFor=Exception.class)
	void updateUserDetails(String userName,Long userId);

	@Modifying
	@Query(value = "UPDATE AssetTag g set g.admin = ?1 where g.fkAdminId = ?2")
	@Transactional(rollbackFor=Exception.class)
	void updateAdminDetails(String userName, Long userId);

	/*
	 * @Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,fkUserId,fkAdminId,wakeupTime,timeZone,dateTime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status) FROM AssetTag WHERE assetTagId = ?1"
	 * ) List<AssetTagBean> getTagDetailsById(long parseLong);
	 */

	@Query("SELECT new com.embel.asset.bean.ResponseAssetTagBean(assetTagId,assetTagName,assetUniqueCodeMacId,assetIMEINumber) FROM AssetTag WHERE status = 'Not-allocated' AND assetTagCategory=?1 ORDER BY assetTagId asc ")
	List<ResponseAssetTagBean> getNotAllocatedTagListForProduct(String category);

	@Query("SELECT new com.embel.asset.bean.ResponseAssetTagBean(assetTagId,assetTagName,assetUniqueCodeMacId,assetIMEINumber) FROM AssetTag WHERE status = 'Not-allocated' AND assetTagCategory=?1 AND fkAdminId =?2 ORDER BY assetTagId asc ")
	List<ResponseAssetTagBean> getNotAllocatedStatusTagListForProductAdmin(String category,Long fkID);
	
	@Query("SELECT new com.embel.asset.bean.ResponseAssetTagBean(assetTagId,assetTagName,assetUniqueCodeMacId,assetIMEINumber) FROM AssetTag WHERE status = 'Not-allocated' AND assetTagCategory=?1 AND fkOrganizationId =?2 ORDER BY assetTagId asc ")
	List<ResponseAssetTagBean> getNotAllocatedStatusTagListForProductOrganization(String category,Long fkID);

	@Query("SELECT new com.embel.asset.bean.ResponseAssetTagBean(assetTagId,assetTagName,assetUniqueCodeMacId,assetIMEINumber) FROM AssetTag WHERE status = 'Not-allocated' AND assetTagCategory=?1 AND fkUserId =?2 ORDER BY assetTagId asc ")
	List<ResponseAssetTagBean> getNotAllocatedStatusTagListForProductUser(String category,Long fkID);
	
	@Query("SELECT new com.embel.asset.bean.ResponseAssetTagBean(assetTagId,assetTagName,assetUniqueCodeMacId,assetIMEINumber) FROM AssetTag WHERE status = 'Not-allocated' AND assetTagCategory=?1 AND assetUniqueCodeMacId LIKE 'G%' ORDER BY assetTagId asc ")
	List<ResponseAssetTagBean> getNotAllocatedTagListForProductforGPS(String category);
	
	
	@Query("SELECT new com.embel.asset.bean.ResponseAssetTagBean(assetTagId,assetTagName,assetUniqueCodeMacId,assetIMEINumber) FROM AssetTag WHERE status = 'Not-allocated' AND assetTagCategory=?1 AND fkOrganizationId =?2 AND assetUniqueCodeMacId LIKE 'G%' ORDER BY assetTagId asc ")
	List<ResponseAssetTagBean> getNotAllocatedStatusTagListForProductOrganizationforGPS(String category, Long fkID);

	@Query("SELECT new com.embel.asset.bean.ResponseAssetTagBean(assetTagId,assetTagName,assetUniqueCodeMacId,assetIMEINumber) FROM AssetTag WHERE status = 'Not-allocated' AND assetTagCategory=?1 AND fkAdminId =?2 AND assetUniqueCodeMacId LIKE 'G%' ORDER BY assetTagId asc ")
	List<ResponseAssetTagBean> getNotAllocatedStatusTagListForProductAdminforGPS(String category, Long fkID);

	@Query("SELECT new com.embel.asset.bean.ResponseAssetTagBean(assetTagId,assetTagName,assetUniqueCodeMacId,assetIMEINumber) FROM AssetTag WHERE status = 'Not-allocated' AND assetTagCategory=?1 AND fkUserId =?2 AND assetUniqueCodeMacId LIKE 'G%' ORDER BY assetTagId asc ")
	List<ResponseAssetTagBean> getNotAllocatedStatusTagListForProductUserforGPS(String category, Long fkID);

	
	
	
	
	
	
	@Modifying
	@Transactional(rollbackFor=Exception.class)
	@Query(value = "UPDATE AssetTag  set status = ?1 WHERE assetTagName = ?2 AND assetUniqueCodeMacId = ?3")
	void updateStatusAfterAllocation(String updatedStatus , String tagName, String tagUniqueCode);

	//Multiple Delete Gateway Details from Database
	@Modifying
	@Transactional
	@Query(value = "delete FROM AssetTag where assetTagId=?1")
	void deleteSelectedTag(Long long1);

	@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status,createdBy) FROM AssetTag WHERE assetTagCategory = 'GPS' ORDER BY assetTagId asc")
	List<AssetTagBean> gettagListForDropdownSuperAdmin();

	@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status,createdBy) FROM AssetTag WHERE assetTagCategory = 'GPS' AND fkAdminId = ?1 ORDER BY assetTagId asc")
	List<AssetTagBean> gettagListForDropdownAdmin(Long fkID);

	@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status,createdBy) FROM AssetTag WHERE assetTagCategory = 'GPS' AND fkUserId = ?1 ORDER BY assetTagId asc")
	List<AssetTagBean> getTagListForDropdownUser(Long fkID);

	AssetTag findByAssetBarcodeSerialNumber(String barCode);

	AssetTag findByAssetUniqueCodeMacId(String uniqueCode);

	
	AssetTag findByAssetIMEINumber(String imeiNumber);
//.........................Pagination...............................................
	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_tag_category='GPS'",nativeQuery=true)
	Page<AssetTag> gettagListForSuperAdminWithPaginationForGps(Pageable pageable);
	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_tag_category='GPS' AND a.organization_id=?1",nativeQuery=true)
	Page<AssetTag> gettagListForOrganizationWithPaginationForGps(Long fkID, Pageable pageable);
	
	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_tag_category='GPS' AND a.admin_id=?1",nativeQuery=true)
	Page<AssetTag> gettagListForAdminWithPaginationForGps(Long fkID, Pageable pageable);
	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_tag_category='GPS' AND a.user_id=?1",nativeQuery=true)
	Page<AssetTag> getTagListForUserWithPaginationForGps(Long fkID, Pageable pageable);
	
	
	
	
	
	
	
	@Query("SELECT new com.embel.asset.bean.AssetTagBean(a.assetTagId,a.assetTagName,a.user,a.admin,organization,fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.assetUniqueCodeMacId,a.assetSimNumber,a.assetIMSINumber,a.assetIMEINumber,a.assetBarcodeSerialNumber,a.assetLocation,a.assetTagCategory,a.status,a.createdBy) FROM AssetTag a WHERE a.assetTagCategory=?1 ORDER BY a.assetTagId  asc")
	Page<AssetTagBean> gettagListForSuperAdminWithPagination(String category, Pageable pageable);
	
	@Query("SELECT new com.embel.asset.bean.AssetTagBean(a.assetTagId,a.assetTagName,a.user,a.admin,organization,fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.assetUniqueCodeMacId,a.assetSimNumber,a.assetIMSINumber,a.assetIMEINumber,a.assetBarcodeSerialNumber,a.assetLocation,a.assetTagCategory,a.status,a.createdBy) FROM AssetTag a WHERE a.assetTagCategory=?1 AND a.fkAdminId=?2 ORDER BY a.assetTagId asc")
	Page<AssetTagBean> gettagListForAdminWithPagination(String category, Long fkID,Pageable pageable);
	
	@Query("SELECT new com.embel.asset.bean.AssetTagBean(a.assetTagId,a.assetTagName,a.user,a.admin,organization,fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.assetUniqueCodeMacId,a.assetSimNumber,a.assetIMSINumber,a.assetIMEINumber,a.assetBarcodeSerialNumber,a.assetLocation,a.assetTagCategory,a.status,a.createdBy) FROM AssetTag a WHERE a.assetTagCategory=?1 AND a.fkOrganizationId=?2 ORDER BY a.assetTagId asc")
	Page<AssetTagBean> gettagListForOrganizationWithPagination(String category, Long fkID, Pageable pageable);
	
	
	@Query("SELECT new com.embel.asset.bean.AssetTagBean(a.assetTagId,a.assetTagName,a.user,a.admin,organization,fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.assetUniqueCodeMacId,a.assetSimNumber,a.assetIMSINumber,a.assetIMEINumber,a.assetBarcodeSerialNumber,a.assetLocation,a.assetTagCategory,a.status,a.createdBy) FROM AssetTag a  WHERE a.assetTagCategory=?1 AND a.fkUserId=?2 ORDER BY a.assetTagId asc")	
	Page<AssetTagBean> getTagListForUserWithPagination(String category, Long fkID,Pageable pageable);
//....................//Get All Tag List .assetTagCategory...................................................................................................................................
//	@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,fkUserId,fkAdminId,wakeupTime,timeZone,dateTime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status) FROM AssetTag WHERE assetTagCategory =?1 ORDER BY assetTagId asc")
//	List<ResponseAssetTagBean> getNotAllocatedTagListForProductassetTagCategorywisePaination(String assetTagCategory);
//
//
//@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,fkUserId,fkAdminId,wakeupTime,timeZone,dateTime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status) FROM AssetTag WHERE assetTagCategory =?1 AND fkAdminId =?2 ORDER BY assetTagId asc")
//List<ResponseAssetTagBean> getNotAllocatedStatusTagListForProductAdminassetTagCategorywisePaination(String assetTagCategory, Long fkID);
//
//
//@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,fkUserId,fkAdminId,wakeupTime,timeZone,dateTime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status) FROM AssetTag WHERE assetTagCategory = ?1 AND fkUserId = ?2 ORDER BY assetTagId asc")
//List<ResponseAssetTagBean> getNotAllocatedStatusTagListForProductUserassetTagCategorywisePaination(
//		String assetTagCategory, Long fkID);
//
////........................categorywise pagination..............................................................
	
	@Query("SELECT new com.embel.asset.bean.AssetTagBean(a.assetTagId,a.assetTagName,a.user,a.admin,organization,fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.assetUniqueCodeMacId,a.assetSimNumber,a.assetIMSINumber,a.assetIMEINumber,a.assetBarcodeSerialNumber,a.assetLocation,a.assetTagCategory,a.status,a.createdBy) FROM AssetTag a WHERE a.assetTagCategory=?1  ORDER BY a.assetTagId  asc")
	Page<AssetTagBean> gettagListForSuperAdminCategorywiseWithPagination(String assetTagCategory, Pageable pageable);
	
	@Query("SELECT new com.embel.asset.bean.AssetTagBean(a.assetTagId,a.assetTagName,a.user,a.admin,organization,fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.assetUniqueCodeMacId,a.assetSimNumber,a.assetIMSINumber,a.assetIMEINumber,a.assetBarcodeSerialNumber,a.assetLocation,a.assetTagCategory,a.status,a.createdBy) FROM AssetTag a WHERE fkAdminId = ?1 AND a.assetTagCategory=?2 ORDER BY a.assetTagId asc")
	Page<AssetTagBean> gettagListForAdminCategorywiseWithPagination(Long fkID, String assetTagCategory,
			Pageable pageable);

	
	@Query("SELECT new com.embel.asset.bean.AssetTagBean(a.assetTagId,a.assetTagName,a.user,a.admin,organization,fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.assetUniqueCodeMacId,a.assetSimNumber,a.assetIMSINumber,a.assetIMEINumber,a.assetBarcodeSerialNumber,a.assetLocation,a.assetTagCategory,a.status,a.createdBy) FROM AssetTag a  WHERE fkUserId = ?1 AND a.assetTagCategory=?2 ORDER BY a.assetTagId asc")	
	Page<AssetTagBean> getTagListForUserCategorywiseWithPagination(Long fkID, String assetTagCategory,
			Pageable pageable);
	
	
	
	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.asset_enable=?1 AND atg.asset_tag_category=?2",nativeQuery=true)
	long AssetTagWorkingCountforSuperAdmin(long assetTagWorking, String category);
	
	
	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.asset_enable=?1 AND atg.asset_tag_category=?2",nativeQuery=true)
	long AssetTagNonWorkingCountforSuperAdmin(long assetTagNonworking, String category);

	
	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.admin_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category=?3",nativeQuery=true)
	long AssetTagWorkingCountforAdmin(Long fkID, long assetTagWorking, String category);

	
	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.admin_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category=?3",nativeQuery=true)
	long AssetTagNonWorkingCountforAdmin(Long fkID, long assetTagNonworking, String category);

	
	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.user_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category=?3",nativeQuery=true)
	long AssetTagWorkingCountforUser(Long fkID, long assetTagWorking, String category);

	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.user_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category=?3",nativeQuery=true)
	long AssetTagNonWorkingCountforUser(Long fkID, long assetTagNonworking, String category);

	
	
	
	
	@Query(value="SELECT atd.barcode_or_serial_number FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atg.admin_id=?1 AND atd.date=?2 AND atg.user_id=?3",nativeQuery=true)
	String findBydate(long adminid, LocalDateTime twodaysearlierDate, long userid);

	
	@Query(value="SELECT g.user_id FROM asset_tag_generation g WHERE g.admin_id=?1",nativeQuery=true)
	List<Long> findAllUser(long adminid);

	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tag_generation ag SET ag.asset_enable=0 WHERE ag.asset_tag_name=?1",nativeQuery=true)
	void switchoff(String tagname);

	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tag_generation ag SET ag.asset_enable=1 WHERE ag.asset_tag_name=?1",nativeQuery=true)
	void switchOn(String tanname);

	@Query(value="SELECT atd.asset_tag_name FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atd.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.admin_id=?1 AND atd.date=?2 AND atg.user_id=?3",nativeQuery=true)
	String findByCurrentDate(long adminid, LocalDateTime currentdate, long userid);

	@Query(value="SELECT a.asset_tag_name FROM asset_tracking_details a WHERE a.date BETWEEN ?1 AND ?2 AND a.asset_tag_name=?3",nativeQuery=true)
	String findBydate( LocalDateTime twodaysearlierDate, LocalDateTime currentdate,String assetTagName);
//..........................................................................................................................
	@Query(value="SELECT p.allocated_tag FROM product_tag_allocation p ORDER BY p.allocated_tag_id DESC",nativeQuery=true)
	List<String> getallocatetagListForSuperAdmin();
	
	@Query(value="SELECT p.allocated_tag FROM product_tag_allocation p WHERE p.user_id=?1 ORDER BY p.allocated_tag_id DESC",nativeQuery=true)
	List<String> getallocatedtagListForAdmin(long userid);
	
	@Query(value="SELECT p.allocated_tag FROM product_tag_allocation p WHERE p.user_id=?1",nativeQuery=true)
	List<String> getallocatedtagListForAdminfornotification(long userid, LocalDateTime formdate, LocalDateTime currentdate);
	
	
	@Query(value="SELECT p.allocated_tag FROM product_tag_allocation p WHERE p.user_id=?1 ORDER BY p.allocated_tag_id DESC",nativeQuery=true)
	List<String> getallocatedTagListForUser(long userid);
	
	
	
	
	@Query(value="SELECT atd.barcode_or_serial_number FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atg.admin_id=?1 AND atd.datetime between ?2 AND ?3 AND atg.user_id=?4",nativeQuery=true)
	String finddatabetweenDate(long adminid, LocalDateTime formdate, LocalDateTime currentdate, long userid);

	
	@Query(value="SELECT a.asset_tag_name FROM asset_tag_generation a WHERE a.asset_tag_category='GPS'",nativeQuery=true)
	List<String> getallgpstaglistforSuperAdmin();

	@Query(value="SELECT a.asset_tag_name FROM asset_tag_generation a WHERE a.admin_id=?1 AND a.asset_tag_category='GPS'",nativeQuery=true)
	List<String> getallgpstaglistforAdmin(long fkid);

	
	@Query(value="SELECT a.asset_tag_name FROM asset_tag_generation a WHERE a.user_id=?1 AND a.asset_tag_category='GPS'",nativeQuery=true)
	List<String> getallgpstaglistforUser(long fkid);

	
	
	
	
	@Query(value="SELECT * FROM asset_tag_generation",nativeQuery=true)
	List<AssetTag> WorkingNonworkingTagReportForSuperAdminExportDownload();

	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.admin_id=?1",nativeQuery=true)
	List<AssetTag> WorkingNonworkingTagReportForAdminExportDownload(Long fkID);

	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.user_id=?1",nativeQuery=true)
	List<AssetTag> WorkingNonworkingTagReportForUserExportDownload(Long fkID);

	
	
	

	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.admin_id=?1",nativeQuery=true)
	List<AssetTag> getallTagListForAdmin(long fkid);

	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.user_id=?1",nativeQuery=true)
	List<AssetTag> getTagListsForUser(long fkid);

	
	@Query(value="SELECT count(admin) FROM AssetTag WHERE fkOrganizationId = ?1")
	String getCountForOrganization(Long fkID, String category);

	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.organization_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category=?3",nativeQuery=true)
	long AssetTagWorkingCountforOrganization(Long fkID, long assetTagWorking, String category);

	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.organization_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category=?3",nativeQuery=true)
	long AssetTagNonWorkingCountforOrganization(Long fkID, long assetTagNonworking, String category);

	
	@Query(value="SELECT a.asset_tag_name FROM asset_tag_generation a WHERE a.organization_id=?1 AND a.asset_tag_category='GPS'",nativeQuery=true)
	List<String> getallgpstaglistforOrganization(long fkid);

	
	
	@Query("SELECT new com.embel.asset.bean.AssetTagBean(assetTagId,assetTagName,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,assetUniqueCodeMacId,assetSimNumber,assetIMSINumber,assetIMEINumber,assetBarcodeSerialNumber,assetLocation,assetTagCategory,status,createdBy) FROM AssetTag WHERE assetTagCategory = 'GPS' AND fkOrganizationId = ?1 ORDER BY assetTagId asc")
	List<AssetTagBean> gettagListForDropdownOrganization(Long fkID);

	
	
//
//	@Query(value="SELECT atg.asset_tag_category FROM asset_tag_generation atg,user_details u WHERE u.username=?1 GROUP BY atg.asset_tag_category",nativeQuery=true)
//	List<String> getcategoryList(String username);

	
	@Query(value="SELECT atg.asset_tag_category FROM user_details u,asset_tag_generation atg WHERE u.username=?1 GROUP BY atg.asset_tag_category",nativeQuery=true)
	List<String> getcategoryListForSuperAdmin(String username);
	
	@Query(value="SELECT atg.asset_tag_category FROM user_details u,asset_tag_generation atg WHERE u.pkuser_id=atg.admin_id AND u.username=?1 GROUP BY atg.asset_tag_category",nativeQuery=true)
	List<String> getcategoryListForAdmin(String username);

	
	@Query(value="SELECT atg.asset_tag_category FROM user_details u,asset_tag_generation atg WHERE u.pkuser_id=atg.organization_id AND atg.organization_name=?1 GROUP BY atg.asset_tag_category",nativeQuery=true)
	List<String> getcategoryListForOrganization(String username);

	@Query(value="SELECT atg.asset_tag_category FROM user_details u,asset_tag_generation atg WHERE u.pkuser_id=atg.user_id AND u.username=?1 GROUP BY atg.asset_tag_category",nativeQuery=true)
	List<String> getcategoryListForUser(String username);

	
	
	
	@Query(value="select Count(a.asset_tag_id) FROM asset_tag_generation a WHERE a.asset_tag_category='GPS'",nativeQuery=true)
	String tagCountgpsForSuperAdmin();

	
	
	
	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.asset_enable=?1 AND atg.asset_tag_category='GPS'",nativeQuery=true)
	long AssetTagWorkingCountforSuperAdminGPS(long assetTagWorking);

	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.asset_enable=?1 AND atg.asset_tag_category='GPS'",nativeQuery=true)
	long AssetTagNonWorkingCountforSuperAdminGPS(long assetTagNonworking);

	
	
	
	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.organization_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category='GPS'",nativeQuery=true)
	long AssetTagWorkingCountforOrganizationGPS(Long fkID, long assetTagWorking);

	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.organization_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category='GPS'",nativeQuery=true)
	long AssetTagNonWorkingCountforOrganizationGPS(Long fkID, long assetTagNonworking);

	
	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.admin_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category='GPS'",nativeQuery=true)
	long AssetTagWorkingCountforAdminGPS(Long fkID, long assetTagWorking);

	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.admin_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category='GPS'",nativeQuery=true)
	long AssetTagNonWorkingCountforAdminGPS(Long fkID, long assetTagNonworking);

	
	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.user_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category='GPS'",nativeQuery=true)
	long AssetTagWorkingCountforUserGPS(Long fkID, long assetTagWorking);

	@Query(value="SELECT COUNT(atg.asset_enable) FROM asset_tag_generation atg WHERE atg.user_id=?1 AND atg.asset_enable=?2 AND atg.asset_tag_category='GPS'",nativeQuery=true)
	long AssetTagNonWorkingCountforUserGPS(Long fkID, long assetTagNonworking);

	
	
	@Query(value="select Count(a.asset_tag_id) FROM asset_tag_generation a WHERE a.asset_tag_category='GPS'AND a.organization_id=?1",nativeQuery=true)
	String tagCountgpsForOrganization(String fkUserId);

	@Query(value="select Count(a.asset_tag_id) FROM asset_tag_generation a WHERE a.asset_tag_category='GPS'AND a.admin_id=?1",nativeQuery=true)
	String tagCountgpsForAdmin(String fkUserId);

	@Query(value="select Count(a.asset_tag_id) FROM asset_tag_generation a WHERE a.asset_tag_category='GPS'AND a.user_id=?1",nativeQuery=true)
	String tagCountgpsForUser(String fkUserId);

	
	@Query(value="SELECT a.admin_id FROM asset_tag_generation a WHERE a.user_id=?1",nativeQuery=true)
	long getAdminid(String fkUserId);

	@Query(value="SELECT COUNT(a.asset_tag_name) FROM asset_tag_generation a WHERE a.organization_id=?1",nativeQuery=true)
	String getUserWiseTagCountForOrganization(String fkUserId);

	@Query(value="SELECT COUNT(a.asset_tag_name) FROM asset_tag_generation a WHERE a.admin_id=?1",nativeQuery=true)
	String getUserWiseTagCountForAdmin(String fkUserId);

	
	@Query(value="SELECT COUNT(a.asset_tag_name) FROM asset_tag_generation a WHERE a.user_id=?1",nativeQuery=true)
	String getUserWiseTagCountForUser(String fkUserId);

	@Query(value="SELECT COUNT(a.asset_tag_name) FROM asset_tag_generation a",nativeQuery=true)
	String getUserWiseTagCountForSuperAdmin();

	
	
	@Query(value="SELECT a.time_zone FROM asset_tag_generation a WHERE a.admin_id=?1 GROUP BY a.time_zone",nativeQuery=true)
	String getTimeZone(long adminid);

	
	@Query(value="SELECT a.time_zone FROM asset_tag_generation a WHERE a.asset_tag_name=?1",nativeQuery=true)
	String getTimeZoneFromTagName(String assetTagName);

	
	@Query(value="SELECT a.asset_tag_name FROM asset_tag_generation a WHERE a.admin_id=?1",nativeQuery=true)
	List<String> findAllTags(long adminid);

	

	
	@Query(value="SELECT a.asset_barcode_number_or_serial_number FROM asset_tag_generation a WHERE a.asset_barcode_number_or_serial_number=?1",nativeQuery=true)
	String findByAssetBarcodeSerialNumberXL(String assetBarcodeSerialNumber);
	
	@Query(value="SELECT a.asset_barcode_number FROM asset_tag_generation a ORDER BY asset_barcode_number DESC LIMIT 1",nativeQuery=true)
	List<Long> getLastAssetBarcodeNumber();
	
	@Query(value="SELECT a.asset_unique_code_or_mac_id FROM asset_tag_generation a WHERE a.asset_unique_code_or_mac_id=?1",nativeQuery=true)
	String findByAssetUniqueCodeMacIdXL(String valueOf);

	
	@Query(value="SELECT a.asset_imei_number FROM asset_tag_generation a WHERE a.asset_imei_number=?1",nativeQuery=true)
	String findByimei(String assetIMEINumber);

	
	
	@Query(value="SELECT Count(a.asset_barcode_number_or_serial_number) FROM asset_tag_generation a WHERE a.asset_barcode_number_or_serial_number=?1",nativeQuery=true)
	String getcountbarcodeList(String assetBarcodeSerialNumber);

	
	@Query(value="SELECT Count(a.asset_unique_code_or_mac_id) FROM asset_tag_generation a WHERE a.asset_unique_code_or_mac_id=?1",nativeQuery=true)
	String getcountuniqueCodeList(String valueOf);

	
	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_enable='0' AND a.asset_tag_category=?1",nativeQuery=true)
	List<AssetTag> getNonWorkingTagListForSuperAdmin(String category);

	

	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_enable='0' AND a.organization_id=?1 AND a.asset_tag_category=?2",nativeQuery=true)
	List<AssetTag> getNonWorkingTagListForOrganization(String fkUserId, String category);

	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_enable='0' AND a.admin_id=?1 AND a.asset_tag_category=?2",nativeQuery=true)
	List<AssetTag> getNonWorkingTagListForAdmin(String fkUserId, String category);

	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_enable='0' AND a.user_id=?1 AND a.asset_tag_category=?2",nativeQuery=true)
	List<AssetTag> getNonWorkingTagListForUser(String fkUserId, String category);

	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_enable='1' AND a.asset_tag_category=?1",nativeQuery=true)
	List<AssetTag> getWorkingTagListForSuperAdmin(String category);

	
	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_enable='1' AND a.organization_id=?1 AND a.asset_tag_category=?2",nativeQuery=true)
	List<AssetTag> getWorkingTagListForOrganization(String fkUserId, String category);

	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_enable='1' AND a.admin_id=?1 AND a.asset_tag_category=?2",nativeQuery=true)
	List<AssetTag> getWorkingTagListForAdmin(String fkUserId, String category);

	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_enable='1' AND a.user_id=?1 AND a.asset_tag_category=?2",nativeQuery=true)
	List<AssetTag> getWorkingTagListForUser(String fkUserId, String category);

	
	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.admin_id=?1 AND a.asset_tag_category=?2 AND a.tag_status='Not-allocated'",nativeQuery=true)
	List<AssetTag> getAdminWiseTagList(long fkUserId, String category);

	
	@Query(value="SELECT a.user_name FROM asset_tag_generation a WHERE a.asset_tag_name=?1",nativeQuery=true)
	String getUserOfTag(String allocatedTagName);

	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_imei_number=?1",nativeQuery=true)
	AssetTag getbyimeiNumber(long imeiNumber);
	
	@Query(value="SELECT * FROM asset_tag_generation a WHERE a.asset_imsi_number=?1",nativeQuery=true)
	AssetTag getbyImsiNumber(long imsiNumber);

	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.imei_number=?1",nativeQuery=true)
	List<AssetTag> getalllistforinsertion(String imeiNumber);
	
	
	//
	
	@Query(value="SELECT a.asset_tag_category FROM asset_tag_generation a WHERE a.asset_imei_number=?1",nativeQuery=true)
	String getcategoryonimei(String imei);

	
	
	
	
	@Query(value="SELECT a.asset_imsi_number FROM asset_tag_generation a WHERE a.asset_imsi_number=?1",nativeQuery=true)
	String findByimsi(String assetIMSINumber);




	

	
	


	

	



	
	
	

	

	

	

	
	
	
	

	
	

	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	@Query(value="SELECT * FROM product_tag_allocation p",nativeQuery=true)
//	List<ProductDetailForAllocation> getallocatedtagListForSuperAdmin();
//
//	@Query(value="SELECT * FROM product_tag_allocation p WHERE p.user_id=?1",nativeQuery=true)
//	List<AssetTagBean> getallocatedtagListForAdmin(long userid);
//	

////	@Query(value="",nativeQuery=true)
////
////	AssetTag getDatewiseBetweenDateTagforSuperAdmin(Date fromdate, Date todate);

	

	
	
	

}
