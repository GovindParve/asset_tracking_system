package com.embel.asset.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
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

import com.embel.asset.dto.RequestSubCategoryDto;
import com.embel.asset.entity.SubCategory;
import com.embel.asset.service.SubCategoryService;

@CrossOrigin("*")
@RestController
public class SubCategoryController implements ErrorController {
	@Autowired
	private SubCategoryService subcategory;
	
	
//////////////////////////////////////////////////////////////
//
//Function name :   Getall()
//Parameters 	:   None 
//Return value  :   List
//Description   :   It is used to get list of  subcategory 
//Author 		:   Pratik  chaudhari
//Date          :   27 dec 2021
//
/////////////////////////////////////////////////////////////
	//Get all List 
	/**
	 * use to get all sub category  List 
	 * @author Pratik chaudhari
	 * @return SubCategory
	 */
		@GetMapping("/subcategory/getallSubCategory")
		public List<SubCategory> Getall()
		{
			
			return subcategory.getsubcategory();
		}
		
		// add contact
		
		/**
		 * use to add  contact
		 * @param SubCategoryDto
		 * @return String
		 */
		@PostMapping("/subcategory/post")
		public String  Add(@RequestBody RequestSubCategoryDto SubCategoryDto)
		{
			subcategory.AddSubCategory(SubCategoryDto); 
			return "Subcategory added.... ";
		}
		
		
		/**
		 * use to get by id 
		 * @param id
		 * @return SubCategory
		 */
		@GetMapping("/subcategory/{id}")
		public Optional<SubCategory> getbyid(@PathVariable String id)
		{
			return subcategory.get(Integer.parseInt(id));
			
		}

		/**
		 * use to get delete by id 
		 * @author HP pratik chaudhari
		 * @param id
		 * @return HttpStatus
		 */
		@DeleteMapping("/subcategory/{id}")
		public ResponseEntity<HttpStatus> delete(@PathVariable String id)
		{
			
			try {
				subcategory.deletebyid(Integer.parseInt(id));

				  return new ResponseEntity<>(HttpStatus.OK);
				}catch(Exception e){
				return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}		
				
		}
		/**
		 * use to update subcategory 
		 * @author Pratik chaudhari
		 * @param SubCategoryDto
		 * @return String 
		 */
		@PutMapping("/subcategory/update")
		public String update(@RequestBody RequestSubCategoryDto SubCategoryDto)
		{
			subcategory.updatesubcategory(SubCategoryDto);
			return "Updated Subcategory information...";
		}

}
