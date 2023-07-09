package com.embel.asset.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.embel.asset.bean.ResponseDownlodeCountBean;
import com.embel.asset.entity.AssetTrackingEntity;
import com.embel.asset.service.AssetTagService;
import com.embel.asset.service.AssetTrackingService;
import com.embel.asset.service.EmployeUserService;
import com.embel.asset.service.UserDownlodService;
import com.embel.asset.service.UserService;
import com.embel.asset.service.columnmappingService;

@RestController
@CrossOrigin("*")
public class ExcelPDFReportGenerateController implements ErrorController{

	@Autowired
	AssetTrackingService trackingService;
	@Autowired
	AssetTagService tagService;
	
	@Autowired
	UserDownlodService userdownlodservice;
	@Autowired
	columnmappingService columnmappingservice;
	@Autowired
	UserService userservice;
	@Autowired
	EmployeUserService empuserService;
	
	
	/**
	 * get Downloding Count
	 * @author Pratik chaudhari 
	 * @param fkUserId
	 * @param role
	 * @return ResponseDownlodeCountBean
	 */
	@GetMapping("/asset/report/get-downloding-count-rolewise")
	public ResponseDownlodeCountBean getDownlodingCount(String fkUserId,String role )
	{
		
		return userdownlodservice.getDownlodeCount(fkUserId,role);
		
	}
	//.........................................................................................................
	
	/**
	 * get Working Non working tag Report
	 * @author Pratik chaudhari
	 * @param fileType
	 * @param fkUserId
	 * @param role
	 * @return Resource 
	 * @throws ParseException
	 */
	@GetMapping("/user/report/get-working-NonWorking-tag-report-export-excel-or-pdf-download")
	public ResponseEntity<Resource> getWorkingNonworkingtagReport(String fileType,String fkUserId, String role) throws ParseException 
	{
			
	
		if(fileType.equals("excel"))
		{
			String filename = "WorkingNonWorkingTagDetailsReportExcel.xls";
			
			
			InputStreamResource file = new InputStreamResource(tagService.loadWorkingNonWorkingTagDataExcel(fkUserId,role));
							
			
			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);
		

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(file);
		}
//		if(fileType.equals("pdf"))
//		{
//			String filename = "WorkingNonWorkingTagDetailsReportPDF.pdf";
//			InputStreamResource file = new InputStreamResource(tagService.loadWorkingNonWorkingTagDataPDF(fkUserId,role));
//			//current download count 
//			long ldownloadcount = 1;
//			//current date
//			Date date = new Date();
//			
//			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);
//
//
//			return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//					.contentType(MediaType.parseMediaType("application/pdf"))
//					.body(file);  
//		}
		
		return null;
	}
	
	
	
	
	
	//----------------------------user report excl pdf-----------------------------------------------------
	
////	//
	/**
	 * Get Filter Payload Report for Super Admin, Admin, User Export Download
	 * @author Pratik Chaudhari
	 * @param fileType
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return Resource
	 * @throws ParseException
	 */
		@GetMapping("/user/report/get-User-report-export-excel-or-pdf-download")
		public ResponseEntity<Resource> getUserReportForExportDownload(String fileType,String fkUserId, String role,String category) throws ParseException 
		{
			if(fileType.equals("excel"))
			{
				String filename = "UserDetailsReportExcel.xls";
				
				
				InputStreamResource file = new InputStreamResource(userservice.loadUserDataExcel(fkUserId,role,category));
								
				
				//current download count 
				long ldownloadcount = 1;
				//current date
				Date date = new Date();
				
				userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);
			

				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
						.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
						.body(file);
			}
			if(fileType.equals("pdf"))
			{
				String filename = "UserDetailsReportPDF.pdf";
				InputStreamResource file = new InputStreamResource(userservice.loadAllUserDataPDF(fkUserId,role,category));
				//current download count 
				long ldownloadcount = 1;
				//current date
				Date date = new Date();
				
				userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);


				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
						.contentType(MediaType.parseMediaType("application/pdf"))
						.body(file);  
			}
			
			return null;
		}
////	
	
//...................................................Employee user ...............................................................
		/**
		 * get Employee User Report For Export Download
		 * @author Pratik chaudhari 
		 * @param fileType
		 * @param fkUserId
		 * @param role
		 * @param category
		 * @return Resource
		 * @throws ParseException
		 */
		@GetMapping("/user/report/get-Employee-User-report-export-excel-or-pdf-download")
		public ResponseEntity<Resource> getEmployeeUserReportForExportDownload(String fileType,String fkUserId, String role,String category) throws ParseException 
		{
						
		
			if(fileType.equals("excel"))
			{
				String filename = "EmployeeUserDetailsReportExcel.xls";
				
				
				InputStreamResource file = new InputStreamResource(empuserService.loadUserDataExcel(fkUserId,role,category));
								
				//
				//current download count 
				long ldownloadcount = 1;
				//current date
				Date date = new Date();
				
				userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);
			

				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
						.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
						.body(file);
			}
			if(fileType.equals("pdf"))
			{
				String filename = "EmployeeUserDetailsReportPDF.pdf";
				InputStreamResource file = new InputStreamResource(empuserService.loadAllUserDataPDF(fkUserId,role,category));
				//current download count 
				long ldownloadcount = 1;
				//current date
				Date date = new Date();
				
				userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);


				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
						.contentType(MediaType.parseMediaType("application/pdf"))
						.body(file);  
			}
			
			return null;
		}
