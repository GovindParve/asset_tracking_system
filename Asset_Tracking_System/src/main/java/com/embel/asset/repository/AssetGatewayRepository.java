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

import com.embel.asset.bean.AssetGatewayBean;
import com.embel.asset.entity.AssetGateway;
import com.embel.asset.entity.AssetGatewayStock;

@Repository
public interface AssetGatewayRepository extends JpaRepository<AssetGateway, Long>{

	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(gatewayId,gatewayName,gatewayUniqueCodeMacId,gatewayBarcodeSerialNumber,gatewayLocation,assetTagCategory,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,createdBy) FROM AssetGateway ORDER BY gatewayId asc")
	List<AssetGatewayBean> getGatewayListForSuperAdmin();

	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(gatewayId,gatewayName,gatewayUniqueCodeMacId,gatewayBarcodeSerialNumber,gatewayLocation,assetTagCategory,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,createdBy) FROM AssetGateway WHERE fkAdminId = ?1 ORDER BY gatewayId asc")
	List<AssetGatewayBean> getGatewayListForAdmin(Long fkID);
	
	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(gatewayId,gatewayName,gatewayUniqueCodeMacId,gatewayBarcodeSerialNumber,gatewayLocation,assetTagCategory,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,createdBy) FROM AssetGateway WHERE fkUserId = ?1 ORDER BY gatewayId asc")
	List<AssetGatewayBean> getGatewayListForUser(Long fkID);
	
	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(gatewayId,gatewayName,gatewayUniqueCodeMacId,gatewayBarcodeSerialNumber,gatewayLocation,assetTagCategory,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,createdBy) FROM AssetGateway WHERE gatewayId=?1")	
	List<AssetGatewayBean> getGatewayForEdit(Long id);
	
	//-------------------Get gateway count for Super-admin-------------------
	@Query(value="SELECT count(admin) FROM AssetGateway WHERE assetTagCategory=?1")
	String getCountForSuperAdmin(String category);

	//-------------------Get gateway count for admin-------------------
	@Query(value="SELECT count(admin) FROM AssetGateway WHERE fkAdminId=?1 AND assetTagCategory=?2")
	String getCountForAdmin(Long fkID, String category);
	@Query(value="SELECT count(admin) FROM AssetGateway WHERE fkAdminId=?1 AND assetTagCategory=?2")
	String getCountForEmpUser(long adminId);
	
	@Query("SELECT count(user) FROM AssetGateway WHERE fkUserId=?1 AND assetTagCategory=?2")
	String getGatewayCountForUser(Long fkID, String category);

	@Modifying
	@Query(value = "UPDATE AssetGateway g set g.user = ?1 where g.fkUserId = ?2")
	@Transactional(rollbackFor=Exception.class)
	void updateUserDetails(String userName,Long id);

	@Modifying
	@Query(value = "UPDATE AssetGateway g set g.admin = ?1 where g.fkAdminId = ?2")
	@Transactional(rollbackFor=Exception.class)
	void updateAdminDetails(String userName, Long id);

	//Multiple Delete Gateway Details from Database
	@Modifying
	@Transactional
	@Query(value = "delete FROM AssetGateway where gatewayId=?1")
	void deleteSelectedGateway(Long gatewayId);

	/*
	 * @Query("SELECT new com.embel.asset.bean.AssetGatewayBean(gatewayId,gatewayName,gatewayUniqueCodeMacId,gatewayBarcodeOrSerialNumber,gatewayLocation,assetTagCategory,user,admin,fkUserId,fkAdminId,wakeupTime,timeZone,dateTime) FROM AssetGateway WHERE gatewayId = ?1"
	 * ) List<AssetGatewayBean> getGatewayDetailsById(long parseLong);
	 */

	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(gatewayId,gatewayName,gatewayUniqueCodeMacId,gatewayBarcodeSerialNumber,gatewayLocation,assetTagCategory,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,createdBy) FROM AssetGateway ORDER BY gatewayId asc")
	List<AssetGatewayBean> getGatewayListForDropdownSuperAdmin();

	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(gatewayId,gatewayName,gatewayUniqueCodeMacId,gatewayBarcodeSerialNumber,gatewayLocation,assetTagCategory,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,createdBy) FROM AssetGateway WHERE fkAdminId = ?1 ORDER BY gatewayId asc")
	List<AssetGatewayBean> getGatewayListForDropdownAdmin(Long fkID);

	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(gatewayId,gatewayName,gatewayUniqueCodeMacId,gatewayBarcodeSerialNumber,gatewayLocation,assetTagCategory,user,admin,organization,fkOrganizationId,fkUserId,fkAdminId,wakeupTime,timeZone,datetime,createdBy) FROM AssetGateway WHERE fkUserId = ?1 ORDER BY gatewayId asc")
	List<AssetGatewayBean> getGatewayListForDropdownUser(Long fkID);

