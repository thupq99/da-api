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
@Table(name = "tbl_xa")
@XmlRootElement
public class Xa extends BaseObject {

	public static final long serialVersionUID = 1L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "huyen_id")
	private Huyen huyen;

	@OneToMany(mappedBy = "xa", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Employee> empls;

	public List<Employee> getEmpls() {
		return empls;
	}

	public void setEmpls(List<Employee> empls) {
		this.empls = empls;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Huyen getHuyen() {
		return huyen;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHuyen(Huyen huyen) {
		this.huyen = huyen;
	}

}
