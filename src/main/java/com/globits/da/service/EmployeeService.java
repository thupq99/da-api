package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.OutputDto.EmployeeByDate;
import com.globits.OutputDto.EmployeeVb;
import com.globits.core.service.GenericService;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.SearchDateSearchDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.dto.search.SearchEmplbyTinhDto;

public interface EmployeeService extends GenericService<Employee, UUID>{
	
	public EmployeeDto saveOrUpdate(UUID id, EmployeeDto dto);
	public List<EmployeeDto> getAll();
	public Page<EmployeeDto> getPage(int pageSize, int pageIndex);
	public EmployeeDto getEmployee(UUID id);
	public String delEmployee(UUID id);
	public Page<EmployeeDto> searchByPage(SearchDto searchDto);
	//public Page<EmployeeDto> searchByPageReport(SearchDto searchDto);
	
	public Page<EmployeeDto> searchByPages(SearchDateSearchDto searchDto);
	public EmployeeDto getEmployeeByCode(String code);
	
	//tong so luong nhan vien theo tinh
	public List<EmployeeVb> listEmployeebyTinh();
	
	//tong so luong nhan vien các huyện theo id tỉnh
	public List<EmployeeVb> listEmplbyTinh(UUID tinh);
	
	//tong so luong nhan vien theo tinh
	public List<EmployeeVb> listEmployeebyHuyen();
		
	//search by page theo tinh:
//	public Page<EmployeeDto> searchByPagebyTinh(SearchEmplbyTinhDto searchDto);
	public List<EmployeeDto> listEmployeeTinh();
	//tim empl theo tinh
//	public List<EmployeeDto> listEmployeeTinh(UUID tinh);
//	public Page<EmployeeDto> listEmployeeTinh(UUID tinh, int pageSize, int pageIndex);
	public List<EmployeeDto> listEmployeeTinh(UUID tinh);
	
	//tong sl nv theo huyen theo tinh
//	public List<EmployeeVb> listEmpByHuyen(String tinh);
	public List<EmployeeVb> listEmpByHuyen(UUID tinh);
	
	//1. Api tổng số lượng employee thêm mới Group by created date (mặc định  30 ngày gần nhất)
	public int getCountEmplbyDate();
	
	//tim kiem theo khoang time
	public List<EmployeeByDate> getEmplbyDate(String date, String toDate);
	
}
