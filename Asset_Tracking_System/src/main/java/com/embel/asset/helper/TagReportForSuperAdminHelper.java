package com.embel.asset.helper;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.embel.asset.bean.ResponseTrackingListBean;
import com.embel.asset.entity.AssetTrackingEntity;

public class TagReportForSuperAdminHelper {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	//assetTagName,atd.assetGatewayName,atd.tagEntryLocation,atd.tagExistLocation,atd.date,atd.entryTime,atd.existTime,atd.dispatchTime
	static String[] HEADERs = {"Tag Name","Gateway Name","Entry Time","Exit Time","Tag Location","Stay-Time","Dispatch time" };
	static String SHEET = "Tag Excel";

	public static ByteArrayInputStream tagExcelForSuperAdmin(List<ResponseTrackingListBean> tagList) throws ParseException
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
			for (ResponseTrackingListBean track : tagList) {
				Row row = sheet.createRow(rowIdx++);

				//System.out.println(track);
				row.createCell(0).setCellValue(track.getAssetTagName());
				row.createCell(1).setCellValue(track.getAssetGatewayName());
				row.createCell(2).setCellValue(track.getEntryTime());
				row.createCell(3).setCellValue(track.getExistTime());
				row.createCell(4).setCellValue(track.getTagEntryLocation());
				
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

				Date existTime =null;

				try {
					existTime = formatter11.parse(track.getExistTime());
				}catch (Exception e) {
					System.out.println(e);
				}
				
				Date entryTime = formatter11.parse(track.getEntryTime());
				if (existTime == null) 
				{
					existTime = entryTime;
				}
				//System.out.println("%%%% "+track.getExistTime());

				long difference_In_Time = existTime.getTime()-entryTime.getTime() ;
				long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
				long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
				long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;
				long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
				long difference_In_Years = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;
				
				
				if(difference_In_Time < 0)
				{
					difference_In_Time =-difference_In_Time;
					difference_In_Seconds=-difference_In_Seconds;    //Updater or Modifier 
					difference_In_Minutes=-difference_In_Minutes;
					difference_In_Hours=-difference_In_Hours;
					difference_In_Days=-difference_In_Days;
					difference_In_Years=-difference_In_Years;
				}
				
				
		        String s1 = Long.toString(difference_In_Hours);
		        String s2 = Long.toString(difference_In_Minutes);
		        String s3=Long.toString(difference_In_Seconds);
		        System.out.println("S1:"+s1);
		        System.out.println("S2:"+s2);
		        String s = s1 +":"+ s2 +":"+ s3;
		        String concat = s1 + s2;
		        Integer stay = Integer.parseInt(concat);
				//System.out.print("Difference" + " between two dates is: " +stay);
//				System.out.println(difference_In_Years+ " years, "+ difference_In_Days+ " days, "+ difference_In_Hours+ " hours, "+ difference_In_Minutes+ " minutes, "+ difference_In_Seconds+ " seconds");

				row.createCell(5).setCellValue(s);
				// row.createCell(6).setCellValue(track.getExistTime());
				row.createCell(6).setCellValue(track.getDispatchTime());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			System.out.println(e);
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream tagExcelForAdminAndUser(List<ResponseTrackingListBean> tagList) throws ParseException 
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
			for (ResponseTrackingListBean track : tagList) {
				Row row = sheet.createRow(rowIdx++);
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				Date existTime =null;
				try {existTime= formatter11.parse(track.getExistTime());} catch (Exception e) {}
			
				Date entryTime =null;
				try {entryTime =formatter11.parse(track.getEntryTime());} catch (Exception e) {}
						
				if (existTime == null) 
				{
					existTime = entryTime;
				}
				//System.out.println(track);
				row.createCell(0).setCellValue(track.getAssetTagName());
				row.createCell(1).setCellValue(track.getAssetGatewayName());
				row.createCell(2).setCellValue(track.getEntryTime());
				row.createCell(3).setCellValue(track.getExistTime());
				row.createCell(4).setCellValue(track.getTagExistLocation());
				
				System.out.println("%%%% "+track.getDate());

				long difference_In_Time =  existTime.getTime()-entryTime.getTime();
				long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
				long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
				long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;
				long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
				long difference_In_Years = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;
				if(difference_In_Time < 0)
				{
					difference_In_Time =-difference_In_Time;
					difference_In_Seconds=-difference_In_Seconds;    //Updater or Modifier 
					difference_In_Minutes=-difference_In_Minutes;
					difference_In_Hours=-difference_In_Hours;
					difference_In_Days=-difference_In_Days;
					difference_In_Years=-difference_In_Years;
				}
				
		        String s1 = Long.toString(difference_In_Hours);
		        String s2 = Long.toString(difference_In_Minutes);
		        String s3 = Long.toString(difference_In_Seconds);
		        String s = s1 +":"+ s2+":"+s3;
		        String concat = s1 + s2;
		        Integer stay = Integer.parseInt(concat);
				//System.out.print("Difference" + " between two dates is: " +stay);
				//System.out.println(difference_In_Years+ " years, "+ difference_In_Days+ " days, "+ difference_In_Hours+ " hours, "+ difference_In_Minutes+ " minutes, "+ difference_In_Seconds+ " seconds");

				row.createCell(5).setCellValue(s);
				// row.createCell(6).setCellValue(track.getExistTime());
				row.createCell(6).setCellValue(track.getDispatchTime());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream tagExcelForSuperAdminBetweenDate(List<ResponseTrackingListBean> tagList) throws ParseException 
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
			for (ResponseTrackingListBean track : tagList) {
				Row row = sheet.createRow(rowIdx++);

				//System.out.println(track);
				row.createCell(0).setCellValue(track.getAssetTagName());
				row.createCell(1).setCellValue(track.getAssetGatewayName());
				row.createCell(2).setCellValue(track.getEntryTime());
				row.createCell(3).setCellValue(track.getExistTime());
				row.createCell(4).setCellValue(track.getTagEntryLocation());
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));


				Date existTime =null;
				try {existTime=	formatter11.parse(track.getExistTime());} catch (Exception e) {}
				//existTime=	formatter11.parse(track.getExistTime());
				Date entryTime =null;
				try {entryTime=	formatter11.parse(track.getEntryTime());} catch (Exception e) {}
				
				if (existTime == null) 
				{
					existTime = entryTime;
				}
				//System.out.println("%%%% "+track.getExistTime());

				long difference_In_Time = existTime.getTime() - entryTime.getTime();
				long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
				long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
				long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;
				long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
				long difference_In_Years = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;

				
				if(difference_In_Time < 0)
				{
					difference_In_Time =-difference_In_Time;
					difference_In_Seconds=-difference_In_Seconds;    //Updater or Modifier 
					difference_In_Minutes=-difference_In_Minutes;
					difference_In_Hours=-difference_In_Hours;
					difference_In_Days=-difference_In_Days;
					difference_In_Years=-difference_In_Years;
				}
				
		        String s1 = Long.toString(difference_In_Hours);
		        String s2 = Long.toString(difference_In_Minutes);
		        String s3 = Long.toString(difference_In_Seconds);
		        String s = s1+":"+s2+":"+s3;
		        String concat = s1 + s2;
		       Integer stay = Integer.parseInt(concat);
				//System.out.print("Difference" + " between two dates is: " +stay);

				//System.out.println(difference_In_Years+ " years, "+ difference_In_Days+ " days, "+ difference_In_Hours+ " hours, "+ difference_In_Minutes+ " minutes, "+ difference_In_Seconds+ " seconds");

				row.createCell(5).setCellValue(s);
				// row.createCell(6).setCellValue(track.getExistTime());
				row.createCell(6).setCellValue(track.getDispatchTime());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream tagExcelForAdminAndUserBetweenDate(List<ResponseTrackingListBean> tagList) throws ParseException
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
			for (ResponseTrackingListBean track : tagList) {
				Row row = sheet.createRow(rowIdx++);

				//System.out.println(track);
				row.createCell(0).setCellValue(track.getAssetTagName());
				row.createCell(1).setCellValue(track.getAssetGatewayName());
				row.createCell(2).setCellValue(track.getEntryTime());
				row.createCell(3).setCellValue(track.getExistTime());
				row.createCell(4).setCellValue(track.getTagEntryLocation());
				
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));


