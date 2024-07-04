package com.javaweb.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Beans.BuildingDTO;

@RestController
public class BuildingAPI {
	@GetMapping(value="/api/building/")
	public BuildingDTO getBuilding(@RequestParam(value="name", required = false) String name,
							@RequestParam(value="numberOfBasement", required = false) Integer numberOfBasement,
							@RequestParam(value="ward", required = false) String ward) {
		//xu li duoi DB xong roi
		BuildingDTO result = new BuildingDTO();
		result.setName(name);
		result.setNumberOfBasement(numberOfBasement);
		result.setWard(ward);
		return result;
	}
	
	@PostMapping(value="/api/building/")
	public BuildingDTO getBuilding2(@RequestBody BuildingDTO buildingDTO) {
		//sau khi xu ly duoi DB
		return buildingDTO;
	}
	
	@DeleteMapping(value="/api/building/{id}/{name}") 
	public void deleteBuilding(@PathVariable Integer id, 
								@PathVariable String name,
								@RequestParam(value="ward", required = false) String ward) {
		System.out.println("Xoá " + id + " và " + name + " ròi nè");
	}
}
