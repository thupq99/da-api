package com.globits.da.service.impl;

import java.util.ArrayList;
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
import com.globits.da.domain.Tinh;
import com.globits.da.domain.Vanbang;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.HuyenDto;
import com.globits.da.dto.VanbangDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.TinhRepository;
import com.globits.da.repository.VanbangRepository;
import com.globits.da.service.VanbangService;

@Service
public class VanbangServiceImpl extends GenericServiceImpl<Vanbang, UUID> implements VanbangService {

	@Autowired
	VanbangRepository vbRepo;

	@Autowired
	TinhRepository tRepo;

	@Autowired
	EmployeeRepository eRepo;

	@Override
	public VanbangDto saveOrupdate(UUID id, VanbangDto dto) {
		if (dto != null) {
			Vanbang entity = null;
			// sua
			if (id != null)
				entity = vbRepo.getOne(id);

			if (entity == null) {
				// them
				entity = new Vanbang();
			}

			// sua
			entity.setCode(dto.getCode());
			entity.setName(dto.getName());
			entity.setDateCap(dto.getDateCap());

			// setup tinh
			Tinh tinh = null;
			if (dto.getTinhdto() != null) {
				tinh = tRepo.getOne(dto.getTinhdto().getId());
			}
			entity.setTinh(tinh);

			// setup employee
			Employee em = null;
			if (dto.getEmployeedto() != null) {
				em = eRepo.getOne(dto.getEmployeedto().getId());
			}
			entity.setEmployee(em);

			entity = vbRepo.save(entity);

			// update
			if (entity != null) {
				return new VanbangDto(entity);
			}
		}
		return null;
	}

	@Override
	public List<VanbangDto> getAllVanbang() {
		return vbRepo.getListVb();
	}

	@Override
	public String delVb(UUID id) {
		String entity = null;
		if (id != null) {
			try {
				entity = getNameVb(id);

			} catch (Exception e) {
				return null;
			}
			if (entity != null) {
				vbRepo.deleteById(id);
				return "success";
			}

		}
		return null;
	}

	@Override
	public String getNameVb(UUID id) {
		return vbRepo.getVbbyID(id);
	}

	@Override
	public List<EmployeeDto> listEmployeebyVb() {
		// TODO Auto-generated method stub
		return vbRepo.listEmpByVb();
	}

	@Override
	public List<Float> getPercent() {
		List<Float> li = new ArrayList<>();
		float temp = 0;
		int a = vbRepo.getCountEmpl();
		System.out.println("a: " + a);

		// tra ve phan tram
		// => t = (double) (vbRepo.getCountEmplOne().size())/6 *100;
		// lam tron temp : (double) Math.round(temp * 100) / 100)
		temp = (float) Math.round((float) (vbRepo.getCountEmplOne().size()) / a * 100 * 100) / 100;
		System.out.println("%1: " + temp);

		li.add(temp);

		temp = (float) Math.round((float) (vbRepo.getCountEmplTwo().size()) / a * 100 * 100) / 100;
		System.out.println("%2: " + temp);
		li.add(temp);

		temp = (float) Math.round((float) (vbRepo.getCountEmplPlus().size()) / a * 100 * 100) / 100;
		System.out.println("%3: " + temp);
		li.add(temp);
		// TODO Auto-generated method stub
		return li;
	}

	@Override
	public List<Integer> getSLEmployee() {
		List<Integer> li = new ArrayList<>();
		int temp = 0;

		temp = vbRepo.getCountEmplOne().size();
		li.add(temp);

		temp = vbRepo.getCountEmplTwo().size();
		li.add(temp);

		temp = vbRepo.getCountEmplPlus().size();
		li.add(temp);
		return li;
	}

	@Override
	public List<Float> getPercentbyTi(String tinh) {
		List<Float> li = new ArrayList<>();
		float temp = 0;
		int sum = vbRepo.getCountEmplbyTi(tinh.toString());
		System.out.println("tong: " + sum);

		// tra ve phan tram
		// => t = (double) (vbRepo.getCountEmplOne().size())/6 *100;
		// lam tron temp : (double) Math.round(temp * 100) / 100)
		temp = (float) Math.round((float) (vbRepo.getCountEmplOnebyTi(tinh).size()) / sum * 100 * 100) / 100;
		System.out.println("%1: " + temp);
		li.add(temp);

//		System.out.println("1: "+vbRepo.getCountEmplOnebyTi(tinh.toString()).size());

		temp = (float) Math.round((float) (vbRepo.getCountEmplTwobyTi(tinh).size()) / sum * 100 * 100) / 100;
		System.out.println("%2: " + temp);
		li.add(temp);
//		System.out.println("2: "+vbRepo.getCountEmplTwobyTi(tinh).size());

		temp = (float) Math.round((float) (vbRepo.getCountEmplPlusbyTi(tinh).size()) / sum * 100 * 100) / 100;
		System.out.println("%3: " + temp);
		li.add(temp);

//		System.out.println("3: "+vbRepo.getCountEmplPlusbyTi(tinh).size());

		return li;
	}

	@Override
	public Page<EmployeeDto> getEmplPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return vbRepo.getlistEmpByVbPage(pageable);
	}

	@Override
	public Page<VanbangDto> searchByPage(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from  Vanbang as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.VanbangDto(entity) from  Vanbang as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, HuyenDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<VanbangDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<VanbangDto> result = new PageImpl<VanbangDto>(entities, pageable, count);
		return result;
	}

}
