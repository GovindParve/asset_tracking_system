package com.embel.asset.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.embel.asset.bean.ResponseTrackingListBean;
import com.embel.asset.bean.UserResponseBean;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateUserPDF
{
	public static ByteArrayInputStream billPDF(List<ResponseTrackingListBean> list) 
	{	
		//System.out.println("Generate Class----> "+list);

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

			return null;
	}
	public static ByteArrayInputStream UserPDF(List<UserResponseBean> userList)
	{
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {

			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setWidths(new int[]{3,3,3,3,3,3,3,3,3});

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("SR NO", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("User Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Role", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("First Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Last Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Phone Number", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Email-Id", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			
//			hcell = new PdfPCell(new Phrase("Password", headFont));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			table.addCell(hcell);
//			
			hcell = new PdfPCell(new Phrase("CompanyName", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Address", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			
			int icnt=1;
			for (UserResponseBean bean : userList) {

				PdfPCell cell;
				
//				System.out.println(bean.getTrackingId());
				cell = new PdfPCell(new Phrase(String.valueOf(icnt++)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getUsername().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(bean.getRole().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getFirstName().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				
				
				cell = new PdfPCell(new Phrase(bean.getLastName().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    
				//u.pkuserId,u.username,u.role,u.firstName,u.lastName,u.phoneNumber,u.emailId,u.password,u.companyName,u.address
				cell = new PdfPCell(new Phrase(bean.getPhoneNumber().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				
				cell = new PdfPCell(new Phrase(bean.getEmailId().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				
//				cell = new PdfPCell(new Phrase(bean.getPassword().toString()));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setPaddingRight(5);
//				table.addCell(cell);    

				cell = new PdfPCell(new Phrase(bean.getCompanyName().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				cell = new PdfPCell(new Phrase(bean.getAddress().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell); 
				
			
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);

			document.close();

		} catch (DocumentException ex) {

			ex.printStackTrace();

			//logger.error("Error occurred: {0}", ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
		//return "200";
		// }

	}

}
