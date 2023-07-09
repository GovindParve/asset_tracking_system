package com.embel.asset.helper;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.embel.asset.bean.ResponseTrackingListBean;
import com.embel.asset.entity.AssetTrackingEntity;
import com.embel.asset.entity.columnmapping;
import com.embel.asset.repository.AssetTrackingRepository;
import com.embel.asset.repository.columnmappingRepository;
import com.embel.asset.service.columnmappingService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateBillPDF {	
	
//	@Autowired
//	
//	columnmappingRepository columnmappingRepo;

	public static ByteArrayInputStream billPDF(List<ResponseTrackingListBean> list) throws ParseException 
	{	
		//System.out.println("Generate Class----> "+list);

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setWidths(new int[]{3, 3, 3,3,3,3,3,3});

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("SR NO", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tag Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Gateway Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Entry Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Exit Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);//Exist Location
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tag Location", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Stay Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			/*
			 * hcell = new PdfPCell(new Phrase("Exist Time", headFont));
			 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(hcell);
			 */

			hcell = new PdfPCell(new Phrase("Dispatch Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			int icnt=1;
			for (ResponseTrackingListBean bean : list) {

				PdfPCell cell;
				
//				System.out.println(bean.getTrackingId());
				cell = new PdfPCell(new Phrase(String.valueOf(icnt++)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getAssetTagName().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getAssetGatewayName().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getEntryTime().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				String exist = "";
				if((bean.getTagExistLocation()) == null)
				{
					exist = "NA";
				}
				else
				{
					exist = bean.getTagExistLocation().toString();
				}
				
				cell = new PdfPCell(new Phrase(exist));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				cell = new PdfPCell(new Phrase(bean.getTagEntryLocation().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				//------------------------------------------------
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

				Date existTime = null;
			try {
				existTime= formatter11.parse(bean.getExistTime());
			}catch (Exception e) {
				System.out.println(e);
			}
				
				Date entryTime = formatter11.parse(bean.getEntryTime());
				if(bean.getExistTime().equals("N/A"))
				{
					existTime = entryTime;
				}
				else
				{
					existTime = formatter11.parse(bean.getExistTime());
				}
	//			System.out.println("%%%% "+bean.getExistTime());

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
//				System.out.print("Difference" + " between two dates is: " +stay);
				//System.out.println(difference_In_Years+ " years, "+ difference_In_Days+ " days, "+ difference_In_Hours+ " hours, "+ difference_In_Minutes+ " minutes, "+ difference_In_Seconds+ " seconds");

				/*
				 * cell = new PdfPCell(new Phrase(bean.getEntryTime().toString()));
				 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); cell.setPaddingRight(5);
				 * table.addCell(cell);
				 */ 

				cell = new PdfPCell(new Phrase(s));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if(bean.getDispatchTime()!=null)
				{
					cell = new PdfPCell(new Phrase(bean.getDispatchTime().toString()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}else
				{
					cell = new PdfPCell(new Phrase());
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}
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

	public static ByteArrayInputStream billPDFBetweenDate(List<ResponseTrackingListBean> list) throws ParseException 
	{	
		//System.out.println("Generate Class----> "+list);

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setWidths(new int[]{1, 3, 3,3,3,3,3,3});

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("SR NO", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tag Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Gateway Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Entry Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tag Location", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Stay Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			/*
			 * hcell = new PdfPCell(new Phrase("Exist Time", headFont));
			 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(hcell);
			 */

			hcell = new PdfPCell(new Phrase("Dispatch Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			int icnt=1;
			for (ResponseTrackingListBean bean : list) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(icnt++)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getAssetTagName().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getAssetGatewayName().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getTagEntryLocation().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				String exist = "";
				if((bean.getTagExistLocation()) == null)
				{
					exist = "NA";
				}
				else
				{
					exist = bean.getTagExistLocation().toString();
				}
				
				cell = new PdfPCell(new Phrase(exist));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				cell = new PdfPCell(new Phrase(bean.getDate().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				//------------------------------------------------
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

				Date existTime =null;
				try {	existTime=formatter11.parse(bean.getExistTime());} catch (Exception e) {}
				
				Date entryTime =null;
				try {entryTime=formatter11.parse(bean.getEntryTime());} catch (Exception e) {}
				
				if((bean.getExistTime()) == null)
				{
					existTime = entryTime;
				}
				else
				{
					try {existTime = formatter11.parse(bean.getExistTime());} catch (Exception e) {}
					
				}
				//System.out.println("%%%% "+bean.getExistTime());
			if(!bean.getExistTime().equals("N/A"))
			{
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
			//	System.out.println(difference_In_Years+ " years, "+ difference_In_Days+ " days, "+ difference_In_Hours+ " hours, "+ difference_In_Minutes+ " minutes, "+ difference_In_Seconds+ " seconds");

				/*
				 * cell = new PdfPCell(new Phrase(bean.getEntryTime().toString()));
				 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); cell.setPaddingRight(5);
				 * table.addCell(cell);
				 */ 

				cell = new PdfPCell(new Phrase(s));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}
			cell = new PdfPCell(new Phrase("N/A"));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(5);
			table.addCell(cell);		

				if(bean.getDispatchTime()!=null)
				{
					cell = new PdfPCell(new Phrase(bean.getDispatchTime().toString()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}else
				{
					cell = new PdfPCell(new Phrase());
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}
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

	public static ByteArrayInputStream billPDFGatewatWise(List<ResponseTrackingListBean> list) throws ParseException 
	{	
		//System.out.println("Generate Class----> "+list);

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setWidths(new int[]{1, 3, 3,3,3,3,3,3});

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("SR NO", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tag Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Gateway Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			
			hcell = new PdfPCell(new Phrase("Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell); 
			
			hcell = new PdfPCell(new Phrase("Entry Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Entry Location", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			

			hcell = new PdfPCell(new Phrase("Stay Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			/*
			 * hcell = new PdfPCell(new Phrase("Exist Time", headFont));
			 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(hcell);
			 */

			hcell = new PdfPCell(new Phrase("Dispatch Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			int icnt=1;
			for (ResponseTrackingListBean bean : list) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(icnt++)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getAssetTagName().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getAssetGatewayName().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getTagEntryLocation().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				String exist = "";
				if((bean.getTagExistLocation()) == null)
				{
					exist = "NA";
				}
				else
				{
					exist = bean.getTagExistLocation().toString();
				}
				
				cell = new PdfPCell(new Phrase(exist));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				cell = new PdfPCell(new Phrase(bean.getDate().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				//------------------------------------------------
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				Date existTime=null;
				try {
					 existTime =formatter11.parse(bean.getExistTime());
				} catch (Exception e) {
					System.err.println(e);
					System.err.println("bean.getExistTime():"+bean.getExistTime());
				}
				
				Date entryTime = formatter11.parse(bean.getEntryTime());
				if(bean.getExistTime().equals("N/A"))
				{
					existTime = entryTime;
				}
				else
				{
					existTime = formatter11.parse(bean.getExistTime());
				}
		//		System.out.println("%%%% "+bean.getExistTime());

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
	//			System.out.print("Difference" + " between two dates is: " +stay);
//				System.out.println(difference_In_Years+ " years, "+ difference_In_Days+ " days, "+ difference_In_Hours+ " hours, "+ difference_In_Minutes+ " minutes, "+ difference_In_Seconds+ " seconds");

				/*
				 * cell = new PdfPCell(new Phrase(bean.getEntryTime().toString()));
				 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); cell.setPaddingRight(5);
				 * table.addCell(cell);
				 */ 

				cell = new PdfPCell(new Phrase(s));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if(bean.getDispatchTime()!=null)
				{
					cell = new PdfPCell(new Phrase(bean.getDispatchTime().toString()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}else
				{
					cell = new PdfPCell(new Phrase());
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}
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

	public static ByteArrayInputStream billPDFGatewatWiseBetweenDate(List<ResponseTrackingListBean> list) throws ParseException 
	{	
		//System.out.println("Generate Class----> "+list);

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setWidths(new int[]{1, 3, 3,3,3,3,3,3});

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("SR NO", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tag Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Gateway Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			

			hcell = new PdfPCell(new Phrase("Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Entry Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tag Location", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);


			hcell = new PdfPCell(new Phrase("Stay Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			/*
			 * hcell = new PdfPCell(new Phrase("Exist Time", headFont));
			 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(hcell);
			 */

			hcell = new PdfPCell(new Phrase("Dispatch Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			int icnt=1;
			for (ResponseTrackingListBean bean : list) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(icnt++)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getAssetTagName().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getAssetGatewayName().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getDate().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				String exist = "";
				if((bean.getTagExistLocation()) == null)
				{
					exist = "NA";
				}
				else
				{
					exist = bean.getTagExistLocation().toString();
				}
				
				cell = new PdfPCell(new Phrase(bean.getEntryTime()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				cell = new PdfPCell(new Phrase(bean.getTagEntryLocation().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				//------------------------------------------------
				SimpleDateFormat formatter11 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
				formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

				Date existTime =null;
				try {existTime=formatter11.parse(bean.getExistTime());} catch (Exception e) {}
						
				Date entryTime =null; 
				try {entryTime=formatter11.parse(bean.getEntryTime());} catch (Exception e) {}
						
				if((bean.getExistTime()) == null)
				{
					existTime = entryTime;
				}
				else
				{
					try {existTime = formatter11.parse(bean.getExistTime());} catch (Exception e) {}
					
				}
//				System.out.println("%%%% "+bean.getExistTime());
				if(!bean.getExistTime().equals("N/A"))
				{
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

					/*
					 * cell = new PdfPCell(new Phrase(bean.getEntryTime().toString()));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); cell.setPaddingRight(5);
					 * table.addCell(cell);
					 */ 

					cell = new PdfPCell(new Phrase(s));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);

				}else
				{
				cell = new PdfPCell(new Phrase("N/A"));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				}
				if(bean.getDispatchTime()!=null)
				{
					cell = new PdfPCell(new Phrase(bean.getDispatchTime().toString()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}else
				{
					cell = new PdfPCell(new Phrase());
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}
				
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

	public static ByteArrayInputStream billPDFDynamicColumnWise(List<AssetTrackingEntity> list, String columnname, String dbcolumnname) throws ParseException
	{
				

				Document document = new Document();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				
				
				try {
					
					System.out.println("Inside pdf code:---");
					PdfPTable table = new PdfPTable(7);
					table.setWidthPercentage(100);
					table.setWidths(new int[]{1,3,3,3,3,3,3});

					Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

					PdfPCell hcell;
					hcell = new PdfPCell(new Phrase("SR NO", headFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("Tag Name", headFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("Date", headFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(hcell);

					if(list.get(0).getTagCategoty().equals("GPS")) 
					{
					hcell = new PdfPCell(new Phrase("Latitude", headFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(hcell);

				

					hcell = new PdfPCell(new Phrase("Longitude", headFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("IMEI NO", headFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(hcell);
					}
					else
					{
						hcell = new PdfPCell(new Phrase("Tag Location", headFont));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(hcell);

					

						hcell = new PdfPCell(new Phrase("Entry Time", headFont));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Exist Time", headFont));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(hcell);
						
					}
					

					hcell = new PdfPCell(new Phrase(columnname, headFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(hcell);
					int icnt=1;
					for (AssetTrackingEntity bean : list) {

						PdfPCell cell;

						cell = new PdfPCell(new Phrase(String.valueOf(icnt++)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(bean.getAssetTagName().toString()));
						cell.setPaddingLeft(5);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(bean.getDate()));
						cell.setPaddingLeft(5);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						if(!bean.getTagCategoty().equals("GPS")) {
							
							cell = new PdfPCell(new Phrase(bean.getTagEntryLocation().toString()));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(5);
							table.addCell(cell);
							
							//------------------------------------------------
							SimpleDateFormat formatter11 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
							formatter11.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

							String existTime =bean.getExistTime();
							String entryTime = bean.getEntryTime();
							
							
							cell = new PdfPCell(new Phrase(entryTime.toString()));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(5);
							table.addCell(cell);  

							cell = new PdfPCell(new Phrase(existTime.toString()));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(5);
							table.addCell(cell);
							
							
							
						}else {
							
							cell = new PdfPCell(new Phrase(bean.getLatitude()));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(5);
							table.addCell(cell);
							
							

							cell = new PdfPCell(new Phrase(bean.getLongitude()));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(5);
							table.addCell(cell);  

							cell = new PdfPCell(new Phrase(bean.getImeiNumber()));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(5);
							table.addCell(cell);
						}
				

					
						String columnvalue=null;
						if(dbcolumnname.equals("lb1")) {columnvalue=bean.getLb1();}
						if(dbcolumnname.equals("lb2")) {columnvalue=bean.getLb2();}	
						if(dbcolumnname.equals("lb3")) {columnvalue=bean.getLb3();}
						if(dbcolumnname.equals("lb4")) {columnvalue=bean.getLb4();}
						if(dbcolumnname.equals("lb5")) {columnvalue=bean.getLb5();}
						if(dbcolumnname.equals("lb6")) {columnvalue=bean.getLb6();}
						if(dbcolumnname.equals("lb7")) {columnvalue=bean.getLb7();}
						if(dbcolumnname.equals("lb8")) {columnvalue=bean.getLb8();}
						if(dbcolumnname.equals("lb9")) {columnvalue=bean.getLb9();}
						if(dbcolumnname.equals("lb10")) {columnvalue=bean.getLb10();}
						if(dbcolumnname.equals("lb11")) {columnvalue=bean.getLb11();}
						if(dbcolumnname.equals("lb12")) {columnvalue=bean.getLb12();}
						if(dbcolumnname.equals("lb13")) {columnvalue=bean.getLb13();}
						if(dbcolumnname.equals("lb14")) {columnvalue=bean.getLb14();}
						if(dbcolumnname.equals("lb15")) {columnvalue=bean.getLb15();}
						
						cell = new PdfPCell(new Phrase(columnvalue));
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
					System.out.println(ex);
					//logger.error("Error occurred: {0}", ex);
				}
				System.out.println("pdf is ready::::");
				return new ByteArrayInputStream(out.toByteArray());
				//return "200";
				// }
			}

	public static ByteArrayInputStream billPDFForgps(List<AssetTrackingEntity> payList)
	{
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setWidths(new int[]{3,3,3,3,3,3,3});

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("SR NO", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tag Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("IMEI", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Latitude", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Longitude", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

//			hcell = new PdfPCell(new Phrase("Stay Time", headFont));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			table.addCell(hcell);

			

			hcell = new PdfPCell(new Phrase("Dispatch Time", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			int icnt=1;
			for (AssetTrackingEntity bean : payList) {

				PdfPCell cell;
				
//				System.out.println(bean.getTrackingId());
				cell = new PdfPCell(new Phrase(String.valueOf(icnt++)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getAssetTagName().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getDate()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(bean.getImeiNumber()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				///{"Tag Name","Date","IMEI","Latitude","Longitude","Dispatch time" };
				
				cell = new PdfPCell(new Phrase(bean.getLatitude()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				cell = new PdfPCell(new Phrase(bean.getLongitude()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);    

				
				

				if(bean.getDispatchTime()!=null)
				{
					cell = new PdfPCell(new Phrase(bean.getDispatchTime().toString()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}else
				{
					cell = new PdfPCell(new Phrase());
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}
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
