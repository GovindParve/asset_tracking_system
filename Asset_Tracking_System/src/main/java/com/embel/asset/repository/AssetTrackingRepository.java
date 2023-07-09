package com.embel.asset.repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.bean.ResponseTrackingBean;
import com.embel.asset.bean.ResponseTrackingListBean;
import com.embel.asset.entity.AssetTrackingEntity;

@Repository
public interface AssetTrackingRepository extends JpaRepository<AssetTrackingEntity, Long>{

	//-----superadmin-------  //,atd.battryPercentage
	//@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId ORDER BY atd.trackingId desc")
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.tag_tpye_or_category=?1",nativeQuery=true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getTrackingListSuperadminwise(String category, Pageable pageable);
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.tag_tpye_or_category=?1 AND atg.organization_id=?2",nativeQuery=true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getTrackingListOrganizationwise(String category, Long userId, Pageable pageable);
	//-----admin--------
	//@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atg.fkAdminId = ?1 ORDER BY atd.trackingId desc")
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.tag_tpye_or_category=?1 AND atg.admin_id=?2",nativeQuery=true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getTrackingListAdminwise(String category, Long userId, Pageable pageable);

	//-----user--------
	//@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atg.fkUserId = ?1 ORDER BY atd.trackingId desc")
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.tag_tpye_or_category=?1 AND atg.user_id=?2",nativeQuery=true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getTrackingListUserwise(String category, Long userId, Pageable pageable);


	//-superadmin-----
	@Query(value="SELECT DISTINCT atd.asset_tag_name FROM asset_tracking_details atd JOIN asset_tag_generation atg WHERE atd.barcode_or_serial_number = atg.asset_barcode_number_or_serial_number AND atd.tag_tpye_or_category=?1 ORDER BY atd.tracking_id asc", nativeQuery = true)
	List<String> getTagListSuperadminwise(String category);
	
	
	@Query(value="SELECT DISTINCT atd.asset_tag_name FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number = atg.asset_barcode_number_or_serial_number AND atd.tag_tpye_or_category=?1 AND atg.organization_id=?2  ORDER BY atd.tracking_id asc", nativeQuery = true)
	List<String> getTagListOrganizationwise(String category, Long userId);
	//------admin--------
	@Query(value="SELECT DISTINCT atd.asset_tag_name FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number = atg.asset_barcode_number_or_serial_number AND atd.tag_tpye_or_category=?1 AND atg.admin_id =?2  ORDER BY atd.tracking_id asc", nativeQuery = true)
	List<String> getTagListAdminwise(String category, Long userId);

	//------user---------
	@Query(value="SELECT DISTINCT atd.asset_tag_name FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number = atg.asset_barcode_number_or_serial_number AND atd.tag_tpye_or_category=?1 AND atg.user_id=?2 ORDER BY atd.tracking_id asc", nativeQuery = true)
	List<String> getTagListUserwise(String category, Long userId);

	//-----superadmin-------
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTrackingListForFilterSuperadminwise(String tagName);

	//-----admin--------
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 AND atg.fkAdminId = ?2 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTrackingListForFilterAdminwise(String tagName,Long userId);

	//-----user--------
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 AND atg.fkUserId = ?2 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTrackingListForFilterUserwise(String tagName,Long userId);

