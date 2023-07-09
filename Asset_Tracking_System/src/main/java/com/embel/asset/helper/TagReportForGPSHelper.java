package com.embel.asset.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.embel.asset.entity.AssetTrackingEntity;

public class TagReportForGPSHelper
{
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	static String[] HEADERs = {"Tag Name","Date","IMEI","Latitude","Longitude","Dispatch time" };
	static String SHEET = "Tag Excel";
	public static ByteArrayInputStream tagExcelForSuperAdminForGPS(List<AssetTrackingEntity> payList) {


		try (Workbook workbook = new HSSFWorkbook(); 
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			//System.out.println("Inside Excel...");
			int rowIdx = 1;
			for (AssetTrackingEntity track : payList) {
				Row row = sheet.createRow(rowIdx++);
				
				//System.out.println(track);
				row.createCell(0).setCellValue(track.getAssetTagName());
				row.createCell(1).setCellValue(track.getDate());
				row.createCell(2).setCellValue(track.getImeiNumber());
				row.createCell(3).setCellValue(track.getLatitude());
				row.createCell(4).setCellValue(track.getLongitude());
				row.createCell(5).setCellValue(track.getDispatchTime());
				
				

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}

		
		
		//return null;
	}
	
	

}
