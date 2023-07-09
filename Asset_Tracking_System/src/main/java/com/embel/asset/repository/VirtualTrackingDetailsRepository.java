package com.embel.asset.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.embel.asset.entity.VirtualTrackingDetails;
@Repository
public interface VirtualTrackingDetailsRepository extends JpaRepository<VirtualTrackingDetails, Long>{

	

	@Query(value ="SELECT v.tracking_id FROM virtual_tracking_details v WHERE v.asset_tag_name=?1",nativeQuery = true)
	String getIdByTagName(String assetTagName);



	@Query(value="SELECT atd.asset_tag_name FROM virtual_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atd.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.admin_id=?1 AND atd.currentdate BETWEEN ?2 AND ?3 AND atg.asset_tag_name=?4",nativeQuery=true)
	String findByCurrentDate(long adminid, String fromdate, String todate, String tagname);


	@Query(value="SELECT atd.asset_tag_name FROM virtual_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atd.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.admin_id=?1 AND atd.currentdate BETWEEN ?2 AND ?3 AND atg.asset_tag_name=?4",nativeQuery=true)
	String finddatabetweenDatexl(long adminid, String fromdate, String todate, String tagname);


	@Query(value="SELECT atd.asset_tag_name FROM virtual_tracking_details atd,asset_tag_generation atg WHERE atd.barcode_or_serial_number=atg.asset_barcode_number_or_serial_number AND atd.unique_code_or_mac_id=atg.asset_unique_code_or_mac_id AND atg.admin_id=?1 AND atd.currentdate=?2 AND atg.asset_tag_name=?3",nativeQuery=true)
	String findBydate(long adminid, LocalDateTime twodaysearlierDate, String tagname);


	
	@Query(value="SELECT * FROM virtual_tracking_details v,asset_gateway_creation c WHERE v.asset_gateway_mac_id=c.gateway_unique_code_or_mac_id AND c.admin_id=?1 AND v.currentdate=?2 AND c.gateway_name=?3",nativeQuery=true)
	String findByCurrentDate(long adminid, LocalDateTime currentdate, String tagname);


	@Query(value="SELECT v.asset_gateway_name FROM virtual_tracking_details v,asset_gateway_creation c WHERE v.asset_gateway_mac_id=c.gateway_unique_code_or_mac_id AND c.admin_id=?1 AND v.currentdate BETWEEN ?2 AND ?3 AND c.gateway_name=?4 GROUP BY v.asset_gateway_name",nativeQuery=true)
	String findByCurrentDateforGateway(long adminid, String fromdate, String todate, String tagname);


	@Query(value="SELECT v.asset_gateway_name FROM virtual_tracking_details v,asset_gateway_creation c WHERE v.asset_gateway_mac_id=c.gateway_unique_code_or_mac_id AND c.admin_id=?1 AND v.currentdate BETWEEN ?2 AND ?3 AND c.gateway_name=?4 GROUP BY v.asset_gateway_name",nativeQuery=true)
	String finddatabetweenDatexxl(long adminid, String string, String string2, String getwayName);
	
	
	
	
	

}
