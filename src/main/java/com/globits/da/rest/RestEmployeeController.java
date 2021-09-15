package com.globits.da.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.dto.CategoryDto;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.EmployeeExporter;
import com.globits.da.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class RestEmployeeController {

	@Autowired
	EmployeeService empService;
     
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<EmployeeDto> listEmprs = empService.getAll();
         
        EmployeeExporter excelExporter = new EmployeeExporter(listEmprs);
         
        excelExporter.export(response);    
    } 
    
	//them sua
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto dto) {
		EmployeeDto result = empService.saveOrUpdate(null, dto);
		return new ResponseEntity<EmployeeDto>(result, HttpStatus.OK);	
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto dto, @PathVariable UUID id) {
		EmployeeDto result = empService.saveOrUpdate(id, dto);
		return new ResponseEntity<EmployeeDto>(result, HttpStatus.OK);	
	}
	//get all
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public  ResponseEntity<List<EmployeeDto>> listEmployee(){
		List<EmployeeDto> empls = null;
		empls = empService.getAll();
		return new ResponseEntity<List<EmployeeDto>>(empls, HttpStatus.OK);
	}
	
	//phan trang
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<EmployeeDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<EmployeeDto> results = empService.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<EmployeeDto>>(results, HttpStatus.OK);
	}
	
	//lay 1 employee theo id
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable UUID id) {
		EmployeeDto results = empService.getEmployee(id);
		return new ResponseEntity<EmployeeDto>(results, HttpStatus.OK);
	}
	
	//xoa 1 employee
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delEmployee(@PathVariable UUID id) {
		
		String mgs = empService.delEmployee(id);
		if(mgs!=null) {
			return new ResponseEntity<String>(mgs, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Id khong ton tai", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<EmployeeDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<EmployeeDto> page = this.empService.searchByPage(searchDto);
		return new ResponseEntity<Page<EmployeeDto>>(page, HttpStatus.OK);
	}
}
