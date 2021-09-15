package com.globits.da.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, UUID> implements EmployeeService{

	@Autowired
	EmployeeRepository repo;
	
	@Override
	public EmployeeDto saveOrUpdate(UUID id, EmployeeDto dto) {
		if(dto!=null) {
			Employee entity = null;
			if(dto.getId()!=null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = repo.getOne(dto.getId());
			}
			if(entity==null) {
				//them
				entity = new Employee();
			}
			entity.setCode(dto.getCode());
			entity.setName(dto.getName());
			entity.setEmail(dto.getEmail());
			entity.setPhone(dto.getPhone());
			entity.setAge(dto.getAge());
			entity = repo.save(entity);
			
			if(entity!=null) {
				return new EmployeeDto(entity);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	//get all
	public List<EmployeeDto> getAll(){
		return repo.getListEmployee();
	}

	@Override
	public Page<EmployeeDto> getPage(int pageSize, int pageIndex) {
			Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
			return repo.getListPage(pageable);
	}

	@Override
	public EmployeeDto getEmployee(UUID id) {
		Employee entity = repo.getOne(id);
		if(entity!=null) {
			return new EmployeeDto(entity);
		}
		return null;
	}

	@Override
	public String delEmployee(UUID id) {
		if(id!=null) {
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

}
