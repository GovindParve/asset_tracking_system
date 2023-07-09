package com.embel.asset.controller;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.embel.asset.bean.AllNotificationsCountBean;
import com.embel.asset.bean.GatewayWiseTagCountBean;
import com.embel.asset.bean.ResponseCollectionBean;
import com.embel.asset.bean.ResponseColumnBean;
import com.embel.asset.bean.ResponseDashbordCountbean;
import com.embel.asset.bean.ResponseDashbordCountbeanForGPS;
import com.embel.asset.bean.ResponseGpsTagBean;
import com.embel.asset.bean.ResponseLowBatteryListBean;
import com.embel.asset.bean.ResponseTrackingBean;
import com.embel.asset.bean.ResponseTrackingListBean;
import com.embel.asset.bean.ResponseltotalBean;
import com.embel.asset.dto.AssetTrackingDto;
import com.embel.asset.dto.IndoorImageDto;
import com.embel.asset.entity.AdminColumns;
import com.embel.asset.entity.AssetTestjson;
import com.embel.asset.entity.AssetTrackingEntity;
import com.embel.asset.entity.EmployeeUser;
import com.embel.asset.entity.IndoorImages;
import com.embel.asset.entity.columnmapping;
import com.embel.asset.entity.limits;
import com.embel.asset.helper.CrunchifyGoogleGSONExample;
import com.embel.asset.helper.FileUploadhelper;
import com.embel.asset.repository.AdminColumnRepository;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.UserRepository;
import com.embel.asset.service.AdminColumnService;
import com.embel.asset.service.AssetTestJsonService;
import com.embel.asset.service.AssetTrackingService;
import com.embel.asset.service.AssetTrackinghoursBackupService;
import com.embel.asset.service.EmployeUserService;
import com.embel.asset.service.IndoorService;
import com.embel.asset.service.columnmappingService;
import com.embel.asset.service.limitsservice;
import com.google.gson.Gson;
@RestController
@CrossOrigin("*")
public class AssetTrackingController implements ErrorController{

	@Autowired
	AssetTrackingService trackingService;
	@Autowired
	columnmappingService columnmappingService;
	@Autowired
	FileUploadhelper fileuploadhelper;
	@Autowired
	AssetTestJsonService assetTestjsonservice;
	@Autowired
	limitsservice limitsservice;
	@Autowired
	AdminColumnService admincolumnService;
	@Autowired
	AdminColumnRepository adminColumnRepository;
	@Autowired
	CrunchifyGoogleGSONExample crunchifyGoogleGSONExample;
	@Autowired
	EmployeUserService employeUserService;
	
	@Autowired
	UserRepository userRepository;

	
	@Autowired
	EmployeUserRepository employeeUserRepo;
	
	@Autowired
	AssetTrackinghoursBackupService assetTrackinghoursBackupService;
	@Autowired
	IndoorService indoorService;
	 
/**
 * @author Pratik chaudhari
 * @param str 
 * @return AssetTrackingDto
 */
	public List<AssetTrackingDto> convertObj(String str)
	{
		
		String[] list=str.split("[=,]+");
		System.out.println(Array.get(list, 0));
		//sunny
		String str1=String.valueOf(list);
		
		System.out.println(str1);
		
		String[] list1=str1.split("=");
		System.out.println(Array.get(list1, 0));
		
		
	    for (String a : list) {
            System.out.println(a);
    }
		
		List<String>list2=new ArrayList<String>();
	
		list2.add((String) (Array.get(list, 1)));	
		list2.add((String) (Array.get(list, 3)));
		list2.add((String) (Array.get(list, 5)));
		list2.add((String) (Array.get(list, 7)));
		list2.add((String) (Array.get(list, 9)));
		List<AssetTrackingDto> assetList=new ArrayList<AssetTrackingDto>();
		AssetTrackingDto asset=new AssetTrackingDto();
		
		String s1=String.valueOf(list2.get(0));
		
		String s2=String.valueOf(list2.get(1));
		String s3=String.valueOf(list2.get(2));
		String s4=String.valueOf(list2.get(3));
		String s5=String.valueOf(list2.get(4));
	
		asset.setgSrNo(s1);
		asset.setgMacId(s2);
		asset.settMacId(s3);
		asset.setbSN(s4);
		asset.setBatStat(Integer.parseInt(s5));
		
		assetList.add(asset);
		return  assetList;
		
	}
	//............Employe user ............
	/**
	 * post api for Add new Employee user 
	 * @param employeeuser The object of Employee class 
	 * @return String
	 */
	@PostMapping("/asset/tracking/addEmployeeUser")
	public String addEmployeUser(@RequestBody EmployeeUser employeeuser)
	{

		return employeUserService.addEmployeUser(employeeuser);
	}
	
	
	
	/**
	 * this function is use to delete Employee
	 * @param list The list of user id
	 * @return String
	 */
	@PostMapping("/asset/tracking/BulkDeleteEmployee")
	public String BulkDeleteEmployee(@RequestBody List<Long> list)
	{
		
		
		return employeUserService.BulkDeleteEmployee(list);
	}
	
	
	
	/**
	 * this function is use to Update  Employee
	 * @param employeeuser object of EmployeeUser class 
	 * @return String
	 */
	@PutMapping("/asset/tracking/UpdateEmployee")
	public String UpdateEmployee(@RequestBody EmployeeUser employeeuser)
	{
		
		
		return employeUserService.UpdateEmployee(employeeuser);
	}
	
	
	
	/* 
	 * this function is use to get-all-EmployeeUser 
	 * */
	
	/**
	 * get all employee user  list with pagination
	 * @param pageNo the page no of employe list 
	 * @param fkUserId The user id of employee user 
	 * @param role The role of employee user 
	 * @param category the category of employee user 
	 * @return EmployeeUser
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/get-all-EmployeeUser/{pageNo}")
	public Page<EmployeeUser> getAllEmployeUser(@PathVariable String pageNo,String fkUserId,String role,String category) throws ParseException 
	{
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
	
		return employeUserService.getAllEmployeUser(fkUserId,role,category,pageable);
	}
	
	
	
	/**
	 * 
	 * @param id  the userid of user 
	 * @return EmployeeUser
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/get-by-id/{id}")
	public Optional<EmployeeUser> getEmployeUser(@PathVariable String id) throws ParseException 	{
		
	
		return employeUserService.getAllEmployeUser(Long.parseLong(id));
	}
	//..............search api for asset tracking............

	/**
	 * this function is use to SearchByParameters
	 * @param pageNo The page no of asset tracking list
	 * @param assetTagName  The name of tag 
	 * @param assetGatewayName The name of Gateway
	 * @param date The Time Stamp 
	 * @param entryTime  The entery time of Tag
	 * @param tagEntryLocation Tag entry location 
	 * @param existTime  The exit time of tag
	 * @param dispatchTime   dispatch time of gateway
 	 * @param time time stamp 
	 * @param battryPercentage the percentage of battry
	 * @param fkUserId  the userid of user 
	 * @param role The role of user 
	 * @return AssetTrackingEntity
	 */
	@GetMapping("/asset/tracking/getlist-SearchByParameters/{pageNo}")
	public Page<AssetTrackingEntity> SearchByParameters(@PathVariable String pageNo,
			@RequestParam (required = false) String  assetTagName,
			@RequestParam(required = false) String assetGatewayName,
			@RequestParam(required = false) String date,@RequestParam(required = false) String entryTime,
			@RequestParam(required = false)	String tagEntryLocation,@RequestParam(required = false) String existTime,
			@RequestParam(required = false) String dispatchTime,@RequestParam(required = false) String time,
			@RequestParam(required = false) String battryPercentage,String fkUserId,String role)
	{
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		System.out.println("entryTime"+entryTime);
		System.out.println("existTime"+existTime);
		System.out.println("date"+date);
		System.out.println("update"+time);
		
	 return	trackingService.SearchByParameters(assetTagName,assetGatewayName,date,entryTime,tagEntryLocation,existTime,dispatchTime,time,battryPercentage,fkUserId,role,pageable);
	}
	
	
	
	//.......................................................................................................
	//.............indoor asset tracking ...........
	
	/**
	 * this api is use to add image 
	 * @param indoorimagedto The object of IndoorImageDto class
	 * @return String
	 */
	@PostMapping("/asset/tracking/add-image")
	public String addAdminWiseIndoorImage(@RequestBody IndoorImageDto indoorimagedto)
	{
		indoorService.deletebyId(indoorimagedto.getFkadminId());
		
		return indoorService.addAdminWiseIndoorImage(indoorimagedto);
	}

	/**
	 * this function is use to update-image
	 * @param indoorimagedto The object of IndoorImageDto class
	 * @return IndoorImages
	 */
	@PutMapping("/asset/tracking/update-image")
	public IndoorImages updateImage(@RequestBody IndoorImageDto indoorimagedto)
	{
		return indoorService.updateImage(indoorimagedto);
	}
	
	

	/**
	 * this function is use to get-image-list
	 * @return IndoorImages  
	 */
	@GetMapping("/asset/tracking/get-image-list")
	public List<IndoorImages> getlist()
	{
		
		return indoorService.getlist();                                                                                                                                                             
	}
	
