package com.embel.asset.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.persistence.Column;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.embel.asset.bean.AssetGatewayBean;
import com.embel.asset.bean.ResponseGetwayWorkingNonWorkingbean;
import com.embel.asset.bean.ResponseworkingNonworkinGatewaybean;
import com.embel.asset.dto.AssetGatewayDto;
import com.embel.asset.entity.AssetGateway;
import com.embel.asset.entity.AssetGatewayStock;
import com.embel.asset.entity.AssetTagStock;
import com.embel.asset.helper.AssetGatewayHelper;
import com.embel.asset.repository.AssetGatewayRepository;
import com.embel.asset.repository.AssetGatewayStockRepository;
import com.embel.asset.service.AssetGatewayService;
import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;

import ch.qos.logback.core.status.Status;

@CrossOrigin("*")
@RestController
public class AssetGatewayController implements ErrorController {

	@Autowired
	AssetGatewayService gatewayService;

	@Autowired
	AssetGatewayStockRepository assetGatewayStockRepo;
	
	@Autowired
	AssetGatewayHelper assetGatewayHelper;

	public void getToCheckLeatestMethod() {
		System.out.println("data is added to git");
	}
	
	
	@GetMapping("/gateway/get-gateway-barcode-number")
	public String getBarcodeNumber()
	{
		return assetGatewayHelper.getBarcodeNumber();
	}
	
