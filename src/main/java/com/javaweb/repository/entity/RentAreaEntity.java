package com.javaweb.repository.entity;

import java.time.LocalDateTime;

public class RentAreaEntity {
	private Long id;
	private Integer value;
	private Long buildingId;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private String createdBy;
	private String modifiedBy;
	public RentAreaEntity() {
		// TODO Auto-generated constructor stub
	}
	public RentAreaEntity(Long id, Integer value, Long buildingId, LocalDateTime createdDate,
			LocalDateTime modifiedDate, String createdBy, String modifiedBy) {
		super();
		this.id = id;
		this.value = value;
		this.buildingId = buildingId;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Long getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
