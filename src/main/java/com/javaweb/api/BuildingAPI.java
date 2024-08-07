package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.service.BuildingService;

@RestController
@PropertySource("classpath:application.properties")
public class BuildingAPI {
	//Presentation Layer
	@Autowired //nếu không xài cái này mà khai báo interface nó sẽ không tìm
				//ra hàm findAll của lớp implement interface
	private BuildingService buildingService;
	
	@Value("$tnh.xinhdep")
	private String data;
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params,
										@RequestParam(name="typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}

	@PostMapping(value = "/api/building")
	public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		buildingService.insert(buildingRequestDTO);
		System.out.println("Insert successfully");
	}
	
	@PutMapping(value = "/api/building")
	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		buildingService.update(buildingRequestDTO);
		System.out.println("Update successfully");
	}

	@DeleteMapping(value = "/api/building/{id}")
	public void deleteBuilding(@PathVariable Long id) {
		buildingService.delete(id);
		System.out.println("Delete successfully");
	}
}