	/**
	 * use to get single image 
	 * @param id the id of image from database 
	 * @return  IndoorImages
	 */
	@GetMapping("/asset/tracking/get-image/{id}")
	public Optional<IndoorImages> getImage(@PathVariable String id)
	{
		
		return indoorService.getImage(Long.parseLong(id));
	}
	
	
	//........................
	
	/**
	 * this api use toget api dashbord count
	 * @param fkUserId  The userid of user 
	 * @param role The role of user 
	 * @param category The category of user 
	 * @return ResponseDashbordCountbean 
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/get-all-DashBordCount")
	public ResponseDashbordCountbean getDashbordCounts(String fkUserId,String role,String category) throws ParseException 
	{
		
		
		//return trackingService.getGpsTagsLocation(fkUserId,role);
		return trackingService.getDashbordCounts(fkUserId,role,category);
	}
	
	/**
	 * use to  get all DashBordCountForGps
	 * @param fkUserId The user id of user 
	 * @param role The role of user 
	 * @return ResponseDashbordCountbeanForGPS
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/get-all-DashBordCountForGps")
	public ResponseDashbordCountbeanForGPS getDashbordCountsForGps(String fkUserId,String role ) throws ParseException 
	{
		
		//return trackingService.getGpsTagsLocation(fkUserId,role);
		return trackingService.getDashbordCountsForGps(fkUserId,role);
	}	
	//........................................................
	//...........................GEOCODEING...........................
	/**
	 * use to get gps tags with location 
	 * @param fkUserId The user id of user 
	 * @param role The role of user 
	 * @return  ResponseGpsTagBean
	 */
	@GetMapping("/asset/tracking/get_all_gps_tracking_details_location")
	public List<ResponseGpsTagBean> getGpsTagsLocation(String fkUserId,String role ) 
	{
		System.out.println(trackingService.getGpsTagsLocation(fkUserId,role));
		return trackingService.getGpsTagsLocation(fkUserId,role);
		
	}
	
	
	/**
	 * use to get tag location tag Namewise
	 * @param tagName The name of asset tag
	 * @param fkUserId The user id of user 
	 * @param role The role of user 
	 * @return ResponseGpsTagBean
	 */
	@GetMapping("/asset/tracking/get_gps_tracking_details_tagNamewise_location")
	public List<ResponseGpsTagBean> getGpsTagsLocationTagwise(String tagName,String fkUserId,String role ) 
	{
		
		return trackingService.getGpsTagsLocationTagNameWise(tagName,fkUserId,role);
		
	}
	//..................................................limits...........................
	
	/**
	 * this api is use to set the limits 
	 * @param fkUserId  the userid of user 
	 * @return  limits 
	 */
	@GetMapping("/asset/tracking/get_limits/{fkUserId}")
	public Optional<limits> getlimitsByid(@PathVariable String fkUserId) {		
		
		return limitsservice.getlimitsByid(fkUserId);
	
	}
	
	/**
	 * use to add limits obj
	 * @param limits The object of limits class 
	 * @return limits
	 */
	@PostMapping("/asset/tracking/add_limits")
	public limits addlimits(@RequestBody limits limits) 
	{

		limitsservice.deletebyid(limits.getFkadminId());
		return limitsservice.addlimits(limits);

	}
	//test 
	/**
	 * testing purpose
	 * @param str  
	 * @return String
	 */
	@PostMapping("/asset/forTestingPurpose")
	public String TestingPurpose(String str) 
	{

		System.out.println("str:-" + str);

		return "OK";

	}
//----------------------test
	/**
	 * use to get the list of limits objects
	 * @return limits
	 */
	@GetMapping("/asset/tracking/get_limits")
	public List<limits> getlimitsList() {		
		
		return limitsservice.getlimitsList();
	
	}
	

	/**
	 * use to update limits 
	 * @param limits object of limits class 
	 * @return String 
	 */
	@PutMapping("/asset/tracking/update_limit")
	public String updatelimit(@RequestBody limits limits) {		
		
		limitsservice.updatelimits(limits);
		return "limits update";
	}
	
	//.................
	/**
	 * testing 
	 * @param str
	 * @return ResponseEntity<Optional<String>>
	 */
	@PostMapping(path= "/asset/tracking/get_tracking_details-test-dynamic")
	public ResponseEntity<Optional<String>> addPayloadBulk1(@RequestBody String  str) 
	{	
		

		assetTrackinghoursBackupService.AssetTrackingHourslyBackup();
		
		List<AssetTrackingDto> requestData =  convertObj(str);

		
		
		try {
			System.out.println("Asset $date:"+requestData);
			String status =  trackingService.addTrackingDetailsBulk(requestData);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("############################"+status);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			
			return ResponseEntity.ok(Optional.of(status));
		}catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}
	//....................................................
	//for Demo purpose
	/**
	 * for DEmo purpose
	 * @param str
	 * @return
	 */
	@PostMapping(path= "/asset/tracking/get_tracking_details-test-gsm", consumes = "text/plain; charset: utf-8")
	public ResponseEntity<Optional<String>> AddPayloadBulk(@RequestBody String  str) 
	{	
		
			
		
//		System.out.println(obj);
//			if(str.isEmpty())

//		{
//			return "string is Empty";
//		}
		
//		String status = "ok";
	
		

		assetTrackinghoursBackupService.AssetTrackingHourslyBackup();
		
		List<AssetTrackingDto> requestData =  convertObj(str);
//		AssetTrackingDto obj=
//		requestData.add(obj);
		
		
		try {
			System.out.println("Asset $date:"+requestData);
			String status =  trackingService.addTrackingDetailsBulk(requestData);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("############################"+status);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			
			return ResponseEntity.ok(Optional.of(status));
		}catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}
	//.........asset tracking bulk uplod with text file
	
	
	//by pratik chaudhari
	public AssetTrackingDto[] StringconvertIntoJsonobj(String str) 
	{
		Gson gson = new Gson();
	    
		AssetTrackingDto[] assetdto = gson.fromJson(str, AssetTrackingDto[].class);
	    
	    System.out.println(assetdto);
	    return assetdto;
	}
	

	
//	
//	
//	//for Demo purpose
//	public List<AssetTrackingDto> convertintoString1() throws IOException 
//	{
//		//String path ="/home/ubuntu/Asset Tracking/files/json.json";
//		String path ="C:\\Users\\HP\\git\\Asset_Tracking_System\\src\\main\\resources\\File\\json.txt";
//
//		String content=null;
//
//        try {
//
//            // default StandardCharsets.UTF_8  
//        	content =java.nio.file.Files.readString(Paths.get(path));
//            System.out.println(content);
//            
//           
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        AssetTrackingDto[] assetdto =StringconvertIntoJsonobj(content);
//        	
//        
//        List<AssetTrackingDto> assetdtodata=new ArrayList<AssetTrackingDto>();
//        
//        for(int i=0;i<assetdto.length;i++)
//        {
//        	assetdtodata.add(assetdto[i]);
//        	System.out.println(assetdto[i]);
//        }
//        
//        
//        System.out.println("assetdtodata:"+assetdtodata);
//        
//        
//        return assetdtodata;
//	}
//		
//	
//
//		
////	//-------------------------------------------------------------------------------------------------------------------------
//	//for Demo purpose
//	@PostMapping("/asset/tracking/get_tracking_details_of_tag_bulk-with-text-file")
//	public String addPayloadBulk(@RequestParam("file") MultipartFile file) throws ParseException, JsonIOException, JsonSyntaxException, org.json.simple.parser.ParseException, IOException 
//	{		
//		
//		if(file.isEmpty())
//		{
//			return "file is Empty";
//		}
//		System.out.println(file.getOriginalFilename());
//		
//		boolean b =fileuploadhelper.UploadFile(file);
//			
//			if(b==true)
//			{
//				System.out.println("file upload succesfully::::");
//			}else {
//				
//				System.out.println("Internal server Error");
//			}
//			
//				List<AssetTrackingDto> aseetdto=convertintoString1();
//
//				System.out.println("Asset $$$$$$$$$$$$$$$$$$$date:"+aseetdto);
//				String status =  trackingService.addTrackingDetailsBulk(aseetdto);
//				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//				System.out.println("############################"+status);
//				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//				
//
//
//			//String status1 = "OK";
//		return status;
//
//	}
	//...................................
	//for Demo purpose for hardware 
	@PostMapping("/asset/tracking/get_tracking_details_of_tag_bulk-with-text-json")		
	public AssetTestjson addTest(@RequestBody AssetTestjson assetTestjson)
	{
		System.out.println("json formate ="+assetTestjson);
		assetTestjsonservice.addjson(assetTestjson);

		return assetTestjson;
	}
		
	
	//.............................................................................
	/**
	 * 
	 * @param str normal string 
	 * @return
	 */
	@PostMapping(path= "/asset/tracking/get_tracking_details_plain_text", consumes = "text/plain; charset: utf-8")
	public String Printtext(@RequestBody String str)
	{
		System.out.println("given string is :"+str);
		return "ok";
	}
//..................Admin Columns Controller......
	/**
	 * get List AdminColumn
	 * @param pageNo The page no of admin columns 
	 * @param fkUserId The user id of user  
	 * @param role The role of user 
	 * @param category The category of AdminColumns 
	 * @return AdminColumns 
	 */
	@GetMapping("/asset/tracking/get-all-list-admincolumns/{pageNo}")
	public Page<AdminColumns> getListAdminColumn(@PathVariable String pageNo,String fkUserId,String role,String category)//
	{
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return admincolumnService.getListAdminColumn(fkUserId,role,category,pageable);//,pageable	
	}
	
	
	
	
	/**
	 * get List Admin Column for status
	 * @param fkUserId The userid 
	 * @param role The role of user 
	 * @param category the category of user 
	 * @return AdminColumns
	 */
	@GetMapping("/asset/tracking/get-all-list-admincolumnsforStatus")
	public List<AdminColumns> getListAdminColumnforstatus(String fkUserId,String role,String category)//
	{
		//Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return admincolumnService.getListAdminColumnforstatus(fkUserId,role,category);//,pageable	
	}
	
	
	/**
	 * get admin columns By id
	 * @param id
	 * @return AdminColumns
	 */
	@GetMapping("/asset/tracking/get-admincolumns-by-id/{userid}")
	public Optional<AdminColumns> getadmincolumnsByid(@PathVariable(value="userid") Long id)
	{
	
		return admincolumnService.getadmincolumnsByid(id);
	}
	
	
	/**
	 * change Status Admin Parameter
	 * @param status
	 * @param pkadminparameterid
	 * @return String 
	 */
	@PostMapping("/asset/tracking/update-status")
	public String changeStatusAdminParameter(String status,long pkadminparameterid)
	{
		return admincolumnService.changestatusAdminParameter(status,pkadminparameterid);
		
	}
	
