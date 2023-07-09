package com.embel.asset.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.embel.asset.entity.AssetTrackingEntity;

public class AssetTrackingReportExcelHelper {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
////	  static String[] HEADERs = { "Date", "Time", "Vol", "Mag_tamp", "Prob_tamp", "Axis_tamp", "AMR ID", "Building Name OR Wing Name" };
//		static String[] HEADERs = 
		static String SHEET = "TagReportExcel";
	
	  public static ByteArrayInputStream TagExcelReport(List<AssetTrackingEntity> tagList, List<String> gatwayList) 
	  {	
		  
		 // String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//		  static String[] HEADERs = { "Date", "Time", "Vol", "Mag_tamp", "Prob_tamp", "Axis_tamp", "AMR ID", "Building Name OR Wing Name" };
			List<String> HEADERs = new ArrayList<String>() ;
			System.out.println("gatwayList:"+gatwayList);
			HEADERs=gatwayList;
			System.out.println("gatwayList:"+HEADERs);
			HEADERs.add(0,"TAGS/GATEWAYS");
			
			System.out.println("Tags:"+HEADERs);
	    try (Workbook workbook = new HSSFWorkbook(); 
	    		ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	    	
	      Sheet sheet = workbook.createSheet(SHEET);
	
	      // Header
	      Row headerRow = sheet.createRow(0);
	
	      for (int col = 0; col < HEADERs.size(); col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs.get(col));
	      }
	
	      int rowIdx = 1;
	  for(AssetTrackingEntity taglist : tagList)
		 {
		        Row row = sheet.createRow(rowIdx++);
		        row.createCell(0).setCellValue(taglist.getAssetTagName());
		       for(int i=1;i<=HEADERs.size()-1;i++)
		        {
	    	  
		    	   System.out.println(taglist.getAssetGatewayName()+" == "+HEADERs.get(i));
					    		  if(taglist.getAssetGatewayName().equals(HEADERs.get(i)))
					    		  {
					    			  row.createCell(i).setCellValue(taglist.getEntryTime());//+" "+taglist.getTime()
					    			  System.out.println("ENTRYTIME"+taglist.getEntryTime());
					    		  }
					    		  else {
					    			  row.createCell(i).setCellValue("Tag did not Entered");
					    		  }
		        			    	  
		        }
   	        
	      }
	
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (Exception e) {
	    	e.printStackTrace();
	      throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
	    }
	  }
	

		
	

}
