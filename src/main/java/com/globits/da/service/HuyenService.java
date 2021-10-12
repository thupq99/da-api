package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Huyen;
import com.globits.da.dto.HuyenDto;
import com.globits.da.dto.search.SearchDto;

public interface HuyenService extends GenericService<Huyen, UUID> {

	public HuyenDto saveOrupdate(UUID id, HuyenDto dto);

	public Page<HuyenDto> getPage(int pageSize, int pageIndex);

	public Boolean deleteHuyen(UUID id);

	public HuyenDto getCertificate(UUID id);

	public Page<HuyenDto> searchByPage(SearchDto dto);

	public List<HuyenDto> getAllHuyen();
}
