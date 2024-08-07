package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.ulti.ConnectJDBCUlti;

//Data access layer
@Repository
public class JDBCBuildingRepositoryImpl implements BuildingRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder join) {
		Long districtId = buildingSearchBuilder.getDistrictId();
		if (districtId != null) {
			join.append(" INNER JOIN district d ON d.id = b.districtId ");
		}

		Long staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			join.append(" INNER JOIN assignmentbuilding ad ON ad.buildingid = b.id ");
		}

//		String rentAreaFrom = (String)params.get("areaFrom");
//		String rentAreaTo = (String)params.get("areaTo");
//		if(StringUlti.checkString(rentAreaFrom) || StringUlti.checkString(rentAreaTo)) {
//			join.append(" INNER JOIN rentarea ra ON ra.buildingid = b.id ");
//		}

		List<String> typeCode = buildingSearchBuilder.getTypeCode();

		if (typeCode != null && typeCode.size() != 0) {
			join.append(" INNER JOIN buildingrenttype brt ON brt.buildingid = b.id ");
			join.append(" INNER JOIN renttype rt ON rt.id = brt.renttypeid ");
		}
	}

	public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
//		for (Map.Entry<String, Object> it : params.entrySet()) {
//			if (!it.getKey().equals("districtId") && !it.getKey().equals("staffId") && !it.getKey().startsWith("area")
//					&& !it.getKey().equals("typeCode") && !it.getKey().startsWith("rent")) {
//				String value = it.getValue().toString();
//				if (StringUlti.checkString(value)) {
//					if (NumberUlti.isNumber(value)) {
//						where.append(" AND b." + it.getKey() + "=" + value);
//					} else {
//						where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%'");
//					}
//				}
//			}
//		}
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field item: fields) {
				item.setAccessible(true); //Cho phép truy cập các trường private
				String fieldName = item.getName();
				if (!fieldName.equals("districtId") && !fieldName.equals("staffId") && !fieldName.startsWith("area")
						&& !fieldName.equals("typeCode") && !fieldName.startsWith("rent")) {
					Object value = item.get(buildingSearchBuilder);
					if (value != null) {
						if (item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b." + fieldName + "=" + value);
						} else if(item.getType().getName().equals("java.lang.String")) {
							where.append(" AND b." + fieldName + " LIKE '%" + value + "%'");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		Long districtId = buildingSearchBuilder.getDistrictId();
		if (districtId != null) {
			where.append(" AND d.id = " + districtId);
		}

		Long staffId =buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			where.append(" AND ad.staffid = " + staffId);
		}

		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		if (rentAreaFrom != null || rentAreaTo != null) {
			where.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE b.id = ra.buildingid ");
			if (rentAreaFrom != null) {
				where.append(" AND ra.value" + ">=" + rentAreaFrom);
			}
			if (rentAreaTo != null) {
				where.append(" AND ra.value" + "<=" + rentAreaTo);
			}
			where.append(") ");
		}

		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		if (rentPriceFrom != null || rentPriceTo != null) {
			if (rentPriceFrom != null) {
				where.append(" AND b.rentprice" + ">=" + rentPriceFrom);
			}
			if (rentPriceTo != null) {
				where.append(" AND b.rentprice" + "<=" + rentPriceTo);
			}
		}

		// java 7
//		if (typeCode != null && typeCode.size() != 0) {
//			List<String> code = new ArrayList<String>();
//			for (String item : typeCode) {
//				code.add("'" + item + "'");
//			}
//			where.append(" AND rt.code IN(" + String.join(",", code) + ") ");
//		}
		//java 8
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			where.append(" AND (");
			String sql = typeCode
					.stream()
					.map(it -> "rt.code LIKE " + "'%" + it + "%'")
					.collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}
	}
 
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.street, b.ward, b.districtid, "
				+ "b.numberofbasement, b.floorarea, b.rentprice, b.managername, b.managerphonenumber, "
				+ "b.servicefee, b.brokeragefee FROM building b");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id");
		sql.append(where);
		List<BuildingEntity> result = new ArrayList<BuildingEntity>();
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
	}

	@Override
	public void insert(BuildingRequestDTO buildingRequestDTO) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(BuildingRequestDTO buildingRequestDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
