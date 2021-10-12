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
import com.globits.da.dto.XaDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.impl.XaServiceImpl;

import Exception.ValidInputDto;

@RestController
@RequestMapping("/api/xa")
public class RestXaController {

	@Autowired
	XaServiceImpl service;

	ValidInputDto valid = new ValidInputDto();

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<XaDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<XaDto> results = service.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<XaDto>>(results, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<XaDto> save(@RequestBody XaDto dto) {
		String mgs = valid.ValidXa(dto);
		if (mgs == "success") {
			XaDto result = service.saveOrupdate(null, dto);
			return new ResponseEntity<XaDto>(result, HttpStatus.OK);
		} else {
			dto.setErrorMessage(mgs);
			return new ResponseEntity<XaDto>(dto, HttpStatus.BAD_REQUEST);
		}

	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<XaDto> save(@RequestBody XaDto dto, @PathVariable UUID id) {
		XaDto result = service.saveOrupdate(id, dto);
		return new ResponseEntity<XaDto>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<XaDto> getList(@PathVariable UUID id) {
		XaDto result = service.getCertificate(id);
		return new ResponseEntity<XaDto>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = service.deleteXa(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/getAllXa", method = RequestMethod.GET)
	public ResponseEntity<List<XaDto>> getAllCategory() {
		List<XaDto> result = service.getAllXa();
		return new ResponseEntity<List<XaDto>>(result, HttpStatus.OK);
	}

	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<XaDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<XaDto> page = this.service.searchByPage(searchDto);
		return new ResponseEntity<Page<XaDto>>(page, HttpStatus.OK);
	}
}
