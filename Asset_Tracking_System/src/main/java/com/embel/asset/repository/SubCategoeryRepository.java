package com.embel.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.embel.asset.entity.SubCategory;
@Repository
public interface SubCategoeryRepository extends JpaRepository<SubCategory, Integer>
{
	
	
	

}