	/**
	 * add admin column
	 * @param adminColumns
	 * @return  String
	 */ 
	@PostMapping("/asset/tracking/add-admincolumns")
	public String addadmincolumn(@RequestBody AdminColumns adminColumns)
	{
		
//		if(adminColumns.getAdminName().isEmpty())
//		{
			
			
//			String organization=null;
//			try{organization=admincolumnService.getOrganizationBycolunmnName(adminColumns.getOrganization(),adminColumns.getColumns());} catch (Exception e) {
//				
//			}
//			String uicolumnName=admincolumnService.getadminUiNameOfOrganization(adminColumns.getColumns(),adminColumns.getOrganization());
				String orgid=userRepository.getOrganizationId(adminColumns.getOrganization());	
							String adminid=null;
				try {adminid=userRepository.getAdminIdInString(adminColumns.getAdminName());	} catch (Exception e) {}
//				
//			if(organization ==null && uicolumnName==null)
//			{
				String unit= columnmappingService.getunit(adminColumns.getColumns());
				adminColumns.setAdminName(adminColumns.getAdminName());
				adminColumns.setFkAdminId(adminid);
				
				adminColumns.setFkOrganizationId(orgid);
				adminColumns.setUnit(unit);
				adminColumns.setStatus("Active");
				admincolumnService.add(adminColumns);
				return "add sucessfully";
//			}else {
//				System.out.println("\"already present\"");
//				return "already present";
//			}
		
			
//			
//		}else {
//		
//			List<AdminColumns> admincolumnlist =admincolumnService.getAdminColumn();
//			String organization=null;
//			try{organization=admincolumnService.getOrganizationBycolunmnName(adminColumns.getOrganization(),adminColumns.getColumns());} catch (Exception e) {
//				
//			}
//			String adminName=null;
//			try {adminName=admincolumnService.BycolunmnName(adminColumns.getAdminName(),adminColumns.getColumns());if(adminName==null) {adminName="N/A";}} catch (Exception e) {}		
//			
//			String adminid=null;
//			try {adminid=userRepository.getAdminIdInString(adminColumns.getAdminName());	} catch (Exception e) {}
//		
//			String uicolumnName=admincolumnService.getadminUiName(adminColumns.getColumns(),adminColumns.getAdminName());
//			
//			String orgid=userRepository.getOrganizationId(adminColumns.getOrganization());
//			if(admincolumnlist.size()==0)
//			{
//				String unit= columnmappingService.getunit(adminColumns.getColumns());
//				adminColumns.setOrganization(adminColumns.getOrganization());
//				
//				adminColumns.setFkAdminId(adminid);
//				adminColumns.setUnit(unit);
//				adminColumns.setFkOrganizationId(orgid);
//				adminColumns.setStatus("Active");
//				admincolumnService.add(adminColumns);
//				return "add sucessfully";
//			}else {
//				
//				if(adminName.equals(adminColumns.getAdminName()) && organization.equals(adminColumns.getOrganization()) && uicolumnName.equals(adminColumns.getColumns()) )
//				{
//					System.out.println("\"already present\"");
//					return "already present";
//				
//					
//				}
//				else {
//					String unit= columnmappingService.getunit(adminColumns.getColumns());
//					adminColumns.setOrganization(adminColumns.getOrganization());
//					
//					adminColumns.setFkAdminId(adminid);
//					adminColumns.setUnit(unit);
//					adminColumns.setFkOrganizationId(orgid);
//					adminColumns.setStatus("Active");
//					admincolumnService.add(adminColumns);
//					return "add sucessfully";
//				
//				}
//		
//			}
//		}	
	}
	/**
	 * use to update admin
	 * @param admincolumn the object of admincolumn class 
	 * @return AdminColumns
	 */
@PutMapping("/asset/tracking/update")
public AdminColumns upadteAdmin(@RequestBody AdminColumns admincolumn)
{
	return admincolumnService.updateAdminColumn(admincolumn);
	
}


/**
 * get List Drop Down List
 * @param fkUserId the userid of user 
 * @param role the role of user
 * @param category the category of user 
 * @return String list 
 */
@GetMapping("/asset/tracking/get-drop-down-list")
public List<String> getListDropDownList(String fkUserId,String role,String category)
{

	return admincolumnService.getListDropDownList(fkUserId,role,category);	
}

/**
 * get List Drop Down ListNew
 * @param fkUserId The userid of user 
 * @param role The role of user
 * @param category The category of user 
 * @return String
 */
@GetMapping("/asset/tracking/get-drop-down-listNew")
public List<String> getListDropDownListNew(String fkUserId,String role,String category)
{

	return admincolumnService.getListDropDownListNew(fkUserId,role,category);	
}

	/**
	 * get List
	 * @param columnname The columnname coloumn
	 * @param category The category of columnname 
	 * @return  columnmapping list
	 */
//............coloummapping.controller..................\
	@GetMapping("/asset/tracking/get-all-list-mapping")
	public List<columnmapping>getList(@RequestParam(required = false) String columnname,String category)
	{
	
		return columnmappingService.getList(columnname,category);	
	}
	
	
	/**
	 *  get Column Name On ui Column Name
	 * @param columnname The name of column
	 * @return String 
	 */
	
	@GetMapping("/asset/tracking/get-dbcolumn-on-uicolumnname")
	public String getColumnNameOnuiColumnName(String columnname)
	{
	
		return columnmappingService.getColumnNameOnuiColumnName(columnname);	
	}
	
/**
 * get Column Name With ui Column Name
 * @param fkUserId the user id of user 
 * @param role The role of user 
 * @param category The category of user 
 * @return  ResponseColumnBean
 */
	@GetMapping("/asset/tracking/get-dbcolumn-with-uicolumnname")
	public ResponseColumnBean getColumnNameWithuiColumnName(String fkUserId,String role,String category)
	{
	
		return columnmappingService.getColumnNameWithuiColumnName(fkUserId,role,category);	
	}
	
	
	/**
	 * get By id
	 * @param id
	 * @return columnmapping
	 */
	@GetMapping("/asset/tracking/get-columnmapping-By-id/{userid}")
	public columnmapping getByid(@PathVariable(value="userid") String id)
	{
	
		return columnmappingService.getByid(id);	
	}
	
	
	/**
	 * add column
	 * @param columnmapping object of columnmapping class 
	 * @return String
	 */
	@PostMapping("/asset/tracking/add-lablel-with-column")
	public String addcolumn(@RequestBody columnmapping columnmapping)
	{
		//String columnName=columnmappingService.BycolunmnName(columnmapping.getColumnName());
		
//		if(columnName==null)
//		{
			columnmappingService.add(columnmapping);
			return "add sucessfully";
		
//			
////		}
//		else {
//		
//			return "already present";
//		}
//	
		
	}
	/**
	 * use to delete columnmapping record from database 
	 * @param id the id of columnmapping record id   
	 * @return String 
	 */
	@DeleteMapping("/asset/tracking/delete-mapping-with-column/{id}")
	public String DeleteMapping(@PathVariable String id)
	{
		columnmappingService.DeleteMapping(id);
	
		return "deleted";
	}
	
	
	/**
	 * use to update columnmapping object in databases
	 * @param columnmapping
	 * @return columnmapping
	 */
	@PutMapping("/asset/tracking/updatecoloumnmapping")
	public columnmapping update(@RequestBody columnmapping columnmapping)
	{
		 return columnmappingService.updateMapping(columnmapping);
	
		
	}
	
	
	/**
	 * get labels List
	 * @return String list 
	 */
	@GetMapping("/asset/tracking/get-label-list-for-dropdown")
	public List<String>getlabelsList()
	{
		System.out.println("inside getlabelsList ");
		return columnmappingService.getlabelsList();	
	}
	
