package com.embel.asset.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.embel.asset.dto.RequestIssueDto;
import com.embel.asset.entity.Issue;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.bean.ResponseIssueBean;
import com.embel.asset.repository.IssueRepository;

@Service
public class IssueService {

	@Autowired
	private IssueRepository IssueRepository;

	/**
	 * get all issue list
	 * 
	 * @param fkUserId
	 * @author Pratik chaudhari
	 * @param role
	 * @return ResponseIssueBean
	 */
	public List<ResponseIssueBean> GetAllIssue(String fkUserId, String role) {

		long userid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return IssueRepository.getAllIssueForSuperAdmin();
		}
		if (role.equals(CommonConstants.admin)) {
			return IssueRepository.getAllIssueForAdmin(userid);
		}
		if (role.equals(CommonConstants.user)) {
			return IssueRepository.getAllIssueForUser(userid);
		}

		return null;
	}

	/**
	 * use to add issue
	 * 
	 * @param issuedto
	 * @author pratik chaudhari
	 */
	public void addissue(RequestIssueDto issuedto) {
		Issue issue = new Issue();
		issue.setIssue(issuedto.getIssue());
		issue.setContact(issuedto.getContact());
		issue.setFkUserId(issuedto.getFkUserId());
		issue.setMailId(issuedto.getMailId());
		issue.setIssueid(issuedto.getIssueid());
		issue.setCategory(issuedto.getCategory().toUpperCase());
		issue.setUserName(issuedto.getUserName());
		IssueRepository.save(issue);
	}

	/**
	 * get by id
	 * 
	 * @param id
	 * @return Issue
	 */
	public Optional<Issue> get(long id) {
		if (id == 0) {
			id = 1;
		}
		return IssueRepository.findById(id);
	}

	/**
	 * use to delete by id
	 * 
	 * @author Pratik chaudhari
	 * @param id
	 */
	public void deletebyid(long id) {
		Issue entity = IssueRepository.getOne(id);
		IssueRepository.delete(entity);
	}

	/**
	 * use to Update Issue
	 * 
	 * @author Pratik chaudhari
	 * @param issuedto
	 * @return Issue
	 */
	public Issue UpdatesIssue(RequestIssueDto issuedto) {
		Issue issue = new Issue();
		issue.setIssue(issuedto.getIssue());
		issue.setContact(issuedto.getContact());
		issue.setFkUserId(issuedto.getFkUserId());
		issue.setMailId(issuedto.getMailId());
		issue.setIssueid(issuedto.getIssueid());
		issue.setCategory(issuedto.getCategory().toUpperCase());
		issue.setUserName(issuedto.getUserName());
		return IssueRepository.save(issue);
	}

	/**
	 * 
	 * use to Get All Issue List
	 * 
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @param category
	 * @return Issue
	 */
	public Page<Issue> GetAllIssueList(String fkUserId, String role, Pageable pageable, String category) {

		long userid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return IssueRepository.getAllIssueListForSuperAdmin(category, pageable);
		}
		if (role.equals(CommonConstants.organization)) {

			return IssueRepository.getAllIssueListForOrganization(category, userid, pageable);

		}
		if (role.equals(CommonConstants.admin)) {

			return IssueRepository.getAllIssueListForAdmin(category, userid, pageable);

		}
		if (role.equals(CommonConstants.user)) {
			return IssueRepository.getAllIssueListForUser(category, userid, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {

			return IssueRepository.getAllIssueListForEmpUser(category, userid, pageable);

		}
		return null;
	}

	/**
	 * change status of issues
	 * 
	 * @param status
	 * @param issueid
	 * @param fkUserId
	 * @param role
	 * @return String
	 */
	public String changestatusofissues(String status, String issueid, String fkUserId, String role) {
		if (role.equals(CommonConstants.superAdmin)) {
			IssueRepository.getissuebyissueid(status, issueid);

			return "ok";
		}
		if (role.equals(CommonConstants.admin)) {
			IssueRepository.getissuebyissueid(status, issueid);
			return "ok";
		}
		return null;
	}
}
