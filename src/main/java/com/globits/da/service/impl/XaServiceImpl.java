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
import com.globits.da.domain.Xa;
import com.globits.da.dto.HuyenDto;
import com.globits.da.dto.XaDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.XaRepository;
import com.globits.da.service.XaService;

@Service
public class XaServiceImpl extends GenericServiceImpl<Xa, UUID> implements XaService {

	@Autowired
	XaRepository repo;

	@Override
	public XaDto saveOrupdate(UUID id, XaDto dto) {
		if (dto != null) {
			Xa entity = null;
			if (dto.getId() != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = repo.getOne(dto.getId());
			}
			if (entity == null) {
				entity = new Xa();
			}
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());

			entity = repo.save(entity);
			if (entity != null) {
				return new XaDto(entity);
			}
		}
		return null;
	}

	@Override
	public Page<XaDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return repo.getListPage(pageable);
	}

	@Override
	public Boolean deleteXa(UUID id) {
		if (id != null) {
			repo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public XaDto getCertificate(UUID id) {
		Xa entity = repo.getOne(id);
		if (entity != null) {
			return new XaDto(entity);
		}
		return null;
	}

	@Override
	public Page<XaDto> searchByPage(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from  Xa as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.XaDto(entity) from  Xa as entity where (1=1)  ";

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
		List<XaDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<XaDto> result = new PageImpl<XaDto>(entities, pageable, count);
		return result;
	}

	@Override
	public List<XaDto> getAllXa() {
		List<XaDto> listt = repo.getAllXa();
		return listt;
	}

}