	/**
	 * get tracking list columnwise
	 * @param pageNo the pageno of colummapping list page 
	 * @param columnname The columnname of column from uiside 
	 * @param fkUserId the userid of user 
	 * @param role The role of user 
	 * @param category The category of tag 
	 * @return AssetTrackingEntity
	 */
	
	@GetMapping("/asset/tracking/get-view-list-tracking-columnwise/{pageNo}")
	public Page<AssetTrackingEntity> getListcloumnwise(@PathVariable String pageNo,@RequestParam (required = false) String columnname,String fkUserId,String role,String category)
	{
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return columnmappingService.getListcloumnwise(columnname,fkUserId,role,pageable,category);	
	}
	
	//....leabling...................
	
	/**
	 * use to allocate coloumns
	 * @param payload  This is the json formate 
	 * @return AssetTrackingDto list
	 * @throws Exception
	 */
	//@PostMapping("/procces")	
	public List<AssetTrackingDto> ReservColumnAllocation(@RequestBody List<Map<String, Object>> payload) throws Exception {
	
		List<AssetTrackingDto> requestData=new ArrayList<AssetTrackingDto>();
		
		for(int i=0;i<payload.size();i++)
		{
			System.out.println(payload.get(i).keySet());
			//System.out.println(payload.get(i).values());
			String entity=payload.get(i).values().toString();
			String entity2=payload.get(i).keySet().toString();	
			System.out.println(entity);
			String[] list=entity.split("[,]+");
			String[] listheader=entity2.split("[,]+");
			System.out.println("list"+list);
			//int icnt=payload.get(i).size()-5;
			
			AssetTrackingDto assetdto=new AssetTrackingDto();
			String imei=Array.get(listheader, 0).toString().replace("["," ").trim();
			System.out.println("imei"+imei);
			if(imei.equals("imeiNumber"))
			{
				assetdto.setImeiNumber(Array.get(list, 0).toString().replace("["," ").trim());
				assetdto.setLatitude(Array.get(list, 1).toString().trim());
				assetdto.setLongitude(Array.get(list, 2).toString().trim());
				int batstat=Integer.parseInt(Array.get(list, 3).toString().replace("]"," ").trim());
			 
				assetdto.setBatStat(batstat);
				//assetdto.setEntryTime(Array.get(list, 4).toString().replace("]"," ").trim());
				for(int j=4;j<payload.get(i).size();j++)
				{
					
					System.out.println("list.get"+Array.get(listheader, j));
					List<columnmapping> clist = new ArrayList<columnmapping>();
					//List<columnmapping> columnMappinglist= columnmappingService.getAlldata();
					//System.out.println("columnMappinglist:"+columnMappinglist);
					
					String str=Array.get(listheader,j).toString();
					//System.out.println("str"+str);
					String str1=str.replace("]", " ");
					String str2=str1.trim();
					System.out.println("str1"+str2);
					
					
					
					String label=columnmappingService.getlablelofColumn(str2);
					System.out.println("label::"+label);
					clist=columnmappingService.getAlldata();
					System.out.println("clist"+clist);
					if(label.equals("lb1"))  {assetdto.setLb1(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb2"))  {assetdto.setLb2(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb3"))  {assetdto.setLb3(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb4"))  {assetdto.setLb4(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb5"))  {assetdto.setLb5(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb6"))  {assetdto.setLb6(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb7"))  {assetdto.setLb7(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb8"))  {assetdto.setLb8(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb9"))  {assetdto.setLb9(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb10")) {assetdto.setLb10(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb11")) {assetdto.setLb11(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb12")) {assetdto.setLb12(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb13")) {assetdto.setLb13(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb14")) {assetdto.setLb14(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb15")) {assetdto.setLb15(Array.get(list, j).toString().replace("]"," ").trim());}
				}
				
			}else
			{
				assetdto.setgSrNo(Array.get(list, 0).toString().replace("["," ").trim());
				assetdto.setgMacId(Array.get(list, 1).toString().trim());
				assetdto.settMacId(Array.get(list, 2).toString().trim());
				assetdto.setbSN(Array.get(list, 3).toString().trim());
				int batstat=Integer.parseInt(Array.get(list, 4).toString().replace("]"," ").trim());
				assetdto.setBatStat(batstat);
			
				assetdto.setEntryTime(Array.get(list, 5).toString().replace("]"," ").trim());
				float tagdist=Float.parseFloat(Array.get(list, 6).toString().replace("]"," ").trim());
				assetdto.setTagDist(tagdist);
				for(int j=7;j<payload.get(i).size();j++)
				{
					
					System.out.println("list.get"+Array.get(listheader, j));
					List<columnmapping> clist = new ArrayList<columnmapping>();
					//List<columnmapping> columnMappinglist= columnmappingService.getAlldata();
					//System.out.println("columnMappinglist:"+columnMappinglist);
					
					String str=Array.get(listheader,j).toString();
					//System.out.println("str"+str);
					String str1=str.replace("]", " ");
					String str2=str1.trim();
					System.out.println("str1"+str2);
					
					
					
					String label=columnmappingService.getlablelofColumn(str2);
					System.out.println("label::"+label);
					clist=columnmappingService.getAlldata();
					System.out.println("clist"+clist);
					if(label.equals("lb1"))  {assetdto.setLb1(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb2"))  {assetdto.setLb2(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb3"))  {assetdto.setLb3(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb4"))  {assetdto.setLb4(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb5"))  {assetdto.setLb5(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb6"))  {assetdto.setLb6(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb7"))  {assetdto.setLb7(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb8"))  {assetdto.setLb8(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb9"))  {assetdto.setLb9(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb10")) {assetdto.setLb10(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb11")) {assetdto.setLb11(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb12")) {assetdto.setLb12(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb13")) {assetdto.setLb13(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb14")) {assetdto.setLb14(Array.get(list, j).toString().replace("]"," ").trim());}
					if(label.equals("lb15")) {assetdto.setLb15(Array.get(list, j).toString().replace("]"," ").trim());}
				}
			}
			
			
			System.out.println("assetdto:"+assetdto); 
			 requestData.add(assetdto);
			 
		}
		
		return requestData;
}
//.......................................................................................................................
	
	
	/**
	 * use to add bulk asset tracking details
	 * @param requestDatamap  the object of list of json
	 * @return String
	 * @throws Exception
	 */
	
	@PostMapping("/asset/tracking/get_tracking_details_of_tag_bulk")
	public ResponseEntity<Optional<String>> addPayloadBulk(@RequestBody List<Map<String, Object>> requestDatamap) throws Exception 
	{		//@RequestBody List<AssetTrackingDto> requestData
		//leatest changes
		System.out.println("@@@@@@@%%%%%%%%%%%%from HardWare %%%%%%%%%%%%@@@@@@@@@@@@@@@@@@@@@:"+requestDatamap);
		List<AssetTrackingDto>  requestData=ReservColumnAllocation(requestDatamap);
		assetTrackinghoursBackupService.AssetTrackingHourslyBackup();
			
			
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println(requestData);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			String status =  trackingService.addTrackingDetailsBulk(requestData);
			
			System.out.println("############################"+status);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			String status1="ok";
			return ResponseEntity.ok(Optional.of(status1));
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
//	
	}
//.................................assetTag wise --duration wise .staytime.....	
	/**
	 * get tagwise /durationwise /stay time 
	 * @param duration   this is String Like "LastWeek","LastMonth"
	 * @param tagName   This is the Name of Tag
	 * @param fkUserId	This is unique id of User 
	 * @param role		This is role of that User 
	 * @return Map
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/get_tracking_details_tagName_duration_stayTime_for_graph")
	public Map<String, String> getstayTime(String duration,String tagName,String fkUserId,String role ) throws ParseException
	{
		
		return trackingService.getassetagstaytime(duration,tagName,fkUserId,role);
		
	}
	//date wise
	/**
	 * get stay time graph date wise 
	 * @param fromdate   this is startng date
	 * @param todate	This is ending date 
	 * @param tagName	This is the Name of Tag
	 * @param fkUserId 	This is unique id of User
	 * @param role		This is role of that User
	 * @return Map
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/get_tracking_details_tagName_datewise_stayTime_for_graph")
	public Map<String, String> getstayTimeDate(String fromdate,String todate,String tagName,String fkUserId,String role ) throws ParseException
	{
		
		return trackingService.getassetagstaytimedatewise(fromdate,todate,tagName,fkUserId,role);
		
	}

	
	

	/**
	 * get tracking list on userwise 
	 * @param pageNo	This is page Number 
	 * @param fkUserId	This is unique id of User
	 * @param role		This is role  of User
	 * @param category	This is category of User
	 * @return AssetTrackingEntity
	 */
	@GetMapping("/asset/tag/get-tracking_list_on_userwise/{pageNo}")
	public Page<AssetTrackingEntity> getTrackingListOnUserwise(@PathVariable String pageNo,String fkUserId,String role,String category)
	{
		
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return trackingService.getAllTrackingDataUserwise(fkUserId,role,pageable,category);		
	}

	//------------------Get Tag-list-for-dropdown for filter--------------------------------------------	
	/**
	 * get tag list drop down filter 
	 * @param fkUserId	This is unique id of User
	 * @param role		This is role  of User
	 * @param category	This is category of User
	 * @return String this is list of Sring 
	 */
	@GetMapping("/asset/tracking/get-tag-list-for-dropdown-in-tracking")
	public List<String> getAllTagListForDrodown(String fkUserId,String role,String category)
	{		
		System.out.println("===========role======" +role +"============ID=====" +fkUserId);
		List<String> tagList = trackingService.getAllTagListForDrodown(fkUserId, role,category);	
		System.out.println("Output list==========" +tagList);
		return tagList;
	}

	//--------------Get All  tracking List on tag-name Wise filter-----------------------------
	/**
	 * get All TrackingData For Filter Userwise
	 * @param tagName   This is Tag Name
	 * @param fkUserId	This is unique id of User
	 * @param role		This is role  of User
	 * @return ResponseTrackingListBean
	 */
	@GetMapping("/asset/tag/get-tracking-list-for-filter-tagnamewise")
	public List<ResponseTrackingListBean> getAllTrackingDataForFilterUserwise(String tagName,String fkUserId,String role)
	{			
		System.out.println("====================");
		return trackingService.getAllTrackingDataForFilterUserwise(tagName,fkUserId,role);		
	}
//,...................................................pagination................................................
	/**
	 * get All TrackingData For Filter Userwise With Pagination
	 * @param pageNo	This is PageNumber
	 * @param tagName	This is Tag Name
	 * @param fkUserId	This user id of user 
	 * @param role		This is role of user 
	 * @return ResponseTrackingListBean
	 */
	@GetMapping("/asset/tag/get-tracking-list-for-filter-tagnamewise-with-pagination/{pageNo}")
	public Page<ResponseTrackingListBean> getAllTrackingDataForFilterUserwiseWithPagination(@PathVariable String pageNo,String tagName,String fkUserId,String role)
	{			
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		System.out.println("====================");
		return trackingService.getAllTrackingDataForFilterUserwisePagination(tagName,fkUserId,role,pageable);		
	}
//...........................................................................................................
	//----------------Get single tag all tracking details on tag-name Wise filter -----------------------------
	/**
	 * Get single tag all tracking details on tag-name Wise filter
	 * @param tagName	This is Tag Name
	 * @param fkUserId	This id of user 
	 * @param role		This is Role of user 
	 * @return ResponseTrackingBean  list
	 */
	@GetMapping("/asset/Singletag/get-tracking-list-for-filter-tagnamewise")
	public List<ResponseTrackingBean> getSingleTrackingDataForFilterUserwise(String tagName,String fkUserId,String role)
	{			
		return trackingService.getSingleTrackingDataForFilterUserwise(tagName,fkUserId,role);		
	}
	
	/**
	 * get Single TrackingData For Filter Userwise Alldata
	 * @param tagName This is Tag Name
	 * @param fkUserId This id of user 
	 * @param role     This role of user 
	 * @return AssetTrackingEntity List
	 */
	@GetMapping("/asset/Singletag/get-tracking-list-for-filter-tagnamewise-all-data")
	public List<AssetTrackingEntity> getSingleTrackingDataForFilterUserwiseAlldata(String tagName,String fkUserId,String role)
	{			
		return trackingService.getSingleTrackingDataForFilterUserwiseAlldata(tagName,fkUserId,role);		
	}
	
	//----------------Get All Gateway List for dropdown------------------------
	/**
	 * Get All Gateway List for dropdown
	 * @param fkUserId This id of user
	 * @param role      This role of user
	 * @return String list
	 */
	@GetMapping("/tracking/gateway/get-gateway-list-for-dropdown")
	public List<String> getAllGatewayListForDropdown(String fkUserId, String role)
	{			
		List<String> listOfGateway = trackingService.getAllGatewayListForDropdown(fkUserId,role);	

		return  listOfGateway;
	}

	//..................................................................................................
	
	

	
//////	
	
	/**
	 * //date(between date ) wise tag view api for with pagination 
	 * @param pageNo	This is PageNumber
	 * @param fkUserId	This user id of user
	 * @param role		This is role of user
	 * @param fromdate	This is starting date 
	 * @param todate	This is Ending date
	 * @param tagName	This is Tag Name
	 * @param category	This is category
	 * @return AssetTrackingEntity
	 */
		@GetMapping("/tag/get-tag-Wise-View-between-date/{pageNo}")
		public Page<AssetTrackingEntity> TagwiseViewApi(@PathVariable String pageNo, String fkUserId, String role,String fromdate,String todate,String tagName,String category)
		{
			Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
			
			
			return trackingService.getTagWiseViewBetweenDate(fkUserId,role,fromdate,todate,pageable,tagName,category);
		
	
	
		}
//
		
		
		/**
		 * //Date wise Gateway view api with pagination
		 * @param pageNo This is PageNumber
		 * @param fkUserId  This user id of user
		 * @param role		This is role of user
		 * @param fromdate	This is starting date
		 * @param todate	This is Endinging date
		 * @param gatewayName	This is GatewayName
		 * @param category		This is Gateway category
		 * @return AssetTrackingEntity
		 */
		@GetMapping("/gateway/get-gateway-Wise-View-between-date/{pageNo}")
		public Page<AssetTrackingEntity> gatewaywiseViewApi(@PathVariable String pageNo,String fkUserId,String role,String fromdate,String todate,String gatewayName,String category )
		{
			Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
			
			
			return trackingService.getGatewayWiseViewBetweenDate(fkUserId,role,fromdate,todate,pageable,gatewayName,category);
		
	
	
		}
	
	
//.......................................
		/**
		 * get tagwise gateway View Api
		 * @param pageNo	This is PageNumber
		 * @param fkUserId	This user id of user
		 * @param role		This is role of user
		 * @param fromdate	This is starting date
		 * @param todate	This is Ending date
		 * @param tagName	This is TagName
		 * @return AssetTrackingEntity List
		 */
		@GetMapping("/tag/get-tag-Wise-gateway-View-between-date/{pageNo}")
		public Page<AssetTrackingEntity> tagwisegatewayViewApi(@PathVariable String pageNo, String fkUserId, String role,String fromdate,String todate,String tagName)
		{
			Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
			
			
			return trackingService.tagwisegatewayViewApiBetweenDate(fkUserId,role,fromdate,todate,pageable,tagName);
		
	
	
		}
		
		
//	//..................................................................................................
	
	
	
	
	
	
	//----Get Tag Count gateway wise ----------------------
	
		/**
		 * -Get Tag Count gateway wise
		 * @param fkUserId This user id of user
		 * @param role     This is role of user
		 * @return GatewayWiseTagCountBean
		 */
	@GetMapping("/tracking/get-gateway-wise-tag-count")
	public List<GatewayWiseTagCountBean> tagCountGatewayWise(String fkUserId,String role)
	{		
		List<GatewayWiseTagCountBean> list = trackingService.tagCountGatewayWise(fkUserId,role);
		System.out.println("================" + list);
		return  list;		
	}
	
	/**
	 * Get Tag Count gateway wise
	 * @param fkUserId This user id of user
	 * @param role     This user id of user
	 * @return GatewayWiseTagCountBean
	 */
	
	//----Get Tag Count gateway wiseby pratik ----------------------
	@GetMapping("/tracking/get-gateway-wise-tag-countnew")
	public List<GatewayWiseTagCountBean> tagCountGatewayWisenew(String fkUserId,String role)
	{		
		List<GatewayWiseTagCountBean> list = trackingService.tagCountGatewayWisenew(fkUserId,role);
		System.out.println("================" + list);
		return  list;		
	}
	//.....................................................................
	
	
	
	//...............................................................................................................
	
		//----Get Tag Count gateway wise --------------by pratik--------
	/**
	 * tag Count By Gateway
	 * @param gatewayName  
	 * @param fkUserId
	 * @param role
	 * @return  List<ResponseCollectionBean>
	 * @throws ParseException
	 */
			@GetMapping("/tracking/display-graph-gateway-wise-aged-and-new-tag-count")
			public List<ResponseCollectionBean> tagCountByGateway(String gatewayName,String fkUserId,String role) throws ParseException
			{	
				
				List<ResponseCollectionBean> arrayList = new ArrayList<ResponseCollectionBean>();
				ResponseCollectionBean list = trackingService.getAgedAndNewTagCountByGateway(gatewayName,fkUserId,role);
				arrayList.add(list);
				System.out.println("================" + list.toString());
				System.out.println("================" + arrayList);
				
				return  arrayList;	
				
				
			}
	//by pratik
	/**
	 * tagCountnew
	 * @param fkUserId
	 * @param role
	 * @return List<ResponseCollectionBean>
	 * @throws ParseException
	 */
	@GetMapping("/tracking/get-aged-and-new-tag-count-for-graphnew")
	public List<ResponseCollectionBean> tagCountnew(String fkUserId,String role) throws ParseException
	{		
		List<ResponseCollectionBean> arrayList = new ArrayList<ResponseCollectionBean>();
		ResponseCollectionBean list = trackingService.getAgedAndNewTagCountnew(fkUserId,role);
		arrayList.add(list);
		System.out.println("================" + list.toString());
		System.out.println("================" + arrayList);
		return  arrayList;			
	}

	//----Get Tag Count gateway wise ----------------------
	/**
	 * -Get Tag Count gateway wise
	 * @param fkUserId
	 * @param role
	 * @return List<ResponseCollectionBean>
	 */
	@GetMapping("/tracking/get-aged-and-new-tag-count-for-graph")
	public List<ResponseCollectionBean> tagCount(String fkUserId,String role)
	{		
		List<ResponseCollectionBean> arrayList = new ArrayList<ResponseCollectionBean>();
		ResponseCollectionBean list = trackingService.getAgedAndNewTagCount(fkUserId,role);
		arrayList.add(list);
		System.out.println("================" + list.toString());
		System.out.println("================" + arrayList);
		return  arrayList;			
	}
	


//----------------------------------------------------------------------------------------------------------------
/*	
	//----Get Tag Count gateway wise -durationwise---------------------
	@GetMapping("/tracking/display-graph-gateway-wise-aged-and-new-tag-count-durationwise")
	public List<ResponseCollectionBean> tagCountByGatewaydurationwise(String duration,String gatewayName,String fkUserId,String role) throws ParseException
	{	
		if(duration.equals("lastWeek"))
		{
			Date date = new Date();  
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int i = c.get(Calendar.DAY_OF_WEEK) - 6;
			c.add(Calendar.DATE, -i - 7);
			Date start = c.getTime();
			String startDate= formatter.format(start); 
			c.add(Calendar.DATE, 6);
			Date end = c.getTime();
			String endDate= formatter.format(end); 
			System.out.println(startDate + " and " + endDate);
			List<ResponseCollectionBean> arrayList = new ArrayList<ResponseCollectionBean>();
			ResponseCollectionBean list = trackingService.getAgedAndNewTagCountByGatewaydurationwise(startDate,endDate,gatewayName,fkUserId,role);
			arrayList.add(list);
			System.out.println("================" + list.toString());
			System.out.println("================" + arrayList);
			return  arrayList;
			
		}
		if(duration.equals("lastMonth"))
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 

			Calendar aCalendar = Calendar.getInstance();			
			aCalendar.add(Calendar.MONTH, -1);			
			aCalendar.set(Calendar.DATE, 1);

			Date firstDateOfPreviousMonth = aCalendar.getTime();
			String startDate= formatter.format(firstDateOfPreviousMonth);
			aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));			
			Date lastDateOfPreviousMonth = aCalendar.getTime();
			String endDate= formatter.format(lastDateOfPreviousMonth);

			System.out.println(startDate +" and "+ endDate);
			List<ResponseCollectionBean> arrayList = new ArrayList<ResponseCollectionBean>();
			ResponseCollectionBean list = trackingService.getAgedAndNewTagCountByGatewaydurationwise(startDate,endDate,gatewayName,fkUserId,role);
			arrayList.add(list);
			System.out.println("================" + list.toString());
			System.out.println("================" + arrayList);
			return  arrayList;
			
		}
		//current date
		else
		{
			//current date
			LocalDate todayDate=LocalDate.now();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.toString());
			System.out.println("############# " + todayDate);
			
			List<ResponseCollectionBean> arrayList = new ArrayList<ResponseCollectionBean>();
			ResponseCollectionBean list = trackingService.getAgedAndNewTagCountByGatewaycurrentdate(date,gatewayName,fkUserId,role);
			arrayList.add(list);
			System.out.println("================" + list.toString());
			System.out.println("================" + arrayList);
			return  arrayList;	
		}
		return null;
	}
*/
	
	/**
	 * //Get Filter Payload Report for Super Admin, Admin, User Export Download
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return List<ResponseTrackingListBean>
	 * @throws ParseException
	 */
	@GetMapping("/asset/report/get-tag-report-view")
	public List<ResponseTrackingListBean> getTrackingReportToView(String tagName,String fkUserId, String role,String category) throws ParseException 
	{
		//			
		List<ResponseTrackingListBean> responseBean = trackingService.getTrackingReportToView(tagName,fkUserId,role,category);

		return responseBean;
	}

	
	/**
	 * //Get Filter Payload Report for Super Admin, Admin, User Export Download
	 * @param gatewayName
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/asset/report/get-tag-and-gateway-report-view")
	public List<ResponseTrackingListBean> getTrackingReportToViewGatewaywise(String gatewayName,String tagName,String fkUserId, String role) throws ParseException 
	{
		List<ResponseTrackingListBean> responseBean = new ArrayList<ResponseTrackingListBean>();
		//					
		if(tagName==null)
		{
			responseBean = trackingService.getTrackingReportToViewGatewaywiseTagReport(gatewayName,fkUserId,role);
		}else
		{
			responseBean = trackingService.getTrackingReportToViewGatewaywise(gatewayName,tagName,fkUserId,role);	
		}

		if(responseBean.size()==0)
		{
			return new ArrayList<ResponseTrackingListBean>();
		}
		return responseBean;
	}

////------------------------------------pagination...........................
//	@GetMapping("/asset/tracking/get_trackining-details-time-wise-tagnamewise/{pageNo}")
//	public Page<ResponseTrackingListBean> getAllTrackingdatailstimewise(@PathVariable String pageNo,String  tagName,String duration)
//	{
//		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
//		if(duration.equals("15min")) {
//			
//			
//			LocalTime currenttime = LocalTime.now(); 
//			LocalTime fromtime=currenttime.minusMinutes(15);
//			return trackingService.getAllTrackingdatailstimewise(fromtime,currenttime,tagName,pageable);
//			
//		}
//		if(duration.equals("30min")) {
//			LocalTime currenttime = LocalTime.now(); 
//			LocalTime fromtime=currenttime.minusMinutes(30);
//			return trackingService.getAllTrackingdatailstimewise(fromtime,currenttime,tagName,pageable);
//		}
//		if(duration.equals("45min")) {
//			LocalTime currenttime = LocalTime.now(); 
//			LocalTime fromtime=currenttime.minusMinutes(45);
//			return trackingService.getAllTrackingdatailstimewise(fromtime,currenttime,tagName,pageable);
//		}
//		return null;
//	}
	
	/**
	 * get All Data For Selected Tag And Duration With PaginationXl
	 * @param pageNo
	 * @param tagName
	 * @param duration
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return Page<AssetTrackingEntity>
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/view-for-Report-all-tracking-data-with-time-duration-with-pagination/{pageNo}")
	public Page<AssetTrackingEntity> getAllDataForSelectedTagAndDurationWithPaginationXl(@PathVariable String pageNo,String tagName,String duration,String fkUserId, String role,String category) throws ParseException
	{
		
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		if(duration.equals("lastWeek"))
		{
			Date date = new Date();  
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int i = c.get(Calendar.DAY_OF_WEEK) - 6;
			c.add(Calendar.DATE, -i - 7);
			Date start = c.getTime();
			String startDate= formatter.format(start); 
			c.add(Calendar.DATE, 6);
			Date end = c.getTime();
			String endDate= formatter.format(end); 
			System.out.println(startDate + " and " + endDate);
			return trackingService.getPayloadReportToViewDurationWithPaginationxl(tagName,startDate,endDate,fkUserId,role,category,pageable);
		}
		if(duration.equals("lastMonth"))
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 

			Calendar aCalendar = Calendar.getInstance();			
			aCalendar.add(Calendar.MONTH, -1);			
			aCalendar.set(Calendar.DATE, 1);

			Date firstDateOfPreviousMonth = aCalendar.getTime();
			String startDate= formatter.format(firstDateOfPreviousMonth);
			aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));			
			Date lastDateOfPreviousMonth = aCalendar.getTime();
			String endDate= formatter.format(lastDateOfPreviousMonth);

			System.out.println(startDate +" and "+ endDate);
			
			Page<AssetTrackingEntity> bean= trackingService.getPayloadReportToViewDurationWithPaginationxl(tagName,startDate,endDate,fkUserId,role,category,pageable);
			System.out.println("lastMonth data: " + bean);
			return bean;
		}
//		if(duration.equals("15min")) {
//			
//			
//			LocalTime currenttime = LocalTime.now(); 
//			LocalTime fromtime=currenttime.minusMinutes(15);
//			return trackingService.getAllTrackingdatailstimewise(fromtime,currenttime,tagName,fkUserId,role,pageable);
//			
//		}
//		if(duration.equals("30min")) {
//			LocalTime currenttime = LocalTime.now(); 
//			LocalTime fromtime=currenttime.minusMinutes(30);
//			return trackingService.getAllTrackingdatailstimewise(fromtime,currenttime,tagName,fkUserId,role,pageable);
//		}
//		if(duration.equals("45min")) {
//			LocalTime currenttime = LocalTime.now(); 
//			LocalTime fromtime=currenttime.minusMinutes(45);
//			return trackingService.getAllTrackingdatailstimewise(fromtime,currenttime,tagName,fkUserId,role,pageable);
//		}
		//current date
		else
		{
			//current date
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"+"00:00:00"); 
			 LocalDate d1 = LocalDate.now();
			Date  date = new SimpleDateFormat("yyyy-MM-dd"+"00:00:00").parse(d1.toString());
//			String today =formatter.format(todayDate);
			System.out.println("############# " + date);
			Page<AssetTrackingEntity> bean= trackingService.getPayloadReportToViewTodaysWithPaginationxl(tagName,date,fkUserId,role,category,pageable);
			System.out.println("today data: " + bean);
	
			return bean;
		}
	}
	/**
	 * get All Data For Selected Tag And Duration With Pagination
	 * @param pageNo
	 * @param tagName
	 * @param duration
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return Page<ResponseTrackingListBean>
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/view-all-tracking-data-with-time-duration-with-pagination/{pageNo}")
	public Page<ResponseTrackingListBean> getAllDataForSelectedTagAndDurationWithPagination(@PathVariable String pageNo,String tagName,String duration,String fkUserId, String role,String category) throws ParseException
	{
		
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		
		System.out.println("Duration"+duration);
		
		if(duration.equals("") && tagName.equals(""))
		{
			return trackingService.getallViewData(fkUserId,role,category,pageable);
		}
		if(duration.equals("lastWeek"))
		{
//			Date date = new Date();  
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
//
//			Calendar c = Calendar.getInstance();
//			c.setTime(date);
//			int i = c.get(Calendar.DAY_OF_WEEK) - 6;
//			c.add(Calendar.DATE, -i - 7);
//			Date start = c.getTime();
//			String startDate= formatter.format(start); 
//			c.add(Calendar.DATE, 6);
//			Date end = c.getTime();
//			String endDate= formatter.format(end); 
//			System.out.println(startDate + " and " + endDate);
//			return trackingService.getPayloadReportToViewDurationWithPagination(tagName,startDate,endDate,fkUserId,role,category,pageable);
//			
//			
			
			LocalDate today = LocalDate.now();
			LocalDate endDate = today.minusDays(1);//yesterday 
			LocalDate startDate = today.minusDays(7);
					//System.out.println("today"+today);
					System.out.println("startDate"+startDate);
					System.out.println("startDate"+endDate);
					
					
					return trackingService.getPayloadReportToViewDurationWithPagination(tagName,startDate.toString(),endDate.toString(),fkUserId,role,category,pageable);		
		}
		if(duration.equals("lastMonth"))
		{
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
//
//			Calendar aCalendar = Calendar.getInstance();			
//			aCalendar.add(Calendar.MONTH, -1);			
//			aCalendar.set(Calendar.DATE, 1);
//
//			Date firstDateOfPreviousMonth = aCalendar.getTime();
//			String startDate= formatter.format(firstDateOfPreviousMonth);
//			aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));			
//			Date lastDateOfPreviousMonth = aCalendar.getTime();
//			String endDate= formatter.format(lastDateOfPreviousMonth);
//
//			System.out.println(startDate +" and "+ endDate);
//			
//			Page<ResponseTrackingListBean> bean= trackingService.getPayloadReportToViewDurationWithPagination(tagName,startDate,endDate,fkUserId,role,category,pageable);
//			System.out.println("lastMonth data: " + bean);

			LocalDate today = LocalDate.now();
			LocalDate endDate = today.minusDays(1);//yesterday 
			LocalDate startDate = today.minusDays(30);
					//System.out.println("today"+today);
					System.out.println("startDate"+startDate);
					System.out.println("startDate"+endDate);
			Page<ResponseTrackingListBean> bean= trackingService.getPayloadReportToViewDurationWithPagination(tagName,startDate.toString(),endDate.toString(),fkUserId,role,category,pageable);
			return bean;
		}
		if(duration.equals("15min")) {
			
			java.util.Date date = new Date();
			SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd");
			String datenew = formatter4.format(date);
			ZoneId zone1 = ZoneId.of("Asia/Kolkata");//"Europe/Bratislava"
			LocalTime currenttime = LocalTime.now(zone1).truncatedTo(ChronoUnit.SECONDS);
			
			
			LocalDateTime currenttime1 = LocalDateTime.now(zone1).truncatedTo(ChronoUnit.SECONDS);
			LocalDateTime fromtime1=currenttime1.minusMinutes(15).truncatedTo(ChronoUnit.SECONDS);
			String currenttime2=currenttime1.toString().replace(" ","T");
			String fromtime2=fromtime1.toString().replace(" ","T");
			
			
			System.out.println("currenttime2"+currenttime2 );
			System.out.println("fromtime2"+fromtime2 );


			
			LocalTime fromtime=currenttime.minusMinutes(15).truncatedTo(ChronoUnit.SECONDS);
			System.out.println("fromtime"+fromtime );
			System.out.println("Totime"+currenttime );
			System.out.println("datenew"+datenew);
			
			return trackingService.getAllTrackingdatailstimewise(fromtime,currenttime,tagName,datenew,fkUserId,role,category,pageable);
			
		}
		if(duration.equals("30min")) {
			java.util.Date date = new Date();
			SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd");
			String datenew = formatter4.format(date);
			System.out.println("datenew"+datenew);
			ZoneId zone1 = ZoneId.of("Asia/Kolkata");//"Europe/Bratislava"
			LocalTime currenttime = LocalTime.now(zone1).truncatedTo(ChronoUnit.SECONDS); 
			
			LocalTime fromtime=currenttime.minusMinutes(30).truncatedTo(ChronoUnit.SECONDS);
			
			System.out.println("fromtime"+fromtime );
			System.out.println("Totime"+currenttime );
			System.out.println("datenew"+datenew);
			return trackingService.getAllTrackingdatailstimewise(fromtime,currenttime,tagName,datenew,fkUserId,role,category,pageable);
		}
		if(duration.equals("45min")) {
			java.util.Date date = new Date();
			SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd");
			String datenew = formatter4.format(date);
			System.out.println("datenew"+datenew);
			ZoneId zone1 = ZoneId.of("Asia/Kolkata");//"Europe/Bratislava"
			LocalTime currenttime = LocalTime.now(zone1).truncatedTo(ChronoUnit.SECONDS); 
			LocalTime fromtime=currenttime.minusMinutes(45).truncatedTo(ChronoUnit.SECONDS);
			
			
			System.out.println("fromtime"+fromtime );
			System.out.println("Totime"+currenttime );
			System.out.println("datenew"+datenew);
			return trackingService.getAllTrackingdatailstimewise(fromtime,currenttime,tagName,datenew,fkUserId,role,category,pageable);
		}
		//current date
		else
		{
			//current date
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			 LocalDate d1 = LocalDate.now();
			 System.out.println("d1"+d1.toString());
			Date  date = new SimpleDateFormat("yyyy-MM-dd").parse(d1.toString());
//			
////			String today =formatter.format(todayDate);
//			System.out.println("############# " + date);
			Page<ResponseTrackingListBean> bean= trackingService.getPayloadReportToViewTodaysWithPagination(tagName,d1.toString(),fkUserId,role,category,pageable);
			System.out.println("today data: " + bean);
	
			return bean;
		}
	}
//................................................................................
	/**
	 * get today Data For Selected Tag And Duration With Pagination
	 * @param pageNo
	 * @param tagName
	 * @param duration
	 * @param fkUserId
	 * @param role
	 * @return Page<ResponseTrackingListBean>
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/view-all-tracking-data-today-duration-with-pagination/{pageNo}")
	public Page<ResponseTrackingListBean> gettodayDataForSelectedTagAndDurationWithPagination(@PathVariable String pageNo,String tagName,String duration,String fkUserId, String role) throws ParseException
	{
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		if(duration.equals("today"))
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"+"00:00:00.00"); 
//			'2022-05-07T00:00:00.00' AND '2022-05-07T23:59:59.999'
			Date newdate =new Date();
			Date date1 = new SimpleDateFormat("yyyy-MM-dd"+"00:00:00.00").parse(newdate.toString());
			System.out.println(date1);
			
//			return trackingService.getPayloadReportTodaysWithPagination(tagName,date1,fkUserId,role,pageable);
		}
		return null;
	}
	
	
//...................................................


	
	
	
//......................................................................................................................
	/**
	 * get All Data For Selected Tag And Duration
	 * @param tagName
	 * @param duration
	 * @param fkUserId
	 * @param role
	 * @return List<ResponseTrackingListBean>
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/view-all-tracking-data-with-time-duration")
	public List<ResponseTrackingListBean> getAllDataForSelectedTagAndDuration(String tagName,String duration,String fkUserId, String role) throws ParseException
	{
		if(duration.equals("lastWeek"))
		{
			Date date = new Date();  
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int i = c.get(Calendar.DAY_OF_WEEK) - 6;
			c.add(Calendar.DATE, -i - 7);
			Date start = c.getTime();
			String startDate= formatter.format(start); 
			c.add(Calendar.DATE, 6);
			Date end = c.getTime();
			String endDate= formatter.format(end); 
			System.out.println(startDate + " and " + endDate);
			return trackingService.getPayloadReportToViewDuration(tagName,startDate,endDate,fkUserId,role);
		}
		if(duration.equals("lastMonth"))
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 

			Calendar aCalendar = Calendar.getInstance();			
			aCalendar.add(Calendar.MONTH, -1);			
			aCalendar.set(Calendar.DATE, 1);

			Date firstDateOfPreviousMonth = aCalendar.getTime();
			String startDate= formatter.format(firstDateOfPreviousMonth);
			aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));			
			Date lastDateOfPreviousMonth = aCalendar.getTime();
			String endDate= formatter.format(lastDateOfPreviousMonth);

			System.out.println(startDate +" and "+ endDate);
			return trackingService.getPayloadReportToViewDuration(tagName,startDate,endDate,fkUserId,role);
		}
		//current date
		else
		{
			//current date
			LocalDate todayDate=LocalDate.now();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.toString());
			System.out.println("############# " + todayDate);
			return trackingService.getPayloadReportToViewDurationTodays(tagName,date,fkUserId,role);
		}
	}
//...............................................................................................
	
	
	/**
	 * get All Notification AlertCount
	 * @param fkUserId
	 * @param role
	 * @return AllNotificationsCountBean
	 */
	@GetMapping("/asset/tracking/view-all-notifications-alert-count-rolewise")
	public AllNotificationsCountBean getAllNotificationAlertCount(String fkUserId, String role)
	{
		
		return trackingService.getAllNotificationAlertCount(fkUserId,role);
	}
	
//...............................................................................................
	//.....................................Low ..battery--percentage------notification
//
	/**
	 * get All Battery Percentage
	 * @param fkUserId
	 * @param role
	 * @return List<ResponseTrackingListBean>
	 */
	@GetMapping("/asset/tracking/view-all-Low-battery-percentage")
	public List<ResponseTrackingListBean> getAllBatteryPercentage(String fkUserId, String role)
	{
		return trackingService.getBatteryPercentageList(fkUserId,role);
	}
	/**
	 * get All Battery Percentage filter TagWise
	 * @param assetTagName
	 * @param fkUserId
	 * @param role
	 * @return List<ResponseTrackingListBean>
	 */
	@GetMapping("/asset/tracking/view-all-Low-battery-PercentagefilterTagWise")
	public List<ResponseTrackingListBean> getAllBatteryPercentagefilterTagWise(String assetTagName,String fkUserId, String role)
	{
		return trackingService.getBatteryPercentagefilterTagWise(assetTagName,fkUserId,role);
	}
	
	/**
	 * get All Battery Percentage Alert Count
	 * @param fkUserId
	 * @param role
	 * @return ResponseLowBatteryListBean
	 */
	@GetMapping("/asset/tracking/view-all-Low-battery-percentage-Alert-Count")
	public ResponseLowBatteryListBean getAllBatteryPercentageAlertCount(String fkUserId, String role)
	{
		return trackingService.getBatteryPercentageListAlertCount(fkUserId,role);
	}
	
	/**
	 * get tag Count GetawayWise
	 * @param fromdate
	 * @param todate
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @return ResponseltotalBean
	 */
	@GetMapping("/asset/tracking/get-datewise-gateway-count")
	public ResponseltotalBean gettagCountGetawayWise(String fromdate,String todate,String gatewayName,String fkUserId,String role)
	{
		return trackingService.gettagCountGetawayWise(fromdate,todate,gatewayName,fkUserId,role);
		
	}
	
	/**
	 * get Payload Report To View Duration For Gateway
	 * @param tagName
	 * @param gatewayName
	 * @param duration
	 * @param fkUserId
	 * @param role
	 * @return List<ResponseTrackingListBean>
	 * @throws ParseException
	 */
	//.................................................................................................................
	@GetMapping("/asset/tracking/view-all-tracking-data-with-time-duration-and-gateway-report-view")//getPayloadReportToViewDurationForGateway
	public List<ResponseTrackingListBean> getPayloadReportToViewDurationForGateway(String tagName,String gatewayName,String duration,String fkUserId, String role) throws ParseException
	{
		if(duration.equals("lastWeek"))
		{
			Date date = new Date();  
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int i = c.get(Calendar.DAY_OF_WEEK) - 6;
			c.add(Calendar.DATE, -i - 7);
			Date start = c.getTime();
			String startDate= formatter.format(start); 
			c.add(Calendar.DATE, 6);
			Date end = c.getTime();
			String endDate= formatter.format(end); 
			System.out.println(startDate + " and " + endDate);
			return trackingService.getTagReportToViewDurationForGateway(gatewayName,startDate,endDate,fkUserId,role);
		}
		if(duration.equals("lastMonth"))
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 

			Calendar aCalendar = Calendar.getInstance();			
			aCalendar.add(Calendar.MONTH, -1);			
			aCalendar.set(Calendar.DATE, 1);

			Date firstDateOfPreviousMonth = aCalendar.getTime();
			String startDate= formatter.format(firstDateOfPreviousMonth);
			aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));			
			Date lastDateOfPreviousMonth = aCalendar.getTime();
			String endDate= formatter.format(lastDateOfPreviousMonth);

