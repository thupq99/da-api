package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Tinh;
import com.globits.da.dto.TinhDto;
import com.globits.da.dto.search.SearchDto;

public interface TinhService extends GenericService<Tinh, UUID> {
	public TinhDto saveOrupdate(UUID id, TinhDto dto);

	public Page<TinhDto> getPage(int pageSize, int pageIndex);

	public Boolean deleteTinh(UUID id);

	public TinhDto getCertificate(UUID id);

	public Page<TinhDto> searchByPage(SearchDto dto);

	public List<TinhDto> getAllTinh();
//	public Boolean deleteCheckById(UUID id);
}
