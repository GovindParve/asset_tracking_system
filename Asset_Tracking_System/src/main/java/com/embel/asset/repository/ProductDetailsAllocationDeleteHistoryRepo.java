package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.embel.asset.entity.ProductDetailsAllocationDeleteHistory;
import com.embel.asset.entity.ProductDetailsAllocationHistory;

@Repository
public interface ProductDetailsAllocationDeleteHistoryRepo extends JpaRepository<ProductDetailsAllocationDeleteHistory, Long>{

	@Query(value="SELECT * FROM product_tag_allocation_delete_history ph WHERE ph.allocated_tag=?1",nativeQuery=true)
	List<ProductDetailsAllocationDeleteHistory> getdeletedHistoyforSuperAdmin(String assetTagName);
	
	@Query(value="SELECT * FROM product_tag_allocation_delete_history ph WHERE ph.user_id=?1",nativeQuery=true)
	List<ProductDetailsAllocationDeleteHistory> getdeletedHistoyforOrganization(long userid);
	
	@Query(value="SELECT * FROM product_tag_allocation_delete_history ph WHERE ph.user_id=?1",nativeQuery=true)
	List<ProductDetailsAllocationDeleteHistory> getdeletedHistoyforAdmin(long userid);

	@Query(value="SELECT * FROM product_tag_allocation_delete_history ph WHERE ph.user_id=?1",nativeQuery=true)
	List<ProductDetailsAllocationDeleteHistory> getdeletedHistoyforUser(long userid);



	

}
