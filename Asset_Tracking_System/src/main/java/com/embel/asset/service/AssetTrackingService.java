package com.embel.asset.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.embel.asset.bean.AllNotificationsCountBean;
import com.embel.asset.bean.GatewayWiseTagCountBean;
import com.embel.asset.bean.ResponseAssetTagWorkingNonWorkinCountBean;
import com.embel.asset.bean.ResponseCollectionBean;
import com.embel.asset.bean.ResponseDashbordCountbean;
import com.embel.asset.bean.ResponseDashbordCountbeanForGPS;
import com.embel.asset.bean.ResponseGetwayWorkingNonWorkingbean;
import com.embel.asset.bean.ResponseGpsTagBean;
import com.embel.asset.bean.ResponseLowBatteryListBean;
import com.embel.asset.bean.ResponseStayTimeListBean;
import com.embel.asset.bean.ResponseTrackingBean;
import com.embel.asset.bean.ResponseTrackingListBean;
import com.embel.asset.bean.ResponseltotalBean;
import com.embel.asset.bean.ResponsesingleGatewayTagCountBean;
import com.embel.asset.bean.positions;
import com.embel.asset.dto.AssetTrackingDto;
import com.embel.asset.entity.AssetGateway;
import com.embel.asset.entity.AssetTag;
import com.embel.asset.entity.AssetTrackingEntity;
import com.embel.asset.entity.DispatchedProductHistory;
import com.embel.asset.entity.ProductDetailForAllocation;
import com.embel.asset.entity.TagGatewayCollection;
import com.embel.asset.entity.VirtualTrackingDetails;
import com.embel.asset.entity.gatewayAndTagCollection;
import com.embel.asset.helper.AssetTrackingReportExcelHelper;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.helper.GenerateBillPDF;
import com.embel.asset.helper.TagReportForGPSHelper;
import com.embel.asset.helper.TagReportForSuperAdminHelper;
import com.embel.asset.repository.AssetGatewayRepository;
import com.embel.asset.repository.AssetTagRepository;
import com.embel.asset.repository.AssetTrackingRepository;
import com.embel.asset.repository.DispatchHistoryRepository;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.ProductAllocationRepository;
import com.embel.asset.repository.TrackingCollectionRepository;
import com.embel.asset.repository.UserDownloadRepo;
import com.embel.asset.repository.UserRepository;
import com.embel.asset.repository.VirtualTrackingDetailsRepository;
import com.embel.asset.repository.columnmappingRepository;
import com.embel.asset.repository.gatewayAndTagCollectionRepository;

@Service
public class AssetTrackingService {

	@Autowired
	AssetTrackingRepository trackingRepo;
	@Autowired
	columnmappingRepository columnmappingrepo;
	@Autowired
	TrackingCollectionRepository collectionRepo;

	@Autowired
	AssetGatewayRepository gatewayRepo;

	@Autowired
	AssetGatewayService gatewayService;
	@Autowired
	AssetTagRepository tagRepository;

	@Autowired
	ProductAllocationRepository allocationRepo;
	@Autowired
	limitsservice limitservice;
	@Autowired
	gatewayAndTagCollectionRepository gatewayAndTagRepo;

	@Autowired
	DispatchHistoryRepository dispatchRepo;
	@Autowired
	UserDownloadRepo userdownlodRepo;
	@Autowired
	AssetTagService assettagservice;
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepo;
	@Autowired
	VirtualTrackingDetailsRepository virtualtrackingdetailsRepo;
	@Autowired
	EmployeUserService employeeUserService;
	@Autowired
	EmployeUserRepository employeeUserRepo;

	/*
	 * public String addTrackingDetails(AssetTrackingDto requestData) { AssetTag
	 * barcodeList =
	 * tagRepository.findByAssetBarcodeSerialNumber(requestData.getbSN()); AssetTag
	 * imeiList = tagRepository.findByAssetIMEINumber(requestData.getImeiNumber());
	 * AssetTag uniqueCodeList =
	 * tagRepository.findByAssetUniqueCodeMacId(requestData.gettMacId());
	 * 
	 * String getLocationFromGateway =
	 * gatewayRepo.getGatewayLocation(requestData.getgMacId());
	 * System.out.println("Tag gateway location..." +getLocationFromGateway);
	 * 
	 * String status = "Failed"; String gatewayName =
	 * requestData.getAssetGatewayName(); String gatewayMacID =
	 * requestData.getgMacId(); String tagName = requestData.getAssetTagName();
	 * 
	 * AssetTrackingEntity entity = new AssetTrackingEntity();
	 * 
	 * if(barcodeList != null && uniqueCodeList != null) { AssetTrackingEntity
	 * dbData = trackingRepo.findByUniqueNumberMacId(requestData.gettMacId());
	 * 
	 * System.out.println(dbData); java.util.Date date1 = new java.util.Date();
	 * //SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss"); Date entryTime =
	 * date1;
	 * 
	 * if(dbData != null) { String dbGateway = dbData.getAssetGatewayName(); String
	 * dbMac = dbData.getAssetGatewayMac_Id(); String dbLocation =
	 * dbData.getTagEntryLocation(); String dbTag = dbData.getAssetTagName();
	 * //if(dbMac.equals(gatewayMacID)&&dbTag.equals(tagName))//dbGateway.equals(
	 * gatewayName) && if(dbGateway.equals(gatewayName) &&
	 * dbMac.equals(gatewayMacID) && dbTag.equals(tagName)) { System.out.
	 * println("Requested tracking details already present in database..."); status
	 * = "Duplicate"; return status; } else { dbData.setExistTime(entryTime);
	 * trackingRepo.save(dbData);
	 * 
	 * //LocalTime time1=LocalTime.parse( new
	 * SimpleDateFormat("yyyy-MM-dd").format(dbData.getEntryTime())); //LocalTime
	 * time2=LocalTime.parse((entryTime)); Date time1=dbData.getEntryTime() ; Date
	 * time2=(entryTime); long difference = (time1.getTime() - time2.getTime());
	 * 
	 * long secondsInMilli = 1000; long minutesInMilli = secondsInMilli * 60; long
	 * hoursInMilli = minutesInMilli * 60; long elapsedHours = difference /
	 * hoursInMilli;
	 * 
	 * System.out.println(elapsedHours); //Date time2=dbData.getEntryTime();
	 * //String time4=dbDate.entryTime; //long elapsedHours =Duration.between(time1,
	 * time2).to; //Duration.between(time1,time2).toHours();
	 * 
	 * String dispatchLocation =
	 * allocationRepo.getDisplatchLocation(requestData.gettMacId());
	 * System.out.println("Tag dispatch location..." +dispatchLocation);
	 * 
	 * if(dispatchLocation.equals(getLocationFromGateway) && elapsedHours>=1) {
	 * System.out.println("Inside if dispatch location match...." + elapsedHours);
	 * entity.setAssetTagName(requestData.getAssetTagName());
	 * entity.setAssetGatewayName(requestData.getAssetGatewayName());
	 * entity.setAssetGatewayMac_Id(requestData.getgMacId());
	 * entity.setBarcodeSerialNumber(requestData.getbSN());
	 * entity.setUniqueNumberMacId(requestData.gettMacId()); //
	 * entity.setDate(date1); entity.setEntryTime(entryTime);
	 * entity.setExistTime(entryTime);
	 * entity.setTagEntryLocation(getLocationFromGateway);
	 * entity.setTagExistLocation(dbLocation);
	 * entity.setLatitude(requestData.getLatitude());
	 * entity.setLongitude(requestData.getLongitude());
	 * entity.setTagCategoty(requestData.getTagCategoty());
	 * entity.setDispatchTime(entryTime.toString());
	 * entity.setBattryPercentage(requestData.getBatStat());
	 * trackingRepo.save(entity); status = "Success";
	 * 
	 * TagGatewayCollection collection =
	 * collectionRepo.findByUniqueNumberMacId(requestData.gettMacId());
	 * 
	 * collection.setAssetGatewayMac_Id(requestData.getgMacId());
	 * collection.setAssetGatewayName(requestData.getAssetGatewayName());
	 * 
	 * collectionRepo.save(collection);
	 * 
	 * int referenceCount = 1; List<AssetTrackingEntity> allDBData
	 * =trackingRepo.getAllDataForGivenTag(tagName); ProductDetailForAllocation
	 * product = allocationRepo.getProductByTag(tagName,requestData.gettMacId());
	 * 
	 * int dispatchCount =
	 * dispatchRepo.findByUniqueNumberMacId(requestData.gettMacId());
	 * System.out.println("Dispatch Count : " +dispatchCount);
	 * 
	 * for (AssetTrackingEntity assetTrackingEntity : allDBData) {
	 * DispatchedProductHistory dispatch = new DispatchedProductHistory();
	 * dispatch.setAssetTagName(assetTrackingEntity.getAssetTagName());
	 * dispatch.setAssetGatewayName(assetTrackingEntity.getAssetGatewayName());
	 * dispatch.setAssetGatewayMac_Id(assetTrackingEntity.getAssetGatewayMac_Id());
	 * dispatch.setBarcodeSerialNumber(assetTrackingEntity.getBarcodeSerialNumber())
	 * ; dispatch.setUniqueNumberMacId(assetTrackingEntity.getUniqueNumberMacId());
	 * // dispatch.setDate(assetTrackingEntity.getDate());
	 * dispatch.setEntryTime(assetTrackingEntity.getEntryTime());
	 * dispatch.setExistTime(assetTrackingEntity.getExistTime());
	 * dispatch.setTagEntryLocation(assetTrackingEntity.getTagEntryLocation());
	 * dispatch.setTagExistLocation(assetTrackingEntity.getTagExistLocation());
	 * dispatch.setLatitude(assetTrackingEntity.getLatitude());
	 * dispatch.setLongitude(assetTrackingEntity.getLongitude());
	 * dispatch.setTagCategoty(assetTrackingEntity.getTagCategoty());
	 * dispatch.setDispatchTime(assetTrackingEntity.getDispatchTime());
	 * 
	 * if(dispatchCount==0) { dispatch.setReferenceCount(referenceCount); }else {
	 * dispatch.setReferenceCount(dispatchCount+1); }
	 * dispatch.setProductId(product.getProductId());
	 * dispatch.setProductName(product.getProductName());
	 * dispatchRepo.save(dispatch); }
	 * 
	 * trackingRepo.deleteByUniqueNumberMacId(requestData.gettMacId());
	 * 
	 * String updatedStatus = "Not-allocated";
	 * tagRepository.updateStatusAfterAllocation(updatedStatus, tagName,
	 * requestData.gettMacId());
	 * allocationRepo.deleteAllocatedProductByTag(tagName,requestData.gettMacId());
	 * System.out.println("product with " +requestData.getAssetTagName()+
	 * "tag is dispatched"); return status; } else {
	 * entity.setAssetTagName(requestData.getAssetTagName());
	 * entity.setAssetGatewayName(requestData.getAssetGatewayName());
	 * entity.setAssetGatewayMac_Id(requestData.getgMacId());
	 * entity.setBarcodeSerialNumber(requestData.getbSN());
	 * entity.setUniqueNumberMacId(requestData.gettMacId()); //
	 * entity.setDate(date1); entity.setEntryTime(entryTime);
	 * entity.setExistTime(null);
	 * entity.setTagEntryLocation(getLocationFromGateway);
	 * entity.setTagExistLocation(dbLocation);
	 * entity.setLatitude(requestData.getLatitude());
	 * entity.setLongitude(requestData.getLongitude());
	 * entity.setTagCategoty(requestData.getTagCategoty());
	 * entity.setDispatchTime(null);
	 * entity.setBattryPercentage(requestData.getBatStat());
	 * trackingRepo.save(entity); status = "Success";
	 * 
	 * TagGatewayCollection collection =
	 * collectionRepo.findByUniqueNumberMacId(requestData.gettMacId());
	 * 
	 * collection.setAssetGatewayMac_Id(requestData.getgMacId());
	 * collection.setAssetGatewayName(requestData.getAssetGatewayName());
	 * collectionRepo.save(collection);
	 * 
	 * System.out.println(requestData.getAssetTagName()+
	 * "tag is scanned to another location..."); return status; } } }//dbdata if }
	 * if(imeiList!=null) { String category = imeiList.getAssetTagCategory();
	 * if(category.equalsIgnoreCase("GPS")) { entity.setAssetGatewayName(null);
	 * entity.setAssetGatewayMac_Id(null); }
	 * entity.setAssetTagName(imeiList.getAssetTagName());
	 * entity.setBarcodeSerialNumber(requestData.getbSN());
	 * entity.setUniqueNumberMacId(null); // entity.setDate(new Date());
	 * entity.setEntryTime(null); entity.setExistTime(null);
	 * entity.setTagEntryLocation(null); entity.setTagExistLocation(null);
	 * entity.setLatitude(requestData.getLatitude());
	 * entity.setLongitude(requestData.getLongitude());
	 * entity.setTagCategoty(category); entity.setDispatchTime(null);
	 * entity.setBattryPercentage(requestData.getBatStat());
	 * trackingRepo.save(entity); status = "Success"; } return status; }
	 */
	/**
	 * get All Tracking Data Userwise
	 * 
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @param category
	 * @return AssetTrackingEntity contain the all data of tracking details
	 */
	public Page<AssetTrackingEntity> getAllTrackingDataUserwise(String fkUserId, String role, Pageable pageable,
			String category) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {

			Page<AssetTrackingEntity> obj = trackingRepo.getTrackingListSuperadminwise(category, pageable);
			System.out.println("object:" + obj);
			return obj;
		}
		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getTrackingListOrganizationwise(category, userId, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTrackingListAdminwise(category, userId, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTrackingListUserwise(category, userId, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);

			return trackingRepo.getTrackingListAdminwise(category, adminid, pageable);
		}
		return null;

	}

