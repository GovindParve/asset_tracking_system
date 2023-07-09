package com.embel.asset.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embel.asset.dto.IndoorImageDto;
import com.embel.asset.entity.IndoorImages;
import com.embel.asset.repository.IndoorImageRepository;

@Service
public class IndoorService {

	@Autowired
	IndoorImageRepository indoorRepo;

	/**
	 * add Admin Wise Indoor Image
	 * 
	 * @author Pratik chaudhari
	 * @param indoorimagedto
	 * @return String
	 */
	public String addAdminWiseIndoorImage(IndoorImageDto indoorimagedto) {

		IndoorImages imagobj = new IndoorImages();
		imagobj.setFkadminId(indoorimagedto.getFkadminId());
		imagobj.setImage(indoorimagedto.getImage());
		imagobj.setIndoorImageId(indoorimagedto.getIndoorImageId());

		indoorRepo.save(imagobj);
		return "saved";
	}

	/**
	 * use to get All List
	 * 
	 * @author Pratik chaudhari
	 * @return IndoorImages
	 */
	public List<IndoorImages> getlist() {
		return indoorRepo.getAllList();

	}

	/**
	 * use to get Image
	 * 
	 * @author Pratik chaudhari
	 * @param id
	 * @return IndoorImages
	 */
	public Optional<IndoorImages> getImage(long id) {

		return indoorRepo.getImageAdminId(id);
	}

	/**
	 * update Image
	 * 
	 * @author Pratik chaudhari
	 * @param indoorimagedto
	 * @return IndoorImages
	 */
	public IndoorImages updateImage(IndoorImageDto indoorimagedto) {

		IndoorImages imagobj = new IndoorImages();
		imagobj.setFkadminId(indoorimagedto.getFkadminId());
		imagobj.setImage(indoorimagedto.getImage());
		imagobj.setIndoorImageId(indoorimagedto.getIndoorImageId());

		return indoorRepo.save(imagobj);

	}

	/**
	 * use to delete by Id
	 * 
	 * @author Pratik chaudhari
	 * @param fkadminId
	 */
	public void deletebyId(long fkadminId) {
		indoorRepo.deletebyId(fkadminId);

	}

}
