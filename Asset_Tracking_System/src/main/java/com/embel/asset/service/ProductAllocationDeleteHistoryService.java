package com.embel.asset.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.entity.ProductDetailsAllocationDeleteHistory;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.ProductDetailsAllocationDeleteHistoryRepo;

@Service
public class ProductAllocationDeleteHistoryService {
	
	@Autowired
	ProductDetailsAllocationDeleteHistoryRepo ProductDetailsAllocationDeleteHistoryRepo;
	@Autowired
	EmployeUserRepository employeeUserRepo;
	
	/**
	 * usee to get Delete History
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param assetTagName
	 * @return ProductDetailsAllocationDeleteHistory
	 */
	public List<ProductDetailsAllocationDeleteHistory> getDeleteHistory(String fkUserId, String role,
			String assetTagName) {

		long userid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return ProductDetailsAllocationDeleteHistoryRepo.getdeletedHistoyforSuperAdmin(assetTagName);

		}
		if (role.equals(CommonConstants.organization)) {
			return ProductDetailsAllocationDeleteHistoryRepo.getdeletedHistoyforOrganization(userid);
		}
		if (role.equals(CommonConstants.admin)) {
			return ProductDetailsAllocationDeleteHistoryRepo.getdeletedHistoyforAdmin(userid);
		}
		if (role.equals(CommonConstants.user)) {
			return ProductDetailsAllocationDeleteHistoryRepo.getdeletedHistoyforUser(userid);

		}
		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			return ProductDetailsAllocationDeleteHistoryRepo.getdeletedHistoyforAdmin(adminId);
		}
		return null;
	}

	

	

	
}
