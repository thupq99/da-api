package com.globits.da.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Huyen;
import com.globits.da.domain.Xa;

public class HuyenDto extends BaseObjectDto {

	private String code;
	private String name;
	private String description;
	private String errorMessage;
	private List<XaDto> xas;
	private List<EmployeeDto> empls;
	private TinhDto tinhdto;

	public HuyenDto() {
		super();
	}

	public HuyenDto(String name, String description, UUID id) {
		this.name = name;
		this.description = description;
		this.id = id;
	}

	public HuyenDto(Huyen entity, Boolean check) {

		if (entity != null) {
			this.setId(entity.getId());
			this.code = entity.getCode();
			this.name = entity.getName();
			this.description = entity.getDescription();

			if (entity.getTinh() != null) {
				this.tinhdto = new TinhDto(entity.getTinh().getCode(), entity.getTinh().getName(),
						entity.getTinh().getDescription());
			}

			this.xas = new ArrayList<XaDto>();
			if (check && entity.getXas() != null) {
				for (int i = 0; i < entity.getXas().size(); ++i) {
					this.xas.add(new XaDto(entity.getXas().get(i).getId(), entity.getXas().get(i).getCode(),
							entity.getXas().get(i).getName(), entity.getXas().get(i).getDescription()));
				}
			}

			this.empls = new ArrayList<EmployeeDto>();
			if (check && entity.getEmpls() != null) {
				entity.getEmpls().forEach(e -> this.empls.add(new EmployeeDto(e.getId(), e.getCode(), e.getName(),
						e.getEmail(), e.getEmail(), e.getAge(), e.getVb())));
			}

		}

	}

	// => check=false ra moi thong tin huyen: co thong tin 1 tinh(ko empl)
	public HuyenDto(Huyen entity) {
		this(entity, true);
	}

	public HuyenDto(String name, List<Xa> xas) {

		this.name = name;
		this.xas = new ArrayList<XaDto>();
		for (int i = 0; i < xas.size(); ++i) {
			this.xas.add(new XaDto(xas.get(i).getName(), xas.get(i).getDescription()));
		}
	}

	public HuyenDto(Huyen entity, UUID id) {

		if (entity != null) {
			this.setId(entity.getId());
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.xas = new ArrayList<XaDto>();
			if (entity.getXas() != null) {
				for (int i = 0; i < entity.getXas().size(); ++i) {
					this.xas.add(new XaDto(entity.getXas().get(i), entity.getXas().get(i).getId()));
				}
			}
			this.empls = new ArrayList<EmployeeDto>();
			if (entity.getEmpls() != null) {
				entity.getEmpls().forEach(e -> this.empls.add(new EmployeeDto(e, e.getAge())));
			}

		}

	}

	public List<EmployeeDto> getEmpls() {
		return empls;
	}

	public void setEmpls(List<EmployeeDto> empls) {
		this.empls = empls;
	}

	public List<XaDto> getXas() {
		return xas;
	}

	public void setXas(List<XaDto> xas) {
		this.xas = xas;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public TinhDto getTinhdto() {
		return tinhdto;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTinhdto(TinhDto tinhdto) {
		this.tinhdto = tinhdto;
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
