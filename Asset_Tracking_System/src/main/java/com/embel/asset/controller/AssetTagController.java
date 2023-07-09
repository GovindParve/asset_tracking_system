package com.embel.asset.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.embel.asset.bean.AssetTagBean;
import com.embel.asset.bean.ResponseAssetTagBean;
import com.embel.asset.bean.ResponseAssetTagWorkingNonWorkinCountBean;
import com.embel.asset.bean.ResponseTagstatusbean;
import com.embel.asset.dto.AssetTagDto;
import com.embel.asset.entity.AssetTag;
import com.embel.asset.entity.AssetTagHistory;
import com.embel.asset.entity.AssetTagStock;
import com.embel.asset.repository.AssetGatewayStockRepository;
import com.embel.asset.repository.AssetTagStockRepository;
import com.embel.asset.repository.UserRepository;
import com.embel.asset.service.AssetTagHistoryService;
import com.embel.asset.service.AssetTagService;

@CrossOrigin("*")
@RestController
public class AssetTagController implements ErrorController{

	@Autowired
	AssetTagService tagService;
	@Autowired
	AssetTagHistoryService tagHistoryService;
	
	@Autowired 
	UserRepository userRepo;
	
	@Autowired
	AssetTagStockRepository assetTagStockRepo;
	
	@Autowired
	AssetGatewayStockRepository assetgatewayStock;
	
	/**
	 * use to add tag details 
	 *  @author Pratik chaudhari
	 * @param inputTag  The object of AssetTagDto class 
	 * @return String
	 */
	@PostMapping("/asset/tag/add_asset_tag")
	public String addAssetTag(@RequestBody AssetTagDto inputTag)
	{
	
		return 	tagService.addAssetTag(inputTag); 
	}

	//Get All Tag List
	/**
	 * use to get all tag details 
	 *  @author Pratik chaudhari
	 * @param fkUserId The id of user
	 * @param role  The role of user 
	 * @return AssetTagBean
	 */
	@GetMapping("/Tag/get-Tag_list")
	public List<AssetTagBean> getAllTagList(String fkUserId, String role)
	{			
		return  tagService.getAllTagList(fkUserId,role);		
	}
	
	
	/**
	 * getAllTagListValidation
	 * @author Pratik chaudhari
	 * @return AssetTag
	 */
	@GetMapping("/Tag/get-Tag_list-for-Validation")
	public List<AssetTag> getAllTagListValidation()
	{			
		return  tagService.getAllTagListValidation();		
	}
	
	/**
	 * get All Tag List For Admin
	 * @author Pratik chaudhari
	 * @param username The username of  user 
	 * @param category The category allocated tags of user 
	 * @return AssetTag List
	 */
	@GetMapping("/Tag/get-Tag_list_For_Admin")
	public List<AssetTag> getAllTagListForAdmin(String username,String category)
	{			
		long fkUserId=userRepo.getAdminId(username);
		return  tagService.getAllTagListForAdmin(fkUserId,category);		
	}
//..........................................pagination................
	
	/**
	 * get All Tag List With Pagination
	 * @author Pratik chaudhari
	 * @param pageNo The page number tagList page 
	 * @param fkUserId the userid of user 
	 * @param role  The role of user 
	 * @param category The category of user 
	 * @return AssetTagBean list
	 */
	
	@GetMapping("/Tag/get-Tag_list-with-pagination/{pageNo}")
	public Page<AssetTagBean> getAllTagListWithPagination(@PathVariable String pageNo, String fkUserId, String role,String category )
	{		
		System.out.println("inside categoery@@@@@@"+"pagination");
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return  tagService.getAllTagListWithPagination(fkUserId,role,pageable,category);		
	}
	
	
	/**
	 * get All Tag List With Pagination For Gps
	 * @author Pratik chaudhari
	 * @param pageNo The page number tagList page 
	 * @param fkUserId The userid of user 
	 * @param role    The role of user 
	 * @return AssetTag
	 */
	
