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
import org.springframework.web.bind.annotation.RestController;

import com.embel.asset.dto.RequestContactusDto;
import com.embel.asset.entity.Contactus;
import com.embel.asset.service.ContactusService;

@CrossOrigin("*")
@RestController
public class ContactusController implements ErrorController {
	@Autowired
	private ContactusService contactservice;
	// Get all List
//	@GetMapping("/contact/getall")
//	public List<Contactus> getall()
//	{
//		
//		return contactservice.GetAllContact();
//	}

//	   //GetAllcontactRoleWise
//		@GetMapping("/contact/getall")
//		public List<ResponseContactusBean> getall(String fkUserId, String role)
//		{
//			
//			return contactservice.GetAllContact(fkUserId,role);
//		}
	
	/**
	 * use to get All Contacts
	 * @author Pratik chaudhari
	 * @param pageNo
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return Contactus
	 */
	@GetMapping("/contact/getall/{pageNo}")
	public Page<Contactus> getAllContacts(@PathVariable String pageNo, String fkUserId, String role, String category) {
		Pageable pageable = PageRequest.of(Integer.parseInt(pageNo), 10);
		return contactservice.GetAllContactList(fkUserId, role, pageable, category);
	}

	// add contact
	/**
	 * use to add contact
	 * @param contactdto
	 * @return String
	 */
	@PostMapping("/contact/post")
	public String contactus(@RequestBody RequestContactusDto contactdto) {
		contactservice.addcontact(contactdto);
		return "your responce is Recorded thanks to contact us ";
	}

	/**
	 * use to get contactus object bu id 
	 * @author Pratik chaudhari 
	 * @param id
	 * @return Contactus this is the object of Contactus class
	 */
	@GetMapping("/contact/{id}")
	public Optional<Contactus> getbyid(@PathVariable String id) {
		System.out.println("@@@@@@@@@" + id);
		return contactservice.get(Long.parseLong(id));

	}

	
	/**
	 * use to delete the contact us object 
	 * @param id
	 * @return HttpStatus
	 */
	@DeleteMapping("/contact/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable String id) {

		try {
			contactservice.deletebyid(Long.parseLong(id));

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * use to update contact us object 
	 * @author Pratik chaudhari
	 * @param contactdto
	 * @return String
	 */
	@PutMapping("/contact/update")
	public String update(@RequestBody RequestContactusDto contactdto) {
		contactservice.updatescontacts(contactdto);
		return "Updated contact us information...";
	}

	public String getErrorPath() {
		return null;
	}

}