	@Query(value = "SELECT gateway_location FROM asset_gateway_creation WHERE gateway_unique_code_or_mac_id = ?1" , nativeQuery = true)
	String getGatewayLocation(String assetGatewayMacID);

	@Query(value = "SELECT gateway_location FROM asset_gateway_creation ORDER BY gateway_id" , nativeQuery = true)
	List<String> getGatewayLocationListForDropdownSuperAdmin();


	@Query(value = "SELECT a.gateway_location FROM asset_gateway_creation a WHERE a.organization_id=?1 ORDER BY a.gateway_id" , nativeQuery = true)	
	List<String> getGatewayLocationListForDropdownOrganization(Long fkID);

	@Query(value = "SELECT gateway_location FROM asset_gateway_creation WHERE admin_id = ?1 ORDER BY gateway_id" , nativeQuery = true)
	List<String> getGatewayLocationListForDropdownAdmin(Long fkID);

	@Query(value = "SELECT gateway_location FROM asset_gateway_creation WHERE user_id = ?1 ORDER BY gateway_id" , nativeQuery = true)
	List<String> getGatewayLocationListForDropdownUser(Long fkID);

	List<AssetGateway> findByGatewayUniqueCodeMacId(String uniqueCode);

	List<AssetGateway> findByGatewayBarcodeSerialNumber(String barCode);
//.........................pagination..............................................................................
	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(a.gatewayId,a.gatewayName,a.gatewayUniqueCodeMacId,a.gatewayBarcodeSerialNumber,a.gatewayLocation,a.assetTagCategory,a.user,a.admin,a.organization,a.fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.createdBy) FROM AssetGateway a ORDER BY gatewayId asc")
	Page<AssetGatewayBean> getGatewayListForSuperAdminWithPagination(Pageable pageable);

	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(a.gatewayId,a.gatewayName,a.gatewayUniqueCodeMacId,a.gatewayBarcodeSerialNumber,a.gatewayLocation,a.assetTagCategory,a.user,a.admin,a.organization,a.fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.createdBy) FROM AssetGateway a WHERE fkAdminId = ?1 ORDER BY gatewayId asc")
	Page<AssetGatewayBean> getGatewayListForAdminWithPagination(Long fkID, Pageable pageable);
	
	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(a.gatewayId,a.gatewayName,a.gatewayUniqueCodeMacId,a.gatewayBarcodeSerialNumber,a.gatewayLocation,a.assetTagCategory,a.user,a.admin,a.organization,a.fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.createdBy) FROM AssetGateway a WHERE fkOrganizationId = ?1 ORDER BY gatewayId asc")
	Page<AssetGatewayBean> getGatewayListForOrganizationWithPagination(Long fkID, Pageable pageable);
	
	@Query("SELECT new com.embel.asset.bean.AssetGatewayBean(a.gatewayId,a.gatewayName,a.gatewayUniqueCodeMacId,a.gatewayBarcodeSerialNumber,a.gatewayLocation,a.assetTagCategory,a.user,a.admin,a.organization,a.fkOrganizationId,a.fkUserId,a.fkAdminId,a.wakeupTime,a.timeZone,a.datetime,a.createdBy) FROM AssetGateway a WHERE fkUserId = ?1 ORDER BY gatewayId asc")
	Page<AssetGatewayBean> getGatewayListForUserWithPagination(Long fkID, Pageable pageable);
//..........................................................................................
	@Query(value="SELECT COUNT(a.admin_name) FROM asset_gateway_creation a WHERE a.asset_getway_enable=?1 AND a.asset_tag_category=?2",nativeQuery=true)
	long getWorkingCountforSuperAdmin(long workingGetway, String category);

