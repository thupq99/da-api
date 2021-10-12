package com.globits.da.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_tinh")
@XmlRootElement
public class Tinh extends BaseObject {

	public static final long serialVersionUID = 1L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "tinh", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Huyen> huyens;

	@OneToMany(mappedBy = "tinh", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Employee> empls;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public List<Employee> getEmpls() {
		return empls;
	}

	public void setEmpls(List<Employee> empls) {
		this.empls = empls;
	}

	public List<Huyen> getHuyens() {
		return huyens;
	}

	public void setHuyens(List<Huyen> huyens) {
		this.huyens = huyens;
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

}
