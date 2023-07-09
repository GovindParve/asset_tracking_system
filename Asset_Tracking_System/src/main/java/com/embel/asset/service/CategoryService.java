package com.embel.asset.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.bean.CategoryBean;
import com.embel.asset.dto.CategoryDto;
import com.embel.asset.entity.Category;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepo;

	/**
	 * get All Categories
	 * 
	 * @author Pratik chaudhari
	 * @return Category
	 */
	public List<Category> getAllCategories() {
		List<Category> categoryList = categoryRepo.findAll();
		return categoryList;
	}

	/**
	 * use to save Category
	 * 
	 * @author Pratik chaudhari
	 * @param category
	 * @return String
	 */
	public String saveCategory(CategoryDto category) {
		Category cat = new Category();
		cat.setCategoryName(category.getCategoryName().toUpperCase());
		if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
			return "Null Value Not Accepted...!";
		}
		String dbcategory = null;
		try {
			dbcategory = categoryRepo.getDbCategoryOnCategoryName(category.getCategoryName().toUpperCase());
			if (dbcategory == null) {
				dbcategory = "N/A";
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		if (dbcategory.equals(cat.getCategoryName())) {
			return "Category Alrady Present..";
		} else {
			System.out.println(category);

			System.out.println(cat);
			categoryRepo.save(cat);
			return " Category Added Successfully...!";
		}

	}

	/**
	 * get Category For Edit
	 * 
	 * @param id
	 * @return CategoryBean
	 */
	public List<CategoryBean> getCategoryForEdit(Long id) {
		return categoryRepo.getCategoryForEdit(id);
	}

	/**
	 * use to update category
	 * 
	 * @author Pratik chaudhari
	 * @param category
	 */
	public void updateCategory(Category category) {
		Category cat = categoryRepo.getOne(category.getPkCatId());
		cat.setCategoryName(category.getCategoryName());
		categoryRepo.save(cat);
	}

	/**
	 * get Category List
	 * 
	 * @param fkUserId
	 * @param role
	 * @return CategoryBean
	 */
	public List<CategoryBean> getCategoryList(String fkUserId, String role) {
		if (role.equals(CommonConstants.superAdmin)) {
			return categoryRepo.getCategoryListForSuperAdmin();
		}
		if (role.equals(CommonConstants.admin)) {
			return categoryRepo.getCategoryListForSuperAdmin();
		}
		if (role.equals(CommonConstants.organization)) {
			return categoryRepo.getCategoryListForSuperAdmin();
		}
		if (role.equals(CommonConstants.empUser)) {
			return categoryRepo.getCategoryListForSuperAdmin();
		}
		return null;
	}

	/**
	 * get Category List
	 * 
	 * @author Pratik Chaudhari
	 * @param fkUserId
	 * @param role
	 * @return String
	 */
	public String getCountCategories(String fkUserId, String role) {
		if (role.equals(CommonConstants.superAdmin)) {
			return categoryRepo.getCountCategoryForSuperAdmin();
		}
		return null;
	}

	/**
	 * use to delete Category
	 * 
	 * @author Pratik chaudhari
	 * @param pkID
	 */
	public void deleteCategory(Long pkID) {
		categoryRepo.deleteById(pkID);

	}
}
