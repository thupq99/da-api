package com.globits.da.service.impl;

import org.springframework.stereotype.Service;

import com.globits.da.service.TestApiService;

@Service
public class TestApiServiceImpl implements TestApiService{

	@Override
	public String getApi() {	
		return "MyFirst ApiService";
	}

}
