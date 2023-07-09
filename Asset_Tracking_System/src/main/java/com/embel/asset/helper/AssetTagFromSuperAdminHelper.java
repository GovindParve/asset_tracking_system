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
import com.embel.asset.entity.AssetTagStock;

public class AssetTagFromSuperAdminHelper 
{
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	static String[] HEADERs = {"SrNo","asset_barcode_number_or_serial_number","asset_imei_number","asset_imsi_number","asset_tag_category","asset_tag_name","asset_unique_code_or_mac_id"};
	static String SHEET = "AssetTag Excel";



	public static ByteArrayInputStream StockExcelForOrganization(List<AssetTagStock> taglist) {
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
			for (AssetTagStock track : taglist) {
				Row row = sheet.createRow(rowIdx++);
			//	{"SrNo","asset_barcode_number_or_serial_number","asset_imei_number","asset_imsi_number","asset_tag_category","asset_tag_name","asset_unique_code_or_mac_id"};
				//System.out.println(track);
				row.createCell(0).setCellValue(rowIdx);
				row.createCell(1).setCellValue(track.getAssetBarcodeSerialNumber());
				row.createCell(2).setCellValue(track.getAssetIMEINumber());
				row.createCell(3).setCellValue(track.getAssetIMSINumber());
				row.createCell(4).setCellValue(track.getAssetTagCategory());
				row.createCell(5).setCellValue(track.getAssetTagName());
				row.createCell(6).setCellValue(track.getAssetUniqueCodeMacId());
			
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			System.out.println(e);
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}

	}
	

}
