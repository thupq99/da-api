package com.globits.da.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.service.TestApiService;

@RestController
@RequestMapping("/api")
public class RestFirstApiControler {
	@Autowired
	TestApiService testApiService;
	
	@RequestMapping(value = "/MyFistApi", method = RequestMethod.GET)
	public ResponseEntity<String> GetApi() {
		String str = null;
		str = testApiService.getApi();
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
}
