package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Category;
import com.globits.da.domain.Employee;

public class EmployeeDto extends BaseObjectDto{
//public class EmployeeDto {
	private String code;	
	private String name;
	private String email;
	private String phone;
	private int age;
	
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
	public EmployeeDto() {
		super();
	}
	public EmployeeDto(Employee entity) {
		if(entity!=null) {
			this.setId(entity.getId());
			this.code = entity.getCode();
			this.name = entity.getName();
			this.email = entity.getEmail();
			this.phone = entity.getPhone();
			this.age = entity.getAge();
		}		
	}
}
