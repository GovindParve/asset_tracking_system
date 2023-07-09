package com.embel.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AssetTrackingSystemApplication {

	public static void main(String[] args) {
		System.out.println("Asset Tracking System Started...!");
		SpringApplication.run(AssetTrackingSystemApplication.class, args);
	}
	
}