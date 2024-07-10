package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

//Data access layer
@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "sapassword";
	
	@Override
	public List<BuildingEntity> findAll(String name, Long districtId ) {
		StringBuilder sql = new StringBuilder("select * from building b where 1 = 1 ");
		if(name != null && !name.equals("")) {
			sql.append(" and b.name like '%" + name + "%' ");
		}
		
		if(districtId != null) {
			sql.append(" and b.districtid = " + districtId + " ");
		}
		List<BuildingEntity> result = new ArrayList<BuildingEntity>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();) {
			while(rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setName(rs.getString("name"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
				building.setNumberOfBasement(rs.getInt("numberofbasement"));
				result.add(building);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return result;
	}

}
