package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.embel.asset.bean.CategoryBean;
import com.embel.asset.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	//Get All Device details for Super Admin
		@Query(value = "SELECT new com.embel.asset.bean.CategoryBean(pkCatId,categoryName) FROM Category ORDER BY pkCatId asc")
		List<CategoryBean> getCategoryListForSuperAdmin();	
		
		//Get Device count for Super Admin
		@Query(value="SELECT count(pkCatId) FROM Category")
		String getCountCategoryForSuperAdmin();

		
		//Get Device for Edit
		@Query(value = "SELECT new com.embel.asset.bean.CategoryBean(pkCatId,categoryName) FROM Category where pkCatId=?1")
		List<CategoryBean> getCategoryForEdit(Long id);

		
		@Query(value = "SELECT c.asset_tracker_name FROM asset_tracking_technology_category c WHERE c.asset_tracker_name=?1",nativeQuery=true)
		String getDbCategoryOnCategoryName(String categoryName);

		
		@Query(value = "SELECT c.asset_tracker_name FROM asset_tracking_technology_category c",nativeQuery=true)
		List<String> getcategoryfroSuperAdmin();

		//List<String> getcategoryfroAdmin();

		
}
