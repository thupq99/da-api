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
import com.globits.da.domain.Tinh;
import com.globits.da.dto.TinhDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.TinhRepository;
import com.globits.da.service.TinhService;

@Service
public class TinhServiceImpl extends GenericServiceImpl<Tinh, UUID> implements TinhService {

	@Autowired
	TinhRepository repo;

	@Override
	public TinhDto saveOrupdate(UUID id, TinhDto dto) {

		if (dto != null) {
			Tinh entity = null;
			if (dto.getId() != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = repo.getOne(dto.getId());
			}
			if (entity == null) {
				entity = new Tinh();
			}
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());

			entity = repo.save(entity);
			if (entity != null) {
				return new TinhDto(entity);
			}
		}
		return null;
	}

	@Override
	public Page<TinhDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return repo.getListPage(pageable);
	}

	@Override
	public Boolean deleteTinh(UUID id) {
		if (id != null) {
			repo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public TinhDto getCertificate(UUID id) {
		Tinh entity = repo.getOne(id);
		if (entity != null) {
			return new TinhDto(entity);
		}
		return null;
	}

	@Override
	public Page<TinhDto> searchByPage(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from  Tinh as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.TinhDto(entity) from  Tinh as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, TinhDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<TinhDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<TinhDto> result = new PageImpl<TinhDto>(entities, pageable, count);
		return result;
	}

	@Override
	public List<TinhDto> getAllTinh() {
		List<TinhDto> listt = repo.getAllTinh();
		return listt;
	}

//	@Override
//	public String delTinh(UUID id) {
//		String entity = null;
//		if(id!=null) {
//			try {
//				entity = getTinh(id);
//				
//			} catch (Exception e) {
//				return null;
//			}
//			if(entity!=null) {
//				repo.deleteById(id);
//				return "success";
//			}
//			
//		}
//		return null;
//	}
//
//	@Override
//	public String getTinh(UUID id) {
//		String tinh = null;
//		tinh = repo.getTinh(id);
//		return tinh;
//	}

}
