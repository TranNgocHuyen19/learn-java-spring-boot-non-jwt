package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.ulti.ConnectJDBCUlti;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {
	@Override
	public List<RentAreaEntity> getValueByBuildingId(Long id) {
		String sql = "SELECT ra.value FROM rentarea ra WHERE ra.buildingid = " + id + ";";
		List<RentAreaEntity> rentAreas = new ArrayList<RentAreaEntity>();
		try (Connection conn = ConnectJDBCUlti.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				RentAreaEntity rentAreaEntity = new RentAreaEntity();
				rentAreaEntity.setValue(rs.getInt("value"));
				rentAreas.add(rentAreaEntity);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentAreas;
	}

}
