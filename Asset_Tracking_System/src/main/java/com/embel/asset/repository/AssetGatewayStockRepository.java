package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.entity.AssetGatewayStock;
import com.embel.asset.entity.AssetTagStock;

@Repository
public interface AssetGatewayStockRepository extends JpaRepository<AssetGatewayStock, Long>{

	
	@Query(value="SELECT * FROM asset_gateway_stock a WHERE a.gateway_barcode_number_or_serial_number=?1",nativeQuery = true)
	List<AssetGatewayStock> findByGatewayBarcodeSerialNumber(String barCode);

	@Query(value="SELECT * FROM asset_gateway_stock a WHERE a.gateway_unique_code_or_mac_id=?1",nativeQuery = true)
	List<AssetGatewayStock> findByGatewayUniqueCodeMacId(String uniqueCode);

	
	@Query(value="SELECT * FROM asset_gateway_stock  WHERE organization_id=?1 ANd asset_tag_category=?2",nativeQuery=true)
	Page<AssetGatewayStock> getFromSuperAdminToview(String fkUserId, String category, Pageable pageable);

	
	@Query(value="SELECT * FROM asset_gateway_stock a WHERE a.organization_id=?1 AND a.asset_tag_category=?2 AND a.status='Non-Allocated'",nativeQuery=true)
	List<AssetGatewayStock> AssetTagFromSuperAdminForOrganizationExportDownload(Long fkID, String category);

	@Modifying
	@Transactional(rollbackFor=Exception.class)
	@Query(value="UPDATE asset_gateway_stock SET status='Allocated' WHERE gateway_unique_code_or_mac_id=?1",nativeQuery=true)
	void updatestatus(String macid);

	

}
