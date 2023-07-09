package com.embel.asset.helper;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.embel.asset.AssetTrackingSystemApplication;

@Component
public class DateTimeSchedular 
{
	@Autowired
	private Mailwithattachment mailattach;
	
	private static final Logger logger = LoggerFactory.getLogger(AssetTrackingSystemApplication.class);
	 

//	@Scheduled(initialDelay = 3000, fixedDelay = 30000) //cron = "0 1 * * * *"
//	public void start()
//	{
//		MyJob job = new MyJob();
//		try {
//			job.execute();
//		} catch (IOException e) {
//		
//			e.printStackTrace();
//		}
//	}
//	@Scheduled(initialDelay = 3000, fixedDelay = 30000)
	public void schedularJob() {
		
		String f = null;
		String executeCmd = "";
		
		
		executeCmd = "C:/Program Files/MySQL/MySQL Server 5.5/bin/mysqldump -u " + "root" + " -p" + "root" + " --add-drop-database -B " + "asset_tracking_system_db.sql" + " -r " + "D:/databackup/asset_tracking_system_db.sql";

		f="D:\\databackup\\asset_tracking_system_db.sql";
		
		
		System.out.println(f);
		
		Process runtimeProcess;
		try
		{
			System.out.println(executeCmd);
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0)
			{
				logger.info("Backup created successfully on: " +new Date());
			}
			else
			{
				logger.info("Could not create the backup on: " +new Date());
			}
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
//		return f;
		mailattach.sendEmail("pchaudhari883@gmail.com");
		
	}
	
	
}