				Date existTime =null;
				try {existTime=	formatter11.parse(track.getExistTime());} catch (Exception e) {}
				
				Date entryTime =null;
				try {entryTime=	formatter11.parse(track.getEntryTime());} catch (Exception e) {}
				
				if (existTime == null) 
				{
					existTime = entryTime;
				}
				//System.out.println("%%%% "+track.getExistTime());

				long difference_In_Time =  existTime.getTime()-entryTime.getTime();
				long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
				long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
				long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;
				long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
				long difference_In_Years = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;
				if(difference_In_Time < 0)
				{
					difference_In_Time =-difference_In_Time;
					difference_In_Seconds=-difference_In_Seconds;    //Updater or Modifier 
					difference_In_Minutes=-difference_In_Minutes;
					difference_In_Hours=-difference_In_Hours;
					difference_In_Days=-difference_In_Days;
					difference_In_Years=-difference_In_Years;
				}
				
		        String s1 = Long.toString(difference_In_Hours);
		        String s2 = Long.toString(difference_In_Minutes);
		        String s3 = Long.toString(difference_In_Seconds);
		        String s = s1+":"+s2+":"+s3;
		        String concat = s1 + s2;
		        Integer stay = Integer.parseInt(concat);
				//System.out.print("Difference" + " between two dates is: " +stay);
//				System.out.println(difference_In_Years+ " years, "+ difference_In_Days+ " days, "+ difference_In_Hours+ " hours, "+ difference_In_Minutes+ " minutes, "+ difference_In_Seconds+ " seconds");

