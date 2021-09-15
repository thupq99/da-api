package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Category;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDto;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
	@Query("select new com.globits.da.dto.EmployeeDto(em) from Employee em")
//	@Query("SELECT e.code, e.name, e.age, e.email, e.phone FROM Employee e")
	List<EmployeeDto> getListEmployee();
	@Query("select new com.globits.da.dto.EmployeeDto(em) from Employee em")
	Page<EmployeeDto> getListPage(Pageable pageable);
}
