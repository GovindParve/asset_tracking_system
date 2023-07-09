package com.embel.asset.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import com.embel.asset.service.AssetTagService;
import com.embel.asset.service.ProductAllocationDeleteHistoryService;
import com.embel.asset.service.ProductService;

@CrossOrigin("*")
@RestController
public class ProductController implements ErrorController{

	@Autowired
	AssetTagService tagService;

	@Autowired
	ProductService productService;
	
	@Autowired
	ProductAllocationDeleteHistoryService ProductAllocationDeleteHistoryservice;
	//----------------Add new product with details to database------------------------
	/**
	 * Add new product with details to database
	 * @author Pratik chaudhari
	 * @param dto
	 * @return String
	 */
	@PostMapping(value = "/product/add-product-details")
	public String saveProductDetails(@RequestBody ProductDetailsDto dto) 
	{
		System.out.println("Product Bean in saveOrUpdateRecord--->"+dto);
		String response = productService.addProduct(dto);
		return response;
	}

		//update product
	/**
	 * use to update product
	 * @param Product
	 * @return String
	 */
			@PutMapping("/product/update-product")
			public String updateproductx(@RequestBody ProductDetailsDto Product)
			{
				System.out.println("Product"+Product);
				productService.updateproduct(Product);
				return "product Updated Successfully....!";
			}
		/**
		 *  use to get Product by id 
		 * @param id
		 * @return ProductDetails
		 */
			@GetMapping("/product/get-product/{id}") 
			public Optional<ProductDetails> getProduct(@PathVariable String id)
			{
				return productService.getproductForEdit(Long.parseLong(id));
			}
		
	//-----------------------Get All Product List-with paginaton----------------------------
			/**
			 * Get All Product List-with paginaton
			 * @author Pratik Chaudhari
			 * @param pageNo
			 * @param fkUserId
			 * @param role
			 * @param category
			 * @return ProductDetailsBean
			 */
	@GetMapping("/product/get-product-list/{pageNo}")
	public Page<ProductDetailsBean> getAllProductListOnProductNamewise(@PathVariable String pageNo,String fkUserId,String role,String category)
	{	
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo),10);
		return productService.getAllProductListOnProductNamewise(fkUserId,role,category,pageable);		
	}
	
	/**
	 * get All Product List
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ProductDetails
	 */
	@GetMapping("/product/get-product-list-productdropDown")
	public List<ProductDetails> getAllProductList(String fkUserId,String role,String category)
	{	
		
		return productService.getAllProductListOnProductNamewisexxl(fkUserId,role,category);		
	}
	
	/**
	 * get All Product Name Wise Product List
	 * @author Pratik chaudhari 
	 * @param productName
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return String
	 */
	@GetMapping("/product/get-ProductNameWise-ProductdropDownList")
	public List<String> getAllProductNameWiseProductList(String productName,String fkUserId,String role,String category)
	{	
		
		return productService.getAllProductNameWiseProductList(productName,fkUserId,role,category);		
	}
	
	//------------------Product tag allocation added to database -------------------------
	/**
	 * Product tag allocation added to database
	 * @author Pratik chaudhari
	 * @param bean
	 * @return String
	 */
	@PostMapping(value = "/product/add-product-tag-allocation")
	public String saveOrUpdateProductDetails(@RequestBody ProductTagAllocationDto bean) {

		System.out.println("Product Bean in saveOrUpdateRecord--->"+bean);

		String response = productService.addProductAndTagAllocation(bean);
		return response;
	}
	//......................pagination 
	/**
	 * get All product Tag Allocation List With Pagination
	 * @author Pratik chaudhari
	 * @param pageNo
	 * @param fkUserId
	 * @param role
	 * @param category
	 * @return ProductDetailForAllocation
	 */
	@GetMapping("/product/get-product-tag-allocation-list/{pageNo}")
	public Page<ProductDetailForAllocation> getAllproductTagAllocationListWithPagination(@PathVariable String pageNo,String fkUserId,String role,String category)
	{		
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo),10);
		return  productService.getAllproductTagAllocationListWithPagination(fkUserId,role,pageable,category);		
	}