	@Query(value="SELECT COUNT(a.admin_name) FROM asset_gateway_creation a WHERE a.asset_getway_enable=?1 AND a.asset_tag_category=?2",nativeQuery=true)
	long getNonworkingCountforSuperAdmin(long nonWorkingGetway, String category);

	@Query(value="SELECT COUNT(a.admin_name) FROM asset_gateway_creation a WHERE a.asset_getway_enable=?1 AND a.admin_id=?2 AND a.asset_tag_category=?3",nativeQuery=true)
	long getWorkingCountforAdmin(long workingGetway, Long fkID, String category);
	
	@Query(value="SELECT COUNT(a.admin_name) FROM asset_gateway_creation a WHERE a.asset_getway_enable=?1 AND a.admin_id=?2 AND a.asset_tag_category=?3",nativeQuery=true)
	long getNonworkingCountforAdmin(long nonWorkingGetway, Long fkID, String category);

	@Query(value="SELECT COUNT(a.admin_name) FROM asset_gateway_creation a WHERE a.asset_getway_enable=?1 AND a.user_id=?2 AND a.asset_tag_category=?3",nativeQuery=true)
	long getWorkingCountforUser(long workingGetway, Long fkID, String category);
	
	@Query(value="SELECT COUNT(a.admin_name) FROM asset_gateway_creation a WHERE a.asset_getway_enable=?1 AND a.user_id=?2 AND a.asset_tag_category=?3",nativeQuery=true)
	long getNonworkingCountforUser(long nonWorkingGetway, Long fkID, String category);

	@Query(value="SELECT g.user_id FROM asset_gateway_creation g WHERE g.admin_id=?1",nativeQuery=true)
	List<Long> findAllUser(long adminid);

	

	@Modifying
	@Transactional
	@Query(value="UPDATE asset_gateway_creation ac SET ac.asset_getway_enable=1 WHERE ac.gateway_name=?1",nativeQuery=true)
	void switchOn(String gatewayName);

	@Query(value="SELECT ac.gateway_name FROM asset_gateway_creation ac WHERE ac.admin_id=?1",nativeQuery=true)
	List<String> findAllGetWay(long adminid);

	@Query(value="SELECT atd.barcode_or_serial_number FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atg.admin_id=?1 AND atd.date between ?2 AND ?3 AND atd.asset_gateway_name=?4",nativeQuery=true)
	String finddatabetweenDate(long adminid, LocalDateTime formdate, LocalDateTime currentdate, String getwayName);
	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_gateway_creation ac SET ac.asset_getway_enable=0 WHERE ac.gateway_name=?1",nativeQuery=true)
	void switchoff(String getwayName);
	
	
	@Query(value="SELECT * FROM asset_gateway_creation WHERE gateway_unique_code_or_mac_id=?1",nativeQuery=true)
	AssetGateway findByGatewayMacId(String getgMacId);

