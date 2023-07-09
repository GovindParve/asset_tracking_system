package com.embel.asset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.entity.AssetTagHistory;
import com.embel.asset.repository.AssetTagHistoryRepository;

@Service
public class AssetTagHistoryService {

	@Autowired
	AssetTagHistoryRepository assetTagHistoryRepo;

	/**
	 * get Asset Tag By Name
	 * 
	 * @author Pratik chaudhari
	 * @param assetTagName
	 * @return AssetTagHistory
	 */
	public AssetTagHistory getAssetTagByName(String assetTagName) {

		return assetTagHistoryRepo.findByAssetTagName(assetTagName);
	}

}