	@GetMapping("/Tag/get-Tag_listForGps-with-pagination/{pageNo}")
	public Page<AssetTag> getAllTagListWithPaginationForGps(@PathVariable String pageNo, String fkUserId, String role)
	{		
		System.out.println("inside categoery@@@@@@"+"pagination");
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return  tagService.getAllTagListWithPaginationForGps(fkUserId,role,pageable);		
	}
//....................................
	//Get All Tag List with working NonWorking Status
	/**
	 * Get All Tag List with working NonWorking Status
	 * @author Pratik chaudhari
	 * @param fkUserId The userid of user 
	 * @param role	The role of user 
	 * @return ResponseTagstatusbean
	 */
		@GetMapping("/Tag/get-Tag_list_with_working_NonWorking_Status")
		public List<ResponseTagstatusbean> getAllTagListwitworkingNonWorkingStatus(String fkUserId, String role)
		{		
			
			List<AssetTag> taglist=tagService.getAllTagListTagStatus(fkUserId,role);	
		  
			
			List<ResponseTagstatusbean> taglist1 = new ArrayList<ResponseTagstatusbean>();
		  for(int i=0;i<taglist.size();i++)
		  {
			  ResponseTagstatusbean bean =new ResponseTagstatusbean();
			  
			  bean.setAdmin(taglist.get(i).getAdmin());
			  bean.setAssetBarcodeSerialNumber(taglist.get(i).getAssetBarcodeSerialNumber());
			  bean.setAssetIMEINumber(taglist.get(i).getAssetIMEINumber());
			  bean.setAssetIMSINumber(taglist.get(i).getAssetIMSINumber());
			  bean.setAssetLocation(taglist.get(i).getAssetLocation());
			  bean.setAssetSimNumber(taglist.get(i).getAssetSimNumber());
			  bean.setAssetTagCategory(taglist.get(i).getAssetTagCategory());
			  bean.setAssetTagEnable(taglist.get(i).getAssetTagEnable());
			  bean.setAssetTagId(taglist.get(i).getAssetTagId());
			  bean.setAssetTagName(taglist.get(i).getAssetTagName());
			  bean.setAssetUniqueCodeMacId(taglist.get(i).getAssetUniqueCodeMacId());
			  bean.setDatetime(taglist.get(i).getDatetime());
			  bean.setFkAdminId(taglist.get(i).getFkAdminId());
			  bean.setFkUserId(taglist.get(i).getFkUserId());
			  bean.setStatus(taglist.get(i).getStatus());
			  bean.setTimeZone(taglist.get(i).getTimeZone());
			  bean.setUser(taglist.get(i).getUser());
			  bean.setWakeupTime(taglist.get(i).getWakeupTime());
			  if(taglist.get(i).getAssetTagEnable()==1)
			  {
				  bean.setWorkingORNonWorking("Working");
				  
			  }else {
				  bean.setWorkingORNonWorking("Non-Working"); 
			  }
			
			  taglist1.add(bean);
			  
		  }
		  
		  return taglist1;
		  
		}
		
		//
		/**
		 * Get Nonworking Tag
		 * @param fkUserId The Userid of user 
		 * @param role  The Role of user 
		 * @param category The category of user 
		 * @return ResponseTagstatusbean
		 */
		@GetMapping("/Tag/getNonWorking-Tag_list")
		public List<ResponseTagstatusbean> getNonWorkingTagList(String fkUserId, String role,String category)
		{		
			
			List<AssetTag> taglist=tagService.getNonWorkingTagList(fkUserId,role,category);	
		  
			
			List<ResponseTagstatusbean> taglist1 = new ArrayList<ResponseTagstatusbean>();
		  for(int i=0;i<taglist.size();i++)
		  {
			  ResponseTagstatusbean bean =new ResponseTagstatusbean();
			  
			  bean.setAdmin(taglist.get(i).getAdmin());
			  bean.setAssetBarcodeSerialNumber(taglist.get(i).getAssetBarcodeSerialNumber());
			  bean.setAssetIMEINumber(taglist.get(i).getAssetIMEINumber());
			  bean.setAssetIMSINumber(taglist.get(i).getAssetIMSINumber());
			  bean.setAssetLocation(taglist.get(i).getAssetLocation());
			  bean.setAssetSimNumber(taglist.get(i).getAssetSimNumber());
			  bean.setAssetTagCategory(taglist.get(i).getAssetTagCategory());
			  bean.setAssetTagEnable(taglist.get(i).getAssetTagEnable());
			  bean.setAssetTagId(taglist.get(i).getAssetTagId());
			  bean.setAssetTagName(taglist.get(i).getAssetTagName());
			  bean.setAssetUniqueCodeMacId(taglist.get(i).getAssetUniqueCodeMacId());
			  bean.setDatetime(taglist.get(i).getDatetime());
			  bean.setFkAdminId(taglist.get(i).getFkAdminId());
			  bean.setFkUserId(taglist.get(i).getFkUserId());
			  bean.setStatus(taglist.get(i).getStatus());
			  bean.setTimeZone(taglist.get(i).getTimeZone());
			  bean.setUser(taglist.get(i).getUser());
			  bean.setWakeupTime(taglist.get(i).getWakeupTime());
			  if(taglist.get(i).getAssetTagEnable()==1)
			  {
				  bean.setWorkingORNonWorking("Working");
				  
			  }else {
				  bean.setWorkingORNonWorking("Non-Working"); 
			  }
			
			  
			  taglist1.add(bean);
			  
		  }
		  
		  return taglist1;
		  
		}
		
