package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.ulti.MapUlti;

@Component
public class BuildingSearchBuilderConverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
				.setName(MapUlti.getObject(params, "name", String.class))
				.setFloorArea(MapUlti.getObject(params, "floorArea", Long.class))
				.setWard(MapUlti.getObject(params, "ward", String.class))
				.setStreet(MapUlti.getObject(params, "street", String.class))
				.setDistrictId(MapUlti.getObject(params, "districtId", Long.class))
				.setNumberOfBasement(MapUlti.getObject(params, "numbeOfBasement", Integer.class))
				.setTypeCode(typeCode)
				.setManagerName(MapUlti.getObject(params, "managerName", String.class))
				.setManagerPhoneNumber(MapUlti.getObject(params, "managerPhoneNumber", String.class))
				.setRentPriceFrom(MapUlti.getObject(params, "rentPriceFrom", Long.class))
				.setRentPriceTo(MapUlti.getObject(params, "rentPriceTo", Long.class))
				.setAreaFrom(MapUlti.getObject(params, "areaFrom", Long.class))
				.setAreaTo(MapUlti.getObject(params, "areaTo", Long.class))
				.setStaffId(MapUlti.getObject(params, "staffId", Long.class))
				.build();										
		return buildingSearchBuilder;
	}
}
