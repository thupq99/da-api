package com.globits.da.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Xa;

public class XaDto extends BaseObjectDto {

	private String code;
	private String name;
	private String description;
	private String errorMessage;
	private HuyenDto huyendto;
	private List<EmployeeDto> empls;

	public XaDto() {
		super();
	}

	public XaDto(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public XaDto(UUID id, String code, String name, String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;

	}

	public XaDto(Xa entity, Boolean check) {
		if (entity != null) {
			this.setId(entity.getId());
			this.code = entity.getCode();
			this.name = entity.getName();
			this.description = entity.getDescription();
			if (check && entity.getHuyen() != null)
				this.huyendto = new HuyenDto(entity.getHuyen(), false);

			this.empls = new ArrayList<EmployeeDto>();
			if (check && entity.getEmpls() != null) {
				entity.getEmpls().forEach(e -> this.empls.add(new EmployeeDto()));
			}
		}

	}

	// => check=false ra moi thong tin xa(ko empl, tinh, huyen)
	public XaDto(Xa entity) {
		this(entity, true);
	}

	public XaDto(Xa entity, UUID id) {
		if (entity != null) {
			this.setId(entity.getId());
			this.name = entity.getName();
			this.description = entity.getDescription();
		}

	}

	public List<EmployeeDto> getEmpls() {
		return empls;
	}

	public void setEmpls(List<EmployeeDto> empls) {
		this.empls = empls;
	}

	public HuyenDto getHuyendto() {
		return huyendto;
	}

	public void setHuyendto(HuyenDto huyendto) {
		this.huyendto = huyendto;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