//....................................................................................................
	//------------------Get product-tag Allocation list to display in table format--------------------------------------------	
	
	/**
	 * get All product Tag Allocation List
	 * @author Pratik chaudhari
	 * @param fkUserId
	 * @param role
	 * @return ProductResponseAllocationBean
	 */
	@GetMapping("/product/get-product-tag-allocation-list")
	public List<ProductResponseAllocationBean> getAllproductTagAllocationList(String fkUserId,String role)
	{		
		return  productService.getAllproductTagAllocationList(fkUserId,role);		
	}

	//------------------Get single Product Details--------------------------------------------	
	/**
	 * Get single Product Details
	 * @author Pratik chaudhari
	 * @param id
	 * @return ProductResponseAllocationBean
	 */
	@GetMapping("/product/get-single-product/{id}") 
	public ProductResponseAllocationBean  getSingleProductById(@PathVariable("id") String id) 
	{ 
		ProductResponseAllocationBean bean = new ProductResponseAllocationBean();
		ProductDetailForAllocation singleProduct = productService.getProductDetails(Long.parseLong(id));
		System.out.println("&&&&&&&&&&&" + singleProduct);

		if(singleProduct == null) 
		{
			return new ProductResponseAllocationBean();
		}
		bean.setAllocatedTagId(singleProduct.getAllocatedTagId());
		bean.setAllocatedTagName(singleProduct.getAllocatedTagName());
		bean.setAssetUniqueCodeOrMacId(singleProduct.getAssetUniqueCodeOrMacId());
		bean.setProductName(singleProduct.getProductName());
		bean.setProductAllocationId(singleProduct.getProductAllocationId());
		bean.setProductId(singleProduct.getProductId());

		return bean; 
	}

	//------------------------------- Delete Product Details from Database------------------------------
	
	/**
	 * Delete Product Details from Database
	 * @author Pratik chaudhari
	 * @param PkID
	 * @return String
	 */
	@DeleteMapping("/product/delete-product/{productId}")
	public String deleteProduct(@PathVariable(value="productId") String  PkID)
	{
		productService.deleteProduct(Long.parseLong(PkID));
		return "Product Delete Successfully...";
	}

	/**
	 * Multiple Delete Tag Details from Database
	 * @author Pratik chaudhari
	 * @param PkID
	 * @return String
	 */
	//--------------------------Multiple Delete Tag Details from Database-----------------------------
	@DeleteMapping("/product/delete-product-for-multiple")
	public String deleteAllSelecteddeleteProduct(@RequestBody List<Long> PkID)
	{
		productService.deleteProductForMultiple(PkID);
		return "All Selected Product Delete Successfully...";
	}

	
	/**
	 * use to delete All multiple product Product 
	 * @param PkID
	 * @return String
	 */
	@PostMapping("/product/delete-multipleproduct")
	public String deleteAllmultipleproductProduct(@RequestBody List<Long> PkID)
	{
		productService.multipleproductDelete(PkID);
		return "All Selected Product Delete Successfully...";
	}
	
	
	/**
	 * get Delete History
	 * @param fkUserId
	 * @param role
	 * @param assetTagName
	 * @return ProductDetailsAllocationDeleteHistory
	 */
	@GetMapping("/product/get-productallocation-deleteHistory-list")
	public List<ProductDetailsAllocationDeleteHistory> getDeleteHistory(String fkUserId,String role,String assetTagName)
	{
		return ProductAllocationDeleteHistoryservice.getDeleteHistory(fkUserId,role,assetTagName);
	}
	//------------------Get product-list-for-dropdown for filter--------------------------------------------
	/**
	 * Get product-list-for-dropdown for filter
	 * @param fkUserId
	 * @author Pratik chaudhari
	 * @param role
	 * @param category
	 * @return ProductDetailForAllocation
	 */
	@GetMapping("/product/get-product-list-for-dropdown")
	public List<ProductDetailForAllocation> getAllproductListForDrodown(String fkUserId,String role,String category)
	{		
		//System.out.println("===========role======" +role +"============ID====="+fkUserId);
		//System.out.println(productService.getAllproductListForDrodown(fkUserId, role));
		return  productService.getAllproductListForDrodown(fkUserId,role,category);		
	}
//..................................................pagination..........................
	
	
	/**
	 * Get product-list-for-dropdown for filter pagination
	 * @param pageNo
	 * @param fkUserId
	 * @param role
	 * @return ResponseProductBean
	 */
	//------------------Get product-list-for-dropdown for filter pagination-------------------------------------------	
	@GetMapping("/product/get-product-list-for-dropdown-pagination/{pageNo}")
	public Page<ResponseProductBean> getAllproductListForDrodownWithPagination(@PathVariable String pageNo,String fkUserId,String role)
	{		
		String dbListCount;
		dbListCount=productService.getdbCount();
		int count=Integer.parseInt(dbListCount);
		if(count<10)
		{
			Pageable pageable=PageRequest.of(Integer.parseInt(pageNo),count);
		}
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo),10);
		System.out.println("===========role======" +role +"============ID====="+fkUserId);
		return  productService.getAllproductListForDrodownWithPagination(fkUserId, role,pageable);		
	}
