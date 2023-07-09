package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.embel.asset.entity.ProductDetailsAllocationHistory;
@Repository
public interface ProductAllocationHistoryRepository extends JpaRepository<ProductDetailsAllocationHistory, Long> {

	@Query(value ="SELECT * FROM product_tag_allocation_history ph WHERE ph.allocated_tag=?1", nativeQuery = true)
	List<ProductDetailsAllocationHistory> getProductAllocationHistoryForSuperAdmin(String productName);

	@Query(value ="SELECT * FROM product_tag_allocation_history ph WHERE ph.allocated_tag=?1 AND ph.user_id=?2", nativeQuery = true)
	List<ProductDetailsAllocationHistory> getProductAllocationHistoryForOrganization(String assetTagName, Long userId);
	@Query(value ="SELECT * FROM product_tag_allocation_history ph WHERE ph.allocated_tag=?1 AND ph.user_id=?2", nativeQuery = true)
	List<ProductDetailsAllocationHistory> getProductAllocationHistoryForAdmin(String assetTagName, Long userId);

	

	

}