	//----------gateway name list for dropdown---------------
	@Query(value="SELECT DISTINCT atd.asset_gateway_name FROM asset_tracking_details atd JOIN asset_gateway_creation atg ON atd.asset_gateway_mac_id = atg.gateway_unique_code_or_mac_id ORDER BY atd.asset_gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownSuperAdmin();

	@Query(value = "SELECT DISTINCT atd.asset_gateway_name FROM asset_tracking_details atd JOIN asset_gateway_creation atg ON atd.asset_gateway_mac_id = atg.gateway_unique_code_or_mac_id WHERE atg.admin_id = ?1 ORDER BY atd.asset_gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownAdmin(Long fkID);

	@Query(value = "SELECT DISTINCT atd.asset_gateway_name FROM asset_tracking_details atd,asset_gateway_creation atg WHERE atd.asset_gateway_mac_id = atg.gateway_unique_code_or_mac_id AND atg.user_id =3  ORDER BY atd.asset_gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForDropdownUser(Long fkID);

	
	
	
	@Query("SELECT DISTINCT new com.embel.asset.bean.ResponseTrackingBean(atd.trackingId,atd.assetTagName,atd.tagEntryLocation,atd.date,atd.entryTime) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.assetTagName = ?1 ORDER BY atd.trackingId desc")
	List<ResponseTrackingBean> getSingleTrackingDataForFilterSuperadminwise(String tagName);

	@Query("SELECT DISTINCT new com.embel.asset.bean.ResponseTrackingBean(atd.trackingId,atd.assetTagName,atd.tagEntryLocation,atd.date,atd.entryTime) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.assetTagName = ?1 AND atg.fkAdminId = ?2 ORDER BY atd.trackingId desc")
	List<ResponseTrackingBean> getSingleTrackingDataForFilterAdminwise(String tagName, Long userId);

	@Query("SELECT DISTINCT new com.embel.asset.bean.ResponseTrackingBean(atd.trackingId,atd.assetTagName,atd.tagEntryLocation,atd.date,atd.entryTime) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.assetTagName = ?1 AND atg.fkUserId = ?2 ORDER BY atd.trackingId desc")
	List<ResponseTrackingBean> getSingleTrackingDataForFilterUserwise(String tagName, Long userId);

	@Query(value = "SELECT DISTINCT t.asset_gateway_name,t.asset_tag_name,t.date,DATEDIFF(CURRENT_DATE(), t.date) AS DateDiff FROM asset_tracking_details t JOIN asset_tag_generation g ON t.barcode_or_serial_number = g.asset_barcode_number_or_serial_number" , nativeQuery = true)
	List<Object[]> getAgedAndNewTagCountGatewayWise();

	
	//select * FROM asset_tracking_details where tracking_id = (select MAX(tracking_id) FROM asset_tracking_details WHERE unique_code_or_mac_id = ?1
	//select * FROM asset_tracking_details where entry_time IN (select MAX(entry_time) FROM asset_tracking_details WHERE unique_code_or_mac_id =?1
//	@Query(value ="select * FROM asset_tracking_details where tracking_id = (select MAX(tracking_id) FROM asset_tracking_details WHERE unique_code_or_mac_id = ?1)" ,nativeQuery = true)
	//@Query(value ="select * FROM asset_tracking_details where tracking_id = unique_code_or_mac_id = ?1 oreder by unique_code_or_mac_id desc limit 1" ,nativeQuery = true)
	
	@Query(value ="select * FROM asset_tracking_details where tracking_id = (select MAX(tracking_id) FROM asset_tracking_details WHERE unique_code_or_mac_id = ?1  )" ,nativeQuery = true)//AND exist_time='N/A'
	
	//@Query(value ="select * FROM asset_tracking_details where entry_time IN (select MAX(entry_time) FROM asset_tracking_details WHERE unique_code_or_mac_id =?1 )" ,nativeQuery = true)//AND exist_time='N/A'
	AssetTrackingEntity findByUniqueNumberMacId(String assetTagMac_Id);

	
	@Query(value ="select * FROM asset_tracking_details where tracking_id = (select MAX(tracking_id) FROM asset_tracking_details WHERE unique_code_or_mac_id =?1 AND entry_time=?2)" ,nativeQuery = true)//AND exist_time='N/A'
	//@Query(value ="select * FROM asset_tracking_details where entry_time IN (select MAX(entry_time) FROM asset_tracking_details WHERE unique_code_or_mac_id =?1 )" ,nativeQuery = true)//AND exist_time='N/A'

	AssetTrackingEntity findByUniqueNumberMacIdxxl(String gettMacId, String entryTime);
	
	
	
	@Query(value ="select * FROM asset_tracking_details where tracking_id = (select MAX(tracking_id) FROM asset_tracking_details WHERE asset_gateway_mac_id=?1 AND unique_code_or_mac_id = ?2)" ,nativeQuery = true)
	AssetTrackingEntity dbDataxl(String getgMacId, String gettMacId);
	
	
	
	@Query(value = "SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)>30 AND asset_gateway_name = ?1" , nativeQuery = true)
	Integer getAgedTagCountByGateway(String str);

	@Query(value = "SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)<=30 AND asset_gateway_name = ?1" , nativeQuery = true)
	Integer getNewTagCountByGateway(String str);
//...........date wise aged and new tag count 
	@Query(value ="SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)>30 AND asset_gateway_name = ?1 AND date BETWEEN ?2 AND ?3", nativeQuery = true)
	Integer getAgedTagCountByGatewayDatewise(String gateway, String fromdate, String todate);
	
	
	@Query(value ="SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)<=30 AND asset_gateway_name = ?1 AND date BETWEEN ?2 AND ?3" , nativeQuery = true)
	Integer getNewTagCountByGatewayDatewise(String gateway, String fromdate, String todate);
//....................................
	@Query(value = "SELECT * FROM asset_tracking_details WHERE asset_tag_name = ?1" ,nativeQuery = true)
	List<AssetTrackingEntity> getAllDataForGivenTag(String tagName);

	void deleteByUniqueNumberMacId(String assetTagMac_Id);

	//--------------Reports-all-------------------------------------
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 AND atg.assetTagCategory=?2 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForSuperAdminExportDownload(String tagName, String category);
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 AND atg.fkOrganizationId = ?2 AND atg.assetTagCategory=?3 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForOrganizationExportDownload(String tagName, Long fkID, String category);
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 AND atg.fkAdminId = ?2 AND atg.assetTagCategory=?3 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForAdminExportDownload(String tagName, Long fkID, String category);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 AND atg.fkUserId = ?2 AND atg.assetTagCategory=?3 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForUserExportDownload(String tagName, Long fkID, String category);

	//--------------------between dates--------------------------------------------------------------
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadBetweenDate(String start, String end,String tagName);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 AND atg.fkOrganizationId = ?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForOrganizationExportDownloadBetweenDate(String fromDate, String toDate,
			String tagName, Long fkID);
	
	
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 AND atg.fkAdminId = ?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForAdminExportDownloadBetweenDate(String fromDate, String toDate,String tagName, Long fkID);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 AND atg.fkUserId = ?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForUserExportDownloadBetweenDate(String fromDate, String toDate,String tagName, Long fkID);

	//-------gateway and tag report----------------
	//--------------------between dates--------------------------------------------------------------
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atd.assetTagName = ?2 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadGatewayWiseTag(String gatewayName,String tagName);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atd.assetTagName = ?2 AND atg.fkAdminId = ?3 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForAdminExportDownloadGatewayWiseTag(String gatewayName,String tagName, Long fkID);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atd.assetTagName = ?2 AND atg.fkUserId = ?3 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForUserExportDownloadGatewayWiseTag(String gatewayName,String tagName, Long fkID);

	//--------------------between dates--------------------------------------------------------------
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd ,AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadGatewayWise(String gatewayName);

	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atg.fkOrganizationId = ?2 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForOrganizationExportDownloadGatewayWise(String gatewayName, Long fkID);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atg.fkAdminId = ?2 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForAdminExportDownloadGatewayWise(String gatewayName, Long fkID);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atg.fkUserId = ?2 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForUserExportDownloadGatewayWise(String gatewayName, Long fkID);

	//@Query(value="SELECT * FROM asset_tracking_details pd JOIN user_details dd WHERE  dd.username=:asset_tag_name", nativeQuery = true)
	//List<ResponseTrackingListBean> getPayLoadReportForSuperAdmintoView(String tagName);
//------------------------------

	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atd.date BETWEEN ?2 AND ?3  ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadGatewayWisexl(String gatewayName,
			String fromDate, String toDate);
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atg.fkOrganizationId =?2 AND atd.date BETWEEN ?3 AND ?4  ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForOrganizationExportDownloadGatewayWisexl(String gatewayName, Long fkID,
			String fromDate, String toDate);
	
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atg.fkAdminId = ?2 AND atd.date BETWEEN ?3 AND ?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForAdminExportDownloadGatewayWisexl(String gatewayName, Long fkID,
			String fromDate, String toDate);
	
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atg.fkUserId = ?2 AND atd.date BETWEEN ?3 AND ?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForUserExportDownloadGatewayWisexl(String gatewayName, Long fkID,
			String fromDate, String toDate);
	///-----------------------------today
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atd.date =?2  ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadGatewayWisexxl(String gatewayName,
			String today);

	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atg.fkOrganizationId =?2 AND atd.date=?3  ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForOrganizationExportDownloadGatewayWisexxl(String gatewayName,
			Long fkID, String today);

	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atg.fkAdminId = ?2 AND atd.date=?3 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForAdminExportDownloadGatewayWisexxl(String gatewayName, Long fkID,
			String today);
	
	
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetGatewayName = ?1 AND atg.fkUserId = ?2 AND atd.date=?3 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForUserExportDownloadGatewayWisexxl(String gatewayName, Long fkID,
			String string);
	
	
	
	//--------------------between dates--------------------------------------------------------------
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName = ?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getGatewayReportForSuperAdminExportDownloadBetweenDate(Date fromDate,Date toDate,String gatewayName);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName = ?3 AND atg.fkAdminId = ?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getGatewayReportForAdminExportDownloadBetweenDate(Date fromDate, Date toDate, String gatewayName, Long id);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName = ?3 AND atg.fkUserId = ?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getGatewayReportForUserExportDownloadBetweenDate(Date fromDate, Date toDate,String gatewayName, Long id);

	//todays tag data
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadTodaysDate(Date date,String tagName);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 AND atg.fkAdminId = ?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForAdminExportDownloadTodaysDate(Date todayDate, String tagName,Long id);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 AND atg.fkUserId = ?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForUserExportDownloadTodaysDate(Date todayDate, String tagName,Long id);

	//todays tag gateway wise data
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetGatewayName = ?2  ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getGatewayReportForSuperAdminExportDownloadTodaysDate(Date todayDate,String gatewayName);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetGatewayName = ?2 AND atg.fkAdminId = ?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getGatewayReportForAdminExportDownloadTodaysDate(Date todayDate,String gatewayName, Long id);

	//.......................................................... getwayName wise ... excel..............................................................................
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName =?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadBetweenDategatewayName(Date fromDate,Date toDate, String gatewayName);


	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName =?3 AND atg.fkAdminId = ?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForAdminExportDownloadBetweenDategatewayName(Date fromDate,Date toDate, String gatewayName, Long fkID);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName =?3 AND atg.fkUserId = ?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getTagReportForUserExportDownloadBetweenDategatewayName(Date fromDate,Date toDate, String gatewayName, Long fkID);
	//.......................................................getwayName wise...pdf..............................................................................................................

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetGatewayName = ?2 AND atg.fkUserId =?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getGatewayReportForUserExportDownloadTodaysDate(Date todayDate,String gatewayName, Long id);

	//@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.date,atd.entryTime,atd.dispatchTime) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 AND atd.assetGatewayName = ?3 AND atg.fkUserId = ?4 ORDER BY atd.trackingId desc ")
	//List<ResponseTrackingListBean> getGatewayReportForUserExportDownloadTodaysDate(LocalDate todayDate, String tagName,String gatewayName, Long id);

	//................................................................................................................................................................

	@Query(value ="select * FROM asset_tracking_details where tracking_id = (select MAX(tracking_id) FROM asset_tracking_details WHERE barcode_or_serial_number = ?1)", nativeQuery = true)
	AssetTrackingEntity findByBarcodeSerialNumber(String barcodeSerialNumber);
	
	
	
	
	
	
//.............................................Pagination..................................................................
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND  atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName =?3 AND atg.assetTagCategory=?4 ORDER BY atd.trackingId desc ")
	Page<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadBetweenDateWithPagination(String fromDate,
			String toDate, String tagName, String category, Pageable pageable);
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName =?3 AND atg.fkOrganizationId =?4 AND atg.assetTagCategory=?5 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getTagReportForOrganizationExportDownloadBetweenDateWithPagination(String fromDate,
			String toDate, String tagName, Long id, String category, Pageable pageable);
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName =?3 AND atg.fkAdminId =?4 AND atg.assetTagCategory=?5 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getTagReportForAdminExportDownloadBetweenDateWithPagination(String fromDate,
			String toDate, String tagName, Long id,String category, Pageable pageable);
	
	
	
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName =?3 AND atg.fkUserId =?4 AND atg.assetTagCategory=?5 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getTagReportForUserExportDownloadBetweenDateWithPagination(String fromDate,
			String toDate, String tagName, Long id,String category, Pageable pageable);

	
	
	//current date
	
		@Query("SELECT new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName =?2 AND atg.assetTagCategory=?3 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadTodaysDateWithPagination(String today,
				String tagName, String category, Pageable pageable);
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName =?2 AND atg.fkOrganizationId =?3 AND atg.assetTagCategory=?4 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getTagReportForOrganizationExportDownloadTodaysDateWithPagination(Pageable pageable);
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName =?2 AND atg.fkAdminId =?3 AND atg.assetTagCategory=?4 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getTagReportForAdminExportDownloadTodaysDateWithPagination(String today,
				String tagName, Long id,String category, Pageable pageable);
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName =?2 AND atg.fkUserId =?3 AND atg.assetTagCategory=?4 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getTagReportForUserExportDownloadTodaysDateWithPagination(String today,
				String tagName, Long id,String category, Pageable pageable);
//......................................................................................................
		
		
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName =?1 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getTrackingListForFilterSuperadminwiseWithPagination(String tagName,
				Pageable pageable);


		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 AND atg.fkAdminId = ?2 ORDER BY atd.trackingId desc")
		Page<ResponseTrackingListBean> getTrackingListForFilterAdminwiseWithPaginatio(String tagName, Long userId,
				Pageable pageable);
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 AND atg.fkUserId = ?2 ORDER BY atd.trackingId desc")
		Page<ResponseTrackingListBean> getTrackingListForFilterUserwiseWithPaginatio(String tagName, Long userId,
				Pageable pageable);
//............................................................................................................................
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName = ?3 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getGatewayReportForSuperAdminExportDownloadBetweenDateWithPagination(
				String fromDate, String toDate, String gatewayName, String category, Pageable pageable);

		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName = ?3 AND atg.fkOrganizationId = ?4 AND atg.assetTagCategory=?5 ORDER BY atd.trackingId desc")
		Page<ResponseTrackingListBean> getGatewayReportForOrganizationExportDownloadBetweenDateWithPagination(
				String fromDate, String toDate, String gatewayName, Long id, String category, Pageable pageable);
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName = ?3 AND atg.fkAdminId = ?4 AND atg.assetTagCategory=?5 ORDER BY atd.trackingId desc")
		Page<ResponseTrackingListBean> getGatewayReportForAdminExportDownloadBetweenDateWithPagination(String fromDate,
				String toDate, String gatewayName, Long id,String category,Pageable pageable);

		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetGatewayName = ?3 AND atg.fkUserId = ?4 AND atg.assetTagCategory=?5 ORDER BY atd.trackingId desc")
		Page<ResponseTrackingListBean> getGatewayReportForUserExportDownloadBetweenDateWithPagination(String fromDate,
				String toDate, String gatewayName, Long id, String category, Pageable pageable);
//..............

		
		
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetGatewayName = ?2 AND atg.assetTagCategory=?3 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getGatewayReportForSuperAdminExportDownloadTodaysDateWithPagination(
				String date, String gatewayName, String category, Pageable pageable);
		
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetGatewayName = ?2 AND atg.fkOrganizationId = ?3 AND atg.assetTagCategory=?4 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getGatewayReportForOrganizationExportDownloadTodaysDateWithPagination(String date,
				String gatewayName, Long id, String category, Pageable pageable);

		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetGatewayName = ?2 AND atg.fkAdminId = ?3 AND atg.assetTagCategory=?4 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getGatewayReportForAdminExportDownloadTodaysDateWithPagination(String date,
				String gatewayName, Long id,String category,Pageable pageable);
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetGatewayName = ?2 AND atg.fkUserId = ?3 AND atg.assetTagCategory=?4 ORDER BY atd.trackingId desc ")
		Page<ResponseTrackingListBean> getGatewayReportForUserExportDownloadTodaysDateWithPagination(String date,
				String gatewayName, Long id,String category,Pageable pageable);
//..................................................................................................................................................
		
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.battryPercentage <?1 ORDER BY atd.trackingId desc ")
//		@Query(value="SELECT * FROM asset_tracking_details a WHERE a.battery_percentage <= ?1", nativeQuery = true)
		List<ResponseTrackingListBean> getBatteryPercentageListSuperAdmin(Integer lowBattryLimit);
		
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId  AND atg.fkAdminId =?1 AND atd.battryPercentage <?2 ORDER BY atd.trackingId desc ")
		List<ResponseTrackingListBean> getBatteryPercentageListAdmin(long fkid, Integer lowBattryLimit);

		
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atg.fkUserId =?1 AND atd.battryPercentage <?2 ORDER BY atd.trackingId desc ")
		List<ResponseTrackingListBean> getBatteryPercentageListUser(long fkUserId, Integer lowBattryLimit);
//...................................
		
		@Query(value="SELECT COUNT(a.battry_percentage) FROM asset_tracking_details a  WHERE a.battry_percentage < ?1", nativeQuery = true)
		long getBatteryPercentageSuperAdminAlertCount(Integer lowBattryLimit);

		@Query(value="SELECT COUNT(a.battry_percentage) FROM asset_tracking_details a  WHERE a.battry_percentage <?1 AND a.date between ?2 AND ?3", nativeQuery = true)
		long getBatteryPercentageSuperAdminAlertCountfornotification(Integer lowBattryLimit, LocalDateTime formdate, LocalDateTime currentdate);

		
		@Query(value="SELECT COUNT(a.battry_percentage) FROM asset_tracking_details a ,asset_tag_generation atg WHERE  a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atg.admin_id=?1 AND a.battry_percentage <?2", nativeQuery = true)
		long getBatteryPercentagetAdminAlertCount(String fkUserId, Integer lowBattryLimit);
		
		@Query(value="SELECT COUNT(a.battry_percentage) FROM asset_tracking_details a ,asset_tag_generation atg WHERE  a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atg.admin_id=?1 AND a.battry_percentage <?2", nativeQuery = true)
		long getBatteryPercentagetAdminAlertCountnotification( long userid,Integer lowBattryLimit);
		
		
		
		@Query(value="SELECT COUNT(a.battry_percentage) FROM asset_tracking_details a ,asset_tag_generation atg WHERE  a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atg.admin_id=?1 AND a.battry_percentage <?2 AND a.date between ?3 AND ?4", nativeQuery = true)
		long getBatteryPercentagetAdminAlertCountfornotification(long userid,Integer lowBattryLimit,LocalDateTime formdate, LocalDateTime currentdate);
		
		
		@Query(value="SELECT COUNT(a.battry_percentage) FROM asset_tracking_details a ,asset_tag_generation atg WHERE  a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atg.user_id=?1 AND a.battry_percentage <?2", nativeQuery = true)
		long getBatteryPercentageUserAlertCount(String fkUserId, Integer lowBattryLimit);
//...............................................................................................................................
		//graph
		/*
		Page<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadBetweenDateWithPagination(Date fromDate,
				Date toDate, String tagName, Pageable pageable);
		
		
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 AND atg.fkAdminId = ?4 ORDER BY atd.trackingId desc")
		Page<ResponseTrackingListBean> getTagReportForAdminExportDownloadBetweenDateWithPagination(Date fromDate,
				Date toDate, String tagName, Long id,Pageable pageable);
		
		
		
		
		@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 AND atg.fkUserId = ?4 ORDER BY atd.trackingId desc")
		Page<ResponseTrackingListBean> getTagReportForUserExportDownloadBetweenDateWithPagination(Date fromDate,
				Date toDate, String tagName, Long id,Pageable pageable);

		
		
		//current date
		
			@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 ORDER BY atd.trackingId desc ")
			Page<ResponseTrackingListBean> getTagReportForSuperAdminExportDownloadTodaysDateWithPagination(Date todayDate,
					String tagName, Pageable pageable);

			@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 AND atg.fkAdminId = ?3 ORDER BY atd.trackingId desc ")
			Page<ResponseTrackingListBean> getTagReportForAdminExportDownloadTodaysDateWithPagination(Date todayDate,
					String tagName, Long id,Pageable pageable);
			@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 AND atg.fkUserId = ?3 ORDER BY atd.trackingId desc ")
			Page<ResponseTrackingListBean> getTagReportForUserExportDownloadTodaysDateWithPagination(Date todayDate,
					String tagName, Long id,Pageable pageable);
	//......................................................................................................
	*/		
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getlistdurationtagNameWiseforSuperadmin(String fromDate,String toDate,String tagName,
				String fkUserId);
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 AND atg.fkOrganizationId =?4 ORDER BY atd.trackingId desc")

	List<ResponseTrackingListBean> getlistdurationtagNameWiseforOrganization(String startDate, String endDate,
			String tagName, String fkUserId);
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 AND atg.fkAdminId =?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getlistdurationtagNameWiseforAdmin(String fromDate, String toDate,String tagName,
			String fkUserId);
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date BETWEEN ?1 AND ?2 AND atd.assetTagName = ?3 AND atg.fkUserId =?4 ORDER BY atd.trackingId desc")
	List<ResponseTrackingListBean> getlistdurationtagNameWiseforUser(String fromDate, String toDate,String tagName,
			String fkUserId);
//current date
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date=?1 AND atd.assetTagName =?2 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getlistCurrentdateNameWiseforSuperadmin(String date,String tagName);//,String fkUserId
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 AND atg.fkOrganizationId = ?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getlistCurrentdatetagNameWiseforOrganization(String date, String tagName,
			String fkUserId);
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 AND atg.fkAdminId = ?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getlistCurrentdatetagNameWiseforAdmin(String date,String tagName,String fkUserId);

	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date =?1 AND atd.assetTagName = ?2 AND atg.fkUserId = ?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getlistCurrentdatetagNameWiseforUser(String date,String tagName,String fkUserId);
//..................tag wise batterypercentage
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId  AND atd.assetTagName = ?1 AND atd.battryPercentage <?2 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getBatteryPercentagefilterTagWiseSuperAdmin(String assetTagName, Integer lowBattryLimit);
	
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId  AND atd.assetTagName = ?1 AND atg.fkAdminId = ?2 AND atd.battryPercentage<?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getBatteryPercentagefilterTagWiseAdmin(String assetTagName, String fkUserId,Integer lowBattryLimit);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId  AND atd.assetTagName = ?1 AND atg.fkUserId = ?2 AND atd.battryPercentage<?3 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getBatteryPercentagefilterTagWiseUser(String assetTagName, String fkUserId,Integer lowBattryLimit);
	
	
	

	@Query(value = "SELECT * FROM asset_tracking_details d WHERE d.date between ?1 AND ?2",nativeQuery=true)
	List<AssetTrackingEntity> getHourBackupAssetTrackingDetails(LocalDateTime formdate, LocalDateTime currentdate);
//....................................................................................................................................
	//for duration wise graph
//	@Query(value = "SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection t JOIN asset_gateway_creation g ON t.asset_gateway_mac_id = g.gateway_unique_code_or_mac_id WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)<=30 AND asset_gateway_name = ?1 AND g.admin_id = ?2" , nativeQuery = true)
//	Integer getAgedTagCountByGateway(String gatewayName, Long id);
//
//	@Query(value = "SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection t JOIN asset_gateway_creation g ON t.asset_gateway_mac_id = g.gateway_unique_code_or_mac_id WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)<=30 AND asset_gateway_name = ?1 AND g.user_id = ?2" , nativeQuery = true)
//	Integer getNewTagCountByGateway(String gatewayName, Long id);

//.....

//
//
//	
//	@Query(value="",nativeQuery = true)
//	Page<AssetTrackingEntity> gettagenameWiseViewDataBetweenDateForSuperAdmin(Date fromdate, Date todate, String assetTagname,
//			Pageable pageable);



//	@Query(value = "SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)>30 AND asset_gateway_name = ?1" , nativeQuery = true)
//	Integer getAgedTagCountByGateway(String str);
//
//	@Query(value = "SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)<=30 AND asset_gateway_name = ?1" , nativeQuery = true)
//	Integer getNewTagCountByGateway(String str);
//	
	
//	@Query(value = "SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)>30 AND asset_gateway_name = ?1" , nativeQuery = true)
//	Integer getAgedTagCountByGatewaywithduration(Date fromDate, String endDate, String gatewayName);
		
//..............................................................................................................................

	
	@Query(value = "SELECT * FROM asset_tracking_details a WHERE a.date between ?1 AND ?2 AND a.asset_tag_name=?3 AND a.tag_tpye_or_category=?4", nativeQuery = true)
	Page<AssetTrackingEntity> gettagenameWiseViewDataBetweenDateForSuperAdmin(String fromdate, String todate,
			String tagName, String category, Pageable pageable);
	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2 AND a.asset_tag_name=?3 AND atg.organization_id=?4 AND a.tag_tpye_or_category=?5", nativeQuery = true)
	Page<AssetTrackingEntity> gettagenameWiseViewDataBetweenDateForOrganization(String fromdate, String todate,
			String tagName, long fkuserid, String category, Pageable pageable);

	
	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2 AND a.asset_tag_name=?3 AND atg.admin_id=?4 AND a.tag_tpye_or_category=?5 ", nativeQuery = true)
	Page<AssetTrackingEntity> gettagenameWiseViewDataBetweenDateForAdmin(String fromdate, String todate,
			String tagName, long fkuserid,String category, Pageable pageable);
	
	
	@Query(value =" SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2 AND a.asset_tag_name=?3 AND atg.user_id=?4 AND a.tag_tpye_or_category=?5", nativeQuery = true)
	Page<AssetTrackingEntity> gettagenameWiseViewDataBetweenDateForUser(String fromdate, String todate, String tagName,
			long fkuserid, String category, Pageable pageable);

	//gateway wise
	@Query(value ="SELECT * FROM asset_tracking_details a WHERE a.date between ?1 AND ?2 AND a.asset_gateway_name=?3 AND a.tag_tpye_or_category=?4 ORDER BY a.date ASC", nativeQuery = true)
	Page<AssetTrackingEntity> getgatewaynameWiseViewDataBetweenDateForSuperAdmin(String fromdate, String todate,
			String gatewayName, String category, Pageable pageable);
	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2 AND a.asset_gateway_name=?3 AND atg.organization_id=?4 AND a.tag_tpye_or_category=?5 ORDER BY a.date ASC", nativeQuery = true)
	Page<AssetTrackingEntity> getgatewaynameWiseViewDataBetweenDateForOrganization(String fromdate, String todate,
			String gatewayName, long fkuserid, String category, Pageable pageable);
	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2 AND a.asset_gateway_name=?3 AND atg.admin_id=?4 AND a.tag_tpye_or_category=?5 ORDER BY a.date ASC", nativeQuery = true)
	Page<AssetTrackingEntity> getgatewaynameWiseViewDataBetweenDateForAdmin(String fromdate, String todate,
			String gatewayName, long fkuserid, String category, Pageable pageable);

	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2 AND a.asset_gateway_name=?3 AND atg.user_id=?4 AND a.tag_tpye_or_category=?5 ORDER BY a.date ASC", nativeQuery = true)
	Page<AssetTrackingEntity> getgatewaynameWiseViewDataBetweenDateForUser(String fromdate, String todate,
			String gatewayName, long fkuserid, String category, Pageable pageable);

	
//.........................time wiae tracking details
	
	
	
	
	
	@Query("SELECT new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd,AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName = ?1 AND atd.timestamp BETWEEN ?2 AND ?3 AND atd.date=?4 AND atg.assetTagCategory=?5 ORDER BY atd.trackingId desc ")
	Page<ResponseTrackingListBean> getalldetailsTrackingForSuperadmin(String tagName, String fromtime, String totime,String datenew,String category, Pageable pageable);
	
	@Query("SELECT new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd,AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName =?1 AND atd.timestamp BETWEEN ?2 AND ?3 AND atd.date=?4 AND atg.fkOrganizationId =?5 AND atg.assetTagCategory=?6 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getalldetailsTrackingForOrganization(String tagName, String string, String string2,String datenew,
			long fkuserid, String category, Pageable pageable);
	@Query("SELECT new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd,AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName =?1 AND atd.timestamp BETWEEN ?2 AND ?3 AND atd.date=?4 AND atg.fkAdminId =?5 AND atg.assetTagCategory=?6 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getalldetailsTrackingForAdmin(String tagName, String string, String string2,String datenew,
			long fkuserid, String category, Pageable pageable);
	
	@Query("SELECT new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd,AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.assetTagName =?1 AND atd.timestamp BETWEEN ?2 AND ?3 AND atd.date=?4 AND atg.fkUserId =?5 AND atg.assetTagCategory=?6 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getalldetailsTrackingForUser(String tagName, String string, String string2,String datenew,
			long fkuserid, String category, Pageable pageable);


	//...
	@Query(value ="SELECT * FROM asset_tracking_details a WHERE a.entry_time between ?1 AND ?2 AND a.asset_tag_name=?3 GROUP BY a.asset_gateway_name", nativeQuery = true)
	Page<AssetTrackingEntity> getTagwiseGatewayViewforSuperAdmin(String fromdate, String todate, String tagName, Pageable pageable);

	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.entry_time between ?1 AND ?2 AND a.asset_tag_name=?3 AND atg.admin_id=?4 GROUP BY a.asset_gateway_name", nativeQuery = true)
	Page<AssetTrackingEntity> getTagwiseGatewayViewforAdmin(String fromdate, String todate, String tagName,
			long fkuserid, Pageable pageable);
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.entry_time between ?1 AND ?2 AND a.asset_tag_name=?3 AND atg.user_id=?4 GROUP BY a.asset_gateway_name", nativeQuery = true)
	Page<AssetTrackingEntity> getTagwiseGatewayViewforUser(String fromdate, String todate, String tagName,
			long fkuserid, Pageable pageable);
	//...

	@Query(value ="SELECT * FROM asset_tracking_details a WHERE a.date between ?1 AND ?2 AND a.`timestamp` between ?3 AND ?4 GROUP BY a.`timestamp`", nativeQuery = true)//AND a.asset_tag_name=?3
	Page<AssetTrackingEntity> getTagwiseGatewayViewforSuperAdminReport(String fromdate, String todate, String fromtime, String totime, Pageable pageable);

	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2  AND a.`timestamp` between ?3 AND ?4 AND atg.organization_id=?5 GROUP BY a.`timestamp`", nativeQuery = true)
	Page<AssetTrackingEntity> getTagwiseGatewayViewforOrganizationReport(String fromdate, String todate,
			String fromtime, String totime, long fkuserid, Pageable pageable);
	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2  AND a.`timestamp` between ?3 AND ?4 AND atg.organization_id=?5 GROUP BY a.`timestamp`", nativeQuery = true)
	List<AssetTrackingEntity> getTagwiseGatewayViewforOrganizationReports(String fromdate, String todate,
			String fromtime, String totime, long fkuserid);
	
	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2  AND a.`timestamp` between ?3 AND ?4 AND atg.admin_id=?5 GROUP BY a.`timestamp`", nativeQuery = true)
	Page<AssetTrackingEntity> getTagwiseGatewayViewforAdminReport(String fromdate, String todate, String fromtime, String totime, long fkuserid, Pageable pageable);

	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.entry_time between ?1 AND ?2 AND a.`timestamp` between ?3 AND ?4 AND atg.user_id=?5 GROUP BY a.`timestamp`", nativeQuery = true)
	Page<AssetTrackingEntity> getTagwiseGatewayViewforUserReport(String fromdate, String todate, String fromtime, String totime, long fkuserid, Pageable pageable);

	
	@Query(value ="SELECT * FROM asset_tracking_details a WHERE a.asset_tag_name=?1 AND a.tag_tpye_or_category='GPS' ORDER BY a.tracking_id DESC LIMIT 1", nativeQuery = true)
	AssetTrackingEntity getbytagName(String tagName);

	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.imei_number=atg.asset_imei_number  AND a.asset_tag_name=?1 AND a.tag_tpye_or_category='GPS'ORDER BY a.tracking_id DESC LIMIT 1", nativeQuery = true)
	AssetTrackingEntity getbytagNameSuperAdmin(String tagName);
	
	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.imei_number=atg.asset_imei_number AND atg.admin_id=?1 AND a.asset_tag_name=?2 AND a.tag_tpye_or_category='GPS' ORDER BY a.tracking_id DESC LIMIT 1", nativeQuery = true)
	AssetTrackingEntity getbytagNameAdmin(long fkid, String tagName);

	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.imei_number=atg.asset_imei_number AND atg.user_id=?1 AND a.asset_tag_name=?2 AND a.tag_tpye_or_category='GPS' ORDER BY a.tracking_id DESC LIMIT 1", nativeQuery = true)
	AssetTrackingEntity getbytagNameUser(long fkid, String tagName);

	

	
	@Query(value="SELECT atd.asset_tag_name FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atd.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.admin_id=?1 AND atd.date=?2 AND atg.user_id=?3",nativeQuery=true)
	String findByCurrentDate(long adminid, LocalDateTime currentdate, long userid);

	
	
	@Query(value="SELECT COUNT(DISTINCT a.asset_tag_name) FROM asset_tracking_details a WHERE a.date between ?1 AND ?2 AND a.asset_gateway_name=?3",nativeQuery=true)
	long getTagCountGatewayWiseforSuperAdmin(String startdate, String enddate,String gatewayName);

	
	
	@Query(value="SELECT COUNT(DISTINCT a.asset_tag_name) FROM asset_tracking_details a,asset_tag_generation atg WHERE a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND a.date between ?1 AND ?2 AND a.asset_gateway_name=?3 AND atg.organization_id=?4",nativeQuery=true)
	long getTagCountGatewayWiseforOrganization(String startdate, String enddate, String gatewayName, long fkid);
	
	
	@Query(value="SELECT COUNT(DISTINCT a.asset_tag_name) FROM asset_tracking_details a,asset_tag_generation atg WHERE a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND a.date between ?1 AND ?2 AND a.asset_gateway_name=?3 AND atg.admin_id=?4",nativeQuery=true)
	long getTagCountGatewayWiseforAdmin(String startdate, String enddate, String gatewayName, long fkid);

	
	
	@Query(value="SELECT COUNT(DISTINCT a.asset_tag_name) FROM asset_tracking_details a,asset_tag_generation atg WHERE a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND a.date between ?1 AND ?2 AND a.asset_gateway_name=?3 AND atg.user_id=?4",nativeQuery=true)
	long getTagCountGatewayWiseforUser(String startdate, String enddate, String gatewayName, long fkid);



	
	@Query(value="SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'asset_tracking_details' AND COLUMN_NAME LIKE 'lb%'",nativeQuery=true)
	List getAssetReservedcolumnlist();

	
	@Query(value="SELECT * FROM asset_tracking_details a",nativeQuery=true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettrackinglist(Pageable pageable);

//	
//	@Query(value="",nativeQuery=true)
//	List<AssetTrackingEntity> gettrackinglistcolumnwise(String columnParameter, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number = atg.asset_barcode_number_or_serial_number AND atg.organization_id =?1 ",nativeQuery=true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettrackinglistForOrganization(Long fkid,Pageable pageable);
	
	@Query(value="SELECT * FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number = atg.asset_barcode_number_or_serial_number AND atg.admin_id =?1 ",nativeQuery=true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettrackinglistForAdmin(Long fkid,Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details",nativeQuery=true)
	List<AssetTrackingEntity> getTrackingReportforSuperAdmin();

	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.admin_id=?1", nativeQuery = true)
	List<AssetTrackingEntity> getTrackingReportforAdmin(Long fkID);
	
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.user_id=?1",nativeQuery=true)
	List<AssetTrackingEntity> getTrackingReportforUser(Long fkID);
	//
	@Query(value="SELECT * FROM asset_tracking_details",nativeQuery=true)
	List<AssetTrackingEntity> getColumnWiseReportForSuperAdminExportDownload();

	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.admin_id=?1", nativeQuery = true)
	List<AssetTrackingEntity> getColumnWiseReportForAdminExportDownload(Long fkID);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.user_id=?1",nativeQuery=true)
	List<AssetTrackingEntity> getColumnWiseReportForUserExportDownload(Long fkID);

	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.asset_tag_name=?1",nativeQuery=true)
	List<AssetTrackingEntity> getSingleTrackingDataForFilterSuperadminwiseAlldata(String tagName);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.asset_tag_name=?1 AND atg.organization_id=?2",nativeQuery=true)
	List<AssetTrackingEntity> getSingleTrackingDataForFilterOrganizationwiseAlldata(String tagName, Long userId);
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.asset_tag_name=?1 AND atg.admin_id=?2",nativeQuery=true)
	List<AssetTrackingEntity> getSingleTrackingDataForFilterAdminwiseAlldata(String tagName, Long userId);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.asset_tag_name=?1 AND atg.user_id=?2",nativeQuery=true)

	List<AssetTrackingEntity> getSingleTrackingDataForFilterUserwiseAlldata(String tagName, Long userId);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId  AND atg.fkOrganizationId =?1 AND atd.battryPercentage <?2 ORDER BY atd.trackingId desc ")
	List<ResponseTrackingListBean> getBatteryPercentageListOrganization(long fkid, Integer lowBattryLimit);

	
	
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND t.asset_tag_name=?1 AND t.asset_tag_category=?2",nativeQuery=true)
	List<AssetTrackingEntity> getTagReportForSuperAdminExportDownloadForGPS(String tagName, String category);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND t.asset_tag_name=?1 AND t.organization_id=?2 AND t.asset_tag_category=?3",nativeQuery=true)
	List<AssetTrackingEntity> getTagReportForOrganizationExportDownloadForGPS(String tagName, Long fkID,
			String category);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND t.asset_tag_name=?1 AND t.admin_id=?2 AND t.asset_tag_category=?3",nativeQuery=true)
	List<AssetTrackingEntity> getTagReportForAdminExportDownloadForGPS(String tagName,Long fkID,String category);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND t.asset_tag_name=?1 AND t.user_id=?2 AND t.asset_tag_category=?3",nativeQuery=true)
	List<AssetTrackingEntity> getTagReportForUserExportDownloadForGPS(String tagName, Long fkID, String category);

	
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND a.date between ?1 AND ?2 AND t.asset_tag_name=?3  AND t.asset_tag_category=?4",nativeQuery=true)
	List<AssetTrackingEntity> getTagReportForSuperAdminExportDownloadBetweenDateForGps(Date fromDate, Date toDate,
			String tagName, String category);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND a.date between ?1 AND ?2 AND t.asset_tag_name=?3 AND t.organization_id=?4  AND t.asset_tag_category=?5",nativeQuery=true)
	List<AssetTrackingEntity> getTagReportForOrganizationExportDownloadBetweenDateForGps(Date fromDate,
			Date toDate, String tagName, Long fkID, String category);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND a.date between ?1 AND ?2 AND t.asset_tag_name=?3 AND t.admin_id=?4  AND t.asset_tag_category=?5",nativeQuery=true)
	List<AssetTrackingEntity> getTagReportForAdminExportDownloadBetweenDateForGPS(Date fromDate, Date toDate,
			String tagName, Long fkID, String category);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND a.date between ?1 AND ?2 AND t.asset_tag_name=?3 AND t.user_id=?4 AND t.asset_tag_category=?5",nativeQuery=true)
	List<AssetTrackingEntity> getTagReportForUserExportDownloadBetweenDateForGPS(Date fromDate, Date toDate,
			String tagName, Long fkID, String category);

	
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND t.asset_tag_category=?1",nativeQuery=true)
	List<AssetTrackingEntity> getColumnWiseReportForSuperAdminExportDownloadForGPS(String category);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND t.admin_id=?1 AND t.asset_tag_category=?2",nativeQuery=true)
	List<AssetTrackingEntity> getColumnWiseReportForAdminExportDownloadForGPS(Long fkID, String category);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND t.user_id=?1 AND t.asset_tag_category=?2",nativeQuery=true)
	List<AssetTrackingEntity> getColumnWiseReportForUserExportDownloadForGPS(Long fkID, String category);

	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.tag_tpye_or_category=?1",nativeQuery=true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettrackinglisForGPSt(String category, Pageable pageable);

	
	
	@Query(value="SELECT * FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.imei_number=atg.asset_imei_number AND atg.organization_id =?1 AND atd.tag_tpye_or_category=?2",nativeQuery=true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettrackinglistForOrganizationForGPS(Long fkid, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details atd,asset_tag_generation atg WHERE atd.imei_number=atg.asset_imei_number AND atg.admin_id=?1 AND atd.tag_tpye_or_category=?2",nativeQuery=true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettrackinglistForAdminForGps(Long fkid, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_barcode_number_or_serial_number AND a.date between ?1 AND ?2 AND a.asset_tag_name=?3 AND t.asset_tag_category=?4 GROUP BY a.tracking_id",nativeQuery=true)
	Page<AssetTrackingEntity> getTagReportForSuperAdminExportDownloadBetweenDateWithPaginationxl(String fromDate,
			String toDate, String tagName, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.barcode_or_serial_number=t.asset_barcode_number_or_serial_number AND a.date between ?1 AND ?2 AND a.asset_tag_name=?3 AND t.organization_id=?4 AND t.asset_tag_category=?5 GROUP BY a.tracking_id",nativeQuery=true)
	Page<AssetTrackingEntity> getTagReportForOrganizationExportDownloadBetweenDateWithPaginationxl(String fromDate,
			String toDate, String tagName, Long id, String category, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.barcode_or_serial_number=t.asset_barcode_number_or_serial_number AND a.date between ?1 AND ?2 AND a.asset_tag_name=?3 AND t.admin_id=?4 AND t.asset_tag_category=?5 GROUP BY a.tracking_id",nativeQuery=true)
	Page<AssetTrackingEntity> getTagReportForAdminExportDownloadBetweenDateWithPaginationxl(String fromDate,
			String toDate, String tagName, Long id, String category, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.barcode_or_serial_number=t.asset_barcode_number_or_serial_number AND a.date between ?1 AND ?2 AND a.asset_tag_name=?3 AND t.user_id=?4 AND t.asset_tag_category=?5 GROUP BY a.tracking_id",nativeQuery=true)
	Page<AssetTrackingEntity> getTagReportForUserExportDownloadBetweenDateWithPaginationxl(String fromDate,
			String toDate, String tagName, Long id, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.barcode_or_serial_number=t.asset_barcode_number_or_serial_number AND a.date=?1 AND a.asset_tag_name=?2 AND t.asset_tag_category=?3 GROUP BY a.tracking_id",nativeQuery=true)
	Page<AssetTrackingEntity> getTagReportForSuperAdminExportDownloadTodaysDateWithPaginationxl(Date today,
			String tagName, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.barcode_or_serial_number=t.asset_barcode_number_or_serial_number AND a.date=?1 AND a.asset_tag_name=?2 AND t.organization_id=?3  AND t.asset_tag_category=?4 GROUP BY a.tracking_id",nativeQuery=true)
	Page<AssetTrackingEntity> getTagReportForOrganizationExportDownloadTodaysDateWithPaginationxl(Date today,
			String tagName, Long id, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.barcode_or_serial_number=t.asset_barcode_number_or_serial_number AND a.date=?1 AND a.asset_tag_name=?2 AND t.admin_id=?3  AND t.asset_tag_category=?4 GROUP BY a.tracking_id",nativeQuery=true)
	Page<AssetTrackingEntity> getTagReportForAdminExportDownloadTodaysDateWithPaginationxl(Date today, String tagName,
			Long id, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.barcode_or_serial_number=t.asset_barcode_number_or_serial_number AND a.date=?1 AND a.asset_tag_name=?2 AND t.user_id=?3  AND t.asset_tag_category=?4 GROUP BY a.tracking_id",nativeQuery=true)
	Page<AssetTrackingEntity> getTagReportForUserExportDownloadTodaysDateWithPaginationxl(Date today, String tagName,
			Long id, String category, Pageable pageable);

	
	
	
	

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE   atg.assetTagCategory=?1 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getTagReportForSuperAdmin(String category,Pageable pageable);

	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atd.date=?1 AND atg.assetTagName=?2 AND atg.fkOrganizationId =?3 AND atg.assetTagCategory=?4 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getTagReportForOrganizationExportDownloadTodaysDateWithPagination(
		String today, String tagName, Long id, String category, Pageable pageable);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId  AND atg.fkOrganizationId=?1 AND atg.assetTagCategory=?2 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getTagReportForOrganizationExportDownloadTodaysDateWithPaginationdefault(Long id,
			String category, Pageable pageable);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId  AND atg.fkAdminId =?1 AND atg.assetTagCategory=?2 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getTagReportForAdminExportDownloadTodaysDateWithPaginationdefault(
			Long id, String category, Pageable pageable);

	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE  atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atg.fkUserId=?1 AND atg.assetTagCategory=?2 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getTagReportForUserExportDownloadTodaysDateWithPaginationdefault(
			Long id, String category, Pageable pageable);

	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId AND atg.assetTagCategory=?1   ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getGatewayReportForSuperAdminExportDownloadBetweenDateWithPaginationdefault(
			 String category, Pageable pageable);

	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId   AND atg.fkOrganizationId=?1 AND atg.assetTagCategory=?2 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getGatewayReportForOrganizationExportDownloadBetweenDateWithPaginationdefault(
			 Long id, String category, Pageable pageable);

	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId   AND atg.fkUserId=?1 AND atg.assetTagCategory=?2 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getGatewayReportForAdminExportDownloadBetweenDateWithPaginationdefault(
		 Long id, String category, Pageable pageable);

//	
//	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId   AND atg.fkAdminId=?1 AND atg.assetTagCategory=?2 ORDER BY atd.trackingId desc")
//	Page<ResponseTrackingListBean> getGatewayReportForAdminExportDownloadBetweenDateWithPaginationxl(long adminid,
//			String category, Pageable pageable);
	
	@Query("SELECT  new com.embel.asset.bean.ResponseTrackingListBean(atd.trackingId,atd.assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime,atd.battryPercentage,atd.time) FROM AssetTrackingEntity atd , AssetTag atg WHERE atd.barcodeSerialNumber = atg.assetBarcodeSerialNumber AND atd.uniqueNumberMacId = atg.assetUniqueCodeMacId   AND atg.fkUserId=?1 AND atg.assetTagCategory=?2 ORDER BY atd.trackingId desc")
	Page<ResponseTrackingListBean> getGatewayReportForAdminExportDownloadBetweenDateWithPagination(
			String fromDate, String toDate, String gatewayName, long adminid, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.tag_tpye_or_category=?1",nativeQuery = true)
	List<AssetTrackingEntity> getColumnWiseReportForSuperAdminExportDownloadxl(String category);
	
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.barcode_or_serial_number=t.asset_barcode_number_or_serial_number AND t.admin_id=?1  AND t.asset_tag_category=?2 GROUP BY a.tracking_id\r\n"
			+ "",nativeQuery=true)
List<AssetTrackingEntity> getColumnWiseReportForAdminExportDownloadpdfxl(Long fkID, String category);

	

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.barcode_or_serial_number=t.asset_barcode_number_or_serial_number AND t.user_id=?1  AND t.asset_tag_category=?2 GROUP BY a.tracking_id",nativeQuery=true)
	List<AssetTrackingEntity> getColumnWiseReportForUserExportDownloadpdf(Long fkID, String category);

	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.asset_tag_name=?1 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getAssetTagNamewiseList(String assetTagName, Pageable pageable);
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.asset_gateway_name=?1",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getAssetGatewayNamewiseList(String assetGatewayName, Pageable pageable);
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.date=?1 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getdatewiseList(String date, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_time LIKE ?1 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getentryTimewiseList(String entryTime, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_location=?1",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettagEntryLocationwiseList(String tagEntryLocation, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.exist_time LIKE ?1 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getexistTimewiseList(String existTime, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.dispatch_time=?1",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getdispatchTimewiseList(String dispatchTime, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.time LIKE ?1",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettimewiseList(String time, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.battry_percentage=?1",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getbattryPercentagewiseList(String battryPercentage, Pageable pageable);

	
	
	@Query(value ="SELECT * FROM asset_tracking_details a WHERE a.date between ?1 AND ?2 AND a.timestamp between ?3 AND ?4 GROUP BY a.timestamp", nativeQuery = true)//AND a.asset_tag_name=?3
	List<AssetTrackingEntity> getTagwiseGatewayViewforSuperAdminReportxl(String fromdate, String todate,
			String fromtime, String totime);

	
	
	
	
	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.date between ?1 AND ?2  AND a.timestamp between ?3 AND ?4 AND atg.admin_id=?5 GROUP BY a.timestamp", nativeQuery = true)

	List<AssetTrackingEntity> getTagwiseGatewayViewforAdminReportxl(String fromdate, String todate, String fromtime,
			String totime, long fkuserid);

	
	
	
	
	
	
	
	
	
	
	@Query(value ="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id  AND a.entry_time between ?1 AND ?2 AND a.timestamp between ?3 AND ?4 AND atg.user_id=?5 GROUP BY a.timestamp", nativeQuery = true)
	List<AssetTrackingEntity> getTagwiseGatewayViewforUserReportxl(String fromdate, String todate, String fromtime,
			String totime, long fkuserid);

	
	
	
	
	// Admin
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.asset_tag_name=?1 AND t.admin_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getAssetTagNamewiseListForAdmin(String assetTagName, long fkuserid, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.asset_gateway_name=?1 AND t.admin_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getAssetGatewayNamewiseListForAdmin(String assetGatewayName, long fkuserid,
			Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.date=?1 AND t.admin_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getdatewiseListForAdmin(String date, long fkuserid, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.entry_time LIKE ?1 AND t.admin_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getentryTimewiseListForAdmin(String entryTime, long fkuserid, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.entry_location=?1 AND t.admin_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettagEntryLocationwiseListForAdmin(String tagEntryLocation, long fkuserid,
			Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.exist_time LIKE ?1 AND t.admin_id=?2",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getexistTimewiseListForAdmin(String existTime, long fkuserid, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.dispatch_time=?1 AND t.admin_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getdispatchTimewiseListForAdmin(String dispatchTime, long fkuserid, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.time=?1 AND t.admin_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettimewiseListForAdmin(String time, long fkuserid, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.battry_percentage=?1 AND t.admin_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getbattryPercentagewiseListForAdmin(String battryPercentage, long fkuserid,
			Pageable pageable);

	
	
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.asset_tag_name=?1 AND t.organization_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getAssetTagNamewiseListForOrganization(String assetTagName, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.asset_gateway_name=?1 AND t.organization_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getAssetGatewayNamewiseListForOrganization(String assetGatewayName, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.date=?1 AND t.organization_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getdatewiseListForOrganization(String date, String fkUserId, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.entry_time LIKE ?1 AND t.organization_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getentryTimewiseListForOrganization(String entryTime, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.entry_location=?1 AND t.organization_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettagEntryLocationwiseListForOrganization(String tagEntryLocation, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.exist_time LIKE ?1 AND t.organization_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getexistTimewiseListForOrganization(String existTime,Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.dispatch_time=?1 AND t.organization_id=?2",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getdispatchTimewiseListForOrganization(String dispatchTime, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.time LIKE ?1 AND t.organization_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettimewiseListForOrganization(String time, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.battry_percentage=?1 AND t.organization_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getbattryPercentagewiseListForOrganization(String battryPercentage, Pageable pageable);

	
	
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.asset_tag_name=?1 AND t.user_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getAssetTagNamewiseListForUserForUser(String assetTagName, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.asset_gateway_name=?1 AND t.user_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getAssetGatewayNamewiseListForUser(String assetGatewayName, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.date=?1 AND t.user_id=?2 ",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getdatewiseListForUser(String date, Pageable pageable);
	
	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.entry_time LIKE ?1 AND t.user_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getentryTimewiseListForUser(String entryTime, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.entry_location=?1 AND t.user_id=?2",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettagEntryLocationwiseListForUser(String tagEntryLocation, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.exist_time LIKE ?1 AND t.user_id=?2",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getexistTimewiseListForUser(String existTime, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.dispatch_time=?1 AND t.user_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getdispatchTimewiseListForUser(String dispatchTime, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.time LIKE ?1 AND t.user_id=?2",nativeQuery = true)// ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> gettimewiseListForUser(String time, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.unique_code_or_mac_id=t.asset_unique_code_or_mac_id AND a.battry_percentage=?1 AND t.user_id=?2",nativeQuery = true)//ORDER BY a.entry_time DESC
	Page<AssetTrackingEntity> getbattryPercentagewiseListForUser(String battryPercentage, Pageable pageable);

	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation t WHERE a.imei_number=t.asset_imei_number AND t.organization_id=?1 AND t.asset_tag_category=?2",nativeQuery = true)//ORDER BY a.entry_time DESC
	List<AssetTrackingEntity> getColumnWiseReportForOrganizationExportDownloadForGPS(Long fkID, String category);

	
	@Query(value="SELECT * FROM asset_tracking_details a,asset_tag_generation atg WHERE a.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND a.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.organization_id=?1",nativeQuery = true)// ORDER BY a.entry_time DESC
	List<AssetTrackingEntity> getColumnWiseReportForOrganizationExportDownload(Long fkID);

	
	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.asset_gateway_mac_id=?1 AND a.unique_code_or_mac_id=?2 AND a.entry_time=?3 AND a.tagdist=?4",nativeQuery = true)
	List<AssetTrackingEntity> getDublicateEntry(String gMacId, String tMacId, String entryTime, float f);

	
	
	@Query(value="SELECT a.entry_time  FROM asset_tracking_details a WHERE a.entry_time < ?1 AND a.unique_code_or_mac_id=?2 ORDER BY a.entry_time DESC LIMIT 1",nativeQuery = true)
	String getbefoureRecordTocheck(String entryTime, String gettMacId);
	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_time=?1 AND a.unique_code_or_mac_id=?2 ORDER BY a.entry_time DESC",nativeQuery = true)
	List<AssetTrackingEntity> getbefoureRecordTocheckisUpdatedorNot(String entryTime, String gettMacId);
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.exist_time=?1 AND a.unique_code_or_mac_id=?2",nativeQuery = true)
	AssetTrackingEntity gettrackingDetailsOnExitTime(String entryTime, String tagmacaddress);

	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tracking_details a SET a.exist_time=?1 WHERE a.tracking_id=?2",nativeQuery = true)
	void updateExitTime(String entryTime, long tId);

	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tracking_details a SET a.time=?1 WHERE a.tracking_id=?2",nativeQuery = true)
	void updateUpdateTime(String updateTime, long trackingId);
	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_time<?1 AND a.unique_code_or_mac_id=?2",nativeQuery = true)
	List<AssetTrackingEntity> getBefourRecords(String entryTime, String gettMacId);

	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_time>?1 AND a.unique_code_or_mac_id=?2  ORDER BY a.entry_time ASC",nativeQuery = true)
	List<AssetTrackingEntity> getAfterRecords(String entryTime, String gettMacId);


	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.asset_gateway_mac_id=?1 AND a.unique_code_or_mac_id=?2  ORDER BY a.tracking_id DESC LIMIT 1",nativeQuery = true)
	AssetTrackingEntity getLastForUpdateGatewayAndTagWise(String assetGatewayMac_Id, String uniqueNumberMacId);

	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_time<?1 AND a.unique_code_or_mac_id=?2  ORDER BY a.entry_time DESC",nativeQuery = true)
	List<AssetTrackingEntity> getpriviousRecords(String entryTime, String gettMacId);

	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_time>?1 AND a.unique_code_or_mac_id=?2 ORDER BY a.entry_time ASC",nativeQuery = true)
	List<AssetTrackingEntity> getAfterRecord(String entryTime, String gettMacId);

	
	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_time=?1 AND a.asset_gateway_mac_id=?2",nativeQuery = true)
	List<AssetTrackingEntity> getListupdatedRecord(String entryTime, String getgMacId);

	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tracking_details a SET a.exist_time=?1 WHERE a.unique_code_or_mac_id=?2 AND a.asset_gateway_mac_id=?3 AND a.exist_time='N/A'",nativeQuery = true)
	void setExitTimetoUpdated(String entrytime, String tagmacid, String gatewaymacid);
//SELECT * FROM asset_tracking_details a WHERE a.entry_time=?1 AND a.unique_code_or_mac_id=?2
	//UPDATE asset_tracking_details a SET a.exist_time=?1 WHERE a.unique_code_or_mac_id=?1 AND a.asset_gateway_mac_id=?1 AND a.exist_time='N/A'

	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.time < ?1  AND a.unique_code_or_mac_id=?2",nativeQuery = true)//ORDER BY entry_time DESC//AND  NOT a.exist_time='N/A'
	List<AssetTrackingEntity> getBefourRecord(String updatetime, String tagmacid);//, String initialTime//AND  NOT a.time =?3 

	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.time <?1  AND a.unique_code_or_mac_id=?2 AND a.timestamp <?3  ORDER BY entry_time DESC",nativeQuery = true)//ORDER BY entry_time DESC//AND  NOT a.exist_time='N/A'
	List<AssetTrackingEntity> getBefourRecordxl(String entryTime, String gettMacId, String timestamp);
	
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.time > ?1  AND a.unique_code_or_mac_id=?2",nativeQuery = true)//AND  NOT a.exist_time='N/A'
	List<AssetTrackingEntity> getAfterRecordofStoreTime(String entryTime, String gettMacId);
	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_time=?1 AND a.unique_code_or_mac_id=?2",nativeQuery = true)
	List<AssetTrackingEntity> getBefourAfterRecordList(String entryTime,String getgMacId);

	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tracking_details a SET a.exist_time=?1 WHERE a.tracking_id=?2",nativeQuery = true)
	void updateRecordOnId(String entryTime, long trackingid);

	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tracking_details a SET a.entry_time=?1 WHERE a.tracking_id=?2",nativeQuery = true)
	void updateEntryTimeOnId(String time, long trackingId);

	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tracking_details a SET a.time=?1 WHERE a.tracking_id=?2",nativeQuery = true)
	void updateUpdateTimeOnId(String time, long trackingId);

	
	@Query(value="SELECT * FROM asset_tracking_details a WHERE a.entry_time=?1",nativeQuery = true)
	List<AssetTrackingEntity> getSameGatewayUpdatedList(String entryTime);

	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tracking_details a SET a.exist_time=?1 WHERE a.tracking_id=?2",nativeQuery = true)
	void updateUpdatedTimeOnEntryTime(String entryTime, long trackingId);

	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE asset_tracking_details a SET a.entry_time=?1,a.time=?2 WHERE a.tracking_id=?3",nativeQuery = true)
	void setupdateTimeZeroAndCreateNew(String entrytime, String time, long trackingId);

	

	



	

	

	

	
	

	
	

	

	

	

	

	

	

	
	
	
	
	




	
	


	

	

	

	

	

	

	
	

	







	

	

	



	

//	@Query(value="",nativeQuery=true)
//	String getcolpumnvalue();

	


	



	

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}