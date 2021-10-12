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
import com.globits.da.dto.TinhDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.impl.TinhServiceImpl;

import Exception.ValidInputDto;

@RestController
@RequestMapping("/api/tinh")
public class RestTinhController {

	@Autowired
	TinhServiceImpl service;

	ValidInputDto valid = new ValidInputDto();

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<TinhDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<TinhDto> results = service.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<TinhDto>>(results, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TinhDto> save(@RequestBody TinhDto dto) {
		String mgs = valid.ValidTinh(dto);
		if (mgs == "success") {
			TinhDto result = service.saveOrupdate(null, dto);
			return new ResponseEntity<TinhDto>(result, HttpStatus.OK);
		} else {
			dto.setErrorMessage(mgs);
			return new ResponseEntity<TinhDto>(dto, HttpStatus.BAD_REQUEST);
		}

	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TinhDto> save(@RequestBody TinhDto dto, @PathVariable UUID id) {
		TinhDto result = service.saveOrupdate(id, dto);
		return new ResponseEntity<TinhDto>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<TinhDto> getList(@PathVariable UUID id) {
		TinhDto result = service.getCertificate(id);
		return new ResponseEntity<TinhDto>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = service.deleteTinh(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/getAllTinh", method = RequestMethod.GET)
	public ResponseEntity<List<TinhDto>> getAllCategory() {
		List<TinhDto> result = service.getAllTinh();
		return new ResponseEntity<List<TinhDto>>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<TinhDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<TinhDto> page = this.service.searchByPage(searchDto);
		return new ResponseEntity<Page<TinhDto>>(page, HttpStatus.OK);
	}

}
