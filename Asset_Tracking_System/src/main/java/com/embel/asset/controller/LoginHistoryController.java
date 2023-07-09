package com.embel.asset.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.embel.asset.bean.ResponseLoginHistoryBean;
import com.embel.asset.service.LoginHistoryService;

@CrossOrigin("*")
@RestController
public class LoginHistoryController implements ErrorController{

	@Autowired
	private LoginHistoryService historyService;
	
	//-----------------------------------------
	/**
	 * Get user list as per role
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ResponseLoginHistoryBean
	 */
			@GetMapping("/login/user-login-history")
			public List<ResponseLoginHistoryBean> getUserLoginHistory(String fkUserId, String role)
			{		
				return  historyService.getLoginHistory(fkUserId,role);		
			}
}
