package com.embel.asset.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.embel.asset.bean.UserResponseBean;
import com.embel.asset.entity.EmployeeUser;

public class UserReportForSuperAdminHelper
{
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	//u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address
	static String[] HEADERs = {"SrNo","UserName","FirstName","LastName","PhoneNumber","Email-Id","CompanyName","Address" };
	static String SHEET = "User Excel";

	public static ByteArrayInputStream UserExcelForSuperAdmin(List<UserResponseBean> userList) 
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
		for (UserResponseBean track : userList) {
			Row row = sheet.createRow(rowIdx++);
			//{"SrNo","UserName","FirstName","LastName","PhoneNumber","Email-Id","Password","CompanyName","Address" };
			//System.out.println(track);
			row.createCell(0).setCellValue(track.getPkuserId());
			row.createCell(1).setCellValue(track.getUsername());
			row.createCell(2).setCellValue(track.getFirstName());
			row.createCell(3).setCellValue(track.getLastName());
			row.createCell(4).setCellValue(track.getPhoneNumber());
			row.createCell(5).setCellValue(track.getEmailId());
			//row.createCell(6).setCellValue(track.getPassword());
			row.createCell(6).setCellValue(track.getCompanyName());
			row.createCell(7).setCellValue(track.getAddress());

		}

		workbook.write(out);
		return new ByteArrayInputStream(out.toByteArray());
	} catch (IOException e) {
		System.out.println(e);
		throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
	}
}

	public static ByteArrayInputStream UserExcelForAdminAndUser(List<UserResponseBean> userList) 
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
		for (UserResponseBean track : userList) {
			Row row = sheet.createRow(rowIdx++);
			//{"SrNo","UserName","FirstName","LastName","PhoneNumber","Email-Id","Password","CompanyName","Address" };
			//System.out.println(track);
			row.createCell(0).setCellValue(track.getPkuserId());
			row.createCell(1).setCellValue(track.getUsername());
			row.createCell(2).setCellValue(track.getFirstName());
			row.createCell(3).setCellValue(track.getLastName());
			row.createCell(4).setCellValue(track.getPhoneNumber());
			row.createCell(5).setCellValue(track.getEmailId());
			//row.createCell(6).setCellValue(track.getPassword());
			row.createCell(6).setCellValue(track.getCompanyName());
			row.createCell(7).setCellValue(track.getAddress());

		}

		workbook.write(out);
		return new ByteArrayInputStream(out.toByteArray());
	} catch (IOException e) {
		System.out.println(e);
		throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream UserExcelForUser(List<UserResponseBean> userList) 
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
			for (UserResponseBean track : userList) {
				Row row = sheet.createRow(rowIdx++);
				//{"SrNo","UserName","FirstName","LastName","PhoneNumber","Email-Id","Password","CompanyName","Address" };
				//System.out.println(track);
				row.createCell(0).setCellValue(track.getPkuserId());
				row.createCell(1).setCellValue(track.getUsername());
				row.createCell(2).setCellValue(track.getFirstName());
				row.createCell(3).setCellValue(track.getLastName());
				row.createCell(4).setCellValue(track.getPhoneNumber());
				row.createCell(5).setCellValue(track.getEmailId());
				//row.createCell(6).setCellValue(track.getPassword());
				row.createCell(6).setCellValue(track.getCompanyName());
				row.createCell(7).setCellValue(track.getAddress());

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			System.out.println(e);
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
			}
		
		
	}

	public static ByteArrayInputStream EmployeeUserExcelForSuperAdmin(List<EmployeeUser> userList) {
		
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
			for (EmployeeUser track : userList) {
				Row row = sheet.createRow(rowIdx++);
				//{"SrNo","UserName","FirstName","LastName","PhoneNumber","Email-Id","Password","CompanyName","Address" };
				//System.out.println(track);
				row.createCell(0).setCellValue(track.getPkuserId());
				row.createCell(1).setCellValue(track.getUsername());
				row.createCell(2).setCellValue(track.getFirstName());
				row.createCell(3).setCellValue(track.getLastName());
				row.createCell(4).setCellValue(track.getPhoneNumber());
				row.createCell(5).setCellValue(track.getEmailId());
				row.createCell(6).setCellValue(track.getPassword());
				row.createCell(7).setCellValue(track.getCompanyName());
				row.createCell(8).setCellValue(track.getAddress());

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			System.out.println(e);
			throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
		}
	}

}
