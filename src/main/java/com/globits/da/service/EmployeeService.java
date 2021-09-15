package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.SearchDto;

public interface EmployeeService extends GenericService<Employee, UUID>{
	public EmployeeDto saveOrUpdate(UUID id, EmployeeDto dto);
	public List<EmployeeDto> getAll();
	public Page<EmployeeDto> getPage(int pageSize, int pageIndex);
	public EmployeeDto getEmployee(UUID id);
	public String delEmployee(UUID id);
	public Page<EmployeeDto> searchByPage(SearchDto searchDto);
}
