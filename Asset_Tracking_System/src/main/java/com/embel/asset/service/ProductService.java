package com.embel.asset.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.embel.asset.bean.ProductDetailsBean;
import com.embel.asset.bean.ProductResponseAllocationBean;
import com.embel.asset.bean.ResponseProductBean;
import com.embel.asset.dto.ProductDetailsDto;
import com.embel.asset.dto.ProductTagAllocationDto;
import com.embel.asset.entity.ProductDetailForAllocation;
import com.embel.asset.entity.ProductDetails;
import com.embel.asset.entity.ProductDetailsAllocationDeleteHistory;
import com.embel.asset.entity.ProductDetailsAllocationHistory;
import com.embel.asset.entity.UserDetail;
import com.embel.asset.helper.CommonConstants;
import com.embel.asset.repository.AssetTagRepository;
import com.embel.asset.repository.EmployeUserRepository;
import com.embel.asset.repository.ProductAllocationHistoryRepository;
import com.embel.asset.repository.ProductAllocationRepository;
import com.embel.asset.repository.ProductDetailRepository;
import com.embel.asset.repository.ProductDetailsAllocationDeleteHistoryRepo;
import com.embel.asset.repository.UserRepository;

@Service
public class ProductService {

	@Autowired
	ProductAllocationRepository allocationRepository;

	@Autowired
	ProductDetailRepository prodRepository;

	@Autowired
	AssetTagRepository tagRepo;

	@Autowired
	UserRepository userRepo;
	@Autowired
	EmployeUserRepository employeeUserRepo;
	@Autowired
	ProductAllocationHistoryRepository producallocationhistory;

	@Autowired
	ProductDetailsAllocationDeleteHistoryRepo producallocationDeletehistoryRepo;

	/**
	 * use to add product
	 * 
	 * @author Pratik chaudhari
	 * @param dto
	 * @return String
	 */
	public String addProduct(ProductDetailsDto dto) {

		ProductDetails newProduct = new ProductDetails();
		long fkUserid = userRepo.getbyuserName(dto.getAdmin());
		UserDetail user = userRepo.getById(fkUserid);

		String userName = user.getFirstName() + " " + user.getLastName();

		newProduct.setProductName(dto.getProductName());
		newProduct.setDescription(dto.getDescription());
		newProduct.setFkUserId(fkUserid);
		newProduct.setCreatedby(dto.getCreatedby());
		newProduct.setSubproductName(dto.getSubproductname());
		newProduct.setCategory(dto.getCategory());
		newProduct.setUpdatedby("N/A");
		newProduct.setAdmin(dto.getAdmin());
		prodRepository.save(newProduct);

		return "Product Added Successfully...!";

	}

	/**
	 * use to update product
	 * 
	 * @author Pratik chaudhari
	 * @param product
	 * 
	 */
	public void updateproduct(ProductDetailsDto product) {
		ProductDetails newProduct = new ProductDetails();
		long fkUserid = userRepo.getbyuserName(product.getAdmin());
		UserDetail user = userRepo.getById(fkUserid);
		String userName = user.getFirstName() + " " + user.getLastName();
		newProduct.setProductId(product.getProductId());
		newProduct.setProductName(product.getProductName());
		newProduct.setDescription(product.getDescription());
		newProduct.setFkUserId(fkUserid);
		newProduct.setCreatedby(userName);
		newProduct.setUpdatedby(product.getUpdatedby());
		newProduct.setAdmin(product.getAdmin());
		newProduct.setSubproductName(product.getSubproductname());
		newProduct.setCategory(product.getCategory());
		prodRepository.save(newProduct);

	}

	/**
	 * get product For Edit
	 * 
	 * @author Pratik chaudhari
	 * @param parseLong
	 * @return ProductDetails
	 */
	public Optional<ProductDetails> getproductForEdit(long parseLong) {

		return prodRepository.findById(parseLong);
	}

	/**
	 * 
	 * @param productId
	 * @param bean
	 */
	public void updateProduct(Long productId, ProductDetailsBean bean) {

	}

