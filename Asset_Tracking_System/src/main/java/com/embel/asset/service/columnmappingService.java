package com.embel.asset.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.embel.asset.bean.ResponseColumnBean;
import com.embel.asset.bean.ResponseColumnMappingbean;
import com.embel.asset.bean.ResponseTrackingListBean;
import com.embel.asset.entity.AdminColumns;
import com.embel.asset.entity.AssetTrackingEntity;
import com.embel.asset.entity.columnmapping;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.helper.GenerateBillPDF;
import com.embel.asset.helper.TagReportForSuperAdminHelper;
import com.embel.asset.repository.AdminColumnRepository;
import com.embel.asset.repository.AssetTagRepository;
import com.embel.asset.repository.AssetTrackingRepository;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.columnmappingRepository;

@Service
public class columnmappingService {
	@Autowired
	columnmappingRepository columnmappingRepo;

	@Autowired
	AssetTrackingRepository trackingrepo;

	@Autowired
	AdminColumnRepository adminColoumnRepository;

	@Autowired
	AssetTagRepository assettagRepo;

	@Autowired
	EmployeUserRepository employeeUserRepo;

	/**
	 * get All data
	 * 
	 * @author Pratik chaudhari
	 * @return columnmapping
	 */
	public List<columnmapping> getAlldata() {

		return columnmappingRepo.findAll();
	}

	/**
	 * use to add columnmapping parameters
	 * 
	 * @param clist
	 * @author Pratik chaudhari
	 */
	public void add(columnmapping clist) {

		columnmapping dtoclist = new columnmapping();
		dtoclist.setAllocatedColumn_db(clist.getAllocatedColumn_db());
		dtoclist.setAllocatedColumn_ui(clist.getAllocatedColumn_ui());
		dtoclist.setCategory(clist.getCategory().toUpperCase());
		dtoclist.setColumnName(clist.getColumnName());
		dtoclist.setPkId(clist.getPkId());
		dtoclist.setUnit(clist.getUnit());

		columnmappingRepo.save(dtoclist);
	}

	/**
	 * get List
	 * 
	 * @param columnname
	 * @param category
	 * @return columnmapping
	 */
	public List<columnmapping> getList(String columnname, String category) {

		if (columnname != null) {

			return columnmappingRepo.getcolumn(columnname);
		} else {
			List<columnmapping> list = columnmappingRepo.getallList(category);
			System.out.println("list" + list);
			return list;

		}

	}

	/**
	 * use to Delete columnmapping
	 * 
	 * @author Pratik chaudhari
	 * @param id
	 */
	public void DeleteMapping(String id) {
		long fkid = Long.parseLong(id);
		columnmapping cobj = columnmappingRepo.getById(fkid);
		columnmappingRepo.delete(cobj);
	}

	/**
	 * get lablel of Column
	 * 
	 * @author Pratik chaudhari
	 * @param str
	 * @return String
	 */
	public String getlablelofColumn(String str) {

		columnmapping column1 = columnmappingRepo.getlabelbycolumn(str);
		String str2 = column1.getColumnName();
		return str2;
	}

	/**
	 * update Mapping
	 * 
	 * @param clist
	 * @author Pratik chaudhari
	 * @return columnmapping
	 */
	public columnmapping updateMapping(columnmapping clist) {
		columnmapping dtoclist = new columnmapping();
		dtoclist.setAllocatedColumn_db(clist.getAllocatedColumn_db());
		dtoclist.setAllocatedColumn_ui(clist.getAllocatedColumn_ui());
		dtoclist.setCategory(clist.getCategory().toUpperCase());
		dtoclist.setColumnName(clist.getColumnName());
		dtoclist.setPkId(clist.getPkId());
		dtoclist.setUnit(clist.getUnit());
		return columnmappingRepo.save(dtoclist);
	}

	/**
	 * get labels List
	 * 
	 * @author Pratik Chaudhari
	 * @return String
	 */
	public List<String> getlabelsList() {

		return columnmappingRepo.getlabelsList();

	}

