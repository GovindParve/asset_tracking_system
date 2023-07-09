package com.embel.asset.controller;

import java.io.IOException;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class GeoLocationController implements ErrorController {

	@GetMapping("/geocode")
	public String getGeocode(@RequestParam String latitude, @RequestParam String longitude) throws IOException {

		//OkHttpClient client = new OkHttpClient();
		/*
		 * // Latitude and Longitude -> calculate distance and location of tag
		@PostMapping("/tag-location-and-distance-from-lat-and-long")
		public void calculateLocationAndDistance(@RequestParam String latitude , String longitude)
		{
			
		}
		
		 * String encodedAddress = URLEncoder.encode(address, "UTF-8"); Request request
		 * = new Request.Builder() .url(
		 * "https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en&address="
		 * + encodedAddress) .get() .addHeader("x-rapidapi-host",
		 * "google-maps-geocoding.p.rapidapi.com") .addHeader("x-rapidapi-key",
		 * {your-api-key-here} Use your API Key here ) .build(); ResponseBody
		 * responseBody = client.newCall(request).execute().body();
		 * 
		 * https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=YOUR_API_KEY
		 * 
		 */
		return "**************";
	}
}