	/*
	 * public List<ResponseProductBean> getAllproductListForDrodown(String
	 * fkUserId,String role) { Long userId = Long.parseLong(fkUserId);
	 * if(role.equals(CommonConstants.superAdmin)) { return
	 * prodRepository.getAllproductListForDrodownSuperadmin(); }
	 * if(role.equals(CommonConstants.admin)) { return
	 * prodRepository.getAllproductListForDrodownAdmin(userId); }
	 * 
	 * return null; }
	 */

	/**
	 * get All Product List On Product Name wise
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @param pageable
	 * @return ProductDetailsBean
	 */
	public Page<ProductDetailsBean> getAllProductListOnProductNamewise(String fkUserId, String role, String category,
			Pageable pageable) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return prodRepository.getAllProductListOnUserwiseSuperadmin(category, pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			String organizationUserName = userRepo.getOrganizationName(fkUserId);
			List<String> adminList = userRepo.getAdminListOnOrganization(organizationUserName);
			List<ProductDetailsBean> beanList = new ArrayList<ProductDetailsBean>();
			System.out.println("adminList.size()" + adminList.size());
			for (int i = 0; i < adminList.size(); i++) {
				System.out.println("adminList.get(i)" + adminList.get(i));

				List<ProductDetails> prodlist = null;
				try {
					prodlist = prodRepository.getAllProducrListForOrganization(adminList.get(i), category);
				} catch (Exception e) {
					System.out.println();
				}
				System.out.println("prodlist" + prodlist);

				if (prodlist.isEmpty()) {
				} else {
					for (int j = 0; j < prodlist.size(); j++) {
						ProductDetailsBean bean = new ProductDetailsBean();
						bean.setProductName(prodlist.get(j).getProductName());
						bean.setSubproductName(prodlist.get(j).getSubproductName());
						bean.setDescription(prodlist.get(j).getDescription());
						bean.setCreatedby(prodlist.get(j).getCreatedby());
						bean.setUpdatedby(prodlist.get(j).getUpdatedby());
						bean.setProductId(prodlist.get(j).getProductId());
						bean.setFkUserId(prodlist.get(j).getFkUserId());
						beanList.add(bean);
					}
				}

			}
			final int start = (int) pageable.getOffset();
			final int end = Math.min((start + pageable.getPageSize()), beanList.size());
			final Page<ProductDetailsBean> page = new PageImpl<>(beanList.subList(start, end), pageable,
					beanList.size());
			return page;
		}
		if (role.equals(CommonConstants.admin)) {
			return prodRepository.getAllProductListOnUserwiseAdmin(userId, category, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			return prodRepository.getAllProductListOnUserwiseUser(userId, category, pageable);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return prodRepository.getAllProductListOnUserwiseAdmin(adminid, category, pageable);
		}
		return null;
	}

