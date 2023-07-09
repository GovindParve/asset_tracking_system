package com.embel.asset.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.dto.RequestSubCategoryDto;
import com.embel.asset.entity.SubCategory;
import com.embel.asset.repository.SubCategoeryRepository;

@Service
public class SubCategoryService {
	@Autowired
	private SubCategoeryRepository SubcategoryRepository;

	/**
	 * use to get subcategory list
	 * 
	 * @author pratik chaudhari
	 * @return SubCategory list
	 */
	public List<SubCategory> getsubcategory() {
		return SubcategoryRepository.findAll();
	}

	/**
	 * use to Add SubCategory
	 * 
	 * @author pratik chaudhari
	 * @param subCategoryDto
	 */
	public void AddSubCategory(RequestSubCategoryDto subCategoryDto) {
		SubCategory subcatobj = new SubCategory();

		subcatobj.setPk_subcatid(subCategoryDto.getPk_subcatid());
		subcatobj.setSubCategoryname(subCategoryDto.getSubCategoryname());

		SubcategoryRepository.save(subcatobj);

	}

	/**
	 * use to get subcategory get by id
	 * 
	 * @param subcateid
	 * @return SubCategory
	 */
	public Optional<SubCategory> get(int subcateid) {

		return SubcategoryRepository.findById(subcateid);
	}

	/**
	 * use to get delete by id
	 * 
	 * @author Pratik chaudhari
	 * @param subcateid
	 */
	public void deletebyid(int subcateid) {

		SubCategory subcate = SubcategoryRepository.getById(subcateid);
		SubcategoryRepository.delete(subcate);
	}

	/**
	 * use to update subcategory
	 * 
	 * @author pratik chaudhari
	 * @param subCategoryDto
	 */
	public void updatesubcategory(RequestSubCategoryDto subCategoryDto) {
		SubCategory subcatobj = SubcategoryRepository.getOne(subCategoryDto.getPk_subcatid());

		subcatobj.setPk_subcatid(subCategoryDto.getPk_subcatid());
		subcatobj.setSubCategoryname(subCategoryDto.getSubCategoryname());

		SubcategoryRepository.save(subcatobj);
	}
}
