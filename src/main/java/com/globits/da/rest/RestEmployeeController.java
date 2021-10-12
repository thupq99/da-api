package com.globits.da.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.OutputDto.EmployeeByDate;
import com.globits.OutputDto.EmployeeVb;
import com.globits.OutputDto.SearchbyDate;
import com.globits.da.AFFakeConstants;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.SearchDateSearchDto;
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
		String headerValue = "attachment; filename=employees_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<EmployeeDto> listEmprs = empService.getAll();

		EmployeeExporter excelExporter = new EmployeeExporter(listEmprs);

		excelExporter.export(response);
	}

	// them
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@Valid @RequestBody EmployeeDto dto) {
		EmployeeDto checkCode = null;
		if (dto.getCode() != null) {
			checkCode = empService.getEmployeeByCode(dto.getCode());
		}
		if (checkCode != null) {
			return new ResponseEntity<String>("Code already exists", HttpStatus.BAD_REQUEST);
		}
		EmployeeDto result = empService.saveOrUpdate(null, dto);
		return new ResponseEntity<EmployeeDto>(result, HttpStatus.OK);
	}

	// sua
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<EmployeeDto> save(@Valid @RequestBody EmployeeDto dto, @PathVariable UUID id) {
		EmployeeDto result = empService.saveOrUpdate(id, dto);
		return new ResponseEntity<EmployeeDto>(result, HttpStatus.OK);
	}

	// get all
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeDto>> listEmployee() {
		List<EmployeeDto> empls = null;
		empls = empService.getAll();
		return new ResponseEntity<List<EmployeeDto>>(empls, HttpStatus.OK);
	}

	// phan trang
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<EmployeeDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<EmployeeDto> results = empService.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<EmployeeDto>>(results, HttpStatus.OK);
	}

	// lay 1 employee theo id
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable UUID id) {
		EmployeeDto results = empService.getEmployee(id);
		return new ResponseEntity<EmployeeDto>(results, HttpStatus.OK);
	}

	// xoa 1 employee
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delEmployee(@PathVariable UUID id) {

		String mgs = empService.delEmployee(id);
		if (mgs != null) {
			return new ResponseEntity<String>(mgs, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Id khong ton tai", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<EmployeeDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<EmployeeDto> page = this.empService.searchByPage(searchDto);
		return new ResponseEntity<Page<EmployeeDto>>(page, HttpStatus.OK);
	}

	// tong so luong nhan vien theo tinh
	@RequestMapping(value = "/report/soluong", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeVb>> getListEmbyTinh() {
		List<EmployeeVb> list = empService.listEmployeebyTinh();
		return new ResponseEntity<List<EmployeeVb>>(list, HttpStatus.OK);
	}
	
	// tong so luong nhan vien các huyen theo tinh
	@RequestMapping(value = "/report/soluongbyHuyen/{tinh}", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeVb>> getListEmbyHuyen(@PathVariable UUID tinh) {
		List<EmployeeVb> list = empService.listEmplbyTinh(tinh);
		return new ResponseEntity<List<EmployeeVb>>(list, HttpStatus.OK);
	}

	// api tim kiem theo tinh => 
	@RequestMapping(value = "/reportEmplByTinh/{tinh}", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeDto>> getListEmTinh(@PathVariable UUID tinh) {
		List<EmployeeDto> results = empService.listEmployeeTinh(tinh);
		return new ResponseEntity<List<EmployeeDto>>(results, HttpStatus.OK);
	}
	
//	@RequestMapping(value = "/report/EmpbyTinh", method = RequestMethod.GET)
//	public ResponseEntity<List<EmployeeDto>> getListEmbyTinh(@RequestParam UUID tinh) {
//		List<EmployeeDto> list = ;
//		return new ResponseEntity<List<EmployeeDto>>(list, HttpStatus.OK);
//	}

	// api tim sl nv theo huyen
//	@RequestMapping(value = "/report/empbyhuyen/{tinh_id}", method = RequestMethod.GET)
//	public ResponseEntity<List<EmployeeVb>> getListEmHuyen(@PathVariable UUID tinh_id) {
//		List<EmployeeVb> list = empService.listEmpByHuyen(tinh_id);
//		return new ResponseEntity<List<EmployeeVb>>(list, HttpStatus.OK);
//	}

	// api tim sl nv them moi trong 30 ngay gan day
	@RequestMapping(value = "/report/slCreate", method = RequestMethod.GET)
	public ResponseEntity<Integer> getListEmCreate() {
		int res = empService.getCountEmplbyDate();
		return new ResponseEntity<Integer>(res, HttpStatus.OK);
	}

	// tim kiem theo khoang thoi gian cach 1

	@RequestMapping(value = "/report/slCreate", method = RequestMethod.POST)
	public ResponseEntity<List<EmployeeByDate>> getListEmTinh(@RequestBody SearchbyDate date) {
		List<EmployeeByDate> list = null;
		if (date != null) {

			// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date1 = date.getDate();
			String date2 = date.getTodate();
			list = empService.getEmplbyDate(date1, date2);
			return new ResponseEntity<List<EmployeeByDate>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<List<EmployeeByDate>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// tim kiem theo khoang thoi gian cach 2
	@Secured({ AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/report/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<EmployeeDto>> searchReportByPage(@RequestBody SearchDateSearchDto searchDto) {
		Page<EmployeeDto> page = this.empService.searchByPages(searchDto);
		return new ResponseEntity<Page<EmployeeDto>>(page, HttpStatus.OK);
	}
//	public ResponseEntity<Page<EmployeeDto>> searchReportByPage(@RequestBody SearchDto searchDto) {
//		Page<EmployeeDto> page = this.empService.searchByPage(searchDto);
//		return new ResponseEntity<Page<EmployeeDto>>(page, HttpStatus.OK);
//	}

//	//api tim sl nv theo huyen
//	@RequestMapping(value = "/report/empbyTinh", method = RequestMethod.GET)
//	public ResponseEntity<List<EmployeeDto>> getListEmTinh(@RequestBody String tinh){
//		List<EmployeeDto> list = empService.listEmployeeTinh(tinh);
//		return new ResponseEntity<List<EmployeeDto>>(list, HttpStatus.OK);
//	}
}