	/**
	 * get All TagList For Drodown
	 * 
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return List<String>
	 */
	public List<String> getAllTagListForDrodown(String fkUserId, String role, String category) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTagListSuperadminwise(category);
		}
		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getTagListOrganizationwise(category, userId);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagListAdminwise(category, userId);
		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagListUserwise(category, userId);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getTagListAdminwise(category, adminid);
		}
		return null;

	}

	/**
	 * get All Tracking Data For Filter Userwise
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @return List<ResponseTrackingListBean>
	 */
	public List<ResponseTrackingListBean> getAllTrackingDataForFilterUserwise(String tagName, String fkUserId,
			String role) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTrackingListForFilterSuperadminwise(tagName);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTrackingListForFilterAdminwise(tagName, userId);
		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTrackingListForFilterUserwise(tagName, userId);
		}
		return null;

	}

	/**
	 * get All Gateway List For Dropdown
	 * 
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @param role
	 * @return List<String>
	 */
	public List<String> getAllGatewayListForDropdown(String fkUserId, String role) {

		if (role.equals(CommonConstants.superAdmin)) {
			return gatewayRepo.getGatewayListForDropdownforSuperAdmin();
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForDropdownforOrganization(fkID);
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForDropdownforAdmin(fkID);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForDropdownforUser(fkID);
		}

		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			return gatewayRepo.getGatewayListForDropdownforAdmin(adminId);
		}
		return null;
	}

	/**
	 * get Single Tracking Data For Filter Userwise
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @return List<ResponseTrackingBean>
	 */
	public List<ResponseTrackingBean> getSingleTrackingDataForFilterUserwise(String tagName, String fkUserId,
			String role) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getSingleTrackingDataForFilterSuperadminwise(tagName);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getSingleTrackingDataForFilterAdminwise(tagName, userId);
		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getSingleTrackingDataForFilterUserwise(tagName, userId);
		}
		return null;

	}

	/**
	 * tag Count GatewayWise
	 * 
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @param role
	 * @return List<GatewayWiseTagCountBean>
	 */
	public List<GatewayWiseTagCountBean> tagCountGatewayWise(String fkUserId, String role) {

		List<GatewayWiseTagCountBean> tagList = new ArrayList<GatewayWiseTagCountBean>();
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			List<Object[]> countList = collectionRepo.getCountForSuperAdmin();

			for (Object[] objects : countList) {

				positions pos = new positions();
				String name = objects[0].toString();
				Integer count = Integer.parseInt(objects[1].toString());
				String location = objects[2].toString();
				long x = gatewayRepo.getxCoordinate(objects[0].toString());
				long y = gatewayRepo.getyCoordinate(objects[0].toString());
				pos.setX(x);
				pos.setY(y);
				tagList.add(new GatewayWiseTagCountBean(name, count, location, pos));
			}
			return tagList;
		}
		if (role.equals(CommonConstants.admin)) {

			List<Object[]> countList = collectionRepo.getCountForAdmin(id);

			for (Object[] objects : countList) {

				String name = objects[0].toString();
				Integer count = Integer.parseInt(objects[1].toString());
				positions pos = new positions();
				long x = gatewayRepo.getxCoordinate(objects[0].toString());
				long y = gatewayRepo.getyCoordinate(objects[0].toString());
				pos.setX(x);
				pos.setY(y);
				String location = objects[2].toString();
				tagList.add(new GatewayWiseTagCountBean(name, count, location, pos));
			}
			return tagList;

		}
		if (role.equals(CommonConstants.user)) {

			List<Object[]> countList = collectionRepo.getCountForUser(id);

			for (Object[] objects : countList) {

				String name = objects[0].toString();
				Integer count = Integer.parseInt(objects[1].toString());
				String location = objects[2].toString();
				positions pos = new positions();
				long x = gatewayRepo.getxCoordinate(objects[0].toString());
				long y = gatewayRepo.getyCoordinate(objects[0].toString());
				pos.setX(x);
				pos.setY(y);
				tagList.add(new GatewayWiseTagCountBean(name, count, location, pos));
			}
			return tagList;

		}
		return null;
	}

	/**
	 * tag Count Gateway Wisenew
	 * 
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @param role
	 * @return List<GatewayWiseTagCountBean>
	 */
	public List<GatewayWiseTagCountBean> tagCountGatewayWisenew(String fkUserId, String role) {
		List<GatewayWiseTagCountBean> tagList = new ArrayList<GatewayWiseTagCountBean>();
		Long id = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {

			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date = formatter.format(new Date());
			System.out.println("@@@date" + date);

			Format formatter1 = new SimpleDateFormat("HH:mm:ss");
			String totime = formatter1.format(new Date());

			LocalTime currenttime = LocalTime.now();
			LocalTime time1 = currenttime.minusMinutes(5);

			DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm:ss");
			String fromtime = time1.format(formatter3);

			System.out.println("fromtime " + fromtime + " and " + " totime " + totime);

			List<Object[]> countList = gatewayAndTagRepo.getcurrenttaginGatewayCountForSuperAdmin();// ,totime

			for (Object[] objects : countList) {

				String name = objects[0].toString();
				Integer count = Integer.parseInt(objects[1].toString());
				String location = objects[2].toString();
				positions pos = new positions();
				long x = gatewayRepo.getxCoordinate(objects[0].toString());
				long y = gatewayRepo.getyCoordinate(objects[0].toString());
				pos.setX(x);
				pos.setY(y);
				tagList.add(new GatewayWiseTagCountBean(name, count, location, pos));
			}

			return tagList;
		}
		if (role.equals(CommonConstants.organization)) {
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date = formatter.format(new Date());
			System.out.println("@@@date" + date);

			Format formatter1 = new SimpleDateFormat("HH:mm:ss");
			String totime = formatter1.format(new Date());

			LocalTime currenttime = LocalTime.now();
			LocalTime time1 = currenttime.minusMinutes(5);

			DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm:ss");
			String fromtime = time1.format(formatter3);
			List<Object[]> countList = gatewayAndTagRepo.getcurrenttaginGatewayCountForOrganization(id);// date,
			for (Object[] objects : countList) {

				String name = objects[0].toString();
				Integer count = Integer.parseInt(objects[1].toString());
				String location = objects[2].toString();
				positions pos = new positions();
				long x = gatewayRepo.getxCoordinate(objects[0].toString());
				long y = gatewayRepo.getyCoordinate(objects[0].toString());
				pos.setX(x);
				pos.setY(y);
				tagList.add(new GatewayWiseTagCountBean(name, count, location, pos));
			}

			return tagList;

		}
		if (role.equals(CommonConstants.admin)) {
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date = formatter.format(new Date());
			System.out.println("@@@date" + date);

			Format formatter1 = new SimpleDateFormat("HH:mm:ss");
			String totime = formatter1.format(new Date());

			LocalTime currenttime = LocalTime.now();
			LocalTime time1 = currenttime.minusMinutes(5);

			DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm:ss");
			String fromtime = time1.format(formatter3);
			List<Object[]> countList = gatewayAndTagRepo.getcurrenttaginGatewayCountForAdmin(id);// date,
			for (Object[] objects : countList) {

				String name = objects[0].toString();
				Integer count = Integer.parseInt(objects[1].toString());
				String location = objects[2].toString();
				positions pos = new positions();
				long x = gatewayRepo.getxCoordinate(objects[0].toString());
				long y = gatewayRepo.getyCoordinate(objects[0].toString());
				pos.setX(x);
				pos.setY(y);
				tagList.add(new GatewayWiseTagCountBean(name, count, location, pos));
			}

			return tagList;

		}
		if (role.equals(CommonConstants.user)) {

			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date = formatter.format(new Date());
			System.out.println("@@@date" + date);

			Format formatter1 = new SimpleDateFormat("HH:mm:ss");
			String totime = formatter1.format(new Date());

			LocalTime currenttime = LocalTime.now();
			LocalTime time1 = currenttime.minusMinutes(5);

			DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm:ss");
			String fromtime = time1.format(formatter3);

			List<Object[]> countList = gatewayAndTagRepo.getcurrenttaginGatewayCountForUser(id);// date,
			for (Object[] objects : countList) {

				String name = objects[0].toString();
				Integer count = Integer.parseInt(objects[1].toString());
				String location = objects[2].toString();
				positions pos = new positions();
				long x = gatewayRepo.getxCoordinate(objects[0].toString());
				long y = gatewayRepo.getyCoordinate(objects[0].toString());
				pos.setX(x);
				pos.setY(y);
				tagList.add(new GatewayWiseTagCountBean(name, count, location, pos));
			}

			return tagList;

		}
		if (role.equals(CommonConstants.empUser)) {
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date = formatter.format(new Date());
			System.out.println("@@@date" + date);

			Format formatter1 = new SimpleDateFormat("HH:mm:ss");
			String totime = formatter1.format(new Date());

			LocalTime currenttime = LocalTime.now();
			LocalTime time1 = currenttime.minusMinutes(5);

			long adminId = employeeUserRepo.getAdminId(fkUserId);
			DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm:ss");
			String fromtime = time1.format(formatter3);
			List<Object[]> countList = gatewayAndTagRepo.getcurrenttaginGatewayCountForAdmin(adminId);// date,
			for (Object[] objects : countList) {

				String name = objects[0].toString();
				Integer count = Integer.parseInt(objects[1].toString());
				String location = objects[2].toString();
				positions pos = new positions();
				long x = gatewayRepo.getxCoordinate(objects[0].toString());
				long y = gatewayRepo.getyCoordinate(objects[0].toString());
				pos.setX(x);
				pos.setY(y);
				tagList.add(new GatewayWiseTagCountBean(name, count, location, pos));
			}

			return tagList;

		}

		return null;
	}

	/**
	 * get Aged And NewTagCount By Gateway
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @return ResponseCollectionBean
	 * @throws ParseException
	 */
	public ResponseCollectionBean getAgedAndNewTagCountByGateway(String gatewayName, String fkUserId, String role)
			throws ParseException {
		Long id = Long.parseLong(fkUserId);

		String gateway[] = new String[1];
		Integer agedTag[] = new Integer[1];
		Integer newTag[] = new Integer[1];
		Integer totalTagCount[] = new Integer[1];
		// .............fordates

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date cdate1 = new java.util.Date();
		Date date = new Date(); // Or where ever you get it from

		formatter.setTimeZone(TimeZone.getTimeZone("IST"));
		String date2 = formatter.format(new Date());
		System.out.println("####date2" + date2);

		LocalDateTime dateTime = LocalDateTime.now();
		Date todate = new java.util.Date();

		todate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date2);

		System.out.println("####current fromdate " + todate);
		cdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date2);

		System.out.println("####current date " + cdate1);
		// .
		LocalDateTime mdate = LocalDateTime.now();
		LocalDateTime mdate1 = mdate.minusDays(30);
		System.out.println("one month befour date:" + mdate1);

		DateTimeFormatter formatter3 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		String mdate3 = mdate1.toString().replace("T", " ");
		System.out.println("mdate3" + mdate3);

		Date fromdate = new java.util.Date();

		fromdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mdate3);
		System.out.println("fromdate:" + fromdate + "todate:" + todate);

		// ..................

		if (role.equals(CommonConstants.superAdmin)) {
			System.out.println(gatewayName);
			Integer agedTagCount = 0;
			Integer newTagCount = 0;
			Integer totalCount = 0;
			try {
				agedTagCount = gatewayAndTagRepo.getAgedTagCountByGateway(mdate3, gatewayName);
				newTagCount = gatewayAndTagRepo.getNewTagCountByGateway(mdate3, date2, gatewayName);
				System.out.println();
				if (agedTagCount == null) {
					agedTagCount = 0;
				}
				if (newTagCount == null) {
					newTagCount = 0;
				}

				totalCount = agedTagCount + newTagCount;
				if (totalCount == null || totalCount == 0) {
					totalCount = 0;
				}
			} catch (Exception e) {
				System.out.println(e);
			}

			System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
			gateway[0] = gatewayName;
			agedTag[0] = agedTagCount;
			newTag[0] = newTagCount;
			totalTagCount[0] = totalCount;
			return new ResponseCollectionBean(gateway, totalTagCount, agedTag, newTag);
		}
		if (role.equals(CommonConstants.organization)) {
			System.out.println(gatewayName);
			Integer agedTagCount = 0;
			Integer newTagCount = 0;
			Integer totalCount = 0;
			try {
				agedTagCount = gatewayAndTagRepo.getAgedTagCountByGateway(mdate3, gatewayName);
				newTagCount = gatewayAndTagRepo.getNewTagCountByGateway(mdate3, date2, gatewayName);
				System.out.println();
				if (agedTagCount == null) {
					agedTagCount = 0;
				}
				if (newTagCount == null) {
					newTagCount = 0;
				}

				totalCount = agedTagCount + newTagCount;
				if (totalCount == null || totalCount == 0) {
					totalCount = 0;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
			gateway[0] = gatewayName;
			agedTag[0] = agedTagCount;
			newTag[0] = newTagCount;
			totalTagCount[0] = totalCount;
			return new ResponseCollectionBean(gateway, totalTagCount, agedTag, newTag);
		}
		if (role.equals(CommonConstants.admin)) {
			System.out.println(gatewayName);
			Integer agedTagCount = 0;
			Integer newTagCount = 0;
			Integer totalCount = 0;
			try {
				agedTagCount = gatewayAndTagRepo.getAgedTagCountByGateway(mdate3, gatewayName);
				newTagCount = gatewayAndTagRepo.getNewTagCountByGateway(mdate3, date2, gatewayName);
				System.out.println();
				if (agedTagCount == null) {
					agedTagCount = 0;
				}
				if (newTagCount == null) {
					newTagCount = 0;
				}

				totalCount = agedTagCount + newTagCount;
				if (totalCount == null || totalCount == 0) {
					totalCount = 0;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
			gateway[0] = gatewayName;
			agedTag[0] = agedTagCount;
			newTag[0] = newTagCount;
			totalTagCount[0] = totalCount;
			return new ResponseCollectionBean(gateway, totalTagCount, agedTag, newTag);
		}

		if (role.equals(CommonConstants.user)) {
			System.out.println(gatewayName);
			Integer agedTagCount = 0;
			Integer newTagCount = 0;
			Integer totalCount = 0;
			try {
				agedTagCount = gatewayAndTagRepo.getAgedTagCountByGateway(mdate3, gatewayName);
				newTagCount = gatewayAndTagRepo.getNewTagCountByGateway(mdate3, date2, gatewayName);
				System.out.println();
				if (agedTagCount == null) {
					agedTagCount = 0;
				}
				if (newTagCount == null) {
					newTagCount = 0;
				}

				totalCount = agedTagCount + newTagCount;
				if (totalCount == null || totalCount == 0) {
					totalCount = 0;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
			gateway[0] = gatewayName;
			agedTag[0] = agedTagCount;
			newTag[0] = newTagCount;
			totalTagCount[0] = totalCount;
			return new ResponseCollectionBean(gateway, totalTagCount, agedTag, newTag);
		}
		return null;
	}

	/**
	 * get Aged And NewTag Countnew
	 * 
	 * @param fkUserId
	 * @param role
	 * @return ResponseCollectionBean
	 * @throws ParseException
	 */
	public ResponseCollectionBean getAgedAndNewTagCountnew(String fkUserId, String role) throws ParseException {
		Long id = Long.parseLong(fkUserId);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date cdate1 = new java.util.Date();
		Date date = new Date(); // Or where ever you get it from

		formatter.setTimeZone(TimeZone.getTimeZone("IST"));
		String date2 = formatter.format(new Date());
		System.out.println("####date2" + date2);

		LocalDateTime dateTime = LocalDateTime.now();
		Date todate = new java.util.Date();

		todate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date2);

		System.out.println("####current fromdate " + todate);
		cdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date2);

		System.out.println("####current date " + cdate1);
		// .
		LocalDateTime mdate = LocalDateTime.now();
		String dayslimit = null;
		LocalDateTime mdate1 = null;
		try {
			dayslimit = limitservice.getdayslimits(fkUserId);
			if (dayslimit == null) {
				dayslimit = "22";
			}
			mdate1 = mdate.minusDays(Long.parseLong(dayslimit));

		} catch (Exception e) {
			System.out.println(e);
		}

		String mdate3 = mdate1.toString().replace("T", " ");

		DateTimeFormatter formatter3 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		System.out.println("mdate3" + mdate3);

		Date fromdate = new java.util.Date();

		fromdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mdate3);
		System.out.println("fromdate:" + fromdate + "todate:" + todate);

		if (role.equals(CommonConstants.superAdmin)) {
			List<String> gatewayList = gatewayRepo.getGatewayNameListForSuperAdmin();
			int i = 0;
			String gatewayName[] = new String[gatewayList.size()];
			Integer agedTag[] = new Integer[gatewayList.size()];
			Integer newTag[] = new Integer[gatewayList.size()];
			Integer totalTagCount[] = new Integer[gatewayList.size()];

			for (String gateway : gatewayList) {
				System.out.println(gateway);
				Integer agedTagCount = 0;
				Integer newTagCount = 0;
				Integer totalCount = 0;
				try {
					agedTagCount = gatewayAndTagRepo.getAgedTagCountByGateway(mdate3, gateway);
					newTagCount = gatewayAndTagRepo.getNewTagCountByGateway(mdate3, date2, gateway);
					System.out.println();
					if (agedTagCount == null) {
						agedTagCount = 0;
					}
					if (newTagCount == null) {
						newTagCount = 0;
					}

					totalCount = agedTagCount + newTagCount;
					if (totalCount == null || totalCount == 0) {
						totalCount = 0;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
				gatewayName[i] = gateway;
				agedTag[i] = agedTagCount;
				newTag[i] = newTagCount;
				totalTagCount[i] = totalCount;
				i++;
			}
			return new ResponseCollectionBean(gatewayName, totalTagCount, agedTag, newTag);
		}
		if (role.equals(CommonConstants.organization)) {
			List<String> gatewayList = gatewayRepo.getGatewayNameListForOrganization(id);
			int i = 0;
			String gatewayName[] = new String[gatewayList.size()];
			Integer agedTag[] = new Integer[gatewayList.size()];
			Integer newTag[] = new Integer[gatewayList.size()];
			Integer totalTagCount[] = new Integer[gatewayList.size()];

			for (String gateway : gatewayList) {
				System.out.println(gateway);
				Integer agedTagCount = 0;
				Integer newTagCount = 0;
				Integer totalCount = 0;
				try {
					agedTagCount = gatewayAndTagRepo.getAgedTagCountByGateway(mdate3, gateway);
					newTagCount = gatewayAndTagRepo.getNewTagCountByGateway(mdate3, date2, gateway);

					if (agedTagCount == null) {
						agedTagCount = 0;
					}
					if (newTagCount == null) {
						newTagCount = 0;
					}

					totalCount = agedTagCount + newTagCount;
					if (totalCount == null || totalCount == 0) {
						totalCount = 0;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
				gatewayName[i] = gateway;
				agedTag[i] = agedTagCount;
				newTag[i] = newTagCount;
				totalTagCount[i] = totalCount;
				i++;
			}
			return new ResponseCollectionBean(gatewayName, totalTagCount, agedTag, newTag);
		}

		if (role.equals(CommonConstants.admin)) {
			List<String> gatewayList = gatewayRepo.getGatewayNameListForAdmin(id);
			int i = 0;
			String gatewayName[] = new String[gatewayList.size()];
			Integer agedTag[] = new Integer[gatewayList.size()];
			Integer newTag[] = new Integer[gatewayList.size()];
			Integer totalTagCount[] = new Integer[gatewayList.size()];

			for (String gateway : gatewayList) {
				System.out.println(gateway);
				Integer agedTagCount = 0;
				Integer newTagCount = 0;
				Integer totalCount = 0;
				try {
					agedTagCount = gatewayAndTagRepo.getAgedTagCountByGateway(mdate3, gateway);
					newTagCount = gatewayAndTagRepo.getNewTagCountByGateway(mdate3, date2, gateway);

					if (agedTagCount == null) {
						agedTagCount = 0;
					}
					if (newTagCount == null) {
						newTagCount = 0;
					}

					totalCount = agedTagCount + newTagCount;
					if (totalCount == null || totalCount == 0) {
						totalCount = 0;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
				gatewayName[i] = gateway;
				agedTag[i] = agedTagCount;
				newTag[i] = newTagCount;
				totalTagCount[i] = totalCount;
				i++;
			}
			return new ResponseCollectionBean(gatewayName, totalTagCount, agedTag, newTag);
		}

		if (role.equals(CommonConstants.user)) {
			List<String> gatewayList = gatewayRepo.getGatewayNameListForUser(id);
			int i = 0;
			String gatewayName[] = new String[gatewayList.size()];
			Integer agedTag[] = new Integer[gatewayList.size()];
			Integer newTag[] = new Integer[gatewayList.size()];
			Integer totalTagCount[] = new Integer[gatewayList.size()];

			for (String gateway : gatewayList) {
				System.out.println(gateway);
				Integer agedTagCount = 0;
				Integer newTagCount = 0;
				Integer totalCount = 0;
				try {
					agedTagCount = gatewayAndTagRepo.getAgedTagCountByGateway(mdate3, gateway);
					newTagCount = gatewayAndTagRepo.getNewTagCountByGateway(mdate3, date2, gateway);

					if (agedTagCount == null) {
						agedTagCount = 0;
					}
					if (newTagCount == null) {
						newTagCount = 0;
					}

					totalCount = agedTagCount + newTagCount;
					if (totalCount == null || totalCount == 0) {
						totalCount = 0;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
				gatewayName[i] = gateway;
				agedTag[i] = agedTagCount;
				newTag[i] = newTagCount;
				totalTagCount[i] = totalCount;
				i++;
			}
			return new ResponseCollectionBean(gatewayName, totalTagCount, agedTag, newTag);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			List<String> gatewayList = gatewayRepo.getGatewayNameListForAdmin(adminId);
			int i = 0;
			String gatewayName[] = new String[gatewayList.size()];
			Integer agedTag[] = new Integer[gatewayList.size()];
			Integer newTag[] = new Integer[gatewayList.size()];
			Integer totalTagCount[] = new Integer[gatewayList.size()];

			for (String gateway : gatewayList) {
				System.out.println(gateway);
				Integer agedTagCount = 0;
				Integer newTagCount = 0;
				Integer totalCount = 0;
				try {
					agedTagCount = gatewayAndTagRepo.getAgedTagCountByGateway(mdate3, gateway);
					newTagCount = gatewayAndTagRepo.getNewTagCountByGateway(mdate3, date2, gateway);

					if (agedTagCount == null) {
						agedTagCount = 0;
					}
					if (newTagCount == null) {
						newTagCount = 0;
					}

					totalCount = agedTagCount + newTagCount;
					if (totalCount == null || totalCount == 0) {
						totalCount = 0;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
				gatewayName[i] = gateway;
				agedTag[i] = agedTagCount;
				newTag[i] = newTagCount;
				totalTagCount[i] = totalCount;
				i++;
			}
			return new ResponseCollectionBean(gatewayName, totalTagCount, agedTag, newTag);
		}

		return null;
	}

	/**
	 * get Aged And NewTagCount
	 * 
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ResponseCollectionBean
	 */
	public ResponseCollectionBean getAgedAndNewTagCount(String fkUserId, String role) {
		Long id = Long.parseLong(fkUserId);

		/*
		 * Gateway : [gateway-1,gateway-2,gateway-3] Aged tag : [0,0,1] New Tag :
		 * [0,2,3]
		 * 
		 * Bean - gateway MytBean b= new MyBean(); b.gateway=new
		 * String[gatewayList.size()] b.Aged=new int[gatewayList.size()] b.new=new
		 * int[gatewayList.size()] i=0;
		 * 
		 */

		if (role.equals(CommonConstants.superAdmin)) {
			List<String> gatewayList = collectionRepo.getGatewayListForSuperAdmin();
			int i = 0;
			String gatewayName[] = new String[gatewayList.size()];
			Integer agedTag[] = new Integer[gatewayList.size()];
			Integer newTag[] = new Integer[gatewayList.size()];
			Integer totalTagCount[] = new Integer[gatewayList.size()];

			for (String gateway : gatewayList) {
				System.out.println(gateway);
				Integer agedTagCount = trackingRepo.getAgedTagCountByGateway(gateway);
				Integer newTagCount = trackingRepo.getNewTagCountByGateway(gateway);
				// b.new[i]=newTagCount
				Integer totalCount = agedTagCount + newTagCount;
				System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
				gatewayName[i] = gateway;
				agedTag[i] = agedTagCount;
				newTag[i] = newTagCount;
				totalTagCount[i] = totalCount;
				i++;
			}
			return new ResponseCollectionBean(gatewayName, totalTagCount, agedTag, newTag);
		}

		if (role.equals(CommonConstants.admin)) {
			List<String> gatewayList = collectionRepo.getGatewayListForAdmin(id);
			int i = 0;
			String gatewayName[] = new String[gatewayList.size()];
			Integer agedTag[] = new Integer[gatewayList.size()];
			Integer newTag[] = new Integer[gatewayList.size()];
			Integer totalTagCount[] = new Integer[gatewayList.size()];

			for (String gateway : gatewayList) {
				System.out.println(gateway);
				Integer agedTagCount = trackingRepo.getAgedTagCountByGateway(gateway);
				Integer newTagCount = trackingRepo.getNewTagCountByGateway(gateway);
				// b.new[i]=newTagCount
				Integer totalCount = agedTagCount + newTagCount;
				System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
				gatewayName[i] = gateway;
				agedTag[i] = agedTagCount;
				newTag[i] = newTagCount;
				totalTagCount[i] = totalCount;
				i++;
			}
			return new ResponseCollectionBean(gatewayName, totalTagCount, agedTag, newTag);
		}

		if (role.equals(CommonConstants.user)) {
			List<String> gatewayList = collectionRepo.getGatewayListForUser(id);
			int i = 0;
			String gatewayName[] = new String[gatewayList.size()];
			Integer agedTag[] = new Integer[gatewayList.size()];
			Integer newTag[] = new Integer[gatewayList.size()];
			Integer totalTagCount[] = new Integer[gatewayList.size()];

			for (String gateway : gatewayList) {
				System.out.println(gateway);
				Integer agedTagCount = trackingRepo.getAgedTagCountByGateway(gateway);
				Integer newTagCount = trackingRepo.getNewTagCountByGateway(gateway);
				// b.new[i]=newTagCount
				Integer totalCount = agedTagCount + newTagCount;
				System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalCount);
				gatewayName[i] = gateway;
				agedTag[i] = agedTagCount;
				newTag[i] = newTagCount;
				totalTagCount[i] = totalCount;
				i++;
			}
			return new ResponseCollectionBean(gatewayName, totalTagCount, agedTag, newTag);
		}
		return null;
	}

	/**
	 * get Aged And NewTag Countdatewise
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param fromdate
	 * @param todate
	 * @param fkUserId
	 * @param role
	 * @return ResponsesingleGatewayTagCountBean
	 */
	public ResponsesingleGatewayTagCountBean getAgedAndNewTagCountdatewise(String gatewayName, String fromdate,
			String todate, String fkUserId, String role) {
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {

			Integer agedTag = 0;
			Integer newTag = 0;

			Integer agedTagCount = trackingRepo.getAgedTagCountByGatewayDatewise(gatewayName, fromdate, todate);
			Integer newTagCount = trackingRepo.getNewTagCountByGatewayDatewise(gatewayName, fromdate, todate);
			// b.new[i]=newTagCount
			Integer totalTagCount = agedTagCount + newTagCount;
			System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalTagCount);

			return new ResponsesingleGatewayTagCountBean(gatewayName, totalTagCount, agedTagCount, newTagCount);
		}

		if (role.equals(CommonConstants.admin)) {

			Integer agedTagCount = trackingRepo.getAgedTagCountByGatewayDatewise(gatewayName, fromdate, todate);
			Integer newTagCount = trackingRepo.getNewTagCountByGatewayDatewise(gatewayName, fromdate, todate);
			// b.new[i]=newTagCount
			Integer totalTagCount = agedTagCount + newTagCount;
			System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalTagCount);

			return new ResponsesingleGatewayTagCountBean(gatewayName, totalTagCount, agedTagCount, newTagCount);
		}

		if (role.equals(CommonConstants.user)) {

			Integer agedTagCount = trackingRepo.getAgedTagCountByGatewayDatewise(gatewayName, fromdate, todate);
			Integer newTagCount = trackingRepo.getNewTagCountByGatewayDatewise(gatewayName, fromdate, todate);

			Integer totalTagCount = agedTagCount + newTagCount;
			System.out.println(agedTagCount + " Count " + newTagCount + " Total Count : " + totalTagCount);

			return new ResponsesingleGatewayTagCountBean(gatewayName, totalTagCount, agedTagCount, newTagCount);
		}
		return null;
	}
//

	// Reports
	/**
	 * load All TrackingData
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 * @throws ParseException
	 */
	public InputStream loadAllTrackingData(String tagName, String fkUserId, String role, String category)
			throws ParseException {
		if (category.equals("GPS")) {
			if (role.equals(CommonConstants.superAdmin)) {
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForSuperAdminExportDownloadForGPS(tagName,
						category);
				System.out.println("payList" + payList);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForOrganizationExportDownloadForGPS(tagName, fkID, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForAdminExportDownloadForGPS(tagName, fkID,
						category);
				if (payList.size() > 0) {

					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForAdminExportDownloadForGPS(tagName,
						adminid, category);
				if (payList.size() > 0) {

					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForUserExportDownloadForGPS(tagName, fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
			}

		} else {
			if (role.equals(CommonConstants.superAdmin)) {
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForSuperAdminExportDownload(tagName,
						category);
				System.out.println("payList" + payList);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForSuperAdmin(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForOrganizationExportDownload(tagName,
						fkID, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUser(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForAdminExportDownload(tagName, fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUser(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForAdminExportDownload(tagName,
						adminid, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUser(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForUserExportDownload(tagName, fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUser(payList);
					return in;
				}
			}

		}
		return null;
	}

	// PDF
	/**
	 * load All Tracking DataPDF
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 * @throws ParseException
	 */
	public InputStream loadAllTrackingDataPDF(String tagName, String fkUserId, String role, String category)
			throws ParseException {

		if (category.equals("GPS")) {
			if (role.equals(CommonConstants.superAdmin)) {
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForSuperAdminExportDownloadForGPS(tagName,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForOrganizationExportDownloadForGPS(tagName, fkID, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForAdminExportDownloadForGPS(tagName, fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForAdminExportDownloadForGPS(tagName,
						adminid, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForUserExportDownloadForGPS(tagName, fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}

		} else {

			if (role.equals(CommonConstants.superAdmin)) {
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForSuperAdminExportDownload(tagName,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDF(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForOrganizationExportDownload(tagName,
						fkID, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDF(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForAdminExportDownload(tagName, fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDF(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForAdminExportDownload(tagName,
						adminid, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDF(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForUserExportDownload(tagName, fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDF(payList);
					return in;
				}
			}

		}
		return null;
	}

	// ...................................................................................................
	// between dates excel

	/**
	 * load All Tracking Data Between Date
	 * 
	 * @author Pratik Chaudhari
	 * @param start
	 * @param end
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 * @throws ParseException
	 */
	public InputStream loadAllTrackingDataBetweenDate(String start, String end, String tagName, String fkUserId,
			String role, String category) throws ParseException {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat formatter11 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		Date fromDate = formatter11.parse(start);
		Date toDate = formatter11.parse(end);
		if (category.equals("GPS")) {
			if (role.equals(CommonConstants.superAdmin)) {
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForSuperAdminExportDownloadBetweenDateForGps(fromDate, toDate, tagName, category);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
				return null;
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForOrganizationExportDownloadBetweenDateForGps(fromDate, toDate, tagName, fkID,
								category);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
				return null;
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForAdminExportDownloadBetweenDateForGPS(fromDate, toDate, tagName, fkID, category);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
				return null;
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForAdminExportDownloadBetweenDateForGPS(
						fromDate, toDate, tagName, adminid, category);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
				return null;
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForUserExportDownloadBetweenDateForGPS(fromDate, toDate, tagName, fkID, category);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForGPSHelper.tagExcelForSuperAdminForGPS(payList);
					return in;
				}
				return null;
			}

		} else {
			if (role.equals(CommonConstants.superAdmin)) {
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForSuperAdminExportDownloadBetweenDate(start, end, tagName);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForSuperAdminBetweenDate(payList);
					return in;
				}
				return null;
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForOrganizationExportDownloadBetweenDate(start, end, tagName, fkID);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserBetweenDate(payList);
					return in;
				}
				return null;
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForAdminExportDownloadBetweenDate(start, end, tagName, fkID);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserBetweenDate(payList);
					return in;
				}
				return null;
			}
			if (role.equals(CommonConstants.empUser)) {
				Long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForAdminExportDownloadBetweenDate(start, end, tagName, adminid);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserBetweenDate(payList);
					return in;
				}
				return null;
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForUserExportDownloadBetweenDate(start, end, tagName, fkID);

				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserBetweenDate(payList);
					return in;
				}
				return null;
			}

		}

		return null;
	}

	// ..........................................................................................................................................
	// pratik : getwayName
	// between dates excel
	/**
	 * load All TrackingData Gatewaywise Between Date
	 * 
	 * @author Pratik Chaudhari
	 * @param start
	 * @param end
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @return InputStream
	 * @throws ParseException
	 */
	public InputStream loadAllTrackingDataGatewaywiseBetweenDate(String start, String end, String gatewayName,
			String fkUserId, String role) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = formatter.parse(start);
		Date toDate = formatter.parse(end);

		if (role.equals(CommonConstants.superAdmin)) {
			List<ResponseTrackingListBean> payList = trackingRepo
					.getTagReportForSuperAdminExportDownloadBetweenDategatewayName(fromDate, toDate, gatewayName);
			if (payList.size() > 0) {
				ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForSuperAdminBetweenDate(payList);
				return in;
			}
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			List<ResponseTrackingListBean> payList = trackingRepo
					.getTagReportForAdminExportDownloadBetweenDategatewayName(fromDate, toDate, gatewayName, fkID);
			if (payList.size() > 0) {
				ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserBetweenDate(payList);
				return in;
			}
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			List<ResponseTrackingListBean> payList = trackingRepo
					.getTagReportForUserExportDownloadBetweenDategatewayName(fromDate, toDate, gatewayName, fkID);
			if (payList.size() > 0) {
				ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserBetweenDate(payList);
				return in;
			}
		}
		return null;
	}

	// pratik
	/**
	 * load All Tracking Data PDF Gatewaywise Between Date
	 * 
	 * @author Pratik Chaudhari
	 * @param start
	 * @param end
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @return InputStream
	 * @throws ParseException
	 */
	public InputStream loadAllTrackingDataPDFGatewaywiseBetweenDate(String start, String end, String gatewayName,
			String fkUserId, String role) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = formatter.parse(start);
		Date toDate = formatter.parse(end);

		if (role.equals(CommonConstants.superAdmin)) {
			List<ResponseTrackingListBean> payList = trackingRepo
					.getTagReportForSuperAdminExportDownloadBetweenDategatewayName(fromDate, toDate, gatewayName);

			if (payList.size() > 0) {
				ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWiseBetweenDate(payList);
				return in;
			}
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			List<ResponseTrackingListBean> payList = trackingRepo
					.getTagReportForAdminExportDownloadBetweenDategatewayName(fromDate, toDate, gatewayName, fkID);
			if (payList.size() > 0) {
				ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWiseBetweenDate(payList);
				return in;
			}
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			List<ResponseTrackingListBean> payList = trackingRepo
					.getTagReportForUserExportDownloadBetweenDategatewayName(fromDate, toDate, gatewayName, fkID);
			if (payList.size() > 0) {
				ByteArrayInputStream in = GenerateBillPDF.billPDFBetweenDate(payList);
				return in;
			}
		}
		return null;
	}

	// ............................................................................................................................................
	/**
	 * load All Tracking Data PDF Between Date
	 * 
	 * @author Pratik Chaudhari
	 * @param start
	 * @param end
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 * @throws ParseException
	 */
	public InputStream loadAllTrackingDataPDFBetweenDate(String start, String end, String tagName, String fkUserId,
			String role, String category) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = formatter.parse(start);
		Date toDate = formatter.parse(end);

		if (category.equals("GPS")) {
			if (role.equals(CommonConstants.superAdmin)) {
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForSuperAdminExportDownloadBetweenDateForGps(fromDate, toDate, tagName, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForOrganizationExportDownloadBetweenDateForGps(fromDate, toDate, tagName, fkID,
								category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForAdminExportDownloadBetweenDateForGPS(fromDate, toDate, tagName, fkID, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.empUser)) {

				long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo.getTagReportForAdminExportDownloadBetweenDateForGPS(
						fromDate, toDate, tagName, adminid, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingRepo
						.getTagReportForUserExportDownloadBetweenDateForGPS(fromDate, toDate, tagName, fkID, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFForgps(payList);
					return in;
				}
			}

		} else {
			if (role.equals(CommonConstants.superAdmin)) {
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForSuperAdminExportDownloadBetweenDate(start, end, tagName);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWiseBetweenDate(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForOrganizationExportDownloadBetweenDate(start, end, tagName, fkID);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFBetweenDate(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForAdminExportDownloadBetweenDate(start, end, tagName, fkID);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFBetweenDate(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForAdminExportDownloadBetweenDate(start, end, tagName, adminid);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFBetweenDate(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForUserExportDownloadBetweenDate(start, end, tagName, fkID);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFBetweenDate(payList);
					return in;
				}
			}

		}
		return null;
	}

	/**
	 * load All Tracking Data GatewatWise
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @param fromDate
	 * @param toDate
	 * @param duration
	 * @return InputStream
	 * @throws ParseException
	 */
	public InputStream loadAllTrackingDataGatewatWise(String gatewayName, String fkUserId, String role, String fromDate,
			String toDate, String duration) throws ParseException {
		if (duration != null) {
			if (duration.equals("lastMonth")) {
				LocalDate today = LocalDate.now();
				LocalDate endDate = today.minusDays(1);// yesterday
				LocalDate startDate = today.minusDays(30);
				// System.out.println("today"+today);
				System.out.println("startDate" + startDate);
				System.out.println("startDate" + endDate);

				if (role.equals(CommonConstants.superAdmin)) {
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForSuperAdminExportDownloadGatewayWisexl(gatewayName, startDate.toString(),
									endDate.toString());
					System.out.println("payList" + payList.size());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForSuperAdminGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.organization)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForOrganizationExportDownloadGatewayWisexl(gatewayName, fkID,
									startDate.toString(), endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.admin)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, fkID, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.empUser)) {
					long adminid = employeeUserRepo.getAdminId(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, adminid, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.user)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForUserExportDownloadGatewayWisexl(gatewayName, fkID, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}

			}
			if (duration.equals("lastWeek")) {
				LocalDate today = LocalDate.now();
				LocalDate endDate = today.minusDays(1);// yesterday
				LocalDate startDate = today.minusDays(7);
				// System.out.println("today"+today);
				System.out.println("startDate" + startDate);
				System.out.println("startDate" + endDate);

				// ,startDate.toString(),endDate.toString()
				if (role.equals(CommonConstants.superAdmin)) {
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForSuperAdminExportDownloadGatewayWisexl(gatewayName, startDate.toString(),
									endDate.toString());
					System.out.println("payList" + payList.size());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForSuperAdminGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.organization)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForOrganizationExportDownloadGatewayWisexl(gatewayName, fkID,
									startDate.toString(), endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.admin)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, fkID, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.empUser)) {
					long adminid = employeeUserRepo.getAdminId(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, adminid, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.user)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForUserExportDownloadGatewayWisexl(gatewayName, fkID, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}

			}
			if (duration.equals("today")) {
				LocalDate today = LocalDate.now();
				if (role.equals(CommonConstants.superAdmin)) {
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForSuperAdminExportDownloadGatewayWisexxl(gatewayName, today.toString());
					System.out.println("payList" + payList.size());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForSuperAdminGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.organization)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForOrganizationExportDownloadGatewayWisexxl(gatewayName, fkID,
									today.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.admin)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexxl(gatewayName, fkID, today.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.empUser)) {
					long adminid = employeeUserRepo.getAdminId(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexxl(gatewayName, adminid, today.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.user)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForUserExportDownloadGatewayWisexxl(gatewayName, fkID, today.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = TagReportForSuperAdminHelper
								.tagExcelForAdminAndUserGatewatWise(payList);
						return in;
					}
				}

			}
		} else {
			if (role.equals(CommonConstants.superAdmin)) {
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForSuperAdminExportDownloadGatewayWisexl(gatewayName, fromDate, toDate);
				System.out.println("payList" + payList.size());
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForSuperAdminGatewatWise(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForOrganizationExportDownloadGatewayWisexl(gatewayName, fkID, fromDate, toDate);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserGatewatWise(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, fkID, fromDate, toDate);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserGatewatWise(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, adminid, fromDate, toDate);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserGatewatWise(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForUserExportDownloadGatewayWisexl(gatewayName, fkID, fromDate, toDate);
				if (payList.size() > 0) {
					ByteArrayInputStream in = TagReportForSuperAdminHelper.tagExcelForAdminAndUserGatewatWise(payList);
					return in;
				}
			}
		}

		return null;
	}

	/**
	 * load All Tracking Data PDFGatewatWise
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @param fromDate
	 * @param toDate
	 * @param duration
	 * @return InputStream
	 * @throws ParseException
	 */

	public InputStream loadAllTrackingDataPDFGatewatWise(String gatewayName, String fkUserId, String role,
			String fromDate, String toDate, String duration) throws ParseException {
		if (duration != null) {
			if (duration.equals("lastMonth")) {
				LocalDate today = LocalDate.now();
				LocalDate endDate = today.minusDays(1);// yesterday
				LocalDate startDate = today.minusDays(30);
				// System.out.println("today"+today);
				System.out.println("startDate" + startDate);
				System.out.println("startDate" + endDate);

				if (role.equals(CommonConstants.superAdmin)) {
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForSuperAdminExportDownloadGatewayWisexl(gatewayName, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.organization)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForOrganizationExportDownloadGatewayWisexl(gatewayName, fkID,
									startDate.toString(), endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.admin)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, fkID, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.empUser)) {
					long adminid = employeeUserRepo.getAdminId(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, adminid, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.user)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForUserExportDownloadGatewayWisexl(gatewayName, fkID, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}

			}
			if (duration.equals("lastWeek")) {
				LocalDate today = LocalDate.now();
				LocalDate endDate = today.minusDays(1);// yesterday
				LocalDate startDate = today.minusDays(7);
				// System.out.println("today"+today);
				System.out.println("startDate" + startDate);
				System.out.println("startDate" + endDate);

				if (role.equals(CommonConstants.superAdmin)) {
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForSuperAdminExportDownloadGatewayWisexl(gatewayName, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.organization)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForOrganizationExportDownloadGatewayWisexl(gatewayName, fkID,
									startDate.toString(), endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.admin)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, fkID, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.empUser)) {
					long adminid = employeeUserRepo.getAdminId(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, adminid, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.user)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForUserExportDownloadGatewayWisexl(gatewayName, fkID, startDate.toString(),
									endDate.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
			}
			if (duration.equals("today")) {
				LocalDate today = LocalDate.now();
				if (role.equals(CommonConstants.superAdmin)) {
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForSuperAdminExportDownloadGatewayWisexxl(gatewayName, today.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.organization)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForOrganizationExportDownloadGatewayWisexxl(gatewayName, fkID,
									today.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.admin)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexxl(gatewayName, fkID, today.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.empUser)) {
					long adminid = employeeUserRepo.getAdminId(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForAdminExportDownloadGatewayWisexxl(gatewayName, adminid, today.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}
				if (role.equals(CommonConstants.user)) {
					Long fkID = Long.parseLong(fkUserId);
					List<ResponseTrackingListBean> payList = trackingRepo
							.getTagReportForUserExportDownloadGatewayWisexxl(gatewayName, fkID, today.toString());
					if (payList.size() > 0) {
						ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
						return in;
					}
				}

			}

		} else {
			if (role.equals(CommonConstants.superAdmin)) {
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForSuperAdminExportDownloadGatewayWisexl(gatewayName, fromDate, toDate);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForOrganizationExportDownloadGatewayWisexl(gatewayName, fkID, fromDate, toDate);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, fkID, fromDate, toDate);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForAdminExportDownloadGatewayWisexl(gatewayName, adminid, fromDate, toDate);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<ResponseTrackingListBean> payList = trackingRepo
						.getTagReportForUserExportDownloadGatewayWisexl(gatewayName, fkID, fromDate, toDate);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFGatewatWise(payList);
					return in;
				}
			}
		}

		return null;
	}

	/**
	 * get Tracking Report To View
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ResponseTrackingListBean
	 */
	public List<ResponseTrackingListBean> getTrackingReportToView(String tagName, String fkUserId, String role,
			String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForSuperAdminExportDownload(tagName,
					category);
			return payList;
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForAdminExportDownload(tagName, fkID,
					category);
			return payList;
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			List<ResponseTrackingListBean> payList = trackingRepo.getTagReportForUserExportDownload(tagName, fkID,
					category);
			return payList;
		}
		return null;
	}

	/**
	 * get Payload Report To View Duration
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param start
	 * @param end
	 * @param fkUserId
	 * @param role
	 * @return ResponseTrackingListBean
	 * @throws ParseException
	 */
	public List<ResponseTrackingListBean> getPayloadReportToViewDuration(String tagName, String start, String end,
			String fkUserId, String role) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = formatter.parse(start);
		Date toDate = formatter.parse(end);

		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTagReportForSuperAdminExportDownloadBetweenDate(start, end, tagName);
		}

		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagReportForAdminExportDownloadBetweenDate(start, end, tagName, id);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagReportForUserExportDownloadBetweenDate(start, end, tagName, id);
		}
		return null;
	}

	/**
	 * get Tracking Report To View Gatewaywise TagReport
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @return ResponseTrackingListBean
	 */
	public List<ResponseTrackingListBean> getTrackingReportToViewGatewaywiseTagReport(String gatewayName,
			String fkUserId, String role) {
		Long id = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTagReportForSuperAdminExportDownloadGatewayWise(gatewayName);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagReportForAdminExportDownloadGatewayWise(gatewayName, id);
		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagReportForUserExportDownloadGatewayWise(gatewayName, id);
		}
		return null;
	}

	/**
	 * get Tracking Report To View Gatewaywise
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @return ResponseTrackingListBean
	 */
	public List<ResponseTrackingListBean> getTrackingReportToViewGatewaywise(String gatewayName, String tagName,
			String fkUserId, String role) {
		Long id = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTagReportForSuperAdminExportDownloadGatewayWiseTag(gatewayName, tagName);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagReportForAdminExportDownloadGatewayWiseTag(gatewayName, tagName, id);
		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagReportForUserExportDownloadGatewayWiseTag(gatewayName, tagName, id);
		}
		return null;
	}

	/**
	 * get Tag Report To View Duration For Gateway
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param start
	 * @param end
	 * @param fkUserId
	 * @param role
	 * @return ResponseTrackingListBean
	 * @throws ParseException
	 */
	public List<ResponseTrackingListBean> getTagReportToViewDurationForGateway(String gatewayName, String start,
			String end, String fkUserId, String role) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = formatter.parse(start);
		Date toDate = formatter.parse(end);

		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getGatewayReportForSuperAdminExportDownloadBetweenDate(fromDate, toDate, gatewayName);
		}

		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getGatewayReportForAdminExportDownloadBetweenDate(fromDate, toDate, gatewayName, id);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getGatewayReportForUserExportDownloadBetweenDate(fromDate, toDate, gatewayName, id);
		}
		return null;
	}

	/**
	 * get Payload Report To View Duration Todays
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param date
	 * @param fkUserId
	 * @param role
	 * @return ResponseTrackingListBean
	 */
	public List<ResponseTrackingListBean> getPayloadReportToViewDurationTodays(String tagName, Date date,
			String fkUserId, String role) {
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTagReportForSuperAdminExportDownloadTodaysDate(date, tagName);
		}

		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagReportForAdminExportDownloadTodaysDate(date, tagName, id);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagReportForUserExportDownloadTodaysDate(date, tagName, id);
		}
		return null;
	}

	/**
	 * get TagReport To View Todays Gateway
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param todayDate
	 * @param fkUserId
	 * @param role
	 * @return ResponseTrackingListBean
	 */
	public List<ResponseTrackingListBean> getTagReportToViewTodaysGateway(String gatewayName, Date todayDate,
			String fkUserId, String role) {
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getGatewayReportForSuperAdminExportDownloadTodaysDate(todayDate, gatewayName);
		}

		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getGatewayReportForAdminExportDownloadTodaysDate(todayDate, gatewayName, id);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getGatewayReportForUserExportDownloadTodaysDate(todayDate, gatewayName, id);
		}
		return null;
	}

//..........................................pagination..............................................
	/**
	 * get Payload Report To View Duration With Pagination
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param fromDate
	 * @param toDate
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return ResponseTrackingListBean
	 * @throws ParseException
	 */
	public Page<ResponseTrackingListBean> getPayloadReportToViewDurationWithPagination(String tagName, String fromDate,
			String toDate, String fkUserId, String role, String category, Pageable pageable) throws ParseException {

		System.out.println(fromDate + "and" + toDate);
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			System.out.println("tagName" + tagName);

			Page<ResponseTrackingListBean> bean = trackingRepo
					.getTagReportForSuperAdminExportDownloadBetweenDateWithPagination(fromDate, toDate, tagName,
							category, pageable);
			System.out.println("bean" + bean.getTotalElements());
			return bean;

		}

		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getTagReportForOrganizationExportDownloadBetweenDateWithPagination(fromDate, toDate,
					tagName, id, category, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagReportForAdminExportDownloadBetweenDateWithPagination(fromDate, toDate, tagName,
					id, category, pageable);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagReportForUserExportDownloadBetweenDateWithPagination(fromDate, toDate, tagName,
					id, category, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getTagReportForAdminExportDownloadBetweenDateWithPagination(fromDate, toDate, tagName,
					adminid, category, pageable);
		}

		return null;
	}

	/**
	 * get Payload Report To View Todays With Pagination
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param today
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return ResponseTrackingListBean
	 */
	public Page<ResponseTrackingListBean> getPayloadReportToViewTodaysWithPagination(String tagName, String today,
			String fkUserId, String role, String category, Pageable pageable) {
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTagReportForSuperAdminExportDownloadTodaysDateWithPagination(today, tagName,
					category, pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getTagReportForOrganizationExportDownloadTodaysDateWithPagination(today, tagName, id,
					category, pageable);
		}

		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagReportForAdminExportDownloadTodaysDateWithPagination(today, tagName, id, category,
					pageable);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagReportForUserExportDownloadTodaysDateWithPagination(today, tagName, id, category,
					pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getTagReportForAdminExportDownloadTodaysDateWithPagination(today, tagName, adminid,
					category, pageable);
		}
		return null;

	}
///////////////////////////////////pagination 

	/**
	 * get All Tracking Data For Filter Userwise Pagination
	 * 
	 * @author Pratik Chaudhari
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @return ResponseTrackingListBean
	 */
	public Page<ResponseTrackingListBean> getAllTrackingDataForFilterUserwisePagination(String tagName, String fkUserId,
			String role, Pageable pageable) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTrackingListForFilterSuperadminwiseWithPagination(tagName, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTrackingListForFilterAdminwiseWithPaginatio(tagName, userId, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTrackingListForFilterUserwiseWithPaginatio(tagName, userId, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getTrackingListForFilterAdminwiseWithPaginatio(tagName, adminid, pageable);
		}
		return null;
	}

	/**
	 * get Tag Report To View Duration For Gateway With Pagination
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param fromDate
	 * @param toDate
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return ResponseTrackingListBean
	 * @throws ParseException
	 */
	public Page<ResponseTrackingListBean> getTagReportToViewDurationForGatewayWithPagination(String gatewayName,
			String fromDate, String toDate, String fkUserId, String role, String category, Pageable pageable)
			throws ParseException {
//		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date fromDate = formatter.parse(start);
//		Date toDate = formatter.parse(end);
//		

		System.out.println("$$$$$$$$$" + fromDate + "to" + toDate);
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getGatewayReportForSuperAdminExportDownloadBetweenDateWithPagination(fromDate, toDate,
					gatewayName, category, pageable);
		}

		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getGatewayReportForOrganizationExportDownloadBetweenDateWithPagination(fromDate, toDate,
					gatewayName, id, category, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getGatewayReportForAdminExportDownloadBetweenDateWithPagination(fromDate, toDate,
					gatewayName, id, category, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getGatewayReportForAdminExportDownloadBetweenDateWithPagination(fromDate, toDate,
					gatewayName, adminid, category, pageable);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getGatewayReportForUserExportDownloadBetweenDateWithPagination(fromDate, toDate,
					gatewayName, id, category, pageable);
		}
		return null;

	}

	/**
	 * get Tag Report To View Todays Gateway With Pagination
	 * 
	 * @author Pratik Chaudhari
	 * @param gatewayName
	 * @param date
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return ResponseTrackingListBean
	 */
	public Page<ResponseTrackingListBean> getTagReportToViewTodaysGatewayWithPagination(String gatewayName, String date,
			String fkUserId, String role, String category, Pageable pageable) {
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getGatewayReportForSuperAdminExportDownloadTodaysDateWithPagination(date, gatewayName,
					category, pageable);
		}

		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getGatewayReportForOrganizationExportDownloadTodaysDateWithPagination(date, gatewayName,
					id, category, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getGatewayReportForAdminExportDownloadTodaysDateWithPagination(date, gatewayName, id,
					category, pageable);
		}

		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getGatewayReportForAdminExportDownloadTodaysDateWithPagination(date, gatewayName,
					adminid, category, pageable);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getGatewayReportForUserExportDownloadTodaysDateWithPagination(date, gatewayName, id,
					category, pageable);
		}
		return null;

	}

	/**
	 * get way Collection Tagwise
	 * 
	 * @author Pratik Chaudhari
	 * @param entity
	 */
	public void getwayCollectionTagwise(AssetTrackingEntity entity) {
		gatewayAndTagCollection dbdata = null;

		try {
			dbdata = gatewayAndTagRepo.getdbdata(entity.getUniqueNumberMacId());
		} catch (Exception e) {
			System.out.println(e);
		}

		gatewayAndTagCollection entity1 = new gatewayAndTagCollection();

		entity1.setAssetTagName(entity.getAssetTagName());
		entity1.setAssetGatewayName(entity.getAssetGatewayName());
		entity1.setgSrNo(entity.getgSrNo());
		entity1.setAssetGatewayMac_Id(entity.getAssetGatewayMac_Id());
		entity1.setBarcodeSerialNumber(entity.getBarcodeSerialNumber());
		entity1.setUniqueNumberMacId(entity.getUniqueNumberMacId());
		// entity.setDate(date1);///////
		entity1.setDate(entity.getDate());
		entity1.setTime(entity.getTime());
		entity1.setEntryTime(entity.getEntryTime());
		entity1.setExistTime(entity.getExistTime());// adhi null set hota
		entity1.setTagEntryLocation(entity.getTagEntryLocation());
		entity1.setTagExistLocation(entity.getTagExistLocation());// dbLocation
		entity1.setLatitude(entity.getLatitude());
		entity1.setLongitude(entity.getLongitude());
		entity1.setTagCategoty(entity.getTagCategoty());
		entity1.setDispatchTime(entity.getDispatchTime());
		entity1.setImeiNumber(entity.getImeiNumber());
		entity1.setBattryPercentage(entity.getBattryPercentage());
		if (dbdata != null) {

			if (dbdata.getTagEntryLocation().equals(entity1.getTagEntryLocation())) {
				System.out.println("same as privious location:" + entity.getTagEntryLocation() + "old location:"
						+ dbdata.getTagEntryLocation());
				return;
			}

			gatewayAndTagRepo.deleteByTagName(entity.getAssetTagName());
			gatewayAndTagRepo.save(entity1);

		} else {
			gatewayAndTagRepo.save(entity1);
		}
	}

	/**
	 * virtual Tracking Details Update
	 * 
	 * @param entity
	 */
	public void virtualTrackingDetailsUpdate(AssetTrackingEntity entity) {
		VirtualTrackingDetails vobj = new VirtualTrackingDetails();
		String id = null;
		try {
			id = virtualtrackingdetailsRepo.getIdByTagName(entity.getAssetTagName());
			if (id == null) {
				id = "N/A";
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		if (id.equals("N/A")) {
			vobj.setAssetGatewayMac_Id(entity.getAssetGatewayMac_Id());
			vobj.setAssetGatewayName(entity.getAssetGatewayName());
			vobj.setAssetTagName(entity.getAssetTagName());
			vobj.setBarcodeSerialNumber(entity.getBarcodeSerialNumber());
			vobj.setBattryPercentage(entity.getBattryPercentage());
			String timezone = tagRepository.getTimeZoneFromTagName(entity.getAssetTagName());

			ZoneId zid = ZoneId.of(timezone);

			LocalDateTime currentdate = LocalDateTime.now(zid);

			vobj.setCurrentdate(currentdate.toString());
			vobj.setDispatchTime(entity.getDispatchTime());
			vobj.setEntryTime(entity.getEntryTime());
			vobj.setUniqueNumberMacId(entity.getUniqueNumberMacId());
			vobj.setExistTime(entity.getExistTime());
			vobj.setgSrNo(entity.getgSrNo());
			vobj.setImeiNumber(entity.getImeiNumber());
			vobj.setLatitude(entity.getLatitude());
			vobj.setLb1(entity.getLb1());
			vobj.setLb2(entity.getLb2());
			vobj.setLb3(entity.getLb3());
			vobj.setLb4(entity.getLb4());
			vobj.setLb5(entity.getLb5());
			vobj.setLb6(entity.getLb6());
			vobj.setLb7(entity.getLb7());
			vobj.setLb8(entity.getLb8());
			vobj.setLb9(entity.getLb9());
			vobj.setLb10(entity.getLb10());
			vobj.setLb11(entity.getLb11());
			vobj.setLb12(entity.getLb12());
			vobj.setLb13(entity.getLb13());
			vobj.setLb14(entity.getLb14());
			vobj.setLb15(entity.getLb15());
			virtualtrackingdetailsRepo.save(vobj);
			System.out.println("save");

		} else {
			vobj.setAssetGatewayMac_Id(entity.getAssetGatewayMac_Id());
			vobj.setTrackingId(Long.parseLong(id));
			vobj.setAssetGatewayName(entity.getAssetGatewayName());
			vobj.setAssetTagName(entity.getAssetTagName());
			vobj.setUniqueNumberMacId(entity.getUniqueNumberMacId());
			vobj.setBarcodeSerialNumber(entity.getBarcodeSerialNumber());
			vobj.setBattryPercentage(entity.getBattryPercentage());
			String timezone = tagRepository.getTimeZoneFromTagName(entity.getAssetTagName());

			ZoneId zid = ZoneId.of(timezone);

			LocalDateTime currentdate = LocalDateTime.now(zid);
			System.out.println("TimeZOne" + currentdate);
			vobj.setCurrentdate(currentdate.toString());
			vobj.setDispatchTime(entity.getDispatchTime());
			vobj.setEntryTime(entity.getEntryTime());

			vobj.setExistTime(entity.getExistTime());
			vobj.setgSrNo(entity.getgSrNo());
			vobj.setImeiNumber(entity.getImeiNumber());
			vobj.setLatitude(entity.getLatitude());
			vobj.setLb1(entity.getLb1());
			vobj.setLb2(entity.getLb2());
			vobj.setLb3(entity.getLb3());
			vobj.setLb4(entity.getLb4());
			vobj.setLb5(entity.getLb5());
			vobj.setLb6(entity.getLb6());
			vobj.setLb7(entity.getLb7());
			vobj.setLb8(entity.getLb8());
			vobj.setLb9(entity.getLb9());
			vobj.setLb10(entity.getLb10());
			vobj.setLb11(entity.getLb11());
			vobj.setLb12(entity.getLb12());
			vobj.setLb13(entity.getLb13());
			vobj.setLb14(entity.getLb14());
			vobj.setLb15(entity.getLb15());
			virtualtrackingdetailsRepo.save(vobj);
			System.out.println("update");

		}

	}

	/**
	 * string To convert LocalDateAndTime
	 * 
	 * @param strDate
	 * @return LocalDateTime
	 */
	public LocalDateTime stringToLocalDateAndTime(String strDate) {
		String strDate1 = strDate.toString().replace(" ", "T");
		LocalDateTime localdate = LocalDateTime.parse(strDate1);
		return localdate;
	}

	/**
	 * sort Update And StoredTime
	 * 
	 * @param befourAfterRecordList
	 * @param assetCurrentList
	 * @param afterRecordList
	 */
	public void sortUpdateAndStoredTime(List<AssetTrackingEntity> befourAfterRecordList,
			AssetTrackingDto assetCurrentList, List<AssetTrackingEntity> afterRecordList) {
		for (int i = 0; i < befourAfterRecordList.size(); i++) {
			if (stringToLocalDateAndTime(befourAfterRecordList.get(i).getTime())
					.isBefore(stringToLocalDateAndTime(assetCurrentList.getEntryTime()))) {
				trackingRepo.updateRecordOnId(assetCurrentList.getEntryTime(),
						befourAfterRecordList.get(i).getTrackingId()); // update befoue record
			} else {
				if (befourAfterRecordList.get(i).getTime().equals(afterRecordList.get(0).getTime())) {
					trackingRepo.updateUpdateTimeOnId(
							afterRecordList.get(0).getTime().substring(0, 11) + "" + "00:00:00",
							befourAfterRecordList.get(i).getTrackingId());// set update time 00:00:00
					trackingRepo.updateEntryTimeOnId(afterRecordList.get(0).getTime(),
							befourAfterRecordList.get(i).getTrackingId());// update after record
				} else {
					trackingRepo.updateEntryTimeOnId(afterRecordList.get(0).getTime(),
							befourAfterRecordList.get(i).getTrackingId());// update after record
				}

			}

		}

	}

	/**
	 * updated Store TimeUpdate
	 * 
	 * @param privious1
	 * @param assetList
	 */
	public void updatedStoreTimeUpdate(List<AssetTrackingEntity> privious1, AssetTrackingDto assetList) {
		for (int i = 0; i < privious1.size(); i++) {
			if (stringToLocalDateAndTime(privious1.get(i).getTime())
					.isBefore(stringToLocalDateAndTime(assetList.getEntryTime()))) {
				trackingRepo.updateUpdatedTimeOnEntryTime(assetList.getEntryTime(), privious1.get(i).getTrackingId());

			} else {

				trackingRepo.setupdateTimeZeroAndCreateNew(privious1.get(i).getTime(),
						privious1.get(i).getTime().substring(0, 11) + "" + "00:00:00",
						privious1.get(i).getTrackingId());// update time is grater than exittime then create new entry
			}

		}

	}

	/**
	 * use to upload list of json in db
	 * 
	 * @author Pratik Chaudhari
	 * @param requestData 
	 * @return String
	 * @throws ParseException
	 */

	public String addTrackingDetailsBulk(List<AssetTrackingDto> requestData) throws ParseException {

		int index = 0;
		List<AssetTrackingDto> assetList = new ArrayList<AssetTrackingDto>();
		Date dispatcheddate;
		assetList = requestData;
		int icnt = 0, icnt2 = 0;
		int count = 0;
		int wrongdatapos = 0;
		System.out.println("%%%S$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ize%%of&&List:" + assetList.size());
		for (index = 0; index <= assetList.size() - 1; index++) {
			count++;

			if (assetList.get(index).getLatitude() == null && assetList.get(index).getLongitude() == null
					&& assetList.get(index).getImeiNumber() == null) {

				assetList.get(index).setLatitude("0");
				assetList.get(index).setLongitude("0");
				assetList.get(index).setImeiNumber("Gatway_REQUEST_" + System.currentTimeMillis());
			} else {
				assetList.get(index).setAssetGatewayName("GPS_REQUEST_" + System.currentTimeMillis());
			}

			AssetTag imeiList = tagRepository.findByAssetIMEINumber(assetList.get(index).getImeiNumber());
			AssetTag barcodeList = tagRepository.findByAssetBarcodeSerialNumber(assetList.get(index).getbSN());
			AssetTag uniqueCodeList = tagRepository.findByAssetUniqueCodeMacId(assetList.get(index).gettMacId());

			AssetGateway gateway = gatewayRepo.findByGatewayMacId(assetList.get(index).getgMacId());

			String getLocationFromGateway = null;

			String tagName = null;

			AssetTrackingEntity entity = new AssetTrackingEntity();
			AssetTrackingEntity entity1 = new AssetTrackingEntity();
			AssetTrackingEntity entity2 = new AssetTrackingEntity();

			if (gateway != null) {
				String gatewayName = gateway.getGatewayName();
				String gatewayMacID = assetList.get(index).getgMacId();

				String gatewayscerialnumber = assetList.get(index).getgSrNo();

				try {
					tagName = barcodeList.getAssetTagName();
				} catch (Exception e) {
					wrongdatapos = count;
					System.out.println("wrong Tag::-" + assetList.get(index));
					System.out.println(e);
				}

				java.util.Date date = new Date();
				SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd");
				String datenew = formatter4.format(date);
				System.out.println("@@@@@@dateformate@@@@@@@@" + datenew);

				if (barcodeList != null && uniqueCodeList != null) //
				{
					getLocationFromGateway = gatewayRepo.getGatewayLocation(assetList.get(index).getgMacId());
					AssetTrackingEntity dbData = null;
					System.out.println("Tag gateway location..." + getLocationFromGateway);
					try {
						dbData = trackingRepo.findByUniqueNumberMacId(assetList.get(index).gettMacId());
						if (!dbData.getExistTime().equals("N/A")) {
							dbData = trackingRepo.findByUniqueNumberMacIdxxl(assetList.get(index).gettMacId(),
									dbData.getExistTime());
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					System.out.println(dbData);

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date1 = new java.util.Date();
					ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
					System.out.println("##############now:" + now);
					formatter.setTimeZone(TimeZone.getTimeZone("IST"));
					String date2 = formatter.format(new Date());
					System.out.println("####date2" + date2);
					LocalDateTime dateTime = LocalDateTime.now();

					Date currentTime = new Date();
					DateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");

					String currenttime = formatter1.format(currentTime);

					String entryTime = assetList.get(index).getEntryTime();

					if (dbData != null) {
						String dbGateway = dbData.getAssetGatewayName();
						String dbMac = dbData.getAssetGatewayMac_Id();
						String dbLocation = dbData.getTagEntryLocation();
						String dbTag = dbData.getAssetTagName();
						String dbdate = dbData.getDate();

						float tagdist = dbData.getTagDist();
						String dbtime = dbData.getTime();
						String dbentrytime = dbData.getEntryTime();
						Integer batstat = dbData.getBattryPercentage();

						entity.setAssetTagName(tagName);
						entity.setAssetGatewayName(gatewayName);
						entity.setgSrNo(assetList.get(index).getgSrNo());
						entity.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
						entity.setBarcodeSerialNumber(assetList.get(index).getbSN());
						entity.setUniqueNumberMacId(assetList.get(index).gettMacId());////

						entity.setDate(datenew);
						// 2022-12-16 13:41:41
						String tstamp = assetList.get(index).getEntryTime().substring(11);
						entity.setTimestamp(tstamp);
						System.out.println(assetList.get(index).getEntryTime() + " EntryTime");
						System.out.println(assetList.get(index).getEntryTime().substring(11) + " EntryTimesubstring");
						entity.setTime(currenttime);
						entity.setEntryTime(assetList.get(index).getEntryTime());
						entity.setExistTime(assetList.get(index).getEntryTime());
						entity.setTagEntryLocation(getLocationFromGateway);
						entity.setTagExistLocation(dbLocation);
						entity.setLatitude(assetList.get(index).getLatitude());
						entity.setLongitude(assetList.get(index).getLongitude());
						entity.setTagCategoty(assetList.get(index).getTagCategoty());
						entity.setDispatchTime(entryTime.toString());
						entity.setBattryPercentage(assetList.get(index).getBatStat());
						entity.setTagDist(assetList.get(index).getTagDist());
						entity.setLb1(assetList.get(index).getLb1());
						entity.setLb2(assetList.get(index).getLb2());
						entity.setLb3(assetList.get(index).getLb3());
						entity.setLb4(assetList.get(index).getLb4());
						entity.setLb5(assetList.get(index).getLb5());
						entity.setLb6(assetList.get(index).getLb6());
						entity.setLb7(assetList.get(index).getLb7());
						entity.setLb8(assetList.get(index).getLb8());
						entity.setLb9(assetList.get(index).getLb9());
						entity.setLb10(assetList.get(index).getLb10());
						entity.setLb11(assetList.get(index).getLb11());
						entity.setLb12(assetList.get(index).getLb12());
						entity.setLb13(assetList.get(index).getLb13());
						entity.setLb14(assetList.get(index).getLb14());
						entity.setLb15(assetList.get(index).getLb15());

		List<AssetTrackingEntity> dublicateEntry = trackingRepo.getDublicateEntry(assetList.get(index).getgMacId(), assetList.get(index).gettMacId(),assetList.get(index).getEntryTime(), assetList.get(index).getTagDist());
						if (dublicateEntry.size() > 0) {
							System.out.println("dublicate from hardware Side");
						} else {
							String centryTime = assetList.get(index).getEntryTime().toString().replace(" ", "T");
							String dbentryTime = dbData.getEntryTime().toString().replace(" ", "T");
							String updateTime1 = (dbData.getTime().substring(0, 11) + "" + dbData.getTimestamp()).toString().replace(" ", "T");
							System.out.println("updateTime1" + updateTime1);

							String cUpdateTime = assetList.get(index).getEntryTime().toString().replace(" ", "T");
							LocalDateTime currentExitTime1 = null;
							LocalDateTime currententryTime = LocalDateTime.parse(centryTime);
							LocalDateTime dblastentryTime = LocalDateTime.parse(dbentryTime);

							LocalDateTime dbupdateTime = LocalDateTime.parse(updateTime1);
							LocalDateTime currentUpdateTime = LocalDateTime.parse(cUpdateTime);
							String RecoedType1 = "N/A";
							String RecoedType2 = "N/A";
							if (currententryTime.isBefore(dblastentryTime)) {
								RecoedType1 = "Apllicable";
							}
							String cExitTime1 = dbData.getExistTime().toString().replace(" ", "T");
							try {
								currentExitTime1 = LocalDateTime.parse(cExitTime1);

								if (currentExitTime1.equals(null)) {
									RecoedType2 = "Apllicable";
								}

								if (currentUpdateTime.isAfter(currentExitTime1)) {
									RecoedType2 = "Apllicable";
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

							if (dbGateway.equals(gatewayName) && dbMac.equals(gatewayMacID) && dbTag.equals(tagName)
									&& batstat.equals(assetList.get(index).getBatStat())&& dbData.getBarcodeSerialNumber().equals(assetList.get(index).getbSN())
									&& dbData.getLb1().equals(assetList.get(index).getLb1())&& dbData.getLb2().equals(assetList.get(index).getLb2())
									&& dbData.getLb3().equals(assetList.get(index).getLb3())&& dbData.getLb4().equals(assetList.get(index).getLb4())
									&& dbData.getLb5().equals(assetList.get(index).getLb5())&& dbData.getLb6().equals(assetList.get(index).getLb6())
									&& dbData.getLb7().equals(assetList.get(index).getLb7())&& dbData.getLb8().equals(assetList.get(index).getLb8())
									&& dbData.getLb9().equals(assetList.get(index).getLb9())&& dbData.getLb10().equals(assetList.get(index).getLb10())
									&& dbData.getLb11().equals(assetList.get(index).getLb11())&& dbData.getLb12().equals(assetList.get(index).getLb12())
									&& dbData.getLb13().equals(assetList.get(index).getLb13())&& dbData.getLb14().equals(assetList.get(index).getLb14())
									&& dbData.getLb15().equals(assetList.get(index).getLb15())&& RecoedType1.equals("N/A") && RecoedType2.equals("N/A"))// &&tagdist==assetList.get(index).getTagDist()
																								// //here any parameter// increment need to add// in the condition// statment
							{
								System.out.println("Requested tracking details already present in database...");
								virtualTrackingDetailsUpdate(entity);
								icnt2++;
							} else {

								String strdbData2 = null;
								// List<AssetTrackingEntity>
								// assetlist1=trackingRepo.getBefourRecords(assetList.get(index).getEntryTime(),assetList.get(index).gettMacId());

								List<AssetTrackingEntity> assetlist2 = null;
								AssetTrackingEntity dbData2 = null;
								try {
									assetlist2 = trackingRepo.getAfterRecords(assetList.get(index).getEntryTime(),assetList.get(index).gettMacId());
									dbData2 = trackingRepo.dbDataxl(assetList.get(index).getgMacId(),assetList.get(index).gettMacId());

									if (dbData2 == null) {strdbData2 = "N/A";}

								} catch (Exception e) {

									System.out.println("assetlist2" + assetlist2.get(0));
									System.out.println("assetlist2" + assetlist2.get(0).getEntryTime());
								}
								LocalDateTime currentExitTime = null;
								try {
									if (strdbData2 == null) {
										String cExitTime = dbData2.getExistTime().toString().replace(" ", "T");
										currentExitTime = LocalDateTime.parse(cExitTime);
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								int updateTimegater = 0;
								int iUpdate = 0;
								int sameGatewayUpdated = 0;

								System.out.println(currententryTime + " currententryTime");
								System.out.println(dblastentryTime + " dblastentryTime");
								int itent = 0;
								String afterStoreTime = null;
								if (currententryTime.isBefore(dblastentryTime)|| currententryTime.isBefore(dbupdateTime)) // use to manage store data// dbupdateTime
								{

									try {
										itent++;

										AssetTrackingEntity firstEntry = trackingRepo.gettrackingDetailsOnExitTime(assetlist2.get(0).getEntryTime(), assetList.get(index).gettMacId());

										if (firstEntry.getAssetGatewayMac_Id().equals(assetList.get(index).getgMacId())) // update pasttime same gateway entry
										{
											sameGatewayUpdated++;
											 entity2.setTrackingId(firstEntry.getTrackingId());
											entity2.setEntryTime(firstEntry.getEntryTime());
											entity2.setAssetTagName(firstEntry.getAssetTagName());
											entity2.setAssetGatewayName(firstEntry.getAssetGatewayName());
											entity2.setgSrNo(assetList.get(index).getgSrNo());
											entity2.setAssetGatewayMac_Id(firstEntry.getAssetGatewayMac_Id());
											entity2.setBarcodeSerialNumber(firstEntry.getBarcodeSerialNumber());
											entity2.setUniqueNumberMacId(firstEntry.getUniqueNumberMacId());
											// entity.setDate(date1);///////
											entity2.setDate(datenew);
											entity2.setTimestamp(firstEntry.getEntryTime().substring(11));
											System.out.println(firstEntry.getEntryTime() + " EntryTime");
											System.out.println(
													firstEntry.getEntryTime().substring(11) + " EntryTimesubstring");
											entity2.setTime(assetList.get(index).getEntryTime().substring(0, 11) + ""+ "00:00:00");//get(index).getEntryTime().substring(0, 11) + ""+ "00:00:00"
											// entity.setEntryTime(assetList.get(index).getEntryTime());
											entity2.setExistTime(firstEntry.getExistTime());// adhi null set hota

											entity2.setTagEntryLocation(firstEntry.getTagEntryLocation());
											entity2.setTagExistLocation(firstEntry.getTagExistLocation());// dbLocation
											entity2.setLatitude(firstEntry.getLatitude());
											entity2.setLongitude(firstEntry.getLongitude());
											entity2.setTagCategoty(barcodeList.getAssetTagCategory());
											Date cdate1 = new Date();
											String dispatchtime = cdate1.toString();
											entity2.setDispatchTime(dispatchtime);
											entity2.setImeiNumber(firstEntry.getImeiNumber());
											entity2.setBattryPercentage(assetList.get(index).getBatStat());
											entity2.setTagDist(assetList.get(index).getTagDist());

											entity2.setLb1(assetList.get(index).getLb1());
											entity2.setLb2(assetList.get(index).getLb2());
											entity2.setLb3(assetList.get(index).getLb3());
											entity2.setLb4(assetList.get(index).getLb4());
											entity2.setLb5(assetList.get(index).getLb5());
											entity2.setLb6(assetList.get(index).getLb6());
											entity2.setLb7(assetList.get(index).getLb7());
											entity2.setLb8(assetList.get(index).getLb8());
											entity2.setLb9(assetList.get(index).getLb9());
											entity2.setLb10(assetList.get(index).getLb10());
											entity2.setLb11(assetList.get(index).getLb11());
											entity2.setLb12(assetList.get(index).getLb12());
											entity2.setLb13(assetList.get(index).getLb13());
											entity2.setLb14(assetList.get(index).getLb14());
											entity2.setLb15(assetList.get(index).getLb15());
											virtualTrackingDetailsUpdate(entity2);
											// trackingRepo.save(entity);

										}

										if (!firstEntry.getAssetGatewayMac_Id().equals(assetList.get(index).getgMacId())) {
											trackingRepo.updateExitTime(assetList.get(index).getEntryTime(),firstEntry.getTrackingId()); // update entry time in firstEntry
										}
										LocalDateTime updateTime;
										if (!firstEntry.getTime().substring(11).equals("00:00:00")) {
											String updateTimeStr = firstEntry.getTime().toString().replace(" ", "T");
											updateTime = LocalDateTime.parse(updateTimeStr);

										} else {

											String updateTimeStr = firstEntry.getTime().toString().replace(" ", "T");
											updateTime = LocalDateTime.parse(updateTimeStr);
										}

										// assetlist2.get(0).setExistTime("N/A");//h.
										// trackingRepo.save(dbData);

									} catch (Exception e) {
										// TODO: handle exception
									}

									List<AssetTrackingEntity> befourRecordList = null;

									if (currententryTime.isBefore(dbupdateTime))// getBefourRecordxl
									{
										befourRecordList = trackingRepo.getBefourRecordxl(assetList.get(index).getEntryTime(), assetList.get(index).gettMacId(),assetList.get(index).getEntryTime().substring(11));// ,(assetList.get(index).getEntryTime().subSequence(0,// 11)+" 00:00:00")

									} else {
										befourRecordList = trackingRepo.getBefourRecord(assetList.get(index).getEntryTime(), assetList.get(index).gettMacId());// ,(assetList.get(index).getEntryTime().subSequence(0,// 11)+"// 00:00:00")
									}

							if (befourRecordList.size() > 0) {

								System.out.println("befourRecordList.get(0).getEntryTime()"+ befourRecordList.get(0).getEntryTime());

	List<AssetTrackingEntity> befourAfterRecordList = trackingRepo.getBefourAfterRecordList(befourRecordList.get(0).getEntryTime(),assetList.get(index).gettMacId());

List<AssetTrackingEntity> afterRecordList = trackingRepo.getAfterRecordofStoreTime(assetList.get(index).getEntryTime(),assetList.get(index).gettMacId());
				if (befourAfterRecordList.size() > 1) {
					sortUpdateAndStoredTime(befourAfterRecordList, assetList.get(index),afterRecordList);
					assetList.get(index).setExistTime(afterRecordList.get(0).getEntryTime()); // set store data ExitTime
					afterStoreTime = afterRecordList.get(0).getTime();
							itent++;
			}

		} // befourlist>0

	} else {
				int inew = 0;

			try {
				if (currentUpdateTime.isAfter(currentExitTime)) {
											updateTimegater++;

											// List<AssetTrackingEntity> privious1 =
											// trackingRepo.getpriviousforSameGatewayRecords(assetList.get(index).getgMacId(),assetList.get(index).getEntryTime(),assetList.get(index).gettMacId());

List<AssetTrackingEntity> privious = trackingRepo.getpriviousRecords(assetList.get(index).getEntryTime(),assetList.get(index).gettMacId());// get befourrecords of
																						// currentrecord from db
List<AssetTrackingEntity> privious1 = trackingRepo.getSameGatewayUpdatedList(privious.get(0).getEntryTime());

	if (privious1.size() > 1) 
	{
								
		updatedStoreTimeUpdate(privious1, assetList.get(index));
								
	} else {
												
		trackingRepo.updateExitTime(assetList.get(index).getEntryTime(),privious.get(0).getTrackingId());// set privious record exit time as a current current  time entry time
		}

			List<AssetTrackingEntity> afterR = trackingRepo.getAfterRecord(assetList.get(index).getEntryTime(),assetList.get(index).gettMacId());

											entity1.setEntryTime(assetList.get(index).getEntryTime());
											entity1.setAssetTagName(tagName);
											entity1.setAssetGatewayName(gatewayName);
											entity1.setgSrNo(assetList.get(index).getgSrNo());
											entity1.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
											entity1.setBarcodeSerialNumber(assetList.get(index).getbSN());
											entity1.setUniqueNumberMacId(assetList.get(index).gettMacId());
											// entity.setDate(date1);///////
											entity1.setDate(datenew);
											entity1.setTimestamp(assetList.get(index).getEntryTime().substring(11));
											System.out.println(assetList.get(index).getEntryTime() + " EntryTime");
											System.out.println(assetList.get(index).getEntryTime().substring(11)
													+ " EntryTimesubstring");
											entity1.setTime(assetList.get(index).getEntryTime().substring(0, 11) + ""+ "00:00:00");

											if (afterR.size() == 0) {
												entity1.setExistTime("N/A");// adhi null set hota
												inew++;

//									    		dbData.setExistTime(assetList.get(index).getEntryTime());//h.
//												trackingRepo.save(dbData);
											} else {

												entity1.setExistTime(afterR.get(0).getEntryTime());// ===   //ithe ghange kelay 23/03/2023
											}

											entity1.setTagEntryLocation(getLocationFromGateway);
											entity1.setTagExistLocation(getLocationFromGateway);// dbLocation
											entity1.setLatitude(assetList.get(index).getLatitude());
											entity1.setLongitude(assetList.get(index).getLongitude());
											entity1.setTagCategoty(barcodeList.getAssetTagCategory());
											Date cdate1 = new Date();
											String dispatchtime = cdate1.toString();
											entity1.setDispatchTime(dispatchtime);
											entity1.setImeiNumber(assetList.get(index).getImeiNumber());
											entity1.setBattryPercentage(assetList.get(index).getBatStat());
											entity1.setTagDist(assetList.get(index).getTagDist());

											entity1.setLb1(assetList.get(index).getLb1());
											entity1.setLb2(assetList.get(index).getLb2());
											entity1.setLb3(assetList.get(index).getLb3());
											entity1.setLb4(assetList.get(index).getLb4());
											entity1.setLb5(assetList.get(index).getLb5());
											entity1.setLb6(assetList.get(index).getLb6());
											entity1.setLb7(assetList.get(index).getLb7());
											entity1.setLb8(assetList.get(index).getLb8());
											entity1.setLb9(assetList.get(index).getLb9());
											entity1.setLb10(assetList.get(index).getLb10());
											entity1.setLb11(assetList.get(index).getLb11());
											entity1.setLb12(assetList.get(index).getLb12());
											entity1.setLb13(assetList.get(index).getLb13());
											entity1.setLb14(assetList.get(index).getLb14());
											entity1.setLb15(assetList.get(index).getLb15());
											// trackingRepo.updateExitTime(assetList.get(index).getEntryTime(),firstEntry.getTrackingId());

										}

									} catch (Exception e) {
										System.out.println(e);
									}

	if (dbData.getAssetGatewayMac_Id().equals(assetList.get(index).getgMacId())&& dbData.getUniqueNumberMacId().equals(assetList.get(index).gettMacId()))
	{
			iUpdate++;
//					    		dbData.setExistTime("N/A");//h.
//								trackingRepo.save(dbData);
			} else 
			{
				List<AssetTrackingEntity> list = trackingRepo.getListupdatedRecord(dbData.getEntryTime(), dbData.getAssetGatewayMac_Id());

										if (list.size() > 1)
										{
											trackingRepo.setExitTimetoUpdated(assetList.get(index).getEntryTime(),
													assetList.get(index).gettMacId(), dbData.getAssetGatewayMac_Id());
										} else 
										{
											if (inew == 0) {
												dbData.setExistTime(assetList.get(index).getEntryTime());// h.
												trackingRepo.save(dbData);
											}

										}

									}

								}
								Date time2 = (date1);
								SimpleDateFormat formatter11 = new SimpleDateFormat("hh:mm:ss");
								SimpleDateFormat formatter12 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

								String time1 = dbData.getEntryTime();
								System.out.println("time1" + time1);
								Date cdate = formatter12.parse(time1);

								long difference = (cdate.getTime() - time2.getTime());

								long secondsInMilli = 1000;
								long minutesInMilli = secondsInMilli * 60;
								long hoursInMilli = minutesInMilli * 60;
								long elapsedHours = difference / hoursInMilli;

								System.out.println(elapsedHours);

								long dispatchtimelimit = 1;
								try {

									dispatchtimelimit = allocationRepo.getDispatchTime(tagName);
									if (dispatchtimelimit == 0) {
										dispatchtimelimit = 1;

									}
									System.out.println("dispatchtimelimit" + dispatchtimelimit);
								} catch (Exception e) {
									System.out.println(e);
								}

								if (elapsedHours >= dispatchtimelimit)// dispatchLocation.equals(getLocationFromGateway)&&
								{
									System.out.println("Inside if dispatch location match...." + elapsedHours);
//							entity.setAssetTagName(tagName);
//							entity.setAssetGatewayName(gatewayName);
//							entity.setgSrNo(assetList.get(index).getgSrNo());
//							entity.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
//							entity.setBarcodeSerialNumber(assetList.get(index).getbSN());
//							entity.setUniqueNumberMacId(assetList.get(index).gettMacId());////
//							//entity.setDate(date1);////
////						    Date dateformated=new SimpleDateFormat("yyyy-MM-dd").parse(format);  
//							//String tstamp=;
//							entity.setTimestamp(assetList.get(index).getEntryTime().substring(11));
//							System.out.println(assetList.get(index).getEntryTime().substring(11)+" EntryTimesubstring");
//							entity.setDate(datenew);
//							entity.setTime(currenttime);
//							entity.setEntryTime(assetList.get(index).getEntryTime());
//							entity.setExistTime(assetList.get(index).getEntryTime());
//							entity.setTagEntryLocation(getLocationFromGateway);
//							entity.setTagExistLocation(dbLocation);
//							entity.setLatitude(assetList.get(index).getLatitude());
//							entity.setLongitude(assetList.get(index).getLongitude());
//							entity.setTagCategoty(assetList.get(index).getTagCategoty());
//							entity.setDispatchTime(entryTime.toString());
//							entity.setBattryPercentage(assetList.get(index).getBatStat());
//							entity.setTagDist(assetList.get(index).getTagDist());
//							entity.setLb1(assetList.get(index).getLb1());
//							entity.setLb2(assetList.get(index).getLb2());
//							entity.setLb3(assetList.get(index).getLb3());
//							entity.setLb4(assetList.get(index).getLb4());
//							entity.setLb5(assetList.get(index).getLb5());
//							entity.setLb6(assetList.get(index).getLb6());
//							entity.setLb7(assetList.get(index).getLb7());
//							entity.setLb8(assetList.get(index).getLb8());
//							entity.setLb9(assetList.get(index).getLb9());
//							entity.setLb10(assetList.get(index).getLb10());
//							entity.setLb11(assetList.get(index).getLb11());
//							entity.setLb12(assetList.get(index).getLb12());
//							entity.setLb13(assetList.get(index).getLb13());
//							entity.setLb14(assetList.get(index).getLb14());
//							entity.setLb15(assetList.get(index).getLb15());
//							virtualTrackingDetailsUpdate(entity); //for nonworkintag
//							trackingRepo.save(entity);
//							getwayCollectionTagwise(entity);
////							status = "Success";
									// icnt++;

									// for update
									if (dbData.getTagEntryLocation().equals(getLocationFromGateway)
											&& dbData.getUniqueNumberMacId().equals(assetList.get(index).gettMacId()))// update
									{

										entity.setEntryTime(dbData.getEntryTime());
										entity.setAssetTagName(tagName);
										entity.setAssetGatewayName(gatewayName);
										entity.setgSrNo(assetList.get(index).getgSrNo());
										entity.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
										entity.setBarcodeSerialNumber(assetList.get(index).getbSN());
										entity.setUniqueNumberMacId(assetList.get(index).gettMacId());
										// entity.setDate(date1);///////
										entity.setDate(datenew);
										entity.setTimestamp(assetList.get(index).getEntryTime().substring(11));
										System.out.println(assetList.get(index).getEntryTime() + " EntryTime");
										System.out.println(assetList.get(index).getEntryTime().substring(11)
												+ " EntryTimesubstring");
										entity.setTime(assetList.get(index).getEntryTime());

										if (itent == 1) {
											entity.setExistTime(assetlist2.get(0).getEntryTime());// adhi null set hota
										} else {
											entity.setExistTime("N/A");// adhi null set hota
										}
										if (iUpdate > 0) {
											entity.setExistTime(dbData.getExistTime());
										}
										if (itent > 1) {
											entity.setExistTime(afterStoreTime);//
										}
										entity.setTagEntryLocation(getLocationFromGateway);
										entity.setTagExistLocation(getLocationFromGateway);// dbLocation
										entity.setLatitude(assetList.get(index).getLatitude());
										entity.setLongitude(assetList.get(index).getLongitude());
										entity.setTagCategoty(barcodeList.getAssetTagCategory());
										Date cdate1 = new Date();
										String dispatchtime = cdate1.toString();
										entity.setDispatchTime(dispatchtime);
										entity.setImeiNumber(assetList.get(index).getImeiNumber());
										entity.setBattryPercentage(assetList.get(index).getBatStat());
										entity.setTagDist(assetList.get(index).getTagDist());

										entity.setLb1(assetList.get(index).getLb1());
										entity.setLb2(assetList.get(index).getLb2());
										entity.setLb3(assetList.get(index).getLb3());
										entity.setLb4(assetList.get(index).getLb4());
										entity.setLb5(assetList.get(index).getLb5());
										entity.setLb6(assetList.get(index).getLb6());
										entity.setLb7(assetList.get(index).getLb7());
										entity.setLb8(assetList.get(index).getLb8());
										entity.setLb9(assetList.get(index).getLb9());
										entity.setLb10(assetList.get(index).getLb10());
										entity.setLb11(assetList.get(index).getLb11());
										entity.setLb12(assetList.get(index).getLb12());
										entity.setLb13(assetList.get(index).getLb13());
										entity.setLb14(assetList.get(index).getLb14());
										entity.setLb15(assetList.get(index).getLb15());
										virtualTrackingDetailsUpdate(entity);
										if (updateTimegater > 0) {
											trackingRepo.save(entity1);
										} else {
											trackingRepo.save(entity);
										}

									} else {
										entity.setAssetTagName(tagName);
										entity.setAssetGatewayName(gatewayName);
										entity.setgSrNo(assetList.get(index).getgSrNo());
										entity.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
										entity.setBarcodeSerialNumber(assetList.get(index).getbSN());
										entity.setUniqueNumberMacId(assetList.get(index).gettMacId());
										// entity.setDate(date1);///////
										entity.setDate(datenew);
										//

										//
										entity.setTime(
												assetList.get(index).getEntryTime().substring(0, 11) + "" + "00:00:00");// "00:00:00"

										entity.setTimestamp(assetList.get(index).getEntryTime().substring(11));
										System.out.println(assetList.get(index).getEntryTime() + " EntryTime");
										System.out.println(assetList.get(index).getEntryTime().substring(11)
												+ " EntryTimesubstring");
										entity.setEntryTime(assetList.get(index).getEntryTime());
										if (itent > 0) {
											try {
												entity.setExistTime(assetlist2.get(0).getEntryTime());// adhi null set
																										// hota
											} catch (Exception e) {
												if (assetlist2.size() == 0) {
													entity.setExistTime(afterStoreTime);// adhi null set hota

												}
											}

										} else {
											entity.setExistTime("N/A");// adhi null set hota
										}

										if (itent > 1) {
											entity.setExistTime(afterStoreTime);//
										}
										entity.setTagEntryLocation(getLocationFromGateway);
										entity.setTagExistLocation(getLocationFromGateway);// dbLocation
										entity.setLatitude(assetList.get(index).getLatitude());
										entity.setLongitude(assetList.get(index).getLongitude());
										entity.setTagCategoty(barcodeList.getAssetTagCategory());
										Date cdate1 = new Date();
										String dispatchtime = cdate1.toString();
										entity.setDispatchTime(dispatchtime);
										entity.setImeiNumber(assetList.get(index).getImeiNumber());
										entity.setBattryPercentage(assetList.get(index).getBatStat());
										entity.setTagDist(assetList.get(index).getTagDist());

										entity.setLb1(assetList.get(index).getLb1());
										entity.setLb2(assetList.get(index).getLb2());
										entity.setLb3(assetList.get(index).getLb3());
										entity.setLb4(assetList.get(index).getLb4());
										entity.setLb5(assetList.get(index).getLb5());
										entity.setLb6(assetList.get(index).getLb6());
										entity.setLb7(assetList.get(index).getLb7());
										entity.setLb8(assetList.get(index).getLb8());
										entity.setLb9(assetList.get(index).getLb9());
										entity.setLb10(assetList.get(index).getLb10());
										entity.setLb11(assetList.get(index).getLb11());
										entity.setLb12(assetList.get(index).getLb12());
										entity.setLb13(assetList.get(index).getLb13());
										entity.setLb14(assetList.get(index).getLb14());
										entity.setLb15(assetList.get(index).getLb15());
										virtualTrackingDetailsUpdate(entity);
										if (updateTimegater > 0) {
											trackingRepo.save(entity1);
										} else if (sameGatewayUpdated > 0) {
											trackingRepo.save(entity2);
										} else {
											trackingRepo.save(entity);
										}

									}
									// end

									Date cnewdate = new Date();
									String dispatchtime = cnewdate.toString();

									int referenceCount = 1;
									List<AssetTrackingEntity> allDBData = trackingRepo.getAllDataForGivenTag(tagName);
									ProductDetailForAllocation product = allocationRepo.getProductByTag(tagName,
											assetList.get(index).gettMacId());

									int dispatchCount = 0;

									try {

										dispatchCount = dispatchRepo
												.findByUniqueNumberMacId(assetList.get(index).gettMacId());
									} catch (Exception e) {
										System.out.println(e);
									}

									System.out.println("Dispatch Count : " + dispatchCount);
									Date ccdate = new Date();
//					
//							
//							//......
									if (product != null) {
										try {
											for (AssetTrackingEntity assetTrackingEntity : allDBData) {
												DispatchedProductHistory dispatch = new DispatchedProductHistory();
												dispatch.setAssetTagName(assetTrackingEntity.getAssetTagName());
												dispatch.setAssetGatewayName(assetTrackingEntity.getAssetGatewayName());
												dispatch.setAssetGatewayMac_Id(
														assetTrackingEntity.getAssetGatewayMac_Id());
												dispatch.setBarcodeSerialNumber(
														assetTrackingEntity.getBarcodeSerialNumber());
												dispatch.setUniqueNumberMacId(
														assetTrackingEntity.getUniqueNumberMacId());
												dispatch.setDate(date1);////
												dispatch.setDispatchTime(assetTrackingEntity.getTime());
												dispatch.setEntryTime(assetTrackingEntity.getEntryTime());
												dispatch.setExistTime(assetTrackingEntity.getExistTime());
												dispatch.setTagEntryLocation(assetTrackingEntity.getTagEntryLocation());
												dispatch.setTagExistLocation(assetTrackingEntity.getTagExistLocation());
												dispatch.setLatitude(assetTrackingEntity.getLatitude());
												dispatch.setLongitude(assetTrackingEntity.getLongitude());
												dispatch.setTagCategoty(assetTrackingEntity.getTagCategoty());
												dispatch.setDispatchTime(assetTrackingEntity.getDispatchTime());

												if (dispatchCount == 0) {
													dispatch.setReferenceCount(referenceCount);
												} else {
													dispatch.setReferenceCount(dispatchCount + 1);
												}
												dispatch.setProductId(product.getProductId());
												dispatch.setProductName(product.getProductName());
												dispatchRepo.save(dispatch);
											}

										} catch (Exception e) {
											System.out.println(e);
										}

										trackingRepo.deleteByUniqueNumberMacId(assetList.get(index).gettMacId());

									}

									String updatedStatus = "Not-allocated";
									tagRepository.updateStatusAfterAllocation(updatedStatus, tagName,
											assetList.get(index).gettMacId());
									allocationRepo.deleteAllocatedProductByTag(tagName,
											assetList.get(index).gettMacId());
									System.out.println("product with " + assetList.get(index).getAssetTagName()
											+ "tag is dispatched");
//							return status;
								} else {

									if (dbData.getTagEntryLocation().equals(getLocationFromGateway)
											&& dbData.getUniqueNumberMacId().equals(assetList.get(index).gettMacId()))// update
									{

										entity.setEntryTime(dbData.getEntryTime());
										entity.setAssetTagName(tagName);
										entity.setAssetGatewayName(gatewayName);
										entity.setgSrNo(assetList.get(index).getgSrNo());
										entity.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
										entity.setBarcodeSerialNumber(assetList.get(index).getbSN());
										entity.setUniqueNumberMacId(assetList.get(index).gettMacId());

										entity.setDate(datenew);
										entity.setTimestamp(assetList.get(index).getEntryTime().substring(11));
										System.out.println(assetList.get(index).getEntryTime() + " EntryTime");
										System.out.println(assetList.get(index).getEntryTime().substring(11)
												+ " EntryTimesubstring");
										entity.setTime(assetList.get(index).getEntryTime());
										entity.setExistTime(dbData.getExistTime());
										if (itent > 0) {
											try {
												entity.setExistTime(assetlist2.get(0).getEntryTime());
											} catch (Exception e) {
												if (assetlist2.size() == 0) {
													entity.setExistTime(afterStoreTime);

												}
											}

										} else {
											entity.setExistTime("N/A");
										}

										if (iUpdate > 0) {
											entity.setExistTime(dbData.getExistTime());
										}
										if (itent > 1) {
											entity.setExistTime(afterStoreTime);//
										}
										entity.setTagEntryLocation(getLocationFromGateway);
										entity.setTagExistLocation(getLocationFromGateway);// dbLocation
										entity.setLatitude(assetList.get(index).getLatitude());
										entity.setLongitude(assetList.get(index).getLongitude());
										entity.setTagCategoty(barcodeList.getAssetTagCategory());
										Date cdate1 = new Date();
										String dispatchtime = cdate1.toString();
										entity.setDispatchTime(dispatchtime);
										entity.setImeiNumber(assetList.get(index).getImeiNumber());
										entity.setBattryPercentage(assetList.get(index).getBatStat());
										entity.setTagDist(assetList.get(index).getTagDist());

										entity.setLb1(assetList.get(index).getLb1());
										entity.setLb2(assetList.get(index).getLb2());
										entity.setLb3(assetList.get(index).getLb3());
										entity.setLb4(assetList.get(index).getLb4());
										entity.setLb5(assetList.get(index).getLb5());
										entity.setLb6(assetList.get(index).getLb6());
										entity.setLb7(assetList.get(index).getLb7());
										entity.setLb8(assetList.get(index).getLb8());
										entity.setLb9(assetList.get(index).getLb9());
										entity.setLb10(assetList.get(index).getLb10());
										entity.setLb11(assetList.get(index).getLb11());
										entity.setLb12(assetList.get(index).getLb12());
										entity.setLb13(assetList.get(index).getLb13());
										entity.setLb14(assetList.get(index).getLb14());
										entity.setLb15(assetList.get(index).getLb15());
										virtualTrackingDetailsUpdate(entity);
										if (updateTimegater > 0) {
											trackingRepo.save(entity1);
										} else {
											trackingRepo.save(entity);
										}
									} else {
										entity.setAssetTagName(tagName);
										entity.setAssetGatewayName(gatewayName);
										entity.setgSrNo(assetList.get(index).getgSrNo());
										entity.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
										entity.setBarcodeSerialNumber(assetList.get(index).getbSN());
										entity.setUniqueNumberMacId(assetList.get(index).gettMacId());

										entity.setDate(datenew);

										entity.setTime(
												assetList.get(index).getEntryTime().substring(0, 11) + "" + "00:00:00");
										entity.setTimestamp(assetList.get(index).getEntryTime().substring(11));
										System.out.println(assetList.get(index).getEntryTime() + " EntryTime");
										System.out.println(assetList.get(index).getEntryTime().substring(11)
												+ " EntryTimesubstring");
										entity.setEntryTime(assetList.get(index).getEntryTime());
										if (itent > 0) {
											try {
												entity.setExistTime(assetlist2.get(0).getEntryTime());// adhi null set
																										// hota
											} catch (Exception e) {
												if (assetlist2.size() == 0) {
													entity.setExistTime(afterStoreTime);// adhi null set hota

												}
											}

										} else {
											entity.setExistTime("N/A");// adhi null set hota
										}
										if (itent > 1) {
											entity.setExistTime(afterStoreTime);//
										}
										entity.setTagEntryLocation(getLocationFromGateway);
										entity.setTagExistLocation(getLocationFromGateway);// dbLocation
										entity.setLatitude(assetList.get(index).getLatitude());
										entity.setLongitude(assetList.get(index).getLongitude());
										entity.setTagCategoty(barcodeList.getAssetTagCategory());
										Date cdate1 = new Date();
										String dispatchtime = cdate1.toString();
										entity.setDispatchTime(dispatchtime);
										entity.setImeiNumber(assetList.get(index).getImeiNumber());
										entity.setBattryPercentage(assetList.get(index).getBatStat());
										entity.setTagDist(assetList.get(index).getTagDist());

										entity.setLb1(assetList.get(index).getLb1());
										entity.setLb2(assetList.get(index).getLb2());
										entity.setLb3(assetList.get(index).getLb3());
										entity.setLb4(assetList.get(index).getLb4());
										entity.setLb5(assetList.get(index).getLb5());
										entity.setLb6(assetList.get(index).getLb6());
										entity.setLb7(assetList.get(index).getLb7());
										entity.setLb8(assetList.get(index).getLb8());
										entity.setLb9(assetList.get(index).getLb9());
										entity.setLb10(assetList.get(index).getLb10());
										entity.setLb11(assetList.get(index).getLb11());
										entity.setLb12(assetList.get(index).getLb12());
										entity.setLb13(assetList.get(index).getLb13());
										entity.setLb14(assetList.get(index).getLb14());
										entity.setLb15(assetList.get(index).getLb15());
										virtualTrackingDetailsUpdate(entity);
										if (updateTimegater > 0) {
											trackingRepo.save(entity1);
										} else if (sameGatewayUpdated > 0) {
											trackingRepo.save(entity2);
										} else {
											trackingRepo.save(entity);
										}

									}

									getwayCollectionTagwise(entity);
//							status = "Success";
									icnt++;

								} // elsepart of if eclepsedhour>1

							}

						}
						// &&dbentrytime.equals(entryTime)&&dbentrytime.equals(entryTime)&&dbdate.equals(datenew)&&dbtime.equals(currenttime)&&dbentrytime.equals(date1)

					} // dbdata if
					else {
						entity.setAssetTagName(tagName);
						entity.setAssetGatewayName(gatewayName);
						entity.setgSrNo(assetList.get(index).getgSrNo());
						entity.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
						entity.setBarcodeSerialNumber(assetList.get(index).getbSN());
						entity.setUniqueNumberMacId(assetList.get(index).gettMacId());
						entity.setTagDist(assetList.get(index).getTagDist());
						// entity.setDate(date1);///////
						entity.setDate(datenew);

						//
						entity.setTime(assetList.get(index).getEntryTime().substring(0, 11) + "" + "00:00:00");// "00:00:00"
						entity.setTimestamp(assetList.get(index).getEntryTime().substring(11));
						System.out.println(assetList.get(index).getEntryTime() + " EntryTime");
						System.out.println(assetList.get(index).getEntryTime().substring(11) + " EntryTimesubstring");
						entity.setEntryTime(assetList.get(index).getEntryTime());
						entity.setExistTime("N/A");// adhi null set hota
						entity.setTagEntryLocation(getLocationFromGateway);
						entity.setTagExistLocation(getLocationFromGateway);// dbLocation
						entity.setLatitude(assetList.get(index).getLatitude());
						entity.setLongitude(assetList.get(index).getLongitude());
						entity.setTagCategoty(barcodeList.getAssetTagCategory());
						Date cdate = new Date();
						String dispatchtime = cdate.toString();
						entity.setDispatchTime(dispatchtime);
						entity.setImeiNumber(assetList.get(index).getImeiNumber());
						entity.setBattryPercentage(assetList.get(index).getBatStat());

						entity.setLb1(assetList.get(index).getLb1());
						entity.setLb2(assetList.get(index).getLb2());
						entity.setLb3(assetList.get(index).getLb3());
						entity.setLb4(assetList.get(index).getLb4());
						entity.setLb5(assetList.get(index).getLb5());
						entity.setLb6(assetList.get(index).getLb6());
						entity.setLb7(assetList.get(index).getLb7());
						entity.setLb8(assetList.get(index).getLb8());
						entity.setLb9(assetList.get(index).getLb9());
						entity.setLb10(assetList.get(index).getLb10());
						entity.setLb11(assetList.get(index).getLb11());
						entity.setLb12(assetList.get(index).getLb12());
						entity.setLb13(assetList.get(index).getLb13());
						entity.setLb14(assetList.get(index).getLb14());
						entity.setLb15(assetList.get(index).getLb15());
						virtualTrackingDetailsUpdate(entity);
						trackingRepo.save(entity);
						getwayCollectionTagwise(entity);
//				status = "Success";
						icnt++;
						try {
							TagGatewayCollection collection = collectionRepo
									.findByUniqueNumberMacId(assetList.get(index).gettMacId());
							if (collection != null) {
								collection.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
								collection.setAssetGatewayName(assetList.get(index).getAssetGatewayName());
								collection.setDate(datenew);
								collectionRepo.save(collection);
							} else {
								TagGatewayCollection collect = new TagGatewayCollection();
								collect.setAssetGatewayMac_Id(gatewayMacID);
								collect.setAssetGatewayName(gatewayName);
								collect.setAssetTagName(tagName);
								collect.setScanningDate(cdate);
								collect.setUniqueNumberMacId(assetList.get(index).getgMacId());
								collect.setDate(datenew);
								collectionRepo.save(collect);

							}
						} // try
						catch (Exception x) {
							System.out.println(x);
						}

						System.out.println(
								assetList.get(index).getAssetTagName() + "tag is scanned to another location...");
//				return status;
					}
				} // barcodelistend if
				else {
					System.out.println("tag scerial no and tag mac id not regester in database");
				}

			}
			if (imeiList != null) {
				assetList.get(index).setAssetGatewayName("GPS_REQUEST_" + System.currentTimeMillis());
				assetList.get(index).setbSN("GPS_REQUEST_" + System.currentTimeMillis());
				assetList.get(index).setgMacId("GPS_REQUEST_" + System.currentTimeMillis());
				assetList.get(index).settMacId("GPS_REQUEST_" + System.currentTimeMillis());
				assetList.get(index).setgSrNo("GPS_REQUEST_" + System.currentTimeMillis());

				int tssize = 0;
				List<AssetTag> tlist = null;
				try {
					tlist = tagRepository.getalllistforinsertion(assetList.get(index).getImeiNumber());
					tssize = tlist.size();
				} catch (Exception e) {
				}

				if (tssize > 1) {

					if (imeiList != null) {
						String category = imeiList.getAssetTagCategory();
						if (category.equalsIgnoreCase("GPS")) {
							entity.setAssetGatewayName(assetList.get(index).getAssetGatewayName());
							entity.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
						}
						entity.setAssetTagName(imeiList.getAssetTagName());
						entity.setBarcodeSerialNumber(assetList.get(index).getbSN());
						entity.setgSrNo(assetList.get(index).getgSrNo());
						entity.setUniqueNumberMacId(assetList.get(index).gettMacId());

						java.util.Date date1 = new java.util.Date();

//					
						Date currentTime = new Date();
						DateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
						TimeZone timezone = TimeZone.getTimeZone("IST");
						formatter1.setTimeZone(timezone);
						String currenttime = formatter1.format(currentTime);
						System.out.println("@@@@@@@@&&&&&&&&&&#######" + currenttime);

						java.util.Date date = new Date();
						SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd");
						String datenew = formatter4.format(date);
						entity.setDate(datenew);
						entity.setTime(currenttime);
						entity.setEntryTime(null);
						entity.setExistTime(null);
						entity.setTagEntryLocation(null);
						entity.setTagExistLocation(null);
						entity.setLatitude(assetList.get(index).getLatitude());
						entity.setLongitude(assetList.get(index).getLongitude());
						entity.setTagCategoty(category);
						entity.setDispatchTime(null);
						entity.setTimestamp(currenttime);
						entity.setImeiNumber(assetList.get(index).getImeiNumber());
						entity.setBattryPercentage(assetList.get(index).getBatStat());
						// entity.setTagDist(0);

						entity.setLb1(assetList.get(index).getLb1());
						entity.setLb2(assetList.get(index).getLb2());
						entity.setLb3(assetList.get(index).getLb3());
						entity.setLb4(assetList.get(index).getLb4());
						entity.setLb5(assetList.get(index).getLb5());
						entity.setLb6(assetList.get(index).getLb6());
						entity.setLb7(assetList.get(index).getLb7());
						entity.setLb8(assetList.get(index).getLb8());
						entity.setLb9(assetList.get(index).getLb9());
						entity.setLb10(assetList.get(index).getLb10());
						entity.setLb11(assetList.get(index).getLb11());
						entity.setLb12(assetList.get(index).getLb12());
						entity.setLb13(assetList.get(index).getLb13());
						entity.setLb14(assetList.get(index).getLb14());
						entity.setLb15(assetList.get(index).getLb15());
						trackingRepo.save(entity);
						getwayCollectionTagwise(entity);
//					status = "Success";
						icnt++;
					}

				} else {
					String category = tagRepository.getcategoryonimei(assetList.get(index).getImeiNumber());
					// String category = assetList.get(index).getTagCategoty();
					if (category.equalsIgnoreCase("GPS")) {
						entity.setAssetGatewayName(assetList.get(index).getAssetGatewayName());
						entity.setAssetGatewayMac_Id(assetList.get(index).getgMacId());
					}
					entity.setAssetTagName(imeiList.getAssetTagName());
					entity.setBarcodeSerialNumber(assetList.get(index).getbSN());
					entity.setgSrNo(assetList.get(index).getgSrNo());
					entity.setUniqueNumberMacId(assetList.get(index).gettMacId());

					java.util.Date date1 = new java.util.Date();

//				
					Date currentTime = new Date();
					DateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
					TimeZone timezone = TimeZone.getTimeZone("IST");
					formatter1.setTimeZone(timezone);
					String currenttime = formatter1.format(currentTime);
					System.out.println("@@@@@@@@&&&&&&&&&&#######" + currenttime);

					java.util.Date date = new Date();
					SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd");
					String datenew = formatter4.format(date);
					entity.setDate(datenew);
					entity.setTime(currenttime);
					entity.setEntryTime(null);
					entity.setExistTime(null);
					entity.setTagEntryLocation(null);
					entity.setTagExistLocation(null);
					entity.setLatitude(assetList.get(index).getLatitude());
					entity.setLongitude(assetList.get(index).getLongitude());
					entity.setTagCategoty(category);
					entity.setDispatchTime(null);
					entity.setTimestamp(currenttime);
					entity.setImeiNumber(assetList.get(index).getImeiNumber());
					entity.setBattryPercentage(assetList.get(index).getBatStat());
					// entity.setTagDist(0);

					entity.setLb1(assetList.get(index).getLb1());
					entity.setLb2(assetList.get(index).getLb2());
					entity.setLb3(assetList.get(index).getLb3());
					entity.setLb4(assetList.get(index).getLb4());
					entity.setLb5(assetList.get(index).getLb5());
					entity.setLb6(assetList.get(index).getLb6());
					entity.setLb7(assetList.get(index).getLb7());

					entity.setLb8(assetList.get(index).getLb8());
					entity.setLb9(assetList.get(index).getLb9());
					entity.setLb10(assetList.get(index).getLb10());
					entity.setLb11(assetList.get(index).getLb11());
					entity.setLb12(assetList.get(index).getLb12());
					entity.setLb13(assetList.get(index).getLb13());
					entity.setLb14(assetList.get(index).getLb14());
					entity.setLb15(assetList.get(index).getLb15());
					trackingRepo.save(entity);
					getwayCollectionTagwise(entity);
//				status = "Success";
					icnt++;

				}
			}

		} // end of for

		if (wrongdatapos != 0) {
			return "only " + icnt + " Tracking details are Saved And " + icnt2 + " Dublicate Tracking details"
					+ "wrong tag scaned at " + wrongdatapos + " position";
		}
		if (icnt == assetList.size() - 1) {

			return "only " + icnt + " Tracking details are Saved And " + icnt2 + " Dublicate Tracking details";
			// "only "+ icnt + " tracking details saved"
		} else {
			return "only " + icnt + " Tracking details are Saved And " + icnt2 + " Dublicate Tracking details";
		}

	}

	/**
	 * get Battery Percentage List
	 * 
	 * @param fkUserId
	 * @param role
	 * @return ResponseTrackingListBean
	 */
	public List<ResponseTrackingListBean> getBatteryPercentageList(String fkUserId, String role) {
		Integer lowBattryLimit = 20;
		long fkid = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getBatteryPercentageListSuperAdmin(lowBattryLimit);
		}
		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getBatteryPercentageListOrganization(fkid, lowBattryLimit);
		}

		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getBatteryPercentageListAdmin(fkid, lowBattryLimit);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getBatteryPercentageListUser(fkid, lowBattryLimit);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getBatteryPercentageListAdmin(adminId, lowBattryLimit);
		}
		return null;
	}

	/**
	 * get Battery Percentage List Alert Count
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ResponseLowBatteryListBean
	 */
	public ResponseLowBatteryListBean getBatteryPercentageListAlertCount(String fkUserId, String role) {

		Integer lowBattryLimit = 20;
		if (role.equals(CommonConstants.superAdmin)) {
			long total = trackingRepo.getBatteryPercentageSuperAdminAlertCount(lowBattryLimit);
			ResponseLowBatteryListBean bean = new ResponseLowBatteryListBean();
			bean.setTotalCount(total);
			bean.setStatus("Battery_Low");

			return bean;
		}

		if (role.equals(CommonConstants.admin)) {
			long total = trackingRepo.getBatteryPercentagetAdminAlertCount(fkUserId, lowBattryLimit);
			ResponseLowBatteryListBean bean = new ResponseLowBatteryListBean();
			bean.setTotalCount(total);
			bean.setStatus("Battery_Low");
			return bean;
		}

		if (role.equals(CommonConstants.user)) {
			long total = trackingRepo.getBatteryPercentageUserAlertCount(fkUserId, lowBattryLimit);
			ResponseLowBatteryListBean bean = new ResponseLowBatteryListBean();
			bean.setTotalCount(total);
			bean.setStatus("Battery_Low");
			return bean;
		}

		return null;
	}

	/**
	 * stay time calculator
	 * 
	 * @author pratik chaudhari
	 * @param list
	 * @return Map<String, String>
	 * @throws ParseException
	 */
	public Map<String, String> staytimecalculator(List<ResponseTrackingListBean> list) throws ParseException {
		System.out.println("@@@@@@@@@@@list size" + list.size());
		List<ResponseStayTimeListBean> bean = new ArrayList<ResponseStayTimeListBean>();
		int icnt = 0;
		// 2
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				break;
			}

			SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
			formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

			Date existTime = null;

			try {
				existTime = formatter11.parse(list.get(i).getExistTime());
			} catch (Exception e) {
				System.out.println("existTime" + existTime);
			}

			Date entryTime = formatter11.parse(list.get(i).getEntryTime());
			if (existTime == null) {
				existTime = entryTime;
			}
			// System.out.println("%%%% "+track.getExistTime());

			long difference_In_Time = existTime.getTime() - entryTime.getTime();
			long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
			long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
			long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;
			long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
			long difference_In_Years = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;
			if (difference_In_Time < 0) {
				difference_In_Time = -difference_In_Time;
				difference_In_Seconds = -difference_In_Seconds; // Updater or Modifier
				difference_In_Minutes = -difference_In_Minutes;
				difference_In_Hours = -difference_In_Hours;
				difference_In_Days = -difference_In_Days;
				difference_In_Years = -difference_In_Years;
			}
			String s1 = Long.toString(difference_In_Hours);
			String s2 = Long.toString(difference_In_Minutes);
			String s3 = Long.toString(difference_In_Seconds);
			String s = s1 + ":" + s2 + ":" + s3;

			System.out.println("@@@@@@@@@@@stay time in Hours:=" + difference_In_Hours);
			System.out.println("@@@@@@@@@@@list gatwayname:" + list.get(i).getAssetGatewayName());

			System.out.println("@@@@@@@@@@@value od i:=" + i);
			System.out.println("@@@@@@@@@@@bean:=" + bean.size());

			ResponseStayTimeListBean tempbean = new ResponseStayTimeListBean();
			tempbean.setGetwayName(list.get(icnt).getAssetGatewayName());
			tempbean.setStaytime(s);
			bean.add(i, tempbean);

			System.out.println("bean@@@@ " + bean);
			System.out.println("calculate stay time ");
			icnt++;
		}
//		List<ResponseStayTimeListBean> bean2= new ArrayList<ResponseStayTimeListBean>();
		Map<String, String> bean2 = new HashMap<String, String>();
		List<ResponseStayTimeListBean> list2 = null;
		for (int j = 0; j < bean.size(); j++) {
			String getwayName = bean.get(j).getGetwayName();
			long staytimeHours = 0;
			long staytimeMinits = 0;
			long staytimeSecond = 0;
			for (int k = 0; k < bean.size(); k++) {
				System.out.println("insidefor" + getwayName);
				if (getwayName.equals(bean.get(k).getGetwayName())) {
					System.out.println("inside if " + getwayName);
					System.out.println("bean.get(k).getStaytime()" + bean.get(k).getStaytime());
					String[] staytime1 = bean.get(k).getStaytime().split(":", 3);

					System.out.println(Array.get(staytime1, 0));
					System.out.println(Array.get(staytime1, 1));
					System.out.println(Array.get(staytime1, 2));
					Array.get(staytime1, 0);
					String H = Array.get(staytime1, 0).toString();
					staytimeHours = staytimeHours + (Long.parseLong(H));

					String M = Array.get(staytime1, 1).toString();
					staytimeMinits = staytimeMinits + (Long.parseLong(M));

					String S = Array.get(staytime1, 2).toString();
					staytimeSecond = staytimeSecond + (Long.parseLong(S));
//					

				}
			}

			ResponseStayTimeListBean tempbean2 = new ResponseStayTimeListBean();
			tempbean2.setGetwayName(getwayName);
			tempbean2.setStaytime(String.valueOf(staytimeHours));// staytimeHours+":"+staytimeMinits+":"+staytimeSecond

			bean2.put(tempbean2.getGetwayName(), tempbean2.getStaytime());

		}

		return bean2;

	}

	// ...........................

	/**
	 * get assetag staytime datewise
	 * 
	 * @author pratik chaudhari
	 * @param fromdate
	 * @param todate
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @return Map<String, String>
	 * @throws ParseException
	 */
	public Map<String, String> getassetagstaytimedatewise(String fromdate, String todate, String tagName,
			String fkUserId, String role) throws ParseException {
		System.out.println(fromdate + "to" + todate);

		if (role.equals(CommonConstants.superAdmin)) {
			List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
			try {
				list = trackingRepo.getlistdurationtagNameWiseforSuperadmin(fromdate, todate, tagName, fkUserId);

			} catch (Exception e) {
				System.out.println(e);
			}

			Map<String, String> bean = staytimecalculator(list);

			return bean;
		}
		if (role.equals(CommonConstants.organization)) {
			List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
			try {
				list = trackingRepo.getlistdurationtagNameWiseforOrganization(fromdate, todate, tagName, fkUserId);
			} catch (Exception e) {
				System.out.println(e);
			}

			Map<String, String> bean = staytimecalculator(list);

			return bean;
		}
		if (role.equals(CommonConstants.admin)) {
			List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
			try {
				list = trackingRepo.getlistdurationtagNameWiseforAdmin(fromdate, todate, tagName, fkUserId);
			} catch (Exception e) {
				System.out.println(e);
			}

			Map<String, String> bean = staytimecalculator(list);

			return bean;
		}
		if (role.equals(CommonConstants.user)) {
			List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
			try {
				list = trackingRepo.getlistdurationtagNameWiseforUser(fromdate, todate, tagName, fkUserId);
			} catch (Exception e) {
				System.out.println(e);
			}
			Map<String, String> bean = staytimecalculator(list);
			System.out.println("@@@@@@@final op" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.empUser)) {
			String adminid = employeeUserRepo.getAdminId1(fkUserId);
			List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
			try {
				list = trackingRepo.getlistdurationtagNameWiseforAdmin(fromdate, todate, tagName, adminid);
			} catch (Exception e) {
				System.out.println(e);
			}

			Map<String, String> bean = staytimecalculator(list);

			return bean;
		}

		return null;
	}

	// ........................................................
	/**
	 * get assetag staytime
	 * 
	 * @author pratik chaudhari
	 * @param duration
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @return Map<String, String>
	 * @throws ParseException
	 */
	public Map<String, String> getassetagstaytime(String duration, String tagName, String fkUserId, String role)
			throws ParseException {

		if (duration.equals("lastWeek")) {

			LocalDate today = LocalDate.now();
			LocalDate endDate = today.minusDays(1);// yesterday
			LocalDate startDate = today.minusDays(7);

			System.out.println("startDate" + startDate);
			System.out.println("startDate" + endDate);
			if (role.equals(CommonConstants.superAdmin)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				try {
					list = trackingRepo.getlistdurationtagNameWiseforSuperadmin(startDate.toString(),
							endDate.toString(), tagName, fkUserId);

					System.out.println("@@@@@@@list from db" + list);
					System.out.println("@@@@@@@list from db" + list.size());
				} catch (Exception e) {
					System.out.println(e);
				}

				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.organization)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				try {
					list = trackingRepo.getlistdurationtagNameWiseforOrganization(startDate.toString(),
							endDate.toString(), tagName, fkUserId);
				} catch (Exception e) {
					System.out.println(e);
				}

				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.admin)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				try {
					list = trackingRepo.getlistdurationtagNameWiseforAdmin(startDate.toString(), endDate.toString(),
							tagName, fkUserId);
				} catch (Exception e) {
					System.out.println(e);
				}

				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.user)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				try {
					list = trackingRepo.getlistdurationtagNameWiseforUser(startDate.toString(), endDate.toString(),
							tagName, fkUserId);
				} catch (Exception e) {
					System.out.println(e);
				}
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.empUser)) {
				String adminid = employeeUserRepo.getAdminId1(fkUserId);

				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				try {
					list = trackingRepo.getlistdurationtagNameWiseforAdmin(startDate.toString(), endDate.toString(),
							tagName, adminid);
				} catch (Exception e) {
					System.out.println(e);
				}

				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}

		}

		if (duration.equals("lastMonth")) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			LocalDate today = LocalDate.now();
			LocalDate endDate = today.minusDays(1);// yesterday
			LocalDate startDate = today.minusDays(7);

			if (role.equals(CommonConstants.superAdmin)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistdurationtagNameWiseforSuperadmin(startDate.toString(), endDate.toString(),
						tagName, fkUserId);
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.organization)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistdurationtagNameWiseforOrganization(startDate.toString(), endDate.toString(),
						tagName, fkUserId);
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.admin)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistdurationtagNameWiseforAdmin(startDate.toString(), endDate.toString(),
						tagName, fkUserId);
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.empUser)) {
				String adminid = employeeUserRepo.getAdminId1(fkUserId);

				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistdurationtagNameWiseforAdmin(startDate.toString(), endDate.toString(),
						tagName, adminid);
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.user)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistdurationtagNameWiseforUser(startDate.toString(), endDate.toString(), tagName,
						fkUserId);
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
		}

		else {

			LocalDate todayDate = LocalDate.now();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.toString());
			System.out.println("############# " + todayDate);

			if (role.equals(CommonConstants.superAdmin)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistCurrentdateNameWiseforSuperadmin(todayDate.toString(), tagName);
				System.out.println(list);

				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.organization)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistCurrentdatetagNameWiseforOrganization(todayDate.toString(), tagName,
						fkUserId);
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.admin)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistCurrentdatetagNameWiseforAdmin(todayDate.toString(), tagName, fkUserId);
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.empUser)) {

				String adminid = employeeUserRepo.getAdminId1(fkUserId);
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistCurrentdatetagNameWiseforAdmin(todayDate.toString(), tagName, adminid);
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
			if (role.equals(CommonConstants.user)) {
				List<ResponseTrackingListBean> list = new ArrayList<ResponseTrackingListBean>();
				list = trackingRepo.getlistCurrentdatetagNameWiseforUser(todayDate.toString(), tagName, fkUserId);
				Map<String, String> bean = staytimecalculator(list);
				System.out.println("@@@@@@@final op" + bean);
				return bean;
			}
		}

		return null;

	}

	/**
	 * get Battery Percentage filter TagWise
	 * 
	 * @author pratik chaudhari
	 * @param assetTagName
	 * @param fkUserId
	 * @param role
	 * @return ResponseTrackingListBean
	 */
	public List<ResponseTrackingListBean> getBatteryPercentagefilterTagWise(String assetTagName, String fkUserId,
			String role) {
		Integer lowBattryLimit = 20;
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getBatteryPercentagefilterTagWiseSuperAdmin(assetTagName, lowBattryLimit);
		}

		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getBatteryPercentagefilterTagWiseAdmin(assetTagName, fkUserId, lowBattryLimit);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getBatteryPercentagefilterTagWiseUser(assetTagName, fkUserId, lowBattryLimit);
		}
		return null;
	}

	/**
	 * get All Notification AlertCount
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return AllNotificationsCountBean
	 */
	public AllNotificationsCountBean getAllNotificationAlertCount(String fkUserId, String role) {

		Integer lowBattryLimit = 20;
		long NotifyCount = 0l;
		long userid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDateTime currentdate = LocalDateTime.now();
			LocalDateTime formdate = currentdate.minusHours(24);
			AllNotificationsCountBean bean = new AllNotificationsCountBean();

			long total = trackingRepo.getBatteryPercentageSuperAdminAlertCountfornotification(lowBattryLimit, formdate,
					currentdate);

			NotifyCount = total;

			bean.setAllNotifyCount(NotifyCount);

			return bean;
		}

		if (role.equals(CommonConstants.admin)) {
			AllNotificationsCountBean bean = new AllNotificationsCountBean();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDateTime currentdate = LocalDateTime.now();
			LocalDateTime formdate = currentdate.minusHours(24);

			long total = trackingRepo.getBatteryPercentagetAdminAlertCountfornotification(userid, lowBattryLimit,
					formdate, currentdate);
			System.out.println("total::" + total);
			long count = userdownlodRepo.getdownlodeingCountforAdminnotification(userid, formdate, currentdate);
			System.out.println("count::" + count);
			long agedtagcount = assettagservice.getAgedTagCountforAdmin(userid, formdate, currentdate);
			System.out.println("agedtagcount::" + agedtagcount);
			NotifyCount = total + count + agedtagcount;
			System.out.println("NotifyCount::" + NotifyCount);

			System.out.println("total");
			bean.setAllNotifyCount(NotifyCount);
			return bean;
		}
		return null;
	}

	/**
	 * get Tag Wise View Between Date
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param fromdate
	 * @param todate
	 * @param pageable
	 * @param tagName
	 * @param category
	 * @return AssetTrackingEntity
	 */
	public Page<AssetTrackingEntity> getTagWiseViewBetweenDate(String fkUserId, String role, String fromdate,
			String todate, Pageable pageable, String tagName, String category) {
		long fkuserid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.gettagenameWiseViewDataBetweenDateForSuperAdmin(fromdate, todate, tagName, category,
					pageable);

		}
		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.gettagenameWiseViewDataBetweenDateForOrganization(fromdate, todate, tagName, fkuserid,
					category, pageable);

		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.gettagenameWiseViewDataBetweenDateForAdmin(fromdate, todate, tagName, fkuserid,
					category, pageable);

		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.gettagenameWiseViewDataBetweenDateForUser(fromdate, todate, tagName, fkuserid, category,
					pageable);

		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.gettagenameWiseViewDataBetweenDateForAdmin(fromdate, todate, tagName, adminid, category,
					pageable);

		}

		return null;
	}

	/**
	 * get Gateway Wise View Between Date
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param fromdate
	 * @param todate
	 * @param pageable
	 * @param gatewayName
	 * @param category
	 * @return AssetTrackingEntity
	 */
	public Page<AssetTrackingEntity> getGatewayWiseViewBetweenDate(String fkUserId, String role, String fromdate,
			String todate, Pageable pageable, String gatewayName, String category) {
		long fkuserid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getgatewaynameWiseViewDataBetweenDateForSuperAdmin(fromdate, todate, gatewayName,
					category, pageable);

		}
		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getgatewaynameWiseViewDataBetweenDateForOrganization(fromdate, todate, gatewayName,
					fkuserid, category, pageable);

		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getgatewaynameWiseViewDataBetweenDateForAdmin(fromdate, todate, gatewayName, fkuserid,
					category, pageable);

		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getgatewaynameWiseViewDataBetweenDateForAdmin(fromdate, todate, gatewayName, adminid,
					category, pageable);

		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getgatewaynameWiseViewDataBetweenDateForUser(fromdate, todate, gatewayName, fkuserid,
					category, pageable);

		}
		return null;
	}

	/**
	 * get All Tracking details time wise
	 * 
	 * @author pratik chaudhari
	 * @param fromtime
	 * @param currenttime
	 * @param tagName
	 * @param datenew
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return ResponseTrackingListBean
	 */
	public Page<ResponseTrackingListBean> getAllTrackingdatailstimewise(LocalTime fromtime, LocalTime currenttime,
			String tagName, String datenew, String fkUserId, String role, String category, Pageable pageable) {

		long fkuserid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			Page<ResponseTrackingListBean> bean = trackingRepo.getalldetailsTrackingForSuperadmin(tagName,
					fromtime.toString(), currenttime.toString(), datenew, category, pageable);
			System.out.println("bean@@@" + bean);
			return bean;

		}
		if (role.equals(CommonConstants.organization)) {
			Page<ResponseTrackingListBean> bean = trackingRepo.getalldetailsTrackingForOrganization(tagName,
					fromtime.toString(), currenttime.toString(), datenew, fkuserid, category, pageable);
			System.out.println("bean@@@" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.admin)) {
			Page<ResponseTrackingListBean> bean = trackingRepo.getalldetailsTrackingForAdmin(tagName,
					fromtime.toString(), currenttime.toString(), datenew, fkuserid, category, pageable);
			System.out.println("bean@@@" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			Page<ResponseTrackingListBean> bean = trackingRepo.getalldetailsTrackingForAdmin(tagName,
					fromtime.toString(), currenttime.toString(), datenew, adminid, category, pageable);
			System.out.println("bean@@@" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.user)) {
			Page<ResponseTrackingListBean> bean = trackingRepo.getalldetailsTrackingForUser(tagName,
					fromtime.toString(), currenttime.toString(), datenew, fkuserid, category, pageable);
			System.out.println("bean@@@" + bean);
			return bean;

		}
		return null;

	}

	/**
	 * tag wise gateway View Api Between Date
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param fromdate
	 * @param todate
	 * @param pageable
	 * @param tagName
	 * @return AssetTrackingEntity
	 */
	public Page<AssetTrackingEntity> tagwisegatewayViewApiBetweenDate(String fkUserId, String role, String fromdate,
			String todate, Pageable pageable, String tagName) {
		long fkuserid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTagwiseGatewayViewforSuperAdmin(fromdate, todate, tagName, pageable);

		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagwiseGatewayViewforAdmin(fromdate, todate, tagName, fkuserid, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagwiseGatewayViewforUser(fromdate, todate, tagName, fkuserid, pageable);
		}

		return null;
	}

//...........for view date and time wise report 
	/**
	 * for view date and time wise report
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param fromdate
	 * @param todate
	 * @param fromtime
	 * @param totime
	 * @param pageable
	 * @return AssetTrackingEntity
	 */
	public Page<AssetTrackingEntity> gettaglistBetweenReportexcel(String fkUserId, String role, String fromdate,
			String todate, String fromtime, String totime, Pageable pageable) {
		long fkuserid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			Page<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforSuperAdminReport(fromdate, todate,
					fromtime, totime, pageable);
			return tagList;
		}
		if (role.equals(CommonConstants.organization)) {
			Page<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforOrganizationReport(fromdate,
					todate, fromtime, totime, fkuserid, pageable);
			return tagList;
		}
		if (role.equals(CommonConstants.admin)) {
			Page<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforAdminReport(fromdate, todate,
					fromtime, totime, fkuserid, pageable);
			return tagList;
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			Page<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforAdminReport(fromdate, todate,
					fromtime, totime, adminid, pageable);
			return tagList;
		}
		if (role.equals(CommonConstants.user)) {
			Page<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforUserReport(fromdate, todate,
					fromtime, totime, fkuserid, pageable);
			return tagList;
		}
		return null;

	}

	/**
	 * tag wise gateway View Api Between Date Report excel
	 * 
	 * @param fkUserId
	 * @param role
	 * @param fromdate
	 * @param todate
	 * @param fromtime
	 * @param totime
	 * @return InputStream
	 */
	public InputStream tagwisegatewayViewApiBetweenDateReportexcel(String fkUserId, String role, String fromdate,
			String todate, String fromtime, String totime) {
		long fkuserid = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			List<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforSuperAdminReportxl(fromdate,
					todate, fromtime, totime);

			List<String> gatwayList = gatewayRepo.getGatewayListForDropdownforsheetSuperAdmin();
			if (tagList.size() > 0) {
				ByteArrayInputStream in = AssetTrackingReportExcelHelper.TagExcelReport(tagList, gatwayList);
				return in;
			}
		}
		if (role.equals(CommonConstants.organization)) {
			List<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforOrganizationReports(fromdate,
					todate, fromtime, totime, fkuserid);
			List<String> gatwayList = gatewayRepo.getGatewayListForDropdownforsheetOrganizationReport(fkuserid);

			if (tagList.size() > 0) {
				ByteArrayInputStream in = AssetTrackingReportExcelHelper.TagExcelReport(tagList, gatwayList);
				return in;
			}
		}
		if (role.equals(CommonConstants.admin)) {
			List<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforAdminReportxl(fromdate, todate,
					fromtime, totime, fkuserid);
			List<String> gatwayList = gatewayRepo.getGatewayListForDropdownforsheetAdmin(fkuserid);

			if (tagList.size() > 0) {
				ByteArrayInputStream in = AssetTrackingReportExcelHelper.TagExcelReport(tagList, gatwayList);
				return in;
			}
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			List<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforAdminReportxl(fromdate, todate,
					fromtime, totime, adminid);
			List<String> gatwayList = gatewayRepo.getGatewayListForDropdownforsheetAdmin(adminid);

			if (tagList.size() > 0) {
				ByteArrayInputStream in = AssetTrackingReportExcelHelper.TagExcelReport(tagList, gatwayList);
				return in;
			}
		}
		if (role.equals(CommonConstants.user)) {
			List<AssetTrackingEntity> tagList = trackingRepo.getTagwiseGatewayViewforUserReportxl(fromdate, todate,
					fromtime, totime, fkuserid);

			List<String> gatwayList = gatewayRepo.getGatewayListForDropdownforsheetUser(fkuserid);
			if (tagList.size() > 0) {
				ByteArrayInputStream in = AssetTrackingReportExcelHelper.TagExcelReport(tagList, gatwayList);
				return in;
			}
		}

		return null;
	}