////	
		
		
		
//..............................................................................................................................................................	
	//
		
		/**
		 * Get Filter Payload Report for Super Admin, Admin, User Export Download
		 * @author Pratik chaudhari
		 * @param tagName
		 * @param fileType
		 * @param fkUserId
		 * @param role
		 * @param category
		 * @return Resource
		 * @throws ParseException
		 */
	@GetMapping("/asset/report/get-tag-report-export-excel-or-pdf-download")
	public ResponseEntity<Resource> getPayloadReportForExportDownload(String tagName,String fileType,String fkUserId, String role,String category) throws ParseException 
	{
					
		System.out.println(tagName);
		if(fileType.equals("excel"))
		{
			String filename = "TagDetailsReportExcel.xls";
			InputStreamResource file = new InputStreamResource(trackingService.loadAllTrackingData(tagName,fkUserId,role,category));
							
			
			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);
		

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(file);
		}
		if(fileType.equals("pdf"))
		{
			String filename = "TagDetailsReportPDF.pdf";
			InputStreamResource file = new InputStreamResource(trackingService.loadAllTrackingDataPDF(tagName,fkUserId,role,category));
			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);


			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/pdf"))
					.body(file);  
		}
		
		return null;
	}

	//Get Filter Payload Report Between Two Date for Super Admin, Admin, User Export Download
	/**
	 * Get Filter Payload Report Between Two Date for Super Admin, Admin, User Export Download
	 * @author Pratik chaudhari
	 * @param fileType
	 * @param fromDate
	 * @param toDate
	 * @param tagName
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return Resource
	 * @throws ParseException
	 */
	@GetMapping("/asset/report/get-tag-report-export-excel-or-pdf-download-between-date")
	public ResponseEntity<Resource> getPayloadReportBetweenTwoDateForAdminExportDownload(String fileType,String fromDate, String toDate,String tagName, String fkUserId, String role,String category) throws ParseException 
	{
		if(fileType.equals("excel"))
		{
			String filename = "TagDetailsReportExcelBetweenDate.xls";
			InputStreamResource file = new InputStreamResource(trackingService.loadAllTrackingDataBetweenDate(fromDate,toDate,tagName,fkUserId,role,category));

			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);
	
		
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(file);
		}
		if(fileType.equals("pdf"))
		{
			String filename = "TagDetailsReportPDFBetweenDate.pdf";
			InputStreamResource file = new InputStreamResource(trackingService.loadAllTrackingDataPDFBetweenDate(fromDate,toDate,tagName,fkUserId,role,category));
			

			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);

			System.out.println(ldownloadcount);
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/pdf"))
					.body(file);  
		}
		return null;
	}
	
	//pratik :gatewayName report 
	/**
	 * use to gatewayName report 
	 * @author Pratik chaudhari
	 * @param fileType
	 * @param fromDate
	 * @param toDate
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @return Resource
	 * @throws ParseException
	 */
	@GetMapping("/asset/report/get-gatewayName-report-export-excel-or-pdf-download-between-date")
	public ResponseEntity<Resource> getPayloadReportBetweenTwoDateForAdminExportDownloadgatewayName(String fileType,String fromDate, String toDate,String gatewayName , String fkUserId, String role) throws ParseException 
	{
		System.out.println(fileType+fromDate+toDate+gatewayName+fkUserId+role);
		if(fileType.equals("excel"))
		{
			String filename = "DateWiseGatewayReportExcel.xls";
			InputStreamResource file = new InputStreamResource(trackingService.loadAllTrackingDataGatewaywiseBetweenDate(fromDate,toDate,gatewayName,fkUserId,role));

			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();

			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(file);
		}
		if(fileType.equals("pdf"))
		{
			String filename = "DateWiseGatewayReportPDF.pdf";
			InputStreamResource file = new InputStreamResource(trackingService.loadAllTrackingDataPDFGatewaywiseBetweenDate(fromDate,toDate,gatewayName,fkUserId,role));

			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/pdf"))
					.body(file);  
		}
		return null;
	}
	
	
	//
	/**
	 * Get Filter Payload Report Between Two Date for Super Admin, Admin, User Export Download
	 * @author Pratik chaudhari
	 * @param fileType
	 * @param fromDate
	 * @param toDate
	 * @param duration
	 * @param gatewayName
	 * @param fkUserId
	 * @param role
	 * @return Resource
	 * @throws ParseException
	 */
	@GetMapping("/asset/report/get-tag-report-export-excel-or-pdf-download-gateway-wise")
	public ResponseEntity<Resource> getPayloadReportGatewatWiseForAdminExportDownload(String fileType,@RequestParam(required = false) String fromDate,@RequestParam(required = false) String toDate,@RequestParam(required = false) String duration,String gatewayName,String fkUserId,String role) throws ParseException 
	{
		if(fileType.equals("excel"))
		{
			String filename = "GatewayWiseTagDetailsReportExcel.xls";
			InputStreamResource file = new InputStreamResource(trackingService.loadAllTrackingDataGatewatWise(gatewayName,fkUserId,role,fromDate,toDate,duration));

			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);

			System.out.println(ldownloadcount);
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(file);
		}
		if(fileType.equals("pdf"))
		{
			String filename = "GatewayWiseTagDetailsReportPDF.pdf";
			InputStreamResource file = new InputStreamResource(trackingService.loadAllTrackingDataPDFGatewatWise(gatewayName,fkUserId,role,fromDate,toDate,duration));

			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);

			System.out.println(ldownloadcount);
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/pdf"))
					.body(file);  
		}
		return null;
	}
	//.....................................
	/**
	 * get tag list Between Report excel
	 * @param pageNo
	 * @param fkUserId
	 * @param role
	 * @param fromdate
	 * @param todate
	 * @param fromtime
	 * @param totime
	 * @return AssetTrackingEntity
	 */
	@GetMapping("/tag/get-list-between-report-excel/{pageNo}")
	public Page<AssetTrackingEntity> gettaglistBetweenReportexcel(@PathVariable String pageNo,String fkUserId, String role, String fromdate, String todate, String fromtime, String totime)
	{
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		return trackingService.gettaglistBetweenReportexcel(fkUserId,role,fromdate,todate,fromtime,totime,pageable);
	}
	/**
	 * get gateway list Between Report excel
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return String
	 */
	@GetMapping("/tag/get-gateway-list-between-report-excel")
	public List<String> getgatewaylistBetweenReportexcel(String fkUserId, String role)
	{
		return trackingService.getgatewaylistBetweenReportexcel(fkUserId,role);
	}
	//.......................................

	
	/**
	 * tag wise gateway View Api
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param fromdate
	 * @param todate
	 * @param fromtime
	 * @param totime
	 * @param fileType
	 * @return Resource
	 */
	@GetMapping("/tag/get-tag-Wise-gateway-View-between-date-and-time-Report")
	public ResponseEntity<Resource> tagwisegatewayViewApi(String fkUserId, String role,String fromdate,String todate,String fromtime,String totime,String fileType)
	{

		if(fileType.equals("excel"))
		{
			String filename = "TagDetailsGetewaywiseReportExcel.xls";
			
			
			InputStreamResource file = new InputStreamResource(trackingService.tagwisegatewayViewApiBetweenDateReportexcel(fkUserId,role,fromdate,todate,fromtime,totime));
							
		 
			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
		
			//userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);


			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(file);
		}
//		if(fileType.equals("pdf"))
//		{
//			String filename = "UserDetailsReportPDF.pdf";
//			InputStreamResource file = new InputStreamResource(trackingService.tagwisegatewayViewApiBetweenDateReport(fkUserId,role,fromdate,todate,tagName));
//			//current download count 
//			long ldownloadcount = 1;
//			//current date
//			Date date = new Date();
//			
//			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);
//
//
//			return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//					.contentType(MediaType.parseMediaType("application/pdf"))
//					.body(file);  
//		}
//		
//		return null;
//		
//		
		return null;
	
	


	}





