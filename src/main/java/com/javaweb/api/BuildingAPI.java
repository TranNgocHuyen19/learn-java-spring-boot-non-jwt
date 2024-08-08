package com.javaweb.api;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

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

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.converter.BuildingEntityConverter;
import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Transactional
@RestController
@PropertySource("classpath:application.properties")
public class BuildingAPI {
	//Presentation Layer
	@Autowired //nếu không xài cái này mà khai báo interface nó sẽ không tìm
				//ra hàm findAll của lớp implement interface
	private BuildingService buildingService;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private BuildingEntityConverter buildingEntityConverter;
	
	@Value("$tnh.xinhdep")
	private String data;
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params,
										@RequestParam(name="typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}
	
	@GetMapping(value = "/api/building/{name}/{street}")
	public BuildingDTO getBuildingById(@PathVariable String name, @PathVariable String street) {
		BuildingDTO result = new BuildingDTO();
		List<BuildingEntity> buildingEntity = buildingRepository.findByNameContainingAndStreet(name, street);
		return result;
	}

//	@PostMapping(value = "/api/building")
//	public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
//		buildingService.insert(buildingRequestDTO);
//		System.out.println("Insert successfully");
//	}
//	
	@PutMapping(value = "/api/building")
	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = buildingRepository.findById(buildingRequestDTO.getId()).get();
		buildingEntity = buildingEntityConverter.toBuildingEntity(buildingRequestDTO);
		buildingRepository.save(buildingEntity);
		System.out.println("Update successfully");
	}
//
	@DeleteMapping(value = "/api/building/{ids}")
	public void deleteBuilding(@PathVariable Long[] ids) {
		buildingRepository.deleteByIdIn(ids);
	}
}
