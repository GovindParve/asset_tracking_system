package com.embel.asset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.entity.AssetTestjson;
import com.embel.asset.repository.AsetTestjsonRepos;

@Service
public class AssetTestJsonService {
	@Autowired
	AsetTestjsonRepos assetTegrepo;

	public AssetTestjson addjson(AssetTestjson assetTestjson) {
		return assetTegrepo.save(assetTestjson);

	}

}
