package com.embel.asset.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.embel.asset.entity.IndoorImages;

@Repository
public interface IndoorImageRepository extends JpaRepository<IndoorImages, Long>
{

	@Query(value="SELECT * FROM indoor_image i",nativeQuery=true)
	List<IndoorImages> getAllList();
	
	@Query(value="SELECT * FROM indoor_image i where i.user_id=?1",nativeQuery=true)
	Optional<IndoorImages> getImageAdminId(long id);


	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM indoor_image WHERE user_id=?1",nativeQuery=true)
	void deletebyId(long fkadminId);
	

}
