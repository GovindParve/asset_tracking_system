package com.embel.asset.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.embel.asset.helper.QRCodeGeneratorHelper;
import com.embel.asset.helper.QRCodePDFGenerator;

@CrossOrigin("*")
@RestController
public class BarcodeQRCodeController {
	
	@Autowired
	QRCodeGeneratorHelper qrCodeGeneratorHelper;
	
	@Autowired
	QRCodePDFGenerator qrCodePDFGenerator;
	
	
	
	@GetMapping(value = "/export", produces = MediaType.APPLICATION_PDF_VALUE)
	   public ResponseEntity<InputStreamResource>  employeeReport
	   (@RequestParam(defaultValue = "Hello") String text) throws IOException, DocumentException, WriterException
	   {
	      byte[] pngData = qrCodeGeneratorHelper.getQRCode(text, 0, 0);
	      InputStreamResource inputStreamResource = qrCodePDFGenerator.InputStreamResource(pngData);
	      HttpHeaders headers = new HttpHeaders();
	      headers.add("Content-Disposition", "inline; " + "filename=my-file.pdf");
	      
	      return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(inputStreamResource);
	   }

}