		//..........
		/**
		 * get Working Tag List
		 * @author Pratik chaudhari
		 * @param fkUserId The userid of user 
		 * @param role The role of user 
		 * @param category The category of user 
		 * @return ResponseTagstatusbean
		 */
		@GetMapping("/Tag/getWorking-Tag_list")
		public List<ResponseTagstatusbean> getWorkingTagList(String fkUserId, String role,String category)
		{		
			
			List<AssetTag> taglist=tagService.getWorkingTagList(fkUserId,role,category);	
		  
			
			List<ResponseTagstatusbean> taglist1 = new ArrayList<ResponseTagstatusbean>();
		  for(int i=0;i<taglist.size();i++)
		  {
			  ResponseTagstatusbean bean =new ResponseTagstatusbean();
			  
			  bean.setAdmin(taglist.get(i).getAdmin());
			  bean.setAssetBarcodeSerialNumber(taglist.get(i).getAssetBarcodeSerialNumber());
			  bean.setAssetIMEINumber(taglist.get(i).getAssetIMEINumber());
			  bean.setAssetIMSINumber(taglist.get(i).getAssetIMSINumber());
			  bean.setAssetLocation(taglist.get(i).getAssetLocation());
			  bean.setAssetSimNumber(taglist.get(i).getAssetSimNumber());
			  bean.setAssetTagCategory(taglist.get(i).getAssetTagCategory());
			  bean.setAssetTagEnable(taglist.get(i).getAssetTagEnable());
			  bean.setAssetTagId(taglist.get(i).getAssetTagId());
			  bean.setAssetTagName(taglist.get(i).getAssetTagName());
			  bean.setAssetUniqueCodeMacId(taglist.get(i).getAssetUniqueCodeMacId());
			  bean.setDatetime(taglist.get(i).getDatetime());
			  bean.setFkAdminId(taglist.get(i).getFkAdminId());
			  bean.setFkUserId(taglist.get(i).getFkUserId());
			  bean.setStatus(taglist.get(i).getStatus());
			  bean.setTimeZone(taglist.get(i).getTimeZone());
			  bean.setUser(taglist.get(i).getUser());
			  bean.setWakeupTime(taglist.get(i).getWakeupTime());
			  if(taglist.get(i).getAssetTagEnable()==1)
			  {
				  bean.setWorkingORNonWorking("Working");
				  
			  }else {
				  bean.setWorkingORNonWorking("Non-Working"); 
			  }
			
			  taglist1.add(bean);
			  
		  }
		  
		  return taglist1;
		  
		}
		
		
//.........................................................................................................
//.................................Working..and .Nonworking tag Count ..................................................................................................

	/**
	 * 	use to get Working And NonWorking Tag Count
	 * @author Pratik chaudhari
	 * @param fkUserId The userid of user 
	 * @param role The role of user 
	 * @param category The category of user 
	 * @return ResponseAssetTagWorkingNonWorkinCountBean
	 * @throws ParseException
	 */
	@GetMapping("/Tag/get-assetTagWorking-NonWorking-count")
	public ResponseAssetTagWorkingNonWorkinCountBean WorkingAndNonWorkingTagCount(String fkUserId,String role,String category) throws ParseException
	{
		//@RequestParam(name = "hours" , required = false) String hours,
//		if(hours==null)
//		{
//			
//			hours= "0";
//			
//			
//			
//		}
		return tagService.WorkingAndNonWorkingTagCount(fkUserId,role,category);//hours,
		

		
	}
	
//.................................................................../	
	
	
	/**
	 * get All Tag List Categiry wise With Pagination
	 * @author pratik chaudhari
	 * @param pageNo The page no of user 
	 * @param fkUserId The userid of user 
	 * @param role The role of user 
	 * @param assetTagCategory The category of user 
	 * @return AssetTagBean
	 */
	@GetMapping("/Tag/get-Tag_list-categorywise-with-pagination/{pageNo}")
	public Page<AssetTagBean> getAllTagListCategirywiseWithPagination(@PathVariable String pageNo, String fkUserId, String role,String assetTagCategory)
	{		
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return  tagService.getAllTagListCategorywiseWithPagination(fkUserId,role,assetTagCategory,pageable);		
	}
	
//....................................................................
	//---------Get Tag Count------- 
	/**
	 * use to get tag count 
	 * @author Pratik chaudhari
	 * @param fkUserId The userid of user 
	 * @param role The role of user 
	 * @param category The category  of user 
	 * @return String
	 */
	@GetMapping("/tag/get_tag_count")
	public String tagCount(String fkUserId, String role,String category)
	{		
		return  tagService.tagCount(fkUserId,role,category);		
	}

