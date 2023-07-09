package com.embel.asset.helper;

import java.io.File;
import java.io.UnsupportedEncodingException;


import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;




@Component
public class Mailwithattachment 
{
	@Autowired
	private   JavaMailSender javamailsender;
	
	public  void sendEmail(String recipientEmail)
			{
		MimeMessage message = javamailsender.createMimeMessage(); 
//		MimeMessagePreparator message = new MimeMessagePreparator();
		
		try {
		MimeMessageHelper helper = new MimeMessageHelper(message,true);

		try {
			helper.setFrom("embelbackup@gmail.com", "Embel Support");
		} catch (UnsupportedEncodingException | javax.mail.MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		helper.setTo(recipientEmail);

		String subject = "Backup file of assetTracking database";

		String content = "<p>Hello,</p>"
				+ "<p>You have file backup in mail attachement.</p>"
				+ "<p>with file Name:asset_tracking_system_db.sql</p>";

		helper.setSubject(subject);
	String filename = "D:\\databackup\\asset_tracking_system_db.sql";//change accordingly  
//		DataSource source = new FileDataSource(filename);  
//		message.setDataHandler(new DataHandler(source));
//		message.setFileName(filename);
//		helper.setText(content, true);
		
		FileSystemResource filesystem=new FileSystemResource(new File(filename));
	

		helper.setText(content);
		helper.addAttachment(filesystem.getFilename(),filesystem);
		
		System.out.println("massage:"+message);
		javamailsender.send(message);
	 } catch (MessagingException | javax.mail.MessagingException e) {
		    
		  }
	}	

}
