package com.javaweb.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Beans.BuildingDTO;
import com.javaweb.Beans.ErrorResponseDTO;

import customexception.FieldRequiredException;

@RestController
public class BuildingAPI {
	@PostMapping(value="/api/building/")
	public Object getBuilding(@RequestBody BuildingDTO buildingDTO) {
		//xu li duoi DB xong roi
		try {
			valiDate(buildingDTO);
		} catch (FieldRequiredException e) {
			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
			errorResponseDTO.setError(e.getMessage());
			List<String> details = new ArrayList<String>();
			details.add("Check name or numberOfBasement again because there is null!!!");
			errorResponseDTO.setDetail(details);
			return errorResponseDTO;
		}
		return null;
	}
	
	public void valiDate(BuildingDTO buildingDTO) throws FieldRequiredException {
		if(buildingDTO.getName() == null || buildingDTO.getName().equals("") 
				|| buildingDTO.getNumberOfBasement() == null) {
			throw new FieldRequiredException("Name or number of basement is null");
		}
	}
	
//	@PostMapping(value="/api/building/")
//	public BuildingDTO getBuilding2(@RequestBody BuildingDTO buildingDTO) {
//		//sau khi xu ly duoi DB
//		return buildingDTO;
//	}
	
	@DeleteMapping(value="/api/building/{id}/{name}") 
	public void deleteBuilding(@PathVariable Integer id, 
								@PathVariable String name,
								@RequestParam(value="ward", required = false) String ward) {
		System.out.println("Xoá " + id + " và " + name + " ròi nè");
	}
}