//.....................................................
	/**
	 * get gateway list Between Report excel
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return List<String>
	 */
	public List<String> getgatewaylistBetweenReportexcel(String fkUserId, String role) {
		long fkuserid = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			List<String> gatwayList = gatewayRepo.getGatewayListForDropdownforsheetSuperAdmin();
			return gatwayList;
		}
		if (role.equals(CommonConstants.organization)) {
			List<String> gatwayList = gatewayRepo.getGatewayListForDropdownforsheetOrganization(fkuserid);
			return gatwayList;
		}
		if (role.equals(CommonConstants.admin)) {
			List<String> gatwayList = gatewayRepo.getGatewayListForDropdownforsheetAdmin(fkuserid);
			return gatwayList;
		}
		if (role.equals(CommonConstants.user)) {
			List<String> gatwayList = gatewayRepo.getGatewayListForDropdownforsheetUser(fkuserid);
			return gatwayList;
		}
		return null;
	}

//.............................................................................

	/**
	 * get GpsTags Location
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ResponseGpsTagBean
	 */

	public List<ResponseGpsTagBean> getGpsTagsLocation(String fkUserId, String role) {

		if (role.equals(CommonConstants.superAdmin)) {
			List<String> gpsTags = tagRepository.getallgpstaglistforSuperAdmin();
			List<ResponseGpsTagBean> bean = new ArrayList<ResponseGpsTagBean>();
			for (int i = 0; i <= gpsTags.size() - 1; i++) {
				AssetTrackingEntity asset = null;
				try {
					asset = trackingRepo.getbytagName(gpsTags.get(i));

					if (asset == null) {
						asset.setAssetTagName("N/A");
						asset.setLatitude("N/A");
						asset.setLongitude("N/A");
						asset.setImeiNumber("N/A");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				if (asset != null) {
					ResponseGpsTagBean bean1 = new ResponseGpsTagBean();
					bean1.setTagName(asset.getAssetTagName());
					bean1.setLatitude(asset.getLatitude());
					bean1.setLongitude(asset.getLongitude());
					bean1.setTagimeiNumber(asset.getImeiNumber());
					Date date = new Date();
					bean1.setTimeStamp(String.valueOf(date));
					bean.add(bean1);
				}

			}
			if (bean.isEmpty()) {
				ResponseGpsTagBean bean2 = new ResponseGpsTagBean();
				bean2.setTagName("N/A");
				bean2.setLongitude("N/A");
				bean2.setLatitude("N/A");
				bean2.setTagimeiNumber("N/A");
				bean2.setTimeStamp(new Date().toString());
				bean.add(bean2);
			}
			return bean;
		}
		if (role.equals(CommonConstants.organization)) {
			long fkid = Long.parseLong(fkUserId);
			List<String> gpsTags = tagRepository.getallgpstaglistforOrganization(fkid);
			List<ResponseGpsTagBean> bean = new ArrayList<ResponseGpsTagBean>();
			for (int i = 0; i <= gpsTags.size() - 1; i++) {
				AssetTrackingEntity asset = null;
				try {
					asset = trackingRepo.getbytagName(gpsTags.get(i));

					if (asset == null) {
						asset.setAssetTagName("N/A");
						asset.setLatitude("N/A");
						asset.setLongitude("N/A");
						asset.setImeiNumber("N/A");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				if (asset != null) {
					ResponseGpsTagBean bean1 = new ResponseGpsTagBean();
					bean1.setTagName(asset.getAssetTagName());
					bean1.setLatitude(asset.getLatitude());
					bean1.setLongitude(asset.getLongitude());
					bean1.setTagimeiNumber(asset.getImeiNumber());
					Date date = new Date();
					bean1.setTimeStamp(String.valueOf(date));
					bean.add(bean1);
				}

			}
			if (bean.isEmpty()) {
				ResponseGpsTagBean bean2 = new ResponseGpsTagBean();
				bean2.setTagName("N/A");
				bean2.setLongitude("N/A");
				bean2.setLatitude("N/A");
				bean2.setTagimeiNumber("N/A");
				bean2.setTimeStamp(new Date().toString());
				bean.add(bean2);
			}
			return bean;
		}
		if (role.equals(CommonConstants.admin)) {
			long fkid = Long.parseLong(fkUserId);
			List<String> gpsTags = tagRepository.getallgpstaglistforAdmin(fkid);
			List<ResponseGpsTagBean> bean = new ArrayList<ResponseGpsTagBean>();
			for (int i = 0; i <= gpsTags.size() - 1; i++) {
				AssetTrackingEntity asset = null;
				try {
					asset = trackingRepo.getbytagName(gpsTags.get(i));

					if (asset == null) {
						asset.setAssetTagName("N/A");
						asset.setLatitude("N/A");
						asset.setLongitude("N/A");
						asset.setImeiNumber("N/A");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				if (asset != null) {
					ResponseGpsTagBean bean1 = new ResponseGpsTagBean();
					bean1.setTagName(asset.getAssetTagName());
					bean1.setLatitude(asset.getLatitude());
					bean1.setLongitude(asset.getLongitude());
					bean1.setTagimeiNumber(asset.getImeiNumber());
					Date date = new Date();
					bean1.setTimeStamp(String.valueOf(date));
					bean.add(bean1);
				}

			}
			if (bean.isEmpty()) {
				ResponseGpsTagBean bean2 = new ResponseGpsTagBean();
				bean2.setTagName("N/A");
				bean2.setLongitude("N/A");
				bean2.setLatitude("N/A");
				bean2.setTagimeiNumber("N/A");
				bean2.setTimeStamp(new Date().toString());
				bean.add(bean2);
			}
			return bean;
		}
		if (role.equals(CommonConstants.user)) {
			long fkid = Long.parseLong(fkUserId);
			List<String> gpsTags = tagRepository.getallgpstaglistforUser(fkid);
			List<ResponseGpsTagBean> bean = new ArrayList<ResponseGpsTagBean>();
			for (int i = 0; i <= gpsTags.size() - 1; i++) {
				AssetTrackingEntity asset = null;
				try {
					asset = trackingRepo.getbytagName(gpsTags.get(i));

					if (asset == null) {
						asset.setAssetTagName("N/A");
						asset.setLatitude("N/A");
						asset.setLongitude("N/A");
						asset.setImeiNumber("N/A");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				if (asset != null) {
					ResponseGpsTagBean bean1 = new ResponseGpsTagBean();
					bean1.setTagName(asset.getAssetTagName());
					bean1.setLatitude(asset.getLatitude());
					bean1.setLongitude(asset.getLongitude());
					bean1.setTagimeiNumber(asset.getImeiNumber());
					Date date = new Date();
					bean1.setTimeStamp(String.valueOf(date));
					bean.add(bean1);
				}
			}
			if (bean.isEmpty()) {
				ResponseGpsTagBean bean2 = new ResponseGpsTagBean();
				bean2.setTagName("N/A");
				bean2.setLongitude("N/A");
				bean2.setLatitude("N/A");
				bean2.setTagimeiNumber("N/A");
				bean2.setTimeStamp(new Date().toString());
				bean.add(bean2);
			}
			return bean;
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			List<String> gpsTags = tagRepository.getallgpstaglistforAdmin(adminId);
			List<ResponseGpsTagBean> bean = new ArrayList<ResponseGpsTagBean>();
			for (int i = 0; i <= gpsTags.size() - 1; i++) {
				AssetTrackingEntity asset = null;
				try {
					asset = trackingRepo.getbytagName(gpsTags.get(i));

					if (asset == null) {
						asset.setAssetTagName("N/A");
						asset.setLatitude("N/A");
						asset.setLongitude("N/A");
						asset.setImeiNumber("N/A");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				if (asset != null) {
					ResponseGpsTagBean bean1 = new ResponseGpsTagBean();
					bean1.setTagName(asset.getAssetTagName());
					bean1.setLatitude(asset.getLatitude());
					bean1.setLongitude(asset.getLongitude());
					bean1.setTagimeiNumber(asset.getImeiNumber());
					Date date = new Date();
					bean1.setTimeStamp(String.valueOf(date));
					bean.add(bean1);
				}

			}
			if (bean.isEmpty()) {
				ResponseGpsTagBean bean2 = new ResponseGpsTagBean();
				bean2.setTagName("N/A");
				bean2.setLongitude("N/A");
				bean2.setLatitude("N/A");
				bean2.setTagimeiNumber("N/A");
				bean2.setTimeStamp(new Date().toString());
				bean.add(bean2);
			}
			return bean;
		}

		return null;
	}

	/**
	 * get Gps Tags Location Tag NameWise
	 * 
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @return ResponseGpsTagBean
	 */
	public List<ResponseGpsTagBean> getGpsTagsLocationTagNameWise(String tagName, String fkUserId, String role) {
		long fkid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			List<ResponseGpsTagBean> bean = new ArrayList<ResponseGpsTagBean>();
			AssetTrackingEntity asset = trackingRepo.getbytagNameSuperAdmin(tagName);
			ResponseGpsTagBean bean1 = new ResponseGpsTagBean();
			bean1.setTagName(asset.getAssetTagName());
			bean1.setLatitude(asset.getLatitude());
			bean1.setLongitude(asset.getLongitude());
			bean1.setTagimeiNumber(asset.getImeiNumber());
			Date date = new Date();
			bean1.setTimeStamp(String.valueOf(date));
			bean.add(bean1);
			return bean;
		}
		if (role.equals(CommonConstants.admin)) {
			List<ResponseGpsTagBean> bean = new ArrayList<ResponseGpsTagBean>();
			AssetTrackingEntity asset = trackingRepo.getbytagNameAdmin(fkid, tagName);
			ResponseGpsTagBean bean1 = new ResponseGpsTagBean();
			bean1.setTagName(asset.getAssetTagName());
			bean1.setLatitude(asset.getLatitude());
			bean1.setLongitude(asset.getLongitude());
			bean1.setTagimeiNumber(asset.getImeiNumber());
			Date date = new Date();
			bean1.setTimeStamp(String.valueOf(date));
			bean.add(bean1);
			return bean;
		}

		if (role.equals(CommonConstants.user)) {
			List<ResponseGpsTagBean> bean = new ArrayList<ResponseGpsTagBean>();
			AssetTrackingEntity asset = trackingRepo.getbytagNameUser(fkid, tagName);
			ResponseGpsTagBean bean1 = new ResponseGpsTagBean();
			bean1.setTagName(asset.getAssetTagName());
			bean1.setLatitude(asset.getLatitude());
			bean1.setLongitude(asset.getLongitude());
			bean1.setTagimeiNumber(asset.getImeiNumber());
			Date date = new Date();
			bean1.setTimeStamp(String.valueOf(date));
			bean.add(bean1);
			return bean;
		}
		return null;
	}

	/**
	 * get tag Count GetawayWise
	 * 
	 * @author pratik chaudhari
	 * @param startdate
	 * @param enddate
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @return ResponseltotalBean
	 */
	public ResponseltotalBean gettagCountGetawayWise(String startdate, String enddate, String gatewayName,
			String fkUserId, String role) {
		long fkid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			long total = trackingRepo.getTagCountGatewayWiseforSuperAdmin(startdate, enddate, gatewayName);
			ResponseltotalBean bean = new ResponseltotalBean();
			bean.setlTotal(total);
			System.out.println("total" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.organization)) {
			long total = trackingRepo.getTagCountGatewayWiseforOrganization(startdate, enddate, gatewayName, fkid);
			ResponseltotalBean bean = new ResponseltotalBean();
			bean.setlTotal(total);
			System.out.println("total" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.admin)) {
			long total = trackingRepo.getTagCountGatewayWiseforAdmin(startdate, enddate, gatewayName, fkid);
			ResponseltotalBean bean = new ResponseltotalBean();
			bean.setlTotal(total);
			System.out.println("total" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			long total = trackingRepo.getTagCountGatewayWiseforAdmin(startdate, enddate, gatewayName, adminid);
			ResponseltotalBean bean = new ResponseltotalBean();
			bean.setlTotal(total);
			System.out.println("total" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.user)) {
			long total = trackingRepo.getTagCountGatewayWiseforUser(startdate, enddate, gatewayName, fkid);
			ResponseltotalBean bean = new ResponseltotalBean();
			bean.setlTotal(total);
			System.out.println("total" + bean);
			return bean;

		}
		return null;
	}

	/**
	 * get Asset Reserved column list
	 * 
	 * @author pratik chaudhari
	 * @return List
	 */
	public List getAssetReservedcolumnlist() {

		List list = trackingRepo.getAssetReservedcolumnlist();

		return list;
	}

	/**
	 * get Single Tracking Data For Filter User wise All data
	 * 
	 * @author pratik chaudhari
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @return AssetTrackingEntity
	 */
	public List<AssetTrackingEntity> getSingleTrackingDataForFilterUserwiseAlldata(String tagName, String fkUserId,
			String role) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getSingleTrackingDataForFilterSuperadminwiseAlldata(tagName);
		}
		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getSingleTrackingDataForFilterOrganizationwiseAlldata(tagName, userId);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getSingleTrackingDataForFilterAdminwiseAlldata(tagName, userId);
		}
		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getSingleTrackingDataForFilterUserwiseAlldata(tagName, userId);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getSingleTrackingDataForFilterAdminwiseAlldata(tagName, adminid);
		}
		return null;

	}

	/**
	 * get Dashbord Counts
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ResponseDashbordCountbean
	 * @throws ParseException
	 */
	public ResponseDashbordCountbean getDashbordCounts(String fkUserId, String role, String category)
			throws ParseException {
		ResponseDashbordCountbean Responsebean = new ResponseDashbordCountbean();
//	Long userId = Long.parseLong(fkUserId);
		String adminCount = null;

		long userCount = 0;
		long empuserCount = 0;
		String tagsCount = "0";
		String gatewayCount = "0";
		String organizationCount = "0";
		ResponseAssetTagWorkingNonWorkinCountBean tagbean = null;
		ResponseGetwayWorkingNonWorkingbean Gatewaybean = null;
		try {
			adminCount = userService.getCountAdmin(fkUserId, role, category);
			if (adminCount == null) {
				adminCount = "0";
			}
		} catch (Exception e) {
			System.out.println(adminCount);
		}
		try {
			userCount = userService.getCountUser(fkUserId, role, category);
			if (userCount == 0) {
				userCount = 0;
			}
		} catch (Exception e) {
			System.out.println(userCount);
		}

		try {
			tagsCount = assettagservice.tagCount(fkUserId, role, category);
			if (tagsCount == null) {
				tagsCount = "0";
			}
		} catch (Exception e) {
			System.out.println(tagsCount);
		}
		try {
			gatewayCount = gatewayService.gatewayCount(fkUserId, role, category);
			if (gatewayCount == null) {
				gatewayCount = "0";
			}
		} catch (Exception e) {
			System.out.println(gatewayCount);
		}
		try {
			tagbean = assettagservice.WorkingAndNonWorkingTagCount(fkUserId, role, category);
		} catch (Exception e) {
			System.out.println(tagbean);
		}

		try {
			Gatewaybean = gatewayService.getWorkingAndNonWorkingCount(fkUserId, role, category);
		} catch (Exception e) {
			System.out.println(Gatewaybean);
		}
		try {
			empuserCount = employeeUserService.getEmployeeUserCount(fkUserId, role, category);
			if (empuserCount == 0) {
				empuserCount = 0;
			}
		} catch (Exception e) {
			System.out.println(empuserCount);
		}

		try {
			organizationCount = userService.getCountOrganization(fkUserId, role, category);
			if (organizationCount == null) {
				organizationCount = "0";
			}
		} catch (Exception e) {
			System.out.println(organizationCount);
		}

		try {
			Responsebean.setTotalOrganigation(organizationCount);
			Responsebean.setTotalAdmin(adminCount);
			Responsebean.setTotalUser(userCount);
			Responsebean.setTotalTags(tagsCount);
			Responsebean.setTotalGateways(gatewayCount);
			Responsebean.setNonWorkingTags(tagbean.getAssetTagNonWorkingCount());
			Responsebean.setWorkingTags(tagbean.getAssetTagWorkingCount());
			Responsebean.setWorkingGateways(Gatewaybean.getWorkingGatwayCount());
			Responsebean.setNonWorkingGateways(Gatewaybean.getNonWorkingGatwayCount());
			Responsebean.setTotalEmpUser(empuserCount);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return Responsebean;

	}

	/**
	 * get DashbordCounts ForGps
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ResponseDashbordCountbeanForGPS
	 */
	public ResponseDashbordCountbeanForGPS getDashbordCountsForGps(String fkUserId, String role) {

		if (role.equals(CommonConstants.superAdmin)) {
			String adminCount = userRepo.getCountAdmingpsForSuperAdmin();
			String userCount = userRepo.getCountUsergpsForSuperAdmin();
			String tagsCount = tagRepository.tagCountgpsForSuperAdmin();
			String organizationCount = userRepo.getCountOrganizationgpsForSuperAdmin();
			ResponseAssetTagWorkingNonWorkinCountBean tagbean = assettagservice
					.WorkingAndNonWorkingTagCountGPS(fkUserId, role);
			ResponseDashbordCountbeanForGPS Responsebean = new ResponseDashbordCountbeanForGPS();
			Responsebean.setTotalAdmin(adminCount);
			Responsebean.setTotalTags(tagsCount);
			Responsebean.setTotalUser(userCount);
			Responsebean.setTotalOrganigation(organizationCount);
			Responsebean.setNonWorkingTags(tagbean.getAssetTagNonWorkingCount());
			Responsebean.setWorkingTags(tagbean.getAssetTagWorkingCount());
			return Responsebean;
		}
		if (role.equals(CommonConstants.organization)) {
			String adminCount = userRepo.getCountAdmingpsForOrganization();
			String userCount = userRepo.getCountUsergpsForOrganization();
			String tagsCount = tagRepository.tagCountgpsForOrganization(fkUserId);

			ResponseAssetTagWorkingNonWorkinCountBean tagbean = assettagservice
					.WorkingAndNonWorkingTagCountGPS(fkUserId, role);
			ResponseDashbordCountbeanForGPS Responsebean = new ResponseDashbordCountbeanForGPS();
			Responsebean.setTotalAdmin(adminCount);
			Responsebean.setTotalTags(tagsCount);
			Responsebean.setTotalUser(userCount);
			Responsebean.setTotalOrganigation("N/A");
			Responsebean.setNonWorkingTags(tagbean.getAssetTagNonWorkingCount());
			Responsebean.setWorkingTags(tagbean.getAssetTagWorkingCount());
			return Responsebean;
		}
		if (role.equals(CommonConstants.admin)) {

			String userCount = userRepo.getCountUsergpsForAdmin();
			String tagsCount = tagRepository.tagCountgpsForAdmin(fkUserId);

			ResponseAssetTagWorkingNonWorkinCountBean tagbean = assettagservice
					.WorkingAndNonWorkingTagCountGPS(fkUserId, role);
			ResponseDashbordCountbeanForGPS Responsebean = new ResponseDashbordCountbeanForGPS();
			Responsebean.setTotalAdmin("N/A");
			Responsebean.setTotalTags(tagsCount);
			Responsebean.setTotalUser(userCount);
			Responsebean.setTotalOrganigation("N/A");
			Responsebean.setNonWorkingTags(tagbean.getAssetTagNonWorkingCount());
			Responsebean.setWorkingTags(tagbean.getAssetTagWorkingCount());
			return Responsebean;

		}
		if (role.equals(CommonConstants.user)) {

			String userCount = userRepo.getCountUsergpsForUser();
			String tagsCount = tagRepository.tagCountgpsForUser(fkUserId);

			ResponseAssetTagWorkingNonWorkinCountBean tagbean = assettagservice
					.WorkingAndNonWorkingTagCountGPS(fkUserId, role);
			ResponseDashbordCountbeanForGPS Responsebean = new ResponseDashbordCountbeanForGPS();
			Responsebean.setTotalAdmin("N/A");
			Responsebean.setTotalTags(tagsCount);
			Responsebean.setTotalUser(userCount);
			Responsebean.setTotalOrganigation("N/A");
			Responsebean.setNonWorkingTags(tagbean.getAssetTagNonWorkingCount());
			Responsebean.setWorkingTags(tagbean.getAssetTagWorkingCount());

			return Responsebean;
		}

		return null;
	}

	/**
	 * get PayloadReportToView Duration With Pagination
	 * 
	 * @author pratik chaudhari
	 * @param tagName
	 * @param fromDate
	 * @param toDate
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return AssetTrackingEntity
	 */
	public Page<AssetTrackingEntity> getPayloadReportToViewDurationWithPaginationxl(String tagName, String fromDate,
			String toDate, String fkUserId, String role, String category, Pageable pageable) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println(fromDate + "and" + toDate);
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			System.out.println("tagName" + tagName);

			Page<AssetTrackingEntity> bean = trackingRepo
					.getTagReportForSuperAdminExportDownloadBetweenDateWithPaginationxl(fromDate, toDate, tagName,
							category, pageable);
			System.out.println("bean" + bean);
			return bean;

		}

		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getTagReportForOrganizationExportDownloadBetweenDateWithPaginationxl(fromDate, toDate,
					tagName, id, category, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagReportForAdminExportDownloadBetweenDateWithPaginationxl(fromDate, toDate, tagName,
					id, category, pageable);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagReportForUserExportDownloadBetweenDateWithPaginationxl(fromDate, toDate, tagName,
					id, category, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getTagReportForAdminExportDownloadBetweenDateWithPaginationxl(fromDate, toDate, tagName,
					adminid, category, pageable);
		}

		return null;
	}

	/**
	 * get Payload Report To View Todays With Pagination
	 * 
	 * @author pratik chaudhari
	 * @param tagName
	 * @param today
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return AssetTrackingEntity
	 */
	public Page<AssetTrackingEntity> getPayloadReportToViewTodaysWithPaginationxl(String tagName, Date today,
			String fkUserId, String role, String category, Pageable pageable) {
		Long id = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTagReportForSuperAdminExportDownloadTodaysDateWithPaginationxl(today, tagName,
					category, pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getTagReportForOrganizationExportDownloadTodaysDateWithPaginationxl(today, tagName, id,
					category, pageable);
		}

		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagReportForAdminExportDownloadTodaysDateWithPaginationxl(today, tagName, id,
					category, pageable);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagReportForUserExportDownloadTodaysDateWithPaginationxl(today, tagName, id,
					category, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getTagReportForAdminExportDownloadTodaysDateWithPaginationxl(today, tagName, adminid,
					category, pageable);
		}

		return null;
	}

	/**
	 * get all View Data
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return ResponseTrackingListBean
	 */
	public Page<ResponseTrackingListBean> getallViewData(String fkUserId, String role, String category,
			Pageable pageable) {

		Long id = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getTagReportForSuperAdmin(category, pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getTagReportForOrganizationExportDownloadTodaysDateWithPaginationdefault(id, category,
					pageable);
		}

		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getTagReportForAdminExportDownloadTodaysDateWithPaginationdefault(id, category,
					pageable);
		}

		if (role.equals(CommonConstants.user)) {
			return trackingRepo.getTagReportForUserExportDownloadTodaysDateWithPaginationdefault(id, category,
					pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getTagReportForAdminExportDownloadTodaysDateWithPaginationdefault(adminid, category,
					pageable);
		}

		return null;
	}

	/**
	 * get all View Datad defualt
	 * 
	 * @author pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return ResponseTrackingListBean
	 */
	public Page<ResponseTrackingListBean> getallViewDatadefualt(String fkUserId, String role, String category,
			Pageable pageable) {
		Long id = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			return trackingRepo.getGatewayReportForSuperAdminExportDownloadBetweenDateWithPaginationdefault(category,
					pageable);
		}

		if (role.equals(CommonConstants.organization)) {
			return trackingRepo.getGatewayReportForOrganizationExportDownloadBetweenDateWithPaginationdefault(id,
					category, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			return trackingRepo.getGatewayReportForAdminExportDownloadBetweenDateWithPaginationdefault(id, category,
					pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return trackingRepo.getGatewayReportForAdminExportDownloadBetweenDateWithPaginationdefault(adminid,
					category, pageable);
		}

		if (role.equals(CommonConstants.user)) {
			Page<ResponseTrackingListBean> pobj = trackingRepo
					.getGatewayReportForAdminExportDownloadBetweenDateWithPaginationdefault(id, category, pageable);
			return pobj;
		}
		return null;

	}

	/**
	 * use to Search By Parameters
	 * 
	 * @author pratik chaudhari
	 * @param assetTagName
	 * @param assetGatewayName
	 * @param date
	 * @param entryTime
	 * @param tagEntryLocation
	 * @param existTime
	 * @param dispatchTime
	 * @param time
	 * @param battryPercentage
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @return AssetTrackingEntity
	 */
	public Page<AssetTrackingEntity> SearchByParameters(String assetTagName, String assetGatewayName, String date,
			String entryTime, String tagEntryLocation, String existTime, String dispatchTime, String time,
			String battryPercentage, String fkUserId, String role, Pageable pageable) {
		long fkuserid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			if (assetTagName != null) {
				return trackingRepo.getAssetTagNamewiseList(assetTagName, pageable);
			}
			if (assetGatewayName != null) {
				return trackingRepo.getAssetGatewayNamewiseList(assetGatewayName, pageable);
			}
			String datestr = null;
			try {
				datestr = date.replace("/", "-");
			} catch (Exception e) {
			}

			if (date != null) {
				return trackingRepo.getdatewiseList(datestr, pageable);
			}
			String entryTimestr = null;
			try {
				entryTimestr = "%" + entryTime;
			} catch (Exception e) {
			}

			if (entryTime != null) {
				return trackingRepo.getentryTimewiseList(entryTimestr, pageable);
			}
			if (tagEntryLocation != null) {
				return trackingRepo.gettagEntryLocationwiseList(tagEntryLocation, pageable);
			}
			String existTimestr = null;
			try {
				existTimestr = "%" + existTime;
			} catch (Exception e) {
			}

			if (existTime != null) {
				return trackingRepo.getexistTimewiseList(existTimestr, pageable);
			}
			if (dispatchTime != null) {
				return trackingRepo.getdispatchTimewiseList(dispatchTime, pageable);
			}
			String timestr = null;
			try {
				timestr = "%" + time;
			} catch (Exception e) {
			}

			if (time != null) {
				return trackingRepo.gettimewiseList(timestr, pageable);
			}
			if (battryPercentage != null) {
				return trackingRepo.getbattryPercentagewiseList(battryPercentage, pageable);
			}
		}
		if (role.equals(CommonConstants.admin)) {
			if (assetTagName != null) {
				return trackingRepo.getAssetTagNamewiseListForAdmin(assetTagName, fkuserid, pageable);
			}
			if (assetGatewayName != null) {
				return trackingRepo.getAssetGatewayNamewiseListForAdmin(assetGatewayName, fkuserid, pageable);
			}
			String datestr = null;
			try {
				datestr = date.replace("/", "-");
			} catch (Exception e) {
			}
			if (date != null) {
				return trackingRepo.getdatewiseListForAdmin(datestr, fkuserid, pageable);
			}
			String entryTimestr = null;
			try {
				entryTimestr = "%" + entryTime;
			} catch (Exception e) {
			}
			if (entryTime != null) {
				return trackingRepo.getentryTimewiseListForAdmin(entryTimestr, fkuserid, pageable);
			}
			if (tagEntryLocation != null) {
				return trackingRepo.gettagEntryLocationwiseListForAdmin(tagEntryLocation, fkuserid, pageable);
			}
			String existTimestr = null;
			try {
				existTimestr = "%" + existTime;
			} catch (Exception e) {
			}
			if (existTime != null) {
				return trackingRepo.getexistTimewiseListForAdmin(existTimestr, fkuserid, pageable);
			}
			if (dispatchTime != null) {
				return trackingRepo.getdispatchTimewiseListForAdmin(dispatchTime, fkuserid, pageable);
			}
			String timestr = null;
			try {
				timestr = "%" + time;
			} catch (Exception e) {
			}
			if (time != null) {
				return trackingRepo.gettimewiseListForAdmin(timestr, fkuserid, pageable);
			}
			if (battryPercentage != null) {
				return trackingRepo.getbattryPercentagewiseListForAdmin(battryPercentage, fkuserid, pageable);
			}

		}
		// --pending
		// ->
		if (role.equals(CommonConstants.organization)) {
			if (assetTagName != null) {
				System.out.println();
				return trackingRepo.getAssetTagNamewiseListForOrganization(assetTagName, pageable);
			}
			if (assetGatewayName != null) {
				return trackingRepo.getAssetGatewayNamewiseListForOrganization(assetGatewayName, pageable);
			}
			String datestr = null;
			try {
				datestr = date.replace("/", "-");
			} catch (Exception e) {
			}
			if (date != null) {
				return trackingRepo.getdatewiseListForOrganization(datestr, fkUserId, pageable);
			}
			String entryTimestr = null;
			try {
				entryTimestr = "%" + entryTime;
			} catch (Exception e) {
			}
			if (entryTime != null) {
				return trackingRepo.getentryTimewiseListForOrganization(entryTimestr, pageable);
			}
			if (tagEntryLocation != null) {
				return trackingRepo.gettagEntryLocationwiseListForOrganization(tagEntryLocation, pageable);
			}
			String existTimestr = null;
			try {
				existTimestr = "%" + existTime;
			} catch (Exception e) {
			}
			if (existTime != null) {
				return trackingRepo.getexistTimewiseListForOrganization(existTimestr, pageable);
			}
			if (dispatchTime != null) {
				return trackingRepo.getdispatchTimewiseListForOrganization(dispatchTime, pageable);
			}
			String timestr = null;
			try {
				timestr = "%" + time;
			} catch (Exception e) {
			}
			if (time != null) {
				return trackingRepo.gettimewiseListForOrganization(time, pageable);
			}
			if (battryPercentage != null) {
				return trackingRepo.getbattryPercentagewiseListForOrganization(battryPercentage, pageable);
			}

		}
		if (role.equals(CommonConstants.user)) {
			if (assetTagName != null) {
				return trackingRepo.getAssetTagNamewiseListForUserForUser(assetTagName, pageable);
			}
			if (assetGatewayName != null) {
				return trackingRepo.getAssetGatewayNamewiseListForUser(assetGatewayName, pageable);
			}
			String datestr = null;
			try {
				datestr = date.replace("/", "-");
			} catch (Exception e) {
			}
			if (date != null) {
				return trackingRepo.getdatewiseListForUser(datestr, pageable);
			}
			String entryTimestr = null;
			try {
				entryTimestr = "%" + entryTime;
			} catch (Exception e) {
			}

			if (entryTime != null) {
				return trackingRepo.getentryTimewiseListForUser(entryTimestr, pageable);
			}
			if (tagEntryLocation != null) {
				return trackingRepo.gettagEntryLocationwiseListForUser(tagEntryLocation, pageable);
			}

			String existTimestr = null;
			try {
				existTimestr = "%" + existTime;
			} catch (Exception e) {
			}
			if (existTime != null) {
				return trackingRepo.getexistTimewiseListForUser(existTimestr, pageable);
			}
			if (dispatchTime != null) {
				return trackingRepo.getdispatchTimewiseListForUser(dispatchTime, pageable);
			}

			String timestr = null;
			try {
				timestr = "%" + time;
			} catch (Exception e) {
			}
			if (time != null) {
				return trackingRepo.gettimewiseListForUser(time, pageable);
			}
			if (battryPercentage != null) {
				return trackingRepo.getbattryPercentagewiseListForUser(battryPercentage, pageable);
			}

		}
		if (role.equals(CommonConstants.empUser)) {
			if (assetTagName != null) {
				return trackingRepo.getAssetTagNamewiseListForAdmin(assetTagName, fkuserid, pageable);
			}
			if (assetGatewayName != null) {
				return trackingRepo.getAssetGatewayNamewiseListForAdmin(assetGatewayName, fkuserid, pageable);
			}
			String datestr = null;
			try {
				datestr = date.replace("/", "-");
			} catch (Exception e) {
			}
			if (date != null) {
				return trackingRepo.getdatewiseListForAdmin(datestr, fkuserid, pageable);
			}
			String entryTimestr = null;
			try {
				entryTimestr = "%" + entryTime;
			} catch (Exception e) {
			}
			if (entryTime != null) {
				return trackingRepo.getentryTimewiseListForAdmin(entryTimestr, fkuserid, pageable);
			}
			if (tagEntryLocation != null) {
				return trackingRepo.gettagEntryLocationwiseListForAdmin(tagEntryLocation, fkuserid, pageable);
			}
			String existTimestr = null;
			try {
				existTimestr = "%" + existTime;
			} catch (Exception e) {
			}
			if (existTime != null) {
				return trackingRepo.getexistTimewiseListForAdmin(existTimestr, fkuserid, pageable);
			}
			if (dispatchTime != null) {
				return trackingRepo.getdispatchTimewiseListForAdmin(dispatchTime, fkuserid, pageable);
			}
			String timestr = null;
			try {
				timestr = "%" + time;
			} catch (Exception e) {
			}
			if (time != null) {
				return trackingRepo.gettimewiseListForAdmin(timestr, fkuserid, pageable);
			}
			if (battryPercentage != null) {
				return trackingRepo.getbattryPercentagewiseListForAdmin(battryPercentage, fkuserid, pageable);
			}

		}

		return null;

	}

}