	//
	/**
	 * Get tag for Edit
	 * @author Pratik chaudhari
	 * @param id The userid of user 
	 * @return AssetTagBean
	 */
	@GetMapping("/tag/get-tag-for-edit/{tagid}")
	public List<AssetTagBean> getTagForEdit(@PathVariable(value="tagid") Long id)
	{
		return tagService.getTagForEdit(id);
	}

	//update Tag
	/**
	 * use to update Tag
	 * @author Pratik chaudhari
	 * @param dto
	 * @return String
	 */
	@PutMapping("/tag/update-tag")
	public String updateTag(@RequestBody AssetTagDto dto)
	{
		
		return tagService.updateTag(dto);
	}	
	//..........aged tag count
	
	/**
	 * get aged Tag Count
	 * @author Pratik chaudhari 
	 * @param fkUserId The userid of user 
	 * @param role The role  of user 
	 * @return String
	 */
	@GetMapping("/tag/get-agedTag-Count")
	public List<String>  getagedTagCount(String fkUserId, String role)
	{
		
		return tagService.getAgedTagCount(fkUserId,role);
		
	}
	
	
	

	//...................................................

	
	
	
	//get asset tag privious allocated history history of 
	/**
	 * use to get asset tag Name  
	 * @author Pratik chaudhari 
	 * @param assetTagName The name of asset Tag
	 * @return AssetTagHistory
	 */
	@GetMapping("/tag/get-asset-tag-privious-allocated-history")
	public AssetTagHistory getByTagName(String assetTagName)
	{
		return tagHistoryService.getAssetTagByName(assetTagName);
	}
	
	//Get single Tag Details
	/**
	 * get Single Tag By Id
	 * @author Pratik chaudhari
	 * @param id The userid of user 
	 * @return AssetTagBean
	 */
	@GetMapping("/tag/get-single-tag/{id}") 
	public AssetTagBean  getSingleTagById(@PathVariable("id") String id) 
	{ 
		AssetTagBean bean = new AssetTagBean();
		AssetTag singleTag = tagService.getTagDetailsById(Long.parseLong(id));
		System.out.println("&&&&&&&&&&&" + singleTag);

		if(singleTag == null)
		{
			return new AssetTagBean();
		}
		bean.setAssetTagId(singleTag.getAssetTagId());
		bean.setAssetTagName(singleTag.getAssetTagName());
		bean.setAdmin(singleTag.getAdmin());
		bean.setUser(singleTag.getUser());
		bean.setFkAdminId(singleTag.getFkAdminId());
		bean.setFkUserId(singleTag.getFkUserId());
		bean.setAssetIMSINumber(singleTag.getAssetIMSINumber());
		bean.setAssetBarcodeSerialNumber(singleTag.getAssetBarcodeSerialNumber());
		bean.setAssetUniqueCodeMacId(singleTag.getAssetUniqueCodeMacId());
		bean.setAssetLocation(singleTag.getAssetLocation());
		bean.setAssetIMEINumber(singleTag.getAssetIMEINumber());
		bean.setWakeupTime(singleTag.getWakeupTime());
		bean.setDatetime(singleTag.getDatetime());
//		bean.setDate(singleTag.getDate());
//		bean.setTime(singleTag.getTime());
		bean.setTimeZone(singleTag.getTimeZone());
		bean.setAssetSimNumber(singleTag.getAssetSimNumber());
		bean.setAssetTagCategory(singleTag.getAssetTagCategory());
		bean.setStatus(singleTag.getStatus());

		return bean; 
	}
//...................tag,pagination,.assetTagCategory...........
//	//Get All Tag List .assetTagCategory
//		@GetMapping("/tag/get_tag_list_product_assetTagCategorywis_with_pagination/{pageNo}")
//		public List<ResponseAssetTagBean> getNotAllocatedTagListForProductassetTagCategorywisePaination(@PathVariable String  pageNo,String fkUserId,
//				String role,String assetTagCategory)
//		{	
//			Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
//			return  tagService.getNotAllocatedTagListForProductassetTagCategorywisePaination(fkUserId, role,assetTagCategory);		
//		}	
//	
	
	
//.......................................................................
	//Get All Tag List
	/**
	 * getNotAllocatedTagListForProduct
	 * @author Pratik chaudhari
	 * @param fkUserId The userid of user 
	 * @param role The role of user 
	 * @param category The category of user 
	 * @return ResponseAssetTagBean
	 */
	@GetMapping("/tag/get_tag_list_for_product")
	public List<ResponseAssetTagBean> getNotAllocatedTagListForProduct(String fkUserId, String role,String category)
	{			
		return  tagService.getNotAllocatedTagListForProduct(fkUserId,role,category);		
	}

