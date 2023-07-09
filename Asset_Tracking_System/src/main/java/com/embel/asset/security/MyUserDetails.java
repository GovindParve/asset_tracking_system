package com.embel.asset.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.embel.asset.entity.EmployeeUser;
import com.embel.asset.entity.UserDetail;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.UserRepository;

@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private EmployeUserRepository employeeRepo;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
	  String role=null;
	  try {
		  role= employeeRepo.getrole(username);
		  if(role==null)
		  {
			  role="N/A";
		  }
	  }catch (Exception e) {
		System.out.println(e);
	}
	  
	  
	 if(role.equals("empuser"))
	 {
		 final EmployeeUser user = employeeRepo.findByUsername(username); 
		    if (user == null) {
		        throw new UsernameNotFoundException("User '" + username + "' not found");
		      }
		      return org.springframework.security.core.userdetails.User
		          .withUsername(username)
		          .password(user.getPassword())
		          
		          
		          
		          .authorities(new ArrayList<>())
		          .accountExpired(false)
		          .accountLocked(false)
		          .credentialsExpired(false)
		          .disabled(false)
		          .build();
		      
	 }else
	 {
		    final UserDetail user = userRepository.findByUsername(username);   
		    if (user == null) {
		        throw new UsernameNotFoundException("User '" + username + "' not found");
		      }
		      return org.springframework.security.core.userdetails.User
		          .withUsername(username)
		          .password(user.getPassword())
		          
		          
		          
		          .authorities(new ArrayList<>())
		          .accountExpired(false)
		          .accountLocked(false)
		          .credentialsExpired(false)
		          .disabled(false)
		          .build();
		    }     
	 }
 


}
