package com.embel.asset.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.entity.AssetTrackingEntity;
import com.embel.asset.entity.AssetTrackingEntityHoursBackup;
import com.embel.asset.repository.AssetTrackingHourBackupRepo;
import com.embel.asset.repository.AssetTrackingRepository;

@Service
public class AssetTrackinghoursBackupService {
	@Autowired
	AssetTrackingHourBackupRepo AssetTrackingHourBackuprepo;
	@Autowired
	AssetTrackingRepository assettrakingRepo;

	public void AssetTrackingHourslyBackup() {

		AssetTrackingHourBackuprepo.deleteAll();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime currentdate = LocalDateTime.now();
		LocalDateTime formdate = currentdate.minusHours(1);

		AssetTrackingEntityHoursBackup hbean = new AssetTrackingEntityHoursBackup();
		List<AssetTrackingEntity> list = assettrakingRepo.getHourBackupAssetTrackingDetails(formdate, currentdate);
		System.out.println(list);
		for (int i = 0; i < list.size(); i++) {
			hbean.setAssetGatewayMac_Id(list.get(i).getAssetGatewayMac_Id());
			hbean.setAssetGatewayName(list.get(i).getAssetGatewayName());
			hbean.setAssetTagName(list.get(i).getAssetTagName());
			hbean.setBarcodeSerialNumber(list.get(i).getBarcodeSerialNumber());
			hbean.setBattryPercentage(list.get(i).getBattryPercentage());
			hbean.setDate(list.get(i).getDate());
			hbean.setDispatchTime(list.get(i).getDispatchTime());
			hbean.setEntryTime(list.get(i).getEntryTime());
			hbean.setExistTime(list.get(i).getExistTime());
			hbean.setImeiNumber(list.get(i).getImeiNumber());
			hbean.setLatitude(list.get(i).getLatitude());
			hbean.setLongitude(list.get(i).getLongitude());
			hbean.setTagCategoty(list.get(i).getTagCategoty());
			hbean.setTagEntryLocation(list.get(i).getTagEntryLocation());
			hbean.setTagExistLocation(list.get(i).getTagExistLocation());
			hbean.setDispatchTime(list.get(i).getDispatchTime());
			hbean.setTrackingId(list.get(i).getTrackingId());
			hbean.setUniqueNumberMacId(list.get(i).getUniqueNumberMacId());

			AssetTrackingHourBackuprepo.save(hbean);

		}

	}

}
