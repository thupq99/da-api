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
import com.globits.da.domain.Huyen;
import com.globits.da.domain.Tinh;
import com.globits.da.dto.HuyenDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.HuyenRepository;
import com.globits.da.repository.TinhRepository;
import com.globits.da.service.HuyenService;

@Service
public class HuyenServiceImpl extends GenericServiceImpl<Huyen, UUID> implements HuyenService {

	@Autowired
	HuyenRepository repo;

	@Autowired
	TinhRepository tinhRepo;

	@Override
	public HuyenDto saveOrupdate(UUID id, HuyenDto dto) {
		if (dto != null) {
			Huyen entity = null;
			if (dto.getId() != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = repo.getOne(dto.getId());
			}
			if (entity == null) {
				entity = new Huyen();
			}
			entity.setCode(dto.getCode());
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			//
			Tinh tinh = null;
			if (dto.getTinhdto() != null) {
				tinh = tinhRepo.getOne(dto.getTinhdto().getId());

			}
			entity.setTinh(tinh);

			entity = repo.save(entity);

			if (entity != null) {
				return new HuyenDto(entity);
			}
		}
		return null;
	}

	@Override
	public Page<HuyenDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return repo.getListPage(pageable);
	}

	@Override
	public Boolean deleteHuyen(UUID id) {
		if (id != null) {
			repo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public HuyenDto getCertificate(UUID id) {
		Huyen entity = repo.getOne(id);
		if (entity != null) {
			return new HuyenDto(entity);
		}
		return null;
	}

	@Override
	public Page<HuyenDto> searchByPage(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from  Huyen as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.HuyenDto(entity) from  Huyen as entity where (1=1)  ";

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
		List<HuyenDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<HuyenDto> result = new PageImpl<HuyenDto>(entities, pageable, count);
		return result;
	}

	@Override
	public List<HuyenDto> getAllHuyen() {
		List<HuyenDto> listt = repo.getAllHuyen();
		return listt;
	}

}
