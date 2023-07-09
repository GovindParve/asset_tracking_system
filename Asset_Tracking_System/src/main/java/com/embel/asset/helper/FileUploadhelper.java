package com.embel.asset.helper;



import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadhelper
{
	//pratik chaudhari
	//public final String UPLOAD_DIR="/home/ubuntu/Asset Tracking/files";
	public final String UPLOAD_DIR="C:\\Users\\HP\\git\\Asset_Tracking_System\\src\\main\\resources\\File";
//	public final String UPLOAD_DIR="C:\\Users\\HP\\git\\Asset_Tracking_System\\src\\main\\resources\\File";
	
	public boolean UploadFile(MultipartFile multipartfile)
	{
		boolean	f=false;
		
		try {
			
//			InputStream  is=multipartfile.getInputStream();
//			
//			byte data[] =new byte[is.available()];
//			is.read(data);
//		
//			//writre 
//			FileOutputStream fos =new FileOutputStream(UPLOAD_DIR+File.separator+multipartfile.getOriginalFilename());
//			fos.write(data);
//			
//			fos.close();
			
			
			
			//instead of upper code in one line
			Files.copy(multipartfile.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+multipartfile.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			f=true;
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		return f;
	}
	
	
}