	/**
	 * get All Product List On Product Namewise
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ProductDetails
	 */
	public List<ProductDetails> getAllProductListOnProductNamewisexxl(String fkUserId, String role, String category) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return prodRepository.getAllProductListOnUserwiseSuperadminx(category);
		}
		if (role.equals(CommonConstants.organization)) {
			String organizationUserName = userRepo.getuserNaame(fkUserId);
			List<String> adminList = userRepo.getAdminListOnOrganization(organizationUserName);
			List<ProductDetails> userbean = new ArrayList<ProductDetails>();
			for (int i = 0; i < adminList.size(); i++) {
				List<ProductDetails> userList = null;
				try {
					userList = prodRepository.getuserListunderAdmin(adminList.get(i), category);
				} catch (Exception e) {
					System.out.println(e);
				}

				for (int j = 0; j < userList.size(); j++) {
					userbean.add(userList.get(j));
				}
			}

			return userbean;
		}
		if (role.equals(CommonConstants.admin)) {
			return prodRepository.getAllProductListOnUserwiseAdminx(userId, category);
		}
		if (role.equals(CommonConstants.user)) {
			return prodRepository.getAllProductListOnUserwiseUserx(userId, category);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return prodRepository.getAllProductListOnUserwiseAdminx(adminid, category);
		}
		return null;

	}

	/**
	 * get All Product Name Wise Product List
	 * 
	 * @param productName
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	public List<String> getAllProductNameWiseProductList(String productName, String fkUserId, String role,
			String category) {

		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return prodRepository.getAllProductListProductNameWiseSuperadmin(productName, category);
		}
		if (role.equals(CommonConstants.organization)) {
//		String userName=userRepo.getuserNaame(fkUserId);
//		List<Long> AdminList=userRepo.getAllAdminListForOrg(userName);
//		
//		for(int i=0;i<AdminList.size();i++)
//		{
//			List<String> plist=prodRepository.getAllProductListProductNameWiseOrganization(productName,AdminList.get(i),category);
//			
//		}
			return prodRepository.getAllProductListProductNameWiseOrganization(productName, userId, category);
		}
		if (role.equals(CommonConstants.admin)) {
			return prodRepository.getAllProductListProductNameWiseAdmin(productName, userId, category);
		}
		if (role.equals(CommonConstants.user)) {
			return prodRepository.getAllProductListProductNameWiseiseUser(productName, userId, category);
		}
		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return prodRepository.getAllProductListProductNameWiseAdmin(productName, adminid, category);
		}
		return null;

	}
	// -------------------------------------------------------------------------------------------------------------------
	// ----------------- Product Tag Allocation---------------------------

	/**
	 * get Product Details
	 * 
	 * @param productId
	 * @return ProductDetailForAllocation
	 */
	public ProductDetailForAllocation getProductDetails(Long productId) {
		if (allocationRepository.existsById(productId)) {
			return allocationRepository.getById(productId);
		}
		return null;
	}

	// ----------------- Product Tag Allocation in Bulk ---------------------------

	// -------------------------------------------------------------------------------------------------------------------

	/**
	 * get All product Tag Allocation List
	 * 
	 * @param fkUserId
	 * @author pratik chaudhari
	 * @param role
	 * @return ProductResponseAllocationBean
	 */
	public List<ProductResponseAllocationBean> getAllproductTagAllocationList(String fkUserId, String role) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return allocationRepository.getAllproductTagListSuperadmin();
		}
		if (role.equals(CommonConstants.admin)) {
			return allocationRepository.getAllproductTagListAdmin(userId);
		}

		return null;
	}

	/**
	 * use to delete Product
	 * 
	 * @author Pratik chaudhari
	 * @param pkID
	 */
	public void deleteProduct(Long pkID) {

		ProductDetailForAllocation bean = allocationRepository.getOne(pkID);
		System.out.println("=================List=========" + bean);

		String tagName = bean.getAllocatedTagName();
		String tagUniqueCode = bean.getAssetUniqueCodeOrMacId();
		String updatedStatus = "Not-allocated";
		tagRepo.updateStatusAfterAllocation(updatedStatus, tagName, tagUniqueCode);
		ProductDetailsAllocationDeleteHistory deleteHistory = new ProductDetailsAllocationDeleteHistory();
		deleteHistory.setAllocatedTagName(bean.getAllocatedTagName());
		deleteHistory.setAssetUniqueCodeOrMacId(bean.getAssetUniqueCodeOrMacId());
		deleteHistory.setDispatchLocation(bean.getDispatchLocation());
		deleteHistory.setFkUserId(bean.getFkUserId());
		deleteHistory.setProductId(bean.getProductId());
		deleteHistory.setProductName(bean.getProductName());
		deleteHistory.setUser(bean.getUser());
		deleteHistory.setAllocatedTagId(bean.getAllocatedTagId());
		producallocationDeletehistoryRepo.save(deleteHistory);
		allocationRepository.deleteById(pkID);
	}

	/**
	 * delete Product in bulk
	 * 
	 * @param pkID
	 */
	public void deleteProductForMultiple(List<Long> pkID) {

		for (int i = 0; i < pkID.size(); i++) {
			ProductDetailForAllocation bean = allocationRepository.getOne(pkID.get(i));
			System.out.println("=================List=========" + bean);
			String tagName = bean.getAllocatedTagName();
			String tagUniqueCode = bean.getAssetUniqueCodeOrMacId();
			String updatedStatus = "Not-allocated";
			tagRepo.updateStatusAfterAllocation(updatedStatus, tagName, tagUniqueCode);

			ProductDetailsAllocationDeleteHistory deleteHistory = new ProductDetailsAllocationDeleteHistory();
			deleteHistory.setAllocatedTagName(bean.getAllocatedTagName());
			deleteHistory.setAssetUniqueCodeOrMacId(bean.getAssetUniqueCodeOrMacId());
			deleteHistory.setDispatchLocation(bean.getDispatchLocation());
			deleteHistory.setFkUserId(bean.getFkUserId());
			deleteHistory.setProductId(bean.getProductId());
			deleteHistory.setProductName(bean.getProductName());
			deleteHistory.setUser(bean.getUser());
			producallocationDeletehistoryRepo.save(deleteHistory);
			allocationRepository.deleteSelectedproduct(pkID.get(i));
		}
	}

	/**
	 * add Product And Tag Allocation
	 * 
	 * @author Pratik chaudhari
	 * @param bean
	 * @return String
	 */
	public String addProductAndTagAllocation(ProductTagAllocationDto bean) {

		ProductDetailForAllocation newProduct = new ProductDetailForAllocation();

		UserDetail user = userRepo.getById(bean.getFkUserId());
		String userName = user.getFirstName() + " " + user.getLastName();
		long fkid = userRepo.getadminFkid(bean.getAdmin());
		String tagUser = null;
		try {
			tagUser = tagRepo.getUserOfTag(bean.getAllocatedTagName());
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (bean.getAssetUniqueCodeOrMacId().equals("")) {
			newProduct.setAssetimei(bean.getAssetimei());
		} else {
			newProduct.setAssetUniqueCodeOrMacId(bean.getAssetimei());
		}

		newProduct.setProductName(bean.getProductName());
		newProduct.setAllocatedTagName(bean.getAllocatedTagName());
		newProduct.setAssetUniqueCodeOrMacId(bean.getAssetUniqueCodeOrMacId());
		newProduct.setAllocatedTagId(bean.getAllocatedTagId());
		newProduct.setProductId(bean.getProductId());

		if (tagUser.equals(null)) {
			newProduct.setUser("N/A");
		} else {
			newProduct.setUser(tagUser);
		}
		newProduct.setFkUserId(fkid);
		newProduct.setDispatchLocation(bean.getDispatchLocation());
		newProduct.setSubproductName(bean.getSubproductName());

		newProduct.setCategory(bean.getCategory());
		newProduct.setDispatchtime(bean.getDispatchtime());
		newProduct.setAdmin(bean.getAdmin());

		allocationRepository.save(newProduct);

		String tagName = bean.getAllocatedTagName();
		String tagUniqueCode = bean.getAssetUniqueCodeOrMacId();
		String updatedStatus = "Allocated";
		tagRepo.updateStatusAfterAllocation(updatedStatus, tagName, tagUniqueCode);

		return "Product allocated Successfully...!";

	}

	/**
	 * get All product List For Drodown
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ProductDetailForAllocation
	 */
	public List<ProductDetailForAllocation> getAllproductListForDrodown(String fkUserId, String role, String category) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			List<ProductDetailForAllocation> list;
			list = allocationRepository.getAllproductListForDrodownSuperadmin(category);
			System.out.println("&&&&&" + list);

			return list;
		}
		if (role.equals(CommonConstants.organization)) {
			String organizationusername = userRepo.getuserNaame(fkUserId);
			List<String> adminlist = userRepo.getAdminList(organizationusername);
			List<ProductDetailForAllocation> beans = new ArrayList();
			for (int i = 0; i < adminlist.size(); i++) {
				List<ProductDetailForAllocation> adminbeans = null;
				try {
					adminbeans = allocationRepository.getAllproductListForDrodownOrganization(adminlist.get(i),
							category);

				} catch (Exception e) {
					System.out.println(e);
				}

				if (adminbeans == null) {
				} else {
					for (int j = 0; j < adminbeans.size(); j++) {
						beans.add(adminbeans.get(j));

					}
				}
			}

			return beans;
		}
		if (role.equals(CommonConstants.admin)) {
			return allocationRepository.getAllproductListForDrodownAdmin(fkUserId, category);
		}
		if (role.equals(CommonConstants.user)) {
			String userName = userRepo.getUserName(fkUserId);
			long adminId = userRepo.getAdminId(userName);
			return allocationRepository.getAllproductListForDrodownUser(adminId, category);
		}
		if (role.equals(CommonConstants.empUser)) {
			String adminId = employeeUserRepo.getAdminIdxx(fkUserId);
			return allocationRepository.getAllproductListForDrodownAdmin(adminId, category);
		}
		return null;
	}

	/**
	 * get All Product Tag List On Product Namewise
	 * 
	 * @author Pratik chaudhari
	 * @param productName
	 * @param fkUserId
	 * @param role
	 * @return ProductResponseAllocationBean
	 */
	public List<ProductResponseAllocationBean> getAllProductTagListOnProductNamewise(String productName,
			String fkUserId, String role) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return allocationRepository.getAllProductTagListOnUserwiseSuperadmin(productName);
		}
		if (role.equals(CommonConstants.admin)) {
			return allocationRepository.getAllProductTagListOnUserwiseAdmin(productName, userId);
		}

		return null;
	}

	/**
	 * get product Allocation For Edit
	 * 
	 * @author Pratik chaudhari
	 * @param id
	 * @return ProductDetailForAllocation
	 */
	public ProductDetailForAllocation getproductAllocationForEdit(Long id) {
		return allocationRepository.findById(id).get();
	}

	/**
	 * update product Allocation
	 * 
	 * @author Pratik chaudhari
	 * @param allocatedProduct
	 */
	public void updateproductAllocation(ProductDetailForAllocation allocatedProduct) {

		ProductDetailForAllocation allocation = allocationRepository.getById(allocatedProduct.getProductAllocationId());
		UserDetail user = userRepo.getById(allocatedProduct.getFkUserId());
		String userName = user.getFirstName() + " " + user.getLastName();
		long fkuserid = userRepo.getadminFkid(allocatedProduct.getAdmin());
		ProductDetailsAllocationHistory productHistory = new ProductDetailsAllocationHistory();
		long fkid = userRepo.getadminFkid(allocation.getAdmin());
		productHistory.setProductName(allocation.getProductName());
		productHistory.setAllocatedTagName(allocation.getAllocatedTagName());

		productHistory.setAssetUniqueCodeOrMacId(allocation.getAssetUniqueCodeOrMacId());
		productHistory.setAllocatedTagId(allocation.getAllocatedTagId());
		productHistory.setProductId(allocation.getProductId());
		productHistory.setUser(allocation.getUser());
		productHistory.setFkUserId(fkid);
		productHistory.setDispatchLocation(allocation.getDispatchLocation());
		productHistory.setSubproductName(allocation.getSubproductName());
		productHistory.setAdmin(allocation.getAdmin());
		producallocationhistory.save(productHistory);
		String tagUser = null;
		try {
			tagUser = tagRepo.getUserOfTag(allocatedProduct.getAllocatedTagName());
		} catch (Exception e) {
			// TODO: handle exception
		}
		allocation.setProductName(allocatedProduct.getProductName());
		allocation.setAllocatedTagName(allocatedProduct.getAllocatedTagName());
		allocation.setAssetUniqueCodeOrMacId(allocatedProduct.getAssetUniqueCodeOrMacId());
		allocation.setAllocatedTagId(allocatedProduct.getAllocatedTagId());
		allocation.setProductId(allocatedProduct.getProductId());
		if (tagUser.equals(null)) {
			allocation.setUser("N/A");
		} else {
			allocation.setUser(tagUser);
		}
		allocation.setFkUserId(fkuserid);
		allocation.setDispatchLocation(allocatedProduct.getDispatchLocation());
		allocation.setSubproductName(allocatedProduct.getSubproductName());
		allocation.setAdmin(allocatedProduct.getAdmin());
		allocation.setCategory(allocatedProduct.getCategory());
		allocationRepository.save(allocation);

		/*
		 * String tagName = allocatedProduct.getAllocatedTagName(); String tagUniqueCode
		 * = allocatedProduct.getAssetUniqueCodeOrMacId(); String updatedStatus =
		 * "Not allocated";
		 * tagRepo.updateStatusAfterAllocation(updatedStatus,tagName,tagUniqueCode);
		 * allocationRepository.deleteById(allocatedProduct.getProductAllocationId());
		 */
	}

	/**
	 * get All product List For Drodown With Pagination
	 * 
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @return ResponseProductBean
	 */
	public Page<ResponseProductBean> getAllproductListForDrodownWithPagination(String fkUserId, String role,
			Pageable pageable) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {

			return allocationRepository.getAllproductListForDrodownSuperadminWithPagination(pageable);

		}
		if (role.equals(CommonConstants.admin)) {
			return allocationRepository.getAllproductListForDrodownAdminWithPagination(userId, pageable);

		}

		return null;

	}

	/**
	 * use to get record count from database
	 * 
	 * @return String
	 */

	public String getdbCount() {

		return allocationRepository.getdbcount();
	}

	/**
	 * get All Product Tag List On Product Namewise With Pagination
	 * 
	 * @param productName
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @return ProductResponseAllocationBean
	 */
	public Page<ProductResponseAllocationBean> getAllProductTagListOnProductNamewiseWithPagination(String productName,
			String fkUserId, String role, Pageable pageable) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return allocationRepository.getAllProductTagListOnUserwiseSuperadminWithPagination(productName, pageable);
		}
		if (role.equals(CommonConstants.admin)) {
			return allocationRepository.getAllProductTagListOnUserwiseAdminWithPagination(productName, userId,
					pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			return allocationRepository.getAllProductTagListOnUserwiseSuperadminWithPagination(productName, pageable);
		}
		return null;

	}

	/**
	 * get All product Tag Allocation List With Pagination
	 * 
	 * @param fkUserId
	 * @param role
	 * @param pageable
	 * @param category
	 * @return ProductDetailForAllocation
	 */
	public Page<ProductDetailForAllocation> getAllproductTagAllocationListWithPagination(String fkUserId, String role,
			Pageable pageable, String category) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return allocationRepository.getAllproductTagListSuperadminWithPagination(category, pageable);
		}
		if (role.equals(CommonConstants.organization)) {
			String username = userRepo.getUserName(fkUserId);
			List<String> adminList = userRepo.getAdminListOnOrganization(username);
			System.out.println("adminList" + adminList);
			List<ProductDetailForAllocation> pobj = new ArrayList<ProductDetailForAllocation>();
			for (int i = 0; i < adminList.size(); i++) {
				List<ProductDetailForAllocation> pageList = null;
				try {
					pageList = allocationRepository.getAllproductTagListOrganizationWithPagination(category,
							adminList.get(i));
					System.out.println("@@@@@@@@@@pageList" + pageList);
				} catch (Exception e) {
					// TODO: handle exception
				}

				System.out.println("pageList" + pageList);
				if (pageList == null) {
					System.out.println("pageList" + pageList);
				} else {
					for (int j = 0; j < pageList.size(); j++) {
						ProductDetailForAllocation tlist = new ProductDetailForAllocation();
						tlist.setProductAllocationId(pageList.get(j).getAllocatedTagId());
						tlist.setProductName(pageList.get(j).getProductName());
						tlist.setProductId(pageList.get(j).getProductId());
						tlist.setAllocatedTagName(pageList.get(j).getAllocatedTagName());
						tlist.setAllocatedTagId(pageList.get(j).getAllocatedTagId());
						tlist.setAssetimei(pageList.get(j).getAssetimei());
						tlist.setAssetUniqueCodeOrMacId(pageList.get(j).getAssetUniqueCodeOrMacId());
						tlist.setUser(pageList.get(j).getUser());
						tlist.setFkUserId(pageList.get(j).getFkUserId());
						tlist.setAdmin(pageList.get(j).getAdmin());
						tlist.setDispatchLocation(pageList.get(j).getDispatchLocation());
						tlist.setDispatchtime(pageList.get(j).getDispatchtime());
						tlist.setSubproductName(pageList.get(j).getSubproductName());
						tlist.setCategory(pageList.get(j).getCategory());

						pobj.add(tlist);

					}

				}

			}
			System.out.println("pobj is ->" + pobj);
			System.out.println("pobj is ->" + pobj.size());
//			int start =0;
//			if(pobj.size()<(int)pageable.getOffset())
//			{  start =pobj.size() ;}else {final int start = (int)pageable.getOffset();}
			final int start = (int) pageable.getOffset();
			int end = 0;

			try {
				end = Math.min((start + pageable.getPageSize()), pobj.size());
			} catch (Exception e) {
				System.out.println(e);
			}

			final Page<ProductDetailForAllocation> page = new PageImpl<>(pobj.subList(start, end), pageable,
					pobj.size());
			// final Page<UserInfobean> page = new PageImpl<>(list);
			return page;

			// System.out.println("page:"+page);
			// return page;
		}
		if (role.equals(CommonConstants.admin)) {
			return allocationRepository.getAllproductTagListAdminWithPagination(category, userId, pageable);
		}

		if (role.equals(CommonConstants.empUser)) {
			long adminid = employeeUserRepo.getAdminId(fkUserId);
			return allocationRepository.getAllproductTagListAdminWithPagination(category, adminid, pageable);
		}
		if (role.equals(CommonConstants.user)) {
			String userName = userRepo.getUserName(fkUserId);
			return allocationRepository.getgetAllproductTaglistUserWithPagination(category, userName, pageable);

		}
		return null;

	}

	/**
	 * use to get list of get product Allocation History
	 * 
	 * @param assetTagName
	 * @param fkUserId
	 * @param role
	 * @return ProductDetailsAllocationHistory
	 */
	public List<ProductDetailsAllocationHistory> getproductAllocationHistory(String assetTagName, String fkUserId,
			String role) {
		Long userId = Long.parseLong(fkUserId);
		if (role.equals(CommonConstants.superAdmin)) {
			return producallocationhistory.getProductAllocationHistoryForSuperAdmin(assetTagName);
		}
		if (role.equals(CommonConstants.organization)) {
			return producallocationhistory.getProductAllocationHistoryForOrganization(assetTagName, userId);
		}
		if (role.equals(CommonConstants.admin)) {
			return producallocationhistory.getProductAllocationHistoryForAdmin(assetTagName, userId);
		}

		if (role.equals(CommonConstants.empUser)) {
			long adminId = employeeUserRepo.getAdminId(fkUserId);
			return producallocationhistory.getProductAllocationHistoryForAdmin(assetTagName, adminId);
		}
		return null;
	}

	/**
	 * add Excel File Data To Database For Product
	 * 
	 * @author Pratik chaudhari
	 * @param files
	 * @param username2
	 * @return String
	 * @throws IOException
	 */
	public String addExcelFileDataToDatabaseForProduct(MultipartFile files, String username2) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		int icnt = 0;
		for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
