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
@Table(name = "tbl_employee")
@XmlRootElement
public class Employee extends BaseObject {

	public static final long serialVersionUID = 1L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "age")
	private int age;

	@ManyToOne
	@JoinColumn(name = "tinh_id")
	private Tinh tinh;

	@ManyToOne
	@JoinColumn(name = "huyen_id")
	private Huyen huyen;

	@ManyToOne
	@JoinColumn(name = "xa_id")
	private Xa xa;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vanbang> vb;

	public List<Vanbang> getVb() {
		return vb;
	}

	public void setVb(List<Vanbang> vb) {
		this.vb = vb;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public int getAge() {
		return age;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Tinh getTinh() {
		return tinh;
	}

	public Huyen getHuyen() {
		return huyen;
	}

	public Xa getXa() {
		return xa;
	}

	public void setTinh(Tinh tinh) {
		this.tinh = tinh;
	}

	public void setHuyen(Huyen huyen) {
		this.huyen = huyen;
	}

	public void setXa(Xa xa) {
		this.xa = xa;
	}

}
