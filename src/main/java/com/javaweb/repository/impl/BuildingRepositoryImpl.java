package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.ulti.ConnectJDBCUlti;
import com.javaweb.ulti.NumberUlti;
import com.javaweb.ulti.StringUlti;

//Data access layer
@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	public static void joinTable(Map<String, Object> params, List<String> typeCode, StringBuilder join) {
		String districtId = (String) params.get("districtId");
		if (StringUlti.checkString(districtId)) {
			join.append(" INNER JOIN district d ON d.id = b.districtId ");
		}

		String staffId = (String) params.get("staffId");
		if (StringUlti.checkString(staffId)) {
			join.append(" INNER JOIN assignmentbuilding ad ON ad.buildingid = b.id ");
		}

//		String rentAreaFrom = (String)params.get("areaFrom");
//		String rentAreaTo = (String)params.get("areaTo");
//		if(StringUlti.checkString(rentAreaFrom) || StringUlti.checkString(rentAreaTo)) {
//			join.append(" INNER JOIN rentarea ra ON ra.buildingid = b.id ");
//		}

		if (typeCode != null && typeCode.size() != 0) {
			join.append(" INNER JOIN buildingrenttype brt ON brt.buildingid = b.id ");
			join.append(" INNER JOIN renttype rt ON rt.id = brt.renttypeid ");
		}
	}

	public static void queryNormal(Map<String, Object> params, StringBuilder where) {
		for (Map.Entry<String, Object> it : params.entrySet()) {
			if (!it.getKey().equals("districtId") && !it.getKey().equals("staffId") && !it.getKey().startsWith("area")
					&& !it.getKey().equals("typeCode") && !it.getKey().startsWith("rent")) {
				String value = it.getValue().toString();
				if (StringUlti.checkString(value)) {
					if (NumberUlti.isNumber(value)) {
						where.append(" AND b." + it.getKey() + "=" + value);
					} else {
						where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%'");
					}
				}
			}
		}
	}

	public static void querySpecial(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
		String districtId = (String) params.get("districtId");
		if (StringUlti.checkString(districtId)) {
			where.append(" AND d.id = " + districtId);
		}

		String staffId = (String) params.get("staffId");
		if (StringUlti.checkString(staffId)) {
			where.append(" AND ad.staffid = " + staffId);
		}

		String rentAreaFrom = (String) params.get("areaFrom");
		String rentAreaTo = (String) params.get("areaTo");
		if (StringUlti.checkString(rentAreaFrom) || StringUlti.checkString(rentAreaTo)) {
			where.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE b.id = ra.buildingid ");
			if (StringUlti.checkString(rentAreaFrom)) {
				where.append(" AND ra.value" + ">=" + rentAreaFrom);
			}
			if (StringUlti.checkString(rentAreaTo)) {
				where.append(" AND ra.value" + "<=" + rentAreaTo);
			}
			where.append(") ");
		}

		String rentPriceFrom = (String) params.get("rentPriceFrom");
		String rentPriceTo = (String) params.get("rentPriceTo");
		if (StringUlti.checkString(rentPriceFrom) || StringUlti.checkString(rentPriceTo)) {
			if (StringUlti.checkString(rentPriceFrom)) {
				where.append(" AND b.rentprice" + ">=" + rentPriceFrom);
			}
			if (StringUlti.checkString(rentPriceTo)) {
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
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.street, b.ward, b.districtid, "
				+ "b.numberofbasement, b.floorarea, b.rentprice, b.managername, b.managerphonenumber, "
				+ "b.servicefee, b.brokeragefee FROM building b");
		joinTable(params, typeCode, sql);
		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		queryNormal(params, where);
		querySpecial(params, typeCode, where);
		where.append(" GROUP BY b.id");
		sql.append(where);
		List<BuildingEntity> result = new ArrayList<BuildingEntity>();
		try (Connection conn = ConnectJDBCUlti.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setId(rs.getLong("b.id"));
				building.setName(rs.getString("b.name"));
				building.setStreet(rs.getString("b.street"));
				building.setWard(rs.getString("b.ward"));
				building.setDistrictId(rs.getLong("b.districtid"));
				;
				building.setNumberOfBasement(rs.getInt("b.numberofbasement"));
				building.setFloorArea(rs.getInt("b.floorarea"));
				building.setRentPrice(rs.getDouble("b.rentprice"));
				building.setManagerName(rs.getString("b.managername"));
				building.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
				building.setServiceFee(rs.getString("b.servicefee"));
				building.setBrokerageFee(rs.getString("b.brokeragefee"));
				result.add(building);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return result;
	}

}
