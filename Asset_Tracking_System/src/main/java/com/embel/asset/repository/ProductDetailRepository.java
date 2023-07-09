package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.bean.ProductDetailsBean;
import com.embel.asset.bean.ResponseProductBean;
import com.embel.asset.entity.ProductDetails;
@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetails, Long>{

	@Query("SELECT DISTINCT new com.embel.asset.bean.ResponseProductBean(productId,productName) FROM ProductDetails ORDER BY productId asc")
	List<ResponseProductBean> getAllproductListForDrodownSuperadmin();
	
	@Query("SELECT DISTINCT new com.embel.asset.bean.ResponseProductBean(productId,productName) FROM ProductDetails WHERE user_id = ?1 ORDER BY productId")
	List<ResponseProductBean> getAllproductListForDrodownAdmin(Long userId);

	//Get All product List On product name and user Wise
	@Query(value = "SELECT new com.embel.asset.bean.ProductDetailsBean(p.productId,p.productName,p.description,p.createdby,p.updatedby,p.fkUserId,p.admin,p.subproductName) FROM ProductDetails p WHERE p.category=?1  ORDER BY productId desc")
	Page<ProductDetailsBean> getAllProductListOnUserwiseSuperadmin(String category,Pageable pageable);

	//Get All product List On product name and user Wise
	@Query(value = "SELECT new com.embel.asset.bean.ProductDetailsBean(p.productId,p.productName,p.description,p.createdby,p.updatedby,p.fkUserId,p.admin,p.subproductName) FROM ProductDetails p,UserDetail u WHERE p.fkUserId=u.pkuserId AND p.fkUserId = ?1 AND p.category=?2 ORDER BY p.productId desc")
	Page<ProductDetailsBean> getAllProductListOnUserwiseAdmin(Long userId,String category,Pageable pageable);
	
//	//@Query(value = "SELECT new com.embel.asset.bean.ProductDetailsBean(p.productId,p.productName,p.description,p.user,p.fkUserId,p.subproductName) FROM ProductDetails p,UserDetail u WHERE p.fkUserId=u.pkuserId AND p.fkUserId = ?1 AND p.category=?2 ORDER BY p.productId desc")
//	Page<ProductDetailsBean> getAllProductListOnUserwiseOrganization(Long userId,String category,Pageable pageable);
//	
	
	@Query(value = "SELECT new com.embel.asset.bean.ProductDetailsBean(p.productId,p.productName,p.description,p.createdby,p.updatedby,p.fkUserId,p.admin,p.subproductName) FROM ProductDetails p,UserDetail u WHERE p.fkUserId=u.pkuserId AND p.fkUserId = ?1 AND  p.category=?2 ORDER BY p.productId desc")
	Page<ProductDetailsBean> getAllProductListOnUserwiseUser(Long userId,String category,Pageable pageable);
	
	@Modifying
	@Query(value = "UPDATE ProductDetails g set g.createdby = ?1 where g.fkUserId = ?2")
	@Transactional(rollbackFor=Exception.class)
	void updateUserDetails(String userName,Long userId);

	
	@Query(value ="SELECT * FROM product_details p WHERE p.category=?1",nativeQuery = true)
	List<ProductDetails> getAllProductListOnUserwiseSuperadminx(String category);
	
	@Query(value ="SELECT * FROM product_details p,user_details u WHERE p.user_id=u.pkuser_id AND p.user_id=?1 AND p.category=?2",nativeQuery = true)
	List<ProductDetails> getAllProductListOnUserwiseOrganizationx(Long userId, String category);

	@Query(value ="SELECT * FROM product_details p,user_details u WHERE p.user_id=u.pkuser_id AND p.user_id=?1 AND p.category=?2",nativeQuery = true)
	List<ProductDetails> getAllProductListOnUserwiseAdminx(Long userId, String category);
	
	@Query(value ="SELECT * FROM product_details p,user_details u WHERE p.user_id=u.pkuser_id AND p.user_id=?1 AND p.category=?2",nativeQuery = true)
	List<ProductDetails> getAllProductListOnUserwiseUserx(Long userId, String category);

	
	@Query(value ="SELECT p.sub_product_name FROM product_details p WHERE p.product_name=?1 AND p.category=?2",nativeQuery = true)
	List<String> getAllProductListProductNameWiseSuperadmin(String productName, String category);

	
	@Query(value ="SELECT p.sub_product_name FROM product_details p WHERE p.product_name=?1 AND p.category=?2",nativeQuery = true)
	List<String> getAllProductListProductNameWiseOrganization(String productName, Long userId, String category);

	@Query(value ="SELECT p.sub_product_name FROM product_details p WHERE p.product_name=?1 AND p.category=?2",nativeQuery = true)
	List<String> getAllProductListProductNameWiseAdmin(String productName, Long userId, String category);

	@Query(value ="SELECT p.sub_product_name FROM product_details p WHERE p.product_name=?1 AND p.category=?2",nativeQuery = true)
	List<String> getAllProductListProductNameWiseiseUser(String productName, Long userId, String category);

	
	@Query(value ="SELECT * FROM product_details p WHERE p.user_id=?1 AND p.category=?2",nativeQuery = true)
	List<ProductDetails> getAllProducrListForOrganization(String adminid, String category);

	@Query(value ="SELECT * FROM product_details p WHERE p.user_id=?1 AND p.category=?2",nativeQuery = true)
	List<ProductDetails> getuserListunderAdmin(String admin, String category);

	
	
	
	@Modifying
	@Transactional
	@Query(value ="delete FROM product_details  WHERE product_id=?1",nativeQuery = true)
	void deleteSelectedTag(Long long1);

	
//	@Query(value ="",nativeQuery = true)
//	List<String> getAllProductListProductNameWiseOrganization(String productName, Long long1, String category);

	

	



	


	
}