	/**
	 * By colunmn Name
	 * 
	 * @param columnName
	 * @author Pratik Chaudhari
	 * @return String
	 */
	public String BycolunmnName(String columnName) {

		return columnmappingRepo.BycolunmnName(columnName);
	}

	/**
	 * get By id
	 * 
	 * @param id
	 * @return columnmapping
	 */
	public columnmapping getByid(String id) {

		return columnmappingRepo.getbyId(id);
	}

	/**
	 * get List cloumnwise
	 * 
	 * @param columnname
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @param category
	 * @return AssetTrackingEntity
	 */
	public Page<AssetTrackingEntity> getListcloumnwise(String columnname, String fkUserId, String role,
			Pageable pageable, String category) {
		Long fkid = Long.parseLong(fkUserId);
		if (category.equals("GPS")) {
			if (role.equals(CommonConstants.superAdmin)) {
				if (columnname != null) {
					return trackingrepo.gettrackinglisForGPSt(category, pageable);
				} else {
					return trackingrepo.gettrackinglisForGPSt(category, pageable);
				}

			}
			if (role.equals(CommonConstants.organization)) {
				if (columnname != null) {

					return trackingrepo.gettrackinglistForOrganizationForGPS(fkid, category, pageable);

				} else {
					return trackingrepo.gettrackinglistForOrganizationForGPS(fkid, category, pageable);
				}

			}
			if (role.equals(CommonConstants.admin)) {
				if (columnname != null) {

					// String columnParameter=columnmappingRepo.getparameterName(columnname);
					return trackingrepo.gettrackinglistForAdminForGps(fkid, category, pageable);
					// return trackingrepo.gettrackinglistcolumnwise(columnParameter,pageable);

				} else {
					return trackingrepo.gettrackinglistForAdminForGps(fkid, category, pageable);
				}

			}
			if (role.equals("empuser")) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				if (columnname != null) {

					// String columnParameter=columnmappingRepo.getparameterName(columnname);
					return trackingrepo.gettrackinglistForAdminForGps(adminid, category, pageable);
					// return trackingrepo.gettrackinglistcolumnwise(columnParameter,pageable);

				} else {
					return trackingrepo.gettrackinglistForAdminForGps(adminid, category, pageable);
				}

			}

		} else {
			if (role.equals(CommonConstants.superAdmin)) {
				if (columnname != null) {

					// String columnParameter=columnmappingRepo.getparameterName(columnname);
					return trackingrepo.gettrackinglist(pageable);
					// return trackingrepo.gettrackinglistcolumnwise(columnParameter,pageable);

				} else {
					return trackingrepo.gettrackinglist(pageable);
				}
				// return trackingrepo.gettrackinglist(pageable);

			}
			if (role.equals(CommonConstants.organization)) {
				if (columnname != null) {

					// String columnParameter=columnmappingRepo.getparameterName(columnname);
					return trackingrepo.gettrackinglistForOrganization(fkid, pageable);
					// return trackingrepo.gettrackinglistcolumnwise(columnParameter,pageable);

				} else {
					return trackingrepo.gettrackinglistForOrganization(fkid, pageable);
				}

			}
			if (role.equals(CommonConstants.admin)) {
				if (columnname != null) {

					// String columnParameter=columnmappingRepo.getparameterName(columnname);
					return trackingrepo.gettrackinglistForAdmin(fkid, pageable);
					// return trackingrepo.gettrackinglistcolumnwise(columnParameter,pageable);

				} else {
					return trackingrepo.gettrackinglistForAdmin(fkid, pageable);
				}

			}
			if (role.equals("empuser")) {
				long adminid = employeeUserRepo.getAdminId(fkUserId);
				if (columnname != null) {

					// String columnParameter=columnmappingRepo.getparameterName(columnname);
					return trackingrepo.gettrackinglistForAdmin(adminid, pageable);
					// return trackingrepo.gettrackinglistcolumnwise(columnParameter,pageable);

				} else {
					return trackingrepo.gettrackinglistForAdmin(adminid, pageable);
				}

			}
		}
		return null;
	}

	/**
	 * load All Tracking Data Dynamic Columnwise
	 * 
	 * @param columnname
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 * @throws ParseException
	 */
	public InputStream loadAllTrackingDataDynamicColumnwise(String columnname, String fkUserId, String role,
			String category) throws ParseException {
		String dbcolumnname = columnmappingRepo.getdbcolumn(columnname);

		if (category.equals("GPS")) {

			if (role.equals(CommonConstants.superAdmin)) {
				List<AssetTrackingEntity> payList = trackingrepo
						.getColumnWiseReportForSuperAdminExportDownloadForGPS(category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFDynamicColumnWise(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo
						.getColumnWiseReportForOrganizationExportDownloadForGPS(fkID, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFDynamicColumnWise(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForAdminExportDownloadForGPS(fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFDynamicColumnWise(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForUserExportDownloadForGPS(fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFDynamicColumnWise(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
		} else {

			if (role.equals(CommonConstants.superAdmin)) {
				List<AssetTrackingEntity> payList = trackingrepo
						.getColumnWiseReportForSuperAdminExportDownloadxl(category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFDynamicColumnWise(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForOrganizationExportDownload(fkID);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFDynamicColumnWise(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForAdminExportDownloadpdfxl(fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFDynamicColumnWise(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForUserExportDownloadpdf(fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = GenerateBillPDF.billPDFDynamicColumnWise(payList, columnname,
							dbcolumnname);
					return in;
				}
			}

		}
		return null;
	}

	/**
	 * load All Tracking Data Dynamic Columnwise
	 * 
	 * @param columnname
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return InputStream
	 */
	public InputStream loadAllTrackingDataDynamicColumnwiseXls(String columnname, String fkUserId, String role,
			String category) {
		String dbcolumnname = columnmappingRepo.getdbcolumn(columnname);
		if (category.equals("GPS")) {
			if (role.equals(CommonConstants.superAdmin)) {
				List<AssetTrackingEntity> payList = trackingrepo
						.getColumnWiseReportForSuperAdminExportDownloadForGPS(category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = ColumnWiseReportHelper.ColumnWiseExcelForSuperAdmin(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo
						.getColumnWiseReportForOrganizationExportDownloadForGPS(fkID, category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = ColumnWiseReportHelper.ColumnWiseExcelForSuperAdmin(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForAdminExportDownloadForGPS(fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = ColumnWiseReportHelper.ColumnWiseExcelForSuperAdmin(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForUserExportDownloadForGPS(fkID,
						category);
				if (payList.size() > 0) {
					ByteArrayInputStream in = ColumnWiseReportHelper.ColumnWiseExcelForSuperAdmin(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
		} else {
			if (role.equals(CommonConstants.superAdmin)) {
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForSuperAdminExportDownload();
				if (payList.size() > 0) {
					ByteArrayInputStream in = ColumnWiseReportHelper.ColumnWiseExcelForSuperAdmin(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.organization)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForOrganizationExportDownload(fkID);
				if (payList.size() > 0) {
					ByteArrayInputStream in = ColumnWiseReportHelper.ColumnWiseExcelForSuperAdmin(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.admin)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForAdminExportDownload(fkID);
				if (payList.size() > 0) {
					ByteArrayInputStream in = ColumnWiseReportHelper.ColumnWiseExcelForSuperAdmin(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
			if (role.equals(CommonConstants.user)) {
				Long fkID = Long.parseLong(fkUserId);
				List<AssetTrackingEntity> payList = trackingrepo.getColumnWiseReportForUserExportDownload(fkID);
				if (payList.size() > 0) {
					ByteArrayInputStream in = ColumnWiseReportHelper.ColumnWiseExcelForSuperAdmin(payList, columnname,
							dbcolumnname);
					return in;
				}
			}
		}

		return null;
	}

	/**
	 * get Column Name On ui ColumnName
	 * 
	 * @author Pratik chaudhari
	 * @param columnname
	 * @return String
	 */
	public String getColumnNameOnuiColumnName(String columnname) {

		return columnmappingRepo.getColumnNameOnuiColumnName(columnname);
	}

	/**
	 * get Column Name With ui ColumnName
	 * 
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ResponseColumnBean
	 */
	public ResponseColumnBean getColumnNameWithuiColumnName(String fkUserId, String role, String category) {
		if (role.equals(CommonConstants.admin)) {
			List<AdminColumns> admincolumns = adminColoumnRepository.getListOfAdminColumn(fkUserId, category);
			List<ResponseColumnMappingbean> columnlist = new ArrayList<ResponseColumnMappingbean>();

			ResponseColumnBean obj = new ResponseColumnBean();

			if (admincolumns.isEmpty()) {
				obj.setAdminName("N/A");
				obj.setList(new ResponseColumnMappingbean("N/A", "N/A"));

			} else {
				for (int i = 0; i < admincolumns.size(); i++) {
					ResponseColumnMappingbean columnobj = new ResponseColumnMappingbean();
					columnobj.setAllocatedColumn_ui(admincolumns.get(i).getColumns());
					columnobj.setColumnName(columnmappingRepo.getdbcolumn(admincolumns.get(i).getColumns()));
					columnobj.setUnit(admincolumns.get(i).getUnit());
					columnlist.add(columnobj);
				}
				obj.setAdminName(admincolumns.get(0).getAdminName());
				obj.setList(columnlist);
			}

			return obj;
		}
		if (role.equals(CommonConstants.user)) {
			long adminid = assettagRepo.getAdminid(fkUserId);
			List<AdminColumns> admincolumns = adminColoumnRepository.getListOfAdminColumnForUser(adminid, category);
			List<ResponseColumnMappingbean> columnlist = new ArrayList<ResponseColumnMappingbean>();
			ResponseColumnBean obj = new ResponseColumnBean();

			for (int i = 0; i < admincolumns.size(); i++) {
				ResponseColumnMappingbean columnobj = new ResponseColumnMappingbean();
				columnobj.setAllocatedColumn_ui(admincolumns.get(i).getColumns());
				columnobj.setColumnName(columnmappingRepo.getdbcolumn(admincolumns.get(i).getColumns()));
				columnobj.setUnit(admincolumns.get(i).getUnit());
				columnlist.add(columnobj);
			}
			obj.setAdminName(admincolumns.get(0).getAdminName());
			obj.setList(columnlist);
			return obj;

		}
		if (role.equals("empuser")) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			List<AdminColumns> admincolumns = adminColoumnRepository.getListOfAdminColumnForUser(adminid, category);
			List<ResponseColumnMappingbean> columnlist = new ArrayList<ResponseColumnMappingbean>();
			ResponseColumnBean obj = new ResponseColumnBean();

			for (int i = 0; i < admincolumns.size(); i++) {
				ResponseColumnMappingbean columnobj = new ResponseColumnMappingbean();
				columnobj.setAllocatedColumn_ui(admincolumns.get(i).getColumns());
				columnobj.setColumnName(columnmappingRepo.getdbcolumn(admincolumns.get(i).getColumns()));
				columnobj.setUnit(admincolumns.get(i).getUnit());
				columnlist.add(columnobj);
			}
			obj.setAdminName(admincolumns.get(0).getAdminName());
			obj.setList(columnlist);
			return obj;

		}
		return null;
	}

	/**
	 * get unit
	 * 
	 * @param columns
	 * @author Pratik chaudhari
	 * @return String
	 */
	public String getunit(String columns) {

		return columnmappingRepo.getUnit(columns);
	}

}
