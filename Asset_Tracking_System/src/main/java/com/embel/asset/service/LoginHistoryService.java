package com.embel.asset.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.bean.ResponseLoginHistoryBean;
import com.embel.asset.entity.UserLoginHistory;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.repository.LoginHistoryRepository;

@Service
public class LoginHistoryService {

	@Autowired
	private LoginHistoryRepository historyRepo;

	/**
	 * use to save Login History
	 * 
	 * @author Pratik chaudhari
	 * @param loginHistory
	 * 
	 */
	public void saveLoginHistory(UserLoginHistory loginHistory) {

		historyRepo.save(loginHistory);
	}

	/**
	 * get LoginHistory list
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ResponseLoginHistoryBean
	 */
	public List<ResponseLoginHistoryBean> getLoginHistory(String fkUserId, String role) {
		if (role.equals(CommonConstants.superAdmin)) {
			return historyRepo.getLoginListForSuperAdmin();
		}
		if (role.equals(CommonConstants.admin)) {
			Long id = Long.parseLong(fkUserId);
			return historyRepo.getLoginListForAdmin(id);
		}
		if (role.equals(CommonConstants.user)) {
			Long id = Long.parseLong(fkUserId);
			return historyRepo.getLoginListForUser(id);
		}
		return null;
	}
}
