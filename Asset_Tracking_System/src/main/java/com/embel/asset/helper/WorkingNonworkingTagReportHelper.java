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

import com.embel.asset.bean.UserResponseBean;
import com.embel.asset.entity.AssetTag;

public class WorkingNonworkingTagReportHelper
{

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	//assetTagName,user,admin,assetUniqueCodeMacId,assetIMEINumber,assetBarcodeSerialNumber,assetTagCategory,status
	static String[] HEADERs = {"SrNo","assetTagName","user","admin","assetUniqueCodeMacId","assetIMEINumber","asset_enable","assetTagCategory","status" };
	static String SHEET = "WorkingNonworking Excel";



	public static ByteArrayInputStream WorkingNonworkingTagExcelForSuperAdmin(List<AssetTag> tagList)
	{
		
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
			int icnt=0;
			for (AssetTag track : tagList) {
				Row row = sheet.createRow(rowIdx++);
				//{"SrNo","assetTagName","user","admin","assetUniqueCodeMacId","assetIMEINumber","asset_enable","assetTagCategory","status" };
				//System.out.println(track);
				row.createCell(0).setCellValue(icnt);
				row.createCell(1).setCellValue(track.getAssetTagName());
				row.createCell(2).setCellValue(track.getUser());
				row.createCell(3).setCellValue(track.getAdmin());
				row.createCell(4).setCellValue(track.getAssetUniqueCodeMacId());
				row.createCell(5).setCellValue(track.getAssetIMEINumber());
				if(track.getAssetTagEnable()==1)
				{
					row.createCell(6).setCellValue("WorkingTag");
					
				}else
				{
					row.createCell(6).setCellValue("Non-WorkingTag");
				}
				
				row.createCell(7).setCellValue(track.getAssetTagCategory());
				row.createCell(8).setCellValue(track.getStatus());

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			System.out.println(e);
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}



	public static ByteArrayInputStream WorkingNonworkingTagExcelForAdminAndUser(List<AssetTag> tagList) {
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
		int icnt=0;
		for (AssetTag track : tagList) {
			Row row = sheet.createRow(rowIdx++);
			//{"SrNo","assetTagName","user","admin","assetUniqueCodeMacId","assetIMEINumber","asset_enable","assetTagCategory","status" };
			//System.out.println(track);
			row.createCell(0).setCellValue(icnt);
			row.createCell(1).setCellValue(track.getAssetTagName());
			row.createCell(2).setCellValue(track.getUser());
			row.createCell(3).setCellValue(track.getAdmin());
			row.createCell(4).setCellValue(track.getAssetUniqueCodeMacId());
			row.createCell(5).setCellValue(track.getAssetIMEINumber());
			if(track.getAssetTagEnable()==1)
			{
				row.createCell(6).setCellValue("WorkingTag");
				
			}else
			{
				row.createCell(6).setCellValue("Non-WorkingTag");
			}
			
			row.createCell(7).setCellValue(track.getAssetTagCategory());
			row.createCell(8).setCellValue(track.getStatus());

		}

		workbook.write(out);
		return new ByteArrayInputStream(out.toByteArray());
	} catch (IOException e) {
		System.out.println(e);
		throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
	}
}


	public static ByteArrayInputStream WorkingNonworkingTagExcelForUser(List<AssetTag> tagList) {

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
			int icnt=0;
			for (AssetTag track : tagList) {
				Row row = sheet.createRow(rowIdx++);
				//{"SrNo","assetTagName","user","admin","assetUniqueCodeMacId","assetIMEINumber","asset_enable","assetTagCategory","status" };
				//System.out.println(track);
				row.createCell(0).setCellValue(icnt);
				row.createCell(1).setCellValue(track.getAssetTagName());
				row.createCell(2).setCellValue(track.getUser());
				row.createCell(3).setCellValue(track.getAdmin());
				row.createCell(4).setCellValue(track.getAssetUniqueCodeMacId());
				row.createCell(5).setCellValue(track.getAssetIMEINumber());
				if(track.getAssetTagEnable()==1)
				{
					row.createCell(6).setCellValue("WorkingTag");
					
				}else
				{
					row.createCell(6).setCellValue("Non-WorkingTag");
				}
				
				row.createCell(7).setCellValue(track.getAssetTagCategory());
				row.createCell(8).setCellValue(track.getStatus());

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			System.out.println(e);
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}
}
