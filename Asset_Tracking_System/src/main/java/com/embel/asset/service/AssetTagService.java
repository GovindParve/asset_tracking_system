package com.embel.asset.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.embel.asset.bean.AssetTagBean;
import com.embel.asset.bean.ResponseAgedTagBean;
import com.embel.asset.bean.ResponseAssetTagBean;
import com.embel.asset.bean.ResponseAssetTagWorkingNonWorkinCountBean;
import com.embel.asset.dto.AssetTagDto;
import com.embel.asset.entity.AssetTag;
import com.embel.asset.entity.AssetTagHistory;
import com.embel.asset.entity.AssetTagStock;
import com.embel.asset.helper.AssetTagFromSuperAdminHelper;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.helper.WorkingNonworkingTagReportHelper;
import com.embel.asset.repository.AssetTagHistoryRepository;
import com.embel.asset.repository.AssetTagRepository;
import com.embel.asset.repository.AssetTagStockRepository;
import com.embel.asset.repository.AssetTrackingRepository;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.UserRepository;
import com.embel.asset.repository.VirtualTrackingDetailsRepository;

@Service
public class AssetTagService {

	@Autowired
	AssetTagRepository tagRepository;
	@Autowired
	AssetTrackingRepository trackingRepo;
	@Autowired
	AssetTagHistoryRepository tagHistoryRepo;
	@Autowired
	limitsservice limitservice;
	@Autowired
	UserRepository userRepo;
	@Autowired
	EmployeUserRepository employeeUserRepo;
	@Autowired
	AssetTagStockRepository assetTagStockRepo;

	@Autowired
	VirtualTrackingDetailsRepository virtualTrackingDetailsRepository;

