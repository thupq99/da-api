package com.globits.da.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_huyen")
@XmlRootElement
public class Huyen extends BaseObject {

	public static final long serialVersionUID = 1L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne // (cascade = CascadeType.REMOVE)
	@JoinColumn(name = "tinh_id")
	private Tinh tinh;

	@OneToMany(mappedBy = "huyen", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Xa> xas;

	@OneToMany(mappedBy = "huyen", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Employee> empls;

	public List<Employee> getEmpls() {
		return empls;
	}

	public void setEmpls(List<Employee> empls) {
		this.empls = empls;
	}

	public String getName() {
		return name;
	}

	public List<Xa> getXas() {
		return xas;
	}

	public void setXas(List<Xa> xas) {
		this.xas = xas;
	}

	public String getDescription() {
		return description;
	}

	public Tinh getTinh() {
		return tinh;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTinh(Tinh tinh) {
		this.tinh = tinh;
	}

}