//..
	//.............................................................
	
	/**
	 * get Payload Report For Dynamic Column Export Download
	 * @author Pratik chaudhari 
	 * @param columnname
	 * @param fileType
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return Resource
	 * @throws ParseException
	 */
	@GetMapping("/asset/report/get-tracking-report-for-dynamic-columns")
	public ResponseEntity<Resource> getPayloadReportForDynamicColumnExportDownload(String columnname,String fileType, String fkUserId, String role,String category) throws ParseException 
	{
		if(fileType.equals("excel"))
		{
			String filename = "ColumnNameWiseReport.xls";
			InputStreamResource file = new InputStreamResource(columnmappingservice.loadAllTrackingDataDynamicColumnwiseXls(columnname,fkUserId,role,category));

			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);

			System.out.println(ldownloadcount);
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(file);
		}
		if(fileType.equals("pdf"))
		{
			String filename = "ColumnNameWiseReportPDF.pdf";
			InputStreamResource file = new InputStreamResource(columnmappingservice.loadAllTrackingDataDynamicColumnwise(columnname,fkUserId,role,category));

			//current download count 
			long ldownloadcount = 1;
			//current date
			Date date = new Date();
			
			userdownlodservice.addDownlodingDetails(fileType,fkUserId,role,date,ldownloadcount);

			System.out.println(ldownloadcount);
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/pdf"))
					.body(file);  
		}
		return null;
	}
	//.....................................
	
}