	//public String deleteGateway(@PathVariable(value = "gatewayid") Long PkID) {
		//(@RequestParam(defaultValue = "Provide Details")
		//(@PathVariable("barcodeNumber") String barcodeNumber)
		//@GetMapping(value = "/gateway/get-barcode-number-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
		@GetMapping(value = "/gateway/get-barcode-number-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
		public ResponseEntity<InputStreamResource>  getBarcodeNumberPDF() throws IOException, DocumentException, WriterException
		{
			System.out.println("");
			String barcodeNumber = "1000";
			AssetGateway assetGateway = gatewayService.getGatewayDetailsByBarcodeNumber(barcodeNumber);
		    
			/*String gatewayName = assetGateway.getGatewayName();
			Long gatewayBarcodeNumber = assetGateway.getGatewayBarcodeNumber();
			String gatewayUniqueCodeMacId = assetGateway.getGatewayUniqueCodeMacId();
			String gatewayBarcodeSerialNumber = assetGateway.getGatewayBarcodeSerialNumber();*/
			
			String assetGatewayDteails = assetGateway.getGatewayName()+", "+assetGateway.getGatewayBarcodeNumber()+", "+assetGateway.getGatewayUniqueCodeMacId()+", "+assetGateway.getGatewayBarcodeSerialNumber();		    
		    
			byte[] pngData = assetGatewayHelper.getBarcodeNumber(assetGatewayDteails, 500, 300);
		     InputStreamResource inputStreamResource = assetGatewayHelper.getBarcodeInputStreamResource(pngData);
		     HttpHeaders headers = new HttpHeaders();
		     headers.add("Content-Disposition", "inline; " + "filename=my-file.pdf");
		     
		     return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(inputStreamResource);
		}
	

	/**
	 * use to create gateway
	 * @author Pratik chaudhari
	 * @param dto  this is object of AssetGatewayDto class 
	 * @return String
	 */
	@PostMapping("/gateway/asset_gateway")
	public String addGateway(@RequestBody AssetGatewayDto dto) {

		return gatewayService.addAssetGateway(dto);
	}

	/**
	 * change the possition of gateway
	 *  @author Pratik chaudhari
	 * @param gatewayName   this is name of Gateway 
	 * @param x  x-coordinate
	 * @param y   y-coordinate
	 * @param fkUserId  this is use to get the id of that perticular user from database
	 * @param role    this is role of user 
	 * @return String
	 */
	@PostMapping("/gateway/change_possitions")
	public String addGateway(String gatewayName, long x, long y, String fkUserId, String role) {

		return gatewayService.changePossitions(gatewayName, x, y, fkUserId, role);
	}

//..............................pagination..........................................
	/**
	 * get All Gateway List
	 * 
	 * @author Pratik chaudhari
	 * @param pageNo
	 * @param fkUserId this is use to get the id of that perticular user from
	 *                 database
	 * @param role     this is role of user
	 * @return AssetGatewayBean
	 */
	@GetMapping("/gateway/get-gateway_list-pagination/{pageNo}")
	public Page<AssetGatewayBean> getAllGatewayList(@PathVariable String pageNo, String fkUserId, String role) {
		Pageable pageable = PageRequest.of(Integer.parseInt(pageNo), 10);
		return gatewayService.getAllGatewayListWithPagination(fkUserId, role, pageable);
	}

	/**
	 * use to get Working And NonWorking Count
	 * @author Pratik chaudhari
	 * @param fkUserId  this is use to get the id of that perticular user from database
	 * @param role    this is role of user 
	 * @param category
	 * @return ResponseGetwayWorkingNonWorkingbean
	 */
//.......................................................
	@GetMapping("/gateway/get-gateway-Nonworking-Count")
	public ResponseGetwayWorkingNonWorkingbean WorkingAndNonWorkingCount(String fkUserId, String role,
			String category) {

		return gatewayService.getWorkingAndNonWorkingCount(fkUserId, role, category);
	}

//...........................................................................................................
	/**
	 * Get All Gateway List
	 * @author Pratik chaudhari
	 * @param fkUserId  this is use to get the id of that perticular user from database
	 * @param role    this is role of user 
	 * @return AssetGatewayBean
	 */
	// Get All Gateway List
	@GetMapping("/gateway/get-gateway_list")
	public List<AssetGatewayBean> getAllGatewayList(String fkUserId, String role) {

		return gatewayService.getAllGatewayList(fkUserId, role);
	}

	/**
	 * get AllGateway List validation
	 *  @author Pratik chaudhari
	 * @return AssetGateway
	 */
	@GetMapping("/gateway/get-gateway_list-for-validation")
	public List<AssetGateway> getAllGatewayListvalidation() {

		return gatewayService.getAllGatewayListvalidation();
	}
//...............................................................................................

	/**
	 * //Get All Gateway List
	 *  @author Pratik chaudhari
	 * @param fkUserId  this is use to get the id of that perticular user from database
	 * @param role    this is role of user 
	 * @return ResponseworkingNonworkinGatewaybean
	 */
	@GetMapping("/gateway/get-gateway_list_for_workingNon_working_gateway")
	public List<ResponseworkingNonworkinGatewaybean> getAllGatewayListWorkingNonWorkingGateway(String fkUserId,
			String role) {

		return gatewayService.getAllGatewayListCheacking(fkUserId, role);
	}

	/**
	 * get All Gateway List Working Non Working Gateway
	 *  @author Pratik chaudhari
	* @param fkUserId  this is use to get the id of that perticular user from database
    * @param role    this is role of user 
	 * @return ResponseworkingNonworkinGatewaybean
	 */
	@GetMapping("/gateway/get-gateway_list_for_working_gateway")
	public List<ResponseworkingNonworkinGatewaybean> getAllGatewayListWorkingWorkingGateway(String fkUserId,
			String role) {

		return gatewayService.getAllGatewayListWorkingWorkingGateway(fkUserId, role);
	}

	/**
	 * get All Gateway List NonWorking Gateway
	 *  @author Pratik chaudhari
	 * @param fkUserId  this is use to get the id of that perticular user from database
	 * @param role    this is role of user 
	 * @return ResponseworkingNonworkinGatewaybean LIst
	 */
	@GetMapping("/gateway/get-gateway_list_for_Nonworking_gateway")
	public List<ResponseworkingNonworkinGatewaybean> getAllGatewayListNonWorkingGateway(String fkUserId, String role) {

		List<ResponseworkingNonworkinGatewaybean> objGatewaybeans = gatewayService
				.getAllGatewayListNonWorkingGateway(fkUserId, role);

		System.out.println("objGatewaybeans:" + objGatewaybeans);
		return objGatewaybeans;
	}
//..............................................................................................................

	/**
	 * Get gateway Count
	 *  @author Pratik chaudhari
	 * @param fkUserId  this is use to get the id of that perticular user from database
	 * @param role    this is role of user 
	 * @param category this is category tag 
	 * @return String
	 */
	@GetMapping("/gateway/get-gateway_count")
	public String gatewayCount(String fkUserId, String role, String category) {
		return gatewayService.gatewayCount(fkUserId, role, category);
	}

	/**
	 * Get gateway for Edit
	 *  @author Pratik chaudhari
	 *  @param id   this is gateway id from database 
	 *  @return AssetGatewayBean
	 */
	@GetMapping("/gateway/get-gateway-for-edit/{gatewayid}")
	public List<AssetGatewayBean> getGatewayForEdit(@PathVariable(value = "gatewayid") Long id) {
		return gatewayService.getGatewayForEdit(id);
	}

	/**
	 * update gateway
	 *  @author Pratik chaudhari
	 * @param dto this is object of assetGatewaydto class
	 * @return String
	 */
	@PutMapping("/gateway/update-gateway")
	public String updateGateway(@RequestBody AssetGatewayDto dto) {

		return gatewayService.updateGateway(dto);
	}

	/**
	 * Delete gateway Details from Database
	 * @author Pratik chaudhari
	 * @param PkID  this is id of gateway 
	 * @return String
	 */
	@DeleteMapping("/gateway/delete-gateway/{gatewayid}")
	public String deleteGateway(@PathVariable(value = "gatewayid") Long PkID) {
		gatewayService.deleteGateway(PkID);
		return "Gateway Delete Successfully...";
	}

	/**
	 * Multiple Delete gateway Details from Database
	 * @author Pratik chaudhari
	 * @param PkIDs this is the id of gateway
	 * @return String
	 */
	@PostMapping("/gateway/delete-multiple-gateway")
	public String deleteAllSelectedGateway(@RequestBody List<Long> PkIDs) {

		gatewayService.deleteAllSelectedGateway(PkIDs);
		return "All Selected Gateway Delete Successfully...";
	}

	/**
	 * //Get single Gateway Details
	 *  @author Pratik chaudhari
	 * @param id this is the id of gateway
	 * @return AssetGatewayBean
	 */
	@GetMapping("/gateway/get-single-gateway/{id}")
	public AssetGatewayBean getSingleGatewayById(@PathVariable("id") String id) {
		AssetGateway singleGateway = gatewayService.getGatewayDetailsById(Long.parseLong(id));

		if (singleGateway != null) {
			AssetGatewayBean gateway = new AssetGatewayBean();
			gateway.setGatewayId(singleGateway.getGatewayId());
			gateway.setGatewayName(singleGateway.getGatewayName());
			gateway.setAssetTagCategory(singleGateway.getAssetTagCategory());
			gateway.setGatewayBarcodeOrSerialNumber(singleGateway.getGatewayBarcodeSerialNumber());
			gateway.setGatewayLocation(singleGateway.getGatewayLocation());
			gateway.setGatewayUniqueCodeMacId(singleGateway.getGatewayUniqueCodeMacId());
			gateway.setUser(singleGateway.getUser());
			gateway.setAdmin(singleGateway.getAdmin());
			gateway.setFkAdminId(singleGateway.getFkAdminId());
			gateway.setFkUserId(singleGateway.getFkUserId());
			gateway.setWakeupTime(singleGateway.getWakeupTime());
			gateway.setDatetime(singleGateway.getDatetime());

			gateway.setTimeZone(singleGateway.getTimeZone());

			return gateway;
		} else {
			return new AssetGatewayBean();
		}

	}

	/**
	 * //Get All Gateway List for dropdown
	 *  @author Pratik chaudhari
	 *  @param fkUserId  this is use to get the id of that perticular user from database
	 *  @param role    this is role of user 
	 *  @param category this is category tag 
	 *  @return AssetGatewayBean
	 */
	@GetMapping("/gateway/get-gateway-list-for-dropdown")
	public List<AssetGatewayBean> getAllGatewayListForDropdown(String fkUserId, String role) {
		return gatewayService.getAllGatewayListForDropdown(fkUserId, role);
	}

	/**
	 * //Get All Gateway Location List for dropdown
	 *  @author Pratik chaudhari
	 * @param fkUserId this is use to get the id of that perticular user from database
	 * @param role this is role of user 
	 * @return String list
	 */
	@GetMapping("/gateway-location/get-gateway-location-list-for-dropdown")
	public List<String> getAllGatewayLocationListForDropdown(String fkUserId, String role) {
		return gatewayService.getAllGatewayLocationListForDropdown(fkUserId, role);
	}

	// ----------------------------------------------

	/**
	 * Excel file upload- from SuperAdmin-
	 *  @author Pratik chaudhari
	 * @param pageNo
	 * @param fkUserId this is use to get the id of that perticular user from database
	 * @param role this is role of user 
	 * @param category this is category tags
	 * @return AssetGatewayStock
	 */
	@GetMapping("/tag/getgateway-list-to-view-from-SuperAdmin/{pageNo}")
	public Page<AssetGatewayStock> getFromSuperAdminToview(@PathVariable String pageNo, String fkUserId, String role,
			String category) {
		Pageable pageable = PageRequest.of(Integer.parseInt(pageNo), 10);
		if (role.equals("organization")) {
			return assetGatewayStockRepo.getFromSuperAdminToview(fkUserId, category, pageable);
		}

		return null;

	}

	/**
	 * import Excel File From SuperAdmin
	 *  @author Pratik chaudhari
	 * @param file The file of Gateway List from super Admin
	 * @return String
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	@PostMapping("/gateway/import-excel-file-with-gateway-from-SuperAdmin")
	public String importExcelFileFromSuperAdmin(@RequestParam("file") MultipartFile file)
			throws IOException, EncryptedDocumentException, InvalidFormatException {

		String status = gatewayService.addExcelFileDataToDatabaseFromSuperAdmin(file);

		return status;
	}

	// -----------------------------------------------
	/**
	 * Excel file upload-
	 *  @author Pratik chaudhari
	 * @param files The file of Gateway List 
	 * @param username
	 * @return String
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	@PostMapping("/gateway/import-excel-file-with-gateway-details-to-database")
	public String importExcelFile(@RequestParam("file") MultipartFile files, String username)
			throws IOException, EncryptedDocumentException, InvalidFormatException {

		String status = gatewayService.addExcelFileDataToDatabase(files, username);

		return status;
	}

	/**
	 * get User Report For Export Download
	 *  @author Pratik chaudhari
	 * @param fileType the type of file 
	 * @param fkUserId The id of user 
	 * @param role The role of User 
	 * @param category The category of Gateway
	 * @return Resource
	 * @throws ParseException
	 */
	@GetMapping("/gateway/get-gateway-From-SuperAdmin-excel-download")
	public ResponseEntity<Resource> getUserReportForExportDownload(String fileType, String fkUserId, String role,
			String category) throws ParseException {

		if (fileType.equals("excel")) {
			String filename = "AssetGatewayFromSuperAdmin.xls";
			InputStreamResource file = null;
			try {
				file = new InputStreamResource(
						gatewayService.loadAssetGatewayFromSuperAdminDataExcel(fkUserId, role, category));

			} catch (Exception e) {
				System.out.println(e);
			}

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
		}

		return null;
	}

}
