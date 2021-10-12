package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.OutputDto.EmployeeVb;
import com.globits.da.domain.Vanbang;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.VanbangDto;

@Repository
public interface VanbangRepository extends JpaRepository<Vanbang, UUID> {
	@Query("select new com.globits.da.dto.VanbangDto(h) from Vanbang h")
	List<VanbangDto> getListVb();

	@Query("SELECT h.name FROM Vanbang h where h.id = ?1 ")
	String getVbbyID(UUID id);

	// thong ke nhan vien >=2 van bang by page
	@Query("SELECT new com.globits.da.dto.EmployeeDto(e) FROM Employee e JOIN Vanbang vb ON vb.employee = e GROUP BY vb.employee HAVING COUNT(vb.employee)>=2 ")
	Page<EmployeeDto> getlistEmpByVbPage(Pageable pageable);

	// thong ke nhan vien > 2 van bang
	@Query("SELECT new com.globits.da.dto.EmployeeDto(e) FROM Employee e JOIN Vanbang vb ON vb.employee = e GROUP BY vb.employee HAVING COUNT(vb.employee)>2 ")
	List<EmployeeDto> listEmpByVb();

	// tong so luong nhan vien co van bang
	@Query(value = "SELECT COUNT(DISTINCT v.employee_id) FROM tbl_vanbang v ", nativeQuery = true)
	int getCountEmpl();

	// tong nhan vien 1 van bang
	@Query(value = "SELECT vb.employee_id, vb.name FROM tbl_vanbang vb GROUP BY vb.employee_id HAVING  COUNT(vb.employee_id)=1", nativeQuery = true)
	List<EmployeeVb> getCountEmplOne();

	// tong nhan vien 2 van bang
	@Query(value = "SELECT vb.employee_id, vb.name FROM tbl_vanbang vb GROUP BY vb.employee_id HAVING  COUNT(vb.employee_id)=2", nativeQuery = true)
	List<EmployeeVb> getCountEmplTwo();

	// tong nhan vien >2 van bang
	@Query(value = "SELECT vb.employee_id, vb.name FROM tbl_vanbang vb GROUP BY vb.employee_id HAVING  COUNT(vb.employee_id)>2", nativeQuery = true)
	List<EmployeeVb> getCountEmplPlus();

	// #3
	// tong nhan vien co bang trong tinh
//	@Query(value = "SELECT COUNT(DISTINCT vb.employee_id) FROM tbl_vanbang vb INNER JOIN tbl_tinh t ON vb.tinh_id=t.id WHERE t.name =?1", nativeQuery = true)
//	@Query(value = "SELECT COUNT(DISTINCT vb.employee_id) FROM tbl_vanbang vb WHERE vb.tinh_id = ?1", nativeQuery = true)
	@Query(value = "SELECT COUNT(DISTINCT vb.employee_id) FROM tbl_vanbang vb INNER JOIN tbl_employee e ON vb.employee_id=e.id INNER JOIN tbl_tinh t ON e.tinh_id=t.id WHERE t.name =?1", nativeQuery = true)
	int getCountEmplbyTi(String tinh);

	// tong nhan vien 1 van bang
//	@Query(value = "SELECT vb.employee_id, vb.name FROM tbl_vanbang vb INNER JOIN tbl_tinh t ON vb.tinh_id=t.id WHERE t.name =?1  GROUP BY vb.employee_id "
//			+ "HAVING  COUNT(vb.employee_id)=1", nativeQuery = true)
	@Query(value = "SELECT vb.employee_id, vb.name FROM tbl_vanbang vb INNER JOIN tbl_employee e ON vb.employee_id=e.id INNER JOIN tbl_tinh t ON e.tinh_id=t.id WHERE t.name = ?1 GROUP BY vb.employee_id "
			+ "HAVING COUNT(vb.employee_id)=1", nativeQuery = true)
	List<EmployeeVb> getCountEmplOnebyTi(String tinh);

	// tong nhan vien 2 van bang
//	@Query(value = "SELECT vb.employee_id, vb.name FROM tbl_vanbang vb INNER JOIN tbl_tinh t ON vb.tinh_id=t.id WHERE t.name =?1  GROUP BY vb.employee_id "
//			+ "HAVING  COUNT(vb.employee_id)=2", nativeQuery = true)
	@Query(value = "SELECT vb.employee_id, vb.name FROM tbl_vanbang vb INNER JOIN tbl_employee e ON vb.employee_id=e.id INNER JOIN tbl_tinh t ON e.tinh_id=t.id WHERE t.name = 'nam dinh' GROUP BY "
			+ "vb.employee_id HAVING COUNT(vb.employee_id)=2", nativeQuery = true)
	List<EmployeeVb> getCountEmplTwobyTi(String tinh);

	// tong nhan vien >2 van bang
//	@Query(value = "SELECT vb.employee_id, vb.name FROM tbl_vanbang vb INNER JOIN tbl_tinh t ON vb.tinh_id=t.id WHERE t.name =?1  GROUP BY vb.employee_id "
//			+ "HAVING  COUNT(vb.employee_id)>2", nativeQuery = true)
	@Query(value = "SELECT vb.employee_id, vb.name FROM tbl_vanbang vb INNER JOIN tbl_employee e ON vb.employee_id=e.id INNER JOIN tbl_tinh t ON e.tinh_id=t.id WHERE t.name = 'nam dinh' GROUP BY "
			+ "vb.employee_id HAVING COUNT(vb.employee_id)>2", nativeQuery = true)
	List<EmployeeVb> getCountEmplPlusbyTi(String tinh);

}
