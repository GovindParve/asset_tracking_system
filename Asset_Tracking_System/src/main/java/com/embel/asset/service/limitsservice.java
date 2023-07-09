package com.embel.asset.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.entity.limits;
import com.embel.asset.repository.limitsRepository;
@Service
public class limitsservice 
{
	@Autowired
	limitsRepository limitsRepository;

	/**
	 * use to add limits
	 * 
	 * @param limits
	 * @return limits
	 */
	public limits addlimits(limits limits) {

		return limitsRepository.save(limits);
	}

	/**
	 * 
	 * @return limits list of limits objects
	 */
	public List<limits> getlimitsList() {

		return limitsRepository.findAll();

	}

	/**
	 * use to update limits
	 * 
	 * @author Pratik chaudhari
	 * @param limits
	 */
	public void updatelimits(limits limits) {

		System.out.println("limits:" + limits);
		limits limitogj = limitsRepository.getlimitsbyid(limits.getFkadminId());
		System.out.println("limits:" + limitogj);
		limits.setLimitid(limitogj.getLimitid());
		limitsRepository.save(limits);
	}

	/**
	 * use to delete by id
	 * 
	 * @param fkadminId
	 */
	public void deletebyid(Long fkadminId) {
		limitsRepository.deletebyfkadminid(fkadminId);

	}

	/**
	 * get limits By id
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @return limits
	 */
	public Optional<limits> getlimitsByid(String fkUserId) {

		return limitsRepository.findByAdminId(Long.parseLong(fkUserId));
	}

	/**
	 * get days limits
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @return String
	 */
	public String getdayslimits(String fkUserId) {

		return limitsRepository.getdayslimit(Long.parseLong(fkUserId));
	}

	/**
	 * get hours Limit for Gateway
	 * 
	 * @author Pratik chaudhari
	 * @param fkID
	 * @return long
	 */
	public long gethoursLimitforGateway(Long fkID) {
		return limitsRepository.gethoursLimitforGateway(fkID);
	}

	/**
	 * get Working And Non-Working Tag
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @return String
	 */
	public String getWorkingAndNonWorkingTag(String fkUserId) {

		return limitsRepository.gethourslimitforgetWorkingAndNonWorkingTag(fkUserId);
	}

}
