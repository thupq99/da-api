package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_vanbang")
@XmlRootElement
public class Vanbang extends BaseObject {

	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;

	@Column(name = "date_cap")
	private String dateCap;

	@ManyToOne // (cascade = CascadeType.REMOVE)
	@JoinColumn(name = "tinh_id")
	private Tinh tinh;

	@ManyToOne // (cascade = CascadeType.REMOVE)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getDateCap() {
		return dateCap;
	}

	public Tinh getTinh() {
		return tinh;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDateCap(String dateCap) {
		this.dateCap = dateCap;
	}

	public void setTinh(Tinh tinh) {
		this.tinh = tinh;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