	//Multiple Delete Tag Details from Database
	/**
	 * use to delete All Selected Tag
	 * @author Pratik chaudhari
	 * @param PkID The userid of user 
	 * @return String
	 */
	@PostMapping("/tag/delete-multiple-tag")
	public String deleteAllSelectedTag(@RequestBody List<String> PkID)
	{
		tagService.deleteAllSelectedTag(PkID);
		return "All Selected Tag Delete Successfully...";
	}

	//Get All Tag List
	/**
	 * get All Tag List For Dropdown
	 * @author Pratik chaudhari 
	 * @param fkUserId The userid of user 
	 * @param role The role of user 
	 * @return AssetTagBean
	 */
	@GetMapping("/Tag/get-gps-tag-list-for-dropdown")
	public List<AssetTagBean> getAllTagListForDropdown(String fkUserId, String role)
	{			
		return  tagService.getAllTagListForDropdown(fkUserId,role);		
	}

	//----------------Excel file upload--------------------------------
/**
 * import Excel File
 * @author Pratik chaudhari
 * @param files The file  of Tag details 
 * @param username The username of user 
 * @return String
 * @throws IOException
 * @throws EncryptedDocumentException
 * @throws InvalidFormatException
 */
	@PostMapping("/tag/import-excel-file-with-tag-details-to-database")
	public String importExcelFile(@RequestParam("file") MultipartFile files,String username) throws IOException, EncryptedDocumentException, InvalidFormatException 
	{
		
		String status = tagService.addExcelFileDataToDatabase(files,username);
	return status;
//		HttpStatus status = HttpStatus.OK;
//		List<AssetTag> assetTagList = tagService.addExcelFileDataToDatabase(files);
//
//		if(assetTagList.size()==0)
//		{
//			status = HttpStatus.FOUND;
//			return new ResponseEntity<List<AssetTag>>(assetTagList , status);
//		}
//		return new ResponseEntity<>(assetTagList, status);
	
	}
	
	
	
	//get from superAdmin
/**
 * import Excel File From SuperAdmin 
 * @author Pratik chaudhari
 * @param file The file  of Tag details from superAdmin
 * @return String
 * @throws IOException
 * @throws EncryptedDocumentException
 * @throws InvalidFormatException
 */
	@PostMapping("/tag/import-excel-file-with-tag-details-to-database-From-superAdmin")
	public String importExcelFileFromSuperAdmin(@RequestParam("file") MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException 
	{
		
		String status = tagService.addExcelFileDataToDatabaseFromSuperAdmin(file);
	return status;
	}
	
	/**
	 * get From SuperAdmin To view
	 * @author Pratik chaudhari
	 * @param pageNo The page no of tag list
	 * @param fkUserId The userid of user 
	 * @param role The role of user 
	 * @param category The category of user 
	 * @return AssetTagStock
	 */
	@GetMapping("/tag/getTag-list-to-view-from-SuperAdmin/{pageNo}")
	public Page<AssetTagStock> getFromSuperAdminToview(@PathVariable String pageNo,String fkUserId, String role,String category)
	{
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		if(role.equals("organization"))
		{
			 return 	assetTagStockRepo.getFromSuperAdminToview(fkUserId,category,pageable);
		}
		return null;
	  
	
	}
	
	// 
	/**
	 * get assettag from superadmin in excel
	 * @author Pratik chaudhari
	 * @param fileType file type of assettag file
	 * @param fkUserId The userid of user 
	 * @param role the role of user 
	 * @param category  the category of asset tag  
	 * @return ResponseEntity<Resource>
	 * @throws ParseException
	 */
	@GetMapping("/tag/get-assetTag-From-SuperAdmin-excel-download")
	public ResponseEntity<Resource> getUserReportForExportDownload(String fileType,String fkUserId, String role,String category) throws ParseException 
	{
					
	
		if(fileType.equals("excel"))
		{
			String filename = "AssetTagFromSuperAdmin.xls";
			InputStreamResource file =null;
			try {file = new InputStreamResource(tagService.loadAssetTagFromSuperAdminDataExcel(fkUserId,role,category));} catch (Exception e) {}
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(file);
		}
	
		
		return null;
	}
	
	}

