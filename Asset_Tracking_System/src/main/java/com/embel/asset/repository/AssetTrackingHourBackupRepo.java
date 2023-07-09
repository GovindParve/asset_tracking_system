package com.embel.asset.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.embel.asset.entity.AssetTrackingEntity;
import com.embel.asset.entity.AssetTrackingEntityHoursBackup;
@Repository
public interface AssetTrackingHourBackupRepo extends JpaRepository<AssetTrackingEntityHoursBackup, Long> 
{

	@Query(value = "SELECT * FROM asset_tracking_details d WHERE d.date between ?1 AND ?2",nativeQuery=true)
	List<AssetTrackingEntity> getHourBackupAssetTrackingDetails(LocalDateTime formdate, LocalDateTime currentdate);
	
	@Query(value ="DELETE FROM asset_tracking_details_hours_backup",nativeQuery=true)
	void deletetable();
	

}
