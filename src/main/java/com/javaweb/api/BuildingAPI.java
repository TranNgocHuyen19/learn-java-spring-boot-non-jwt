package com.javaweb.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Beans.BuildingDTO;

import customexception.FieldRequiredException;

@RestController
public class BuildingAPI {
	@PostMapping(value = "/api/building/")
	public Object getBuilding(@RequestBody BuildingDTO buildingDTO) {
		// xu li duoi DB xong roi
		valiDate(buildingDTO);
		return null;
	}

	public void valiDate(BuildingDTO buildingDTO) {
		if (buildingDTO.getName() == null || buildingDTO.getName().equals("")
				|| buildingDTO.getNumberOfBasement() == null) {
			throw new FieldRequiredException("Name or number of basement is null");
		}
	}

//	@PostMapping(value="/api/building/")
//	public BuildingDTO getBuilding2(@RequestBody BuildingDTO buildingDTO) {
//		//sau khi xu ly duoi DB
//		return buildingDTO;
//	}

	@DeleteMapping(value = "/api/building/{id}/{name}")
	public void deleteBuilding(@PathVariable Integer id, @PathVariable String name,
			@RequestParam(value = "ward", required = false) String ward) {
		System.out.println("Xoá " + id + " và " + name + " ròi nè");
	}
}
