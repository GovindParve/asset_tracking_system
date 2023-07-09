package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.embel.asset.bean.ResponseLoginHistoryBean;
import com.embel.asset.entity.UserLoginHistory;

@Repository
public interface LoginHistoryRepository extends JpaRepository<UserLoginHistory, Long>{

	@Query(value = "SELECT NEW com.embel.asset.bean.ResponseLoginHistoryBean(historyId, loginIP, loginDateTime, userName, userRole, userId) FROM UserLoginHistory ORDER BY historyId asc")
	List<ResponseLoginHistoryBean> getLoginListForSuperAdmin();

	@Query(value = "SELECT NEW com.embel.asset.bean.ResponseLoginHistoryBean(historyId, loginIP, loginDateTime, userName, userRole, userId) FROM UserLoginHistory WHERE userId = ?1 AND userRole = 'admin' ORDER BY historyId asc")
	List<ResponseLoginHistoryBean> getLoginListForAdmin(Long id);

	@Query(value = "SELECT NEW com.embel.asset.bean.ResponseLoginHistoryBean(historyId, loginIP, loginDateTime, userName, userRole, userId) FROM UserLoginHistory WHERE userId = ?1 AND userRole = 'user' ORDER BY historyId asc")
	List<ResponseLoginHistoryBean> getLoginListForUser(Long id);

}
