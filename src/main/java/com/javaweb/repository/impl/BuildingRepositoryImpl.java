package com.javaweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingEntityConverter;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;

@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private BuildingEntityConverter buildingEntityConverter;
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		//1. JPQL: JPA Query Language
		String sql = "FROM BuildingEntity b WHERE b.name like '%building%'";
		Query query = entityManager.createQuery(sql, BuildingEntity.class);
		
		//2. SQL Native
//		String sql = "SELECT * FROM building b";
//		Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
		return query.getResultList();
	}

	@Override
	@Transactional
	public void insert(BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = buildingEntityConverter.toBuildingEntity(buildingRequestDTO);
		entityManager.persist(buildingEntity);
	}
	
	@Override
	@Transactional
	public void update(BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = buildingEntityConverter.toBuildingEntity(buildingRequestDTO);
		buildingEntity.setId(1L);
		entityManager.merge(buildingEntity);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
		entityManager.remove(buildingEntity);
	}

}
