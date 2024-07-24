package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
		BuildingDTO building = new BuildingDTO();
		building.setName(buildingEntity.getName());
		DistrictEntity districtEntity = districtRepository.findNameById(buildingEntity.getDistrictId());
		building.setAddress(buildingEntity.getStreet() + "," + buildingEntity.getWard() + ", " + districtEntity.getName());
		building.setNumberOfBasement(buildingEntity.getNumberOfBasement());
		building.setManagerName(buildingEntity.getManagerName());
		building.setManagerPhoneNumber(buildingEntity.getManagerPhoneNumber());
		building.setFloorArea(buildingEntity.getFloorArea());
		building.setRentPrice(buildingEntity.getRentPrice());
		List<RentAreaEntity> rentAreas = rentAreaRepository.getValueByBuildingId(buildingEntity.getId());
		String areaResult = rentAreas.stream()
				.map(it -> it.getValue().toString())
				.collect(Collectors.joining(","));
		building.setRentArea(areaResult);
		building.setServiceFee(buildingEntity.getServiceFee());
		building.setBrokerageFee(buildingEntity.getBrokerageFee());
		return building;
	}
	
}
