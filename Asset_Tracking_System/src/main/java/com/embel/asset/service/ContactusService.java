package com.embel.asset.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.embel.asset.bean.ResponseContactusBean;
import com.embel.asset.dto.RequestContactusDto;
import com.embel.asset.entity.Contactus;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.repository.ContactusRepository;

@Service
public class ContactusService
{

	@Autowired
	private ContactusRepository contactRepository;
	
	/**
	 * use to add contactus objects
	 * @author Pratik chaudhari
	 * @param contactdto
	 */
	public void addcontact(RequestContactusDto contactdto) 
	{
		Contactus contact = new Contactus();
		
		contact.setContactid(contactdto.getContactid());
		contact.setContactname(contactdto.getContactname());
		contact.setContactnumber(contactdto.getContactnumber());
		contact.setDescription(contactdto.getDescription());
		contact.setEmail(contactdto.getEmail());
		contact.setCategory(contactdto.getCategory().toUpperCase());
		contact.setFkUserId(contactdto.getFkUserId());
	
		 contactRepository.save(contact);
		
	}


	/**
	 * Get All Contact
	 * @param fkUserId
	 * @param role
	 *  @author Pratik chaudhari
	 * @return ResponseContactusBean
	 */
	public List<ResponseContactusBean> GetAllContact(String fkUserId, String role)
	{
		long userid=Long.parseLong(fkUserId);
		if(role.equals(CommonConstants.superAdmin)) 
		{
			return contactRepository.getAllContactusForSuperAdmin();
		}
		if(role.equals(CommonConstants.admin)) 
		{
			
			return contactRepository.getAllContactusForAdmin(userid);
		}
		if(role.equals(CommonConstants.user))
		{
			
			return contactRepository.getAllContactusForUser(userid);
		}
		
		return null;
		
		//return contactRepository.findAll();
	}


	/**
	 * use get  Contactus object
	 * @param l
	 * @author Pratik chaudhari
	 * @return Contactus
	 */
	public Optional<Contactus> get(long l) {
		
		Optional<Contactus> con= contactRepository.findById(l) ;
		System.out.println(con);
		return con;
	}


	/**
	 * use to delete by id
	 * @author Pratik chaudhari
	 * @param id
	 * @return Contactus
	 */
	public Contactus deletebyid(long id) {
		
		Contactus entity =contactRepository.getOne(id);
		 contactRepository.delete(entity);
		 return entity;
		
	}


	/**
	 * updates contacts object
	 * @author Pratik Chaudhari
	 * @param contactdto
	 */
	public void updatescontacts(RequestContactusDto contactdto) 
	{
		Contactus contact = new Contactus();
		
		contact.setContactid(contactdto.getContactid());
		contact.setContactname(contactdto.getContactname());
		contact.setContactnumber(contactdto.getContactnumber());
		contact.setDescription(contactdto.getDescription());
		contact.setEmail(contactdto.getEmail());
		contact.setCategory(contactdto.getCategory().toUpperCase());
		contact.setFkUserId(contactdto.getFkUserId());
		 contactRepository.save(contact);
	}


/**
 * Get All Contact List
 * @param fkUserId
 * @param role
 * @param pageable
 * @param category
 * @return Contactus
 */
	public Page<Contactus> GetAllContactList(String fkUserId, String role, Pageable pageable, String category) 
	{
		long userid=Long.parseLong(fkUserId);
		if(role.equals(CommonConstants.superAdmin)) 
		{
			return contactRepository.getAllContactusListForSuperAdmin(category,pageable);
		}
		
		if(role.equals(CommonConstants.organization)) 
		{
			
			return contactRepository.getAllContactusListForOrganization(category,userid,pageable);
		}
		if(role.equals(CommonConstants.admin)) 
		{
			
			return contactRepository.getAllContactusListForAdmin(category,userid,pageable);
		}
		if(role.equals(CommonConstants.user))
		{
			
			return contactRepository.getAllContactusListForUser(category,userid,pageable);
		}
		if(role.equals(CommonConstants.empUser))
		{
			
			return contactRepository.getAllContactusListForEmpUser(category,userid,pageable);
		}
		
		return null;
		
	}



	
	

}
