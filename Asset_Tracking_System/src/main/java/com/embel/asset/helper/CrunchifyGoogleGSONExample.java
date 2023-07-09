package com.embel.asset.helper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
 
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
@Component
public class CrunchifyGoogleGSONExample 
{


//		JSONArray array = readFileContent();
//		convertJSONArraytoArrayList(array);
//	
 
	public  ArrayList<?> convertJSONArraytoArrayList(JSONArray array) {
 
		// Use method fromJson() to deserializes the specified Json into an object
		// of the specified class
		final ArrayList<?> jsonArray = new Gson().fromJson(array.toString(), ArrayList.class);
//		log("\nArrayList: " + jsonArray);
		return jsonArray;
 
	}
 
	public  JSONArray readFileContent() throws FileNotFoundException {
		JSONArray crunchifyArray = new JSONArray();
		String lineFromFile;
		FileReader file =new FileReader("C:\\Users\\HP\\git\\Asset_Tracking_System\\src\\main\\resources\\File\\json.json");
		
		try (BufferedReader bufferReader = new BufferedReader(file)) {
		     
			
			//Thread.sleep(7000);  
			while ((lineFromFile = bufferReader.readLine()) != null)
			{
				if (lineFromFile != null && !lineFromFile.isEmpty())
				{
					JSONObject crunchifyObject = new JSONObject();
					log("Line: ==>" + lineFromFile);
 
					// escape any blank space between tokens
					String[] split = lineFromFile.split("\\s+");
					
					System.out.println(split);
					
//					crunchifyObject.get("gMacId");
//					crunchifyObject.get("tMacId");
//					crunchifyObject.get("bSN");
//					crunchifyObject.get("batStat");
//					crunchifyArray.put(crunchifyObject);
//					System.out.println(crunchifyArray);
////					crunchifyObject.put("gSrNo", split[0]);
//					crunchifyObject.put("gMacId", split[1]);
//					crunchifyObject.put("tMacId", split[2]);
//					crunchifyObject.put("bSN", split[3]);
//					crunchifyObject.put("batStat", split[4]);//
//					crunchifyArray.put(crunchifyObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\nJSONArray: " + crunchifyArray.toString());
		return crunchifyArray;
 
	}
 
	private void open(FileReader file) {
		// TODO Auto-generated method stub
		
	}

	public  void log(Object string) {
		System.out.println(string);
	}
}