//			if()
//			{
//				
//			}
			System.out.println("worksheet.getPhysicalNumberOfRows()" + worksheet.getPhysicalNumberOfRows());
			if (index > 0) {
				UserDetail userDet = new UserDetail();
				XSSFRow row = worksheet.getRow(index);

				String productname = row
						.getCell(0, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String subproductname = row
						.getCell(1, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String productdiscription = row
						.getCell(2, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String dispatchlocation = row
						.getCell(3, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String admin = row.getCell(4, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String username = row.getCell(5, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String category = row.getCell(6, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				long fkUserid = 0;
				try {
					fkUserid = userRepo.getbyuserName(admin);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (fkUserid == 0) {
					break;
				}

				ProductDetails pobj = new ProductDetails();

				pobj.setProductName(productname);
				pobj.setSubproductName(subproductname);
				pobj.setCreatedby(username2);
				pobj.setDescription(productdiscription);
				pobj.setFkUserId(fkUserid);
				pobj.setAdmin(admin);
				pobj.setCategory(category);
				pobj.setUpdatedby("N/A");
				ProductDetails sobj = prodRepository.save(pobj);

				icnt++;
			}

		}
		int lastrow = worksheet.getPhysicalNumberOfRows();
		if (icnt == lastrow - 1) {
			return " Product sucessfully " + icnt + "Record are saved";
		} else {

			return "only " + icnt + " Product Record are saved ";
		}

	}

	/**
	 * add Excel File Data To Database
	 * 
	 * @param files
	 * @param username2
	 * @return String
	 * @throws IOException
	 */
	public String addExcelFileDataToDatabase(MultipartFile files, String username2) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		int icnt = 0;
		for (int index = 1; index < worksheet.getPhysicalNumberOfRows() - 1; index++) {
			if (index > 0) {
				UserDetail userDet = new UserDetail();
				XSSFRow row = worksheet.getRow(index);

				String productname = row
						.getCell(0, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String subproductname = row
						.getCell(1, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String productdiscription = row
						.getCell(2, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String assettagname = row
						.getCell(3, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String dispatchlocation = row
						.getCell(4, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String username = row.getCell(5, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				String category = row.getCell(6, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				String taguniquecode = null;
				String assetimei = null;
//				if(category.equals("BLE"))
//				{
				taguniquecode = row.getCell(7, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
//				}else {
				assetimei = row.getCell(8, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();

				// }

				long fkUserid = userRepo.getbyuserName(username);

				ProductDetails pobj = new ProductDetails();

				pobj.setProductName(productname);
				pobj.setSubproductName(subproductname);
				pobj.setCreatedby(username2);
				pobj.setDescription(productdiscription);
				pobj.setFkUserId(fkUserid);
				pobj.setCategory(category);

				ProductDetails sobj = prodRepository.save(pobj);

				System.out.println("sobj" + sobj);
				ProductDetailForAllocation newProduct = new ProductDetailForAllocation();

				UserDetail user = userRepo.getById(fkUserid);
				String userName = user.getFirstName() + " " + user.getLastName();

				newProduct.setProductName(productname);
				newProduct.setAllocatedTagName(assettagname);
				newProduct.setAssetUniqueCodeOrMacId(taguniquecode);

				newProduct.setProductId(sobj.getProductId());
				newProduct.setUser(userName);
				newProduct.setFkUserId(fkUserid);
				newProduct.setDispatchLocation(dispatchlocation);
				newProduct.setSubproductName(subproductname);
				newProduct.setCategory(category);
				newProduct.setAssetimei(assetimei);
				// newProduct.setDispatchtime(new Date());
				newProduct.setAdmin(userName);
				allocationRepository.save(newProduct);

				String tagName = assettagname;
				String tagUniqueCode = taguniquecode;
				String updatedStatus = "Allocated";
				tagRepo.updateStatusAfterAllocation(updatedStatus, tagName, tagUniqueCode);
				icnt++;
			}

		}
		int lastrow = worksheet.getPhysicalNumberOfRows();
		if (icnt == lastrow - 1) {
			return " Product sucessfully " + icnt + "Record are saved";
		} else {

			return "only " + icnt + " Product Record are saved ";
		}

	}

	/**
	 * multiple product Delete
	 * 
	 * @param pkID
	 */
	public void multipleproductDelete(List<Long> pkID) {

		for (int i = 0; i < pkID.size(); i++) {
			// Long fkID = Long.parseLong();
			prodRepository.deleteSelectedTag(pkID.get(i));

		}

	}

}
