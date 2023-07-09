package com.embel.asset.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class AssetGatewayHelper {
	
	
	
	
	
	public String getBarcodeNumber()
	{
		
		try {
			String text = "GW1, MacId:C4:4F:33:4F:65:69";
			String path = "E:\\Barcode Files\\barcode.jpg";
			
			Code128Writer writer = new Code128Writer();
			BitMatrix matrix = writer.encode(text, BarcodeFormat.CODE_128, 500, 300);
			
			MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
			
			System.out.println("Barcode Created...");
			
			return "Success";
			
		} catch (WriterException e) {
			System.out.println("Error while wrirting file...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error while getting path file...");
			e.printStackTrace();
		}
		return "Fail";
	}
	
	
	public byte[] getBarcodeNumber(String text, int width, int height) throws WriterException, IOException
	{
		Code128Writer qrCodeWriter = new Code128Writer();
	    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.CODE_128, width, height);
	    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
	      //MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFF04B4AE);
	      MatrixToImageConfig con = new MatrixToImageConfig();
	      MatrixToImageWriter.writeToStream(bitMatrix, "jpg", pngOutputStream, con);
	      byte[] pngData = pngOutputStream.toByteArray();
	      
	      return pngData;
	}
	
	public InputStreamResource getBarcodeInputStreamResource(byte[] pngData) throws DocumentException, MalformedURLException, IOException
	{
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    Document document = new Document();
	    PdfWriter.getInstance(document, out);
	    document.open();
	    
	    Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
	    Paragraph para = new Paragraph("Scan Barcode Number", font);
	    para.setAlignment(Element.ALIGN_CENTER);
	    document.add(para);
	    document.add(Chunk.NEWLINE);

	    Image image = Image.getInstance(pngData);
	    image.scaleAbsolute(170f, 170f);
	    image.setAlignment(Element.ALIGN_CENTER);
	    
	    document.add(image);
	    document.close();
	    ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
	    return new InputStreamResource(bis);
	 }

}