				row.createCell(5).setCellValue(s);
				// row.createCell(6).setCellValue(track.getExistTime());
				row.createCell(6).setCellValue(track.getDispatchTime());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream tagExcelForSuperAdminGatewatWise(List<ResponseTrackingListBean> tagList) throws ParseException 
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
			for (ResponseTrackingListBean track : tagList) {
				Row row = sheet.createRow(rowIdx++);

				//System.out.println(track);
				row.createCell(0).setCellValue(track.getAssetTagName());
				row.createCell(1).setCellValue(track.getAssetGatewayName());
				row.createCell(2).setCellValue(track.getEntryTime());
				row.createCell(3).setCellValue(track.getExistTime());
				row.createCell(4).setCellValue(track.getTagEntryLocation());
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				
				
				Date existTime = null;
					
				try {
					existTime=formatter11.parse(track.getExistTime());
				}catch (Exception e) {
					System.out.println("existTime"+existTime);
				}
				
				Date entryTime = formatter11.parse(track.getEntryTime());
				if (existTime == null) 
				{
					existTime = entryTime;
				}
				//System.out.println("%%%% "+track.getExistTime());

				long difference_In_Time =existTime.getTime()-entryTime.getTime();
				long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
				long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
				long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;
				long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
				long difference_In_Years = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;
				if(difference_In_Time < 0)
				{
					difference_In_Time =-difference_In_Time;
					difference_In_Seconds=-difference_In_Seconds;    //Updater or Modifier 
					difference_In_Minutes=-difference_In_Minutes;
					difference_In_Hours=-difference_In_Hours;
					difference_In_Days=-difference_In_Days;
					difference_In_Years=-difference_In_Years;
				}
		        String s1 = Long.toString(difference_In_Hours);
		        String s2 = Long.toString(difference_In_Minutes);
		        String s3 = Long.toString(difference_In_Seconds);
		        String s = s1+":"+s2+":"+s3;
		        String concat = s1 + s2;
		        Integer stay = Integer.parseInt(concat);
				//System.out.print("Difference" + " between two dates is: " +stay);
//				System.out.println(difference_In_Years+ " years, "+ difference_In_Days+ " days, "+ difference_In_Hours+ " hours, "+ difference_In_Minutes+ " minutes, "+ difference_In_Seconds+ " seconds");

				row.createCell(5).setCellValue(s);
				// row.createCell(6).setCellValue(track.getExistTime());
				row.createCell(6).setCellValue(track.getDispatchTime());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream tagExcelForAdminAndUserGatewatWise(List<ResponseTrackingListBean> tagList) throws ParseException
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
			for (ResponseTrackingListBean track : tagList) {
				Row row = sheet.createRow(rowIdx++);

				//System.out.println(track);
				row.createCell(0).setCellValue(track.getAssetTagName());
				row.createCell(1).setCellValue(track.getAssetGatewayName());
				row.createCell(2).setCellValue(track.getEntryTime());
				row.createCell(3).setCellValue(track.getExistTime());
				row.createCell(4).setCellValue(track.getTagEntryLocation());
				
//				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
//				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
//				
//				
//				Date existTime = null;
//					
//				try {
//					existTime=formatter11.parse(track.getExistTime());
//				}catch (Exception e) {
//					System.out.println("existTime"+existTime);
//				}
				
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss",Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

				Date existTime =null;
				try {existTime = formatter11.parse(track.getExistTime());} catch (Exception e) {}
				 
				Date entryTime = formatter11.parse(track.getEntryTime());
				if (existTime == null) 
				{
					existTime = entryTime;
				}
				//System.out.println("%%%% "+track.getExistTime());

				long difference_In_Time = existTime.getTime()-entryTime.getTime();
				long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
				long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
				long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;
				long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
				long difference_In_Years = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;
				if(difference_In_Time < 0)
				{
					difference_In_Time =-difference_In_Time;
					difference_In_Seconds=-difference_In_Seconds;    //Updater or Modifier 
					difference_In_Minutes=-difference_In_Minutes;
					difference_In_Hours=-difference_In_Hours;
					difference_In_Days=-difference_In_Days;
					difference_In_Years=-difference_In_Years;
				}
		        String s1 = Long.toString(difference_In_Hours);
		        String s2 = Long.toString(difference_In_Minutes);
		        String s3 = Long.toString(difference_In_Seconds);
		        String s = s1+":"+s2+":"+s3;
		        String concat = s1 + s2;
		        Integer stay = Integer.parseInt(concat);
				//System.out.print("Difference" + " between two dates is: " +stay);
//				System.out.println(difference_In_Years+ " years, "+ difference_In_Days+ " days, "+ difference_In_Hours+ " hours, "+ difference_In_Minutes+ " minutes, "+ difference_In_Seconds+ " seconds");

				row.createCell(5).setCellValue(s);
				// row.createCell(6).setCellValue(track.getExistTime());
				row.createCell(6).setCellValue(track.getDispatchTime());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream ColumnWiseExcelForSuperAdmin(List<AssetTrackingEntity> tagList)
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
			for (AssetTrackingEntity track : tagList) {
				Row row = sheet.createRow(rowIdx++);

				//System.out.println(track);
				row.createCell(0).setCellValue(track.getAssetTagName());
				row.createCell(1).setCellValue(track.getAssetGatewayName());
				row.createCell(2).setCellValue(track.getTagEntryLocation());
				row.createCell(3).setCellValue(track.getTagExistLocation());
				row.createCell(4).setCellValue(track.getDate());
				
				
//
//				row.createCell(5).setCellValue(s);
//				// row.createCell(6).setCellValue(track.getExistTime());
//				row.createCell(6).setCellValue(track.getDispatchTime());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}

	
}

