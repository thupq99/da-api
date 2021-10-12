package com.globits.da.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Vanbang;

public class EmployeeDto extends BaseObjectDto {
	@NotEmpty(message = "code khong de trong")
	@Size(min = 6, max = 10, message = "Do dai nam trong khoang 6-10")
	private String code;

	@NotEmpty(message = "name khong de trong")
	private String name;

	@Email(message = "Email khong hop le")
	private String email;

	@NotEmpty(message = "phone khong de trong")
	@Pattern(regexp = "([0-9]{10})", message = "std co 10 chu so")
	private String phone;

	@Min(value = 0, message = "age khong am")
	private int age;

	private TinhDto tinhdto;
	private HuyenDto huyendto;
	private XaDto xadto;

	private List<VanbangDto> vbs;

	public TinhDto getTinhdto() {
		return tinhdto;
	}

	public HuyenDto getHuyendto() {
		return huyendto;
	}

	public XaDto getXadto() {
		return xadto;
	}

	public List<VanbangDto> getVbs() {
		return vbs;
	}

	public void setVbs(List<VanbangDto> vbs) {
		this.vbs = vbs;
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

	public void setTinhdto(TinhDto tinhdto) {
		this.tinhdto = tinhdto;
	}

	public void setHuyendto(HuyenDto huyendto) {
		this.huyendto = huyendto;
	}

	public void setXadto(XaDto xadto) {
		this.xadto = xadto;
	}

	// ...............
	public EmployeeDto() {
		super();
	}

	public EmployeeDto(Employee entity, Boolean check) {
		if (entity != null) {
			this.setId(entity.getId());
			this.code = entity.getCode();
			this.name = entity.getName();
			this.email = entity.getEmail();
			this.phone = entity.getPhone();
			this.age = entity.getAge();
			if (check && entity.getTinh() != null)
				this.tinhdto = new TinhDto(entity.getTinh(), false);
			if (check && entity.getHuyen() != null)
				this.huyendto = new HuyenDto(entity.getHuyen(), false);
			if (check && entity.getXa() != null)
				this.xadto = new XaDto(entity.getXa(), false);

			this.vbs = new ArrayList<VanbangDto>();
			if (check && entity.getVb() != null) {
				entity.getVb().forEach(e -> this.vbs.add(new VanbangDto(e, false)));
			}

		}
	}

	public EmployeeDto(Employee entity) {
		this(entity, true);
	}

//	public EmployeeDto(Employee entity) {
//		if (entity != null) {
//			this.setId(entity.getId());
//			this.code = entity.getCode();
//			this.name = entity.getName();
//			this.email = entity.getEmail();
//			this.phone = entity.getPhone();
//			this.age = entity.getAge();
//			this.tinhdto = new TinhDto(entity.getTinh());
//			this.huyendto = new HuyenDto(entity.getHuyen());
//			this.xadto = new XaDto(entity.getXa());
//
//			this.vbs = new ArrayList<VanbangDto>();
//			if (entity.getVb() != null) {
//				entity.getVb().forEach(e -> this.vbs.add(new VanbangDto(e)));
//			}
//		}
//	}

//	public EmployeeDto(Employee entity, UUID id) {
//		if (entity != null) {
//			this.setId(entity.getId());
//			this.code = entity.getCode();
//			this.name = entity.getName();
//			this.email = entity.getEmail();
//			this.phone = entity.getPhone();
//			this.age = entity.getAge();
//			this.tinhdto = new TinhDto(entity.getTinh());
//			this.huyendto = new HuyenDto(entity.getHuyen());
//			this.xadto = new XaDto(entity.getXa());
//
//		}
//	}

	public EmployeeDto(Employee entity, int age) {
		if (entity != null) {
			this.setId(entity.getId());
			this.code = entity.getCode();
			this.name = entity.getName();
			this.email = entity.getEmail();
			this.phone = entity.getPhone();
			this.age = entity.getAge();
		}
	}

	public EmployeeDto(UUID id,
			@NotEmpty(message = "code khong de trong") @Size(min = 6, max = 10, message = "Do dai nam trong khoang 6-10") String code,
			@NotEmpty(message = "name khong de trong") String name, @Email(message = "Email khong hop le") String email,
			@NotEmpty(message = "phone khong de trong") @Pattern(regexp = "([0-9]{10})", message = "std co 10 chu so") String phone,
			@Min(value = 0, message = "age khong am") int age) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.age = age;

	}

	public EmployeeDto(UUID id,
			@NotEmpty(message = "code khong de trong") @Size(min = 6, max = 10, message = "Do dai nam trong khoang 6-10") String code,
			@NotEmpty(message = "name khong de trong") String name, @Email(message = "Email khong hop le") String email,
			@NotEmpty(message = "phone khong de trong") @Pattern(regexp = "([0-9]{10})", message = "std co 10 chu so") String phone,
			@Min(value = 0, message = "age khong am") int age, List<Vanbang> vbs) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.vbs = new ArrayList<VanbangDto>();
		if (vbs.size() > 0) {
			vbs.forEach(e -> this.vbs.add(new VanbangDto(e.getName(), e.getCode(), e.getDateCap())));
		}
	}

}
