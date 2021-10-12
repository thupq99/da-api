package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Xa;
import com.globits.da.dto.XaDto;
import com.globits.da.dto.search.SearchDto;

public interface XaService extends GenericService<Xa, UUID> {
	public XaDto saveOrupdate(UUID id, XaDto dto);

	public Page<XaDto> getPage(int pageSize, int pageIndex);

	public Boolean deleteXa(UUID id);

	public XaDto getCertificate(UUID id);

	public Page<XaDto> searchByPage(SearchDto dto);

	public List<XaDto> getAllXa();
}
