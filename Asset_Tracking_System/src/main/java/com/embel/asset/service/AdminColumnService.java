package com.embel.asset.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.embel.asset.entity.AdminColumns;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.repository.AdminColumnRepository;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.UserRepository;
import com.embel.asset.repository.columnmappingRepository;

@Service
public class AdminColumnService {
	@Autowired
	AdminColumnRepository AdminColumnRepo;

	@Autowired
	EmployeUserRepository employeeUserRepo;
	@Autowired
	columnmappingRepository columnmappingRepo;
	@Autowired
	UserRepository userRepo;

	/**
	 * get List Admin Column for status
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId  
	 * @param role
	 * @param category 
	 * @return AdminColumns
	 */
	
	
	public List<AdminColumns> getListAdminColumnforstatus(String fkUserId, String role, String category) {
		long fkid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return AdminColumnRepo.getallListForSuperadminforstatus(category);// pageable
		}
		if (role.equals(CommonConstants.organization)) {
			String organizationUserName = userRepo.getOrganizationName(fkUserId);
			List<String> adminList = userRepo.getAdminListOnOrganization(organizationUserName);
			List<AdminColumns> beanList = new ArrayList<AdminColumns>();

			List<AdminColumns> OrganizationcolumnList = AdminColumnRepo.getAllOrganizationList(category, fkid);// ,pageable

			for (int k = 0; k < OrganizationcolumnList.size(); k++) {
				beanList.add(OrganizationcolumnList.get(k));
			}

			for (int i = 0; i < adminList.size(); i++) {
				List<AdminColumns> adminAdmincolumnList = null;
				try {
					adminAdmincolumnList = AdminColumnRepo.getAllAdminsListForOrganizationAdmin(category,
							adminList.get(i));
				} catch (Exception e) {
				}
				if (adminAdmincolumnList == null) {
				} else {
					for (int j = 0; j < adminAdmincolumnList.size(); j++) {
						beanList.add(adminAdmincolumnList.get(j));
					}
				}

			}

			return beanList;
//				final int start = (int)pageable.getOffset();
//				final int end = Math.min((start + pageable.getPageSize()), beanList.size());
//				final Page<AdminColumns> page = new PageImpl<>(beanList.subList(start, end), pageable, beanList.size());
//				//final Page<UserInfobean> page = new PageImpl<>(list);
//				
//				return page;

		}
		if (role.equals(CommonConstants.admin)) {

			return AdminColumnRepo.getAllAdminsListforstatus(category, fkid);// ,pageable
		}

		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return AdminColumnRepo.getAllAdminsListforstatus(category, adminid);// ,pageable
		}
		if (role.equals(CommonConstants.user)) {
			return AdminColumnRepo.getAllUserListforstatus(category, fkid);//
		}

		return null;
	}

	/**
	 * get List AdminColumn
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return AdminColumns
	 */
	public Page<AdminColumns> getListAdminColumn(String fkUserId, String role, String category, Pageable pageable) {
		long fkid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return AdminColumnRepo.getallListForSuperadmin(category, pageable);// pageable
		}
		if (role.equals(CommonConstants.organization)) {
			String organizationUserName = userRepo.getOrganizationName(fkUserId);
			List<String> adminList = userRepo.getAdminListOnOrganization(organizationUserName);
			List<AdminColumns> beanList = new ArrayList<AdminColumns>();
			List<AdminColumns> OrganizationcolumnList = null;
			try {
				OrganizationcolumnList = AdminColumnRepo.getAllOrganizationList(category, fkid);
			} catch (Exception e) {
			}

			if (OrganizationcolumnList.size() == 0) {

			} else {
				for (int k = 0; k < OrganizationcolumnList.size(); k++) {
					System.out.println("" + OrganizationcolumnList.get(k));
					beanList.add(OrganizationcolumnList.get(k));
				}

			}

			for (int i = 0; i < adminList.size(); i++) {
				List<AdminColumns> adminAdmincolumnList = null;
				try {
					adminAdmincolumnList = AdminColumnRepo.getAllAdminsListForOrganizationAdmin(category,
							adminList.get(i));
				} catch (Exception e) {
				}
				if (adminAdmincolumnList == null) {
				} else {
					for (int j = 0; j < adminAdmincolumnList.size(); j++) {
						beanList.add(adminAdmincolumnList.get(j));
						// System.out.println(""+OrganizationcolumnList.get(j));
					}
				}

			}

			final int start = (int) pageable.getOffset();
			final int end = Math.min((start + pageable.getPageSize()), beanList.size());
			final Page<AdminColumns> page = new PageImpl<>(beanList.subList(start, end), pageable, beanList.size());
			// final Page<UserInfobean> page = new PageImpl<>(list);

			return page;

		}
		if (role.equals(CommonConstants.admin)) {

			return AdminColumnRepo.getAllAdminsList(category, fkid, pageable);// ,pageable
		}

		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return AdminColumnRepo.getAllAdminsList(category, adminid, pageable);// ,pageable
		}
		if (role.equals(CommonConstants.user)) {
			return AdminColumnRepo.getAllUserList(category, fkid, pageable);//
		}

		return null;
	}

	/**
	 * get admincolumns By id
	 * 
	 * @author Pratik chaudhari
	 * @param id
	 * @return AdminColumns
	 */
	public Optional<AdminColumns> getadmincolumnsByid(long id) {

		Optional<AdminColumns> aobj = AdminColumnRepo.findById(id);
		System.out.println("aobj" + aobj);
		return aobj;
	}

	/**
	 * By colunmn Name
	 * 
	 * @author Pratik Chaudhri
	 * @param adminName
	 * @param columnName
	 * @return String
	 */
	public String BycolunmnName(String adminName, String columnName) {

		return AdminColumnRepo.getadminName(adminName, columnName);
	}

	/**
	 * get Organization By colunmnName
	 * 
	 * @author Pratik chaudhari
	 * @param organization
	 * @param columns
	 * @return String
	 */
	public String getOrganizationBycolunmnName(String organization, String columns) {

		return AdminColumnRepo.getOrgnizationName(organization, columns);
	}

	/**
	 * use to add admin columns object
	 * 
	 * @author Pratik Chaudhari
	 * @param adminColumns
	 */
	public void add(AdminColumns adminColumns) {

		AdminColumns alist = new AdminColumns();
		alist.setAdminName(adminColumns.getAdminName());
		alist.setCategory(adminColumns.getCategory().toUpperCase());
		alist.setColumns(adminColumns.getColumns());
		alist.setFkAdminId(adminColumns.getFkAdminId());
		alist.setPkadmincolumnsId(adminColumns.getPkadmincolumnsId());
		alist.setUnit(adminColumns.getUnit());
		alist.setFkOrganizationId(adminColumns.getFkOrganizationId());
		alist.setOrganization(adminColumns.getOrganization());
		alist.setStatus(adminColumns.getStatus());
		AdminColumnRepo.save(alist);
	}

	/**
	 * use to update AdminColumn
	 * 
	 * @param adminColumns
	 * @return AdminColumns
	 */
	public AdminColumns updateAdminColumn(AdminColumns adminColumns) {
		AdminColumns alist = new AdminColumns();
		alist.setAdminName(adminColumns.getAdminName());
		alist.setCategory(adminColumns.getCategory().toUpperCase());
		alist.setColumns(adminColumns.getColumns());
		alist.setFkAdminId(adminColumns.getFkAdminId());
		alist.setPkadmincolumnsId(adminColumns.getPkadmincolumnsId());
		alist.setUnit(adminColumns.getUnit());
		alist.setFkOrganizationId(adminColumns.getFkOrganizationId());
		alist.setOrganization(adminColumns.getOrganization());
		return AdminColumnRepo.save(alist);
	}

	/**
	 * get List Drop Down List
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	public List<String> getListDropDownList(String fkUserId, String role, String category) {

		if (role.equals(CommonConstants.superAdmin)) {
			return columnmappingRepo.getUicolumnListforSuperAdmin(category);
		}
		if (role.equals(CommonConstants.organization)) {
			return columnmappingRepo.getUicolumnListforSuperAdmin(category);
		}
		if (role.equals(CommonConstants.admin)) {
			return columnmappingRepo.getUicolumnListforSuperAdmin(category);
		}
		if (role.equals(CommonConstants.user)) {
			return columnmappingRepo.getUicolumnListforSuperAdmin(category);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return AdminColumnRepo.getUicolumnListforAdmin(adminid);
		}
		return null;
	}

	/**
	 * get List Drop Down List New
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	public List<String> getListDropDownListNew(String fkUserId, String role, String category) {
		long fkid = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return columnmappingRepo.getUicolumnListforSuperAdmin(category);
		}
		if (role.equals(CommonConstants.organization)) {
			return AdminColumnRepo.getUicolumnForOrganization(fkid, category);
		}
		if (role.equals(CommonConstants.admin)) {
			return AdminColumnRepo.getUicolusmnListforAdmin(fkid, category);
		}
		if (role.equals(CommonConstants.user)) {
			String AdminName = userRepo.getadminName(fkUserId);
			long pkidAdmin = userRepo.getpkidOnUserName(AdminName);
			return AdminColumnRepo.getUicolusmnListforAdmin(pkidAdmin, category);
		}
		return null;
	}

	/**
	 * change status Admin Parameter
	 * 
	 * @param status
	 * @param pkadminparameterid
	 * @return String
	 */
	public String changestatusAdminParameter(String status, long pkadminparameterid) {

		if (status.equals("Active")) {
			AdminColumnRepo.updateStatusOfAdminParameterActivate(pkadminparameterid);
		} else {
			AdminColumnRepo.updateStatusOfAdminParameterDeActivate(pkadminparameterid);
		}

		return "Parameter is: " + status + " Now";
	}

	/**
	 * get admin Ui Name
	 * 
	 * @param columns
	 * @param adminName
	 * @return String
	 */
	public String getadminUiName(String columns, String adminName) {

		return AdminColumnRepo.getcolumn(columns, adminName);
	}

	/**
	 * use to get AdminColumn
	 * 
	 * @return AdminColumns
	 */
	public List<AdminColumns> getAdminColumn() {

		return AdminColumnRepo.getAdminColumn();
	}

	/**
	 * get admin Ui Name Of Organization
	 * 
	 * @param columns
	 * @param organization
	 * @return String
	 */
	public String getadminUiNameOfOrganization(String columns, String organization) {

		return AdminColumnRepo.getuicolumnofOraganization(columns, organization);
	}

}
