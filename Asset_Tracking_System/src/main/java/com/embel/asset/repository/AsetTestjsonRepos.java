package com.embel.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.embel.asset.entity.AssetGateway;
import com.embel.asset.entity.AssetTestjson;

@Repository
public interface AsetTestjsonRepos extends JpaRepository<AssetTestjson, Integer>
{

}
