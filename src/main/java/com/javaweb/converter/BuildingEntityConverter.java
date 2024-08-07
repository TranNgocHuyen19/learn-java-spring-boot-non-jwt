package com.javaweb.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;

@Component
public class BuildingEntityConverter {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private DistrictRepository districtRepository;

	public BuildingEntity toBuildingEntity(BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = modelMapper.map(buildingRequestDTO, BuildingEntity.class);
		if (buildingRequestDTO.getDistrictId() != null) {
//			DistrictEntity districtEntity = new DistrictEntity();
//			districtEntity.setId(buildingRequestDTO.getDistrictId()); 
			
			buildingEntity.setDistrict(districtRepository.findById(buildingRequestDTO.getDistrictId()).get());
			
		}
		return buildingEntity;
	}
}
