package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Vanbang;

public class VanbangDto extends BaseObjectDto {

	private String name;

	private String code;

	private String dateCap;

	private String errorMessage;

	private TinhDto tinhdto;

	private EmployeeDto employeedto;

	// errormeaseage

	public VanbangDto() {
		super();
	}

	public VanbangDto(String name, String code, String dateCap, TinhDto tinhdto, EmployeeDto employeedto) {
		super();
		this.name = name;
		this.code = code;
		this.dateCap = dateCap;
		this.tinhdto = tinhdto;
		this.employeedto = employeedto;
	}

	public VanbangDto(Vanbang entity, Boolean check) {
		if (entity != null) {
			this.setId(entity.getId());
			this.code = entity.getCode();
			this.name = entity.getName();
			if (entity.getTinh() != null) {
				this.tinhdto = new TinhDto(entity.getTinh().getCode(), entity.getTinh().getName(),
						entity.getTinh().getDescription(), entity.getTinh().getId());
			}

			if (check && entity.getEmployee() != null)
				this.employeedto = new EmployeeDto(entity.getEmployee(), false);

		}
	}

	public VanbangDto(Vanbang entity) {
		this(entity, true);
	}

	public VanbangDto(String name, String code, String dateCap) {
		super();
		this.name = name;
		this.code = code;
		this.dateCap = dateCap;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getDateCap() {
		return dateCap;
	}

	public TinhDto getTinhdto() {
		return tinhdto;
	}

	public EmployeeDto getEmployeedto() {
		return employeedto;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDateCap(String dateCap) {
		this.dateCap = dateCap;
	}

	public void setTinhdto(TinhDto tinhdto) {
		this.tinhdto = tinhdto;
	}

	public void setEmployeedto(EmployeeDto employeedto) {
		this.employeedto = employeedto;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
