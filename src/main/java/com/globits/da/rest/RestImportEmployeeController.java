package com.globits.da.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employee_import")
public class RestImportEmployeeController {

	@Autowired
	EmployeeRepository repository;

	@RequestMapping(method = RequestMethod.POST)
	public List<EmployeeDto> importExcelFile(@RequestParam("file") MultipartFile files) throws IOException {

		List<EmployeeDto> EmployeeDtos = new ArrayList<>();

		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());

		// Read student data form excel file sheet1.
		XSSFSheet worksheet = workbook.getSheetAt(0);
		for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				XSSFRow row = worksheet.getRow(index);
				EmployeeDto dto = new EmployeeDto();

				dto.setCode(getCellValue(row, 0));
				dto.setName(getCellValue(row, 1));
				dto.setEmail(getCellValue(row, 2));
				dto.setPhone(getCellValue(row, 3));
				dto.setAge(convertStringToInt(getCellValue(row, 4)));

				EmployeeDtos.add(dto);
			}
		}

		// Save to db.
		List<Employee> entities = new ArrayList<>();
		if (EmployeeDtos.size() > 0) {
			EmployeeDtos.forEach(x -> {
				Employee entity = new Employee();
				entity.setCode(x.getCode());
				entity.setName(x.getName());
				entity.setEmail(x.getEmail());
				entity.setPhone(x.getPhone());
				entity.setAge(x.getAge());

				entities.add(entity);
			});

			repository.saveAll(entities);
		}

		return EmployeeDtos;
	}

	private int convertStringToInt(String str) {
		int result = 0;

		if (str == null || str.isEmpty() || str.trim().isEmpty()) {
			return result;
		}

		result = Integer.parseInt(str);

		return result;
	}

	private String getCellValue(Row row, int cellNo) {
		DataFormatter formatter = new DataFormatter();

		Cell cell = row.getCell(cellNo);

		return formatter.formatCellValue(cell);
	}
}