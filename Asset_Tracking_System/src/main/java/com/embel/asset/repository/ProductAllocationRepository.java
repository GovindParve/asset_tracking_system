package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.bean.ProductResponseAllocationBean;
import com.embel.asset.bean.ResponseProductBean;
import com.embel.asset.entity.ProductDetailForAllocation;
import com.embel.asset.entity.ProductDetailsAllocationHistory;

@Repository
public interface ProductAllocationRepository extends JpaRepository<ProductDetailForAllocation, Long>{

	//Multiple Delete product Details from Database
	@Modifying
	@Transactional
	@Query(value = "delete FROM ProductDetailForAllocation where productAllocationId=?1")
	void deleteSelectedproduct(Long long1);

	
	//Get All product List On product name and user Wise
	@Query(value = "SELECT new com.embel.asset.bean.ProductResponseAllocationBean(productAllocationId,productName,productId,allocatedTagName,allocatedTagId,assetUniqueCodeOrMacId,user,fkUserId,dispatchLocation,subproductName) FROM ProductDetailForAllocation WHERE productName = ?1 ORDER BY productAllocationId desc")
	List<ProductResponseAllocationBean> getAllProductTagListOnUserwiseSuperadmin(String productName);

	
	//Get All product List On product name and user Wise
	@Query(value = "SELECT new com.embel.asset.bean.ProductResponseAllocationBean(productAllocationId,productName,productId,allocatedTagName,allocatedTagId,assetUniqueCodeOrMacId,user,fkUserId,dispatchLocation,subproductName) FROM ProductDetailForAllocation WHERE productName = ?1 AND fkUserId = ?2 ORDER BY productAllocationId desc")
	List<ProductResponseAllocationBean> getAllProductTagListOnUserwiseAdmin(String productName, Long userId);

	
	@Query("SELECT new com.embel.asset.bean.ProductResponseAllocationBean(productAllocationId,productName,productId,allocatedTagName,allocatedTagId,assetUniqueCodeOrMacId,user,fkUserId,dispatchLocation,subproductName) FROM ProductDetailForAllocation ORDER BY productAllocationId asc")
	List<ProductResponseAllocationBean> getAllproductTagListSuperadmin();
	
	
	@Query("SELECT new com.embel.asset.bean.ProductResponseAllocationBean(productAllocationId,productName,productId,allocatedTagName,allocatedTagId,assetUniqueCodeOrMacId,user,fkUserId,dispatchLocation,subproductName) FROM ProductDetailForAllocation WHERE fkUserId = ?1 ORDER BY productAllocationId asc")
	List<ProductResponseAllocationBean> getAllproductTagListAdmin(Long userId);

	@Query(value ="SELECT * FROM product_tag_allocation p,asset_tag_generation a WHERE p.allocated_tag=a.asset_tag_name AND p.category=?1 ORDER BY product_allocation_id",nativeQuery =true)
	List<ProductDetailForAllocation> getAllproductListForDrodownSuperadmin(String category);

	@Query(value ="SELECT * FROM product_tag_allocation p,asset_tag_generation a WHERE p.allocated_tag=a.asset_tag_name AND p.user_id=?1 AND p.category=?2 ORDER BY product_allocation_id",nativeQuery =true)
	List<ProductDetailForAllocation> getAllproductListForDrodownAdmin(String userId, String category);
	
	@Query(value ="SELECT * FROM product_tag_allocation p,asset_tag_generation a WHERE p.allocated_tag=a.asset_tag_name  AND p.user_id=?1 AND p.category=?2 ORDER BY product_allocation_id",nativeQuery =true)
	List<ProductDetailForAllocation> getAllproductListForDrodownOrganization(String admin, String category);
	
	@Query(value ="SELECT * FROM product_tag_allocation p,asset_tag_generation a WHERE p.allocated_tag=a.asset_tag_name  AND p.user_id=?1 AND p.category=?2 ORDER BY product_allocation_id",nativeQuery =true)
	List<ProductDetailForAllocation> getAllproductListForDrodownUser(Long userId, String category);

	
	
	///////////////////////////////////////////////////////////////////
	@Modifying
	@Query(value = "UPDATE ProductDetailForAllocation g set g.user = ?1 where g.fkUserId = ?2")
	@Transactional(rollbackFor=Exception.class)
	void updateUserDetails(String userName,Long userId);


	@Query(value = "SELECT dispatch_location FROM product_tag_allocation WHERE allocated_tag_unique_code = ?1" , nativeQuery = true)
	String getDisplatchLocation(String assetTagMac_Id);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM ProductDetailForAllocation WHERE allocatedTagName = ?1 AND assetUniqueCodeOrMacId = ?2")
	void deleteAllocatedProductByTag(String tagName, String macID);

	@Query(value = "SELECT product_id,product_name FROM product_tag_allocation WHERE allocated_tag = ?1 AND allocated_tag_unique_code = ?2" , nativeQuery = true)
	ProductDetailForAllocation getProductByTag(String tagName, String assetTagMac_Id);


	

//....................................pagination...........................................
	

 	@Query("SELECT new com.embel.asset.bean.ResponseProductBean(p.productId,p.productName) FROM ProductDetailForAllocation p  ORDER BY p.productAllocationId asc")
 
//	@Query(value ="SELECT p.product_allocation_id,p.product_name FROM product_tag_allocation p GROUP BY p.product_allocation_id ASC", nativeQuery = true)
	Page<ResponseProductBean> getAllproductListForDrodownSuperadminWithPagination(Pageable pageable);

