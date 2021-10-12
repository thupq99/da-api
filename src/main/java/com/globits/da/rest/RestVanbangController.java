package com.globits.da.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.OutputDto.SearchTinh;
import com.globits.da.AFFakeConstants;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.VanbangDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.impl.VanbangServiceImpl;

import Exception.ValidInputDto;

@RestController
@RequestMapping("/api/vanbang")
public class RestVanbangController {
	@Autowired
	VanbangServiceImpl service;

	ValidInputDto valid = new ValidInputDto();
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<VanbangDto> save(@RequestBody VanbangDto dto) {
		String mgs = valid.ValidvanBang(dto);
		if(mgs == "success") {
			dto.setErrorMessage(null);
			VanbangDto res = service.saveOrupdate(null, dto);
			return new ResponseEntity<VanbangDto>(res, HttpStatus.OK);
		}
		else {
			dto.setErrorMessage(mgs);
			return new ResponseEntity<VanbangDto>(dto, HttpStatus.BAD_REQUEST);
		}
	}

	// sua
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<VanbangDto> update(@RequestBody VanbangDto dto, @PathVariable UUID id) {
		VanbangDto res = service.saveOrupdate(id, dto);
		return new ResponseEntity<VanbangDto>(res, HttpStatus.OK);
	}

	// get all
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<VanbangDto>> getList() {
		List<VanbangDto> list = service.getAllVanbang();
		return new ResponseEntity<List<VanbangDto>>(list, HttpStatus.OK);
	}

	// get a
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getName(@PathVariable UUID id) {
		String name = service.getNameVb(id);
		return new ResponseEntity<String>(name, HttpStatus.OK);
	}

	// xoa
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delVb(@PathVariable UUID id) {
		String res = service.delVb(id);
		// if(res!=null) return new ResponseEntity<String>(res, HttpStatus.OK);
		System.out.println(res);
		if (res == null)
			return new ResponseEntity<String>("id does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}

	// search by page
	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<VanbangDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<VanbangDto> page = this.service.searchByPage(searchDto);
		return new ResponseEntity<Page<VanbangDto>>(page, HttpStatus.OK);
	}

//	//thong ke nhan vien >=2 van bang
	@RequestMapping(value = "/emplVb", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeDto>> getListEmbyVb() {
		List<EmployeeDto> list = service.listEmployeebyVb();
		return new ResponseEntity<List<EmployeeDto>>(list, HttpStatus.OK);
	}

	// thong ke nhan vien >=2 van bang phan trang
	@RequestMapping(value = "/emplVb/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<EmployeeDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<EmployeeDto> results = service.getEmplPage(pageSize, pageIndex);
		return new ResponseEntity<Page<EmployeeDto>>(results, HttpStatus.OK);
	}

	// phan tram so luogn employee co vb?
	@RequestMapping(value = "/report/percent", method = RequestMethod.GET)
	public ResponseEntity<List<Float>> getPercentEmbyVb() {
		List<Float> listPer = service.getPercent();
		return new ResponseEntity<List<Float>>(listPer, HttpStatus.OK);
	}

	// phan tram so luong employee co vb? theo tinh
	@RequestMapping(value = "/report/percentTi", method = RequestMethod.POST)
	public ResponseEntity<List<Float>> getPercentEmbyVb(@RequestBody SearchTinh tinh) {
		String name = tinh.getName();
		System.out.println(name);
		List<Float> listPer = service.getPercentbyTi(name);
		return new ResponseEntity<List<Float>>(listPer, HttpStatus.OK);
	}

	// so luogn employee co vb?
	@RequestMapping(value = "/report/employeeVb", method = RequestMethod.GET)
	public ResponseEntity<List<Integer>> getEmbyVb() {
		List<Integer> listPer = service.getSLEmployee();
		return new ResponseEntity<List<Integer>>(listPer, HttpStatus.OK);
	}
}
