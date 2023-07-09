package com.embel.asset.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.embel.asset.entity.EmployeeUser;
import com.embel.asset.repository.EmployeUserRepository;

//@Service
//public class EmployeeDetails implements UserDetailsService {
//		@Autowired
//	  private EmployeUserRepository employeeUserRepo;
//	
//	  @Override
//	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
//	  {	  
//	    final EmployeeUser user = employeeUserRepo.findByUsername(username);  
//	    
//	    if (user == null) 
//	    {
//	      throw new UsernameNotFoundException("User '" + username + "' not found");
//	    }
//	    return org.springframework.security.core.userdetails.User
//	        .withUsername(username)
//	        .password(user.getPassword())
//	        
//	        
//	        
//	        .authorities(new ArrayList<>())
//	        .accountExpired(false)
//	        .accountLocked(false)
//	        .credentialsExpired(false)
//	        .disabled(false)
//	        .build();
//	  }  
//	//  
//
//}
