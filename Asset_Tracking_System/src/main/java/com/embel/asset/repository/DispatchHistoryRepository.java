package com.embel.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.embel.asset.entity.DispatchedProductHistory;

@Repository
public interface DispatchHistoryRepository extends JpaRepository<DispatchedProductHistory, Long> {

	int findByUniqueNumberMacId(String assetTagMac_Id);

}