			System.out.println(startDate +" and "+ endDate);
			return trackingService.getTagReportToViewDurationForGateway(gatewayName,startDate,endDate,fkUserId,role);
		}
		//current date
		else
		{
			//current date
			LocalDate todayDate=LocalDate.now();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.toString());
			System.out.println("############# " + todayDate);
			return trackingService.getTagReportToViewTodaysGateway(gatewayName,date,fkUserId,role);
		}
	}
//................................pagination.............................................................
	
	/**
	 * 
	 * @param pageNo
	 * @param gatewayName
	 * @param duration
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return Page<ResponseTrackingListBean>
	 * @throws ParseException
	 */
	@GetMapping("/asset/tracking/view-all-tracking-data-with-time-duration-and-gateway-report-view-with-pagination/{pageNo}")//getPayloadReportToViewDurationForGateway//String tagName,
	public Page<ResponseTrackingListBean> getPayloadReportToViewDurationForGatewayWithPagination(@PathVariable String pageNo,String gatewayName,String duration,String fkUserId,String role,String category) throws ParseException
	{
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		if(duration.equals("")&&gatewayName.equals(""))
		{
			return trackingService.getallViewDatadefualt(fkUserId,role,category,pageable);
		}
		if(duration.equals("lastWeek"))
			
		{
//			Date date = new Date();  
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
//
//			Calendar c = Calendar.getInstance();
//			c.setTime(date);
//			int i = c.get(Calendar.DAY_OF_WEEK) - 6;
//			c.add(Calendar.DATE, -i - 7);
//			Date start = c.getTime();
//			String startDate= formatter.format(start); 
//			c.add(Calendar.DATE, 6);
//			Date end = c.getTime();
//			String endDate= formatter.format(end); 
//			System.out.println(startDate + " and " + endDate);
			LocalDate today = LocalDate.now();
			LocalDate endDate = today.minusDays(1);//yesterday 
			LocalDate startDate = today.minusDays(7);
					//System.out.println("today"+today);
					System.out.println("startDate"+startDate);
					System.out.println("startDate"+endDate);
			return trackingService.getTagReportToViewDurationForGatewayWithPagination(gatewayName,startDate.toString(),endDate.toString(),fkUserId,role,category,pageable);
		}
		if(duration.equals("lastMonth"))
		{
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
//
//			Calendar aCalendar = Calendar.getInstance();			
//			aCalendar.add(Calendar.MONTH, -1);			
//			aCalendar.set(Calendar.DATE, 1);
//
//			Date firstDateOfPreviousMonth = aCalendar.getTime();
//			String startDate= formatter.format(firstDateOfPreviousMonth);
//			aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));			
//			Date lastDateOfPreviousMonth = aCalendar.getTime();
//			String endDate= formatter.format(lastDateOfPreviousMonth);
//
//			System.out.println(startDate +" and "+ endDate);
			LocalDate today = LocalDate.now();
			LocalDate endDate = today.minusDays(1);//yesterday 
			LocalDate startDate = today.minusDays(30);
					//System.out.println("today"+today);
					System.out.println("startDate"+startDate);
					System.out.println("startDate"+endDate);
			return trackingService.getTagReportToViewDurationForGatewayWithPagination(gatewayName,startDate.toString(),endDate.toString(),fkUserId,role,category,pageable);
		}
		//current date
		else
		{
			//current date
			LocalDate todayDate=LocalDate.now();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.toString());
			System.out.println("############# " + todayDate);
			return trackingService.getTagReportToViewTodaysGatewayWithPagination(gatewayName,todayDate.toString(),fkUserId,role,category,pageable);
		}
	}
	
	
	
	
	
	
	
	
	//.......................................................................................................................................
	
	
	
	
}
