package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.OutputDto.EmployeeByDate;
import com.globits.OutputDto.EmployeeVb;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDto;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
	@Query("select new com.globits.da.dto.EmployeeDto(em) from Employee em")
	List<EmployeeDto> getListEmployee();

	@Query("select new com.globits.da.dto.EmployeeDto(em) from Employee em")
	Page<EmployeeDto> getListPage(Pageable pageable);

	@Query("select new com.globits.da.dto.EmployeeDto(em) from Employee em where em.code = ?1")
	EmployeeDto getEmpByCode(String code);

	// tong so luong nhan vien theo tinh
	@Query(value = "SELECT COUNT(e.tinh_id) AS Sl, t.name FROM tbl_employee e INNER JOIN tbl_tinh t ON e.tinh_id=t.id GROUP BY e.tinh_id", nativeQuery = true)
	List<EmployeeVb> listEmpByTinh();
	
	// tong so luong nhan vien theo huyen tim theo id tinh
	@Query(value = "SELECT COUNT(e.huyen_id) AS Sl,h.name FROM tbl_employee e INNER JOIN tbl_huyen h ON e.huyen_id=h.id WHERE e.tinh_id=?1 GROUP BY e.huyen_id ", nativeQuery = true)
	List<EmployeeVb> slEmpByHuyen(String tinh);
	//	List<EmployeeVb> slEmpByHuyen(UUID tinh);

	
	// api tim kiem employee theo tinh
	@Query("select new com.globits.da.dto.EmployeeDto(em) from Employee em where em.tinh.id = ?1")
	List<EmployeeDto> listEmployeeTinh(UUID tinh);


	// tong so luong employee theo tung huyen
//	@Query(value = "SELECT COUNT(e.huyen_id) AS Sl, h.name FROM ((tbl_employee e INNER JOIN tbl_huyen h ON e.huyen_id=h.id) "
//			+ "INNER JOIN tbl_tinh t ON e.tinh_id=t.id) WHERE t.name = ?1 GROUP BY e.huyen_id", nativeQuery = true)
//	List<EmployeeVb> listEmpByHuyen(String tinh);
	@Query(value = "SELECT COUNT(e.huyen_id) AS Sl,h.name FROM tbl_employee e INNER JOIN tbl_huyen h ON e.huyen_id=h.id WHERE e.tinh_id=?1 GROUP BY e.huyen_id ", nativeQuery = true)
	List<EmployeeVb> listEmpByHuyen(UUID tinh);

	// #4
	// 1. Api tổng số lượng employee thêm mới Group by created date (mặc định 30
	// ngày gần nhất)

	@Query(value = "SELECT COUNT(*) FROM tbl_employee e WHERE e.create_date BETWEEN DATE_SUB(NOW(), INTERVAL '30' DAY) AND NOW()", nativeQuery = true)
	int getCountEmplbyDate();

	// select new com.globits.da.dto.EmployeeDto(em) from Employee em where
	// em.create_date BETWEEN ?1 AND ?2
	@Query(value = "select em.name, em.code, em.email, em.phone, em.age from tbl_employee em where em.create_date BETWEEN ?1 AND ?2", nativeQuery = true)
	List<EmployeeByDate> getEmplbyDate(String date, String toDate);
}