	//----------gateway name list for dropdown---------------
	@Query(value="SELECT a.gateway_name FROM asset_gateway_creation a ORDER BY a.gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownforSuperAdmin();

	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.admin_id=?1 ORDER BY a.gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownforAdmin(Long fkID);

	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.user_id=?1 ORDER BY a.gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownforUser(Long fkID);





	@Query(value="SELECT a.gateway_name FROM asset_gateway_creation a ORDER BY a.gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownforsheetSuperAdmin();

	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.organization_id=?1 ORDER BY a.gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownforsheetOrganizationReport(long fkuserid);
	
	
	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.organization_id=?1 ORDER BY a.gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownforsheetOrganization(long fkuserid);
	
	
	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.admin_id=?1 ORDER BY a.gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownforsheetAdmin(Long fkID);

	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.user_id=?1 ORDER BY a.gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownforsheetUser(Long fkID);

	
	
	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a ", nativeQuery = true)
	List<String> getGatewayNameListForSuperAdmin();

	
	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.admin_id=?1", nativeQuery = true)
	List<String> getGatewayNameListForAdmin(Long id);
	
	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.user_id=?1", nativeQuery = true)
	List<String> getGatewayNameListForUser(Long id);

	@Query(value = "SELECT * FROM asset_gateway_creation a WHERE a.admin_id=?1", nativeQuery = true)
	List<AssetGateway> getalllistforAdmin(long fkid);

	@Query(value = "SELECT * FROM asset_gateway_creation a WHERE a.user_id=?1", nativeQuery = true)
	List<AssetGateway> getalllistforUser(long fkid);
	
	@Query(value="SELECT count(admin) FROM AssetGateway WHERE fkOrganizationId=?1 AND assetTagCategory=?2")
	String getCountForOrganization(Long fkID, String category);

	@Query(value="SELECT COUNT(a.admin_name) FROM asset_gateway_creation a WHERE a.asset_getway_enable=?1 AND a.organization_id=?2 AND a.asset_tag_category=?3",nativeQuery=true)
	long getWorkingCountforOrganization(long workingGetway, Long fkID, String category);
	
	@Query(value="SELECT COUNT(a.admin_name) FROM asset_gateway_creation a WHERE a.asset_getway_enable=?1 AND a.organization_id=?2 AND a.asset_tag_category=?3",nativeQuery=true)
	long getNonworkingCountforOrganization(long nonWorkingGetway, Long fkID, String category);
	
	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.organization_id=?1 ORDER BY a.gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownforOrganization(Long fkID);

	@Query(value = "SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.organization_id=?1", nativeQuery = true)
	List<String> getGatewayNameListForOrganization(Long id);

	@Query(value = "SELECT a.x_coordinate FROM asset_gateway_creation a WHERE a.gateway_name=?1", nativeQuery = true)
	long getxCoordinate(String string);

	@Query(value = "SELECT a.y_coordinate FROM asset_gateway_creation a WHERE a.gateway_name=?1", nativeQuery = true)
	long getyCoordinate(String string);

	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_gateway_creation ac SET ac.x_coordinate=?1,ac.y_coordinate=?2 WHERE ac.gateway_name=?3",nativeQuery=true)
	void changePossitions(long x, long y,String gatewayName);
	
	
	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_gateway_creation ac SET ac.x_coordinate=?1,ac.y_coordinate=?2 WHERE ac.gateway_name=?3 AND ac.admin_id=?4 ",nativeQuery=true)
	void changePossitionsForAdmin(long x, long y, String gatewayName, long fkid);

	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_gateway_creation ac SET ac.x_coordinate=?1,ac.y_coordinate=?2 WHERE ac.gateway_name=?3 AND ac.organization_id=?4",nativeQuery=true)
	void changePossitionsForOrganization(long x, long y, String gatewayName, long fkid);

	
	
	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_gateway_creation ac SET ac.x_coordinate=?1,ac.y_coordinate=?2 WHERE ac.gateway_name=?3 AND ac.user_id=?4",nativeQuery=true)
	void changePossitionsForUser(long x, long y, String gatewayName, long fkid);

	
	@Query(value="SELECT COUNT(a.gateway_name) FROM asset_gateway_creation a WHERE a.organization_id=?1",nativeQuery=true)
	String getUserWiseGatewayCountForOrganization(String fkUserId);

	@Query(value="SELECT COUNT(a.gateway_name) FROM asset_gateway_creation a WHERE a.admin_id=?1",nativeQuery=true)
	String getUserWiseGatewayCountForAdmin(String fkUserId);

	
	@Query(value="SELECT COUNT(a.gateway_name) FROM asset_gateway_creation a  WHERE a.user_id=?1",nativeQuery=true)
	String getUserWiseGatewayCountForUser(String fkUserId);

	@Query(value="SELECT COUNT(a.gateway_name) FROM asset_gateway_creation a",nativeQuery=true)
	String getUserWiseGatewayCountForSuperAdmin();

	
	@Query(value="SELECT a.gateway_name FROM asset_gateway_creation a WHERE a.admin_id=?1",nativeQuery=true)
	List<String> findAllGateways(long adminid);

	
	
	@Query(value="SELECT a.time_zone FROM asset_gateway_creation a WHERE a.admin_id=?1 GROUP BY a.time_zone",nativeQuery=true)
	String getTimeZone(long adminid);

	
	
	
	
	
	@Query(value="SELECT a.gateway_barcode_number FROM asset_gateway_creation a ORDER BY gateway_barcode_number DESC LIMIT 1",nativeQuery=true)
	List<Long> getLastGatewayBarcodeNumber();
	
	
	@Query(value="SELECT a.gateway_unique_code_or_mac_id FROM asset_gateway_creation a WHERE a.gateway_unique_code_or_mac_id=?1",nativeQuery=true)
	String getMacId(String gatewayUniqueCodeMacId);

	
	@Query(value="SELECT a.gateway_barcode_number_or_serial_number FROM asset_gateway_creation a WHERE a.gateway_barcode_number_or_serial_number=?1",nativeQuery=true)
	String getGatewayBarcodeOrSerialNumber(String gatewayBarcodeOrSerialNumber);

	@Query(value="SELECT Count(a.gateway_unique_code_or_mac_id) FROM asset_gateway_creation a WHERE a.gateway_unique_code_or_mac_id=?1",nativeQuery=true)
	String getMacIdCount(String gatewayUniqueCodeMacId);

	
	@Query(value="SELECT Count(a.gateway_barcode_number_or_serial_number) FROM asset_gateway_creation a WHERE a.gateway_barcode_number_or_serial_number=?1",nativeQuery=true)
	String getGatewayBarcodeOrSerialNumberCount(String gatewayBarcodeOrSerialNumber);

	
	
	
	
	@Query(value="SELECT * FROM asset_gateway_creation g WHERE g.asset_getway_enable='1'",nativeQuery=true)
	List<AssetGateway> getAllWorkingGatewayListxl();

	@Query(value="SELECT * FROM asset_gateway_creation g WHERE g.asset_getway_enable='1' AND g.organization_id=?1",nativeQuery=true)
	List<AssetGateway> getalllistWorkingGatewayforOrganization(long fkid);

	
	@Query(value="SELECT * FROM asset_gateway_creation g WHERE g.asset_getway_enable='1' AND g.admin_id=?1",nativeQuery=true)
	List<AssetGateway> getalllistWorkingGatewayforAdmin(long fkid);

	@Query(value="SELECT * FROM asset_gateway_creation g WHERE g.asset_getway_enable='1' AND g.user_id=?1",nativeQuery=true)
	List<AssetGateway> getalllistWorkingGatewayforUser(long fkid);

	
	@Query(value="SELECT * FROM asset_gateway_creation g WHERE g.asset_getway_enable='0'",nativeQuery=true)
	List<AssetGateway> getAllNonWorkingGatewayListxl();

	@Query(value="SELECT * FROM asset_gateway_creation g WHERE g.asset_getway_enable='0' AND g.organization_id=?1",nativeQuery=true)
	List<AssetGateway> getalllistNonWorkingGatewayforOrganization(long fkid);

	@Query(value="SELECT * FROM asset_gateway_creation g WHERE g.asset_getway_enable='0' AND g.admin_id=?1",nativeQuery=true)
	List<AssetGateway> getalllistNonWorkingGatewayforAdmin(long fkid);

	
	@Query(value="SELECT * FROM asset_gateway_creation g WHERE g.asset_getway_enable='0' AND g.user_id=?1",nativeQuery=true)
	List<AssetGateway> getalllistNonWorkingGatewayforUser(long fkid);
	
	
	@Query(value="SELECT * FROM asset_gateway_creation a WHERE gateway_barcode_number=?1",nativeQuery=true)
	AssetGateway getGatewayDetailsByBarcodeNumber(long barcodeNumber);
	//List<AssetGateway> getGatewayDetailsByBarcodeNumber(long barcodeNumber);

	
	
	

	






	





	
//.................................................................................................................




}
