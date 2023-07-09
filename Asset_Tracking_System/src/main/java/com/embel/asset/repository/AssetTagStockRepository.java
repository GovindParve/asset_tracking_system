package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.entity.AssetTagStock;

@Repository
public interface AssetTagStockRepository extends JpaRepository<AssetTagStock, Long>
{

	@Query(value="SELECT * FROM asset_tag_stock a WHERE a.asset_barcode_number_or_serial_number=?1",nativeQuery = true)
	AssetTagStock findByAssetBarcodeSerialNumber(String barCode);

	@Query(value="SELECT * FROM asset_tag_stock a WHERE a.asset_unique_code_or_mac_id=?1",nativeQuery = true)
	AssetTagStock findByAssetUniqueCodeMacId(String valueOf);

	
	@Query(value="SELECT * FROM asset_tag_stock a WHERE a.organization_id=?1 AND a.asset_tag_category=?2",nativeQuery = true)
	Page<AssetTagStock> getFromSuperAdminToview(String fkUserId, String category, Pageable pageable);

	@Query(value="SELECT * FROM asset_tag_stock a WHERE a.organization_id=?1 AND a.asset_tag_category=?2 AND a.status='Non-Allocated'",nativeQuery = true)
	List<AssetTagStock> AssetTagFromSuperAdminForOrganizationExportDownload(Long fkID, String category);

	@Modifying
	@Transactional(rollbackFor=Exception.class)
	@Query(value="UPDATE asset_tag_stock SET status='Allocated' WHERE asset_unique_code_or_mac_id=?1",nativeQuery = true)
	void updateStockStatus(String macid);



	

	
	
	
	
	
}