	/**
	 * use to add asset tag
	 * 
	 * @author Pratik Chaudhari
	 * @param dto
	 * @return String
	 */
	public String addAssetTag(AssetTagDto dto)
	{
		long assetBarcodeNumber = 1000l;
		
		
		AssetTag tag = new AssetTag();
		if (!dto.getAssetUniqueCodeMacId().equals(null) && dto.getAssetTagCategory().equals("BLE")) {
			String barcodeList = null;
			String uniqueCodeList = null;
			String imei = null;

			try {
				
				List<Long> assetBarcodeNumberList = tagRepository.getLastAssetBarcodeNumber();
				for (int j = 0; j < assetBarcodeNumberList.size(); j++)
				{
					//AssetTag assetTagEntity =  assetBarcodeNumberList.get(j);
					//assetBarcodeNumber = assetTagEntity.getAssetBarcodeNumber();
					assetBarcodeNumber = assetBarcodeNumberList.get(j);
					assetBarcodeNumber++;
				}

				uniqueCodeList = tagRepository
						.findByAssetUniqueCodeMacIdXL(String.valueOf(dto.getAssetUniqueCodeMacId()));
				imei = tagRepository.findByimei(dto.getAssetIMEINumber());

			} catch (Exception e) {
				System.out.println(e);
			}

			if (uniqueCodeList == null)// barcodeList==null &&
			{
				String status = "Not-allocated";
				tag.setAssetTagName(dto.getAssetTagName());
				
				tag.setAssetBarcodeNumber(assetBarcodeNumber);
				
				tag.setAdmin(dto.getAdmin());
				tag.setUser(dto.getUser());
				tag.setOrganization(dto.getOrganization());
				tag.setFkOrganizationId(dto.getFkOrganizationId());
				tag.setFkAdminId(dto.getFkAdminId());
				tag.setFkUserId(dto.getFkUserId());
				tag.setAssetIMSINumber("BLE_TAG" + System.currentTimeMillis());
				tag.setAssetBarcodeSerialNumber(dto.getAssetBarcodeSerialNumber());
				tag.setAssetUniqueCodeMacId(dto.getAssetUniqueCodeMacId().toUpperCase());
				tag.setAssetLocation(dto.getAssetLocation());
				tag.setAssetIMEINumber("BLE_TAG" + System.currentTimeMillis());
				tag.setWakeupTime(dto.getWakeupTime());
				tag.setCreatedBy(dto.getCreatedBy());
				tag.setDatetime(dto.getDatetime());
//				tag.setDate(dto.getDate());
//				tag.setTime(dto.getTime());
				tag.setTimeZone(dto.getTimeZone());
				tag.setAssetSimNumber(dto.getAssetSimNumber());
				tag.setAssetTagCategory(dto.getAssetTagCategory()); // +" "+ dto.getAssetTagSubCategory()
				tag.setStatus(status);
				tag.setAssetTagEnable((long) 1);
				tagRepository.save(tag);
				assetTagStockRepo.updateStockStatus(dto.getAssetUniqueCodeMacId().toUpperCase());
				return "Asset Tag generated successfully...!";

			} else {
				return "Dublicate!";
			}

		} else {

			String imei = null;
			String imsi = null;
			try {
				imei = tagRepository.findByimei(dto.getAssetIMEINumber());
			} catch (Exception e) {
				System.out.println(e);
			}
			try {
				imsi = tagRepository.findByimsi(dto.getAssetIMSINumber());
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (imei == null && imsi == null) {
				String status = "Not-allocated";
				tag.setAssetTagName(dto.getAssetTagName());
				
				tag.setAssetBarcodeNumber(assetBarcodeNumber);
				
				tag.setAdmin(dto.getAdmin());
				tag.setUser(dto.getUser());
				tag.setOrganization(dto.getOrganization());
				tag.setFkOrganizationId(dto.getFkOrganizationId());
				tag.setFkAdminId(dto.getFkAdminId());
				tag.setFkUserId(dto.getFkUserId());
				tag.setAssetIMSINumber(dto.getAssetIMSINumber());
				tag.setAssetBarcodeSerialNumber(dto.getAssetBarcodeSerialNumber());
				tag.setAssetUniqueCodeMacId("GPS_TAG" + System.currentTimeMillis());
				tag.setAssetLocation(dto.getAssetLocation());
				tag.setAssetIMEINumber(dto.getAssetIMEINumber());
				tag.setWakeupTime(dto.getWakeupTime());
				tag.setDatetime(dto.getDatetime());
//				tag.setDate(dto.getDate());
//				tag.setTime(dto.getTime());
				tag.setTimeZone(dto.getTimeZone());
				tag.setAssetSimNumber(dto.getAssetSimNumber());
				tag.setAssetTagCategory(dto.getAssetTagCategory()); // +" "+ dto.getAssetTagSubCategory()
				tag.setStatus(status);
				tag.setAssetTagEnable((long) 1);
				tagRepository.save(tag);
				return "Asset Tag generated successfully...!";

			} else {
				return "Dublicate";
			}

		}

	}

	/**
	 * get All Tag List
	 * 
	 * @param fkUserId
	 * @param role
	 * @return AssetTagBean
	 */
	public List<AssetTagBean> getAllTagList(String fkUserId, String role) {

		if (role.equals(CommonConstants.superAdmin)) {
			return tagRepository.gettagListForSuperAdmin();
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.gettagListForAdmin(fkID);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.getTagListForUser(fkID);
		}
		return null;
	}

	/**
	 * use to get tag count
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	public String tagCount(String fkUserId, String role, String category) {

		if (role.equals(CommonConstants.superAdmin)) {
			return tagRepository.getCountForSuperAdmin(category);
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.getCountForOrganization(fkID, category);
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.getCountForAdmin(fkID, category);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.getCountForUser(fkID, category);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			return tagRepository.getCountForEmpuser(adminId, category);
		}
		return null;
	}

	/**
	 * use to get tag for edit
	 * 
	 * @author Pratik chaudhari
	 * @param id
	 * @return AssetTagBean
	 */
	public List<AssetTagBean> getTagForEdit(Long id) {

		return tagRepository.gettagForEdit(id);
	}

	/**
	 * use to gate update tag
	 * 
	 * @param dto
	 * @return String
	 */
	public String updateTag(AssetTagDto dto) {
		AssetTag updateTag = tagRepository.getOne(dto.getAssetTagId());
//		
//		String barcodeList=null;
//		String uniqueCodeList=null;
//		//String imei=null;
//		
//		try {
//			 barcodeList = tagRepository.getcountbarcodeList(dto.getAssetBarcodeSerialNumber());
//			 uniqueCodeList  = tagRepository.getcountuniqueCodeList(String.valueOf(dto.getAssetUniqueCodeMacId()));
//			 //imei=tagRepository.findByimei(dto.getAssetIMEINumber());
//	
//		} catch (Exception e) {
//			System.out.println(e);
//		}

		// Asset TagHistory by pratik chaudhari
		AssetTagHistory tagHistory = new AssetTagHistory();
		tagHistory.setAssetTagName(updateTag.getAssetTagName());
		tagHistory.setAdmin(updateTag.getAdmin());
		tagHistory.setOrganization(updateTag.getOrganization());
		tagHistory.setFkOrganizationId(updateTag.getFkOrganizationId());
		tagHistory.setUser(updateTag.getUser());
		tagHistory.setFkUserId(updateTag.getFkUserId());
		tagHistory.setFkAdminId(updateTag.getFkAdminId());
		tagHistory.setAssetIMSINumber(updateTag.getAssetIMSINumber());
		tagHistory.setAssetBarcodeSerialNumber(updateTag.getAssetBarcodeSerialNumber());
		tagHistory.setAssetUniqueCodeMacId(updateTag.getAssetUniqueCodeMacId().toUpperCase());
		tagHistory.setAssetLocation(updateTag.getAssetLocation());
		tagHistory.setAssetIMEINumber(updateTag.getAssetIMEINumber());
		tagHistory.setWakeupTime(updateTag.getWakeupTime());
		tagHistory.setDate(updateTag.getDatetime());
		tagHistory.setCreatedBy(dto.getCreatedBy());
		tagHistory.setTimeZone(updateTag.getTimeZone());
		tagHistory.setAssetSimNumber(updateTag.getAssetSimNumber());
		tagHistory.setAssetTagEnable((long) 1);
		tagHistory.setAssetTagCategory(updateTag.getAssetTagCategory());

		tagHistoryRepo.save(tagHistory);

//		if(barcodeList.equals("1") && uniqueCodeList.equals("1"))
//		{
		updateTag.setAssetTagName(dto.getAssetTagName());
		updateTag.setAdmin(dto.getAdmin());
		updateTag.setOrganization(dto.getOrganization());
		updateTag.setFkOrganizationId(dto.getFkOrganizationId());
		updateTag.setUser(dto.getUser());
		updateTag.setFkUserId(dto.getFkUserId());
		updateTag.setFkAdminId(dto.getFkAdminId());
		updateTag.setAssetIMSINumber(dto.getAssetIMSINumber());
		updateTag.setAssetBarcodeSerialNumber(dto.getAssetBarcodeSerialNumber());
		updateTag.setAssetUniqueCodeMacId(dto.getAssetUniqueCodeMacId().toUpperCase());
		updateTag.setAssetLocation(dto.getAssetLocation());
		updateTag.setAssetIMEINumber(dto.getAssetIMEINumber());
		updateTag.setWakeupTime(dto.getWakeupTime());
		updateTag.setDatetime(dto.getDatetime());
		// updateTag.setDate(dto.getDate());
		// updateTag.setTime(dto.getTime());
		updateTag.setTimeZone(dto.getTimeZone());
		updateTag.setAssetSimNumber(dto.getAssetSimNumber());
		updateTag.setCreatedBy(dto.getCreatedBy());
		updateTag.setAssetTagEnable((long) 1);
		updateTag.setAssetTagCategory(dto.getAssetTagCategory());// +" "+dto.getAssetTagSubCategory()
		// updateTag.setStatus(status);
		tagRepository.save(updateTag);
		return "Tag Updated Successfully....!";
//		//}
//		else {
//			return "Tag macid or scerial No Alrady Exit !";
//		}
	}

	/**
	 * get Tag Details By Id
	 * 
	 * @param parseLong
	 * @return AssetTag
	 */
	public AssetTag getTagDetailsById(long parseLong) {
		if (tagRepository.existsById(parseLong)) {
			return tagRepository.getById(parseLong);
		}
		return null;
	}

	/**
	 * get Not Allocated Tag List For Product
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ResponseAssetTagBean
	 */
	public List<ResponseAssetTagBean> getNotAllocatedTagListForProduct(String fkUserId, String role, String category) {
		if (category.equals("BLE")) {
			if (role.equals(CommonConstants.superAdmin)) {
				return tagRepository.getNotAllocatedTagListForProduct(category);
			}

			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				return tagRepository.getNotAllocatedStatusTagListForProductOrganization(category, fkID);
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				return tagRepository.getNotAllocatedStatusTagListForProductAdmin(category, fkID);
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				return tagRepository.getNotAllocatedStatusTagListForProductAdmin(category, adminid);
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				return tagRepository.getNotAllocatedStatusTagListForProductUser(category, fkID);
			}

		} else {
			if (role.equals(CommonConstants.superAdmin)) {
				return tagRepository.getNotAllocatedTagListForProductforGPS(category);
			}

			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				return tagRepository.getNotAllocatedStatusTagListForProductOrganizationforGPS(category, fkID);
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				return tagRepository.getNotAllocatedStatusTagListForProductAdminforGPS(category, fkID);
			}
			if (role.equals(CommonConstants.empUser)) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				return tagRepository.getNotAllocatedStatusTagListForProductAdminforGPS(category, adminid);
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				return tagRepository.getNotAllocatedStatusTagListForProductUserforGPS(category, fkID);
			}

		}

		return null;
	}

	/**
	 * delete All Selected Tag
	 * 
	 * @author Pratik chaudhari
	 * @param pkID
	 */
	public void deleteAllSelectedTag(List<String> pkID) {

		for (int i = 0; i < pkID.size(); i++) {
			Long fkID = Long.parseLong(pkID.get(i));
			tagRepository.deleteSelectedTag(fkID);
		}
	}

	/**
	 * get All Tag List For Dropdown
	 * 
	 * @param fkUserId
	 * @param role
	 * @return AssetTagBean
	 */
	public List<AssetTagBean> getAllTagListForDropdown(String fkUserId, String role) {
		if (role.equals(CommonConstants.superAdmin)) {
			return tagRepository.gettagListForDropdownSuperAdmin();
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.gettagListForDropdownOrganization(fkID);
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.gettagListForDropdownAdmin(fkID);
		}

		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.getTagListForDropdownUser(fkID);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);

			return tagRepository.gettagListForDropdownAdmin(adminId);
		}
		return null;
	}

	/**
	 * add Excel File Data To Database From SuperAdmin
	 * 
	 * @param files
	 * @return String
	 * @throws IOException
	 */
	public String addExcelFileDataToDatabaseFromSuperAdmin(MultipartFile files) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		System.out.println("worksheet.getPhysicalNumberOfRows()" + worksheet.getPhysicalNumberOfRows());
		List<AssetTag> tagList = new ArrayList<>();
		int icnt = 0;
		for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {

			if (index > 0) {
				AssetTagStock tag = new AssetTagStock();

				XSSFRow row = worksheet.getRow(index);

				String tagName = row.getCell(0, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String category = row.getCell(1, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String barCode = row.getCell(2, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String uniqueCode = row
						.getCell(3, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				long imeiNumber = (long) row
						.getCell(4, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getNumericCellValue();

				long imsiNumber = (long) row
						.getCell(5, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getNumericCellValue();
				String organization = row
						.getCell(6, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				long organizationId = 0;
				try {
					organizationId = userRepo.getpkid(organization);
				} catch (Exception e) {
					// TODO: handle exception
				}

				if (organizationId == 0) {
					break;
				}
				AssetTagStock barcodeList = assetTagStockRepo.findByAssetBarcodeSerialNumber(barCode);
				AssetTagStock uniqueCodeList = assetTagStockRepo.findByAssetUniqueCodeMacId(String.valueOf(uniqueCode));

				if (barcodeList == null && uniqueCodeList == null) {
					tag.setAssetTagName(tagName.replaceAll(" ", ""));
					tag.setAssetTagCategory(category);
					tag.setAssetBarcodeSerialNumber(barCode);
					tag.setAssetUniqueCodeMacId(String.valueOf(uniqueCode).toUpperCase());
					tag.setAssetIMEINumber(String.valueOf(imeiNumber));
					tag.setOrganization_id(organizationId);
					tag.setAssetIMSINumber(String.valueOf(imsiNumber));
					tag.setStatus("Non-allocated");
					assetTagStockRepo.save(tag);
					icnt++;
					System.out.println("Data is added successfully...");
				}

			}
		} // end of for
		int lastrow = worksheet.getPhysicalNumberOfRows();
		if (icnt == lastrow - 1) {
			return "Devices sucessfully added";
		} else {

			return " only " + icnt + "Record are saved OR Given barcode and unique number is already exist..";
		}
	}

	/**
	 * add Excel File Data To Database
	 * 
	 * @param files
	 * @param username
	 * @return String
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public String addExcelFileDataToDatabase(MultipartFile files, String username)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		List<AssetTag> tagList = new ArrayList<>();
		int icnt = 0;
		for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				AssetTag tag = new AssetTag();

				String status = "Not-allocated";

				XSSFRow row = worksheet.getRow(index);
				// Integer id = (int) row.getCell(0).getNumericCellValue();

				// tag.setAssetTagId(id);

				/*
				 * long barcode = (long)
				 * row.getCell(3,org.apache.poi.ss.usermodel.Row.MissingCellPolicy.
				 * CREATE_NULL_AS_BLANK).getNumericCellValue();
				 * tag.setAssetBarcodeOrSerialNumber(String.valueOf(barcode));
				 */

				String tagName = row.getCell(0, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String category = row.getCell(1, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String barCode = row.getCell(2, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				// mac ID
				String uniqueCode = row
						.getCell(3, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String location = row.getCell(4, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				long imeiNumber = (long) row
						.getCell(5, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getNumericCellValue();
//				long wakeUp = (long) row.getCell(7,org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
				String timeZone = row.getCell(6, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				long simNumber = (long) row
						.getCell(7, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getNumericCellValue();
				long imsiNumber = (long) row
						.getCell(8, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getNumericCellValue();
				String admin = row.getCell(9, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String organization = row
						.getCell(10, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String user = row.getCell(11, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
//				long userId = (long) row.getCell(12,org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
				String createdby = row
						.getCell(12, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
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

				if (uniqueCode.equals("") && barCode.equals("")) {
					if (imeiNumber == 0) {
						break;
					}

					System.out.println(" || " + tagName + " || ");
					System.out.println(" || " + category + " || ");
					System.out.println(" || " + barCode + " || ");
					System.out.println(" || " + uniqueCode + " || ");
					System.out.println(" || " + location + " || ");
					System.out.println(" || " + imeiNumber + " || ");
//					System.out.println(" || " + wakeUp + " || ");
					System.out.println(" || " + timeZone + " || ");
					System.out.println(" || " + simNumber + " || ");
					System.out.println(" || " + imsiNumber + " || ");
					System.out.println(" || " + admin + " || ");
					System.out.println(" || " + adminId + " || ");
					System.out.println(" || " + user + " || ");
					System.out.println(" || " + userId + " || ");

					AssetTag imeiNumberobj = tagRepository.getbyimeiNumber(imeiNumber);
					AssetTag imsiNumberobj = tagRepository.getbyImsiNumber(imsiNumber);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date registrationDate = new Date();
					System.out.println("Device registration date..." + registrationDate);

					String currentDate = simpleDateFormat.format(registrationDate);
					if (imeiNumberobj == null && imsiNumberobj == null) {
						tag.setAssetTagName(tagName.replaceAll(" ", ""));
						tag.setAssetTagCategory(category);
						tag.setAssetBarcodeSerialNumber(barCode);
						tag.setAssetUniqueCodeMacId(String.valueOf(uniqueCode).toUpperCase());
						tag.setAssetIMEINumber(String.valueOf(imeiNumber));

						tag.setWakeupTime(currentDate); // String.valueOf(wakeUp)
						tag.setTimeZone(timeZone);
						tag.setAssetSimNumber(String.valueOf(simNumber));
						tag.setAssetIMSINumber(String.valueOf(imsiNumber));
//						tag.setDateTime(new java.util.Date());
						tag.setDatetime(registrationDate);

						tag.setCreatedBy(username);
						tag.setAssetLocation(location);
						tag.setStatus(status);
						tag.setAdmin(admin);
						tag.setOrganization(organization);
						tag.setFkAdminId(adminId);
						tag.setUser(user);
						tag.setFkUserId(userId);
						tag.setFkOrganizationId(organizationId);
						tag.setAssetTagEnable((long) 1);
						tagList.add(tag);
						tagRepository.save(tag);
						icnt++;
						assetTagStockRepo.updateStockStatus(String.valueOf(uniqueCode).toUpperCase());
						System.out.println("Data is added successfully...");
					}
				} else {
					if (uniqueCode.equals("")) {
						break;
					}
					System.out.println(" || " + tagName + " || ");
					System.out.println(" || " + category + " || ");
					System.out.println(" || " + barCode + " || ");
					System.out.println(" || " + uniqueCode + " || ");
					System.out.println(" || " + location + " || ");
					System.out.println(" || " + imeiNumber + " || ");
//				System.out.println(" || " + wakeUp + " || ");
					System.out.println(" || " + timeZone + " || ");
					System.out.println(" || " + simNumber + " || ");
					System.out.println(" || " + imsiNumber + " || ");
					System.out.println(" || " + admin + " || ");
					System.out.println(" || " + adminId + " || ");
					System.out.println(" || " + user + " || ");
					System.out.println(" || " + userId + " || ");

					AssetTag barcodeList = tagRepository.findByAssetBarcodeSerialNumber(barCode);
					AssetTag uniqueCodeList = tagRepository.findByAssetUniqueCodeMacId(String.valueOf(uniqueCode));
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date registrationDate = new Date();
					System.out.println("Device registration date..." + registrationDate);

					String currentDate = simpleDateFormat.format(registrationDate);
					if (barcodeList == null && uniqueCodeList == null) {
						tag.setAssetTagName(tagName.replaceAll(" ", ""));
						tag.setAssetTagCategory(category);
						tag.setAssetBarcodeSerialNumber(barCode);
						tag.setAssetUniqueCodeMacId(String.valueOf(uniqueCode).toUpperCase());
						tag.setAssetIMEINumber(String.valueOf(imeiNumber));

						tag.setWakeupTime(currentDate); // String.valueOf(wakeUp)
						tag.setTimeZone(timeZone);
						tag.setAssetSimNumber(String.valueOf(simNumber));
						tag.setAssetIMSINumber(String.valueOf(imsiNumber));
//					tag.setDateTime(new java.util.Date());
						tag.setDatetime(registrationDate);

						tag.setCreatedBy(username);
						tag.setAssetLocation(location);
						tag.setStatus(status);
						tag.setAdmin(admin);
						tag.setOrganization(organization);
						tag.setFkAdminId(adminId);
						tag.setUser(user);
						tag.setFkUserId(userId);
						tag.setFkOrganizationId(organizationId);
						tag.setAssetTagEnable((long) 1);
						tagList.add(tag);
						tagRepository.save(tag);
						icnt++;
						assetTagStockRepo.updateStockStatus(String.valueOf(uniqueCode).toUpperCase());
						System.out.println("Data is added successfully...");
					}

//}catch(Exception e){
//	System.out.println("Exception is :"+e);
//}
//				else
//				{
//					System.out.println("Given barcode and unique number is already exist...");
//					return tagList;
//				}
				} // end of else
			}
		} // end of for
		int lastrow = worksheet.getPhysicalNumberOfRows();
		if (icnt == lastrow - 1) {
			return "Devices sucessfully added";
		} else {

			return " only " + icnt + "Record are saved OR Given barcode and unique number is already exist..";
		}
//			return tagList;
	}

	/**
	 * get All Tag List With Pagination
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @param category
	 * @return AssetTagBean
	 */
	public Page<AssetTagBean> getAllTagListWithPagination(String fkUserId, String role, Pageable pageable,
			String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			System.out.println("inside service categoery@@@@@@" + "pagination");
			return tagRepository.gettagListForSuperAdminWithPagination(category, pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.gettagListForOrganizationWithPagination(category, fkID, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.gettagListForAdminWithPagination(category, fkID, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.getTagListForUserWithPagination(category, fkID, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);

			return tagRepository.gettagListForAdminWithPagination(category, adminId, pageable);
		}

		return null;
	}

	/**
	 * get All Tag List With Pagination For Gps
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @return AssetTag
	 */
	public Page<AssetTag> getAllTagListWithPaginationForGps(String fkUserId, String role, Pageable pageable) {
		if (role.equals(CommonConstants.superAdmin)) {
			System.out.println("inside service categoery@@@@@@" + "pagination");
			return tagRepository.gettagListForSuperAdminWithPaginationForGps(pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.gettagListForOrganizationWithPaginationForGps(fkID, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.gettagListForAdminWithPaginationForGps(fkID, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.getTagListForUserWithPaginationForGps(fkID, pageable);
		}

		return null;

	}

	/**
	 * get All Tag List Category wise With Pagination
	 * 
	 * @param fkUserId
	 * @author Pratik chaudhari
	 * @param role
	 * @param assetTagCategory
	 * @param pageable
	 * @return AssetTagBean
	 */
	public Page<AssetTagBean> getAllTagListCategorywiseWithPagination(String fkUserId, String role,
			String assetTagCategory, Pageable pageable) {
		if (role.equals(CommonConstants.superAdmin)) {
			return tagRepository.gettagListForSuperAdminCategorywiseWithPagination(assetTagCategory, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.gettagListForAdminCategorywiseWithPagination(fkID, assetTagCategory, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			return tagRepository.getTagListForUserCategorywiseWithPagination(fkID, assetTagCategory, pageable);
		}
		return null;
	}

	/**
	 * check AssetTag Enable oR Disable
	 * 
	 * @author Pratik chaudhari
	 * @param hours
	 * @param adminid
	 * @param tagname
	 * @return boolean
	 */
	public boolean checkAssetTagEnableoRDisable(long hours, long adminid, String tagname) {

		String assetTagName1;
		String assetTagName2;

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String timezone = tagRepository.getTimeZone(adminid);
		ZoneId zid = ZoneId.of(timezone);
		LocalDateTime currentdate = LocalDateTime.now(zid);

		LocalDateTime twodaysearlierDate = currentdate.minusDays(2);
		LocalDateTime onedaysearlierDate = currentdate.minusDays(1);
		if (hours > 0) {

//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//			 
//	       String currentTime =String.valueOf(currentdate);
//	 
//	        
//	 
//	        LocalDateTime formdate = LocalDateTime.parse(currentTime,formatter);
//	 
//	        formdate=formdate.minusHours(hours);
//			//.................................
//			

			LocalDateTime formdate = currentdate.minusHours(hours);
			System.out.println("fromdate" + formdate);
			System.out.println("current date" + currentdate);
			String tempbar = null;
			try {
				// tempbar=trackingRepo.finddatabetweenDate(adminid,formdate,currentdate,userid);
				tempbar = virtualTrackingDetailsRepository.finddatabetweenDatexl(adminid, formdate.toString(),
						currentdate.toString(), tagname);
			} catch (Exception e) {
				System.out.println(e);
			}

			if (tempbar == null) {
				return false;
			} else {
				return true;
			}
		}

		else {
			int icnt = 0;
			assetTagName1 = virtualTrackingDetailsRepository.findBydate(adminid, twodaysearlierDate, tagname);

			assetTagName2 = virtualTrackingDetailsRepository.findBydate(adminid, onedaysearlierDate, tagname);
			if (assetTagName1 == null) {
				icnt++;
			}
			if (assetTagName2 == null) {
				icnt++;
			}
			if (icnt == 2) {
				return false;
			} else {
				return true;
			}

		}

	}

	/**
	 * use to check trackingDetails are Came Or Not
	 * 
	 * @param adminid
	 * @param tagname
	 * @return boolean
	 */
	private boolean trackingDetailsCameOrNot(long adminid, String tagname) {

		String timezone = tagRepository.getTimeZone(adminid);
		ZoneId zid = ZoneId.of(timezone);

		LocalDateTime currentdate = LocalDateTime.now(zid);
		LocalDateTime fromdate = currentdate.minusMinutes(10);
		System.out.println("currentdate" + currentdate);
		int icnt = 0;
		String Tagname = virtualTrackingDetailsRepository.findByCurrentDate(adminid, fromdate.toString(),
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
	 * use to enable asset tag enable switch
	 * 
	 * @author Pratik chaudhari
	 * @param adminid
	 * @throws ParseException
	 */
	public void assetEnableSwitch(long adminid) throws ParseException {
		List<String> list = new ArrayList<String>();
		list = tagRepository.findAllTags(adminid);

		System.out.println(list);
//		long userid;
		for (int i = 0; i < list.size(); i++) {

//						userid=list.get(i);	
			System.out.println("&&&&&&&&&&&&&&&&&&&&&" + list.get(i));
			if (trackingDetailsCameOrNot(adminid, list.get(i)) == true) {
				tagRepository.switchOn(list.get(i));
				System.out.println("meter is ON" + list.get(i));
			}
		}

	}

	/**
	 * asset Tag Disable Switch
	 * 
	 * @author Pratik chaudhari
	 * @param hours
	 * @param adminid
	 * @throws ParseException
	 */
	public void assetTagDisableSwitch(long hours, long adminid) throws ParseException {
		List<String> list = new ArrayList<String>();
		// list=tagRepository.findAllUser(adminid);
		list = tagRepository.findAllTags(adminid);

		System.out.println(list);
		long userid;
		for (int i = 0; i < list.size(); i++) {

			// userid=list.get(i);
			System.out.println("&&&&&&&&&&&&&&&&&&&&&" + list.get(i));
			if (checkAssetTagEnableoRDisable(hours, adminid, list.get(i)) == false) // if false data is not comeing
			{
				tagRepository.switchoff(list.get(i));
				System.out.println("Tag is off due to data is not geting" + list.get(i));
			}
		}

	}

	/**
	 * Working And Non Working Tag Count
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ResponseAssetTagWorkingNonWorkinCountBean
	 * @throws ParseException
	 */
	public ResponseAssetTagWorkingNonWorkinCountBean WorkingAndNonWorkingTagCount(String fkUserId, String role,
			String category) throws ParseException {
		// String hours,
		String hours = null;
		try {
			hours = limitservice.getWorkingAndNonWorkingTag(fkUserId);
			if (hours == null) {
				hours = "5";

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		long lhours = Long.parseLong(hours);
		long assetTagWorking = 1;
		long assetTagNonworking = 0;

		if (role.equals(CommonConstants.superAdmin)) {

			assetTagWorking = tagRepository.AssetTagWorkingCountforSuperAdmin(assetTagWorking, category);
			assetTagNonworking = tagRepository.AssetTagNonWorkingCountforSuperAdmin(assetTagNonworking, category);
			ResponseAssetTagWorkingNonWorkinCountBean bean = new ResponseAssetTagWorkingNonWorkinCountBean();
			bean.setAssetTagWorkingCount(assetTagWorking);
			bean.setAssetTagNonWorkingCount(assetTagNonworking);

			return bean;
		}
		if (role.equals(CommonConstants.organization)) {

			Long fkID = Long.parseLong(fkUserId);
//			assetEnableSwitch(fkID);
//			assetTagDisableSwitch(lhours,fkID);

			assetTagWorking = tagRepository.AssetTagWorkingCountforOrganization(fkID, assetTagWorking, category);
			assetTagNonworking = tagRepository.AssetTagNonWorkingCountforOrganization(fkID, assetTagNonworking,
					category);
			ResponseAssetTagWorkingNonWorkinCountBean bean = new ResponseAssetTagWorkingNonWorkinCountBean();
			bean.setAssetTagWorkingCount(assetTagWorking);
			bean.setAssetTagNonWorkingCount(assetTagNonworking);

			return bean;
		}
		if (role.equals(CommonConstants.admin)) {

			Long fkID = Long.parseLong(fkUserId);
			assetEnableSwitch(fkID);
			assetTagDisableSwitch(lhours, fkID);

			assetTagWorking = tagRepository.AssetTagWorkingCountforAdmin(fkID, assetTagWorking, category);
			assetTagNonworking = tagRepository.AssetTagNonWorkingCountforAdmin(fkID, assetTagNonworking, category);
			ResponseAssetTagWorkingNonWorkinCountBean bean = new ResponseAssetTagWorkingNonWorkinCountBean();
			bean.setAssetTagWorkingCount(assetTagWorking);
			bean.setAssetTagNonWorkingCount(assetTagNonworking);

			return bean;
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			assetTagWorking = tagRepository.AssetTagWorkingCountforUser(fkID, assetTagWorking, category);
			assetTagNonworking = tagRepository.AssetTagNonWorkingCountforUser(fkID, assetTagNonworking, category);
			ResponseAssetTagWorkingNonWorkinCountBean bean = new ResponseAssetTagWorkingNonWorkinCountBean();
			bean.setAssetTagWorkingCount(assetTagWorking);
			bean.setAssetTagNonWorkingCount(assetTagNonworking);

			return bean;
		}

		if (role.equals(CommonConstants.empUser)) {

			long adminId = employeeUserRepo.getAdminId(fkUserId);
//			Long fkID = Long.parseLong(fkUserId);
//			assetEnableSwitch(fkID);
//			assetTagDisableSwitch(lhours,fkID);

			assetTagWorking = tagRepository.AssetTagWorkingCountforAdmin(adminId, assetTagWorking, category);
			assetTagNonworking = tagRepository.AssetTagNonWorkingCountforAdmin(adminId, assetTagNonworking, category);
			ResponseAssetTagWorkingNonWorkinCountBean bean = new ResponseAssetTagWorkingNonWorkinCountBean();
			bean.setAssetTagWorkingCount(assetTagWorking);
			bean.setAssetTagNonWorkingCount(assetTagNonworking);

			return bean;
		}
		return null;
	}
//.........check aged tag count

	/**
	 * check Aged AssetTag
	 * 
	 * @author Pratik chaudhari
	 * @param tagList
	 * @return String
	 */
	public List<String> checkAgedAssetTag(List<String> tagList) {
		int agedcount = 0;

		List<String> agesList = new ArrayList<String>();
//		 Map<ResponseAgedTagBean> bean2= new HashMap<ResponseAgedTagBean>();
		ResponseAgedTagBean bean = new ResponseAgedTagBean();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDateTime currentdate = LocalDateTime.now();

		LocalDateTime twodaysearlierDate = currentdate.minusDays(2);

		String result = null;
		for (int i = 0; i < tagList.size(); i++) {

			try {
				result = tagRepository.findBydate(twodaysearlierDate, currentdate, tagList.get(i));
			} catch (Exception e) {
				System.out.println(e);
			}

			System.out.println("result:::->" + result);
			if (result == null) {
//				bean.setAgedTagName(tagList.get(i));
				agesList.add(tagList.get(i));
//				agesList.add(bean);
//				agedcount++;
				System.out.println(agesList);
			}

		}

		return agesList;

	}

//.................getaged tag count 
	/**
	 * get Aged Tag Count for Admin
	 * 
	 * @author Pratik chaudhari
	 * @param userid
	 * @param formdate
	 * @param currentdate
	 * @return long
	 */
	public long getAgedTagCountforAdmin(long userid, LocalDateTime formdate, LocalDateTime currentdate) {
		long agedcount;
		List<String> tagList = tagRepository.getallocatedtagListForAdminfornotification(userid, formdate, currentdate);
		System.out.println("list@@@@@" + tagList);
		List<String> bean = checkAgedAssetTag(tagList);
		agedcount = bean.size();
		return agedcount;

	}
//.................................................................................

	/**
	 * get Aged Tag Count
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return String list of string
	 */
	public List<String> getAgedTagCount(String fkUserId, String role) {

		long userid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			List<String> tagList = tagRepository.getallocatetagListForSuperAdmin();
			System.out.println("list@@@@@" + tagList);
//			List<AssetTagBean> agedtagList=new 

			return checkAgedAssetTag(tagList);
		}
		if (role.equals(CommonConstants.organization)) {
			List<String> tagList = tagRepository.getallocatedtagListForAdmin(userid);
			System.out.println("list@@@@@" + tagList);
			return checkAgedAssetTag(tagList);
		}
		if (role.equals(CommonConstants.admin)) {
			List<String> tagList = tagRepository.getallocatedtagListForAdmin(userid);
			System.out.println("list@@@@@" + tagList);
			return checkAgedAssetTag(tagList);
		}
		if (role.equals(CommonConstants.user)) {

			List<String> tagList = tagRepository.getallocatedTagListForUser(userid);
			return checkAgedAssetTag(tagList);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			List<String> tagList = tagRepository.getallocatedtagListForAdmin(adminId);
			System.out.println("list@@@@@" + tagList);
			return checkAgedAssetTag(tagList);
		}

		return null;

	}

	/**
	 * load Working Non-Working Tag Data Excel
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return InputStream
	 */
	public InputStream loadWorkingNonWorkingTagDataExcel(String fkUserId, String role) {
		if (role.equals(CommonConstants.superAdmin)) {
			List<AssetTag> tagList = tagRepository.WorkingNonworkingTagReportForSuperAdminExportDownload();
			if (tagList.size() > 0) {
				ByteArrayInputStream in = WorkingNonworkingTagReportHelper
						.WorkingNonworkingTagExcelForSuperAdmin(tagList);
				return in;
			}
		}
		if (role.equals(CommonConstants.admin)) {
			Long fkID = Long.parseLong(fkUserId);
			List<AssetTag> tagList = tagRepository.WorkingNonworkingTagReportForAdminExportDownload(fkID);
			if (tagList.size() > 0) {
				ByteArrayInputStream in = WorkingNonworkingTagReportHelper
						.WorkingNonworkingTagExcelForAdminAndUser(tagList);
				return in;
			}
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			List<AssetTag> tagList = tagRepository.WorkingNonworkingTagReportForUserExportDownload(fkID);
			if (tagList.size() > 0) {
				ByteArrayInputStream in = WorkingNonworkingTagReportHelper.WorkingNonworkingTagExcelForUser(tagList);
				return in;
			}
		}
		return null;

	}

	/**
	 * get All Tag List Tag Status
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return AssetTag
	 */
	public List<AssetTag> getAllTagListTagStatus(String fkUserId, String role) {

		long fkid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return tagRepository.findAll();
		}
		if (role.equals(CommonConstants.admin)) {
			return tagRepository.getallTagListForAdmin(fkid);
		}
		if (role.equals(CommonConstants.user)) {
			return tagRepository.getTagListsForUser(fkid);
		}
		return null;

	}

	/**
	 * Working And Non-Working Tag Count GPS
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ResponseAssetTagWorkingNonWorkinCountBean
	 */
	public ResponseAssetTagWorkingNonWorkinCountBean WorkingAndNonWorkingTagCountGPS(String fkUserId, String role) {
		// String hours,
		String hours = null;
		try {
			hours = limitservice.getWorkingAndNonWorkingTag(fkUserId);
			if (hours == null) {
				hours = "5";

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		long lhours = Long.parseLong(hours);
		long assetTagWorking = 1;
		long assetTagNonworking = 0;

		if (role.equals(CommonConstants.superAdmin)) {

			assetTagWorking = tagRepository.AssetTagWorkingCountforSuperAdminGPS(assetTagWorking);
			assetTagNonworking = tagRepository.AssetTagNonWorkingCountforSuperAdminGPS(assetTagNonworking);
			ResponseAssetTagWorkingNonWorkinCountBean bean = new ResponseAssetTagWorkingNonWorkinCountBean();
			bean.setAssetTagWorkingCount(assetTagWorking);
			bean.setAssetTagNonWorkingCount(assetTagNonworking);

			return bean;
		}
		if (role.equals(CommonConstants.organization)) {

			Long fkID = Long.parseLong(fkUserId);
//					assetEnableSwitch(fkID);
//					assetTagDisableSwitch(lhours,fkID);

			assetTagWorking = tagRepository.AssetTagWorkingCountforOrganizationGPS(fkID, assetTagWorking);
			assetTagNonworking = tagRepository.AssetTagNonWorkingCountforOrganizationGPS(fkID, assetTagNonworking);
			ResponseAssetTagWorkingNonWorkinCountBean bean = new ResponseAssetTagWorkingNonWorkinCountBean();
			bean.setAssetTagWorkingCount(assetTagWorking);
			bean.setAssetTagNonWorkingCount(assetTagNonworking);

			return bean;
		}
		if (role.equals(CommonConstants.admin)) {

			Long fkID = Long.parseLong(fkUserId);
//					assetEnableSwitch(fkID);
//					assetTagDisableSwitch(lhours,fkID);

			assetTagWorking = tagRepository.AssetTagWorkingCountforAdminGPS(fkID, assetTagWorking);
			assetTagNonworking = tagRepository.AssetTagNonWorkingCountforAdminGPS(fkID, assetTagNonworking);
			ResponseAssetTagWorkingNonWorkinCountBean bean = new ResponseAssetTagWorkingNonWorkinCountBean();
			bean.setAssetTagWorkingCount(assetTagWorking);
			bean.setAssetTagNonWorkingCount(assetTagNonworking);

			return bean;
		}
		if (role.equals(CommonConstants.user)) {
			Long fkID = Long.parseLong(fkUserId);
			assetTagWorking = tagRepository.AssetTagWorkingCountforUserGPS(fkID, assetTagWorking);
			assetTagNonworking = tagRepository.AssetTagNonWorkingCountforUserGPS(fkID, assetTagNonworking);
			ResponseAssetTagWorkingNonWorkinCountBean bean = new ResponseAssetTagWorkingNonWorkinCountBean();
			bean.setAssetTagWorkingCount(assetTagWorking);
			bean.setAssetTagNonWorkingCount(assetTagNonworking);

			return bean;
		}
		return null;
	}

	/**
	 * get All Tag List Validation
	 * 
	 * @author Pratik chaudhari
	 * @return AssetTag
	 */
	public List<AssetTag> getAllTagListValidation() {

		return tagRepository.findAll();
	}

	/**
	 * get Non Working Tag List
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return AssetTag
	 */
	public List<AssetTag> getNonWorkingTagList(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			return tagRepository.getNonWorkingTagListForSuperAdmin(category);
		}
		if (role.equals(CommonConstants.organization)) {

			return tagRepository.getNonWorkingTagListForOrganization(fkUserId, category);
		}
		if (role.equals(CommonConstants.admin)) {
			return tagRepository.getNonWorkingTagListForAdmin(fkUserId, category);
		}
		if (role.equals(CommonConstants.user)) {
			return tagRepository.getNonWorkingTagListForUser(fkUserId, category);
		}
		if (role.equals(CommonConstants.empUser)) {
			return tagRepository.getNonWorkingTagListForAdmin(fkUserId, category);
		}
		return null;
	}

	/**
	 * get Working Tag List
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return AssetTag
	 */
	public List<AssetTag> getWorkingTagList(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.superAdmin)) {
			return tagRepository.getWorkingTagListForSuperAdmin(category);
		}
		if (role.equals(CommonConstants.organization)) {

			return tagRepository.getWorkingTagListForOrganization(fkUserId, category);
		}
		if (role.equals(CommonConstants.admin)) {
			return tagRepository.getWorkingTagListForAdmin(fkUserId, category);
		}
		if (role.equals(CommonConstants.user)) {
			return tagRepository.getWorkingTagListForUser(fkUserId, category);
		}
		if (role.equals(CommonConstants.empUser)) {
			return tagRepository.getWorkingTagListForAdmin(fkUserId, category);
		}

		return null;
	}

	/**
	 * get All Tag List For Admin
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param category
	 * @return AssetTag
	 */
	public List<AssetTag> getAllTagListForAdmin(long fkUserId, String category) {

		return tagRepository.getAdminWiseTagList(fkUserId, category);
	}

	/**
	 * load Asset Tag From SuperAdmin Data Excel
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 */

	public InputStream loadAssetTagFromSuperAdminDataExcel(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.organization)) {
			Long fkID = Long.parseLong(fkUserId);
			List<AssetTagStock> taglist = assetTagStockRepo.AssetTagFromSuperAdminForOrganizationExportDownload(fkID,
					category);
			if (taglist.size() > 0) {
				ByteArrayInputStream in = AssetTagFromSuperAdminHelper.StockExcelForOrganization(taglist);
				return in;
			}
		}
		return null;
	}

//	public InputStream loadWorkingNonWorkingTagDataPDF(String fkUserId, String role) 
//	{
//		if(role.equals(CommonConstants.superAdmin)) {
//			List<AssetTag> userList = tagRepository.WorkingNonworkingTagReportForSuperAdminExportDownload();
//			if(userList.size() > 0)
//			{
//			ByteArrayInputStream in = WorkingNonworkingTagGenerateUserPDF.WorkingNonworkingTagSuperAdminPDF(userList);
//			return in;
//			}
//		}
//		if(role.equals(CommonConstants.admin)) {
//			Long fkID = Long.parseLong(fkUserId);
//			List<AssetTag> userList = tagRepository.WorkingNonworkingTagReportForAdminExportDownload(fkID);
//			if(userList.size() > 0)
//			{
//			ByteArrayInputStream in = WorkingNonworkingTagGenerateUserPDF.WorkingNonworkingTagSuperAdminPDF(userList);
//			return in;
//			}
//		}
//		if(role.equals(CommonConstants.user)) {
//			Long fkID = Long.parseLong(fkUserId);
//			List<AssetTag> userList = tagRepository.WorkingNonworkingTagReportForUserExportDownload(fkID);
//			if(userList.size() > 0)
//			{
//			ByteArrayInputStream in = WorkingNonworkingTagGenerateUserPDF.WorkingNonworkingTagUserPDF(userList);
//			return in;
//			}
//		}
//		return null;
//		

	// }

//	public List<ResponseAssetTagBean> getNotAllocatedTagListForProductassetTagCategorywisePaination(String fkUserId,
//			String role, String assetTagCategory) {
//		if(role.equals(CommonConstants.superAdmin)) 
//		{
//			return tagRepository.getNotAllocatedTagListForProductassetTagCategorywisePaination(assetTagCategory);
//		}
//
//		if(role.equals(CommonConstants.admin)) 
//		{
//			Long fkID = Long.parseLong(fkUserId);
//			return tagRepository.getNotAllocatedStatusTagListForProductAdminassetTagCategorywisePaination(assetTagCategory,fkID);
//		}
//			
//		if(role.equals(CommonConstants.user))
//		{
//			Long fkID = Long.parseLong(fkUserId);
//			return tagRepository.getNotAllocatedStatusTagListForProductUserassetTagCategorywisePaination(assetTagCategory,fkID);
//			
//		}
//		return null;
//	}

}