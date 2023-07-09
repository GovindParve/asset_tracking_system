package com.embel.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.embel.asset.entity.AssetTagHistory;

@Repository
public interface AssetTagHistoryRepository extends JpaRepository<AssetTagHistory, Long>
{
	
	@Query(value="SELECT * FROM asset_tag_generation_history WHERE asset_tag_name=?1",nativeQuery=true)
	AssetTagHistory findByAssetTagName(String assetTagName);

}
