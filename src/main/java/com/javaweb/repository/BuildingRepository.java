package com.javaweb.repository;

import java.util.List;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder);
	void insert(BuildingRequestDTO buildingRequestDTO);
	void update(BuildingRequestDTO buildingRequestDTO);
	void delete(Long id);
}
