package com.embel.asset.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import  com.embel.asset.dto.RequestIssueDto;
import com.embel.asset.entity.Issue;
import com.embel.asset.service.IssueService;
@CrossOrigin("*")
@RestController
@RequestMapping()
public class IssueController implements ErrorController
{
	@Autowired
	private IssueService issueservice;
	
	
	
//		
//		@GetMapping("/Issue/getAllIssueList")
//		public List<ResponseIssueBean>GetAllissueRoleWise(String fkUserId, String role )
//		{
//			return issueservice.getAllIssuelistToView(fkUserId,role);;
//		}
	
	
	
	/**
	 * get All Issue List
	 * @author Pratik chaudhari
	 * @param pageNo
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return
	 */
	@GetMapping("/issue/getallList/{pageNo}")
	public Page<Issue>getAllIssueList(@PathVariable String pageNo,String fkUserId, String role,String category)
	{
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo), 10);
		
		System.out.println("inside issue");
		
		return issueservice.GetAllIssueList(fkUserId,role,pageable,category);
		
		
	}
	
	
	
	
	//.............................................
	/**
	 * use to change Status
	 * @param status
	 * @param issueid
	 * @param fkUserId
	 * @param role
	 * @return String
	 */
		@PostMapping("/issue/update-status")
		public String changeStatus(String status,String issueid,String fkUserId, String role)
		{
			return issueservice.changestatusofissues(status,issueid,fkUserId,role);
			
		}
	
	//...........
//	.............................
	
	//GetAllissueRoleWise
//	@GetMapping("/issue/getallList")
//	public List<ResponseIssueBean> getall(String fkUserId, String role)
//	{
//		return issueservice.GetAllIssue(fkUserId,role);
//	}
	
	// add issue
		/**
		 * use to add Issue
		 * @author Pratik chaudhari
		 * @param issuedto
		 * @return String
		 */
	@PostMapping("/issue/post")
	public String  Issue(@RequestBody RequestIssueDto issuedto)
	{
		issueservice.addissue(issuedto); 
		return "your responce is Recorded thanks to contact us ";
	}
	
	
	/**
	 * use to get Issue by id 
	 * @author Pratik chaudhari
	 * @param id
	 * @return Issue
	 */
	@GetMapping("/issue/{id}")
	public Optional<Issue> getbyid(@PathVariable String id)
	{
		return issueservice.get(Long.parseLong(id));
		
	}

	
	/**
	 * use to delete issue 
	 * @author Pratik chaudhari
	 * @param id
	 * @return HttpStatus
	 */
	@DeleteMapping("/issue/delete/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable String id)
	{
		
		try {
			issueservice.deletebyid(Long.parseLong(id));

			  return new ResponseEntity<>(HttpStatus.OK);
			}catch(Exception e){
			return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
			}		
			
	}
	
	/**
	 * use to update
	 * @param contactdto use to get the object 
	 * @return String 
	 */
	@PutMapping("/issue/update")
	public String update(@RequestBody RequestIssueDto contactdto)
	{
		issueservice.UpdatesIssue(contactdto);
		return " Issue Updated ..." ;
	}

	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
