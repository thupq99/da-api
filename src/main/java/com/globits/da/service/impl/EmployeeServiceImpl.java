package com.globits.da.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.OutputDto.EmployeeByDate;
import com.globits.OutputDto.EmployeeVb;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Huyen;
import com.globits.da.domain.Tinh;
import com.globits.da.domain.Xa;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.SearchDateSearchDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.dto.search.SearchEmplbyTinhDto;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.HuyenRepository;
import com.globits.da.repository.TinhRepository;
import com.globits.da.repository.XaRepository;
import com.globits.da.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, UUID> implements EmployeeService {

	@Autowired
	EmployeeRepository repo;

	@Autowired
	TinhRepository tinhRepo;

	@Autowired
	HuyenRepository huyenRepo;

	@Autowired
	XaRepository xaRepo;

	@Override
	public EmployeeDto saveOrUpdate(UUID id, EmployeeDto dto) {
		if (dto != null) {
			Employee entity = null;
			if (dto.getId() != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = repo.getOne(dto.getId());
			}
			if (entity == null) {
				// them
				entity = new Employee();
			}
			entity.setCode(dto.getCode());
			entity.setName(dto.getName());
			entity.setEmail(dto.getEmail());
			entity.setPhone(dto.getPhone());
			entity.setAge(dto.getAge());

			//
			Tinh tinh = null;
			if (dto.getTinhdto() != null)
				tinh = tinhRepo.getOne(dto.getTinhdto().getId());

			entity.setTinh(tinh);

			//
			Huyen huyen = null;
			if (dto.getHuyendto() != null) {
				huyen = huyenRepo.getOne(dto.getHuyendto().getId());

			}
			entity.setHuyen(huyen);
			//
			Xa xa = null;
			if (dto.getXadto() != null) {
				xa = xaRepo.getOne(dto.getXadto().getId());

			}
			entity.setXa(xa);
			//
			entity = repo.save(entity);
			// update
			if (entity != null) {
				return new EmployeeDto(entity, false);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	// get all
	public List<EmployeeDto> getAll() {
		return repo.getListEmployee();
	}

	@Override
	public Page<EmployeeDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return repo.getListPage(pageable);
	}

	@Override
	public EmployeeDto getEmployee(UUID id) {
		Employee entity = repo.getOne(id);
		if (entity != null) {
			return new EmployeeDto(entity);
		}
		return null;
	}

	@Override
	public String delEmployee(UUID id) {
		if (id != null) {
			repo.deleteById(id);
			return "success";
		}
		return null;
	}

	@Override
	public Page<EmployeeDto> searchByPage(SearchDto dto) {
		if (dto == null) {
			return null;
		}

		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";

		String orderBy = " ORDER BY entity.createDate DESC";

		String sqlCount = "select count(entity.id) from  Employee as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.EmployeeDto(entity) from  Employee as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, EmployeeDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<EmployeeDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<EmployeeDto> result = new PageImpl<EmployeeDto>(entities, pageable, count);
		return result;
	}

	@Override
	public Page<EmployeeDto> searchByPages(SearchDateSearchDto dto) {
		if (dto == null) {
			return null;
		}

		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";

		String orderBy = " ORDER BY entity.createDate DESC";

		String sqlCount = "select count(entity.id) from  Employee as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.EmployeeDto(entity) from  Employee as entity where (1=1)  ";

		if (dto.getFromDate() != null && dto.getToDate() != null) {// && StringUtils.hasText(dto.getToDate())
																	// &&StringUtils.hasText(dto.getFromDate())
			whereClause += "AND(entity.createDate BETWEEN :text AND :textt) ";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, EmployeeDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getToDate() != null && dto.getFromDate() != null) {
			
			System.out.println("_____________________________________");
			
			System.out.println(dto.getToDate() + "; " + dto.getFromDate());

			q.setParameter("text", dto.getToDate());
			q.setParameter("textt", dto.getFromDate());
			qCount.setParameter("text", dto.getToDate());
			qCount.setParameter("textt", dto.getFromDate());
		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<EmployeeDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<EmployeeDto> result = new PageImpl<EmployeeDto>(entities, pageable, count);
		return result;
	}

	@Override
	public EmployeeDto getEmployeeByCode(String code) {

		return repo.getEmpByCode(code);
	}

	@Override
	public List<EmployeeVb> listEmployeebyTinh() {
		// TODO Auto-generated method stub
		return repo.listEmpByTinh();
	}

	@Override
	public int getCountEmplbyDate() {

		return repo.getCountEmplbyDate();
	}

	@Override
	public List<EmployeeByDate> getEmplbyDate(String date, String toDate) {
		// TODO Auto-generated method stub
		return repo.getEmplbyDate(date, toDate);
	}

	@Override
//	public List<EmployeeVb> listEmpByHuyen(UUID tinh) {
	public List<EmployeeVb> listEmpByHuyen(UUID tinh) {
		// TODO Auto-generated method stub
		System.out.println("tinh: " + tinh);
		return repo.listEmpByHuyen(tinh);
	}

	@Override
	public List<EmployeeDto> listEmployeeTinh(UUID tinh) {
		if(tinh!=null) {
			return repo.listEmployeeTinh(tinh);
		}
		return null;
	}

	@Override
	public List<EmployeeDto> listEmployeeTinh() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeVb> listEmployeebyHuyen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeVb> listEmplbyTinh(UUID tinh) {
		// TODO Auto-generated method stub
		return repo.slEmpByHuyen(tinh.toString());
	}

}
