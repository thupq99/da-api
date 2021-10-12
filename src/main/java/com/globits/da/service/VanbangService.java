package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Vanbang;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.VanbangDto;
import com.globits.da.dto.search.SearchDto;

public interface VanbangService extends GenericService<Vanbang, UUID> {

	public VanbangDto saveOrupdate(UUID id, VanbangDto dto);

	public Page<EmployeeDto> getEmplPage(int pageSize, int pageIndex);

	public List<VanbangDto> getAllVanbang();

	public String delVb(UUID id);

	public String getNameVb(UUID id);

	public Page<VanbangDto> searchByPage(SearchDto dto);

	public List<EmployeeDto> listEmployeebyVb();

	public List<Float> getPercent();

	public List<Integer> getSLEmployee();

	// list chua thong tin so luong nv co 1 2 >2 vb theo tinh
	public List<Float> getPercentbyTi(String tinh);
}
