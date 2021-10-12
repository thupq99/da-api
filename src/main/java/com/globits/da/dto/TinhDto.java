package com.globits.da.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Huyen;
import com.globits.da.domain.Tinh;

public class TinhDto extends BaseObjectDto {

	private String code;
	private String name;
	private String description;
	private String errorMessage;
	private List<HuyenDto> huyens;
	private List<EmployeeDto> empls;

	public TinhDto() {
		super();
	}

	public TinhDto(String code, String name, String description, UUID id) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.id = id;
	}

	public TinhDto(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

	public TinhDto(Tinh entity, Boolean check) {
		if (entity != null) {
			this.setId(entity.getId());
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.huyens = new ArrayList<HuyenDto>();
			if (check && entity.getHuyens() != null && entity.getHuyens().size() > 0) {
				entity.getHuyens().forEach(e -> this.huyens.add(new HuyenDto(e.getName(), e.getXas())));
			}

			this.empls = new ArrayList<EmployeeDto>();
			if (check && entity.getEmpls() != null) {
				entity.getEmpls().forEach(e -> this.empls.add(new EmployeeDto(e, false)));
//				entity.getEmpls().forEach(e -> this.empls.add(new EmployeeDto(e.getId(), e.getCode(), e.getName(),
//						e.getEmail(), e.getEmail(), e.getAge(), e.getVb())));
			}
		}

	}

	public TinhDto(Tinh entity) {
		this(entity, true);
	}

	public TinhDto(Huyen entity, UUID id) {
		if (entity != null) {
			this.setId(entity.getId());
			this.name = entity.getName();
			this.description = entity.getDescription();

		}

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<EmployeeDto> getEmpls() {
		return empls;
	}

	public void setEmpls(List<EmployeeDto> empls) {
		this.empls = empls;
	}

	public List<HuyenDto> getHuyens() {
		return huyens;
	}

	public void setHuyens(List<HuyenDto> huyens) {
		this.huyens = huyens;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
