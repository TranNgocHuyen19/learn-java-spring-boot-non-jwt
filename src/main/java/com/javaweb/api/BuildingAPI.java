package com.javaweb.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
	//Presentation Layer
	@Autowired //nếu không xài cái này mà khai báo interface nó sẽ không tìm
				//ra hàm findAll của lớp implement interface
	private BuildingService buildingService;
	
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam(name="name", required = false) String name,
										@RequestParam(name="districtid", required = false) Long district ) {
		List<BuildingDTO> result = buildingService.findAll(name, district);
		return result;
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
