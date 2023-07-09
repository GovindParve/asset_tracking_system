package com.embel.asset.service;

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

/**
 * this is use to fetch report coloumnwise
 * 
 * @author Pratik chaudhari
 *
 */
public class ColumnWiseReportHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	// assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime

	public static ByteArrayInputStream ColumnWiseExcelForSuperAdmin(List<AssetTrackingEntity> tagList,
			String columnname, String dbcolumnname) {
		String[] HEADERs = { "Tag Name", "Date", "Entry Time", "Tag Location", "Exist Time", "Dispatch Time",
				columnname };
		String SHEET = "Tag Excel";
		try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			// System.out.println("Inside Excel...");
			int rowIdx = 1;
			for (AssetTrackingEntity track : tagList) {
				Row row = sheet.createRow(rowIdx++);

				// System.out.println(track);
				row.createCell(0).setCellValue(track.getAssetTagName());
				row.createCell(1).setCellValue(track.getDate());
				row.createCell(2).setCellValue(track.getEntryTime());
				row.createCell(3).setCellValue(track.getTagEntryLocation());
				row.createCell(4).setCellValue(track.getExistTime());

				row.createCell(5).setCellValue(track.getDispatchTime());
				String columnvalue = null;
				if (dbcolumnname.equals("lb1")) {
					columnvalue = track.getLb1();
				}
				if (dbcolumnname.equals("lb2")) {
					columnvalue = track.getLb2();
				}
				if (dbcolumnname.equals("lb3")) {
					columnvalue = track.getLb3();
				}
				if (dbcolumnname.equals("lb4")) {
					columnvalue = track.getLb4();
				}
				if (dbcolumnname.equals("lb5")) {
					columnvalue = track.getLb5();
				}
				if (dbcolumnname.equals("lb6")) {
					columnvalue = track.getLb6();
				}
				if (dbcolumnname.equals("lb7")) {
					columnvalue = track.getLb7();
				}
				if (dbcolumnname.equals("lb8")) {
					columnvalue = track.getLb8();
				}
				if (dbcolumnname.equals("lb9")) {
					columnvalue = track.getLb9();
				}
				if (dbcolumnname.equals("lb10")) {
					columnvalue = track.getLb10();
				}
				if (dbcolumnname.equals("lb11")) {
					columnvalue = track.getLb11();
				}
				if (dbcolumnname.equals("lb12")) {
					columnvalue = track.getLb12();
				}
				if (dbcolumnname.equals("lb13")) {
					columnvalue = track.getLb13();
				}
				if (dbcolumnname.equals("lb14")) {
					columnvalue = track.getLb14();
				}
				if (dbcolumnname.equals("lb15")) {
					columnvalue = track.getLb15();
				}
				row.createCell(6).setCellValue(columnvalue);

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}
}
