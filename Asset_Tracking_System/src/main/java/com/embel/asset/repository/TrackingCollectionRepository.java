package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.embel.asset.bean.GatewayWiseTagCountBean;
import com.embel.asset.entity.TagGatewayCollection;

@Repository
public interface TrackingCollectionRepository extends JpaRepository<TagGatewayCollection, Long>{

	TagGatewayCollection findByUniqueNumberMacId(String assetTagMac_Id);

	//-------------------Get gateway wise tag count for Super-admin-------------------//GROUP BY g.gateway_name
	//@Query(value="SELECT g.gateway_name,COUNT(t.asset_tag_name),g.gateway_location FROM asset_tag_and_gateway_tracking_collection t  RIGHT OUTER JOIN asset_gateway_creation g ON t.asset_gateway_name = g.gateway_name GROUP BY g.gateway_name", nativeQuery = true)
	@Query(value="SELECT g.gateway_name,COUNT(DISTINCT(t.asset_tag_name)),g.gateway_location FROM asset_tag_and_gateway_tracking_collection t RIGHT OUTER JOIN asset_gateway_creation g ON t.asset_gateway_name = g.gateway_name GROUP BY g.gateway_name", nativeQuery = true)

	List<Object[]> getCountForSuperAdmin();

	//-------------------Get gateway wise tag count for Super-admin-------------------
	//@Query(value="SELECT g.gateway_name,COUNT(t.asset_tag_name),g.gateway_location FROM asset_tag_and_gateway_tracking_collection t  RIGHT OUTER JOIN asset_gateway_creation g ON t.asset_gateway_name = g.gateway_name WHERE g.admin_id = ?1 GROUP BY g.gateway_name", nativeQuery = true)

	@Query(value="SELECT g.gateway_name,COUNT(DISTINCT(t.asset_tag_name)),g.gateway_location FROM asset_tag_and_gateway_tracking_collection t  RIGHT OUTER JOIN asset_gateway_creation g ON t.asset_gateway_name = g.gateway_name WHERE g.admin_id = ?1 GROUP BY g.gateway_name", nativeQuery = true)
	List<Object[]> getCountForAdmin(Long id);

	//-------------------Get gateway wise tag count for Super-admin-------------------
	//@Query(value="SELECT g.gateway_name,COUNT(t.asset_tag_name),g.gateway_location FROM asset_tag_and_gateway_tracking_collection t  RIGHT OUTER JOIN asset_gateway_creation g ON t.asset_gateway_name = g.gateway_name WHERE g.user_id = ?1 GROUP BY g.gateway_name", nativeQuery = true)
	@Query(value="SELECT g.gateway_name,COUNT(DISTINCT(t.asset_tag_name)),g.gateway_location FROM asset_tag_and_gateway_tracking_collection t  RIGHT OUTER JOIN asset_gateway_creation g ON t.asset_gateway_name = g.gateway_name WHERE g.user_id = ?1 GROUP BY g.gateway_name", nativeQuery = true)
	List<Object[]> getCountForUser(Long id);

	//----------gateway name list for dropdown---------------
	@Query(value="SELECT gateway_name FROM asset_gateway_creation ORDER BY gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForSuperAdmin();

	@Query(value = "SELECT gateway_name FROM asset_gateway_creation WHERE admin_id = ?1 ORDER BY gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForAdmin(Long fkID);

	@Query(value = "SELECT gateway_name FROM asset_gateway_creation WHERE user_id = ?1 ORDER BY gateway_name ASC", nativeQuery = true)
	List<String> getGatewayListForUser(Long fkID);

	@Query(value = "SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection t JOIN asset_gateway_creation g ON t.asset_gateway_mac_id = g.gateway_unique_code_or_mac_id WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)<=30 AND asset_gateway_name = ?1 AND g.admin_id = ?2" , nativeQuery = true)
	Integer getAgedTagCountByGateway(String gatewayName, Long id);

	@Query(value = "SELECT COUNT(asset_tag_name) FROM asset_tag_and_gateway_tracking_collection t JOIN asset_gateway_creation g ON t.asset_gateway_mac_id = g.gateway_unique_code_or_mac_id WHERE DATEDIFF(CURRENT_DATE(),first_scanning_date)<=30 AND asset_gateway_name = ?1 AND g.user_id = ?2" , nativeQuery = true)
	Integer getNewTagCountByGateway(String gatewayName, Long id);

	//..............................................................................
	//@Query(value="SELECT g.gateway_name,COUNT(t.asset_tag_name),g.gateway_location FROM asset_tracking_details t RIGHT OUTER JOIN asset_gateway_creation g ON t.asset_gateway_name = g.gateway_name  AND t.time between ?1 AND ?2 GROUP BY g.gateway_name", nativeQuery = true)
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
