package com.embel.asset.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.embel.asset.bean.AssetGatewayBean;
import com.embel.asset.bean.ResponseGetwayWorkingNonWorkingbean;
import com.embel.asset.bean.ResponseworkingNonworkinGatewaybean;
import com.embel.asset.dto.AssetGatewayDto;
import com.embel.asset.entity.AssetGateway;
import com.embel.asset.entity.AssetGatewayStock;
import com.embel.asset.helper.AssetGatewaylistFromSuperAdminHelper;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.repository.AssetGatewayRepository;
import com.embel.asset.repository.AssetGatewayStockRepository;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.UserRepository;
import com.embel.asset.repository.VirtualTrackingDetailsRepository;

@Service
public class AssetGatewayService {

	@Autowired
	AssetGatewayRepository gatewayRepo;

	@Autowired
	AssetGatewayStockRepository assetGatewayStockRepository;

	@Autowired
	VirtualTrackingDetailsRepository virtualtrackingRepo;

	@Autowired
	limitsservice limitservice;
	@Autowired
	EmployeUserRepository employeeUserRepo;
	@Autowired
	UserRepository userRepo;

	/**
	 * @author pratik chaudhari
	 * @param dto this dto is object of AssetGatewayDto class
	 * @return String
	 */
	public String addAssetGateway(AssetGatewayDto dto)
	{
		long gatewayBarcodeNumber = 1000l;
		
		AssetGateway gateway = new AssetGateway();

		String uniqueCodeMacId = null;
		String GatewayBarcodeOrSerialNumber = null;
		try {
			List<Long> gatewayBarcodeNumberList = gatewayRepo.getLastGatewayBarcodeNumber();
			for (int j = 0; j < gatewayBarcodeNumberList.size(); j++)
			{
				//AssetGateway assetGatewayEntity =  gatewayBarcodeNumberList.get(j);
				//gatewayBarcodeNumber = assetGatewayEntity.getGatewayBarcodeNumber();
				gatewayBarcodeNumber = gatewayBarcodeNumberList.get(j);
				gatewayBarcodeNumber++;
			}
			
			uniqueCodeMacId = gatewayRepo.getMacId(dto.getGatewayUniqueCodeMacId());
			GatewayBarcodeOrSerialNumber = gatewayRepo
					.getGatewayBarcodeOrSerialNumber(dto.getGatewayBarcodeOrSerialNumber());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		if (uniqueCodeMacId == null && GatewayBarcodeOrSerialNumber == null) {

			gateway.setGatewayName(dto.getGatewayName());
			
			gateway.setGatewayBarcodeNumber(gatewayBarcodeNumber);
			
			gateway.setAssetTagCategory(dto.getAssetTagCategory());// +" "+ dto.getAssetSubCategory()
			gateway.setGatewayBarcodeSerialNumber(dto.getGatewayBarcodeOrSerialNumber());
			gateway.setGatewayLocation(dto.getGatewayLocation());
			gateway.setGatewayUniqueCodeMacId(dto.getGatewayUniqueCodeMacId().toUpperCase());
			gateway.setUser(dto.getUser());
			gateway.setAdmin(dto.getAdmin());
			gateway.setOrganization(dto.getOrganization());
			gateway.setFkOrganizationId(dto.getFkOrganizationId());
			gateway.setFkAdminId(dto.getFkAdminId());
			gateway.setFkUserId(dto.getFkUserId());
			gateway.setWakeupTime(dto.getWakeupTime());
			gateway.setDatetime(dto.getDatetime());

			gateway.setxCoordinate(dto.getxCoordinate());
			gateway.setyCoordinate(dto.getyCoordinate());
			gateway.setTimeZone(dto.getTimeZone());
			gateway.setCreatedBy(dto.getCreatedBy());
			gateway.setAssetGetwayEnable((long) 1);

			gatewayRepo.save(gateway);
			assetGatewayStockRepository.updatestatus(dto.getGatewayUniqueCodeMacId().toUpperCase());
			return "saved :";

		} else {
			return "Dublicate macid or barcode scerial Number :";
		}

	}

	/**
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return AssetGatewayBean
	 */
	public List<AssetGatewayBean> getAllGatewayList(String fkUserId, String role) {

		if (role.equals(CommonConstants.superAdmin)) {
			return gatewayRepo.getGatewayListForSuperAdmin();
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForAdmin(fkID);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForUser(fkID);
		}
		return null;
	}

	/**
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	public String gatewayCount(String fkUserId, String role, String category) {

		if (role.equals(CommonConstants.superAdmin)) {
			return gatewayRepo.getCountForSuperAdmin(category);
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getCountForOrganization(fkID, category);

		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getCountForAdmin(fkID, category);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayCountForUser(fkID, category);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			return gatewayRepo.getCountForEmpUser(adminId);
		}
		return null;
	}

	/**
	 * get Gateway For Edit
	 * 
	 * @param id
	 * @return AssetGatewayBean
	 */
	public List<AssetGatewayBean> getGatewayForEdit(Long id) {

		return gatewayRepo.getGatewayForEdit(id);
	}

	/**
	 * update Gateway
	 * 
	 * @author pratik chaudhari
	 * @param dto
	 * @return String
	 */
	public String updateGateway(AssetGatewayDto dto) {

		AssetGateway updateGateway = gatewayRepo.getOne(dto.getGatewayId());
		long userid = 0;
		try {
			userRepo.getpkuserIdOnUserName(dto.getUser());
		} catch (Exception e) {
			System.out.println(e);
		}

		updateGateway.setGatewayName(dto.getGatewayName());
		updateGateway.setAssetTagCategory(dto.getAssetTagCategory()); // +" "+ dto.getAssetSubCategory()
		updateGateway.setGatewayBarcodeSerialNumber(dto.getGatewayBarcodeOrSerialNumber());
		updateGateway.setGatewayLocation(dto.getGatewayLocation());
		updateGateway.setGatewayUniqueCodeMacId(dto.getGatewayUniqueCodeMacId().toUpperCase());
		updateGateway.setUser(dto.getUser());
		updateGateway.setAdmin(dto.getAdmin());
		updateGateway.setFkAdminId(dto.getFkAdminId());
		updateGateway.setFkUserId(userid);
		updateGateway.setWakeupTime(dto.getWakeupTime());
		updateGateway.setDatetime(dto.getDatetime());

		updateGateway.setCreatedBy(dto.getCreatedBy());
		updateGateway.setxCoordinate(dto.getxCoordinate());
		updateGateway.setyCoordinate(dto.getyCoordinate());
		updateGateway.setOrganization(dto.getOrganization());
		updateGateway.setFkOrganizationId(dto.getFkOrganizationId());
		updateGateway.setTimeZone(dto.getTimeZone());
		updateGateway.setAssetGetwayEnable((long) 1);

		gatewayRepo.save(updateGateway);
		return "Gateway Updated Successfully";

	}

	/**
	 * delete gateway
	 * 
	 * @author Pratik chaudhari
	 * @param pkID
	 */
	public void deleteGateway(Long pkID) {

		gatewayRepo.deleteById(pkID);
	}

	/**
	 * delete All Selected Gateway
	 * 
	 * @author Pratik chaudhari
	 * @param pkIDs
	 */
	public void deleteAllSelectedGateway(List<Long> pkIDs) {
		for (int i = 0; i < pkIDs.size(); i++) {
			gatewayRepo.deleteSelectedGateway(pkIDs.get(i));
		}
	}

	/**
	 * get Gateway Details By Id
	 * 
	 * @author Pratik chaudhari
	 * @param parseLong
	 * @return AssetGateway
	 */
	public AssetGateway getGatewayDetailsById(long parseLong) {

		if (gatewayRepo.existsById(parseLong)) {
			return gatewayRepo.getById(parseLong);
		}
		return null;
	}

	/**
	 * get All Gateway List For Dropdown
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return AssetGatewayBean
	 */
	public List<AssetGatewayBean> getAllGatewayListForDropdown(String fkUserId, String role) {

		if (role.equals(CommonConstants.superAdmin)) {
			return gatewayRepo.getGatewayListForDropdownSuperAdmin();
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForDropdownAdmin(fkID);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForDropdownUser(fkID);
		}
		return null;
	}

	/**
	 * get All Gateway Location List For Dropdown
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return String
	 */
	public List<String> getAllGatewayLocationListForDropdown(String fkUserId, String role) {

		if (role.equals(CommonConstants.superAdmin)) {
			return gatewayRepo.getGatewayLocationListForDropdownSuperAdmin();
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayLocationListForDropdownOrganization(fkID);
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayLocationListForDropdownAdmin(fkID);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return gatewayRepo.getGatewayLocationListForDropdownAdmin(adminid);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayLocationListForDropdownUser(fkID);
		}
		return null;
	}

	/**
	 * add Excel File Data To Database From SuperAdmin
	 * 
	 * @param file
	 * @return String
	 * @throws IOException
	 */
	public String addExcelFileDataToDatabaseFromSuperAdmin(MultipartFile file) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		List<AssetGateway> gatewayList = new ArrayList<>();
		int icnt = 0;
		for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				AssetGatewayStock gateway = new AssetGatewayStock();

				XSSFRow row = worksheet.getRow(index);

				String category = row.getCell(0, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String barCode = row.getCell(1, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String gatewayName = row
						.getCell(2, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String uniqueCode = row
						.getCell(3, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String organization = row
						.getCell(4, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				long organizationId = userRepo.getpkid(organization);

				List<AssetGatewayStock> barcodeList = assetGatewayStockRepository
						.findByGatewayBarcodeSerialNumber(barCode);
				List<AssetGatewayStock> uniqueCodeList = assetGatewayStockRepository
						.findByGatewayUniqueCodeMacId(uniqueCode);

				if (barcodeList.size() == 0 && uniqueCodeList.size() == 0) {
					gateway.setGatewayName(gatewayName);
					gateway.setAssetTagCategory(category);
					gateway.setGatewayBarcodeSerialNumber(barCode);
					gateway.setGatewayUniqueCodeMacId(uniqueCode.toUpperCase());
					gateway.setOrganizationid(organizationId);
					gateway.setStatus("Non-allocated");
					assetGatewayStockRepository.save(gateway);
					icnt++;
					System.out.println("Data is added successfully...");
				}

			}

		}
		int lastrow = worksheet.getPhysicalNumberOfRows();
		if (icnt == worksheet.getPhysicalNumberOfRows() - 1) {
			return "Devices sucessfully added";
		} else {

			return "only " + icnt + " Record are saved OR Given barcode and unique number is already exist....";
		}

	}

	/**
	 * add Excel File Data To Database
	 * 
	 * @param files
	 * @param username
	 * @return String
	 * @throws IOException
	 */
	public String addExcelFileDataToDatabase(MultipartFile files, String username) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		List<AssetGateway> gatewayList = new ArrayList<>();
		int icnt = 0;
		for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				AssetGateway gateway = new AssetGateway();

				XSSFRow row = worksheet.getRow(index);

				String category = row.getCell(0, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String barCode = null;
				try {
					barCode = row.getCell(1, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
				} catch (Exception e) {
					System.out.println(e);
				}

				String location = row.getCell(2, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String gatewayName = row
						.getCell(3, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String uniqueCode = row
						.getCell(4, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String timeZone = row.getCell(5, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String user = row.getCell(6, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String admin = row.getCell(7, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String organization = row
						.getCell(8, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String createdby = row
						.getCell(9, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				long userId = 0;
				long adminId = 0;
				long organizationId = 0;
				try {
					userId = userRepo.getpkid(user);
				} catch (Exception e) {
					System.out.println(e);
				}
				try {
					adminId = userRepo.getpkid(admin);
				} catch (Exception e) {
					System.out.println(e);
				}
				try {
					organizationId = userRepo.getpkid(organization);
				} catch (Exception e) {
					System.out.println(e);
				}
				if (uniqueCode.equals("")) {
					break;
				}

				List<AssetGateway> barcodeList = gatewayRepo.findByGatewayBarcodeSerialNumber(barCode);
				List<AssetGateway> uniqueCodeList = gatewayRepo.findByGatewayUniqueCodeMacId(uniqueCode);
				SimpleDateFormat simpleTimeFormat1 = new SimpleDateFormat("HH:mm:ss");
				String currentDate1 = simpleTimeFormat1.format(new Date());
				SimpleDateFormat simpleTimeFormat11 = new SimpleDateFormat("yyyy:MM:dd");
				String currentDate2 = simpleTimeFormat11.format(new Date());

				if (barcodeList.size() == 0 && uniqueCodeList.size() == 0) {
					gateway.setGatewayName(gatewayName);
					gateway.setAssetTagCategory(category);
					gateway.setGatewayBarcodeSerialNumber(barCode);
					gateway.setGatewayUniqueCodeMacId(uniqueCode.toUpperCase());
					gateway.setWakeupTime(currentDate1);
					gateway.setTimeZone(timeZone);
					gateway.setOrganization(organization);
					gateway.setFkOrganizationId(organizationId);

					gateway.setGatewayLocation(location);
					gateway.setAdmin(admin);
					gateway.setCreatedBy(username);
					gateway.setFkAdminId(adminId);
					gateway.setUser(user);

					gateway.setFkUserId(userId);
					gateway.setAssetGetwayEnable((long) 1);
					gatewayList.add(gateway);

					gatewayRepo.save(gateway);
					icnt++;
					assetGatewayStockRepository.updatestatus(uniqueCode.toUpperCase());

				}

			}

		}
		int lastrow = worksheet.getPhysicalNumberOfRows();
		if (icnt == lastrow - 1) {
			return "Devices sucessfully added";
		} else {

			return "only " + icnt + " Record are saved OR Given barcode and unique number is already exist....";
		}

	}

	/**
	 * get All Gateway List With Pagination
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @return AssetGatewayBean
	 */
	public Page<AssetGatewayBean> getAllGatewayListWithPagination(String fkUserId, String role, Pageable pageable) {
		if (role.equals(CommonConstants.superAdmin)) {
			return gatewayRepo.getGatewayListForSuperAdminWithPagination(pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForOrganizationWithPagination(fkID, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForAdminWithPagination(fkID, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return gatewayRepo.getGatewayListForUserWithPagination(fkID, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			return gatewayRepo.getGatewayListForAdminWithPagination(adminId, pageable);
		}
		return null;

	}

	/**
	 * use to enable gateway
	 * 
	 * @author pratik chaudhari
	 * @param adminid
	 */
	private void Enablegetway(long adminid) {
		List<String> list = new ArrayList<String>();
		list = gatewayRepo.findAllGateways(adminid);

		System.out.println(list);

		for (int i = 0; i < list.size(); i++) {

			System.out.println("&&&&&&&&&&&&&&&&&&&&&" + list.get(i));
			if (GetWaytagDetailsCameOrNot(adminid, list.get(i)) == true) {
				gatewayRepo.switchOn(list.get(i));
				System.out.println("meter is ON" + list.get(i));
			}
		}

	}

	/**
	 * Get Way tag Details Came Or Not
	 * 
	 * @param adminid
	 * @param tagname
	 * @return
	 */
	private boolean GetWaytagDetailsCameOrNot(long adminid, String tagname) {

		String timezone = gatewayRepo.getTimeZone(adminid);
		ZoneId zid = ZoneId.of(timezone);

		LocalDateTime currentdate = LocalDateTime.now(zid);
		LocalDateTime fromdate = currentdate.minusMinutes(10);
		int icnt = 0;
		System.out.println("fromdate" + fromdate + "todate" + currentdate);
		String Tagname = virtualtrackingRepo.findByCurrentDateforGateway(adminid, fromdate.toString(),
				currentdate.toString(), tagname);

		if (Tagname == null) {
			icnt--;
		} else {
			icnt++;

		}

		if (icnt < 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * DisableGetway
	 * 
	 * @author Pratik chaudhari
	 * @param hours
	 * @param adminid
	 */
	private void DisableGetway(long hours, long adminid) {

		List<String> list = new ArrayList<String>();
		list = gatewayRepo.findAllGetWay(adminid);

		System.out.println(list);
		String GetwayName;
		for (int i = 0; i < list.size(); i++) {

			GetwayName = list.get(i);
			System.out.println("&&&&&&&&&&&&&&&&&&&&&" + GetwayName);
			if (checkGetWayEnableoRDisable(hours, adminid, GetwayName) == false) {
				gatewayRepo.switchoff(GetwayName);
				System.out.println("Getway is off due to data is not geting" + GetwayName);
			}
		}

	}

	/**
	 * check Get Way Enable oR Disable
	 * 
	 * @param hours
	 * @author Pratik chaudhari
	 * @param adminid
	 * @param getwayName
	 * @return boolean
	 * @throws UsernameNotFoundException
	 */
	private boolean checkGetWayEnableoRDisable(long hours, long adminid, String getwayName)
			throws UsernameNotFoundException {

		String timezone = gatewayRepo.getTimeZone(adminid);
		ZoneId zid = ZoneId.of(timezone);

		LocalDateTime currentdate = LocalDateTime.now(zid);

		if (hours != 0) {

			LocalDateTime formdate = currentdate.minusHours(hours);
			System.out.println("fromdate" + formdate);
			System.out.println("current date" + currentdate);
			String tempbar = null;
			try {
				tempbar = virtualtrackingRepo.finddatabetweenDatexxl(adminid, formdate.toString(),
						currentdate.toString(), getwayName);
			} catch (Exception e) {
				System.out.println(e);
			}

			System.out.println("tempbar" + tempbar);
			if (tempbar == null) {
				return false;
			} else {
				return true;
			}
		} else {
			LocalDateTime formdate = currentdate.minusHours(22); // .minusDays(2)
			System.out.println("fromdate48" + formdate);
			System.out.println("current date" + currentdate);
			String tempbar = gatewayRepo.finddatabetweenDate(adminid, formdate, currentdate, getwayName);
			if (tempbar == null) {
				return false;
			} else {
				return true;
			}

		}

	}

	/**
	 * get Working And Non Working Count
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ResponseGetwayWorkingNonWorkingbean
	 */
	public ResponseGetwayWorkingNonWorkingbean getWorkingAndNonWorkingCount(String fkUserId, String role,
			String category) {
		long workingGetway = 1;
		long NonWorkingGetway = 0;
		if (role.equals(CommonConstants.superAdmin)) {

			workingGetway = gatewayRepo.getWorkingCountforSuperAdmin(workingGetway, category);
			NonWorkingGetway = gatewayRepo.getNonworkingCountforSuperAdmin(NonWorkingGetway, category);
			ResponseGetwayWorkingNonWorkingbean bean = new ResponseGetwayWorkingNonWorkingbean();
			bean.setWorkingGatwayCount(workingGetway);
			bean.setNonWorkingGatwayCount(NonWorkingGetway);
			return bean;
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);

			workingGetway = gatewayRepo.getWorkingCountforOrganization(workingGetway, fkID, category);
			NonWorkingGetway = gatewayRepo.getNonworkingCountforOrganization(NonWorkingGetway, fkID, category);
			ResponseGetwayWorkingNonWorkingbean bean = new ResponseGetwayWorkingNonWorkingbean();
			bean.setWorkingGatwayCount(workingGetway);
			bean.setNonWorkingGatwayCount(NonWorkingGetway);
			System.out.println("bean" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			long hours = 0;
			try {
				hours = limitservice.gethoursLimitforGateway(fkID);
			} catch (Exception e) {
				System.out.println(e);
			}

			System.out.println("hours" + hours);
			Enablegetway(fkID);
			DisableGetway(hours, fkID);

			workingGetway = gatewayRepo.getWorkingCountforAdmin(workingGetway, fkID, category);
			NonWorkingGetway = gatewayRepo.getNonworkingCountforAdmin(NonWorkingGetway, fkID, category);
			ResponseGetwayWorkingNonWorkingbean bean = new ResponseGetwayWorkingNonWorkingbean();
			bean.setWorkingGatwayCount(workingGetway);
			bean.setNonWorkingGatwayCount(NonWorkingGetway);
			System.out.println("bean" + bean);
			return bean;
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			workingGetway = gatewayRepo.getWorkingCountforUser(workingGetway, fkID, category);
			NonWorkingGetway = gatewayRepo.getNonworkingCountforUser(NonWorkingGetway, fkID, category);
			ResponseGetwayWorkingNonWorkingbean bean = new ResponseGetwayWorkingNonWorkingbean();
			bean.setWorkingGatwayCount(workingGetway);
			bean.setNonWorkingGatwayCount(NonWorkingGetway);
			return bean;
		}

		if (role.equals(CommonConstants.empUser)) {
			long AdminId = employeeUserRepo.getAdminId(fkUserId);

			workingGetway = gatewayRepo.getWorkingCountforAdmin(workingGetway, AdminId, category);
			NonWorkingGetway = gatewayRepo.getNonworkingCountforAdmin(NonWorkingGetway, AdminId, category);
			ResponseGetwayWorkingNonWorkingbean bean = new ResponseGetwayWorkingNonWorkingbean();
			bean.setWorkingGatwayCount(workingGetway);
			bean.setNonWorkingGatwayCount(NonWorkingGetway);
			System.out.println("bean" + bean);
			return bean;
		}
		return null;

	}

	/**
	 * use to converet bean
	 * 
	 * @author Pratik Chaudhari
	 * @param list
	 * @return ResponseworkingNonworkinGatewaybean
	 */
	public List<ResponseworkingNonworkinGatewaybean> convertbean(List<AssetGateway> list) {
		List<ResponseworkingNonworkinGatewaybean> listbean = new ArrayList<ResponseworkingNonworkinGatewaybean>();

		for (int i = 0; i < list.size(); i++) {
			ResponseworkingNonworkinGatewaybean bean = new ResponseworkingNonworkinGatewaybean();
			bean.setAdmin(list.get(i).getAdmin());
			bean.setAssetGetwayEnable(list.get(i).getAssetGetwayEnable());
			bean.setAssetTagCategory(list.get(i).getAssetTagCategory());
			bean.setDatetime(list.get(i).getDatetime());
			bean.setFkAdminId(list.get(i).getFkAdminId());
			bean.setFkUserId(list.get(i).getFkUserId());
			bean.setGatewayBarcodeSerialNumber(list.get(i).getGatewayBarcodeSerialNumber());
			bean.setGatewayId(list.get(i).getGatewayId());
			bean.setGatewayLocation(list.get(i).getGatewayLocation());
			bean.setGatewayName(list.get(i).getGatewayName());
			bean.setGatewayUniqueCodeMacId(list.get(i).getGatewayUniqueCodeMacId());
			bean.setTimeZone(list.get(i).getTimeZone());
			bean.setUser(list.get(i).getUser());
			bean.setWakeupTime(list.get(i).getWakeupTime());
			if (list.get(i).getAssetGetwayEnable() == 1) {
				bean.setWorkingorNonworking("Working");
			} else {
				bean.setWorkingorNonworking("Non-Working");
			}
			listbean.add(bean);
		}

		return listbean;

	}

	/**
	 * get All Gateway List Cheacking
	 * 
	 * @param fkUserId
	 * @param role
	 * @return ResponseworkingNonworkinGatewaybean
	 */
	public List<ResponseworkingNonworkinGatewaybean> getAllGatewayListCheacking(String fkUserId, String role) {
		long fkid = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			List<AssetGateway> list = gatewayRepo.findAll();
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;
		}
		if (role.equals(CommonConstants.admin)) {
			List<AssetGateway> list = gatewayRepo.getalllistforAdmin(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;
		}
		if (role.equals(CommonConstants.user)) {
			List<AssetGateway> list = gatewayRepo.getalllistforUser(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;

		}

		return null;
	}

	/**
	 * get All Gateway List Working Working Gateway
	 * 
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ResponseworkingNonworkinGatewaybean
	 */
	public List<ResponseworkingNonworkinGatewaybean> getAllGatewayListWorkingWorkingGateway(String fkUserId,
			String role) {

		long fkid = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			List<AssetGateway> list = gatewayRepo.getAllWorkingGatewayListxl();
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;
		}
		if (role.equals(CommonConstants.organization)) {
			List<AssetGateway> list = gatewayRepo.getalllistWorkingGatewayforOrganization(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;
		}
		if (role.equals(CommonConstants.admin)) {
			List<AssetGateway> list = gatewayRepo.getalllistWorkingGatewayforAdmin(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;
		}
		if (role.equals(CommonConstants.user)) {
			List<AssetGateway> list = gatewayRepo.getalllistWorkingGatewayforUser(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;

		}
		if (role.equals(CommonConstants.empUser)) {
			List<AssetGateway> list = gatewayRepo.getalllistWorkingGatewayforAdmin(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;

		}

		return null;
	}

	/**
	 * get All Gateway List Non-WorkingGateway
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ResponseworkingNonworkinGatewaybean
	 */
	public List<ResponseworkingNonworkinGatewaybean> getAllGatewayListNonWorkingGateway(String fkUserId, String role) {
		long fkid = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			List<AssetGateway> list = gatewayRepo.getAllNonWorkingGatewayListxl();
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;
		}
		if (role.equals(CommonConstants.organization)) {
			List<AssetGateway> list = gatewayRepo.getalllistNonWorkingGatewayforOrganization(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;
		}
		if (role.equals(CommonConstants.admin)) {
			List<AssetGateway> list = gatewayRepo.getalllistNonWorkingGatewayforAdmin(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;
		}
		if (role.equals(CommonConstants.user)) {
			List<AssetGateway> list = gatewayRepo.getalllistNonWorkingGatewayforUser(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;

		}
		if (role.equals(CommonConstants.empUser)) {
			List<AssetGateway> list = gatewayRepo.getalllistNonWorkingGatewayforAdmin(fkid);
			List<ResponseworkingNonworkinGatewaybean> listbean = convertbean(list);
			return listbean;

		}
		return null;
	}

	/**
	 * use to change possition of gatway image on gatewa name
	 * 
	 * @param gatewayName
	 * @param x
	 * @param y
	 * @param fkUserId
	 * @param role
	 * @return String
	 */
	public String changePossitions(String gatewayName, long x, long y, String fkUserId, String role) {

		long fkid = Long.parseLong(fkUserId);

		if (role.equals(CommonConstants.superAdmin)) {
			gatewayRepo.changePossitions(x, y, gatewayName);
			return "changed";
		}
		if (role.equals(CommonConstants.organization)) {
			gatewayRepo.changePossitionsForOrganization(x, y, gatewayName, fkid);
			return "changed";

		}
		if (role.equals(CommonConstants.admin)) {
			gatewayRepo.changePossitionsForAdmin(x, y, gatewayName, fkid);
			return "changed";

		}
		if (role.equals(CommonConstants.user)) {
			gatewayRepo.changePossitionsForUser(x, y, gatewayName, fkid);
			return "changed";

		}
		return "you don't have permission to access";

	}

	/**
	 * get All Gateway List validation
	 * 
	 * @author Pratik chaudhari
	 * @return AssetGateway
	 */
	public List<AssetGateway> getAllGatewayListvalidation() {

		return gatewayRepo.findAll();
	}

	/**
	 * load AssetGateway From SuperAdmin DataExcel
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 */
	public InputStream loadAssetGatewayFromSuperAdminDataExcel(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			List<AssetGatewayStock> Gatewaylist = assetGatewayStockRepository
					.AssetTagFromSuperAdminForOrganizationExportDownload(fkID, category);
			if (Gatewaylist.size() > 0) {
				ByteArrayInputStream in = AssetGatewaylistFromSuperAdminHelper.StockExcelForOrganization(Gatewaylist);
				return in;
			}
		}

		return null;
	}
	
	
	
	
	
	public AssetGateway getGatewayDetailsByBarcodeNumber(String barcodeNo)
	{
		long barcodeNumber = Long.parseLong(barcodeNo);
		//List<AssetGateway> assetGateway = gatewayRepo.getGatewayDetailsByBarcodeNumber(barcodeNumber);
		
		
		AssetGateway assetGateway = gatewayRepo.getGatewayDetailsByBarcodeNumber(barcodeNumber);
		
		return assetGateway;
		
	}
	

}
