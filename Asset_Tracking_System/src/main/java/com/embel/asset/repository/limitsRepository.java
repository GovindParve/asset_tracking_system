package com.embel.asset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.entity.limits;

@Repository
public interface limitsRepository extends  JpaRepository<limits, Long>{

	@Query(value="SELECT * FROM limits l WHERE l.admin_id=?1",nativeQuery=true)
	limits getlimitsbyid(Long fkadminId);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM limits WHERE admin_id=?1",nativeQuery=true)
	void deletebyfkadminid(Long fkadminId);

	
	
	@Query(value="SELECT * FROM limits l WHERE l.admin_id=?1",nativeQuery=true)
	Optional<limits> findByAdminId(long parseLong);

	@Query(value="SELECT l.days_limits_aged_and_recent_tag FROM limits l WHERE l.admin_id=?1",nativeQuery=true)
	String getdayslimit(long adminid);


	@Query(value="SELECT l.hrs_limits_nonworking_gateway FROM limits l WHERE l.admin_id=?1",nativeQuery=true)
	long gethoursLimitforGateway(Long fkID);

	
	@Query(value="SELECT l.hrs_limits_nonworking_tag FROM limits l WHERE l.admin_id=?1",nativeQuery=true)
	String gethourslimitforgetWorkingAndNonWorkingTag(String fkUserId);


	

}
