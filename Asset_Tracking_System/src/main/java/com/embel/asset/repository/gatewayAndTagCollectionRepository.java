package com.embel.asset.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.entity.AssetTrackingEntity;
import com.embel.asset.entity.gatewayAndTagCollection;

public interface gatewayAndTagCollectionRepository extends  JpaRepository<gatewayAndTagCollection, Long>{


	 @Modifying
	 @Transactional
	@Query(value="DELETE  FROM gateway_and_tag_collection WHERE asset_tag_name=?1",nativeQuery=true)
	void deleteByTagName(String tagName);

	 
	 

	 @Query(value="SELECT g.gateway_name,COUNT(t.asset_tag_name),g.gateway_location FROM gateway_and_tag_collection t,asset_gateway_creation g WHERE t.asset_gateway_name = g.gateway_name  GROUP BY g.gateway_name", nativeQuery = true)
	List<Object[]> getcurrenttaginGatewayCountForSuperAdmin();//String totime,

	@Query(value="SELECT g.gateway_name,COUNT(t.asset_tag_name),g.gateway_location FROM gateway_and_tag_collection t,asset_gateway_creation g WHERE t.asset_gateway_name = g.gateway_name  AND g.admin_id=?1 GROUP BY g.gateway_name", nativeQuery = true)
	List<Object[]> getcurrenttaginGatewayCountForAdmin(Long id);//String date,
	
	
	@Query(value="SELECT g.gateway_name,COUNT(t.asset_tag_name),g.gateway_location FROM gateway_and_tag_collection t,asset_gateway_creation g WHERE t.asset_gateway_name = g.gateway_name  AND g.user_id=?1 GROUP BY g.gateway_name", nativeQuery = true)
	List<Object[]> getcurrenttaginGatewayCountForUser(Long id);//String date,



	 
	@Query(value="SELECT COUNT(a.asset_tag_name) FROM gateway_and_tag_collection a WHERE a.entry_time <?1 AND a.asset_gateway_name=?2", nativeQuery = true)
	Integer getAgedTagCountByGateway(String mdate3, String gateway);



	@Query(value="SELECT COUNT(a.asset_tag_name) FROM gateway_and_tag_collection a WHERE a.entry_time BETWEEN ?1 AND ?2 AND a.asset_gateway_name=?3", nativeQuery = true)
	Integer getNewTagCountByGateway(String mdate3, String date2, String gateway);



	@Query(value="SELECT * FROM gateway_and_tag_collection g WHERE g.unique_code_or_mac_id=?1", nativeQuery = true)
	gatewayAndTagCollection getdbdata(String tagmacid);



	@Query(value="SELECT g.gateway_name,COUNT(t.asset_tag_name),g.gateway_location FROM gateway_and_tag_collection t,asset_gateway_creation g WHERE t.asset_gateway_name = g.gateway_name  AND g.organization_id=?1 GROUP BY g.gateway_name", nativeQuery = true)
	List<Object[]> getcurrenttaginGatewayCountForOrganization(Long id);


	

}