//...............................................................................................

	//----------------------------------------------------
	/**
	 * Get All Product Tag List on product-name Wise filter
	 * @author Pratik Chaudhari
	 * @param productName
	 * @param fkUserId
	 * @param role
	 * @return ProductResponseAllocationBean
	 */
	@GetMapping("/product/get-product-tag-allocation-list-from-product-name")
	public List<ProductResponseAllocationBean> getAllProductTagListOnProductNamewise(String productName,String fkUserId,String role)
	{			
		return productService.getAllProductTagListOnProductNamewise(productName,fkUserId,role);		
	}
//..................................pagination...................

//	//-----------------------Get All Product Tag List on product-name Wise filter-----------------------------
	/**
	 * Get All Product Tag List on product-name Wise filter
	 * @author Pratik Chaudhari
	 * @param pageNo
	 * @param productName
	 * @param fkUserId
	 * @param role
	 * @return ProductResponseAllocationBean
	 */
	@GetMapping("/product/get-product-tag-allocation-list-from-product-name-with-pagination/{pageNo}")
	public Page<ProductResponseAllocationBean> getAllProductTagListOnProductNamewiseWithpagination(@PathVariable String pageNo,String productName,String fkUserId,String role)
	{	
		Pageable pageable=PageRequest.of(Integer.parseInt(pageNo),10);
		return productService.getAllProductTagListOnProductNamewiseWithPagination(productName,fkUserId,role,pageable);		
	}
//.....................................................................................................................
//	
	/**
	 * get All product Tag Allocation List
	 * @author Pratik chaudhari
	 * @param String
	 * @param fkUserId
	 * @param role
	 * @return ProductResponseAllocationBean
	 */
	@GetMapping("/product/get-product-tag-allocation-list-with-pagination/{pageNo}")
	public List<ProductResponseAllocationBean> getAllproductTagAllocationList(PathVariable String ,String fkUserId,String role)
	{		
		return  productService.getAllproductTagAllocationList(fkUserId,role);		
	}
	
	
	
//,.....................................................................................................................
	//
	/**
	 * Get product-allocation for Edit
	 * @author Pratik chaudhari
	 * @param id
	 * @return ProductDetailForAllocation
	 */
		@GetMapping("/product/get-product-allocation-for-edit/{productAllocationid}")
		public ProductDetailForAllocation BeanproducttewayForEdit(@PathVariable(value="productAllocationid") Long id)
		{
			return productService.getproductAllocationForEdit(id);
		}
		
		
		/**
		 * get Product Allocation History
		 * @author Pratik chaudhari
		 * @param assetTagName
		 * @param fkUserId
		 * @param role
		 * @return ProductDetailsAllocationHistory
		 */
		@GetMapping("/product/get-product-allocation-History")
		public List<ProductDetailsAllocationHistory> getProductAllocationHistory(String assetTagName,String fkUserId,String role)
		{
			List<ProductDetailsAllocationHistory> bean= productService.getproductAllocationHistory(assetTagName,fkUserId,role);
			return bean;
		}
		
		//update product-allocation
		/**
		 * update product-allocation
		 * @author Pratik chaudhari
		 * @param allocatedProduct
		 * @return String
		 */
		@PutMapping("/product/update-product-allocation")
		public String updateproduct(@RequestBody ProductDetailForAllocation allocatedProduct)
		{
			productService.updateproductAllocation(allocatedProduct);
			return "product-allocation Updated Successfully....!";
		}	
		
		//............product bulk upload with alocation
		/**
		 * product bulk upload with alocation
		 * @param file
		 * @author Pratik chaudhari
		 * @param username
		 * @return
		 * @throws IOException
		 * @throws EncryptedDocumentException
		 * @throws InvalidFormatException
		 * @throws ParseException
		 */
		@PostMapping("/user/import-excel-file-with-product-allocation-to-database")
		public String importExcelFileBulk(@RequestParam("file") MultipartFile file,String username) throws IOException, EncryptedDocumentException, InvalidFormatException, ParseException {

			//HttpStatus status = HttpStatus.OK;
			String input = productService.addExcelFileDataToDatabase(file,username);

			return input;
			
		}
		//............
		/**
		 * product bulk upload only
		 * @author Pratik chaudhari
		 * @param file
		 * @param username
		 * @return String 
		 * @throws IOException
		 * @throws EncryptedDocumentException
		 * @throws InvalidFormatException
		 * @throws ParseException
		 */
		@PostMapping("/user/import-excel-file-with-product-bulk-upload")
		public String importExcelFileProductBulk(@RequestParam("file") MultipartFile file,String username) throws IOException, EncryptedDocumentException, InvalidFormatException, ParseException {

			//HttpStatus status = HttpStatus.OK;
			String input = productService.addExcelFileDataToDatabaseForProduct(file,username);

			return input;
		}
		//.......................................................
}