	@Query("SELECT new com.embel.asset.bean.ResponseProductBean(a.productId,a.productName) FROM ProductDetailForAllocation a WHERE a.fkUserId = ?1 ORDER BY a.productAllocationId asc")
//	@Query(value ="SELECT p.product_allocation_id,p.product_name FROM product_tag_allocation p WHERE p.user_id=?1 GROUP BY p.product_allocation_id ASC", nativeQuery = true)
	Page<ResponseProductBean> getAllproductListForDrodownAdminWithPagination(Long userId,Pageable pageable);

	@Query(value ="SELECT COUNT(p.product_allocation_id) FROM product_tag_allocation p", nativeQuery = true)
	String getdbcount();

//......................................................................................................................
	//Get All product List On product name and user Wise

	
	@Query(value = "SELECT new com.embel.asset.bean.ProductResponseAllocationBean(p.productAllocationId,p.productName,p.productId,p.allocatedTagName,p.allocatedTagId,p.assetUniqueCodeOrMacId,p.user,p.fkUserId,p.dispatchLocation,p.subproductName) FROM ProductDetailForAllocation p WHERE productName = ?1 ORDER BY productAllocationId desc")
	Page<ProductResponseAllocationBean> getAllProductTagListOnUserwiseSuperadminWithPagination(String productName,
			Pageable pageable);

	@Query(value = "SELECT new com.embel.asset.bean.ProductResponseAllocationBean(p.productAllocationId,p.productName,p.productId,p.allocatedTagName,p.allocatedTagId,p.assetUniqueCodeOrMacId,p.user,p.fkUserId,p.dispatchLocation,p.subproductName) FROM ProductDetailForAllocation p WHERE productName = ?1 AND fkUserId = ?2 ORDER BY productAllocationId desc")
	Page<ProductResponseAllocationBean> getAllProductTagListOnUserwiseAdminWithPagination(String productName,
			Long userId, Pageable pageable);
//.......................................................................
	

	//@Query("SELECT DISTINCT new com.embel.asset.bean.ProductResponseAllocationBean(p.productAllocationId,p.productName,p.productId,p.allocatedTagName,p.allocatedTagId,p.assetUniqueCodeOrMacId,p.user,p.fkUserId,p.dispatchLocation,p.subproductName) FROM ProductDetailForAllocation p,AssetTag a WHERE a.assetTagCategory=?1 ORDER BY p.productAllocationId asc")
	@Query(value ="SELECT * FROM product_tag_allocation p WHERE  p.category=?1 GROUP BY product_allocation_id", nativeQuery = true)
	Page<ProductDetailForAllocation> getAllproductTagListSuperadminWithPagination(String category, Pageable pageable);

//SELECT new com.embel.asset.bean.ProductResponseAllocationBean(p.productAllocationId,p.productName,p.productId,p.allocatedTagName,p.allocatedTagId,p.assetUniqueCodeOrMacId,p.user,p.fkUserId,p.dispatchLocation,p.subproductName) FROM ProductDetailForAllocation p,AssetTag a WHERE p.fkUserId = a.fkUserId AND a.fkOrganizationId=?1 ORDER BY p.productAllocationId asc
	//@Query("SELECT DISTINCT new com.embel.asset.bean.ProductResponseAllocationBean(p.productAllocationId,p.productName,p.productId,p.allocatedTagName,p.allocatedTagId,p.assetUniqueCodeOrMacId,p.user,p.fkUserId,p.dispatchLocation,p.subproductName) FROM ProductDetailForAllocation p,AssetTag a WHERE p.fkUserId = a.fkAdminId AND a.assetTagCategory=?1 AND p.fkUserId=?2 ORDER BY p.productAllocationId asc")
	@Query(value ="SELECT * FROM product_tag_allocation p,asset_tag_generation a WHERE p.user_id=a.admin_id AND p.category=?1 AND p.user_id=?2 GROUP BY product_allocation_id", nativeQuery = true)
	List<ProductDetailForAllocation> getAllproductTagListOrganizationWithPagination(String category, String userId);
	
	
	//@Query("SELECT DISTINCT new com.embel.asset.bean.ProductResponseAllocationBean(p.productAllocationId,p.productName,p.productId,p.allocatedTagName,p.allocatedTagId,p.assetUniqueCodeOrMacId,p.user,p.fkUserId,p.dispatchLocation,p.subproductName) FROM ProductDetailForAllocation p,AssetTag a WHERE p.fkUserId = a.fkAdminId AND a.assetTagCategory=?1 AND p.fkUserId=?2 ORDER BY p.productAllocationId asc")
	@Query(value ="SELECT * FROM product_tag_allocation p,asset_tag_generation a WHERE p.user_id=a.admin_id AND p.category=?1 AND p.user_id=?2 GROUP BY product_allocation_id", nativeQuery = true)
	Page<ProductDetailForAllocation> getAllproductTagListAdminWithPagination(String category, Long userId, Pageable pageable);
	
	
//

	
	@Query(value ="SELECT p.dispatch_time FROM product_tag_allocation p WHERE p.allocated_tag=?1", nativeQuery = true)
	long getDispatchTime(String tagName);


	
	
	@Query(value ="SELECT * FROM product_tag_allocation p,asset_tag_generation a WHERE  p.allocated_tag=a.asset_tag_name AND p.category=?1 AND p.user_name=?2", nativeQuery = true)
	Page<ProductDetailForAllocation> getgetAllproductTaglistUserWithPagination(String category, String userName,
			Pageable pageable);


	





	
	
	
	
	

	
	
	
	
	
//...........................................................................................

}
