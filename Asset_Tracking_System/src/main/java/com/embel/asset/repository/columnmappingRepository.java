package com.embel.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.embel.asset.bean.ResponseColumnMappingbean;
import com.embel.asset.entity.columnmapping;

@Repository
public interface columnmappingRepository extends JpaRepository<columnmapping, Long> 
{


	@Query(value="SELECT * FROM column_mapping c WHERE c.allocated_column_db=?1",nativeQuery=true)
	columnmapping getlabelbycolumn(String str);

	
	@Query(value="SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='asset_tracking_details' AND column_name LIKE 'lb%'",nativeQuery=true)
	List<String> getlabelsList();


	@Query(value="SELECT c.column_name FROM column_mapping c WHERE c.column_name=?1",nativeQuery=true)
	String BycolunmnName(String allocatedColumn_db);


	@Query(value="SELECT * FROM column_mapping c WHERE c.pk_id=?1",nativeQuery=true)
	columnmapping getbyId(String id);

	@Query(value="SELECT * FROM column_mapping c WHERE c.category=?1",nativeQuery=true)
	List<columnmapping> getallList(String category);

	@Query(value="SELECT * FROM column_mapping c WHERE c.column_name=?1",nativeQuery=true)
	List<columnmapping> getcolumn(String columnname);

	@Query(value="SELECT c.allocated_column_ui FROM column_mapping c",nativeQuery=true)
	List<String> getUicolumnList();

	@Query(value="SELECT c.column_name FROM column_mapping c WHERE c.allocated_column_ui=?1",nativeQuery=true)
	String getdbcolumn(String columnname);

	
	@Query(value="SELECT c.column_name FROM column_mapping c WHERE c.allocated_column_ui=?1",nativeQuery=true)
	String getColumnNameOnuiColumnName(String columnname);


	@Query(value="SELECT New com.embel.asset.bean.ResponseColumnMappingbean(c.columnName,c.allocatedColumn_ui,c.unit) FROM columnmapping c")
	List<ResponseColumnMappingbean> getColumnNameWithuiColumnName();


	@Query(value="SELECT c.allocated_column_ui FROM column_mapping c WHERE c.category=?1",nativeQuery=true)
	List<String> getUicolumnListforSuperAdmin(String category);


	@Query(value="SELECT c.unit FROM column_mapping c WHERE c.allocated_column_ui=?1",nativeQuery=true)
	String getUnit(String columns);





//	@Query(value="SELECT c.allocated_column_db FROM column_mapping c WHERE c.allocated_column_ui=?1",nativeQuery=true)
//	String getparameterName(String columnname);
	
	//columnmapping getlabelbycolumn(Object str);


//	@Query(value="",nativeQuery=true)
//	columnmapping getListofNONAllocatedColumns();
	

}
