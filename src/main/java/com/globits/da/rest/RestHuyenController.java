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

import com.globits.da.AFFakeConstants;
import com.globits.da.dto.HuyenDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.impl.HuyenServiceImpl;

import Exception.ValidInputDto;

@RestController
@RequestMapping("/api/huyen")
public class RestHuyenController {

	@Autowired
	HuyenServiceImpl service;

	ValidInputDto valid = new ValidInputDto();

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<HuyenDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<HuyenDto> results = service.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<HuyenDto>>(results, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<HuyenDto> save(@RequestBody HuyenDto dto) {
		String mgs = valid.ValidHuyen(dto);
		if (mgs == "success") {
			HuyenDto result = service.saveOrupdate(null, dto);
			return new ResponseEntity<HuyenDto>(result, HttpStatus.OK);
		} else {
			dto.setErrorMessage(mgs);
			return new ResponseEntity<HuyenDto>(dto, HttpStatus.BAD_REQUEST);
		}

	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HuyenDto> save(@RequestBody HuyenDto dto, @PathVariable UUID id) {
		HuyenDto result = service.saveOrupdate(id, dto);
		return new ResponseEntity<HuyenDto>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<HuyenDto> getList(@PathVariable UUID id) {
		HuyenDto result = service.getCertificate(id);
		return new ResponseEntity<HuyenDto>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = service.deleteHuyen(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/getAllHuyen", method = RequestMethod.GET)
	public ResponseEntity<List<HuyenDto>> getAllCategory() {
		List<HuyenDto> result = service.getAllHuyen();
		return new ResponseEntity<List<HuyenDto>>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<HuyenDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<HuyenDto> page = this.service.searchByPage(searchDto);
		return new ResponseEntity<Page<HuyenDto>>(page, HttpStatus.OK);
	}
}
