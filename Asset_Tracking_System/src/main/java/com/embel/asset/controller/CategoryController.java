package com.embel.asset.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.embel.asset.bean.CategoryBean;
import com.embel.asset.dto.CategoryDto;
import com.embel.asset.entity.Category;
import com.embel.asset.service.CategoryService;

@CrossOrigin("*")
@RestController
public class CategoryController implements ErrorController{

	@Autowired
	CategoryService categoryService;
	
	/**
	 * use to add Category
	 * @author Pratik chaudhari
	 * @param category
	 * @return String
	 */
	@PostMapping("/category/add")
	public String addCategory(@RequestBody CategoryDto category) {
		System.out.println(category);
		return categoryService.saveCategory(category);
	}
	
	//Get All Category List
	/**
	 * Get All Category List
	 * @author Pratik chaudhari 
	 * @param fkUserId
	 * @param role
	 * @return CategoryBean
	 */
		@GetMapping("/category/get-category_list")
		public List<CategoryBean> getCategoryList(String fkUserId, String role)
		{			
			return  categoryService.getCategoryList(fkUserId,role);		
		}
		
		//
		
		/**
		 * Get Category Count 
		 * @author Pratik chaudhari
		 * @param fkUserId
		 * @param role
		 * @return String
		 */
		@GetMapping("/category/get-Category_count")
		public String getCountCategory(String fkUserId, String role)
		{		
			return  categoryService.getCountCategories(fkUserId,role);		
		}
		
		//Get Category for Edit
		/**
		 * get Category For Edit
		 * @param id
		 * @return CategoryBean
		 */
		@GetMapping("/category/get-category-for-edit/{catid}")
		public List<CategoryBean> getCategoryForEdit(@PathVariable(value="catid") Long id)
		{
			return categoryService.getCategoryForEdit(id);
		}
		
		//update Category
		/**
		 * use to update Category
		 * @param cat use to get category object 
		 * @return String
		 */
		@PutMapping("/category/update-Category")
		public String updateCategory(@RequestBody Category cat)
		{
			categoryService.updateCategory(cat);
			return "Category Updated Successfully.... ";
		}	
		
		//Multiple Delete Category Details from Database
		/**
		 * Multiple Delete Category Details from Database
		 * @author Pratik chaudhari
		 * @param PkID
		 * @return String
		 */
		@DeleteMapping("/category/delete-Category/{catid}")
		public String deleteCategory(@PathVariable(value="catid") Long PkID)
		{
			categoryService.deleteCategory(PkID);
			return "Category Delete Successfully...";
		}
}
